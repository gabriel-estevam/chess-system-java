package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		//Logica basica para rodar a partida
		while(!chessMatch.getCheckMate())
		{
			//Enquanto a partida não der cheque mate ele roda o programa
			try 
			{
				UI.clearScreen();
				//UI.printBoard(chessMatch.getPieces()); trocado abaixo
				UI.printMatch(chessMatch,captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source); //Criado uma matriz boolean para retorna os movimentos possiveis
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				/*Sempre que o movimento de uma peça realizada resultar em uma peça capturada, a peça é add na lista*/
				if(capturedPiece != null)
				{
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPromoted() !=null)
				{
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}
			}
			catch (ChessException e) 
			{
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e)
			{
				System.out.println(e.getMessage());
				sc.nextLine();
			}	
		}
		//deu cheque mate
		UI.clearScreen(); //limpa tela
		UI.printMatch(chessMatch, captured); //e mostra a partida finalizada
	}
}
