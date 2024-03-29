package ttt;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Game {
	TTTService ttt;
	Scanner keyboardSc;
	int winner = 0;
	int player = 1;

	public Game() throws Exception {
		ttt = (TTTService) Naming.lookup("//localhost:1099/TTT");
		keyboardSc = new Scanner(System.in);
	}

	public int readPlay() {
		int play;
		do {
			System.out.printf(
					"\nPlayer %d, please enter the number of the square "
							+ "where you want to place your %c (or 0 to refresh the board): \n",
					player, (player == 1) ? 'X' : 'O');
			play = keyboardSc.nextInt();
		} while (play > 9 || play < 0);
		return play;
	}

	public void playGame() throws RemoteException{
		int play;
		boolean playAccepted;

		do {
			player = ++player % 2;
			do {
				System.out.println(ttt.currentBoard());
				play = readPlay();
				if (play != 0) {
					playAccepted = ttt.play(--play / 3, play % 3, player);
					if (!playAccepted)
						System.out.println("Invalid play! Try again.");
				} else
					playAccepted = false;
			} while (!playAccepted);
			winner = ttt.checkWinner();
		} while (winner == -1);
		System.out.println(ttt.currentBoard());
	}

	public void congratulate() {
		if (winner == 2)
			System.out.printf("\nHow boring, it is a draw\n");
		else
			System.out.printf("\nCongratulations, player %d, YOU ARE THE WINNER!\n", winner);
	}

	public static void main(String[] args) throws Exception {

		try{
			Game g = new Game();
	
			System.out.println("Server found.");
			g.playGame();
			g.congratulate();
		}catch (RemoteException e){
			System.out.println("Remote Exception");
		}catch (Exception e){
			System.out.println("Lookup Exception");
		}
	}
}