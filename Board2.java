// An abstract two-player game with outcomes in the integers.
// We define a particular game by implementing the abstract methods.
//
// Our approach requires immutable implementations of Board2.  We
// require that the only public constructor builds the initial Board2.
// Other constructors may be used for private purposes.

import java.util.LinkedHashMap;
import java.util.Set;

public abstract class Board2<Move> {
  abstract Player nextPlayer();
  abstract Set<Move> availableMoves(); 
  abstract Set<Move> availableMovesH();
  abstract Set<Move> availableMovesV();
  abstract int value(); 
  abstract Board2<Move> play(Move move);
  private int alfa = 1;
  private int beta = -1;
  private int level;
  private String tree;

  public GameTree2<Move> tree() {
	this.tree = "normal";
    if (availableMoves().isEmpty())
      return new GameTree2<Move>
                    (this, 
                     new LinkedHashMap<Move,GameTree2<Move>>(), 
                     value());
    else
      return (nextPlayer() == Player.MAXIMIZER ? maxTree() : minTree()); 
  }
  
  /**
   * Heuristic function
   * @return - leaf with heuristic value when the level is 0 or maxTree/minTree
   */
  public GameTree2<Move> heuristicTree(int level){
	  this.tree = "heuristic";
	  this.level = level;
	  int heuristicValue = availableMovesH().size() - availableMovesV().size();
	  if(availableMoves().isEmpty())
		  return new GameTree2<Move>
      					(this, 
      					new LinkedHashMap<Move,GameTree2<Move>>(), 
      					value());
	  else if(level == 0){
		  if(availableMovesH().size() < availableMovesV().size()){
			  return new GameTree2<Move>
				(this, 
				new LinkedHashMap<Move,GameTree2<Move>>(), 
				heuristicValue);
		  }else{
			  return new GameTree2<Move>
				(this, 
				new LinkedHashMap<Move,GameTree2<Move>>(), 
				heuristicValue); 
		  }
	  }else{
		  return (nextPlayer() == Player.MAXIMIZER ? maxTree() : minTree()); 
	  }
  }
  

  /**
   * Use the heuristicTree when the board size (width or height) is bigger than 5
   * Play optimal on boards (4x5, 5x5, 5x5) 
   * @return GameTree
   */
  public GameTree2<Move> maxTree() {
    assert(!availableMoves().isEmpty());
    int optimalOutcome = Integer.MIN_VALUE;
    LinkedHashMap<Move,GameTree2<Move>> children 
                 = new LinkedHashMap<Move,GameTree2<Move>>(); 

    for (Move m : availableMoves()) {
    	GameTree2<Move> subtree = null;
    	if(tree.equals("normal")){
    		 subtree = play(m).tree();
    	}else if(tree.equals("heuristic")){
    		subtree = play(m).heuristicTree(level - 1);
    	}
    	
      children.put(m,subtree);
      
      optimalOutcome = Math.max(optimalOutcome,subtree.optimalOutcome());
      
      if(tree.equals("normal") && optimalOutcome == alfa){
    	  break;
      }
      
    }
    return new GameTree2<Move>(this,children,optimalOutcome); 
  }

  /**
   * Use the heuristicTree when the board size (width or height) is bigger than 5
   * Play optimal on boards (4x5, 5x5, 5x5) 
   * @return GameTree
   */
  public GameTree2<Move> minTree() {
    assert(!availableMoves().isEmpty());
    int optimalOutcome = Integer.MAX_VALUE;
    LinkedHashMap<Move,GameTree2<Move>> children 
                 = new LinkedHashMap<Move,GameTree2<Move>>(); 
    
   for (Move m : availableMoves()) {
	   GameTree2<Move> subtree = null;
	   if(tree.equals("normal")){
		   subtree = play(m).tree();
	   }else if(tree.equals("heuristic")){
		   subtree = play(m).heuristicTree(level - 1);
	   }
     
      children.put(m,subtree);
      optimalOutcome = Math.min(optimalOutcome,subtree.optimalOutcome());

      
      if(tree.equals("normal") && optimalOutcome == beta){
    	  break;
      }
    }
    return new GameTree2<Move>(this,children,optimalOutcome); 
  }
  
  public String getTree(){
	  return tree;
  }
}
