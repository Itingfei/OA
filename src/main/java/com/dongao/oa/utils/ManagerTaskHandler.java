package com.dongao.oa.utils;

import com.dongao.oa.config.SystemSettings;
import com.dongao.oa.pojo.*;
import com.dongao.oa.service.*;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fengjifei on 2016/8/24.
 */

@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener {
    @Autowired
    private UserService userService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ProcessPersonnelService processPersonnelService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SystemSettings systemSettings;
//    private ProcessPersonnelService processPersonnelService;

    /**
     * 通过用户表的审核条件，直接查找下一个任务的办理人
     */
    public ManagerTaskHandler() {
        userService = (UserService) SpringUtils.getBean("userServiceImpl");
        positionService = (PositionService) SpringUtils.getBean("positionServiceImpl");
        organizationService = (OrganizationService) SpringUtils.getBean("organizationServiceImpl");
        processPersonnelService = (ProcessPersonnelService) SpringUtils.getBean("processPersonnelServiceImpl");
        messageService = (MessageService) SpringUtils.getBean("messageServiceImpl");
        categoryService = (CategoryService) SpringUtils.getBean("categoryServiceImpl");
        systemSettings = (SystemSettings) SpringUtils.getBean("systemSettings");
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        //获取当前员工的id
        String ProcessId = delegateTask.getProcessDefinitionId().split(":")[0];
        Map currentUser = UserUtils.getUserInfo();
        if (currentUser == null || currentUser.size() == 0) {//APP登录
            currentUser = UserUtils.getAPPCurrentUser();
        }
        ProcessPersonnel processPersonnel = processPersonnelService.selectPPByTaskNameAndProcessKey(delegateTask.getName(), ProcessId);
        if (processPersonnel != null) {
            String branchVariable = processPersonnel.getBranchVariable();
            if ("1".equals(branchVariable)) {
//                查询当前用户直属领导
                setAssignee((Long) currentUser.get("userId"), delegateTask);
            } else {
//                按照职位和部门找到审批人
                setAssignee(processPersonnel.getOrgId(), processPersonnel.getPositionId(), delegateTask);
            }
        }
    }

    /**
     * 根据直属领导查询审批人
     *
     * @param id
     * @param delegateTask
     */
    private void setAssignee(Long id, DelegateTask delegateTask) {
        PositionUserOrganization positionUserOrganization = userService.selectLeaderId(id);
        Long leaderid = positionUserOrganization.getLeaderId();
        Map<String, Object> map = userService.selectUserInfoByUserId(id);
        if (leaderid == null && ((int) map.get("isLeader") == 1))
            delegateTask.setAssignee(userService.selectByPositionName(systemSettings.getPurchase()).get(0).getId() + "");   //指定下一个任务的办理人（个人任务）
        else
            delegateTask.setAssignee(leaderid + "");   //指定下一个任务的办理人（个人任务）
        Category category = categoryService.selectOne(Long.valueOf(delegateTask.getCategory()));
        category.setAssignee(leaderid);
        sendMessage(id, leaderid, category);
        categoryService.updateCategory(category, null, null);
    }

    private void sendMessage(Long id, Long leaderid, Category category) {
        Message message = new Message();
        message.setType(0);
        message.setTitle(systemSettings.getFlowBeginTitle());
        message.setContent(systemSettings.getFlowBeginContent());
        message.setSender(id);
        message.setRecipient(leaderid);
        message.setCategoryId(category.getId());
        message.setSendTime(new Date());
        message.setCreateDate(new Date());
        message.setCreateBy(userService.selectOne(id).getUserName());
        messageService.createMessage(message);
    }

    /**
     * 根据部门和职位查询审批人
     *
     * @param orgId
     * @param positionId
     * @param delegateTask
     */
    private void setAssignee(Long orgId, Long positionId, DelegateTask delegateTask) {
//    Organization organization = new Organization();
//    organization.setOr(orgId);
//    List<Organization> organizationByOrganization = organizationService.findOrganizationByOrganization(organization);
        Position position = positionService.selectOne(positionId);
        List<PositionUserOrganization> list = positionService.selectUserByOrgIdAndPostionName(orgId, position.getName());
        if (list != null && list.size() > 0) {
            delegateTask.setAssignee("" + list.get(0).getUserId());
            Map<String, Object> map = userService.selectUserInfoByUserId(list.get(0).getUserId());
            Category category = categoryService.selectOne(Long.valueOf(delegateTask.getCategory()));
            if (map.get("positionName").equals(systemSettings.getPurchase()))
                category.setStatus(BaseConst.ENQUIRY);
            category.setAssignee(list.get(0).getUserId());
            Map currentUser = UserUtils.getUserInfo();
            if (currentUser == null || currentUser.size() == 0) {//APP登录
                currentUser = UserUtils.getAPPCurrentUser();
            }
            categoryService.updateCategory(category, null, null);
            sendMessage((Long) currentUser.get("userId"), list.get(0).getUserId(), category);
        }
    }
}
