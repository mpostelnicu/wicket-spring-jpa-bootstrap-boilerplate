package org.sample.web.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author mpostelnicu
 * 
 */
@Order(value=1)
public class WebAppInitializer implements WebApplicationInitializer {
  private static final Logger log = LoggerFactory.getLogger(WebAppInitializer.class);

  @Override
  public void onStartup(ServletContext sc) throws ServletException {

    log.debug("web starting up...");

    // Create the 'root' Spring application context
    AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
    ctx.register(SpringConfiguration.class);
    ctx.refresh();
    
    // Register the Spring Context in the ServletContext
    sc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ctx);
    
    // Request Listener
    sc.addListener(new RequestContextListener());
    
    // Manages the lifecycle
    sc.addListener(new ContextCleanupListener());
    
    sc.addListener(new HttpSessionEventPublisher());
   

    log.info("web initialized.");
  }
}