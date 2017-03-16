package com.dongao.oa.form;
import javax.persistence.Column;
import javax.persistence.Transient;
/**
 * 办理任务表单
 * yangjie
 */
public class WorkflowBean {
	@Transient
	private Long formId;//流程表单ID
	@Transient
	private String taskId;	//任务ID
	@Transient
	private String outcome;//审批结果
	@Transient
	private String comment;//备注
	@Transient
	private Integer money;//总金额

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
}
