package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
	
	protected boolean isThereOpponetPiece(Position position)
	{
		/*Essa opera��o vai verificar se tem uma pe�a adversaria em uma determinada posi��o
		 * Criado uma variavel auxliar p do tipo ChessPiece em ma determinada posi��o do tabuleiro
		 * Essa opera��o so vai retorna se "p" existir e "p" seja de outra cor, isto � a cor adversaria
		 */
		ChessPiece p = (ChessPiece)getBoard().piece(position); //Variavel auxliar que recebe uma determinada posi��o
		return p!= null && p.getColor() != color; //Retorna se a pe�a existir e sua cor seja diferente dela mesma
	}
	
	
}