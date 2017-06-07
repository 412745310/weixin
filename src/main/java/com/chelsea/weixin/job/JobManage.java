package com.chelsea.weixin.job;

import java.util.Date;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chelsea.weixin.domain.SchedualJob;
import com.chelsea.weixin.service.SchedualJobService;
import com.chelsea.weixin.util.DateUtil;

/**
 * 定时任务管理类
 * 
 * @author baojun
 *
 */

public class JobManage {

	private static Logger LOGGER = LoggerFactory.getLogger(JobManage.class);

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SchedualJobService schedualJobService;

	public void loadJobInit() {
		try {
			LOGGER.info("job定时任务初始化开始-------");
			List<SchedualJob> schedualJobList = schedualJobService
					.querySchedualJob();
			int size = schedualJobList.size();
			for (int i = 0; i < size; i++) {
				SchedualJob schedualJob = schedualJobList.get(i);
				this.addOrUpdateJob(schedualJob);
			}
			this.startJobs();
			LOGGER.info("job定时任务初始化结束-------");
		} catch (Exception e) {
			LOGGER.error("job定时任务初始化失败，失败原因 :" + e.getMessage(), e);
		}
	}

	/**
	 * @Description:添加一个定时任务
	 * @param job
	 * @param classz
	 * @throws SchedulerException
	 * @return void
	 * @author:baojun
	 * @throws ClassNotFoundException
	 * @date:2017年3月15日 上午10:02:57
	 */
	@SuppressWarnings("unchecked")
	public void addOrUpdateJob(SchedualJob job) throws SchedulerException,
			ClassNotFoundException {
		// 这里获取任务信息数据
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),
				job.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (trigger == null) {
			// 不存在，创建一个
			JobDetail jobDetail = JobBuilder
					.newJob((Class<? extends Job>) Class.forName(job
							.getJobClass()))
					.withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("schedualJob", job);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			String cronExpression = trigger.getCronExpression();
			if (job.getCronExpression() != null
					&& !job.getCronExpression().equals(cronExpression)) {
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
						.cronSchedule(job.getCronExpression());
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
						.withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		}
	}

	/**
	 * @Description:执行job前的校验，包括版本和并发控制
	 * @return void
	 * @author:baojun
	 * @date:2017年3月15日 下午9:58:35
	 */
	public SchedualJob jobValidate(String jobName, String jobGroup) {
		SchedualJob schedualJob = schedualJobService.queryByJobNameAndGroup(
				jobName, jobGroup);
		if (schedualJob == null) {
			return null;
		}
		Long lastUpdateTime = schedualJob.getUpdateTime();
		Date lastUpdateDate = DateUtil.getDateByLong(lastUpdateTime);
		String cronExpression = schedualJob.getCronExpression();
		Date nextExecuteDate = DateUtil.getNextDateByCronExpression(
				lastUpdateDate, cronExpression);
		if (new Date().before(nextExecuteDate)) {
			return null;
		}
		Long updateTime = Long.valueOf(DateUtil.getCurrentTime());
		Long version = schedualJob.getVersion();
		Integer count = schedualJobService.updateVersionByJobNameAndGroup(
				jobName, jobGroup, version, updateTime);
		if (count == 0) {
			return null;
		}
		return schedualJob;
	}

	/**
	 * 启动所有定时任务
	 * 
	 * @throws SchedulerException
	 */
	public void startJobs() throws SchedulerException {
		scheduler.start();
	}

}
