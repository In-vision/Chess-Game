package chess;

public class Queen extends Piece {
	public Queen(boolean color) {
		super(color);
	}

	protected MoveList[] getPieceMoves() {
		MoveList[] m = { MoveList.UP, MoveList.UP_RIGHT, MoveList.RIGHT, MoveList.DOWN_RIGHT, MoveList.DOWN,
				MoveList.DOWN_LEFT, MoveList.LEFT, MoveList.UP_LEFT };
		return m;
	}
 
	protected boolean usesSingleMove() {
		return false;
	}

	protected String getName() {
		return "queen";
	}
}