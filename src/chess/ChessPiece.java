package chess;

import boardgame.Board;
import boardgame.Piece;
//Classe que representa uma caracteristica de uma peça
public abstract class ChessPiece extends Piece 
{
	private Color color; //A peça tem que ter uma cor
	
	public ChessPiece(Board board, Color color) 
	{
		super(board);
		this.color = color;
	}
	
	public Color getColor() 
	{
		return color;
	}
	
}