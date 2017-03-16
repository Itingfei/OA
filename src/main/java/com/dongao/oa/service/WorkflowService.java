package com.dongao.oa.service;

import com.dongao.oa.form.WorkflowBean;
import com.dongao.oa.pojo.User;
import com.dongao.oa.utils.PageInfo;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


public interface WorkflowService {

	List<Deployment> findDeploymentList();

	List<ProcessDefinition> findProcessDefinitionList();

	void saveDeployment(MultipartFile file);

	void deleteDeploymentById(String deploymentId);

	InputStream findInputStreamWithImage(String deploymentId, String imageNam);

	void saveStartProcess(Long id);

	PageInfo<Task> findPersonalTaskById(User user);

	String getFormTaskData(String taskId);

	String getBussinessKeyWithId(String taskId);

	List<String> findOutComeListByTaskId(String taskId);

	List<Comment> getCommentList(String taskId);

	ProcessDefinition findProcessDefinitionByTaskID(String taskId);

	Map<String, Object> findCoodingListByTaskID(String taskId);

	List<Comment> getCommentListByObject(Object object);
	List<String> getHiDetailList(String userId,String outcome);
	ProcessInstance saveSubmitTask(WorkflowBean workflowBean);

	/**
	 * 启动流程
	 * @param object
	 * @return
     */
    String startProcess(Object object);

	/**
	 * 查询所有的任务
	 * @param userId
	 * @return
     */
	List<Task> findPersonalTaskById(Long userId);
}
