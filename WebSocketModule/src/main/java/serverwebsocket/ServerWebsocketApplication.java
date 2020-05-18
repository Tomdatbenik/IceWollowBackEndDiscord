package serverwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = {"serverwebsocket", "restmodule"})
@ComponentScan(basePackages = {"serverwebsocket", "restmodule"})
@EnableJpaRepositories(basePackages = {"restmodule.dal", "serverwebsocket"})
@EnableTransactionManagement
@Configuration
public class ServerWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerWebsocketApplication.class, args);
    }
}
