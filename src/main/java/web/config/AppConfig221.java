
package web.config;



import Model.User;
//import hiber.model.Car;
//import hiber.model.User;
//import com.zaxxer.hikari.HikariDataSource;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = {"Model","web.controller"})                     //   (value = "hiber")
public class AppConfig221 {

    @Autowired
    private Environment env;

//содержит логику подключения к БД

//    @Bean
//    public LocalContainerEntityManagerFactoryBean getEntityManager() {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(getDataSource());
//        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
//        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
//        return entityManagerFactoryBean;
//
//
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());





        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));

//
//        // Логгирование свойств
//        System.out.println("db.driver: " + env.getProperty("db.driver"));
//        System.out.println("db.url: " + env.getProperty("db.url"));
//        System.out.println("db.initialSize: " + env.getProperty("db.initialSize"));
//        System.out.println("db.timeBetweenEvictionRunsMillis: " + env.getProperty("db.timeBetweenEvictionRunsMillis"));

        // Настройки пула соединений
        dataSource.setInitialSize(Integer.parseInt(env.getRequiredProperty("db.initialSize")));//(env.getProperty("db.initialSize")));
        dataSource.setMinIdle(Integer.parseInt(env.getRequiredProperty("db.minIdle")));
        dataSource.setMaxIdle(Integer.parseInt(env.getRequiredProperty("db.maxIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getRequiredProperty("db.testOnBorrow")));
        dataSource.setValidationQuery(env.getRequiredProperty("db.validationQuery"));

        return dataSource;
    }



    @Bean
    public PlatformTransactionManager getTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(getEntityManager().getObject());

        return transactionManager;
    }

    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        try (InputStream is = AppConfig221.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            properties.put("hibernate.hbm2ddl.auto", "update"); // или другой режим
            properties.put("hibernate.show_sql", "true");//возможно надо будет удалить
            properties.load(is);
            return properties;

        } catch (IOException e) {
            System.out.println("не удалось подключиться");
            throw new RuntimeException(e);
        }



    }
}

//
//# MySQL properties
//db.driver=com.mysql.cj.jdbc.Driver
//db.url=jdbc:mysql://localhost:3306/schemaex100
//db.username=root
//db.password=3937Tyvm
//db.entity.package = Model.User
//
//db.initialSize = 30
//db.minIdle = 30
//db.maxIdle = 60
//db.timeBetweenEvictionRunsMillis = 30000
//db.minEvictableIdleTimeMillis = 60000
//db.testOnBorrow = true
//db.validationQuery = select version()



//# Hibernate properties
//hibernate.dialect = org.hibernate.dialect.PostgreSQL9Dialect
//
//hibernate.show_sql=true
//hibernate.hbm2ddl.auto=create

//package web.config;
//import Model.User;
////import hiber.model.Car;
////import hiber.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//
//@Configuration
//@PropertySource("classpath:db.properties")
//@EnableTransactionManagement
//@ComponentScan//(value = "hiber")
//public class AppConfig221 {
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("db.driver"));
//        dataSource.setUrl(env.getProperty("db.url"));
//        dataSource.setUsername(env.getProperty("db.username"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public LocalSessionFactoryBean getSessionFactory() {
//        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//        factoryBean.setDataSource(getDataSource());
//
//        Properties props=new Properties();
//        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//
//        factoryBean.setHibernateProperties(props);
//        factoryBean.setAnnotatedClasses(User.class);
//        return factoryBean;
//    }
//
//    @Bean
//    public HibernateTransactionManager getTransactionManager() {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(getSessionFactory().getObject());
//        return transactionManager;
//    }
//}