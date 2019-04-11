package com.ahinea.handwrittendigits.models;

public interface Classifier {
    String name();

    Classification recognize(final float[] pixels);
}