package Run;
import Controller.ChatGptEndpoints;
import Controller.OtherEndpoints;
import Controller.UserEndpoints;
import Utils.ServiceException;
import org.example.ServiceUser;
import org.example.SimpleUserInfo;
import org.example.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Properties;
import java.util.Vector;

import Utils.RestAPIUtils;

@CrossOrigin
@ComponentScan("Controller")
@ComponentScan("Utils")
@SpringBootApplication
public class RunRestAPI {
    public static void main(String[] args){
        new UserEndpoints();
        new OtherEndpoints();
        new ChatGptEndpoints();
        SpringApplication.run(RunRestAPI.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventListener() {
        return (ApplicationReadyEvent event) -> {
            String serverPort = event.getApplicationContext().getEnvironment().getProperty("local.server.port");
            String serverAddress = event.getApplicationContext().getEnvironment().getProperty("local.server.address", "localhost");
            System.out.println("Server pornit la adresa: http://" + serverAddress + ":" + serverPort);
        };
    }
}
