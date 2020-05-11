package chatcomponent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = {"serverwebsocket", "servercomponent", "usercomponent", "chatcomponent"})
@ComponentScan(basePackages = {"usercomponent", "chatcomponent"})
@EnableJpaRepositories(basePackages = {"usercomponent.dal"})
@EnableTransactionManagement
@Configuration
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}
