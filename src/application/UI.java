package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

//Essa Classe é responsavel po imprimir o tabuleiro com as peças na tela
public class UI 
{
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
		
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	// https://stackoverflow.com/questions/2979383/java-clear-the-console 
	public static void clearScreen() 
	{     
		System.out.print("\033[H\033[2J");      
		System.out.flush();   
	} 
	
	public static ChessPosition readChessPosition(Scanner sc) 
	{
		//Função para ler uma posição do teclado
		try
		{
			String s = sc.nextLine(); //Recebe o valor informado
			char column = s.charAt(0); //column recebe o primeiro caracter
			
			//Agora vamos recortar o valor digitado
			int row = Integer.parseInt(s.substring(1)); //Recortou a String, row recebe o caracter apartir da posição 1 
			return new ChessPosition(column, row); //Retorna a linha e a coluna
		}
		catch(RuntimeException e)
		{
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
		}
		//O codigo foi envolvido em um try-catch pois caso acontece erro de entrada de dados
	}
	
	public static void printBoard(ChessPiece[][] pieces) 
	{
		//Essa função tem como parametro uma matriz de peças to tipo ChessPiece
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
		//Metodo auxliar para mostrar as peças no tabuleiro
		//Tem como parametro um objeto do tipo chessPiece, em seguida ele valida a existencia de peças
		//Essa logica desse metodo poderia ser no metodo acima, mas foi divido para não ficar muito grande
		
		//Pequna modificação na exibição das peças 
	  	if (piece == null) 
	  	{
            //Caso a peça não exista mostra um traço
	  		System.out.print("-");
        }
	  	
        else 
        {
        	//Aqui esta a modificação, caso a peça instanciada seja a WHITE, mostra ele como branca
        	//Caso a peça instanciada seja a BLACK, mostra a peça em amarelo, isso porque vamos executar
        	//no terminal do git bash e esse tem o fundo preto
        	if (piece.getColor() == Color.WHITE) 
            {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else 
            {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
}

