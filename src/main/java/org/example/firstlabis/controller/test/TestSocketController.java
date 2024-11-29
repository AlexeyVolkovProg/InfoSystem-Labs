package org.example.firstlabis.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestSocketController {
    @GetMapping("/test")
    public String websocketTest() {
        return "websocket-test";
    }
}
