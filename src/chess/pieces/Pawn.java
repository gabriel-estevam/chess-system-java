package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn  extends ChessPiece
{
	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) 
	{
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public boolean[][] possibleMove() 
	{
		/*Implementando os movimentos possiveis para o peção
		 * o peão inicialmente pode mover duas casas para frente
		 * apos isso ele pode mover para diagonais esquerda ou direita ou uma casa a frente
		 * */
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		/*O primeiro if abaixo testa se a peça é um peão branco, caso seja ele so podera
		 * apenas movimentar para cima no tabuleiro
		 * 
		 * Essa primeira logica o peção move apenas duas casas para frente
		 * 
		 * */
		if(getColor() == Color.WHITE)
		{
			
			p.setValues(position.getRow() -1, position.getColumn()); //passa o movimento para acima (pra frente)
			
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
			{
				/*Esse if vai testar se o peão pode fazer o movimento para a posição acima*/
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() -2, position.getColumn()); //o peão move duas casas para frente
			
			Position p2 = new Position( position.getRow() - 1, position.getColumn() );
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0)
			{
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() -1, position.getColumn() -1); //movimento para diagonal esquerda
			
			if( getBoard().positionExists(p) && isThereOpponetPiece(p) )
			{
				//Esse if vai verificar se a posição na diagonal é valida e se tem peça adversaria
				//caso atenda as duas condições move
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			p.setValues(position.getRow() -1, position.getColumn() + 1); //Movimento para diagonal direita
			
			if( getBoard().positionExists(p) && isThereOpponetPiece(p) )
			{
				//Esse fi vai verificar se a posição na diagonal direita existe e se tem peça adversaria
				//Caso atenda as duas move
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//#specialmove en passant white
			if(position.getRow() == 3)
			{
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponetPiece(left)  && getBoard().piece(left) == chessMatch.getEnPassantVulnerable() )
				{
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				
				
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponetPiece(right)  && getBoard().piece(right) == chessMatch.getEnPassantVulnerable() )
				{
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
		}
		else
		{
			p.setValues(position.getRow() +1, position.getColumn()); //passa o movimento para acima
			
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) )
			{
				/*Esse if vai testar se o peão pode fazer o movimento para a posição acima*/
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() +2, position.getColumn()); //o peão move duas casas para frente
			
			Position p2 = new Position( position.getRow() +1, position.getColumn() );
			
			if( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0)
			{
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() +1, position.getColumn() -1); //movimento para diagonal esquerda
			
			if( getBoard().positionExists(p) && isThereOpponetPiece(p) )
			{
				//Esse if vai verificar se a posição na diagonal é valida e se tem peça adversaria
				//caso atenda as duas condições move
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			
			p.setValues(position.getRow() +1, position.getColumn() + 1); //Movimento para diagonal direita
			
			if( getBoard().positionExists(p) && isThereOpponetPiece(p) )
			{
				//Esse fi vai verificar se a posição na diagonal direita existe e se tem peça adversaria
				//Caso atenda as duas move
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//#specialmove en passant black
			if(position.getRow() == 4)
			{
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponetPiece(left)  && getBoard().piece(left) == chessMatch.getEnPassantVulnerable() )
				{
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				
				
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponetPiece(right)  && getBoard().piece(right) == chessMatch.getEnPassantVulnerable() )
				{
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString()
	{
		return "P";
	}
}
