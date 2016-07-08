/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: AppConfig.java
 */
package com.ilts.anywhere;

import com.ilts.anywhere.authentication.SessionDAO;
import com.ilts.anywhere.authentication.SessionDAOImpl;
import com.ilts.anywhere.authentication.UserDAO;
import com.ilts.anywhere.authentication.UserDAOImpl;
import com.ilts.anywhere.betting.dao.BetDao;
import com.ilts.anywhere.betting.dao.BetDaoImpl;
import com.ilts.anywhere.games.dao.GameDao;
import com.ilts.anywhere.games.dao.GameDaoImpl;
import com.ilts.anywhere.logging.ConfigMgr;
import com.ilts.anywhere.logging.utils.LogUtils;
import com.ilts.anywhere.settings.SettingsDAO;
import com.ilts.anywhere.settings.SettingsDAOImpl;
import com.ilts.anywhere.tickets.dao.TicketDao;
import com.ilts.anywhere.tickets.dao.TicketDaoImpl;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class AppConfig {
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/lottery");
        dataSource.setUsername("root");
        dataSource.setPassword("0000");

        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) { 
        System.out.println("+++++ Hibernate Session begins ____+++++");
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.scanPackages("com.ilts.anywhere");
        sessionBuilder.setProperty("hibernate.show_sql", "true");
        sessionBuilder.setProperty("hibernate.type", "TRACE");
        System.out.println("++++++ Hibernate Session Ends  ____+++++");

        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name="transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
	HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

	return transactionManager;
    }

    @Bean(name = "ticketDao")
    public TicketDao getTicketDao() {
	return new TicketDaoImpl();
    }

    @Bean(name = "betDao")
    public BetDao getBetDao() {
	return new BetDaoImpl();
    }

    @Bean(name = "gameDao")
    public GameDao getGameDao() {
	return new GameDaoImpl();
    }

    @Bean(name = "settingsDao")
    public SettingsDAO getSettingsDAO() {
	return new SettingsDAOImpl();
    }

    @Bean(name = "userDAO")
    public UserDAO getUserDAO() {
	return new UserDAOImpl();
    }

    @Bean(name = "sessionDAO")
    public SessionDAO getSessionDAO() {
	return new SessionDAOImpl();
    }

    @Bean(name = "configMgr")
    public ConfigMgr getConfigMgr() {
        Properties properties = null;
        ConfigMgr configMgr = null;

        try {
            // Setup logging
            properties = new Properties();
            properties.load(new FileReader(new File("lottery-app.properties")));

            configMgr = new ConfigMgr(properties);
            LogUtils.setupConfigurationMgr(configMgr);
        } catch (Exception ex) {
            System.out.println("***** ERROR SETTING UP ConfigMgr for Logging *****");
        }

	return configMgr;
    }
}
