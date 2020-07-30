package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

//Esse classe vai ficar armazenado as regras do jogo, aqui é onde tudo funciona
public class ChessMatch 
{
	private Board board;
	
	public ChessMatch() 
	{
		//Assim que instanciado ja define um tabuleiro 8x8
		board = new Board(8, 8);
		initialSetup();
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
	
	private void initialSetup()
	{
		//Função para inicializar uma partida de xadrez
		//board.placePiece(new Rook(board,Color.WHITE), new Position(9,1)); posição que não existe
		board.placePiece(new King(board,Color.BLACK), new Position(0,4));
		//board.placePiece(new Rook(board,Color.WHITE), new Position(2,1));Posição que ja esta sendo usada
		board.placePiece(new Rook(board,Color.WHITE), new Position(2,1));
	}
}
