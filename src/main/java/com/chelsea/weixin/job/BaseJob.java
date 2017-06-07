package com.chelsea.weixin.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chelsea.weixin.domain.SchedualJob;

/**
 * job基础类
 * 
 * @author baojun
 *
 */
public abstract class BaseJob implements Job{
	
	private static Logger LOGGER = LoggerFactory.getLogger(BaseJob.class);
	
	@Autowired
	private JobManage jobManage; 
	
	@Override
	public void execute(JobExecutionContext context) {
		try{
			SchedualJob v_schedualJob = (SchedualJob)context.getJobDetail().getJobDataMap().get("schedualJob");
			String jobName = v_schedualJob.getJobName();
			String jobGroup = v_schedualJob.getJobGroup();
			// 执行job前的校验，包括版本和并发控制
			SchedualJob schedualJob = jobManage.jobValidate(jobName, jobGroup);
			if(schedualJob == null){
				return;
			}
			// 执行对应的job子类业务
			this.process();
			// 重新设置cron
			jobManage.addOrUpdateJob(schedualJob);
		}catch(Exception e){
			LOGGER.error("job定时任务执行失败，失败原因 :" + e.getMessage(), e);
		}
	}
	
	public abstract void process();
}
