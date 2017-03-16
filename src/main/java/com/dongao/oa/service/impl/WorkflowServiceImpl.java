package com.dongao.oa.service.impl;

import com.dongao.oa.config.SystemSettings;
import com.dongao.oa.dao.CategoryItemExtMapper;
import com.dongao.oa.form.WorkflowBean;
import com.dongao.oa.pojo.Category;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.CategoryService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.service.WorkflowService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.UserUtils;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.zip.ZipInputStream;

@Service
public class WorkflowServiceImpl implements WorkflowService {
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
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public CategoryItemExtMapper categoryItemExtMapper;
    @Autowired
    private SystemSettings systemSettings;
    @Autowired
    private HttpServletRequest request;

    @Override
    public void saveDeployment(MultipartFile file) {
        //1：MultipartFile类型的文件转换成inputstream流，再转换成ZipInputStream流
        try {
            ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
            //2：使用流程引擎部署ZipInputStream流
            Deployment deploy = repositoryService.createDeployment()//
                    .name(file.getOriginalFilename().replace(".zip", ""))//添加名称
                    .addZipInputStream(zipInputStream)//
                    .deploy();//完成部署
            System.out.println(deploy.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询部署对象表，获取部署对象的集合，返回List<Deployment>
     */
    @Override
    public List<Deployment> findDeploymentList() {
        List<Deployment> list = repositoryService.createDeploymentQuery()//
                .orderByDeploymenTime().asc()
                .list();
        return list;
    }

    /**
     * 查询流程定义表，获取流程定义的集合，返回List<ProcessDefinition>
     */
    @Override
    public List<ProcessDefinition> findProcessDefinitionList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//
                .orderByProcessDefinitionVersion().asc()//
                .list();
        return list;
    }

    /**
     * 删除部署信息
     */
    /**
     * 使用部署ID，完成删除流程定义（级联删除）
     */
    @Override
    public void deleteDeploymentById(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }

    @Override
    public InputStream findInputStreamWithImage(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId, imageName);
    }

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

    /**
     * 查询个人任务列表
     *
     * @param user
     * @return
     */
    @Override
    public PageInfo<Task> findPersonalTaskById(User user) {
        List<Task> taskList = new ArrayList<Task>();
        List<Task> list = taskService.createTaskQuery()//
                .taskAssignee(user.getId() + "")//
                .orderByTaskCreateTime().desc()//
                .list();
        for (Task task : list) {
            User user1 = userService.selectOne(Long.valueOf(task.getAssignee()));
            task.setAssignee(user1.getRealName());
            taskList.add(task);
        }
        PageInfo<Task> pageInfo = new PageInfo<Task>();
        pageInfo.setList(taskList);
        return pageInfo;
    }

    @Override
    public ProcessInstance saveSubmitTask(WorkflowBean workflowBean) {
        //获取任务ID
        String taskId = workflowBean.getTaskId();
        //获取流程表单ID
        Long id = workflowBean.getFormId();
        //审核结果
        String outcome = workflowBean.getOutcome();
        //获取批注的信息
        String comment = workflowBean.getComment();
        /**
         * 1：把审核结果放置到流程变量中（对应流程图中${outcome=='批准'}）
         * 此时流程变量的名称：outcome
         * 此时流程变量的值：‘批准’
         */
        Category category = categoryService.selectCategoryById(id);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("outcome", outcome);
        Map<String, Object> mapUserInfo = UserUtils.getUserInfo();
        if (mapUserInfo == null || mapUserInfo.size() == 0) {
            mapUserInfo = UserUtils.getAPPCurrentUser();
        }
        String positionName = (String) mapUserInfo.get("positionName");
        double applicationPrice = categoryItemExtMapper.findApplicationPrice(id);
        variables.put("money", applicationPrice);
        if ("2".equals(variables.get("flag"))) {
            //标识为2 查询实际价格
            double realPrice = categoryItemExtMapper.findRealPrice(id);
            variables.put("money", realPrice);
        }
        if (positionName != null && positionName.equals(systemSettings.getPurchase())) {
            variables.put("flag", "2");
            //登录用户为采购  标识为2
        }
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//
                .singleResult();
        variables.put("objStatus", task.getAssignee()+":"+outcome+":"+id);
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        /**
         * 添加批准信息（将领导的审核记录关联当前申请）
         */
        //添加批注之前，添加认证信息，使当前任务的办理人，与批注人进行绑定
        Authentication.setAuthenticatedUserId(String.valueOf(mapUserInfo.get("userId")));
        taskService.addComment(taskId, processInstanceId, comment);
        //3：使用流程变量定义的连线名称，完成当前人的个人任务
        taskService.complete(taskId, variables);
        //使用流程实例ID，获取当前的流程实例的对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)//
                .singleResult();
        return pi;
    }

    @Override
    public String startProcess(Object object) {
        String result = "0";
        try {
            //从Session获取当前登录人
            User currentUser = UserUtils.getCurrentUser();
            Map<String, Object> variables = new HashMap<String, Object>();
            if (currentUser == null) {//说是APP登录
                Map<String, Object> user = (Map<String, Object>) request.getSession(true).getAttribute("user");
                variables.put("inputUser", user.get("userId"));//当前用户就是下一个任务的办理人
            } else {
                variables.put("inputUser", currentUser.getId());//当前用户就是下一个任务的办理人
            }
            //3：提交申请的时候，一定要使用流程关联业务，使用流程变量和业务key，存放业务，是的业务关联流程（格式：LeaveBill.id（LeaveBill.1））
            String classType = object.getClass().getSimpleName();
            Object id = object.getClass().getMethod("getId", new Class[]{}).invoke(object, new Object[]{});
            String objId = classType + "." + id;
            variables.put("objId", objId);
            variables.put("category", String.valueOf(id));
            // 4：流程实例要启动，使用流程定义的key
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(classType, objId, variables);
            result = processInstance.getProcessInstanceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Task> findPersonalTaskById(Long userId) {
        List<Task> list = taskService.createTaskQuery()//
                .taskAssignee(userId + "")//
                .orderByTaskCreateTime().desc()//
                .list();
        return list;
    }

    @Override
    public String getFormTaskData(String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        String url = taskFormData.getFormKey();
        return url;
    }

    @Override
    public String getBussinessKeyWithId(String taskId) {
        //使用任务ID，获取任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//使用任务ID查询
                .singleResult();
        //从而获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //再使用流程实例ID，获取流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        //获取业务key(LeaveBill.2)
        String businessKey = pi.getBusinessKey();
        //流程表单ID
        String id = "";
        if (StringUtils.isNotBlank(businessKey)) {
            //截取小数点后面的值
            id = businessKey.split("\\.")[1];
        }
        return id;
    }

    @Override
    public List<String> findOutComeListByTaskId(String taskId) {
        List<String> outcomeList = new ArrayList<String>();
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//
                .singleResult();
        //从而获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //使用流程定义ID，获取ProcessDefinitionEntity
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        //获取流程实例ID，使用流程实例ID，获取当前的流程实例的对象，获取当前活动的节点ID
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)//
                .singleResult();
        //当前活动的节点ID
        String activityId = pi.getActivityId();
        //获取当前活动的对象
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
        List<PvmTransition> list = activityImpl.getOutgoingTransitions();
        if (list != null && list.size() > 0) {
            for (PvmTransition pvmTransition : list) {
                //获取.bpmn中连线的属性名称为name属性的值
                String outcomeName = (String) pvmTransition.getProperty("name");
                //添加当前活动节点完成后的连线名称的集合
                outcomeList.add(outcomeName);
            }
        }
        return outcomeList;
    }

    @Override
    public List<Comment> getCommentList(String taskId) {
        //存放返回的批注信息
        List<Comment> commenList = new ArrayList<Comment>();
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//
                .singleResult();
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
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

    @Override
    public ProcessDefinition findProcessDefinitionByTaskID(String taskId) {
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//
                .singleResult();
        //获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //使用流程定义的Id，查询流程定义的对象
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
                .processDefinitionId(processDefinitionId)//
                .singleResult();
        return pd;
    }

    @Override
    public Map<String, Object> findCoodingListByTaskID(String taskId) {
        //存放坐标的集合
        Map<String, Object> map = new HashMap<String, Object>();
        //使用任务ID，查询任务对象
        Task task = taskService.createTaskQuery()//
                .taskId(taskId)//
                .singleResult();
        //获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        //获取流程定义的实体对象ProcessDefinitionEntity
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        //获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //使用流程实例ID，查询流程实例的对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)//
                .singleResult();
        //获取当前活动的ID
        String activityId = pi.getActivityId();
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
        map.put("x", activityImpl.getX());
        map.put("y", activityImpl.getY());
        map.put("width", activityImpl.getWidth());
        map.put("height", activityImpl.getHeight());
        return map;
    }

    @Override
    public List<Comment> getCommentListByObject(Object object) {
        //存放返回的批注信息
        List<Comment> commenList = new ArrayList<Comment>();
        String classType = object.getClass().getSimpleName();
        try {
            Object id = object.getClass().getMethod("getId", new Class[]{}).invoke(object, new Object[]{});
            //使用流程表单ID，获取流程表单对象
            String variablesValue = classType + "." + id;
            String variablesName = "objId";
            //查询历史的流程变量，并使用名/值对进行匹配
            HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//
                    .variableValueEquals(variablesName, variablesValue)//
                    .singleResult();

            //获取流程实例ID
            if (hvi != null) {
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
//                    for (Comment comment : cList) {
//                        commenList.add(comment);
//                    }
                            commenList.addAll(cList);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return commenList;
    }

    @Override
    public List<String> getHiDetailList(String userId,String outcome) {
        List<String> formIds= new ArrayList<String>();
        List<HistoricDetail> list = historyService.createHistoricDetailQuery().list();
        for (HistoricDetail detail:list){
            if (detail instanceof HistoricDetailVariableInstanceUpdateEntity){
                HistoricDetailVariableInstanceUpdateEntity due = (HistoricDetailVariableInstanceUpdateEntity) detail;
                if (due.getName().equals("objStatus")) {
                    String textValue = due.getTextValue();
                    if (StringUtils.isNotEmpty(textValue) && textValue.contains(userId) && textValue.contains(outcome)) {
                        String[] split = textValue.split(":");
                        formIds.add(split[2]);
                    }
                }
            }
        }
        return formIds;
    }
}
