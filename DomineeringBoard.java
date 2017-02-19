import java.util.HashSet;
import java.util.Iterator;

/**
 * Extends Board
 * @author Simeon Kostadinov
 *
 */
public class DomineeringBoard extends Board<DomineeringMove> {

	private static final Player H = Player.MAXIMIZER;
	private static final Player V = Player.MINIMIZER;

	private final HashSet<DomineeringMove> hMoves;
	private final HashSet<DomineeringMove> vMoves;

	private final boolean array[][];

	public DomineeringBoard() {
		array = new boolean[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				array[i][j] = false;
			}
		}

		hMoves = new HashSet<>();
		vMoves = new HashSet<>();
	}

	public DomineeringBoard(int m, int n) {
		array = new boolean[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				array[i][j] = false;
			}
		}

		hMoves = new HashSet<>();
		vMoves = new HashSet<>();
	}

	private DomineeringBoard(HashSet<DomineeringMove> hMoves, HashSet<DomineeringMove> vMoves, boolean array[][]) {
		assert(disjoint(hMoves,vMoves));
		this.hMoves = hMoves;
		this.vMoves = vMoves;
		this.array = array;
	}

	/**
	 * decide the next player
	 * @return - H or V
	 */
	@Override
	public Player nextPlayer() {
		return ((hMoves.size() + vMoves.size()) % 2 == 0 ? H : V);

	}

	/**
	 * @return - player = H then availableMoves of H, else alailableMoves of V
	 */
	@Override
	public HashSet<DomineeringMove> availableMoves() {
		return (nextPlayer() == H ? complementH() : complementV());
	}

	/**
	 * The value depended on the win 
	 * @return - '1' if H wins, '-1' if V wins , otherwise 0
	 */
	@Override
	int value() {
		boolean check = true;
		
		for(int i =0 ;i< array.length; i++){
			for(int j = 0; j < array[0].length; j++){
				if(array[i][j] == false){
					check = false;
				}
			}
		}
		
		if(check){
			return (nextPlayer() == H ? -1 : 1);
		}
		
		return (DomineeringMove.winsH(array) ? 1 : DomineeringMove.winsV(array) ? -1 : 0);
	}

	/**
	 * Add the move to the current set
	 * @return - DomineeringBoard (with move added)
	 */
	@Override
	Board<DomineeringMove> play(DomineeringMove move) {
		assert(!hMoves.contains(move) && !vMoves.contains(move));
	    if (nextPlayer() == H){
	      
	      return new DomineeringBoard(add(hMoves,move), vMoves, newArray(array, move));
	    }
	    else{
	 
	      return new DomineeringBoard(hMoves, add(vMoves,move), newArray(array, move));
	    }
	    
	}
	
	/**
	 * Because the array is immutable (final) then we save it in new one with the move added
	 * @param array
	 * @param newMove
	 * @return array with move added
	 */
	private boolean[][] newArray(boolean[][] array, DomineeringMove newMove){
		boolean[][] array2 = new boolean[array.length][array[0].length];
		
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				array2[i][j] = array[i][j];
			}
		}
		
		int x1 = newMove.getX1();
		int y1 = newMove.getY1();
		int x2 = newMove.getX2();
		int y2 = newMove.getY2();
		
		array2[x1][y1] = true;
		array2[x2][y2] = true;
		
		return array2;
		
	}
	
	  public String toString() {
		  String Board = "";
		  
		  for(int i = 0; i < array[0].length; i++){
			  for(int j = 0; j < array.length; j++){
				  Board += pm(j, i) + " | ";
			  }
			  Board += "\n----------------------------\n";
		  }
		  
		  return Board;
	  } 

	  private String pm(int i, int j) {
		 String place = "-";
		 
		 Iterator<DomineeringMove> iteratorh = hMoves.iterator();
		 while(iteratorh.hasNext()){
			 DomineeringMove hMove = iteratorh.next();
			 if((hMove.getX1() == i && hMove.getY1() == j) || (hMove.getX2() == i && hMove.getY2() == j)){
				 place = "H";
			 }
		 }
		 
		 Iterator<DomineeringMove> iteratorv = vMoves.iterator();
		 while(iteratorv.hasNext()){
			 DomineeringMove vMove = iteratorv.next();
			 if((vMove.getX1() == i && vMove.getY1() == j) || (vMove.getX2() == i && vMove.getY2() == j)){
				 place = "V";
			 }
		 }
		  
	    return place;
	  }

	  /**
	   * Clone the set because to be immutable
	   * @param a
	   * @param b
	   * @return - set with removed moves
	   */
		static private HashSet<DomineeringMove> intersection(HashSet<DomineeringMove> a, HashSet<DomineeringMove> b) {
			@SuppressWarnings("unchecked")
			HashSet<DomineeringMove> c = (HashSet<DomineeringMove>) a.clone(); // a.clone();
			c.retainAll(b);
			return c;
		}

		static private boolean disjoint(HashSet<DomineeringMove> a, HashSet<DomineeringMove> b) {
			return (intersection(a, b).isEmpty());
		}
	
		/**
		 * Clone the set for immutability
		 * @param a - set
		 * @param b - move
		 * @return - set with added move
		 */
		static private HashSet<DomineeringMove> add(HashSet<DomineeringMove> a, DomineeringMove b) {
			@SuppressWarnings("unchecked")
			HashSet<DomineeringMove> c = (HashSet<DomineeringMove>) a.clone(); // .clone();
			c.add(b);
			return c;
		}
	
		/**
		 * Available moves
		 * @return available moves of H at level
		 */
		private HashSet<DomineeringMove> complementH() {
			HashSet<DomineeringMove> possibleMoves = new HashSet<DomineeringMove>();
			
			for(int i = 0; i < array.length-1; i++){
				for(int j = 0; j < array[0].length; j++){
					if(!array[i][j] && !array[i+1][j]){
						possibleMoves.add(new DomineeringMove(i, j, i + 1, j));
					}
				}
			}
			
			return possibleMoves;
			
			
		}
		
		/**
		 * Available moves
		 * @return available moves of V at level
		 */
		private HashSet<DomineeringMove> complementV() {		
			HashSet<DomineeringMove> possibleMoves = new HashSet<DomineeringMove>();
			
			for(int i = 0; i < array.length; i++){
				for(int j = 0; j < array[0].length-1; j++){
					if(!array[i][j] && !array[i][j + 1]){
						possibleMoves.add(new DomineeringMove(i, j, i, j+1));
					}
				}
			}
			
			return possibleMoves;
		}
	}
