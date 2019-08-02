package eu.qcloud.websocket;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(Principal principal, String message) throws Exception {

        Thread.sleep(1000); // simulated delay
        return "your message: " + message;
    }

    @MessageMapping("/bye")
    public void greetingDos(Principal principal, String message) throws Exception {

        messagingTemplate.convertAndSendToUser("dmance@outlook.es", "/queue/reply", "caca " + message);
    }
}
