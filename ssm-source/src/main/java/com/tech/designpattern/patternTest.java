package com.tech.designpattern;

import java.util.Collections;

public class patternTest {

    public static void main(String[] args) {
        BuilderPattern.Builder builder = new BuilderPattern.Builder("xxj",89);
        builder.addAddress("dfs");
        builder.addScore(89);
        builder.addCondition(Collections.emptyMap());
        BuilderPattern builderPattern = builder.build();
        System.out.printf("ok~");
    }
}
