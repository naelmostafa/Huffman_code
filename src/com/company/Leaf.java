package com.company;

public class Leaf {
    private final char character;
    private final int freq;

    public Leaf(char character, int freq) {
        this.character = character;
        this.freq = freq;
    }

    public char getCharacter() {
        return character;
    }

    public int getFreq() {
        return freq;
    }
}
