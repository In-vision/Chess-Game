package chess;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Space extends Button {
	private int x;
	private int y;
	private Piece piece; 
	private boolean threatenedByWhite;
	private boolean threatenedByBlack;
	private int whiteThreads;
	private int blackThreads;

	public String toString() {
		return "[" + x + "," + y + "]";
	}

	public Space(boolean light, int x, int y, int themeID) {
		super();
		this.x = x;
		this.y = y;
		this.piece = null;
		whiteThreads = blackThreads = 0;
		threatenedByBlack = threatenedByWhite = false;
		this.getStyleClass().add("chess-space");
		if (light)
			if (themeID == 3)
				this.getStyleClass().add("chess-space-translucid-white");
			else
				this.getStyleClass().add("chess-space-light");
		else {
			if (themeID == 1)
				this.getStyleClass().add("chess-space-dark");
			else if (themeID == 2)
				this.getStyleClass().add("chess-space-tournament-green");
			else if (themeID == 3)
				this.getStyleClass().add("chess-space-translucid-grey");

		}
	}

	// returns true if space is occupied
	public boolean isOccupied() {
		return this.piece != null;
	}

	// removes piece from space
	public Piece releasePiece() {
		Piece tmpPiece = this.piece;
		setPiece(null);
		return tmpPiece;
	}

	public Piece getPiece() {
		return this.piece;
	}

	// Sets the piece, draws image of piece on space
	public void setPiece(Piece piece) {
		this.piece = piece;
		if (this.piece != null) {
			this.setGraphic(new ImageView(piece.getImage()));
		} else
			this.setGraphic(new ImageView());
	}

	public String getPieceColor() {
		if (getPiece() != null)
			return getPiece().getColor();
		else // space empty
			return "";
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return this.y;
	}

	public boolean isThreatenedByWhite() {
		return threatenedByWhite;
	}

	public boolean isThreatenedByBlack() {
		return threatenedByBlack;
	}

	public void setThreatenedByWhite(boolean threatenedByWhite) {
		this.threatenedByWhite = threatenedByWhite;
	}

	public void setThreatenedByBlack(boolean threatenedByBlack) {
		this.threatenedByBlack = threatenedByBlack;
	}

	public boolean equals(Space b) {
		if (this.x != b.x || this.y != b.y)
			return false;
		return true;
	}

	public int getWhiteThreads() {
		return whiteThreads;
	}

	public int getBlackThreads() {
		return blackThreads;
	}

	public void incWhiteThreads() {
		this.whiteThreads++;
		if (this.whiteThreads > 0)
			this.threatenedByWhite = true;
	}

	public void setWhiteThreads(int whiteThreads) {
		this.whiteThreads = whiteThreads;
	}

	public void setBlackThreads(int blackThreads) {
		this.blackThreads = blackThreads;
	}

	public void decWhiteThreads() {
		if (this.whiteThreads == 0)
			return;
		this.whiteThreads--;
		if (this.whiteThreads == 0)
			this.threatenedByWhite = false;

	}

	public void incBlackThreads() {
		this.blackThreads++;
		if (this.blackThreads > 0)
			this.threatenedByBlack = true;
	}

	public void decBlackThreads() {
		if (this.blackThreads == 0)
			return;
		this.blackThreads--;
		if (this.blackThreads == 0)
			this.threatenedByBlack = false;
	}

	public boolean threadHandlerAndChecker(boolean giveOrTake, boolean playerTurn) {
		boolean check = false;
		if (giveOrTake) {
			if (playerTurn) {
				incWhiteThreads();
				if (isOccupied() && getPieceColor().equals("black") && getPiece().getName().equals("king")) {
					check = true;
				}
			} else {
				incBlackThreads();
				if (isOccupied() && getPieceColor().equals("white") && getPiece().getName().equals("king")) {
					check = true;
				}
			}
		} else {
			if (playerTurn) {
				decWhiteThreads();
			} else {
				decBlackThreads();
			}
		}
		return check;
	}
}