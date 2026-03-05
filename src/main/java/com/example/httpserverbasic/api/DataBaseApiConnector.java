package com.example.httpserverbasic.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataBaseApiConnector {



    @PostMapping("/sentdata")
    public void sendData(@RequestParam Integer var){
        if (var == 0 || var == 1){
            DataBaseModule.SentData(var);
            System.out.println("Data sent confirmation!");
        }
    }





}
