
public class CommandLineDomineering {
	
	private static class CommandLineDomi implements MoveChannel<DomineeringMove>{

		/**
		 * read move from the command line
		 */
		@Override
		public DomineeringMove getMove() {
			System.out.println("Enter your move: ");
			int x1 = Integer.parseInt(System.console().readLine());
			int y1 = Integer.parseInt(System.console().readLine());
			int x2 = Integer.parseInt(System.console().readLine());
			int y2 = Integer.parseInt(System.console().readLine());
			
			return new DomineeringMove(x1, y1, x2, y2);
		}

		/**
		 * Print the move the program played
		 */
		@Override
		public void giveMove(DomineeringMove move) {
			System.out.println("I play " + move);
		}

		/**
		 * The game ends with a winner
		 */
		@Override
		public void end(int Value) {
			System.out.println("Game over. The result is " + Value);
		}

		/**
		 * Print the board
		 */
		@Override
		public void comment(String msg) {
		      System.out.println(msg);
		}
	}

	public static void main(String[] args) {
		//DomineeringBoard board = new DomineeringBoard();
		DomineeringBoard2 board2 = new DomineeringBoard2(8, 8);
		//board.tree().firstPlayer(new CommandLineDomi());
	    //board2.tree().secondPlayer(new CommandLineDomi());
	    board2.heuristicTree(2).secondPlayer(new CommandLineDomi());

	}

}
