package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen  extends ChessPiece
{

	public Queen(Board board, Color color) 
	{
		super(board, color);
	}

	@Override 
	public String toString()
	{
		return "Q";
	}

	@Override
	public boolean[][] possibleMove() 
	{
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		//Implementando a logica de movimento da Rainha
		Position p = new Position(0,0);
		
		// above Logica para mover para acima
		p.setValues(position.getRow() - 1, position.getColumn());//-1 porque para não pegar a propria linha
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left logica para mover para esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1 );
		}
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Right Logica para andar para direita
		p.setValues(position.getRow(), position.getColumn() + 1 );
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//below logica para andar para abaixo
		p.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() -1, position.getColumn() -1);
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			//Esse while vai ficar verificando se tem espaço livre para movimentar
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() -1, p.getColumn() -1); 
			
		}
		
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			//Caso a posição exista e tenha uma peça adversaria la, então uma peça pode ir para la, sendo assim capturando a peça adversaria
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//ne mover para nordeste
		p.setValues(position.getRow() -1, position.getColumn() +1);
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() -1, p.getColumn() +1 );
		}
		
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//se mover para sudeste
		p.setValues(position.getRow() +1, position.getColumn() +1 );
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() +1, p.getColumn() +1); //A coluna anda para direita, isto pe aumenta uma coluna
		}
		
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sw mover para sudoeste
		p.setValues(position.getRow() +1, position.getColumn() -1 );
		
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow() +1, p.getColumn() -1);
		}
		
		if(getBoard().positionExists(p) && isThereOpponetPiece(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat; //Retorna a matriz
		
	}
}
