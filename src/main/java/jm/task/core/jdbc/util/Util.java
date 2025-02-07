package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class Util {
    private static final Properties properties = new Properties();
    private static SessionFactory sessionFactory;

    static {
        loadProperties();
    }

    // Загрузка свойств из application.properties
    private static void loadProperties() {
        try (InputStream input = Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    // Получение соединения с базой данных через JDBC
    public static Connection getConnection() {
        try {
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                log.info("Создание SessionFactory...");
                Configuration configuration = new Configuration();
                // Настройки Hibernate
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, properties.getProperty("hibernate.connection.driver_class"));
                settings.put(Environment.URL, properties.getProperty("hibernate.connection.url"));
                settings.put(Environment.USER, properties.getProperty("hibernate.connection.username"));
                settings.put(Environment.PASS, properties.getProperty("hibernate.connection.password"));
                settings.put(Environment.DIALECT, properties.getProperty("hibernate.dialect"));
                settings.put(Environment.SHOW_SQL, properties.getProperty("hibernate.show_sql"));
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, properties.getProperty("hibernate.current_session_context_class"));
                settings.put(Environment.HBM2DDL_AUTO, properties.getProperty("hibernate.hbm2ddl.auto"));
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class); // Регистрируем сущность User
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                log.info("SessionFactory успешно создан");
            } catch (Exception e) {
                log.error("Ошибка при создании SessionFactory", e);
                throw new RuntimeException("Ошибка при создании SessionFactory", e);
            }
        }
        return sessionFactory;
    }
    // Получение SessionFactory для Hibernate
//    public static SessionFactory getSessionFactory() {
//        log.info("Create SessionFactory");
//        if (sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration();
//
////                // Загрузка свойств
////                properties.load(Util.class.getClassLoader().getResourceAsStream("application.properties"));
////
////                // Настройка свойств Hibernate
////                properties.forEach((key, value) -> configuration.setProperty(key.toString(), value.toString()));
////
////                // Добавление аннотированных классов
////                configuration.addAnnotatedClass(User.class);
////
////                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
////                        .applySettings(configuration.getProperties()).build();
////
////                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException("Error creating SessionFactory", e);
//            }
//        }
//        return sessionFactory;
//    }
    public static void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
    public  static void closeConnection() {
        try {
            if (DriverManager.getConnection(properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.username"),
                    properties.getProperty("jdbc.password")) != null) {
                DriverManager.getConnection(properties.getProperty("jdbc.url"),
                        properties.getProperty("jdbc.username"),
                        properties.getProperty("jdbc.password")).close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closed Connection", e);
        }
    }
    // Получение SQL-запроса из application.properties
    public static String getQuery(String key) {
        return properties.getProperty(key);
    }
}