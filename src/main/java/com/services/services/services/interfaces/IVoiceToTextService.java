package com.services.services.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IVoiceToTextService {
    String getTextFromAudio(MultipartFile audio);
}
