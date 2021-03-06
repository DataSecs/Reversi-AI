package de.marcluque.reversi.util;

import de.marcluque.reversi.ai.evaluation.HeuristicEvaluation;
import de.marcluque.reversi.map.Map;

import java.util.Objects;

/*
 * Created with <3 by marcluque, March 2021
 */
public class SortNode {

    private final int x;

    private final int y;

    private final int specialTile;

    private final double heuristicValue;

    private Map map;

    public SortNode(Move move, Map mapClone) {
        this.x = move.getX();
        this.y = move.getY();
        this.specialTile = move.getSpecialTile();
        // We always evaluate for MAX since MIN tries to play the worst possible move for MAX
        this.heuristicValue = HeuristicEvaluation.heuristicValue(mapClone);
        this.map = mapClone;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Move getMove() {
        return new Move(x, y, specialTile);
    }

    public double getHeuristicValue() {
        return heuristicValue;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }
}