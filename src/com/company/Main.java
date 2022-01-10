package com.company;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        Huffman huffman = new Huffman(args[0], args[1]);
    }
}

/* { =010, a=00, r=1011, s=011, e=1111, h=100, i=1010, k=1110, m=110}
*   1110 00 1011 1010 110 010 011 00 110 1111 100
*  */