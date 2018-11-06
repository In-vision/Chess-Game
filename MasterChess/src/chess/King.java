package chess;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {
	public King(boolean color) {
		// this calls the constructor of Piece
		super(color);
	}

	protected MoveList[] getPieceMoves() {
		MoveList[] m = {};
		ArrayList<MoveList> tmp_moves = new ArrayList<>();
		tmp_moves.addAll(Arrays.asList(MoveList.UP, MoveList.UP_RIGHT, MoveList.RIGHT, MoveList.DOWN_RIGHT, MoveList.DOWN,
				MoveList.DOWN_LEFT, MoveList.LEFT, MoveList.UP_LEFT));
		if(!hasMoved) {
			tmp_moves.add(MoveList.KING_CASTLE_KINGSIDE);
			tmp_moves.add(MoveList.KING_CASTLE_QUEENSIDE);
		}
		m = tmp_moves.toArray(m);
		return m;
	}

	protected boolean usesSingleMove() {
		return true;
	}
 
	protected String getName() {
		return "king";
	}
}
