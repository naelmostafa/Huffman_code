package com.company;

import static java.lang.Integer.compare;


public class Node implements Comparable<Node> {
    private int frequency;
    private Node leftNode;
    private Node rightNode;

    public Node(Node leftNode, Node rightNode) {
        this.frequency = leftNode.getFrequency() + rightNode.getFrequency();
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(Node node) {
        return compare(frequency, node.getFrequency());
    }
}
