package chess;

import boardgame.Board;

//Esse classe vai ficar armazenado as regras do jogo, aqui é onde tudo funciona
public class ChessMatch 
{
	private Board board;
	
	public ChessMatch() 
	{
		//Assim que instanciado ja define um tabuleiro 8x8
		board = new Board(8, 8);
	}

	
	public ChessPiece[][] getPieces() 
	{

		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i=0; i<board.getRows(); i++) 
		{

			for (int j=0; j<board.getColumns(); j++) 
			{
				mat[i][j] = (ChessPiece) board.piece(i, j);

			}
		}

		return mat;
	}
}
