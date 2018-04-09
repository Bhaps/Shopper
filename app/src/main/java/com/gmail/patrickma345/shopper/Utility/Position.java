package com.gmail.patrickma345.shopper.Utility;

/**
 * Used to represent a position on the board that is used in the zero one knapsack algorithm,
 * specified by a given itemIndex and capacityIndex.
 *
 * Created by patrickma345 on 5/02/18.
 */

public class Position {

    int itemIndex;
    int capacityIndex;

    public Position(int i, int c) {
        itemIndex = i;
        capacityIndex = c;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public int getCapacityIndex() {
        return capacityIndex;
    }

    @Override
    public String toString() {
        return String.format("(itemIndex: %d capacityIndex: %d)", itemIndex, capacityIndex);
    }

    @Override
    public boolean equals(Object obj) {
        Position otherPos = (Position) obj;
        if(this.itemIndex == otherPos.itemIndex && this.capacityIndex == otherPos.capacityIndex) {
            return true;
        } else {
            return false;
        }
    }
}
