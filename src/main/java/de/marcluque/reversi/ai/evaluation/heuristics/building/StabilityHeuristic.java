package de.marcluque.reversi.ai.evaluation.heuristics.building;

import de.marcluque.reversi.ai.evaluation.heuristics.AbstractHeuristic;
import de.marcluque.reversi.ai.evaluation.heuristics.Heuristic;
import de.marcluque.reversi.ai.search.AbstractSearch;
import de.marcluque.reversi.map.Map;
import de.marcluque.reversi.moves.AbstractMove;
import de.marcluque.reversi.util.Coordinate;
import de.marcluque.reversi.util.MapUtil;
import de.marcluque.reversi.util.Transition;

public class StabilityHeuristic extends AbstractHeuristic implements Heuristic {

    public StabilityHeuristic(double weight) {
        super.weight = weight;
    }

    @Override
    public void initHeuristic(Map map) {

    }

    @Override
    public double executeHeuristic(Map map) {
        int threatCounter = 0;

        for (int y = 0; y < Map.getMapHeight(); y++) {
            for (int x = 0; x < Map.getMapWidth(); x++) {
                if (map.getGameField()[y][x] == AbstractSearch.MAX) {
                    Transition transition;
                    Coordinate neighbour;
                    Coordinate oppositeNeighbour;

                    for (int k = 0; k < 4; k++) {
                        // Checks whether a path via a transition can be enclosed
                        // Check the potential neighbour in direction k
                        transition = Map.getTransitions().get(new Transition(x, y, k));
                        neighbour = (transition != null)
                                ? new Coordinate(transition.getX(), transition.getY())
                                : new Coordinate(x + AbstractMove.CORNERS[k][0], y + AbstractMove.CORNERS[k][1]);

                        // Check the potential opposite neighbour in direction k + 4
                        transition = Map.getTransitions().get(new Transition(x, y, k + 4));
                        oppositeNeighbour = (transition != null)
                                ? new Coordinate(transition.getX(), transition.getY())
                                : new Coordinate(x + AbstractMove.CORNERS[k + 4][0], y + AbstractMove.CORNERS[k + 4][1]);

                        boolean threatInMap = MapUtil.isCoordinateInMap(neighbour.getX(), neighbour.getY())
                                && MapUtil.isCoordinateInMap(oppositeNeighbour.getX(), oppositeNeighbour.getY());

                        boolean neighbourIsThreat = MapUtil.isTileFree(
                                map.getGameField()[neighbour.getY()][neighbour.getX()])
                                && MapUtil.isDifferentPlayerStone(map, oppositeNeighbour.getX(),
                                                                oppositeNeighbour.getY(), AbstractSearch.MAX);

                        boolean oppositeNeighbourIsThreat = MapUtil.isTileFree(
                                map.getGameField()[oppositeNeighbour.getY()][oppositeNeighbour.getX()])
                                && MapUtil.isDifferentPlayerStone(map, neighbour.getX(), neighbour.getY(),
                                                                AbstractSearch.MAX);

                        // Threat is found
                        if (threatInMap && (neighbourIsThreat || oppositeNeighbourIsThreat)) {
                            threatCounter++;
                        }
                    }
                }
            }
        }

        // Divide by number of directions
        return threatCounter / 8d;
    }
}