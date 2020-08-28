package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece 
{
	private ChessMatch ChessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) 
	{
		super(board, color);
		this.ChessMatch = chessMatch;
	}
	
	@Override
	public String toString()
	{
		return "K";
	}

	private boolean canMove(Position position)
	{
		/*Função auxliar para movimentar a peça Rei*/
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position)
	{
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMove() 
	{
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		/*Implementando a movimentação do Rei
		 * A implementação do movimento do rei é diferente,a regra é:
		 * O rei pode mover apenas uma vez em qualquer uma das direções */
		
		Position p = new Position(0,0);
		//above  movimento para acima
		p.setValues(position.getRow() - 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p) )
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//below movimento para abaixo
		p.setValues(position.getRow() + 1, position.getColumn());
		if(getBoard().positionExists(p)  && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left movimento para esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Right movimento para direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//nw movimento para noroeste
		p.setValues(position.getRow() - 1, position.getColumn() -1 );
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//ne movmento para nordeste
		p.setValues(position.getRow() -1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sw movimento para o sudoeste
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//se movimento para o sudeste
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//#specialmove castling
		
		if( getMoveCount() == 0 && !ChessMatch.getCheck() )
		{
			//#specialmove kingside rook
			
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			if( testRookCastling(posT1) )
			{
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null )
				{
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			//#specialmove queenside rook
			
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
			if( testRookCastling(posT2) )
			{
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null )
				{
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		return mat;
	}
}
