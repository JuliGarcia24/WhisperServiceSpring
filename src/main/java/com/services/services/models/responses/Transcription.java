package com.services.services.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Transcription {
    private String text;
    private List<Segment> segments;
    private String language;
}
