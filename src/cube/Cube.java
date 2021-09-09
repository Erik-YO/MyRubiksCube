package cube;


import block.Block;

import java.util.ArrayList;
import java.util.Random;



public class Cube {



	/*
	 * 
	 * Pretesting Main
	 * 
	 * */
	public static void main(String[] args) {

		Cube c = new Cube();

		System.out.println(c);
		System.out.println(c.punctuation());


		// Notation
		c.sequenceFromGreen("rlfbrlfb");
		System.out.println(c);
		System.out.println(c.punctuation());
		System.out.println(c.getMoves());

		// Notation
		c.sequenceFromOrange("ru\'bdlf\'");
		System.out.println(c);
		System.out.println(c.punctuation());
		System.out.println(c.getMoves());
		c.resetMoves();

		c.mix();

		System.out.println(c);
		System.out.println(c.punctuation());
		System.out.println(c.getMoves());
		
		
		String s="R\'RULLL\'L\'RRRDDDUU\'D";
		System.out.println(s);
		System.out.println(compressSequence(s));
		
		
	}



	/*
	 * 
	 * Atributes
	 * 
	 * */
	private int size;
	private Block blocks[][][];
	private Random rGenerator;
	private int moves;
	private String movements;





	/*
	 * 
	 * 		Initialization
	 * 
	 * */
	private Cube(int size) {

		this.rGenerator = new Random(System.currentTimeMillis());

		this.size=size;
		this.moves=0;
		this.movements="";

		this.blocks = new Block[size][size][size];

		for(int i=0; i<size; i++) {

			for(int j=0; j<size; j++) {

				for(int k=0; k<size; k++) {

					this.blocks[i][j][k] = new Block(size, i, j, k);

				}

			}

		}

	}

	public Cube() {
		this(3);
	}


	private void updateBlockPositions() {

		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				for(int k=0; k<size; k++) {

					this.blocks[i][j][k].setPosition(i, j, k);

				}
			}
		}

	}






	/*
	 * 
	 * 		ROTATION
	 * 
	 * */

	public void left() {	// blue
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[0][i][j].rotate_left();
			}
		}

		int x=0;
		Block aux;

		aux = this.blocks[x][0][1];
		this.blocks[x][0][1]=this.blocks[x][1][0];
		this.blocks[x][1][0]=this.blocks[x][2][1];
		this.blocks[x][2][1]=this.blocks[x][1][2];
		this.blocks[x][1][2]=aux;

		aux = this.blocks[x][0][0];
		this.blocks[x][0][0]=this.blocks[x][2][0];
		this.blocks[x][2][0]=this.blocks[x][2][2];
		this.blocks[x][2][2]=this.blocks[x][0][2];
		this.blocks[x][0][2]=aux;

		updateBlockPositions();
		moves++;
		movements+="L";
	}

	public void right() {	// green
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[this.size-1][i][j].rotate_right();
			}
		}

		int x=this.size-1;
		Block aux;

		aux = this.blocks[x][0][1];
		this.blocks[x][0][1]=this.blocks[x][1][2];
		this.blocks[x][1][2]=this.blocks[x][2][1];
		this.blocks[x][2][1]=this.blocks[x][1][0];
		this.blocks[x][1][0]=aux;

		aux = this.blocks[x][0][0];
		this.blocks[x][0][0]=this.blocks[x][0][2];
		this.blocks[x][0][2]=this.blocks[x][2][2];
		this.blocks[x][2][2]=this.blocks[x][2][0];
		this.blocks[x][2][0]=aux;

		updateBlockPositions();
		moves++;
		movements+="R";
	}

	public void down() {	// white
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[i][0][j].rotate_down();
			}
		}

		int y=0;
		Block aux;

		aux = this.blocks[0][y][0];
		this.blocks[0][y][0]=this.blocks[0][y][2];
		this.blocks[0][y][2]=this.blocks[2][y][2];
		this.blocks[2][y][2]=this.blocks[2][y][0];
		this.blocks[2][y][0]=aux;

		aux = this.blocks[1][y][0];
		this.blocks[1][y][0]=this.blocks[0][y][1];
		this.blocks[0][y][1]=this.blocks[1][y][2];
		this.blocks[1][y][2]=this.blocks[2][y][1];
		this.blocks[2][y][1]=aux;

		updateBlockPositions();
		moves++;
		movements+="D";
	}

	public void up() {	// yellow
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[i][this.size-1][j].rotate_up();
			}
		}

		int y=this.size-1;
		Block aux;

		aux = this.blocks[0][y][0];
		this.blocks[0][y][0]=this.blocks[2][y][0];
		this.blocks[2][y][0]=this.blocks[2][y][2];
		this.blocks[2][y][2]=this.blocks[0][y][2];
		this.blocks[0][y][2]=aux;

		aux = this.blocks[0][y][1];
		this.blocks[0][y][1]=this.blocks[1][y][0];
		this.blocks[1][y][0]=this.blocks[2][y][1];
		this.blocks[2][y][1]=this.blocks[1][y][2];
		this.blocks[1][y][2]=aux;

		updateBlockPositions();
		moves++;
		movements+="U";
	}

	public void front() {	// looking to the red
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[i][j][0].rotate_front();
			}
		}

		int z=0;
		Block aux;

		aux = this.blocks[0][0][z];
		this.blocks[0][0][z]=this.blocks[2][0][z];
		this.blocks[2][0][z]=this.blocks[2][2][z];
		this.blocks[2][2][z]=this.blocks[0][2][z];
		this.blocks[0][2][z]=aux;

		aux = this.blocks[0][1][z];
		this.blocks[0][1][z]=this.blocks[1][0][z];
		this.blocks[1][0][z]=this.blocks[2][1][z];
		this.blocks[2][1][z]=this.blocks[1][2][z];
		this.blocks[1][2][z]=aux;

		updateBlockPositions();
		moves++;
		movements+="F";
	}

	public void back() {	// orange
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[i][j][this.size-1].rotate_back();
			}
		}

		int z=this.size-1;
		Block aux;

		aux = this.blocks[0][0][z];
		this.blocks[0][0][z]=this.blocks[0][2][z];
		this.blocks[0][2][z]=this.blocks[2][2][z];
		this.blocks[2][2][z]=this.blocks[2][0][z];
		this.blocks[2][0][z]=aux;

		aux = this.blocks[0][1][z];
		this.blocks[0][1][z]=this.blocks[1][2][z];
		this.blocks[1][2][z]=this.blocks[2][1][z];
		this.blocks[2][1][z]=this.blocks[1][0][z];
		this.blocks[1][0][z]=aux;

		updateBlockPositions();
		moves++;
		movements+="B";
	}

	public void leftCounter() {	// blue
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[0][i][j].rotate_leftCounter();
			}
		}

		int x=0;
		Block aux;

		aux = this.blocks[x][0][1];
		this.blocks[x][0][1]=this.blocks[x][1][2];
		this.blocks[x][1][2]=this.blocks[x][2][1];
		this.blocks[x][2][1]=this.blocks[x][1][0];
		this.blocks[x][1][0]=aux;

		aux = this.blocks[x][0][0];
		this.blocks[x][0][0]=this.blocks[x][0][2];
		this.blocks[x][0][2]=this.blocks[x][2][2];
		this.blocks[x][2][2]=this.blocks[x][2][0];
		this.blocks[x][2][0]=aux;

		updateBlockPositions();
		moves++;
		movements+="L\'";
	}

	public void rightCounter() {	// green
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[this.size-1][i][j].rotate_rightCounter();
			}
		}

		int x=this.size-1;
		Block aux;

		aux = this.blocks[x][0][1];
		this.blocks[x][0][1]=this.blocks[x][1][0];
		this.blocks[x][1][0]=this.blocks[x][2][1];
		this.blocks[x][2][1]=this.blocks[x][1][2];
		this.blocks[x][1][2]=aux;

		aux = this.blocks[x][0][0];
		this.blocks[x][0][0]=this.blocks[x][2][0];
		this.blocks[x][2][0]=this.blocks[x][2][2];
		this.blocks[x][2][2]=this.blocks[x][0][2];
		this.blocks[x][0][2]=aux;

		updateBlockPositions();
		moves++;
		movements+="R\'";
	}

	public void downCounter() {	// white
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[i][0][j].rotate_downCounter();
			}
		}

		int y=0;
		Block aux;

		aux = this.blocks[0][y][0];
		this.blocks[0][y][0]=this.blocks[2][y][0];
		this.blocks[2][y][0]=this.blocks[2][y][2];
		this.blocks[2][y][2]=this.blocks[0][y][2];
		this.blocks[0][y][2]=aux;

		aux = this.blocks[0][y][1];
		this.blocks[0][y][1]=this.blocks[1][y][0];
		this.blocks[1][y][0]=this.blocks[2][y][1];
		this.blocks[2][y][1]=this.blocks[1][y][2];
		this.blocks[1][y][2]=aux;

		updateBlockPositions();
		moves++;
		movements+="D\'";
	}

	public void upCounter() {	// yellow
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[i][this.size-1][j].rotate_upCounter();
			}
		}

		int y=this.size-1;
		Block aux;

		aux = this.blocks[0][y][0];
		this.blocks[0][y][0]=this.blocks[0][y][2];
		this.blocks[0][y][2]=this.blocks[2][y][2];
		this.blocks[2][y][2]=this.blocks[2][y][0];
		this.blocks[2][y][0]=aux;

		aux = this.blocks[1][y][0];
		this.blocks[1][y][0]=this.blocks[0][y][1];
		this.blocks[0][y][1]=this.blocks[1][y][2];
		this.blocks[1][y][2]=this.blocks[2][y][1];
		this.blocks[2][y][1]=aux;

		updateBlockPositions();
		moves++;
		movements+="U\'";
	}

	public void frontCounter() {	// looking to the red
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[i][j][0].rotate_frontCounter();
			}
		}

		int z=0;
		Block aux;

		aux = this.blocks[0][0][z];
		this.blocks[0][0][z]=this.blocks[0][2][z];
		this.blocks[0][2][z]=this.blocks[2][2][z];
		this.blocks[2][2][z]=this.blocks[2][0][z];
		this.blocks[2][0][z]=aux;

		aux = this.blocks[0][1][z];
		this.blocks[0][1][z]=this.blocks[1][2][z];
		this.blocks[1][2][z]=this.blocks[2][1][z];
		this.blocks[2][1][z]=this.blocks[1][0][z];
		this.blocks[1][0][z]=aux;

		updateBlockPositions();
		moves++;
		movements+="F\'";
	}

	public void backCounter() {	// orange
		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) {
				this.blocks[i][j][this.size-1].rotate_backCounter();
			}
		}

		int z=this.size-1;
		Block aux;

		aux = this.blocks[0][0][z];
		this.blocks[0][0][z]=this.blocks[2][0][z];
		this.blocks[2][0][z]=this.blocks[2][2][z];
		this.blocks[2][2][z]=this.blocks[0][2][z];
		this.blocks[0][2][z]=aux;

		aux = this.blocks[0][1][z];
		this.blocks[0][1][z]=this.blocks[1][0][z];
		this.blocks[1][0][z]=this.blocks[2][1][z];
		this.blocks[2][1][z]=this.blocks[1][2][z];
		this.blocks[1][2][z]=aux;

		updateBlockPositions();
		moves++;
		movements+="B\'";
	}




	
	
	
	

	@SuppressWarnings("unused")
	private static final int leftIdx = 0, leftCIdx = leftIdx+6,
							rightIdx = 1, rightCIdx = rightIdx+6,
							downIdx = 2, downCIdx = downIdx+6,
							upIdx = 3, upCIdx = upIdx+6,
							frontIdx = 4, frontCIdx = frontIdx+6,
							backIdx = 5, backCIdx = backIdx+6;
	
	interface MoveAction{
		void move(Cube c);
	}
	static final private MoveAction[] allPosibleMovements = new MoveAction[] {
			new MoveAction() { public void move(Cube c) { c.left(); }},
			new MoveAction() { public void move(Cube c) { c.right(); }},
			new MoveAction() { public void move(Cube c) { c.down(); }},
			new MoveAction() { public void move(Cube c) { c.up(); }},
			new MoveAction() { public void move(Cube c) { c.front(); }},
			new MoveAction() { public void move(Cube c) { c.back(); }},
			new MoveAction() { public void move(Cube c) { c.leftCounter(); }},
			new MoveAction() { public void move(Cube c) { c.rightCounter(); }},
			new MoveAction() { public void move(Cube c) { c.downCounter(); }},
			new MoveAction() { public void move(Cube c) { c.upCounter(); }},
			new MoveAction() { public void move(Cube c) { c.frontCounter(); }},
			new MoveAction() { public void move(Cube c) { c.backCounter(); }}
	};

	/*
	leftIdx = 0;
	rightIdx = 1;
	downIdx = 2;
	upIdx = 3;
	frontIdx = 4;
	backIdx = 5;
	 */







	/*
	 * 
	 * 		MIX
	 * 
	 * */

	public void mix() {

		int steps=rGenerator.nextInt(6)+20;

		for(int i =0; i<steps; i++) {
			allPosibleMovements[rGenerator.nextInt(allPosibleMovements.length)].move(this);
		}

	}



	/*
	 * 
	 * 		SEQUENCE execution
	 * 
	 * */
	private static ArrayList<String> sequenceToInstructions(String sec){
		ArrayList<String> sl = new ArrayList<String>();
		int i=0;

		while(i<sec.length()) {
			if(i+1<sec.length()) {

				if(sec.charAt(i+1)=='\'') {
					sl.add(sec.substring(i, i+2));
					i++;
				}else {
					sl.add(sec.substring(i, i+1));
				}

			}else {
				sl.add(sec.substring(i));
			}

			i++;
		}

		return sl;
	}
	

	private static ArrayList<Integer> sequenceToMovements(ArrayList<String> instructions){
		ArrayList<Integer> ml = new ArrayList<>();
		
		if(instructions!=null) {

			for(String s: instructions) {
				int indx;

				if(s.contains("l")) {
					indx=leftIdx;
				}else if(s.contains("r")) {
					indx=rightIdx;
				}else if(s.contains("d")) {
					indx=downIdx;
				}else if(s.contains("u")) {
					indx=upIdx;
				}else if(s.contains("f")) {
					indx=frontIdx;
				}else if(s.contains("b")) {
					indx=backIdx;
				}else {
					indx=-1;
				}

				if(indx>=0) {
					if(s.contains("\'")) {
						indx+=6;
					}
					ml.add(indx);
				}
			}
		}

		return ml;
	}
	

	private static ArrayList<Integer> sequenceToMovements(String sec){

		ArrayList<String> instructions = sequenceToInstructions(sec);
		return sequenceToMovements(instructions);

		
	}

	@SuppressWarnings("unchecked")
	private void sequence(ArrayList<?> moves) {

		ArrayList<Integer> ms;

		if(moves != null && !moves.isEmpty())
		{
			if (moves.get(0) instanceof String)
			{
				ms = Cube.sequenceToMovements((ArrayList<String>)moves);
			}
			else if(moves.get(0) instanceof Integer)    
			{
				ms = (ArrayList<Integer>)moves;
			}else {
				return;
			}

			for(int ma:ms) {
				allPosibleMovements[ma].move(this);
			}
		}

	}

	
	/*
	private void sequence(String sec) {
		ArrayList<Integer> moves = Cube.sequenceToMovements(sec.toLowerCase());
		sequence(moves);
	}
	*/
	
	
	
	
	/*
	 * 
	 * SEQUENCE TRANSLATOR
	 * 
	 */
	
	public void sequenceFromRed(String sec) {
		sequence(Cube.sequenceToMovements(sec.toLowerCase()));
	}

	public void sequenceFromGreen(String sec) {
		ArrayList<Integer> orig = Cube.sequenceToMovements(sec.toLowerCase());
		ArrayList<Integer> moves = new ArrayList<Integer>();


		if(orig==null || orig.size()<=0) {
			return;
		}

		for(int i: orig) {
			int m;
			switch(i%6) {
			case leftIdx:
				m=frontIdx;
				break;
			case rightIdx:
				m=backIdx;
				break;
			case frontIdx:
				m=rightIdx;
				break;
			case backIdx:
				m=leftIdx;
				break;  
			default:
				m=i%6;
			};

			if(i>=6) m+=6;
			if(m>=0) moves.add(m);
		}


		sequence(moves);
	}

	public void sequenceFromBlue(String sec) {
		ArrayList<Integer> orig = Cube.sequenceToMovements(sec.toLowerCase());
		ArrayList<Integer> moves = new ArrayList<Integer>();

		if(orig==null || orig.size()<=0) {
			return;
		}

		for(int i: orig) {
			int m;
			switch(i%6) {
			case leftIdx:
				m=backIdx;
				break;
			case rightIdx:
				m=frontIdx;
				break;
			case frontIdx:
				m=leftIdx;
				break;
			case backIdx:
				m=rightIdx;
				break;  
			default:
				m=i%6;
			};

			if(i>=6) m+=6;
			if(m>=0) moves.add(m);
		}
		
		sequence(moves);
	}
	
	public void sequenceFromOrange(String sec) {
		ArrayList<Integer> orig = Cube.sequenceToMovements(sec.toLowerCase());
		ArrayList<Integer> moves = new ArrayList<Integer>();

		if(orig==null || orig.size()<=0) {
			return;
		}

		for(int i: orig) {
			int m;
			switch(i%6) {
			case leftIdx:
				m=rightIdx;
				break;
			case rightIdx:
				m=leftIdx;
				break;
			case frontIdx:
				m=backIdx;
				break;
			case backIdx:
				m=frontIdx;
				break;  
			default:
				m=i%6;
			};

			if(i>=6) m+=6;
			if(m>=0) moves.add(m);
		}
		
		sequence(moves);
	}
	
	public void sequenceFromColor(int col, String sec) {
		if(col==Block.redIdx) {
			sequenceFromRed(sec);
		}else if(col==Block.blueIdx) {
			sequenceFromBlue(sec);
		}else if(col==Block.greenIdx) {
			sequenceFromGreen(sec);
		}else if(col==Block.orangeIdx) {
			sequenceFromOrange(sec);
		}
	}
	
	
	public static String compressSequence(String sec) {
		
		ArrayList<String> ins = Cube.sequenceToInstructions(sec);
		
		int prev=0;
		while(prev!=ins.size()) {
			prev=ins.size();
			ArrayList<String> newIn = new ArrayList<>();
			
			// Find X X' -> 
			for(int i=0; i<ins.size(); i++) {
				
				if(i+1 < ins.size()) {
					if(! ins.get(i).equals(moveCounter(ins.get(i+1)))) {
						newIn.add(ins.get(i));
					}else {
						i++;
					}
				}else {
					newIn.add(ins.get(i));
				}
			}			

			ArrayList<String> newIn2 = new ArrayList<>();
			
			// Find X X X -> X'
			for(int i=0; i<newIn.size(); i++) {
				
				if(i+2<newIn.size()) {
					
					if(newIn.get(i).equals(newIn.get(i+1)) && newIn.get(i+1).equals(newIn.get(i+2))) {
						newIn2.add(moveCounter(newIn.get(i)));
						i+=2;
					}else {
						newIn2.add(newIn.get(i));
					}
					
				}else {
					newIn2.add(newIn.get(i));
				}
				
			}
			
			ins = newIn2;
		}
		
		
		String com="";
		for(String s: ins) com+=s+" ";
		
		return com;
	}
	
	private static String moveCounter(String m) {
		if(m.length()==1) {
			return m + "\'";
		}else if(m.length()==2) {
			return m.substring(0, 1);
		}else {
			return "";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	/*
	 * 
	 * 		CHECKS
	 * 
	 * */
	public boolean equals(Cube c) {

		if(this.size!=c.size) return false;

		for(int i=0; i<this.size; i++) {
			for(int j=0; j<this.size; j++) {
				for(int k=0; k<this.size; k++) {

					if(!this.blocks[i][j][k].equals(c.blocks[i][j][k])) return false;

				}
			}
		}

		return true;
	}

	public boolean is_solved() {
		
		if(!is_colorSolved(Block.yellowIdx)) return false;
		if(!is_colorSolved(Block.blueIdx)) return false;
		if(!is_colorSolved(Block.redIdx)) return false;
		if(!is_colorSolved(Block.greenIdx)) return false;
		if(!is_colorSolved(Block.orangeIdx)) return false;
		if(!is_colorSolved(Block.whiteIdx)) return false;
		
		return true;
		/*
		int k=this.size-1;

		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) 	if(this.blocks[i][k][j].get_up()!=Block.yellowIdx) return false;// yellow
			for(int i=0; i<this.size; i++) 	if(this.blocks[0][j][i].get_left()!=Block.blueIdx) return false;//blue
			for(int i=0; i<this.size; i++) 	if(this.blocks[i][j][0].get_front()!=Block.redIdx) return false;//red
			for(int i=0; i<this.size; i++) 	if(this.blocks[k][j][i].get_right()!=Block.greenIdx) return false;//green
			for(int i=0; i<this.size; i++) 	if(this.blocks[i][j][k].get_back()!=Block.orangeIdx) return false; // orange
			for(int i=0; i<this.size; i++) 	if(this.blocks[i][0][j].get_down()!=Block.whiteIdx) return false;//white
		}

		return true;*/
	}
	
	public boolean is_colorSolved(int color) {
		int[][] fCols = this.getColorColors(color);
		
		boolean solved=true;
		for(int i=0; i<size && solved; i++) {
			for(int j=0; j<size && solved; j++) {
				if(fCols[i][j]!=color) solved=false;
			}
		}
		
		return solved;
	}


	private int punctuation() {

		int k=this.size-1, points=0;

		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) 	if(this.blocks[i][k][j].get_up()==Block.yellowIdx) points++;// yellow
			for(int i=0; i<this.size; i++) 	if(this.blocks[0][j][i].get_left()==Block.blueIdx) points++;//blue
			for(int i=0; i<this.size; i++) 	if(this.blocks[i][j][0].get_front()==Block.redIdx) points++;//red
			for(int i=0; i<this.size; i++) 	if(this.blocks[k][j][i].get_right()==Block.greenIdx) points++;//green
			for(int i=0; i<this.size; i++) 	if(this.blocks[i][j][k].get_back()==Block.orangeIdx) points++; // orange
			for(int i=0; i<this.size; i++) 	if(this.blocks[i][0][j].get_down()==Block.whiteIdx) points++;//white
		}

		return points;
	}

	@SuppressWarnings("unused")
	private int maxPuntuation() {
		return this.size*this.size*6;
	}
	
	public boolean is_bottomSolved() {
		
		Block[][] b = this.getWhiteFace();
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(b[i][j].get_down()!=Block.whiteIdx) return false;
			}
		}
		if(b[0][0].get_left()!=Block.blueIdx || b[0][2].get_left()!=Block.blueIdx) return false;
		if(b[0][2].get_front()!=Block.redIdx || b[2][2].get_front()!=Block.redIdx) return false;
		if(b[2][2].get_right()!=Block.greenIdx || b[2][0].get_right()!=Block.greenIdx) return false;
		if(b[2][0].get_back()!=Block.orangeIdx || b[0][0].get_back()!=Block.orangeIdx) return false;
		
		return true;
	}
	
	public boolean is_middleSolved() {
		
		Block b;
		int y=1;
		
		b=blocks[0][y][0];
		if(b.get_front()!=Block.redIdx || b.get_left()!=Block.blueIdx) return false;

		b=blocks[2][y][0];
		if(b.get_front()!=Block.redIdx || b.get_right()!=Block.greenIdx) return false;

		b=blocks[2][y][2];
		if(b.get_back()!=Block.orangeIdx || b.get_right()!=Block.greenIdx) return false;

		b=blocks[0][y][2];
		if(b.get_back()!=Block.orangeIdx || b.get_left()!=Block.blueIdx) return false;

		return true;
	}


	public boolean is_topSolved() {

		Block[][] b = this.getYellowFace();
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(b[i][j].get_up()!=Block.yellowIdx) return false;
			}
		}
		
		if(b[0][0].get_left()!=Block.blueIdx || b[0][2].get_left()!=Block.blueIdx) return false;
		if(b[0][2].get_back()!=Block.orangeIdx || b[2][2].get_back()!=Block.orangeIdx) return false;
		if(b[2][2].get_right()!=Block.greenIdx || b[2][0].get_right()!=Block.greenIdx) return false;
		if(b[2][0].get_front()!=Block.redIdx || b[0][0].get_front()!=Block.redIdx) return false;

		return true;
	}

	




	/*
	 * 
	 *   OUTPUT - Graphical representation
	 * 
	 * */


	/*
	 * 
	 *   |Am|
	 * --------
	 * Az|Ro|Ve
	 * --------
	 *   |Bl|Na
	 * 
	 * 
	 * */
	public String toString() {

		String s="";
		int k=this.size-1;

		for(int j=k; j>=0; j--) {
			for(int i=0; i<this.size; i++) s+=" ";
			for(int i=0; i<this.size; i++) s+=Block.colorToSpace(this.blocks[i][k][j].get_up());// yellow
			s+="\n";
		}

		for(int j=k; j>=0; j--) {
			// 0j2, 0j1, 0j0 // 0j0, 1j0, 2j0 // 2j0, 2j1, 2j2
			for(int i=k; i>=0; i--) 			s+= Block.colorToSpace(this.blocks[0][j][i].get_left());//blue
			for(int i = 0; i<this.size; i++) 	s+= Block.colorToSpace(this.blocks[i][j][0].get_front());//red
			for(int i = 0; i<this.size; i++) 	s+= Block.colorToSpace(this.blocks[k][j][i].get_right());//green

			for(int i=k; i>=0; i--) 			s+= Block.colorToSpace(this.blocks[i][j][k].get_back()); // orange
			s+="\n";
		}

		for(int j=0; j<this.size; j++) {
			for(int i=0; i<this.size; i++) 		s+=" ";
			for(int i=0; i<this.size; i++) 		s+= Block.colorToSpace(this.blocks[i][0][j].get_down());//white
			//for(int i=k; i>=0; i--) 			s+= Block.colorToSpace(this.blocks[i][j2][k].get_back()); // orange
			s+="\n";
		}



		return s;
	}






	/*
	 * 
	 * 		Setters & Getters
	 * 
	 * */

	public Block getBlock(int x, int y, int z) {

		if(x>=size || y>=size || z>=size || x<0 || y<0 || z<0) return null;

		return this.blocks[x][y][z];

	}

	public Cube copy() {
		Cube c = new Cube(this.size);
		c.moves=this.moves;
		c.movements=this.movements;
		
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				for(int k=0; k<size; k++) {

					c.blocks[i][j][k] = this.blocks[i][j][k].copy();

				}
			}
		}

		return c;
	}

	public int getSize() {
		return this.size;
	}
	
	public int getMoves() {
		return this.moves;
	}
	
	public String getMovements() {
		return this.movements;
	}
	
	public void resetMoves() {
		this.moves=0;
		this.movements="";
	}
	




	/*
	 *        +------+
	 *        |02  22|
	 *        |  UP  |
	 *        |00  20|
	 * +------+------+------+------+
	 * |02  22|02  22|02  22|02  22|
	 * |  LE  |  FR  |  RI  |  BA  |
	 * |00  20|00  20|00  20|00  20|
	 * +------+------+------+------+
	 *        |02  22|
	 *        |  DO  |
	 *        |00  20|
	 *        +------+
	 */
	public int[][] getColorColors(int color){
		int[][] b=null;
		switch(color) {
		case Block.yellowIdx:
			b=getYellowColors();
			break;
		case Block.blueIdx:
			b=getBlueColors();
			break;
		case Block.greenIdx:
			b=getGreenColors();
			break;
		case Block.redIdx:
			b=getRedColors();
			break;
		case Block.whiteIdx:
			b=getWhiteColors();
			break;
		case Block.orangeIdx:
			b=getOrangeColors();
			break;
		}
		return b;
	}
	
	public int[][] getYellowColors(){

		if(this.size!=3) {
			return null;	
		}

		int[][] cols = {
				{blocks[0][2][0].get_up(), blocks[0][2][1].get_up(), blocks[0][2][2].get_up()},
				{blocks[1][2][0].get_up(), blocks[1][2][1].get_up(), blocks[1][2][2].get_up()},
				{blocks[2][2][0].get_up(), blocks[2][2][1].get_up(), blocks[2][2][2].get_up()}
		};

		return cols;
	}

	public int[][] getWhiteColors(){

		if(this.size!=3) {
			return null;	
		}

		int[][] cols = {
				{blocks[0][0][2].get_down(), blocks[0][0][1].get_down(), blocks[0][0][0].get_down()},
				{blocks[1][0][2].get_down(), blocks[1][0][1].get_down(), blocks[1][0][0].get_down()},
				{blocks[2][0][2].get_down(), blocks[2][0][1].get_down(), blocks[2][0][0].get_down()}
		};

		return cols;
	}

	public int[][] getGreenColors(){

		if(this.size!=3) {
			return null;	
		}

		int[][] cols = {
				{blocks[2][0][0].get_right(), blocks[2][1][0].get_right(), blocks[2][2][0].get_right()},
				{blocks[2][0][1].get_right(), blocks[2][1][1].get_right(), blocks[2][2][1].get_right()},
				{blocks[2][0][2].get_right(), blocks[2][1][2].get_right(), blocks[2][2][2].get_right()}
		};

		return cols;
	}

	public int[][] getBlueColors(){

		if(this.size!=3) {
			return null;	
		}

		int[][] cols = {
				{blocks[0][0][2].get_left(), blocks[0][1][2].get_left(), blocks[0][2][2].get_left()},
				{blocks[0][0][1].get_left(), blocks[0][1][1].get_left(), blocks[0][2][1].get_left()},
				{blocks[0][0][0].get_left(), blocks[0][1][0].get_left(), blocks[0][2][0].get_left()}
		};

		return cols;
	}

	public int[][] getRedColors(){

		if(this.size!=3) {
			return null;	
		}

		int[][] cols = {
				{blocks[0][0][0].get_front(), blocks[0][1][0].get_front(), blocks[0][2][0].get_front()},
				{blocks[1][0][0].get_front(), blocks[1][1][0].get_front(), blocks[1][2][0].get_front()},
				{blocks[2][0][0].get_front(), blocks[2][1][0].get_front(), blocks[2][2][0].get_front()}
		};

		return cols;
	}

	public int[][] getOrangeColors(){

		if(this.size!=3) {
			return null;	
		}

		int[][] cols = {
				{blocks[2][0][2].get_back(), blocks[2][1][2].get_back(), blocks[2][2][2].get_back()},
				{blocks[1][0][2].get_back(), blocks[1][1][2].get_back(), blocks[1][2][2].get_back()},
				{blocks[0][0][2].get_back(), blocks[0][1][2].get_back(), blocks[0][2][2].get_back()}
		};

		return cols;
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * 		FACES
	 * 
	 */
	public Block[][] getColorFace(int color){
		Block[][] b=null;
		switch(color) {
		case Block.yellowIdx:
			b=getYellowFace();
			break;
		case Block.blueIdx:
			b=getBlueFace();
			break;
		case Block.greenIdx:
			b=getGreenFace();
			break;
		case Block.redIdx:
			b=getRedFace();
			break;
		case Block.whiteIdx:
			b=getWhiteFace();
			break;
		case Block.orangeIdx:
			b=getOrangeFace();
			break;
		}
		return b;
	}
	
	public Block[][] getYellowFace(){

		if(this.size!=3) {
			return null;	
		}

		Block[][] bl = {
				{blocks[0][2][0], blocks[0][2][1], blocks[0][2][2]},
				{blocks[1][2][0], blocks[1][2][1], blocks[1][2][2]},
				{blocks[2][2][0], blocks[2][2][1], blocks[2][2][2]}
		};

		return bl;
	}

	public Block[][] getWhiteFace(){

		if(this.size!=3) {
			return null;	
		}

		Block[][] bl = {
				{blocks[0][0][2], blocks[0][0][1], blocks[0][0][0]},
				{blocks[1][0][2], blocks[1][0][1], blocks[1][0][0]},
				{blocks[2][0][2], blocks[2][0][1], blocks[2][0][0]}
		};

		return bl;
	}

	public Block[][] getGreenFace(){

		if(this.size!=3) {
			return null;	
		}

		Block[][] bl = {
				{blocks[2][0][0], blocks[2][1][0], blocks[2][2][0]},
				{blocks[2][0][1], blocks[2][1][1], blocks[2][2][1]},
				{blocks[2][0][2], blocks[2][1][2], blocks[2][2][2]}
		};

		return bl;
	}

	public Block[][] getBlueFace(){

		if(this.size!=3) {
			return null;	
		}

		Block[][] bl = {
				{blocks[0][0][2], blocks[0][1][2], blocks[0][2][2]},
				{blocks[0][0][1], blocks[0][1][1], blocks[0][2][1]},
				{blocks[0][0][0], blocks[0][1][0], blocks[0][2][0]}
		};

		return bl;
	}

	public Block[][] getRedFace(){

		if(this.size!=3) {
			return null;	
		}

		Block[][] bl = {
				{blocks[0][0][0], blocks[0][1][0], blocks[0][2][0]},
				{blocks[1][0][0], blocks[1][1][0], blocks[1][2][0]},
				{blocks[2][0][0], blocks[2][1][0], blocks[2][2][0]}
		};

		return bl;
	}

	public Block[][] getOrangeFace(){

		if(this.size!=3) {
			return null;	
		}

		Block[][] bl = {
				{blocks[2][0][2], blocks[2][1][2], blocks[2][2][2]},
				{blocks[1][0][2], blocks[1][1][2], blocks[1][2][2]},
				{blocks[0][0][2], blocks[0][1][2], blocks[0][2][2]}
		};

		return bl;
	}
	
	
	
	/*
	 * Get blocks by type
	 * */
	public Block[] getCenters() {
		Block[] b= {blocks[1][1][0],blocks[1][1][2],
					blocks[0][1][1],blocks[2][1][1],
					blocks[1][0][1],blocks[1][2][1]};
		return b;
	}

	public Block[] getEdges() {
		Block[] b= {blocks[1][0][0],blocks[0][1][0], blocks[1][2][0],blocks[2][1][0],
					blocks[0][0][1],blocks[2][0][1], blocks[0][2][1],blocks[2][2][1],
					blocks[1][0][2],blocks[0][1][2], blocks[1][2][2],blocks[2][1][2]};
		return b;
	}

	public Block[] getVertices() {
		Block[] b= {blocks[0][0][0],blocks[2][0][0], blocks[0][2][0],blocks[2][2][0],
					blocks[0][0][2],blocks[2][0][2], blocks[0][2][2],blocks[2][2][2]};
		return b;
	}
	
	
	
	





}
