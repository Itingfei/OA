package com.dongao.oa.controller.purchase;


import com.dongao.oa.config.SystemSettings;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.form.WorkflowBean;
import com.dongao.oa.pojo.*;
import com.dongao.oa.service.*;
import com.dongao.oa.utils.BaseConst;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.UserUtils;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by fengjifei on 2016/8/23.
 * 采购表单controller
 */

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(getClass());
    // 上级目录
    private static final String prefix = "category/";
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SystemSettings systemSettings;
    @Autowired
    private ClassifyService classifyService;
    @ModelAttribute("classifys")
    public List<Map<String,Object>> organizationList(){
        return classifyService.selectAll();
    }
    /**
     * 流程表单列表展示
     * @param model
     * @return
     */
//	@RequiresPermissions("menu:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Category category, Model model) {
        category.setUserId(UserUtils.getCurrentUser().getId());
        createPageHelper(category);
        addAttributesToModel(model,"page,category",categoryService.selectAll(category),category);
        return  prefix + "categoryList";
    }
    /**
     * 添加流程表单页面
     */
    @RequestMapping("categoryEdit")
    public String add(Model model, Long id) {
        if(id != null && id > 0)
            addAttributesToModel(model,"category,categoryItems", categoryService.selectCategoryById(id),categoryService.selectCategoryItemByCategoryIdNoPage(id));
        addAttributesToModel(model,"userList", userService.selectAllUser());
        return  prefix + "categoryEdit";
    }
    @RequestMapping(value = "/{id}/itemDetail", method = RequestMethod.GET)
    public String showDetailForm(@PathVariable("id") Long id,CategoryItem categoryItem,Model model) {
        categoryItem.setCategoryId(id);
        createPageHelper(categoryItem);
        addAttributesToModel(model,"page,categoryItem",categoryService.selectCategoryItemByCategoryId(categoryItem),categoryItem);
        return prefix + "itemDetail";
    }
    /**
     * 保存流程表单 / 更新流程表单
     * @param category
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("saveCategory")
    public Result<?> saveCategory(Category category,HttpServletRequest request) {
        Result<Object> result = new Result<Object>();
         String deleteItemId = request.getParameter("deleteItemId");
        List<CategoryItem> items = getCategoryItems(request);
        String loginName = UserUtils.getCurrentUser().getUserName();
        Date date = new Date();
        if (category.getId() == null || category.getId() == 0) { //保存
            category.setCreateBy(loginName);
            category.setCreateDate(date);
            category.setUpdateBy(loginName);
            category.setUpdateDate(date);
            category.setUserId(UserUtils.getCurrentUser().getId());
            category.setStatus(BaseConst.NOTSTART);
            Map<String, Object> map = userService.selectUserInfoByUserId(UserUtils.getCurrentUser().getId());
            category.setOrgId((Long) map.get("orgId"));
            if (categoryService.saveCategory(category,items)>0){
                result.setMsg("添加流程表单成功!");
            }else{
                result.setCode("1");
                result.setMsg("添加流程表单失败!");
            }
        }else{ //修改
            category.setUpdateBy(loginName);
            category.setUpdateDate(date);
            if (categoryService.updateCategory(category,items,deleteItemId)>0){
                result.setMsg("编辑流程表单成功!");
            }else{
                result.setCode("1");
                result.setMsg("编辑流程表单失败!");
            }
        }
        return result;
    }

    /**
     * 拼装List<CategoryItem>
     * @param request
     * @return
     */
    private List<CategoryItem> getCategoryItems(HttpServletRequest request) {
        String[] itemNames = request.getParameterValues("itemName");
        String[] needcounts = request.getParameterValues("needcount");
        String[] counts = request.getParameterValues("count");
        String[] stocks = request.getParameterValues("stock");
        String[] models = request.getParameterValues("model");
        String[] applyPrices = request.getParameterValues("applyPrice");
        String[] actualPrices = request.getParameterValues("actualPrice");
        String[] itemRemarks = request.getParameterValues("itemRemarks");
        String[] classifyIds = request.getParameterValues("classifyId");
        String[] itemIds = request.getParameterValues("itemId");
        String itemSize = request.getParameter("itemSize");
        List<CategoryItem> items = new ArrayList<CategoryItem>();
        for (int i = 0; i < Integer.valueOf(itemSize); i++) {
            CategoryItem categoryItem = new CategoryItem();
            if (itemIds!=null) {
                String itemId = itemIds[i];
                if (StringUtils.isNotEmpty(itemId))
                    categoryItem.setId(Long.valueOf(itemId));
            }
            if (itemNames!=null)
                categoryItem.setItemName(itemNames[i]);
            if (needcounts!=null) {
                String needcount = needcounts[i];
                if (StringUtils.isNotEmpty(needcount))
                    categoryItem.setNeedcount(Integer.valueOf(needcount));
            }
            if (counts!=null) {
                String count = counts[i];
                if (StringUtils.isNotEmpty(count))
                    categoryItem.setCount(Integer.valueOf(count));
            }
            if (stocks!=null) {
                String stock = stocks[i];
                if (StringUtils.isNotEmpty(stock))
                    categoryItem.setStock(Integer.valueOf(stock));
            }
            if (models!=null)
                categoryItem.setModel(models[i]);
            if (applyPrices!=null) {
                String applyPrice = applyPrices[i];
                if (StringUtils.isNotEmpty(applyPrice))
                    categoryItem.setApplyPrice(new BigDecimal(applyPrice));
            }
            if (actualPrices!=null) {
                String actualPrice = actualPrices[i];
                if (StringUtils.isNotEmpty(actualPrice))
                    categoryItem.setActualPrice(new BigDecimal(actualPrice));
            }
            if (classifyIds!=null) {
                String classifyId = classifyIds[i];
                if (StringUtils.isNotEmpty(classifyId))
                    categoryItem.setClassifyId(Long.valueOf(classifyId));
            }
            if (itemRemarks!=null)
                categoryItem.setItemRemarks(itemRemarks[i]);
            items.add(categoryItem);
        }
        return items;
    }

    /**
     * 删除流程表单
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
    public Result<?> delete(@PathVariable("id") Long id) {
        Result<Object> result = new Result<Object>();
        if (id==null && id<0){
            result.setCode("1");
            result.setMsg("请选择要删除的流程表单!");
        }else {
            Category category = categoryService.selectCategoryById(id);
            if (category==null){
                result.setCode("1");
                result.setMsg("要删除的流程表单不存在!");
            }else{
                int count = categoryService.delete(category);
                if (count > 0) {
                    result.setMsg("删除流程表单成功!");
                } else {
                    result.setCode("1");
                    result.setMsg("删除流程表单失败!");
                }
            }
        }
        return result;
    }
    /**
     * 发起申请
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{id}/startApply",method = RequestMethod.POST)
    public Result<?> startApply(@PathVariable("id") Long id) {
        Result<Object> result = new Result<Object>();
        if (id==null && id<0){
            result.setCode("1");
            result.setMsg("请选择要发起申请的流程表单!");
        }else {
            Category category = categoryService.selectCategoryById(id);
            PositionUserOrganization positionUserOrganization = userService.selectLeaderId(category.getUserId());
            if (category==null){
                result.setCode("1");
                result.setMsg("要发起申请的流程表单不存在!");
            }else if (positionUserOrganization==null || positionUserOrganization.getLeaderId()==null){
                result.setCode("1");
                result.setMsg("您没有直属领导,无法发起申请,请联系管理员!");
            }else{
                //将流程表单对象中state状态，从0变成1（从未审核变成审核中）
                String processDefinitionId = workflowService.startProcess(category);
                if (!processDefinitionId.equals("0")) {
                    category.setStatus(BaseConst.INTHEREVIEW);//审核中
                    category.setUpdateBy(UserUtils.getCurrentUser().getUserName());
                    category.setUpdateDate(new Date());
                    category.setDeploymentId(processDefinitionId);
                    categoryService.updateCategory(category,null,null);
                    result.setMsg("发起申请成功!");
                } else {
                    result.setCode("1");
                    result.setMsg("发起申请失败!");
                }
            }
        }
        return result;
    }
    /**
     * 打开任务表单
     */
    @RequestMapping(value = "/{id}/viewTaskForm",method = RequestMethod.GET)
    public String viewTaskForm(@PathVariable("id") String taskId,Model model){
        //获取每个任务中Form key值：
        String url = workflowService.getFormTaskData(taskId);
        //获取流程表单ID，使用任务ID，获取任务对象，从而获取流程实例ID，再使用流程实例ID，获取流程实例对象，从流程实例对象中获取
        String id = workflowService.getBussinessKeyWithId(taskId);
        //传递任务ID和流程表单ID
        url += "?taskId="+taskId+"&id="+id;
        System.out.println(url);
        model.addAttribute("url",url);
        return "redirect:"+url;
    }

    // 准备表单数据
    @RequestMapping(value = "/audit",method = RequestMethod.GET)
    public String audit(String taskId,Long id,Model model){
        //1:使用流程表单ID，获取流程表单的对象，放置到栈顶，使用struts2的标签，支持表单回显
        if(id!=null){
            Category category = categoryService.selectOne(id);
            User user = userService.selectOne(category.getUserId());
            category.setUserName(user.getRealName());
            model.addAttribute("category",category);
            CategoryItem categoryItem = new CategoryItem();
            categoryItem.setCategoryId(category.getId());
            createPageHelper(categoryItem);
            PageInfo<CategoryItem> categoryItems = categoryService.selectCategoryItemByCategoryId(categoryItem);
            addAttributesToModel(model,"category,categoryItems,taskId",category,categoryItems,taskId);
        }
        //2:使用任务ID，获取任务完成之后连线名称的集合
        //获取任务Id
        List<String> outcomeList = workflowService.findOutComeListByTaskId(taskId);

        //3：查询每个任务完成后的批注信息，返回List<Comment>.获取每个人对当前申请记录的审批信息
        List<Comment> commentList = workflowService.getCommentList(taskId);
        List<Map<String,Object>> comments =  new ArrayList<Map<String,Object>>();
        for (Comment comment:commentList){
            Map<String,Object> map = new HashMap<String,Object>();
            User user = userService.selectOne(Long.valueOf(comment.getUserId()));
            if (user!=null)
                map.put("userName",user.getRealName());
            map.put("time",comment.getTime());
            map.put("fullMessage",comment.getFullMessage());
            comments.add(map);
        }
        addAttributesToModel(model,"outcomeList,commentList",outcomeList,comments);
        return prefix +"taskForm";
    }
    /**
     * 办理任务
     * @param workflowBean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submitCateGoryTask",method = RequestMethod.POST)
    public Result<?> submitCateGoryTask(WorkflowBean workflowBean,HttpServletRequest request) {
        Result<Object> result = new Result<Object>();
        try {
            if (workflowBean==null || StringUtils.isEmpty(workflowBean.getTaskId())){
                result.setCode("1");
                result.setMsg("请选择要办理的任务");
            }else {
                List<CategoryItem> items = getCategoryItems(request);
                Category category = categoryService.selectCategoryById(workflowBean.getFormId());
                if (category==null){
                    result.setCode("1");
                    result.setMsg("要办理的任务没有表单!");
                }else{
                    Object positionName = UserUtils.getUserInfo().get("positionName");
                    if (positionName!=null && positionName.equals(systemSettings.getPurchase())) {
                        String inventoryStatus = request.getParameter("inventoryStatus");
                        String channels = request.getParameter("channels");
                        String payment = request.getParameter("payment");
                        if (StringUtils.isNotEmpty(inventoryStatus) && StringUtils.isNotEmpty(channels) && StringUtils.isNotEmpty(payment)){
                            category.setInventoryStatus(Integer.valueOf(inventoryStatus));
                            category.setChannels(channels);
                            category.setPayment(payment);
                        }
                        category.setBuyerId(UserUtils.getCurrentUser().getId()); //设置采购专员
                    }
                    if (workflowBean.getOutcome().equals(systemSettings.getAgree()))
                        category.setStatus(BaseConst.AGREE);
                    else if (workflowBean.getOutcome().equals(systemSettings.getReject())) {
                        category.setStatus(BaseConst.REJECT);
                        category.setAssignee(category.getUserId());
                    }
                    categoryService.updateCategory(category,items,null);
                    //办理任务
                    ProcessInstance processInstance = workflowService.saveSubmitTask(workflowBean);
                    category = categoryService.selectCategoryById(workflowBean.getFormId());
                    //此时表示流程结束了
                    if(processInstance==null){
                        //此时需要更新流程表单表的状态state，从1-->2(审核中-->审核完成)
                        category.setStatus(BaseConst.AWAITLOAN);
                        category.setUpdateBy(UserUtils.getCurrentUser().getUserName());
                        category.setUpdateDate(new Date());
                        category = sendMessage(category);
                        categoryService.updateCategory(category,null,null);
                    }
                    result.setMsg("办理任务成功!");
                }
                result.setResult(workflowBean.getTaskId());
            }
        } catch (Exception e) {
            result.setCode("1");
            result.setMsg("办理任务失败!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 给出纳员发送消息
     * @param category
     */
    private Category sendMessage(Category category) {
        Message message = new Message();
        message.setTitle(systemSettings.getFlowEndTitle());
        message.setContent(systemSettings.getFlowEndContent());
        message.setType(0);
        message.setSender(UserUtils.getCurrentUser().getId());
        List<PositionUserOrganization> puos = userService.selectByPositionName(systemSettings.getMoneyLender());
        if (puos!=null && puos.size()>0) {
            message.setRecipient(puos.get(0).getUserId());
            category.setAssignee(puos.get(0).getUserId());
        } else {
            System.out.println(systemSettings.getMoneyLender() + "不存在!");
        }
        message.setCategoryId(category.getId());
        String loginName = UserUtils.getCurrentUser().getUserName();
        Date date = new Date();
        message.setCreateBy(loginName);
        message.setCreateDate(date);
        message.setUpdateBy(loginName);
        message.setUpdateDate(date);
        message.setSendTime(date);
        messageService.createMessage(message);
        return category;
    }

    @RequestMapping(value = "/{id}/showRecord",method = RequestMethod.GET)
    public String showRecord(@PathVariable("id") Long id,Model model){
        Category category = categoryService.selectCategoryById(id);
        List<Comment> commentList = workflowService.getCommentListByObject(category);
        List<Map<String,Object>> comments =  new ArrayList<Map<String,Object>>();
        for (Comment comment:commentList){
            Map<String,Object> map = new HashMap<String,Object>();
            User user = userService.selectOne(Long.valueOf(comment.getUserId()));
            if (user!=null)
                map.put("userName",user.getRealName());
            map.put("time",comment.getTime());
            map.put("fullMessage",comment.getFullMessage());
            comments.add(map);
        }
        CategoryItem categoryItem = new CategoryItem();
        categoryItem.setCategoryId(category.getId());
        createPageHelper(categoryItem);
        PageInfo<CategoryItem> categoryItems = categoryService.selectCategoryItemByCategoryId(categoryItem);
        addAttributesToModel(model,"category,commentList,categoryItems",category,comments,categoryItems);
        return prefix +"showRecord";
    }

}
