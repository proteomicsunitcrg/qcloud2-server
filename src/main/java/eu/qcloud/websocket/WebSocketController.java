package eu.qcloud.websocket;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(Principal principal, String message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return message;
    }
}
