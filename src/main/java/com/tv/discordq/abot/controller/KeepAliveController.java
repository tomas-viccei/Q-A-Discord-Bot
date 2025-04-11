package com.tv.discordq.abot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeepAliveController {

    @GetMapping("/")
    public String ping(){
        return "bot is awake";
    }
}
