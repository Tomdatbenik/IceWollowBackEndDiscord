package websocketserver.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import websocketserver.serverlets.ChatServlet;
import websocketserver.util.ChatBeanUtil;

/**
 * Created by dnsh on 25/12/17.
 */
@Configuration
public class AppConfig {

    @Bean
    public ServletRegistrationBean socketServlet(){
        return new ServletRegistrationBean(new ChatServlet(), "/ws/stockpile");
    }

    @Bean
    public ChatBeanUtil stockpileBeanUtil() {
        return new ChatBeanUtil();
    }
}
