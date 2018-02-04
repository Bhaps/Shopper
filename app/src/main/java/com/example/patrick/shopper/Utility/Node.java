package com.example.patrick.shopper.Utility;

/**
 * A node that will be used to create a network of nodes. Holds information as a String and the
 * previous/next node.
 *
 * @author Patrick Ma
 * @since 4/02/2018
 */

public class Node {

    private String info;
    private Node nextNode;
    private Node prevNode;
    private boolean isStart = false;
    private boolean isFinish = false;

    public Node(String info, Node nextNode, Node prevNode) {
        this.info = info;
        this.nextNode = nextNode;
        this.prevNode = prevNode;
    }

    public Node(String info, Node nextNode) {
        this(info, nextNode, null);
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public String getInfo() {
        return info;
    }

    public boolean getIsFinish() {
        return this.isFinish;
    }

    public boolean getIsStart() {
        return this.isStart;
    }



}
