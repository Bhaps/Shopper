package com.example.patrick.shopper.Utility;

import java.util.ArrayList;

/**
 * Stack which pushes and pops integers.
 *
 * Created by patrick on 4/02/18.
 */

public class Stack {
    private ArrayList<Position> stack;

    public Stack() {
        stack = new ArrayList<>();
    }

    public void push(Position pos) {
        stack.add(pos);
    }

    public Position pop() {
        int lastIndex = stack.size() - 1;
        Position poppedValue = stack.get(lastIndex);
        stack.remove(lastIndex);
        return poppedValue;
    }

    public int getSize() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.size() == 0;
    }

    @Override
    public String toString() {
        String s = "Bottom of stack ->";
        for(Position pos : stack) {
            s += String.format("itemIndex: " + pos.getItemIndex() + " capacityIndex: " + pos.getCapacityIndex());
            s += System.getProperty("line.separator");
        }
        return s;
    }

}
