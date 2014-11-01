package org.sample.web.app.security;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author mpostelnicu
 * 
 */
@Order(2)
public class SecurityWebApplicationInitializer 
   extends AbstractSecurityWebApplicationInitializer {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	
	
	@Override
	protected EnumSet<DispatcherType> getSecurityDispatcherTypes() {
		// TODO Auto-generated method stub
		 EnumSet<DispatcherType> securityDispatcherTypes = super.getSecurityDispatcherTypes();
		 securityDispatcherTypes.add(DispatcherType.FORWARD);
		 securityDispatcherTypes.add(DispatcherType.ASYNC);
		 securityDispatcherTypes.add(DispatcherType.REQUEST);
		return securityDispatcherTypes;
	}
	
	@Override
	protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
		log.info("afterSpringSecurityFilterChain");
		super.afterSpringSecurityFilterChain(servletContext);
	}
	
}