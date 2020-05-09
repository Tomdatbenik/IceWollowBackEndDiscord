package serverwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"serverwebsocket", "servercomponent", "usercomponent", "chatcomponent"})
@ComponentScan(basePackages = {"serverwebsocket", "servercomponent", "usercomponent", "chatcomponent"})
@EnableJpaRepositories({"usercomponent.dal", "servercomponent.dal"})
public class ServerWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerWebsocketApplication.class, args);
    }
}
