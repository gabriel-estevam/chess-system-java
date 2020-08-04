package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece 
{

	public Rook(Board board, Color color) 
	{
		super(board, color);
	}
	
	@Override 
	public String toString()
	{
		return "R";
	}

	@Override
	public boolean[][] possibleMove() 
	{
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		//Implementando a logica de movimento da Torre
		Position p = new Position(0,0); //Posição auxiliar
		
		// above Logica para mover para acima
		p.setValues(position.getRow() -1, position.getColumn());//-1 porque para não pegar a propria linha
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			//Esse while vai ficar verificando se tem espaço livre para movimentar
			mat[p.getRow()][p.getColumn()] = true; //Caso tenha espaço livre retorna true a posição, indicando que pode ser movida
			p.setRow(p.getRow() -1); //Anda para acima (above), isto é diminui uma linha
		}
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			//Caso a posição exista e tenha uma peça adversaria la, então uma peça pode ir para la, sendo assim capturando a peça adversaria
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left logica para mover para esquerda
		p.setValues(position.getRow(), position.getColumn() -1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true; //Livre para ser movimentada
			p.setColumn(p.getColumn() -1 ); //A coluna anda para esquerda, isto é diminui uma coluna
		}
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Right Logica para andar para direita
		p.setValues(position.getRow(), position.getColumn() +1 );
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1); //A coluna anda para direita, isto pe aumenta uma coluna
		}
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//below logica para andar para abaixo
		p.setValues(position.getRow() +1, position.getColumn());
		while(getBoard().positionExists(p)  && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			
		}
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		return mat; //Retorna a matriz
	}
}
