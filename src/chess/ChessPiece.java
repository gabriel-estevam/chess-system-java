package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
	
	protected boolean isThereOpponetPiece(Position position)
	{
		/*Essa operação vai verificar se tem uma peça adversaria em uma determinada posição
		 * Criado uma variavel auxliar p do tipo ChessPiece em ma determinada posição do tabuleiro
		 * Essa operação so vai retorna se "p" existir e "p" seja de outra cor, isto é a cor adversaria
		 */
		ChessPiece p = (ChessPiece)getBoard().piece(position); //Variavel auxliar que recebe uma determinada posição
		return p!= null && p.getColor() != color; //Retorna se a peça existir e sua cor seja diferente dela mesma
	}
	
	
}