package com.services.services.controllers;

import com.services.services.services.interfaces.IVoiceToTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/converter")
public class ConverterController {

    @Autowired
    private IVoiceToTextService voiceToTextService;

    @GetMapping
    public ResponseEntity<String> sayHi(){
        return new ResponseEntity<>("Welcome to SpringBoot API", HttpStatus.OK);
    }

    @PostMapping("/audio")
    public ResponseEntity<String> getTextFromAudio(@RequestParam("audio") MultipartFile audio){
        return new ResponseEntity<>(this.voiceToTextService.getTextFromAudio(audio), HttpStatus.OK);
    }

}
