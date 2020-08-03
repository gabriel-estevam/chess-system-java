package chess;

import boardgame.Board;
import boardgame.Piece;
//Classe que representa uma caracteristica de uma pe�a
public abstract class ChessPiece extends Piece 
{
	private Color color; //A pe�a tem que ter uma cor
	
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