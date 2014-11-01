package org.sample.web.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author mpostelnicu
 * 
 */
@Configuration
@Lazy
@ComponentScan(basePackages = "org.sample")
public class SpringConfiguration {

}