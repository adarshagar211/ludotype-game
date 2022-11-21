package de.tabit.test.alexandria.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

	private Player[] players;
	private Field[] fields = new Field[30];

	private boolean isGameRunning = true;
	private int currentPlayerNum = 0;
}
