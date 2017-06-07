package com.chelsea.weixin.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义JobFactory
 * 
 * @author baojun
 *
 */

@Component
public class CustomJobFactory extends AdaptableJobFactory implements ApplicationContextAware {
	
    ApplicationContext applicationContext;  
    
    /**
     * 扩展创建job的方法
     */
    @Override  
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {  
		Object jobInstance = super.createJobInstance(bundle);
		AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
		factory.autowireBeanProperties(jobInstance,AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
		return jobInstance;  
    }  
  
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext)  
            throws BeansException {  
        this.applicationContext = applicationContext;  
    } 

}
