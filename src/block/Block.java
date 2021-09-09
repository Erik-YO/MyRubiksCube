package block;


public class Block {

	public static void main(String[] args) {

	}


	private int[] colors;
	private int[] position;
	private Type type;
	
	// Negro, azul, verde, blanco, amarillo, rojo, naranja
	//private static final int[][] referenceColors = {{0, 0, 0}, {0,0,255}, {0,128,0}, {255, 255, 255}, {255,255,0}, {255,0,0}, {255,165,0}};
	public static final int blackIdx = 0,
							blueIdx = 1,
							greenIdx = 2,
							whiteIdx = 3,
							yellowIdx = 4,
							redIdx = 5,
							orangeIdx = 6;

	public static final int leftIdx = 0,
							rightIdx = 1,
							downIdx = 2,
							upIdx = 3,
							frontIdx = 4,
							backIdx = 5;
	
	public static enum Type{
		CENTER, EDGE, VERTEX
	}
	
	
	
	
	/*
	 * 
	 * 		Initialization
	 * 
	 * */

	public Block(int size, int i, int j, int k) {
		this.position = new int[3];
		this.position[0]=i;
		this.position[1]=j;
		this.position[2]=k;

		this.colors = new int[6];
		if(size>0) {
			this.setColors(size);
			this.setType();
		}
	}
	
	private Block(int i, int j, int k) {
		this(-1, i, j, k);
	}


	private void setColors(int size) {
		int x=position[0], y=position[1], z=position[2];
		
		for(int i=0; i<6; i++)
			this.colors[i] = blackIdx; // Negro

		if(x==0) {
			colors[leftIdx] = blueIdx;	// Azul
		}
		if(x==size-1) {
			colors[rightIdx] = greenIdx;	// Verde
		}

		if(y==0) {
			colors[downIdx] = whiteIdx;	// Blanco
		}
		if(y==size-1) {
			colors[upIdx] = yellowIdx;	// Amarillo
		}

		if(z==0) {
			colors[frontIdx] = redIdx;	// Rojo
		}
		if(z==size-1) {
			colors[backIdx] = orangeIdx;	// Naranja
		}

	}
	
	private void setType() {
		
		int i=this.position[0], j=this.position[1], k= this.position[2];
		
		if(!(i==1 || j==1 || k==1)) {
			this.type=Type.VERTEX;
		}else if((i==1&&j==1) || (i==1&&k==1) || (j==1&&k==1)) {
			this.type=Type.CENTER;
		}else {
			this.type=Type.EDGE;
		}
		
	}
	
	
	
	
	
	/*
	 * 
	 * 		Color Rotation
	 * 
	 * */
	public void rotate_left() {	// blue
		int aux=colors[frontIdx];
		colors[frontIdx]=colors[upIdx];
		colors[upIdx]=colors[backIdx];
		colors[backIdx]=colors[downIdx];
		colors[downIdx]=aux;
	}
	
	public void rotate_right() {	// green
		int aux=colors[frontIdx];
		colors[frontIdx]=colors[downIdx];
		colors[downIdx]=colors[backIdx];
		colors[backIdx]=colors[upIdx];
		colors[upIdx]=aux;
	}

	public void rotate_down() {	// white
		int aux=colors[frontIdx];
		colors[frontIdx]=colors[leftIdx];
		colors[leftIdx]=colors[backIdx];
		colors[backIdx]=colors[rightIdx];
		colors[rightIdx]=aux;
	}
	
	public void rotate_up() {	// yellow
		int aux=colors[frontIdx];
		colors[frontIdx]=colors[rightIdx];
		colors[rightIdx]=colors[backIdx];
		colors[backIdx]=colors[leftIdx];
		colors[leftIdx]=aux;
	}

	public void rotate_front() {	// looking to the red
		int aux=colors[upIdx];
		colors[upIdx]=colors[leftIdx];
		colors[leftIdx]=colors[downIdx];
		colors[downIdx]=colors[rightIdx];
		colors[rightIdx]=aux;
	}
	
	public void rotate_back() {	// orange
		int aux=colors[upIdx];
		colors[upIdx]=colors[rightIdx];
		colors[rightIdx]=colors[downIdx];
		colors[downIdx]=colors[leftIdx];
		colors[leftIdx]=aux;
	}
	
	public void rotate_leftCounter() {	// blue
		this.rotate_right();
	}
	
	public void rotate_rightCounter() {	// green
		this.rotate_left();
	}

	public void rotate_downCounter() {	// white
		this.rotate_up();
	}
	
	public void rotate_upCounter() {	// yellow
		this.rotate_down();
	}

	public void rotate_frontCounter() {	// looking to the red
		this.rotate_back();
	}
	
	public void rotate_backCounter() {	// orange
		this.rotate_front();
	}
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * 		Position Control
	 * 
	 * */
	public void setPosition(int x, int y, int z) {
		position[0]=x;
		position[1]=y;
		position[2]=z;
	}
	
	public int getX() {
		return position[0];
	}
	
	public int getY() {
		return position[1];
	}
	
	public int getZ() {
		return position[2];
	}
	
	
	
	

	
	
	
	/*
	 * 
	 *    	OUTPUT - Graphical representation
	 * 
	 * */
	
	
	/*
	 * 
	 * 		Get color index
	 * 
	 * */
	public int get_up() {
		return this.colors[upIdx];
	}
	public int get_down() {
		return this.colors[downIdx];
	}
	public int get_left() {
		return this.colors[leftIdx];
	}
	public int get_right() {
		return this.colors[rightIdx];
	}
	public int get_front() {
		return this.colors[frontIdx];
	}
	public int get_back() {
		return this.colors[backIdx];
	}
	
	
	public Block copy() {
		Block b = new Block(this.position[0], this.position[1], this.position[2]);
		
		for(int i=0; i<this.colors.length; i++) {
			b.colors[i]=this.colors[i];
		}
		
		return b;
	}
	
	
	
	
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001b[48;5;220m";//= "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	public static final String ANSI_ORANGE_BACKGROUND = "\u001b[48;5;208m";//202
	
	static public String colorToSpace(int c) {
		
		if(c == blueIdx) return ANSI_BLUE_BACKGROUND + " " + ANSI_RESET;
		if(c == greenIdx) return ANSI_GREEN_BACKGROUND + " " + ANSI_RESET;
		if(c == orangeIdx) return ANSI_ORANGE_BACKGROUND + " " + ANSI_RESET;
		if(c == redIdx) return ANSI_RED_BACKGROUND + " " + ANSI_RESET;
		if(c == whiteIdx) return ANSI_WHITE_BACKGROUND + " " + ANSI_RESET;
		if(c == yellowIdx) return ANSI_YELLOW_BACKGROUND + " " + ANSI_RESET;
		

		return " ";
	}
	




	public String toString() {

		return "[]";
	}
	
	public Type getType() {
		return this.type;
	}

	
	// Specially useful to get centers' color
	public int[] getLateralColor() {
		int[] col;
		if(this.type==Type.CENTER) {
			col=new int[1];
			boolean found=false;
			for(int i=0; i<this.colors.length && !found;i++)
				if(this.colors[i]!=blackIdx) {
					col[0]=this.colors[i];
					found=true;
				}
		}else if(this.type==Type.EDGE) {
			col=new int[2];
			int found=0;
			for(int i=0; i<this.colors.length && found<2;i++)
				if(this.colors[i]!=blackIdx) {
					col[found]=this.colors[i];
					found++;
				}
		}else if(this.type==Type.VERTEX) {
			col=new int[3];
			int found=0;
			for(int i=0; i<this.colors.length && found<3;i++)
				if(this.colors[i]!=blackIdx) {
					col[found]=this.colors[i];
					found++;
				}
		}else {
			return null;
		}
		return col;
	}
	
	public int get_color(int face) {
		if(face<0 || face>=6) return blackIdx;
		return colors[face];
	}

	static public int colorToFace(int col) {
		return col-1;
	}

	static public int faceToColor(int fac) {
		return fac+1;
	}


	/*
	 * 
	 * 		CHECKS
	 * 
	 * */
	public boolean equals(Block b) {
		
		if(this.type!=b.type) return false;
		
		for(int i=0; i<this.colors.length; i++) {
			if(this.colors[i]!=b.colors[i]) return false;
		}
		
		for(int i=0; i<this.position.length; i++) {
			if(this.position[i]!=b.position[i]) return false;
		}
		
		return true;
	}
	
	public boolean hasColor(int col) {
		for(int c: this.colors) {
			if(c==col) return true;
		}
		return false;
	}
	
	
	
	
	
	
	

}
