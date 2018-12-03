package chess;

public class Rook extends Piece {
	public Rook(boolean color) {
		super(color);
	}

	protected MoveList[] getPieceMoves() {
		MoveList[] m = { MoveList.UP, MoveList.RIGHT, MoveList.DOWN, MoveList.LEFT };
		return m;
	}

	protected boolean usesSingleMove() {
		return false;
	}
 
	protected String getName() {
		return "rook";
	}
}
