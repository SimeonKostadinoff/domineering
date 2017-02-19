import java.util.Scanner;

public class BlackBoxDomineering2 {
	private static class BlackBoxDomi implements MoveChannel<DomineeringMove>{
		
		private String player;
		public BlackBoxDomi(String player){
			this.player = player;
		}
		
		/**
		 * Split the string 'x,y' and send the move 
		 * @return - the move that the opponent plays
		 */
		@Override
		public DomineeringMove getMove() {
			@SuppressWarnings("resource")
			Scanner moves = new Scanner(System.in);
			String move = moves.nextLine();
			String[] separateMoves = {};
			int firstCoord = 0;
			int secondCoord = 0;
			
			separateMoves = move.split(",");
			
			try{
				firstCoord = Integer.parseInt(separateMoves[0]);
				secondCoord = Integer.parseInt(separateMoves[1]);
			}catch(NumberFormatException e){
				System.exit(1);
			}
			
			if(player.equals("first")){
				return new DomineeringMove(firstCoord, secondCoord, firstCoord, secondCoord + 1);
			}else{
				return new DomineeringMove(firstCoord, secondCoord, firstCoord + 1, secondCoord);
			}
		}


		/**
		 * Print the move and flush after that to delete it from the Buffer reader
		 */
		@Override
		public void giveMove(DomineeringMove move) {
			System.out.println(move);
			System.out.flush();
			
		}
		

		/**
		 * Exit when the game ends
		 */
		@Override
		public void end(int Value) {
			System.exit(0);
		}

		@Override
		public void comment(String msg) {
			
		}
		
	}
	
	public static void main(String[] args){
		
		try{
			assert(args.length == 4);
		}catch(AssertionError e){
			System.exit(1);
		}
		
		String player = args[0];
		String blackBoxPlayer = args[1];
		String width = args[2];
		String height = args[3];
		int exactWidth = 0;
		int exactHeight = 0;
		
		try{
			exactWidth = Integer.parseInt(width);
			exactHeight = Integer.parseInt(height);
		}catch(NumberFormatException e){
			System.exit(1);
		}
		
		DomineeringBoard2 board = new DomineeringBoard2(exactWidth, exactHeight);
		
		/**
		 * Play first player if types "first", play second if typed "second"
		 * Play heuristic if the board size (width or height) is more than 5
		 */
		if(player.equals("first")){
			if(exactWidth + exactHeight >= 10){
				board.heuristicTree(2).firstPlayer(new BlackBoxDomi("first"));
			}else{
				board.tree().firstPlayer(new BlackBoxDomi("first"));
			}
			player = "first";
			
		}else if(player.equals("second")){
			if(exactWidth + exactHeight >= 10){
				board.heuristicTree(2).secondPlayer(new BlackBoxDomi("second"));
			}else{
				board.tree().secondPlayer(new BlackBoxDomi("second"));
			}
			player = "second";
			
		}else{
			System.exit(1);
		}
		
	}
}
