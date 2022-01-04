package com.company;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Enter fileName:");
        Scanner input =new Scanner(System.in);
        String inputFileName = input.nextLine();
        Huffman human = new Huffman(inputFileName+".txt");


    }
}
