package de.tabit.test.alexandria.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

	private int playerNumber;
	private int currentPosition;
	private Color color; 
	
	private boolean isJokerActivated;
	private boolean isSkipActivated;
	
}
