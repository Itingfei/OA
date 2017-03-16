package com.dongao.oa.service;

import org.activiti.engine.task.Comment;

import java.util.List;


public interface CategoryWorkflowService {
	void saveStartProcess(Long id);
	List<Comment> getCommentListById(Long id);
}
