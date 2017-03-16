package com.dongao.oa.service.impl;

import com.dongao.oa.pojo.Category;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.CategoryWorkflowService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.UserUtils;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryWorkflowServiceImpl implements CategoryWorkflowService {


    @Autowired
    public RepositoryService repositoryService;
    @Autowired
    public RuntimeService runtimeService;
    @Autowired
    public TaskService taskService;
    @Autowired
    public FormService formService;
    @Autowired
    public HistoryService historyService;
    @Autowired
    public UserService userService;

    @Override
    public void saveStartProcess(Long id) {
        //查询表单内容
        Category category = new Category();//需要改为根据id查询
        if (category != null) {
            //审批状态 0 未审核 1 审核中  2已转入下一流程 3审核结束
            category.setStatus("1");
            //改变状态为审核中
            User user = UserUtils.getCurrentUser();
            //定义流程变量
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("inputUser", user.getId());//当前用户就是下一个任务的办理人
            String classType = category.getClass().getSimpleName();
            String objId = classType + "." + id;
            variables.put("objId", objId);
            runtimeService.startProcessInstanceByKey(classType, objId, variables);
        }
    }


    @Override
    public List<Comment> getCommentListById(Long id) {
        //存放返回的批注信息
        List<Comment> commenList = new ArrayList<Comment>();
        Category category = new Category();//需要改为根据id查询
        String variablesValue = category.getClass().getSimpleName() + "." + id;
        String variablesName = "objId";
        //查询历史的流程变量，并使用名/值对进行匹配
        HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//
                .variableValueEquals(variablesName, variablesValue)//
                .singleResult();
        //获取流程实例ID
        String processInstanceId = hvi.getProcessInstanceId();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()//创建历史的活动实例查询
                .processInstanceId(processInstanceId)//使用流程实例的ID查询
                .activityType("userTask")//表示任务节点
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricActivityInstance hai : list) {
                //获取当前流程对应的任务ID，（每个任务上，对应一个批注信息）
                String hisTaskId = hai.getTaskId();
                //获取当前任务节点的批注信息
                List<Comment> cList = taskService.getTaskComments(hisTaskId);
                if (cList != null && cList.size() > 0) {
                    for (Comment comment : cList) {
                        commenList.add(comment);
                    }
                }
            }
        }
        return commenList;
    }
}
