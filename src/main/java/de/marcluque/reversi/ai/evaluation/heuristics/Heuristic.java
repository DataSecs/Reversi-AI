package de.marcluque.reversi.ai.evaluation.heuristics;

import de.marcluque.reversi.map.Map;

/*
 * Created with <3 by marcluque, March 2021
 */
public interface Heuristic {
    
    void initHeuristic(Map map);

    double executeHeuristic(Map map, char player);

    double getWeight();

    void updateWeight(double weight);
}