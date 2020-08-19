package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn  extends ChessPiece
{

	public Pawn(Board board, Color color) 
	{
		super(board, color);
		
	}
	
	@Override
	public boolean[][] possibleMove() 
	{
		/*Implementando os movimentos possiveis para o peção*/
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		/*O primeiro if abaixo testa se a peça é um peão branco, caso seja ele so podera
		 * apenas movimentar para cima no tabuleiro*/
		if(getColor() == Color.WHITE)
		{
			
			p.setValues(position.getRow() -1, position.getColumn()); //passa o movimento para acima
			
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
		}
		return mat;
		
	}
	
	@Override
	public String toString()
	{
		return "P";
	}
}
