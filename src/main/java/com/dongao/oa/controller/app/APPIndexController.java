package com.dongao.oa.controller.app;

import com.dongao.oa.config.SystemSettings;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.*;
import com.dongao.oa.service.CategoryService;
import com.dongao.oa.service.MenuService;
import com.dongao.oa.service.WorkflowService;
import com.dongao.oa.utils.BaseConst;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.resultType.AppResultModel;
import com.dongao.oa.utils.resultType.ResultMessage;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app首页控制类
 *
 * @author yangjie
 */
@Controller
@RequestMapping("app")
public class APPIndexController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private SystemSettings systemSettings;

    @ResponseBody
    @RequestMapping(value = "index", method = {RequestMethod.GET, RequestMethod.POST})
    public AppResultModel index() {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        String msg = "";
        Map<String, Object> listMap = null;
        try {
            Map<String, Object> user = getAPPUser(request);
            List<Map<String, Object>> indexList = new ArrayList<Map<String, Object>>();
            listMap = new HashMap<String, Object>();
            if (user != null && user.size() > 0) {
                Map<String, Object> findMap = new HashMap<String, Object>();
                findMap.put("userId", (Long) user.get("userId"));
                findMap.put("type", 1);
                getMenu(listMap, findMap, user);
                Object isLeader = user.get("isLeader"); //当前登录人是否是领导
                Category findCategory = new Category();
                findCategory.setPageSize(5);
                if (isLeader != null) {
                    Map<String, Object> itemMenu = new HashMap<String, Object>();
                    if (((int) isLeader) == 1) {//领导
                        itemMenu.put("itemCode", BaseConst.INTHEREVIEW);
                        itemMenu.put("itemName", systemSettings.getLeaderItemName());
                        findCategory.setAssignee((Long) user.get("userId"));
                        createPageHelper(findCategory);
                        selectItem(indexList, findCategory);
                    } else {
                        findCategory.setUserId((Long) user.get("userId"));
                        createPageHelper(findCategory);
                        selectItem(indexList, findCategory);
                        itemMenu.put("itemCode", BaseConst.ISTART);
                        itemMenu.put("itemName", systemSettings.getNoleaderItemName());
                    }
                    listMap.put("itemMenu", itemMenu);
                }
                listMap.put("category", indexList);
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                msg = BaseConst.USERNOTLOGIN;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultMessage.setMsg(msg);
        appResultModel.setResult(resultMessage);
        appResultModel.setBody(listMap);
        return appResultModel;
    }

    private void getMenu(Map<String, Object> listMap, Map<String, Object> findMap, Map<String, Object> user) {
        List<Menu> menuList = new ArrayList<Menu>();
        List<Menu> menus = menuService.findMenusByUserId(findMap);
        Category category = new Category();
        category.setAssignee((Long) user.get("userId"));
        for (Menu menu : menus) {
            if (menu.getName().equals(systemSettings.getAwaitloanMenu())) {
                category.setStatus(BaseConst.AWAITLOAN);
                createPageHelper(category);
                PageInfo<Map<String, Object>> pageInfo = categoryService.selectAll(category);
                List<Map<String, Object>> taskList = pageInfo.getList();
                if (taskList != null && taskList.size() > 0){
                   for (Map<String,Object> task:taskList){
                       if (task.get("status").equals(BaseConst.AWAITLOAN))
                           menu.setMessageSize(taskList.size());
                   }
                }
                menu.setStatusType(BaseConst.AWAITLOAN);
            }
            if (menu.getName().equals(systemSettings.getPurchaseMenu())) {
                category.setStatus(BaseConst.ALREADYLOAN);
                createPageHelper(category);
                PageInfo<Map<String, Object>> pageInfo = categoryService.selectAll(category);
                List<Map<String, Object>> taskList = pageInfo.getList();
                if (taskList != null && taskList.size() > 0){
                    for (Map<String,Object> task:taskList){
                        if (task.get("status").equals(BaseConst.ALREADYLOAN))
                            menu.setMessageSize(taskList.size());
                    }
                }
                menu.setStatusType(BaseConst.ALREADYLOAN);
            }
            if (menu.getName().equals(systemSettings.getEnquiryMenu())) {
                category.setStatus(BaseConst.ENQUIRY);
                createPageHelper(category);
                PageInfo<Map<String, Object>> pageInfo = categoryService.selectAll(category);
                List<Map<String, Object>> taskList = pageInfo.getList();
                if (taskList != null && taskList.size() > 0){
                    for (Map<String,Object> task:taskList){
                        if (task.get("status").equals(BaseConst.ENQUIRY))
                            menu.setMessageSize(taskList.size());
                    }
                }
                menu.setStatusType(BaseConst.ENQUIRY);
            }
            if (menu.getName().equals(systemSettings.getInthereviewMenu())) {
                createPageHelper(category);
                PageInfo<Map<String, Object>> pageInfo = categoryService.selectAll(category);
                List<Map<String, Object>> taskList = pageInfo.getList();
                if (taskList != null && taskList.size() > 0){
                    for (Map<String,Object> task:taskList){
                        if (task.get("status").equals(BaseConst.ENQUIRY))
                            menu.setMessageSize(taskList.size());
                    }
                }
                if (taskList != null && taskList.size() > 0)
                    menu.setMessageSize(taskList.size());
                menu.setStatusType(BaseConst.INTHEREVIEW);
            }
            if (menu.getName().equals(systemSettings.getRejectMenu())) {
                menu.setStatusType(BaseConst.REJECT);
            }
            if (menu.getName().equals(systemSettings.getAgreeMenu())) {
                menu.setStatusType(BaseConst.AGREE);
            }
            if (menu.getName().equals(systemSettings.getIstartMenu())) {
                menu.setStatusType(BaseConst.ISTART);
            }
            menuList.add(menu);
        }
        listMap.put("menus", menuList);
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
                map.put("beforeTotalPrice", task.get("before_total_price"));
                map.put("id", task.get("id"));
                indexList.add(map);
            }
        }
    }
}
