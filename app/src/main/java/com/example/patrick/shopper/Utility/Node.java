package com.example.patrick.shopper.Utility;

import java.util.ArrayList;

/**
 * A node that will be used to create a network of nodes. Each Node nolds the an integer as
 * information.
 *
 * @author Patrick Ma
 * @since 4/02/2018
 */

public class Node {

    private int value;
    private ArrayList<Node> children = null;
    private Node parent = null;
    private boolean isStart = false;
    private boolean isFinish = false;

    public Node() {
        this.children = new ArrayList<>();
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }

    public int getValue() {
        return value;
    }

    public boolean getIsFinish() {
        return this.isFinish;
    }

    public boolean getIsStart() {
        return this.isStart;
    }



}
