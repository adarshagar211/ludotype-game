package de.tabit.test.alexandria.simulation;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.tabit.test.alexandria.engine.api.IAlexandriaGameEngine;
import de.tabit.test.alexandria.engine.dummy.DummyEngine;
import de.tabit.test.alexandria.pojo.Board;

public class AlexandriaSimulation {

    private static final Boolean USE_DUMMY_ENGINE = Boolean.TRUE;

    public static void main(String[] args) {
        IAlexandriaGameEngine gameEngine = locateGameEngine();
        Board board = startGame(gameEngine);

        while(gameEngine.gameIsRunning(board)) {
            nextTurn(gameEngine , board);
        }

        System.out.println("Congratulations, the game has ended. Please play again soon...");
        
    }

    private static Board startGame(IAlexandriaGameEngine gameEngine) {
        System.out.println("The game is starting...");
        System.out.print("Please enter the number of players (2-4):");
        Integer input = null;
        while(true) {
            input = userInput();
            if (input != null && input >= 2 && input <= 4) {
                break;
            } else {
                System.err.println("Please enter a number between 2 and 4.");
            }
        }
        Board gameEngineInformation = gameEngine.startGame(input);
        System.out.println(gameEngineInformation);
        return gameEngineInformation;
    }

    private static void nextTurn(IAlexandriaGameEngine gameEngine, Board board) {
        System.out.print(format("The next player is %s. Please enter the dice number: ", gameEngine.nextPlayer(board)));
        Integer input = null;
        while(true) {
            input = userInput();
            if (input != null && input >= 1 && input <= 6) {
                break;
            } else {
                System.err.println("Please enter a number between 1 and 6.");
            }
        }
        Board gameEngineInformation = gameEngine.nextPlayerTurn(input, board);
        System.out.println(gameEngineInformation);
    }

    private static IAlexandriaGameEngine locateGameEngine() {
        if (USE_DUMMY_ENGINE) {
            return new DummyEngine();
        }
        throw new IllegalStateException("Working engine not yet implemented!");
    }

    private static Integer userInput()
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            Integer value = Integer.valueOf(line);
            return value;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Invalid user input. Please try again");
            return null;
        }
    }
}
