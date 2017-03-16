package com.dongao.oa.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dongao.oa.config.SystemSettings;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.form.WorkflowBean;
import com.dongao.oa.pojo.*;
import com.dongao.oa.service.*;
import com.dongao.oa.utils.*;
import com.dongao.oa.utils.resultType.AppResultModel;
import com.dongao.oa.utils.resultType.ResultMessage;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yangjie on 2016/9/20.
 * APP采购表单controller
 */

@Controller
@RequestMapping("/app")
public class AppCategoryController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private SystemSettings systemSettings;
    @Autowired
    private ClassifyService classifyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private DeploymentCatrgoryService deploymentCatrgoryService;
    @Autowired
    private OrganizationService organizationService;

    /**
     * 保存流程表单 / 更新流程表单
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveCategory", method = {RequestMethod.GET, RequestMethod.POST})
    public AppResultModel saveCategory(@RequestBody String content) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage result = new ResultMessage();
        String msg = "";
        try {
            Map<String, Object> user = getAPPUser(request);
            if (user != null && user.size() > 0) {
                String loginName = (String) user.get("user_name");
                JSONObject jsonObject = JsonUtils.toJsonObject(JsonUtils.getString(content));
                String scategory = String.valueOf(jsonObject.get("category"));
                String deleteItemId = String.valueOf(jsonObject.get("deleteId"));
                if (StringUtils.isNotEmpty(scategory)) {
                    Category category = JSON.parseObject(scategory, Category.class);
                    category.setCreateBy(loginName);
                    category.setCreateDate(new Date());
                    category.setUpdateBy(loginName);
                    category.setUpdateDate(new Date());
                    category.setUserId((Long) user.get("userId"));
                    category.setStatus(BaseConst.NOTSTART);
                    Map<String, Object> map = userService.selectUserInfoByUserId((Long) user.get("userId"));
                    category.setOrgId((Long) map.get("orgId"));
                    category.setTitle(((String) user.get("real_name")) + "的采购");
                    List<CategoryItem> categoryItems = category.getCategoryItems();
                    if (categoryItems != null && categoryItems.size() > 0) {
                        long categoryId = 0;
                        if (category.getId() == null) {
                            categoryId = categoryService.saveCategory(category, categoryItems);
                        } else {
                            categoryId = categoryService.updateCategory(category, categoryItems, deleteItemId);
                        }
                        if (categoryId > 0) {
                            result.setCode(BaseConst.SUCCEED);
                            msg = BaseConst.CATEGORYSUCCEED;
                            appResultModel.setBody(String.valueOf(categoryId));
                        } else {
                            result.setCode(BaseConst.FAILURE);
                            msg = BaseConst.ITEMSFAILURE;
                        }
                    } else {
                        result.setCode(BaseConst.NOTNULL);
                        msg = BaseConst.ITEMSNOTNULL;
                    }
                } else {
                    result.setCode(BaseConst.NOTNULL);
                    msg = BaseConst.CATETNULL;
                }
            } else {
                result.setCode(BaseConst.NOTLOGIN);
                msg = BaseConst.USERNOTLOGIN;
            }
        } catch (Exception e) {
            result.setCode(BaseConst.FAILURE);
            msg = BaseConst.ITEMSFAILURE;
            e.printStackTrace();
        }
        result.setMsg(msg);
        appResultModel.setResult(result);
        return appResultModel;
    }

    @RequestMapping(value = "/categoryDetail", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AppResultModel categoryDetail(@RequestBody String id) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        try {
            Map<String, Object> user = getAPPUser(request);
            if (user != null && user.size() > 0) {
                JSONObject jsonObject = JsonUtils.toJsonObject(JsonUtils.getString(id));
                id = (String) jsonObject.get("id");
                if (StringUtils.isNotEmpty(id)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("applDepar", user.get("orgName"));
                    map.put("realName", user.get("real_name"));
                    map.put("realName", user.get("img_url"));
                    if (StringUtils.isNotEmpty(id)) {
                        Category category = categoryService.selectOne(Long.valueOf(id));
                        List<CategoryItem> items = new ArrayList<CategoryItem>();
                        List<CategoryItem> categoryItems = categoryService.selectCategoryItemByCategoryIdNoPage(category.getId());
                        for (CategoryItem item : categoryItems) {
                            item.setClassifyName(classifyService.selectOne(item.getClassifyId()).getName());
                            items.add(item);
                        }
                        category.setCategoryItems(items);
                        map.put("category", category); //询价前
                        if (category.getAssignee() != null)
                            map.put("assignee", userService.selectOne(category.getAssignee()).getRealName());
                        List<Comment> commentList = workflowService.getCommentListByObject(category);
                        List<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
                        for (Comment comment : commentList) {
                            Map<String, Object> com = new HashMap<String, Object>();
                            User assignee = userService.selectOne(Long.valueOf(comment.getUserId()));
                            if (assignee != null)
                                com.put("userName", assignee.getRealName());
                            com.put("time", DateUtils.toString(comment.getTime(), DateUtils.DEFAULT_DATETIME_FORMAT_MINUTE));
                            com.put("fullMessage", comment.getFullMessage());
                            if (Long.valueOf(comment.getUserId()) == category.getUserId())
                                com.put("status", "发起申请");
                            else
                                com.put("status", "已审批");
                            comments.add(com);
                        }
                        map.put("comments", comments);
                        List<Task> list = taskService.createTaskQuery().taskCategory(id).list();
                        if (list != null && list.size() > 0) {
                            map.put("taskId", list.get(0).getId());
                        }
                        appResultModel.setBody(map);
                    }
                } else {
                    resultMessage.setCode(BaseConst.NOTNULL);
                    resultMessage.setMsg(BaseConst.CATEGORYIDNULL);
                }
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.CATEGORYFAILURE);
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }

    @RequestMapping(value = "/classifyList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AppResultModel classifyList() {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        Map<String, Object> user = getAPPUser(request);
        if (user != null && user.size() > 0) {
            List<Map<String, Object>> classifys = classifyService.selectAll();
            appResultModel.setBody(classifys);
        } else {
            resultMessage.setCode(BaseConst.NOTLOGIN);
            resultMessage.setMsg(BaseConst.USERNOTLOGIN);
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }

    /**
     * 办理任务
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submitCateGoryTask", method = RequestMethod.POST)
    public AppResultModel submitCateGoryTask(@RequestBody String content) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        try {
            Map<String, Object> user = UserUtils.getAPPCurrentUser();
            if (user != null && user.size() > 0) {
                WorkflowBean workflowBean = JsonUtils.jsonToBean(JsonUtils.getString(content), WorkflowBean.class);
                Category category = categoryService.selectCategoryById(workflowBean.getFormId());
                if (category == null) {
                    resultMessage.setCode(BaseConst.NOTNULL);
                    resultMessage.setMsg(BaseConst.ITEMSNOTNULL);
                } else {
                    String processDefinitionId = workflowService.startProcess(category);
                    if (!processDefinitionId.equals("0")) {
                        category.setStatus(BaseConst.INTHEREVIEW);//审核中
                        category.setDeploymentId(processDefinitionId);
                        categoryService.updateCategory(category, null, null);
                        Task task = taskService.createTaskQuery().taskCategory(category.getId() + "").list().get(0);
                        if (task != null) {
                            workflowBean.setTaskId(task.getId());
                            workflowService.saveSubmitTask(workflowBean);
                            resultMessage.setCode(BaseConst.SUCCEED);
                            resultMessage.setMsg(BaseConst.TASKSUCCEED);
                            appResultModel.setBody(category.getId());
                            categoryService.updateCategory(category, null, null);
                        }
                    } else {
                        resultMessage.setCode(BaseConst.FAILURE);
                        resultMessage.setMsg(BaseConst.ITEMSFAILURE);
                    }
                }
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.TASKFAILURE);
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }

    /**
     * 查询动态
     *
     * @return
     */
    @RequestMapping(value = "/searchDynamicState", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AppResultModel searchDynamicState() {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        try {
            Map<String, Object> user = getAPPUser(request);
            if (user != null && user.size() > 0) {
                List<Map<String, Object>> categorys = new ArrayList<Map<String, Object>>();
                Map<String, Object> listMap = new HashMap<String, Object>();
                listMap.put("things", deploymentCatrgoryService.selectAll());//事项
                if (user.get("positionName").equals(systemSettings.getPurchase())) {
                    Organization organization = new Organization();
                    organization.setParentId(1l);
                    List<Organization> organizationByOrganization = organizationService.findOrganizationByOrganization(organization);
                    listMap.put("orgs", organizationByOrganization);
                } else {
                    Map<String, Object> map = userService.selectUserInfoByUserId((Long) user.get("userId"));
                    Organization organization = organizationService.selectOne((Long) map.get("orgId"));
                    List<Organization> orgs = new ArrayList<Organization>();
                    orgs.add(organization);
                    listMap.put("orgs", orgs);
                }
                appResultModel.setBody(listMap);
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.FAILURESTATUS);
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }

    @RequestMapping(value = "/searchCategory", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AppResultModel searchCategory(@RequestBody String content) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        try {
            Map<String, Object> user = getAPPUser(request);
            if (user != null && user.size() > 0) {
                List<Map<String, Object>> categorys = new ArrayList<Map<String, Object>>();
                List<String> thingTypes = new ArrayList<String>();
                List<String> departTypes = new ArrayList<String>();
                String statusType = null;
                JSONObject jsonObject = JsonUtils.toJsonObject(JsonUtils.getString(content));
                JSONArray thingTypeObject = (JSONArray) jsonObject.get("thingType");
                JSONArray departTypeObject = (JSONArray) jsonObject.get("departType");
                Object statusTypeObject = jsonObject.get("statusType");
                Map<String, Object> listMap = new HashMap<String, Object>();
                if (thingTypeObject == null || thingTypeObject.size() == 0) {
                    thingTypes.add(BaseConst.PURCHASE);
                } else {
                    thingTypes = JSON.parseArray(String.valueOf(thingTypeObject), String.class);
                }
                if (thingTypes != null && thingTypes.contains(BaseConst.PURCHASE)) {
                    Category category = new Category();
                    if (departTypeObject != null && departTypeObject.size() > 0) {
                        departTypes = JSON.parseArray(String.valueOf(departTypeObject), String.class);
                        category.setOrgIds(departTypes);
                    }
                    if (statusTypeObject != null) {
                        statusType = String.valueOf(statusTypeObject);
                    }
                    category.setPageSize(10000);
                    createPageHelper(category);
                    Object isLeader = user.get("isLeader"); //当前登录人是否是领导
                    if (isLeader != null && statusType == null) {
                        if (((int) isLeader) == 1) {//领导
                            statusType = BaseConst.INTHEREVIEW;
                        } else {
                            statusType = BaseConst.ISTART;
                        }
                    }
                    if (statusType.equals(BaseConst.ISTART)) { //我发起的
                        category.setUserId((Long) user.get("userId"));
                        selectItem(categorys, category);

                    }

                    if (statusType.equals(BaseConst.INTHEREVIEW)) { //待审批
                        category.setAssignee((Long) user.get("userId"));
                        selectItem(categorys, category);
                    }

                    if (statusType.equals(BaseConst.REJECT)) { //驳回
//                        category.setStatus(BaseConst.REJECT);
                        List<String> formIds = workflowService.getHiDetailList(String.valueOf(user.get("userId")), BaseConst.REJECTSTATE);
                        if (formIds != null && formIds.size() > 0) {
                            category.setIds(formIds);
                            selectItem(categorys, category);
                        }
                    }
                    if (statusType.equals(BaseConst.AGREE)) { //同意
//                        category.setStatus(BaseConst.AGREE);
                        List<String> formIds = workflowService.getHiDetailList(String.valueOf(user.get("userId")), BaseConst.AGREESTATE);
                        if (formIds != null && formIds.size() > 0) {
                            category.setIds(formIds);
                            selectItem(categorys, category);
                        }
                    }
                    if (statusType.equals(BaseConst.ENQUIRY)) { //询价
                        category.setStatus(BaseConst.ENQUIRY);
                        selectItem(categorys, category);
                    }
                    if (statusType.equals(BaseConst.ALREADYLOAN)) { //采购
                        category.setStatus(BaseConst.ALREADYLOAN);
                        selectItem(categorys, category);
                    }
                    if (statusType.equals(BaseConst.AWAITLOAN)) { //放款
                        category.setStatus(BaseConst.AWAITLOAN);
                        selectItem(categorys, category);
                    }
                    listMap.put("categorys", categorys);
                    appResultModel.setBody(listMap);
                }
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.ITEMSFAILURE);
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }

    public static List<Long> stringToLong(List<String> stringList) {
        if (stringList == null || stringList.size() < 1) {
            return null;
        }
        List<Long> longList = new ArrayList<Long>();
        for (int i = 0; i < stringList.size(); i++) {
            try {
                longList.add(Long.valueOf(stringList.get(i)));
            } catch (NumberFormatException e) {
                longList.add(0l);
                continue;
            }
        }
        return longList;
    }

    private void selectItem(List<Map<String, Object>> indexList, Category findCategory) {
        PageInfo<Map<String, Object>> pageInfo = categoryService.selectAll(findCategory);
        List<Map<String, Object>> list = pageInfo.getList();
        if (list != null && list.size() > 0) {
            for (Map<String, Object> task : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("title", task.get("title"));
                map.put("status", BaseConst.getCategoryStatus((String) task.get("status")));
                map.put("expectDate", task.get("expect_date"));
                List<CategoryItem> categoryItems = categoryService.selectCategoryItemByCategoryIdNoPage((Long) task.get("id"));
                if (categoryItems != null && categoryItems.size() > 0) {
                    CategoryItem categoryItem = categoryItems.get(0);
                    map.put("name", categoryItem.getItemName());
                    map.put("needcount", categoryItem.getNeedcount());
                    map.put("size", categoryItems.size());
                }
                map.put("id", task.get("id"));
                String status = (String) task.get("status");
                if (Integer.valueOf(status) > 4)
                    map.put("totalPrice", task.get("after_total_price"));
                else
                    map.put("totalPrice", task.get("before_total_price"));
                List<Task> taskList = taskService.createTaskQuery().taskCategory(task.get("id") + "").list();
                if (taskList != null && taskList.size() > 0) {
                    map.put("taskId", taskList.get(0).getId());
                }
                indexList.add(map);
            }
        }
    }

    /**
     * 办理任务
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/approveTask", method = RequestMethod.POST)
    public AppResultModel approveCateGoryTask(@RequestBody String content) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        try {
            Map<String, Object> user = getAPPUser(request);
            if (user != null && user.size() > 0) {
                List<CategoryItem> items = null;
                Category categoryParam = null;
                String categoryContent = null;
                JSONObject jsonObject = JsonUtils.toJsonObject(JsonUtils.getString(content));
                String workflowContent = String.valueOf(jsonObject.get("workflowContent"));
                Object categoryObject = jsonObject.get("categoryContent");
                if (categoryObject != null)
                    categoryContent = String.valueOf(jsonObject.get("categoryContent"));
                if (StringUtils.isNotEmpty(workflowContent)) {
                    if (StringUtils.isNotEmpty(categoryContent)) {
                        categoryParam = JSON.parseObject(categoryContent, Category.class);
                        Object positionName = user.get("positionName");
                        if (positionName != null && positionName.equals(systemSettings.getPurchase())) {
                            categoryParam.setBuyerId((Long) user.get("userId")); //设置采购专员
                        }
                        items = categoryParam.getCategoryItems();
                    }
                    List<WorkflowBean> workflowBeanList = JSON.parseArray(workflowContent, WorkflowBean.class);
                    for (WorkflowBean workflowBean : workflowBeanList) {
                        Category category = categoryService.selectCategoryById(workflowBean.getFormId());
                        if (category == null) {
                            resultMessage.setCode(BaseConst.NOTNULL);
                            resultMessage.setMsg(BaseConst.CATEGORYNOTNULL);
                        } else {
                            if (workflowBean.getOutcome().equals(systemSettings.getAgree()))
                                category.setStatus(BaseConst.AGREE);
                            else if (workflowBean.getOutcome().equals(systemSettings.getReject())) {
                                category.setStatus(BaseConst.REJECT);
                                category.setAssignee(category.getUserId());
                            }
                            if (categoryParam != null) {
                                category.setInventoryStatus(categoryParam.getInventoryStatus());
                                category.setChannels(categoryParam.getChannels());
                                category.setPayment(categoryParam.getPayment());
                                category.setRemarks(categoryParam.getRemarks());
                                category.setAfterTotalPrice(categoryParam.getAfterTotalPrice());
                                category.setBuyerId(categoryParam.getBuyerId());
                            }
                            categoryService.updateCategory(category, items, null);
                            //办理任务
                            ProcessInstance processInstance = workflowService.saveSubmitTask(workflowBean);
                            category = categoryService.selectCategoryById(workflowBean.getFormId());
                            //此时表示流程结束了
                            if (processInstance == null) {
                                //此时需要更新流程表单表的状态state，从1-->2(审核中-->审核完成)
                                category.setStatus(BaseConst.AWAITLOAN);
                                category.setUpdateBy((String) user.get("user_name"));
                                category.setUpdateDate(new Date());
                                List<PositionUserOrganization> puos = userService.selectByPositionName(systemSettings.getMoneyLender());
                                category.setAssignee(puos.get(0).getUserId());
//                                sendMessage(category);
                                categoryService.updateCategory(category, null, null);
                            }
                            resultMessage.setCode(BaseConst.SUCCEED);
                            resultMessage.setMsg(BaseConst.TASKSUCCEED);
                        }
                    }
                } else {
                    resultMessage.setCode(BaseConst.NOTNULL);
                    resultMessage.setMsg(BaseConst.TASKNOTNULL);
                }
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.TASKFAILURE);
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }

    /**
     * 改变状态(流程图走完后调此接口)
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping("sendMessage")
    public AppResultModel sendMessage(@RequestBody String content) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        try {
            Map<String, Object> user = getAPPUser(request);
            if (user != null && user.size() > 0) {
                Category category = JsonUtils.jsonToBean(JsonUtils.getString(content), Category.class);
                if (category != null && category.getId() != null) {
                    category = categoryService.selectOne(category.getId());
                    if (category.getStatus().equals(BaseConst.AWAITLOAN)) { //待放款
                        category.setStatus(BaseConst.ALREADYLOAN); //已放款
                        List<PositionUserOrganization> puos = userService.selectByPositionName(systemSettings.getPurchase());
                        category.setAssignee(puos.get(0).getUserId());
                    } else if (category.getStatus().equals(BaseConst.ALREADYLOAN)) { //已放款
                        category.setStatus(BaseConst.PURCHASESUCCEED); //采购完成
                        category.setAssignee(category.getUserId());
                    }
                    categoryService.updateCategory(category, null, null);
                    resultMessage.setMsg(BaseConst.SENDMESSAGESUCCEED);
                }
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.SENDMESSAGEFAILURE);
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }
}
