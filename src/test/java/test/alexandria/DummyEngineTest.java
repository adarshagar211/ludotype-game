package test.alexandria;

import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import de.tabit.test.alexandria.engine.dummy.DummyEngine;
import de.tabit.test.alexandria.pojo.Board;
import de.tabit.test.alexandria.pojo.FieldType;

public class DummyEngineTest {

	@Test
	public void testinitializeBoardFields() {
		DummyEngine dummyEngine = new DummyEngine();
		Board board = dummyEngine.startGame(3);

		Assert.assertNotNull(board);
		Assert.assertNotNull(board.getPlayers());
		Assert.assertNotNull(board.getFields());
		Assert.assertEquals(3, board.getPlayers().length);
		Assert.assertEquals(30, board.getFields().length);
		Assert.assertEquals(5, Stream.of(board.getFields()).filter(x -> x.getFieldType() == FieldType.BONUS).count());
		Assert.assertEquals(5, Stream.of(board.getFields()).filter(x -> x.getFieldType() == FieldType.TRAP).count());
	}

	@Test
	public void testIsGameRunning() {
		DummyEngine dummyEngine = new DummyEngine();
		Board board = dummyEngine.startGame(3);

		Assert.assertNotNull(board);
		Assert.assertNotNull(board.getPlayers());
		Assert.assertNotNull(board.getFields());
		Assert.assertTrue(board.isGameRunning());
	}

	@Test
	public void testNextPlayer() {
		DummyEngine dummyEngine = new DummyEngine();
		Board board = dummyEngine.startGame(3);
		Assert.assertEquals(0, board.getCurrentPlayerNum());
		dummyEngine.nextPlayer(board);
		Assert.assertNotNull(board);
		Assert.assertNotNull(board.getPlayers());
		Assert.assertNotNull(board.getFields());
		Assert.assertEquals(1, board.getCurrentPlayerNum());
	}

	@Test(expected = Exception.class)
	public void testNextPlayerTurnException() {
		DummyEngine dummyEngine = new DummyEngine();
		Board board = dummyEngine.startGame(3);
		dummyEngine.nextPlayerTurn(4, board);
		Assert.assertNotNull(board);
		Assert.assertNotNull(board.getPlayers());
		Assert.assertNotNull(board.getFields());
		Assert.assertEquals(1, board.getCurrentPlayerNum());
	}

	@Test
	public void testNextPlayerTurn() {
		DummyEngine dummyEngine = new DummyEngine();
		Board board = dummyEngine.startGame(3);
		Assert.assertEquals(0, board.getCurrentPlayerNum());
		dummyEngine.nextPlayer(board);
		dummyEngine.nextPlayerTurn(4, board);
		Assert.assertNotNull(board);
		Assert.assertNotNull(board.getPlayers());
		Assert.assertNotNull(board.getFields());
		Assert.assertEquals(1, board.getCurrentPlayerNum());
	}

	@Test
	public void testGameCompleted() {
		DummyEngine dummyEngine = new DummyEngine();
		Board board = dummyEngine.startGame(3);
		Assert.assertEquals(0, board.getCurrentPlayerNum());
		dummyEngine.nextPlayer(board);

		board.getPlayers()[0].setCurrentPosition(27);
		dummyEngine.nextPlayerTurn(4, board);
		Assert.assertNotNull(board);
		Assert.assertNotNull(board.getPlayers());
		Assert.assertNotNull(board.getFields());
		Assert.assertFalse(board.isGameRunning());
	}

	@Test
	public void testGameNotCompleted() {
		DummyEngine dummyEngine = new DummyEngine();
		Board board = dummyEngine.startGame(3);
		Assert.assertEquals(0, board.getCurrentPlayerNum());
		dummyEngine.nextPlayer(board);

		board.getPlayers()[0].setCurrentPosition(23);
		dummyEngine.nextPlayerTurn(6, board);
		Assert.assertNotNull(board);
		Assert.assertNotNull(board.getPlayers());
		Assert.assertNotNull(board.getFields());
		Assert.assertTrue(board.isGameRunning());
	}
}
