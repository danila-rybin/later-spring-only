package ru.practicum;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.practicum.config.AppConfig;

public class LaterApplication {

    private static final Logger log = LoggerFactory.getLogger(LaterApplication.class);

    public static void main(String[] args) throws LifecycleException {

        log.info("Запуск приложения Later...");

        Tomcat tomcat = new Tomcat();
        tomcat.getConnector().setPort(8080);

        Context tomcatContext = tomcat.addContext("", null);

        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();

        // ➜ Добавляем конфигурацию, которая грузит application.properties
        applicationContext.register(AppConfig.class);

        // ➜ Сканируем компоненты
        applicationContext.scan("ru.practicum");

        applicationContext.setServletContext(tomcatContext.getServletContext());
        applicationContext.refresh();

        log.info("Spring контекст успешно загружен");

        // добавляем диспетчер запросов
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        Wrapper dispatcherWrapper =
                Tomcat.addServlet(tomcatContext, "dispatcher", dispatcherServlet);
        dispatcherWrapper.addMapping("/");
        dispatcherWrapper.setLoadOnStartup(1);

        log.info("Tomcat запускается на порту 8080");

        tomcat.start();
    }
}
