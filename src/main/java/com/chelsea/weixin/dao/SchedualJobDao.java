package com.chelsea.weixin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chelsea.weixin.domain.SchedualJob;


/**
 * 任务版本控制dao
 * 
 * @author baojun
 *
 */
public interface SchedualJobDao {

	/**
	 * @Description:根据任务名称查找最新版本号
	 * @param jobClass
	 * @return
	 * @return Long
	 * @author:baojun
	 * @date:2017年3月11日 下午12:56:50
	 */
	SchedualJob queryByJobNameAndGroup(@Param("jobName")String jobName, @Param("jobGroup")String jobGroup);
	
	/**
	 * @Description:根据任务名称更新最新版本号
	 * @param jobClass
	 * @param version
	 * @return void
	 * @author:baojun
	 * @date:2017年3月11日 下午12:57:18
	 */
	Integer updateVersionByJobNameAndGroup(@Param("jobName")String jobName, @Param("jobGroup")String jobGroup,@Param("version") Long version,@Param("updateTime")Long updateTime);
	
	/**
	 * @Description:查询任务版本列表
	 * @return
	 * @return List<SchedualJob>
	 * @author:baojun
	 * @date:2017年3月15日 下午9:49:00
	 */
	public List<SchedualJob> querySchedualJob();
}
