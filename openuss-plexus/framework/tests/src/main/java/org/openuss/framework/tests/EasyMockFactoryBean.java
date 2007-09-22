package org.openuss.framework.tests;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.util.Assert;

public class EasyMockFactoryBean extends AbstractFactoryBean implements BeanFactoryAware, BeanNameAware {
	
	private static final String CONTROL_NAME_SUFFIX = "Control";
	
	private ConfigurableBeanFactory beanFactory;
	private String beanName;
	private String controlName;
	private Class<?> targetClass;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object createInstance() throws Exception {
		Assert.notNull(targetClass, "Required property 'targetClass' is unset.");
		
		IMocksControl control = EasyMock.createControl();
		Object mock = control.createMock(targetClass);
		registerControl(this.beanFactory,this.controlName != null ? this.controlName : this.beanName + CONTROL_NAME_SUFFIX, control);
		return mock;
	}
	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		Assert.isInstanceOf(ConfigurableBeanFactory.class, beanFactory,"beanFactory must be an instance of ConfigurableBeanFactory" );
		this.beanFactory = (ConfigurableBeanFactory) beanFactory;
	}
	
	public Class<?> getObjectType() {
		return targetClass;
	}
	
	public void setBeanName(String name) {
		this.beanName = name;
	}
	
	public String getBeanName() {
		return beanName;
	}
	
	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
	
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	protected void registerControl(ConfigurableBeanFactory factory, String name, IMocksControl control) {
		factory.registerSingleton(name, control);		
	}
	
}
