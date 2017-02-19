import java.util.Scanner;

public class BlackBoxDomineering {
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
		
		DomineeringBoard board = new DomineeringBoard(exactWidth, exactHeight);
		
		/**
		 * Play first player if types "first", play second if typed "second"
		 */
		if(player.equals("first")){
			board.tree().firstPlayer(new BlackBoxDomi("first"));
			player = "first";
			
		}else if(player.equals("second")){
			board.tree().secondPlayer(new BlackBoxDomi("second"));
			player = "second";
			
		}else{
			System.exit(1);
		}
		
	}
}
