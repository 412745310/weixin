package com.chelsea.weixin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chelsea.weixin.dao.SchedualJobDao;
import com.chelsea.weixin.domain.SchedualJob;

/**
 * 定时器service
 * 
 * @author baojun
 *
 */
@Service
public class SchedualJobService {

	@Autowired
	private SchedualJobDao schedualJobDao;

	public SchedualJob queryByJobNameAndGroup(String jobName, String jobGroup) {
		return schedualJobDao.queryByJobNameAndGroup(jobName, jobGroup);
	}

	public Integer updateVersionByJobNameAndGroup(String jobName,
			String jobGroup, Long version, Long updateTime) {
		return schedualJobDao.updateVersionByJobNameAndGroup(jobName, jobGroup,
				version, updateTime);
	}

	public List<SchedualJob> querySchedualJob() {
		return schedualJobDao.querySchedualJob();
	}

}
