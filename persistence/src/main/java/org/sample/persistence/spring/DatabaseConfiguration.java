/**
 * 
 */
package org.sample.persistence.spring;

import java.io.PrintWriter;
import java.net.InetAddress;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.derby.jdbc.ClientDriver;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author mpostelnicu
 * 
 */
@Configuration
@Lazy
public class DatabaseConfiguration {

	
	
	@Bean
	 public DataSource dataSource() {
	 DataSource dataSource = new DataSource();
	 dataSource.setDriverClassName(ClientDriver.class.getName());
	 dataSource.setUrl("jdbc:derby://localhost//derby/sample;create=true");
	 dataSource.setUsername("app");
	 dataSource.setPassword("app");
	 return dataSource;
	 }

	
	@Bean(destroyMethod = "shutdown")
	public NetworkServerControl derbyServer() throws Exception {
		NetworkServerControl nsc = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
		nsc.start(new PrintWriter(java.lang.System.out, true));
		return nsc;
	}
	
}
