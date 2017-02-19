public class DomineeringMove {
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public DomineeringMove(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public int getX1(){
		return this.x1;
	}
	public int getY1(){
		return this.y1;
	}
	public int getX2(){
		return this.x2;
	}
	public int getY2(){
		return this.y2;
	}

	
	public static boolean winsV(boolean array[][]){
		boolean result = true;
		for(int i =0; i < array.length-1; i++){
			for(int j = 0; j < array[0].length; j++){
				if(!array[i][j] && !array[i+1][j]){
					result = false;
				}
			}
		}
		return result;
	}
	
	public static boolean winsH(boolean array[][]){
		boolean result = true;
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[0].length-1; j++){
					if(!array[i][j] && !array[i][j+1]){
						result = false;
				}
			}
		}
		return result;
		
	}
	
	public String toString(){
		return x1 + "," + y1;  
	}
	
	public int hashCode(){
		return 69*this.getX1() + 6969*this.getY1() + 696969*this.getX2() + 69696969*this.getY2();
	}
	
	public boolean equals(Object obj){
		boolean result = false;
		if(!(obj instanceof DomineeringMove)){
			result = false;
		}else{
			DomineeringMove move = (DomineeringMove) obj;
			if(move.getX1() == this.getX1() && move.getY1() == this.getY1() && move.getX2() == this.getX2() && move.getY2() == this.getY2()){
				result = true;
			}
		}
		
		return result;
	}
}
