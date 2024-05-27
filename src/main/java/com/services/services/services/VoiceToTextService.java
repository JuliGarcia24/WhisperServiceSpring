package com.services.services.services;

import com.services.services.models.responses.TranscriptionResponse;
import com.services.services.services.interfaces.IVoiceToTextService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VoiceToTextService implements IVoiceToTextService {

    private final WebClient whisperClient;

    public VoiceToTextService(){
        this.whisperClient = WebClient.builder()
                .baseUrl("https://6201-104-155-214-185.ngrok-free.app") // Should get this from the properties
                .build();
    }

    @Override
    public String getTextFromAudio(MultipartFile audio) {

        List<String> acceptedFormats = new ArrayList<String>(Arrays.asList("ogg", "mp3", "wav"));

        if (audio.isEmpty()) {
            return "Please select a file to upload";
        }

        String extension = FilenameUtils.getExtension(audio.getOriginalFilename());

        if(!acceptedFormats.contains(extension)){
            return "That type of file is not allowed";
        }

        Mono<TranscriptionResponse> responseMono = this.whisperClient.post()
                .uri("/transcribe")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("audio", audio.getResource()))
                .retrieve()
                .bodyToMono(TranscriptionResponse.class)
                .doOnError(WebClientResponseException.class, ex -> {
                    throw new RuntimeException("Error during audio transcription: " + ex.getResponseBodyAsString());
                });

        TranscriptionResponse response = responseMono.block(); // Wait for the response

        if (response != null) {
            System.out.println("Response: " + response);
            return response.getTranscription().getText();
        } else {
            throw new RuntimeException("No response received from server");
        }
    }

}
