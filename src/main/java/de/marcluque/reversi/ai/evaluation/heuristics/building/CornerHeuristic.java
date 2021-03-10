package de.marcluque.reversi.ai.evaluation.heuristics.building;

import de.marcluque.reversi.ai.evaluation.heuristics.AbstractHeuristic;
import de.marcluque.reversi.ai.evaluation.heuristics.Heuristic;
import de.marcluque.reversi.ai.search.AbstractSearch;
import de.marcluque.reversi.map.Map;
import de.marcluque.reversi.util.Coordinate;
import de.marcluque.reversi.util.MapUtil;

public class CornerHeuristic extends AbstractHeuristic implements Heuristic {

    public CornerHeuristic(double weight) {
        super.weight = weight;
    }

    @Override
    public void initHeuristic(Map map) {
        for (int y = 0; y < Map.getMapHeight(); y++) {
            for (int x = 0; x < Map.getMapWidth(); x++) {
                if (MapUtil.isTileCorner(map, x, y) != null) {
                    Map.getCorners().add(new Coordinate(x, y));
                }
            }
        }
    }

    @Override
    public double executeHeuristic(Map map) {
        return Map.getCorners()
                .stream()
                .filter(corner -> map.getGameField()[corner.getY()][corner.getX()] == AbstractSearch.MAX)
                .count() / (double) Map.getCorners().size();
    }
}