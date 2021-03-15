package de.marcluque.reversi.ai.search.strategies.opps;

import de.marcluque.reversi.ai.evaluation.Evaluation;
import de.marcluque.reversi.ai.evaluation.rules.Rules;
import de.marcluque.reversi.ai.search.AbstractSearch;
import de.marcluque.reversi.ai.search.MoveSorting;
import de.marcluque.reversi.map.Map;
import de.marcluque.reversi.util.MapUtil;
import de.marcluque.reversi.util.Move;
import de.marcluque.reversi.util.SortNode;

/*
 * Created with <3 by Marc Luqué, March 2021
 */
public class OpponentPruningParanoidSearch extends AbstractSearch {

    // TODO: MIGHT NEED SOME FINE TUNING:
    private static final int SIZE_GROUP_ONE = 1;

    private static final int BRANCHING_LIMIT_GROUP_ONE = 5;

    private static final int BRANCHING_LIMIT_GROUP_TWO = 1;

    public static Move search(Map map, int depth, int[] totalStates) {
        Move bestMove = null;
        double maxValue = Double.MIN_VALUE;

        var sortedMoves = MoveSorting.sort(map, MAX, MapUtil.getAvailableMoves(map, MAX));
        int numberAvailableMoves = sortedMoves.size();
        int moveCount = 0;

        for (int i = 0, sortedMovesSize = sortedMoves.size(); i < sortedMovesSize; i++) {
            SortNode move = sortedMoves.get(i);
            totalStates[0]++;

            if (i >= BRANCHING_LIMIT_GROUP_TWO) {
                moveCount++;
            }

            double value = OPPS(move.getMap(), depth - 1, MapUtil.nextPlayer(MapUtil.playerToInt(MAX)),
                    moveCount, numberAvailableMoves, totalStates);

            maxValue = Math.max(maxValue, value);
            if (value > maxValue) {
                bestMove = move.getMove();
                maxValue = value;
            }
        }

        return bestMove;
    }

    private static double OPPS(Map map, int depth, int turn, int moveCount, int numberAvailableMoves, int[] totalStates) {
        if (depth <= 0 || (numberAvailableMoves < 2 && MapUtil.isTerminal(map))) {
            return Evaluation.utility(map, MAX);
        }

        char player = MapUtil.intToPlayer(turn);
        boolean maxTurn = turn == MAX;

        var sortedMoves = MoveSorting.sort(map, player, MapUtil.getAvailableMoves(map, MAX));
        numberAvailableMoves = sortedMoves.size();

        if (maxTurn) {
            moveCount = 0;
        } else if (moveCount == SIZE_GROUP_ONE) {
            sortedMoves = sortedMoves.subList(0, BRANCHING_LIMIT_GROUP_TWO);
        } else {
            sortedMoves = sortedMoves.subList(0, BRANCHING_LIMIT_GROUP_ONE);
        }

        double bestValue = 0;
        for (int i = 0, sortedMovesSize = sortedMoves.size(); i < sortedMovesSize; i++) {
            totalStates[0]++;

            if (i >= BRANCHING_LIMIT_GROUP_TWO) {
                moveCount++;
            }

            double value = OPPS(sortedMoves.get(i).getMap(), depth - 1,
                    MapUtil.nextPlayer(turn), moveCount, numberAvailableMoves, totalStates);

            bestValue = maxTurn ? Math.max(bestValue, value) : Math.min(bestValue, value);
        }

        return bestValue;
    }
}