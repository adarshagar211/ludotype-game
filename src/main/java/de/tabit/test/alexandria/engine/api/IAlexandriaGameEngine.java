package de.tabit.test.alexandria.engine.api;

import de.tabit.test.alexandria.pojo.Board;

public interface IAlexandriaGameEngine {

    /**
     * Initializes a new game with the number of players.
     * @return
     */
    Board startGame(Integer numberOfPlayers);

    /**
     * While no player has passed the 30th playing field, the game is still running.
     * */
    boolean gameIsRunning(Board board);

    /**
     * Returns which player is next in line, e.g. 'Player 3'.
     * This call does NOT advances the player on the board.
     * */
    String nextPlayer(Board board);

    /**
     * Advances the player which turn it is the given number of fields; activates the bonus or trap field if necessary and
     * returns a (very) telling message about what just happened and the general game situation.
     * */
    Board nextPlayerTurn(Integer input , Board board);
}
