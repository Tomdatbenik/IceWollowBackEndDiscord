package websocketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {"websocketserver", "servercomponent"})
@EnableAutoConfiguration
public class WebsocketServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketServerApplication.class, args);
	}
}
