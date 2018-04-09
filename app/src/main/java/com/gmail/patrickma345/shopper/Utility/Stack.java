package com.gmail.patrickma345.shopper.Utility;

import java.util.ArrayList;

/**
 * Stack which pushes and pops integers.
 *
 * Created by patrickma345 on 4/02/18.
 */

public class Stack {
    private ArrayList<Position> stack;

    public Stack() {
        stack = new ArrayList<>();
    }

    public Stack(ArrayList<Position> stack) {
        this.stack = stack;
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

    public Position peek() {
        int lastIndex = stack.size() - 1;
        return stack.get(lastIndex);
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


    @Override
    public Stack clone() {
        ArrayList<Position> posCopy = (ArrayList<Position>) stack.clone();
        return new Stack(posCopy);
    }

}
