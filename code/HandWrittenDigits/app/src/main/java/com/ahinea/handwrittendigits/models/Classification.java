package com.ahinea.handwrittendigits.models;

public class Classification {

    private float confidence;
    private String label;

    Classification() {
        this.confidence = -1.0F;
        this.label = null;
    }

    void update(float conf, String label) {
        this.confidence = conf;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public float getConfidence() {
        return confidence;
    }
}
