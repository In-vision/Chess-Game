package chess;

import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class ChessBoard extends GridPane {
	public Space[][] spaces = new Space[8][8];
	// const
	//Commit 2 Isaac
	// last clicked space
	public Space activeSpace = null;
	public static boolean playerTurn = true; //True para blancas. False para negras
	
	public Space previousWhiteMove = null;
	public Space previousBlackMove = null;
	public MoveList prevWhiteMoveList = null;
	public MoveList prevBlackMoveList = null;
	
	public boolean hasPromoted;
	
	public boolean kingInCheck = false;
	/** Sound effects **/
	Media media = new Media(getClass().getResource("/soundEffects/chessMove.mp3").toExternalForm());
	Media enroqueMedia = new Media(getClass().getResource("/soundEffects/enroque.mp3").toExternalForm());
	MediaPlayer player = new MediaPlayer(media);
	
	public ChessBoard(boolean playerIsWhite) {   
		// cause always call super
		super();  
		hasPromoted = false;
		// initialize 8x8 array of spaces
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				boolean light = ((x + y) % 2 != 0); // checkerboard space colors
				this.spaces[x][y] = new Space(light, x, y);

				// if white, add Spaces so ensured bottom left is 0,0
				// if Black, add Spaces so ensured bottom left is 7,7
				if (playerIsWhite) {
					this.add(this.spaces[x][y], x, 7 - y);
				} else {
					this.add(this.spaces[x][y], 7 - x, y);
				}

				// Gets values into event handler
				final int xVal = x;
				final int yVal = y;
				// runs things that happen when a space is clicked

				this.spaces[x][y].setOnAction(e -> onSpaceClick(xVal, yVal));
			}
		}
		// put pieces in start positions
		this.defineStartPositions();
	}

	// Use this to get a space, using GridPane methods will (I think) cause color
	// problems
	public Space getSpace(int x, int y) {
		return this.spaces[x][y];
	}

	public void setActiveSpace(Space s) {
		// Remove style from old active space
		if (this.activeSpace != null) {
			this.activeSpace.getStyleClass().removeAll("chess-space-active");
		}
		this.activeSpace = s;

		// Add style to new active space
		if (this.activeSpace != null) {
			this.activeSpace.getStyleClass().add("chess-space-active");
		}
	}

	// Use this to get a space, using GridPane methods will (I think) cause color
	// problems
	public Space getActiveSpace() {
		return this.activeSpace;
	}

	// prints location of all pieces on the board
	// TODO: Unfinished
	public String toString() {
		String pieceList = "";
		for (int i = 0; i < spaces[0].length; i++) {
			for (int j = 0; j < spaces[1].length; j++) {
				if (spaces[i][j].isOccupied()) {
					// pieceList += spaces[i][j].toString();
				}
			}
		}
		return pieceList;
	}

	// define the starting piece positions
	public void defineStartPositions() {
		// white pieces
		this.spaces[0][0].setPiece(new Rook(true));
		this.spaces[1][0].setPiece(new Knight(true));
		this.spaces[2][0].setPiece(new Bishop(true));
		this.spaces[3][0].setPiece(new Queen(true));
		this.spaces[4][0].setPiece(new King(true));
		this.spaces[5][0].setPiece(new Bishop(true));
		this.spaces[6][0].setPiece(new Knight(true));
		this.spaces[7][0].setPiece(new Rook(true));

		for (int i = 0; i < this.spaces[0].length; i++)
			this.spaces[i][1].setPiece(new Pawn(true));

		// black pieces
		this.spaces[0][7].setPiece(new Rook(false));
		this.spaces[1][7].setPiece(new Knight(false));
		this.spaces[2][7].setPiece(new Bishop(false));
		this.spaces[3][7].setPiece(new Queen(false));
		this.spaces[4][7].setPiece(new King(false));
		this.spaces[5][7].setPiece(new Bishop(false));
		this.spaces[6][7].setPiece(new Knight(false));
		this.spaces[7][7].setPiece(new Rook(false));

		for (int i = 0; i < this.spaces[0].length; i++)
			this.spaces[i][6].setPiece(new Pawn(false));
	}

	public void onSpaceClick(int x, int y) {
		Space clickedSpace = spaces[x][y];
		// if piece is selected && user didn't click on allied piece
		if (this.activeSpace != null && this.activeSpace.getPiece() != null
				&& clickedSpace.getPieceColor() != this.activeSpace.getPieceColor()) {
			MoveInfo p;
			p = new MoveInfo(this.activeSpace.getX(), this.activeSpace.getY(), x, y);
			processMove(p);
			// decouples space from space on board
			this.setActiveSpace(null);
		} else {
			// if there's a piece on the selected square when no active square
			if (spaces[x][y].getPiece() != null) {
				if(ChessBoard.playerTurn) {
					if(spaces[x][y].getPiece().getColor() == "white") {
						this.setActiveSpace(spaces[x][y]);
					}
				}
				else {
					if(spaces[x][y].getPiece().getColor() == "black") {
						this.setActiveSpace(spaces[x][y]);
					}
				}
				// make active square clicked square
//				this.setActiveSpace(spaces[x][y]);
//				disablePieces(playerTurn);
//				playerTurn = !playerTurn;
			}
		}

	}

	
	// Process a move after it has been made by a player
	protected boolean processMove(MoveInfo p) {
		if (moveIsValid(p)) {
			Space oldSpace = spaces[p.getOldX()][p.getOldY()];
			Space newSpace = spaces[p.getNewX()][p.getNewY()];
			previousWhiteMove = (ChessBoard.playerTurn) ? newSpace: previousWhiteMove;
			previousBlackMove = (!ChessBoard.playerTurn)? newSpace: previousBlackMove;
			if(this.hasPromoted && ChessBoard.playerTurn) {
				oldSpace.releasePiece();
				newSpace.setPiece(new Queen(true));
				this.hasPromoted = false;
			}
			else if(this.hasPromoted && !ChessBoard.playerTurn) {
				oldSpace.releasePiece();
				newSpace.setPiece(new Queen(false));
				this.hasPromoted = false;
			}
			else {
				newSpace.setPiece(oldSpace.releasePiece());
			}
//			player.play();
//			player = new MediaPlayer(media);
			if(ChessBoard.playerTurn && (prevWhiteMoveList == MoveList.KING_CASTLE_KINGSIDE ||
					prevWhiteMoveList == MoveList.KING_CASTLE_QUEENSIDE)) {
				player = new MediaPlayer(enroqueMedia);
				player.play();
			}
			else if (!ChessBoard.playerTurn && (prevBlackMoveList == MoveList.KING_CASTLE_KINGSIDE ||
					prevBlackMoveList == MoveList.KING_CASTLE_QUEENSIDE)) {
				player = new MediaPlayer(enroqueMedia);
				player.play();		
			}
			else {
				player = new MediaPlayer(media);
				player.play();
			}
			
//			prevWhiteMoveList == MoveList.KING_CASTLE_KINGSIDE
			ChessBoard.playerTurn = !ChessBoard.playerTurn;
			return true;
		} else // invalid move
		{
			return false;
		}
	}

	public boolean moveIsValid(MoveInfo p) {
		Space oldSpace;
		Space newSpace;
		Piece piece;
		MoveList[] moves;

		// TODO:
		// -Check if player's king is put into check
		// -Pawn logic (Possibly implement as part of pawn's movelist?)
		// -Castling logic

		// Check for null move
		if (p == null) {
			return false;
		}

		// Note: Ideally we would check the space coordinates
		// beforehand, but the try-catch blocks below were
		// easier to implement.

		// Check if oldSpace in range
		try {
			oldSpace = spaces[p.getOldX()][p.getOldY()];
		} catch (NullPointerException e) {
			return false;
		}

		// Check if newSpace in range
		try {
			newSpace = spaces[p.getNewX()][p.getNewY()];
		} catch (NullPointerException e) {
			return false;
		}

		// Check if oldSpace is empty; (no movable piece)
		if (!oldSpace.isOccupied()) {
			return false;
		}

		// Check piece's move list
		piece = oldSpace.getPiece();
		moves = piece.getPieceMoves();
		boolean matchesPieceMoves = false;

		// for Pieces that move more than 1 base move (Bishop, Rook, Queen)
		int multiMoveCount;
		int stretchedMoveX;
		int stretchedMoveY;

		// labels this loop to break out later
		MoveLoop: for (MoveList m : moves){// iterates through multiple times if has multiple possible moves
			multiMoveCount = 1;
			if (piece.usesSingleMove() == false) {
				multiMoveCount = 8;
			}

			boolean hasCollided = false;

			for (int c = 1; c <= multiMoveCount; c++) {
				// if the prior run hit a piece of opponent's color, done with this move
				if (hasCollided) {
					break;
				}

				// stretches a base move out to see if it matches the move made
				stretchedMoveX = m.getX() * c;
				stretchedMoveY = m.getY() * c;
				Space tempSpace;

				// If OOB, go to next move of the piece -- ensures space exists later
				try {
					tempSpace = spaces[p.getOldX() + stretchedMoveX][p.getOldY() + stretchedMoveY];
				} catch (Exception e) {
					break;
				}
				// handles piece collision and capturing
				if (tempSpace.isOccupied()) {
					hasCollided = true;
					boolean piecesSameColor = tempSpace.getPiece().getColor() == oldSpace.getPiece().getColor();
					// stops checking this move if pieces are the same color
					if (piecesSameColor) {
						break;
					}
				}
				prevWhiteMoveList = (ChessBoard.playerTurn) ? m: prevWhiteMoveList;
				prevBlackMoveList = (!ChessBoard.playerTurn)? m: prevBlackMoveList;
				// if stretched move matches made move
				if (p.getGapX() == stretchedMoveX && p.getGapY() == stretchedMoveY) {
					matchesPieceMoves = true;
					if(castleCheck(p, m) == false) {
						return false;
					}
					if (pawnValidityCheck(p, m) == false) {
						return false;
					}

					piece.setHasMoved(true);
					// breaks out of MoveLoop (both loops)
					break MoveLoop;
				}
			}
			
		}
		if (!matchesPieceMoves) {
			return false;
		}
		//tqlo
		return true;
	}

	/************** LÓGICA PARA ENROCARSE **************************/
	/************** aun faltan ciertas validaciones ****************/
	private boolean castleCheck(MoveInfo p, MoveList m){
		String pieceName = this.activeSpace.getPiece().getName();
		if(pieceName.equals("king")) {
			if(m.isEqual(MoveList.KING_CASTLE_KINGSIDE)) {
				Space KingRookSpace = spaces[7][p.getOldY()];
				if(!KingRookSpace.isOccupied() || spaces[5][p.getOldY()].isOccupied()){
					return false;
				}
				else if(KingRookSpace.getPiece().getName().equals("rook")) {
					Space newKingRookSpace = spaces[5][p.getOldY()];
					newKingRookSpace.setPiece(KingRookSpace.releasePiece());
					return true;
				}
			}
			else if(m.isEqual(MoveList.KING_CASTLE_QUEENSIDE)){
				Space QueenRookSpace = spaces[0][p.getOldY()];
				if(!QueenRookSpace.isOccupied() || spaces[1][p.getOldY()].isOccupied() || 
						spaces[3][p.getOldY()].isOccupied()) {
					return false;
				}
				else if(QueenRookSpace.getPiece().getName().equals("rook")){
					Space newQueenRookSpace = spaces[3][p.getOldY()];
					newQueenRookSpace.setPiece(QueenRookSpace.releasePiece());
					return true;
				}
			}
			else{
				return true;
			}
		}
		
		return true;
	}
	
	protected boolean pawnValidityCheck(MoveInfo p, MoveList m) {
		// this should only be called in moveIsValid, so checks are done there
		Space oldSpace = spaces[p.getOldX()][p.getOldY()];
		Space newSpace = spaces[p.getNewX()][p.getNewY()];
		Piece piece = oldSpace.getPiece();

		// If it's not a pawn, it passes
		if (!piece.getName().equals("pawn")) {
			return true;
		}

		/****************** PROMOTION *****************************/
		this.hasPromoted = ((ChessBoard.playerTurn && p.getNewY() == 7) ||
							(!ChessBoard.playerTurn && p.getNewY() == 0))? true : false;
		// if this is a "straight" move
		if (p.getGapX() == 0) {
			// black is negative 1, white is positive 1, for direction later
			int colorMod = p.getGapY() / Math.abs(p.getGapY());

			// if there's a piece in the way for a straight move, don't allow move
			for (int c = 1; c <= Math.abs(p.getGapY()); c++) {
				if (spaces[p.getOldX()][p.getOldY() + (c * colorMod)].isOccupied()) {
					return false;
				}
			}

		} else // if it's a diagonal move
		{
			/************* COMER AL PASO CON EL PEON ****************************/
			Space enemyPawn;
			if(piece.getColor() == "white") {
				
				if(p.getOldY() == 4 && m == MoveList.UP_RIGHT && (p.getOldX() + 1) < 8){
					enemyPawn = spaces[p.getOldX() + 1][p.getOldY()];
					if(enemyPawn.getPiece() != null && enemyPawn.getPiece().getName() == "pawn"
							&& enemyPawn.getX() == previousBlackMove.getX() 
							&& enemyPawn.getY() == previousBlackMove.getY()
							&& !newSpace.isOccupied()
							&& prevBlackMoveList == MoveList.DOUBLE_DOWN) {
						enemyPawn.releasePiece();
						return true;
					}
				}
				else if(p.getOldY() == 4 && m == MoveList.UP_LEFT && (p.getOldX() - 1) >= 0) {
					enemyPawn = spaces[p.getOldX() - 1][p.getOldY()];
					if(enemyPawn.getPiece() != null && enemyPawn.getPiece().getName() == "pawn"
							&& enemyPawn.getX() == previousBlackMove.getX() 
							&& enemyPawn.getY() == previousBlackMove.getY()
							&& !newSpace.isOccupied()
							&& prevBlackMoveList == MoveList.DOUBLE_DOWN) {
						enemyPawn.releasePiece();
						return true;
					}
				}
				
			} else if(piece.getColor() == "black") {
				if(p.getOldY() == 3 && m == MoveList.DOWN_RIGHT && (p.getOldX() + 1) < 8){
					enemyPawn = spaces[p.getOldX() + 1][p.getOldY()];
					if(enemyPawn.getPiece() != null && enemyPawn.getPiece().getName() == "pawn"
							&& enemyPawn.getX() == previousWhiteMove.getX() 
							&& enemyPawn.getY() == previousWhiteMove.getY()
							&& !newSpace.isOccupied()
							&& prevWhiteMoveList == MoveList.DOUBLE_UP) {
						enemyPawn.releasePiece();
						return true;
					}
				}
				else if(p.getOldY() == 3 && m == MoveList.DOWN_LEFT && (p.getOldX() - 1) >= 0) {
					enemyPawn = spaces[p.getOldX() - 1][p.getOldY()];
					if(enemyPawn.getPiece() != null && enemyPawn.getPiece().getName() == "pawn"
							&& enemyPawn.getX() == previousWhiteMove.getX() 
							&& enemyPawn.getY() == previousWhiteMove.getY()
							&& !newSpace.isOccupied()
							&& prevWhiteMoveList == MoveList.DOUBLE_UP) {
						enemyPawn.releasePiece();
						return true;
					}
				}
			}
			// if the target square doesn't have an opposing piece, don't allow move
			if ((!newSpace.isOccupied()) || piece.getColor() == newSpace.getPiece().getColor()) {
				return false;
			}
		}

		return true;
	}

//	private void disablePieces(boolean playerTurn) {
//		for (int i = 0; i < 8; i++) {
//			for (int j = 0; j < 8; j++) {
//				if (spaces[i][j].isOccupied()) {
//					if (playerTurn && spaces[i][j].getPieceColor() == "white")
//						spaces[i][j].setDisable(true);
//					else if(playerTurn && spaces[i][j].getPieceColor() == "black")
//						spaces[i][j].setDisable(false);
//					else if (!playerTurn && spaces[i][j].getPieceColor() == "black")
//						spaces[i][j].setDisable(true);
//					else
//						spaces[i][j].setDisable(false);
//				}
//			}
//		}
//	}
}
