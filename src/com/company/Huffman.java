package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Huffman {
    private Node root;
    private Map<Character, Integer> freqMap;
    private Map<Character, String> codeword;
    private String filename;
    private float ratio;

    public Huffman(String option, String filename) throws IOException {
        option = option.toLowerCase();
        this.filename = filename;
        if (option.equals("c")) {
            readGenerateFreq(filename);
            encode();
        } else if (option.equals("d")) {
            decompress();
        }
    }

    public Map<Character, Integer> getFreqMap() {
        return freqMap;
    }

    public Map<Character, String> getCodeword() {
        return codeword;
    }

    private void readGenerateFreq(String filename) throws IOException {
        freqMap = new HashMap<>();
        /* Creation of File Descriptor for input file */
        File file = new File(filename);

        /* Creation of File Reader object */
        FileReader reader = new FileReader(file);

        /* Creation of BufferedReader object */
        BufferedReader buffer = new BufferedReader(reader);

        /* Read char by Char */
        int c = 0;
        while ((c = buffer.read()) != -1) {
            char character = (char) c;          // converting integer to char
            freqMap.put(character, freqMap.getOrDefault(character, 0) + 1);
        }
        reader.close();
    }

    /*  priority Queue
        generate codeword */

    private void createTree() {
        Queue<Node> queue = new PriorityQueue<>();
        freqMap.forEach((character, frequency) -> queue.add(new Leaf(character, frequency)));

        /* Create Tree root Node point on tree root */
        while (queue.size() > 1) {
            queue.add(new Node(queue.poll(), Objects.requireNonNull(queue.poll())));
        }
        root = queue.poll();
    }

    private void generateCodeword() {
        codeword = new HashMap<Character, String>();
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

    /*
    __generateCodeword(node.getLeftNode(), (byte) (code << 1));
    __generateCodeword(node.getRightNode(), (byte) ((byte) (code << 1) + 1));
    */

    /**
     * Writing the freq
     *
     * @param output
     */
    private void writeCodeword(FileWriter output) throws IOException {
        freqMap.forEach(((character, freq) -> {
            try {
                output.write(character);
                output.write('~');
                output.write(freq.toString());
                output.write('-');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        output.write("\n");
    }

    public void readCodeword(BufferedReader buffer) throws IOException {
        freqMap = new HashMap<>();
        int c = 0;
        int temp;
        while ((c = buffer.read()) != '\n') {

            char character = (char) c;          // converting integer to char
            temp = buffer.read();
            if (temp == '~') {
                StringBuilder str = new StringBuilder();
                while ((temp = buffer.read()) != '-') {
                    str.append((char) temp);
                }
                freqMap.put(character, Integer.parseInt(str.toString()));
                buffer.mark(c);
            }
//            if ((char) temp == '\n') {
//                break;
//            }
        }
    }

    /* TODO: Encode replace every character with its corresponding code word */
    private void encode() throws IOException {

        createTree();
        generateCodeword();
        String compressedName = "6169.1." + filename + ".hc";
        File compressedFile = new File(compressedName);
        FileWriter myWriter = new FileWriter(compressedFile);
        writeCodeword(myWriter);

        /* Creation of File Descriptor for input file */
        File decompressedFile = new File(filename);
        /* Creation of File Reader object */
        FileReader reader = new FileReader(decompressedFile);
        /* Creation of BufferedReader object */
        BufferedReader buffer = new BufferedReader(reader);
        /* Read char by Char */
        int c = 0;
        while ((c = buffer.read()) != -1) {
            char character = (char) c;          // converting integer to char
            myWriter.write(codeword.get(character));
        }
        reader.close();
        myWriter.close();

        /* File size in kilobytes */
        long compressedSize = (Files.size(Paths.get(compressedName))) / 1024;
        System.out.println("CompressedSize  = " + String.format("%,d kilobyte", compressedSize));
        long uncompressedSize = (Files.size(Paths.get(filename))) / 1024;
        System.out.println("UncompressedSize = " + String.format("%,d kilobyte", uncompressedSize));
        ratio = (float) compressedSize / uncompressedSize;
        System.out.println("Compression ratio = " + ratio);
    }

    private char TranslateCodeword(Node node, String code) {
        if (node instanceof Leaf) {
            return ((Leaf) node).getCharacter();

        }
        char a = TranslateCodeword(node.getLeftNode(), code.concat("0"));
        TranslateCodeword(node.getRightNode(), code.concat("1"));
        return 0;
    }

    private void decode(FileWriter fileWriter, BufferedReader buffer) throws IOException {
        int c = 0;
        int temp;
        /* read char by char to map in the tree */
        buffer.reset();
        StringBuilder str = new StringBuilder();
        Node node = root;
        while ((c = buffer.read()) != -1) {
            char character = (char) c;
            /* TODO: replace */
            if (node instanceof Leaf) {
                fileWriter.write(((Leaf) node).getCharacter());
                node = root;
            }
            if (character == '0') {
                node = node.getLeftNode();
            } else if (character == '1') {
                node = node.getRightNode();
            }
        }
        fileWriter.write(((Leaf) node).getCharacter());
    }

    /**
     * reference tutorialsPoint.com
     **/
    private String removeEx(String name) {
        int pos = name.lastIndexOf('.');
        if (pos > -1)
            return name.substring(0, pos);
        else
            return name;
    }
    private long getFileSize(File file) {
        long length = file.length();
        return length;
    }
    private void decompress() throws IOException {
        /* Creation of File Descriptor for input file */
        File compressedFile = new File(filename);

        /* Creation of File Reader object */
        FileReader reader = new FileReader(compressedFile);

        /* Creation of BufferedReader object */
        BufferedReader buffer = new BufferedReader(reader);

        /* Creat decompressed File */
        String decompressedName = "extracted" + removeEx(filename);
        File decompressedFile = new File(decompressedName);
        FileWriter fileWriter = new FileWriter(decompressedFile);

        readCodeword(buffer);
        createTree();
        generateCodeword();
        decode(fileWriter, buffer);

        reader.close();
        fileWriter.close();
    }

}