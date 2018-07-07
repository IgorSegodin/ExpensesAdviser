package org.isegodin.expenses.adviser.telegram;

import lombok.SneakyThrows;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author isegodin
 */
@Configuration
@ComponentScan({"org.isegodin.expenses.adviser.telegram"})
@PropertySources({
        @PropertySource("org/isegodin/expenses/adviser/telegram/telegram-bot.properties")
})
@EnableJpaRepositories
@EnableTransactionManagement
@EnableWebMvc
public class App {

    @SneakyThrows
    public static void main(String[] args) {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.setBaseResource(new ClasspathJettyResourceImpl());

        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        context.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webCtx);

        webCtx.setServletContext(context.getServletContext());
        webCtx.register(App.class);
        webCtx.refresh();

        context.addServlet(new ServletHolder(new DispatcherServlet(webCtx)), "/*");

        Server server = new Server(Integer.valueOf(webCtx.getEnvironment().getProperty("server.port")));
        server.setHandler(context);
        server.start();
        server.join();
    }
}
