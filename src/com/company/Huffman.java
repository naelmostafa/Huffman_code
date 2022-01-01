package com.company;

import java.util.HashMap;
import java.util.Map;

public class Huffman {
    private Node root;
    private Map<Character, Integer> freqMap;
    private String text;

    public Huffman(String text) {
        readGenerateFreq(text);
        freqMapGenerator();
        this.text = text;
    }

    private void readGenerateFreq(String fileName) {
        freqMap = new HashMap<>();
        /*  read file
            loop on file by char
            store in hashMap (char, freq) */
        // freqMap.put(character, freqMap.getOrDefault(character, 0) + 1);

    }

    /* Create Tree root Node point on tree root */
    private void creatTree() {
        /*  priority Queue
            generate codeword */
    }

    private void freqMapGenerator() {
        /* loop on all file to get freq of char and store them in HashMap */
        freqMap = new HashMap<>();
        for (Character character : text.toCharArray()) {
            freqMap.put(character, freqMap.getOrDefault(character, 0) + 1);
        }
    }
}