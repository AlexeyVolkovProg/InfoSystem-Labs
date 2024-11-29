package org.example.firstlabis.controller.socket;

import lombok.extern.slf4j.Slf4j;
import org.example.firstlabis.dto.domain.request.HumanBeingUpdateDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class HumanWebSocketController {

    @MessageMapping("/human-being")
    @SendTo("/topic/human-being")
    public HumanBeingUpdateDTO handleUpdateHuman(HumanBeingUpdateDTO message){
        log.info("New Socket Message" + message);
        return message;
    }
}
