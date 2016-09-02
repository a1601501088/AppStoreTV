package com.basicframe.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Spring 工具类</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Component
public class SpringUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	/**
	 * 根据提供的bean名称得到相应的服务类 
	 * @param name bean名称
	 * @return 根据名字返回相应对象
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 根据提供的bean名称得到对应于指定类型的服务类
	 * @param name  bean名称
	 * @param requiredType 返回的bean类型,若类型不匹配,将抛出异常
	 * @return 返回相应对象
	 * @throws BeansException
	 */
	public static Object getBean(String name, Class<?> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 查找Bean
	 * @param name bean名称
	 * @return 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true 
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype
	 * @param name bean名称
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}
	
	/**
	 * 根据名字获取Bean的类型
	 * @param name bean名称
	 * @return 根据名字返回相应对象类型
	 * @throws BeansException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 根据名字获取Bean的别名
	 * @param name bean名称
	 * @return 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * @throws BeansException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
	
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
