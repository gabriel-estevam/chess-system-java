package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece 
{

	public Knight(Board board, Color color) 
	{
		super(board, color);
	}
	
	@Override
	public String toString()
	{
		return "N";
	}

	private boolean canMove(Position position)
	{
		/*Função auxliar para movimentar a peça Cavalo*/
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	@Override
	public boolean[][] possibleMove() 
	{
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		/*Implementando a movimentação do Cavalo*/
		
		Position p = new Position(0,0);
		
		p.setValues(position.getRow() -1, position.getColumn() -2);
		if(getBoard().positionExists(p) && canMove(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getRow() -2, position.getColumn() -1);
		if(getBoard().positionExists(p)  && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getRow() -2, position.getColumn() +1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() -1, position.getColumn() +2);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getRow() +1, position.getColumn() +2 );
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getRow() +2, position.getColumn() +1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
				
		p.setValues(position.getRow() +2, position.getColumn() -1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		
		p.setValues(position.getRow() +1, position.getColumn() -2);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		return mat;
	}
}
