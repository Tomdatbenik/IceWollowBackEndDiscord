package serverwebsocket.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import serverwebsocket.serverlets.ServerServlet;
import serverwebsocket.util.ServerBeanUtil;

/**
 * Created by dnsh on 25/12/17.
 */
@Configuration
public class AppConfig {

    @Bean
    public ServletRegistrationBean socketServlet(){
        return new ServletRegistrationBean(new ServerServlet(), "/ws/server");
    }

    @Bean
    public ServerBeanUtil stockpileBeanUtil() {
        return new ServerBeanUtil();
    }
}
