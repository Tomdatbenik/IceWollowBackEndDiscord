package serverwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = { "servercomponent", "usercomponent", "chatcomponent"})
@ComponentScan(basePackages = { "servercomponent", "usercomponent", "chatcomponent"})
public class ServerWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerWebsocketApplication.class, args);
    }
}
