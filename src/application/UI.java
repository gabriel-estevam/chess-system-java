package application;

import chess.ChessPiece;

//Essa Classe � responsavel po imprimir o tabuleiro com as pe�as na tela
public class UI 
{
	public static void printBoard(ChessPiece[][] pieces) 
	{
		//Essa fun��o tem como parametro uma matriz de pe�as to tipo ChessPiece
		for (int i=0; i<pieces.length; i++) 
		{

			System.out.print((8 - i) + " ");

			for (int j=0; j<pieces.length; j++) 
			{

				printPiece(pieces[i][j]);
			}
			
			System.out.println();
		}

		System.out.println("  a b c d e f g h");

	}

	private static void printPiece(ChessPiece piece) 
	{
		//Metodo auxliar para mostrar as pe�as no tabuleiro
		//Tem como parametro um objeto do tipo chessPiece, em seguida ele valida a existencia de pe�as
		//Essa logica desse metodo poderia ser no metodo acima, mas foi divido para n�o ficar muito grande
		if (piece == null) 
		{
			System.out.print("-");
		}


		else 
		{
			System.out.print(piece);
		}
		
		System.out.print(" ");
	}
	
}
