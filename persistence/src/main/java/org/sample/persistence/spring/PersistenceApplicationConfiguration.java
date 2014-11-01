/**
 * 
 */
package org.sample.persistence.spring;

import java.util.Properties;

import javax.management.MBeanServer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.hibernate.dialect.DerbyTenSevenDialect;
import org.sample.persistence.repository.DummyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author mpostelnicu
 * 
 */
@Configuration
@Lazy
@EnableJpaRepositories(basePackageClasses = DummyRepository.class)
@EnableTransactionManagement
public class PersistenceApplicationConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MBeanServer mbeanServer;

	@Autowired
	private CacheManager cacheManager;
	

	@Bean
	@DependsOn(value = {"derbyServer","ehCacheManagerFactoryBean"})
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource);
		bean.setPackagesToScan(new String[] { "org.sample.persistence.dao" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		bean.setJpaVendorAdapter(vendorAdapter);
		bean.setJpaProperties(additionalProperties());
		return bean;
	}

	Properties additionalProperties() {
		return new Properties() {
			private static final long serialVersionUID = 1L;
			{
				setProperty("hibernate.hbm2ddl.auto", "update");
				setProperty("hibernate.dialect",
						DerbyTenSevenDialect.class.getName());
				setProperty("hibernate.cache.region.factory_class",
						SingletonEhCacheRegionFactory.class.getName());
				setProperty("hibernate.cache.use_second_level_cache", "true");
				setProperty("hibernate.cache.use_query_cache", "true");
				setProperty("hibernate.show_sql", "false");
				setProperty("hibernate.format_sql", "false");
			}
		};
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(
				"ehcache.xml"));
		ehCacheManagerFactoryBean.setShared(true);
		return ehCacheManagerFactoryBean;
	}

    @Bean
    public MBeanServerFactoryBean mbeanServer() {
    	MBeanServerFactoryBean mBeanServerFactoryBean = new MBeanServerFactoryBean();
    	mBeanServerFactoryBean.setLocateExistingServerIfPossible(true);
    	return mBeanServerFactoryBean;
    }
       
	@Bean(destroyMethod="dispose",initMethod="init")
	//@Lazy(value=false) 
	//-- also enable statistics in ehcache.xml
	@DependsOn(value = {"ehCacheManagerFactoryBean","mbeanServer"})
	public ManagementService ehCacheManagementService() {
		ManagementService managementService = new ManagementService(cacheManager, mbeanServer, true, true, true, true);
		return managementService;
	}


	@Bean(destroyMethod = "close")
	public EntityManager entityManager() {
		return entityManagerFactory().getObject().createEntityManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager(
			EntityManagerFactory emf) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(emf);
		return jpaTransactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
