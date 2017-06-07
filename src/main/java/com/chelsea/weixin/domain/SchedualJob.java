package com.chelsea.weixin.domain;

import java.io.Serializable;

/**
 * 定时任务版本实体类
 * 
 * @author baojun
 *
 */
public class SchedualJob implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String jobName;
	private String jobClass;
	private String jobGroup;
	private Long version;
	private Long updateTime;
	private String cronExpression;
	private Integer status;
	
	public SchedualJob() {
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	
}
