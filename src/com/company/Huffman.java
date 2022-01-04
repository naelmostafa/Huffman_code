package com.company;

import java.io.*;
import java.util.*;

public class Huffman {
    private Node root;
    private Map<Character, Integer> freqMap;
    private Map<Character, String> codeword;

    public Huffman(char type, String filename) throws IOException {
        if (type == 'c') {
            encode(filename);
        } else if (type == 'd') {
            decode(filename);
        }
    }

    private void readGenerateFreq(String filename) throws IOException {
        freqMap = new HashMap<>();
        /* Creation of File Descriptor for input file */
        File f = new File(filename);

        /* Creation of File Reader object */
        FileReader fr = new FileReader(f);

        /* Creation of BufferedReader object */
        BufferedReader br = new BufferedReader(fr);

        /* Read char by Char */
        int c = 0;
        while ((c = br.read()) != -1) {
            if (c == ' '){
                continue;
            }
            char character = (char) c;          // converting integer to char
            freqMap.put(character, freqMap.getOrDefault(character, 0) + 1);
        }
    }

    /*  priority Queue
        generate codeword */
    private void creatTree() {
        Queue<Node> queue = new PriorityQueue<>();
        freqMap.forEach((character, frequency) -> queue.add(new Leaf(character, frequency)));

        /* Create Tree root Node point on tree root */
        while (queue.size() > 1) {
            queue.add(new Node(queue.poll(), Objects.requireNonNull(queue.poll())));
        }
        root = queue.poll();
    }

    private void generateCodeword() {
        codeword = new HashMap<>();
        __generateCodeword(root, "");
    }

    private void __generateCodeword(Node node, String code) {
        if (node instanceof Leaf) {
            codeword.put(((Leaf) node).getCharacter(), code);
            return;
        }
        __generateCodeword(node.getLeftNode(), code.concat("0"));
        __generateCodeword(node.getRightNode(), code.concat("1"));
    }

    /*  output extension ==> <id>.<n>.abc.exe.hc */
    public void encode(String filename) throws IOException {
        readGenerateFreq(filename);
        creatTree();
        generateCodeword();
        /* TODO: Encode replace every character with its corresponding code word */

    }

    /* TODO: read after -1 => codewords ex: a:0 b:10 delimiter = ":" --- generate hashmap<Codeword>*/
    public void decode(String filename) {
        readEncoded(filename);
        translateFile(filename, codeword);
    }

    private void translateFile(String filename, Map<Character, String> codeword) {
    }

    private void readEncoded(String filename) {
    }
}