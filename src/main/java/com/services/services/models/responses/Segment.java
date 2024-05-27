package com.services.services.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Segment {
    private int id;
    private int seek;
    private double start;
    private double end;
    private String text;
    private List<Integer> tokens;
    private double temperature;
    private double avg_logprob;
    private double compression_ratio;
    private double no_speech_prob;

}
