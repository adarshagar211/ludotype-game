package de.tabit.test.alexandria.engine.dummy;

import de.tabit.test.alexandria.engine.api.IAlexandriaGameEngine;
import de.tabit.test.alexandria.pojo.Board;
import de.tabit.test.alexandria.pojo.Color;
import de.tabit.test.alexandria.pojo.Field;
import de.tabit.test.alexandria.pojo.FieldType;
import de.tabit.test.alexandria.pojo.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import static java.lang.String.format;

/**
 * @author Alexandria
 * 
 * Added changes by Adarsh Agarwal
 *
 */
public class DummyEngine implements IAlexandriaGameEngine {

	private static final AtomicInteger TURNS = new AtomicInteger(0);
	private final Random random = new Random();

	@Override
	public Board startGame(Integer numberOfPlayers) {
		Board board = initializeBoardFields();
		initializePlayers(numberOfPlayers, board);
		board.setCurrentPlayerNum(0);
		return board;
	}

	@Override
	public boolean gameIsRunning(Board board) {
		return board.isGameRunning();
	}

	@Override
	public String nextPlayer(Board board) {
		int currentPlayerNum = board.getCurrentPlayerNum() + 1;
		if (currentPlayerNum <= board.getPlayers().length) {
			board.setCurrentPlayerNum(currentPlayerNum);
		} else {
			board.setCurrentPlayerNum(1);
		}
		return format("Player %d", board.getCurrentPlayerNum());
	}

	@Override
	public Board nextPlayerTurn(Integer input, Board board) {
		Player player = board.getPlayers()[board.getCurrentPlayerNum() - 1];
		// Is skip activated
		if (player.isSkipActivated()) {
			player.setSkipActivated(false);
			return board;
		} else {
			player.setCurrentPosition(player.getCurrentPosition() + input);
			applyRules(board, player);
			return board;
		}
	}

	private void applyRules(Board board, Player player) {
		if ((player.getCurrentPosition() < 30)
				&& board.getFields()[player.getCurrentPosition() - 1].getFieldType() == FieldType.TRAP) {
			applyTrapRules(board, player);
		} else if ((player.getCurrentPosition() < 30)
				&& board.getFields()[player.getCurrentPosition() - 1].getFieldType() == FieldType.BONUS) {
			applyBonusRules(board, player);
		}
		for (Player allPlayer : board.getPlayers()) {
			if (allPlayer.getCurrentPosition() >= 30) {
				System.out.format("Player Winner %s , with color : %s \n", allPlayer.getPlayerNumber(),
						allPlayer.getColor());
				board.setGameRunning(false);
			}
		}
	}
    
	// Setting board with Trap and Bonus fields
	
	private Board initializeBoardFields() {
		Board board = new Board();
		int positionsAvailable = 0;
		List<Field> fields = new ArrayList<>();
		while (positionsAvailable++ < 30) {
			fields.add(Field.builder().fieldPosition(positionsAvailable).fieldType(FieldType.STANDARD).build());
		}
		Collections.shuffle(fields);
		positionsAvailable = 0;
		for (; positionsAvailable < 5; positionsAvailable++) {
			fields.get(positionsAvailable).setFieldType(FieldType.BONUS);
		}
		for (; positionsAvailable < 10; positionsAvailable++) {
			fields.get(positionsAvailable).setFieldType(FieldType.TRAP);
		}
		fields.sort(Comparator.comparing(Field::getFieldPosition));

		Field[] fieldArray = fields.stream().toArray(Field[]::new);
		board.setFields(fieldArray);
		return board;
	}

	/**
	 * Initializing Players on the board
	 * 
	 * @param numberOfPlayers
	 * @param board
	 */
	private void initializePlayers(Integer numberOfPlayers, Board board) {
		Player[] players = new Player[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			Player player = Player.builder().playerNumber(i + 1).currentPosition(0).color(Color.values()[i]).build();
			System.out.format("Player %d assigned color: %s \n", player.getPlayerNumber(), player.getColor());
			players[i] = player;
		}
		board.setPlayers(players);
	}

	/**
	 * Apply Trap Rules
	 * 
	 * @param board
	 * @param player
	 */
	private void applyTrapRules(Board board, Player player) {
		if (player.isJokerActivated()) {
			player.setJokerActivated(false);
		} else {
			int type = random.nextInt(3) + 1;
			System.out.println("Sorry you got into trap number : " + type);
			switch (type) {
			case 1:
				player.setCurrentPosition(player.getCurrentPosition() - 2);
				break;
			case 2:
				for (Player allPlayer : board.getPlayers())
					if (allPlayer != player)
						allPlayer.setCurrentPosition(allPlayer.getCurrentPosition() + 2);
				break;
			case 3:
				player.setSkipActivated(true);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Apply Bonus rules
	 * 
	 * @param board
	 * @param player
	 */
	private void applyBonusRules(Board board, Player player) {
		int type = random.nextInt(3) + 1;
		System.out.println("Welcome you got into bonus number : " + type);
		switch (type) {
		case 1:
			player.setCurrentPosition(player.getCurrentPosition() + 2);
			break;
		case 2:
			for (Player allPlayer : board.getPlayers())
				if (allPlayer != player) {
					int position = allPlayer.getCurrentPosition() > 2 ? allPlayer.getCurrentPosition() - 2 : 0;
					allPlayer.setCurrentPosition(position);
				}
			break;
		case 3:
			player.setJokerActivated(true);
			break;
		default:
			break;
		}
	}
}
