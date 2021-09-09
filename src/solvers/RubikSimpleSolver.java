package solvers;

import block.Block;
import cube.Cube;



/*
 * 
 *  Implements Human-Like solver for 3x3x3 Rubik's cube
 * 
 * */
public class RubikSimpleSolver implements RubikSolver{


	public static void main(String[] args) {

		RubikSimpleSolver rss=new RubikSimpleSolver();

		Cube c = new Cube();
		System.out.println(c);

		c.mix();
		System.out.println(c);
		System.out.println(Cube.compressSequence(c.getMovements()));

		c.resetMoves();
		rss.solve(c);
		System.out.println(c);
		System.out.println(c.getMoves());
		System.out.println(c.getMovements());
		System.out.println(Cube.compressSequence(c.getMovements()));

		System.out.println(c.is_bottomSolved());

	}


	public RubikSimpleSolver() {

	}

	@Override
	public void solve(Cube c) {

		if(c==null) return;
		if(c.getSize()!=3) return;


		simple_bottom(c);
		simple_middle(c);
		simple_top(c);

	}

	/*
		simpleSolve
			simple_bottom
				simple_bottomEdges
					0-4 x simple_bottomEdge
				simple_bottomVertices
					0-4 x simple_bottomVertex
			simple_middle
				0-4 x simple_middleEdge
	 		simple_top
				simple_topYellow
				simple_topLaterals
					simple_topLateralCorners
					simple_topLateralEdges

	 */

	static private void simple_bottom(Cube c) {
		simple_bottomEdges(c);
		simple_bottomVertices(c);
	}

	static private void simple_middle(Cube c) {

		Block[] allE = c.getEdges();
		Block[] e = new Block[4];
		for(int i=0, j=0; i<allE.length;i++) {

			if((!allE[i].hasColor(Block.yellowIdx)) && (allE[i].getY()>0)) {
				e[j]=allE[i];
				j++;
			}
		}

		// First the ones on top 
		for(int i=0; i<e.length; i++) {

			Block edge=null;
			for(int j=0; j<e.length; j++) {
				if(e[j].getY()==2) {
					edge = e[j];
					j=e.length;
				}
			}

			if(edge==null) {	//There are no more edges on top
				i=e.length;
			}else{
				simple_middleEdge(c, edge);
			}

		}

		for(int i=0; i<e.length; i++) {	

			int[] cs = e[i].getLateralColor();
			int face1 = Block.colorToFace(cs[0]), face2 = Block.colorToFace(cs[1]);		// Get the faces where it should be
			// Compare its colors with the colors of the faces it should be		(would be Block.black if incorrect)
			boolean incorrect = (cs[0] != e[i].get_color(face1)) || (cs[1] != e[i].get_color(face2));	

			// If not in correct place/orientation
			if(incorrect) {
				simple_middleEdge(c, e[i]);
			}
			// With this step, some edges in incorrect positions but middle layer might end on top layer
		}

		// An other sweep for those that were on the middle layer but were changed to the top 
		for(int i=0; i<e.length; i++) {

			Block edge=null;
			for(int j=0; j<e.length; j++) {
				if(e[j].getY()==2) {
					edge = e[j];
					j=e.length;
				}
			}

			if(edge==null) {	//There are no more edges on top
				i=e.length;
			}else{
				simple_middleEdge(c, edge);
			}

		}

		return;
	}

	static private void simple_top(Cube c) {

		// Solve yellow face
		if(!c.is_colorSolved(Block.yellowIdx)) {
			simple_topYellow(c);
		}

		// Solve the laterals of top layer
		if(!c.is_solved()) {
			simple_topLaterals(c);
		}

	}




	static private void simple_bottomEdges(Cube c) {
		int wc = Block.whiteIdx;

		Block[][] g=c.getGreenFace();
		if(!(wc==g[1][0].get_down() && g[1][1].get_right()==g[1][0].get_right())) {// Green not in position
			simple_bottomEdge(c, Block.greenIdx);
		}

		Block[][] b=c.getBlueFace();
		if(!(wc==b[1][0].get_down() && Block.blueIdx==b[1][0].get_right())) {// Blue not in position
			simple_bottomEdge(c, Block.blueIdx);
		}

		Block[][] o=c.getOrangeFace();
		if(!(wc==o[1][0].get_down() && Block.orangeIdx==o[1][0].get_right())) {// Orange not in position
			simple_bottomEdge(c, Block.orangeIdx);
		}

		Block[][] r=c.getRedFace();
		if(!(wc==r[1][0].get_down() && Block.redIdx==r[1][0].get_right())) {// Red not in position
			simple_bottomEdge(c, Block.redIdx);
		}

		return;
	}

	static private void simple_bottomEdge(Cube c, int col){

		Block[] edges = c.getEdges();
		Block e=null;

		for(int i=0; i<edges.length; i++) {
			if(edges[i].hasColor(col) && edges[i].hasColor(Block.whiteIdx)) {
				e=edges[i];
				i=edges.length;
			}
		}

		if(e==null) {
			System.out.println("Edge not found");
			return;
		}

		int y=e.getY();
		if(y==0) {	// Is in the bottom layer

			if(e.get_down()==Block.whiteIdx) {
				c.sequenceFromColor(c.getBlock(e.getX(), 1, e.getZ()).getLateralColor()[0], "ff");
			}else{
				c.sequenceFromColor(c.getBlock(e.getX(), 1, e.getZ()).getLateralColor()[0], "fl\'u\'l");
			}


			// The edge is in the top layer with the white face upwards

		}else if(y==1) {	// Is in the middle layer

			int face;
			// 	Get the color where the white side is facing
			if(e.get_left()==Block.whiteIdx) {	
				face=Block.blueIdx;
			}else if(e.get_front()==Block.whiteIdx) {
				face=Block.redIdx;
			}else if(e.get_right()==Block.whiteIdx) {
				face=Block.greenIdx;
			}else {
				face=Block.orangeIdx;
			}

			Block[][] b=c.getColorFace(face);

			if(b[0][1]==e) {		// The edge is on the left of the selected center
				c.sequenceFromColor(b[1][1].getLateralColor()[0], "l\'u\'l");
			}else if(b[2][1]==e){						// The edge is on the right of the selected center
				c.sequenceFromColor(b[1][1].getLateralColor()[0], "rur\'");
			}

			// The edge is in the top layer with the white face upwards

		}else {		// Is in the top layer

			if(e.get_up()!=Block.whiteIdx) {	// If the white side is not upwards
				c.sequenceFromColor(c.getBlock(e.getX(), y-1, e.getZ()).getLateralColor()[0], "r\'frf\'");
			}

			// The edge is in the top layer with the white face upwards

		}

		Block centr = c.getBlock(e.getX(), 1, e.getZ());
		if(centr.getLateralColor()[0]!=col) {	// Not on top of the desired center

			Block[] centers = c.getCenters();
			Block desCentr=null;
			for(int i=0, found=0; i<centers.length && found<1; i++) {
				if(centers[i].hasColor(col)) {
					desCentr=centers[i];
					found++;
				}
			}
			if(desCentr==null) return;

			if(e.getX()==desCentr.getX() && e.getZ()==desCentr.getZ()) {
			}else if(e.getX()==desCentr.getX() || e.getZ()==desCentr.getZ()) {	//On the oposite side
				c.up();
				c.up();
			}else {

				boolean counter;
				if(e.getX()==0) {
					counter = (desCentr.getZ()==0) ? true : false;
				}else if(e.getX()==2) {
					counter = (desCentr.getZ()==0) ? false : true;
				}else {
					counter = (desCentr.getX()==e.getZ()) ? false : true;
				}

				// 	Rotate
				if(counter) c.upCounter();
				else c.up();

			}
		}		

		// Correct position
		c.sequenceFromColor(col, "ff");
	}



	static private void simple_bottomVertices(Cube c) {


		Block[][] w=c.getWhiteFace();
		Block check = w[2][2];	// White, red, green
		if(!(check.get_down()==Block.whiteIdx && check.get_front()==Block.redIdx && check.get_right()==Block.greenIdx)) {
			simple_bottomVertex(c, Block.redIdx, Block.greenIdx);
		}


		w=c.getWhiteFace();
		check = w[2][0];	// White, green, orange
		if(!(check.get_down()==Block.whiteIdx && check.get_right()==Block.greenIdx && check.get_back()==Block.orangeIdx)) {
			simple_bottomVertex(c, Block.greenIdx, Block.orangeIdx);
		}

		w=c.getWhiteFace();
		check = w[0][0];	// White, orange, blue
		if(!(check.get_down()==Block.whiteIdx && check.get_back()==Block.orangeIdx && check.get_left()==Block.blueIdx)) {
			simple_bottomVertex(c, Block.orangeIdx, Block.blueIdx);
		}

		//System.out.println(c);
		//System.out.println("3/4 of Bottom Vertex:" + Cube.compressSequence(c.getMovements()));
		//c.resetMoves();

		w=c.getWhiteFace();
		check = w[0][2];	// White, blue, red
		if(!(check.get_down()==Block.whiteIdx && check.get_left()==Block.blueIdx && check.get_front()==Block.redIdx)) {
			simple_bottomVertex(c, Block.blueIdx, Block.redIdx);
		}


		//System.out.println("Last Bottom Vertex:" + Cube.compressSequence(c.getMovements()));
	}

	// col1 must be on the left of col2
	static private void simple_bottomVertex(Cube c, int col1, int col2) {

		Block[] vertices = c.getVertices();
		Block v=null;

		for(int i=0; i<vertices.length; i++) {
			if(vertices[i].hasColor(Block.whiteIdx) && vertices[i].hasColor(col1) && vertices[i].hasColor(col2)) {
				v=vertices[i];
				i=vertices.length;
			}
		}

		if(v==null) {
			System.out.println("Vertex not found");
			return;
		}


		if(v.getY()==0) {	// Vertex on bottom layer

			int face=-1;
			for(int i=0; i<6; i++) {
				if(v.get_color(i)==Block.whiteIdx) {
					face=i;
					i=8;
				}
			}
			if(face==-1) return;
			if(face==Block.downIdx) {	// White side downwards

				if(v.getZ()==0) {
					face=Block.frontIdx;
				}else {
					face=Block.backIdx;
				}	
			}

			Block[][] f=c.getColorFace(Block.faceToColor(face));
			if(f[0][0]==v) {	//	Left down
				c.sequenceFromColor(Block.faceToColor(face), "fuf\'");
			}else {				//	Right down
				c.sequenceFromColor(Block.faceToColor(face), "f\'u\'f");
			}


		}else if(v.get_up()==Block.whiteIdx) {	// Vertex on top with white upwards


			int x=v.getX(), z=v.getZ(), dx, dz;
			dx= (col1== Block.blueIdx|| col2==Block.blueIdx) ? 0 : 2;
			dz= (col1== Block.redIdx|| col2==Block.redIdx) ? 0 : 2;

			// Situate vertex on top of its desired position
			if(!(x==dx && z==dz)) {
				if(v.getX()!=dx && v.getZ()!=dz) {
					c.up();
					c.up();
				}else {

					if(x==dz) {
						c.upCounter();
					}else {
						c.up();
					}
				}
			}

			// Vertex on top of its correct position
			c.sequenceFromColor(col1, "f\'uuf");
			// White side not upwards
		}

		//	Vertex on top with white not upwards

		int x=v.getX(), z=v.getZ(), dx, dz;		
		// Desired position
		dx= (col1== Block.blueIdx|| col2==Block.blueIdx) ? 0 : 2;
		dz= (col1== Block.redIdx|| col2==Block.redIdx) ? 0 : 2;

		if(v.get_up()==col1) {	// White on the left of the vertex
			// Turn desired position 1 x Up
			if(dx==dz) {
				dz=(dz-2)*(-1);	// 2->0 & 0->2
			}else {
				dx=(dx-2)*(-1);
			}
		}else {					// White on the right of the vertex
			// Turn desired position 1 x CUp
			if(dx!=dz) {
				dz=(dz-2)*(-1);	// 2->0 & 0->2
			}else {
				dx=(dx-2)*(-1);
			}
		}

		//System.out.println("Position=(" + x + ", " + z + ") -> DesiredTurned=(" + dx + ", " + dz +")");

		// Situate vertex on top of its desired position ( +U / +U' )
		if(!(x==dx && z==dz)) {
			if(x!=dx && z!=dz) {
				//System.out.println("2U");
				c.up();
				c.up();
			}else {
				if(x==dz) {
					//System.out.println("Uc");
					c.upCounter();
				}else {
					//System.out.println("U");
					c.up();
				}
			}
		}

		// Down 
		if(v.get_up()==col1) {
			c.sequenceFromColor(col1, "ru\'r\'");
		}else {
			c.sequenceFromColor(col2, "l\'ul");
		}

		return;
	}



	static private void simple_middleEdge(Cube c, Block e) {

		if(e.getY()==1) {	// If it is in the middle layer but wrong position/orientation
			// Place it on y=2

			int x=e.getX(), z=e.getZ(), colorOnRight;

			if(x==0) {
				if(z==0) colorOnRight=Block.redIdx;
				else colorOnRight=Block.blueIdx;
			}else {
				if(z==0) colorOnRight=Block.greenIdx;
				else colorOnRight=Block.orangeIdx;
			}

			c.sequenceFromColor(colorOnRight, "l\'ulufu\'f\'");



		}

		if(e.getY()==2) {

			int oposCol = e.get_up();
			int[] cols = e.getLateralColor();
			int sideCol = (oposCol==cols[0]) ? cols[1] : cols[0];

			// Place the edge on the oposite side of the color on the top face
			int x=e.getX(), z=e.getZ(), dx, dz;

			if(oposCol==Block.blueIdx || oposCol==Block.greenIdx) {	//Get desired x,z (oposite side of oposCol)
				dz=1;
				if(oposCol==Block.blueIdx) dx=2;
				else dx=0;
			}else {
				dx=1;
				if(oposCol==Block.redIdx) dz=2;
				else dz=0;
			}


			// Turn to situate on the desired position
			if(!(x==dx && z==dz)) {
				if(x==dx || z==dz) {
					c.up();
					c.up();
				}else {
					if(x==dz) c.upCounter();
					else c.up();
				}
			}
			// The edge is on the oposite side of its up-facing color

			// opositeColor on the right of sideColor ?
			boolean right;
			if(oposCol==Block.blueIdx && sideCol==Block.orangeIdx) {
				right=true;
			}else {
				if(oposCol==Block.orangeIdx && sideCol==Block.greenIdx) {
					right=true;
				}else {
					if(oposCol==Block.greenIdx && sideCol==Block.redIdx) {
						right=true;
					}else {
						if(oposCol==Block.redIdx && sideCol==Block.blueIdx) {
							right=true;
						}else {
							right=false;
						}
					}
				}
			}

			if(right){// If the upwards color is on the right of the other color
				c.sequenceFromColor(oposCol, "fu\'f\'u\'l\'ul");
			}else{//else
				c.sequenceFromColor(oposCol, "f\'ufuru\'r\'");
			}

		}

		return;
	}




	
	
	
	
	
	
	
	
	/*
	 * 			TOP LAYER
	 */

	
	static private void simple_topYellow(Cube c) {

		if(patternsToEnd(c)) {
			return;
		}else {
			//System.out.println("Not possible to end top face");
		}
		
		if(patternsAlmostEnd(c)) {
			patternsToEnd(c);
			return;
		}else {
			//System.out.println("Not possible to end top face");
		}

		String s = "frur\'u\'f\'";
		if(!executeIfYellowPattern(c, "*b*uuu*f*", s)) {

			if(!executeIfYellowPattern(c, "*u*uur*f*", s)) {
				executeIfYellowPattern(c, "****u****", s);
				executeIfYellowPattern(c, "*u*uur*f*", s);
			}
			executeIfYellowPattern(c, "*b*uuu*f*", s);

		}


		if(c.is_colorSolved(Block.yellowIdx)) {
			return;
		}
		
		if(patternsToEnd(c)) {
			return;
		}
		
		if(patternsAlmostEnd(c)) {
			patternsToEnd(c);
			return;
		}else {
			//System.out.println("Not possible to end top face");
		}


	}

	
	/*
	 * END
	 * */

	// Solves ~9.5% of situations
	static private boolean patternsToEnd(Cube c) {
		String fr = "rur\'uruur\'",
				frI = "ruur\'u\'ru\'r\'",
				s3 = "frur\'u\'rur\'u\'rur\'u\'f\'",
				sI2 = "furu\'r\'uru\'r\'f\'",
				s2 = "frur\'u\'rur\'u\'f\'",
				sI ="furu\'r\'f\'",
				s = "frur\'u\'f\'";

		if(executeIfYellowPattern(c, "luuuuufur", frI)) {	// Fish 1
			//System.out.println("Fish 1");
			return true;
		}

		if(executeIfYellowPattern(c, "buruuuuuf", fr)) {	// Fish 2
			//System.out.println("Fish 2");
			return true;
		}

		if(executeIfYellowPattern(c, "bubuuufuf", s3)) {	// End 1
			//System.out.println("End 1");
			return true;
		}
		
		if(executeIfYellowPattern(c, "bbruuuffr", sI2)) {	// End 2
			//System.out.println("End 2");
			return true;
		}
		
		if(executeIfYellowPattern(c, "lubuurlff", s2)) {	// End 3
			//System.out.println("End 3");
			return true;
		}
		
		if(executeIfYellowPattern(c, "uuruurufr", sI)) {	// End 4
			//System.out.println("End 4");
			return true;
		}
		
		if(executeIfYellowPattern(c, "lbuuuulfu", s)) {	// End 5
			//System.out.println("End 5");
			return true;
		}

		return false;
	}

	
	/*
	 * ALMOST END
	 * */
	static private boolean patternsAlmostEnd(Cube c) {
		String fr = "rur\'uruur\'",
				sI2 = "furu\'r\'uru\'r\'f\'",
				sI ="furu\'r\'f\'",
				s = "frur\'u\'f\'";

		executeIfYellowPattern(c, "bbbuuufff", s);
		if(executeIfYellowPattern(c, "bubuuulur", fr)) {	// End 1
			//System.out.println("End 1");
			return true;
		}
		
		executeIfYellowPattern(c, "lbruuulfr", s);
		if(executeIfYellowPattern(c, "buuuuufuu", fr)) {	// End 2
			//System.out.println("End 2");
			return true;
		}
		
		executeIfYellowPattern(c, "ubuuuuufu", s);
		if(executeIfYellowPattern(c, "uuuuuufuf", fr)) {	// End 3
			//System.out.println("End 3");
			return true;
		}
		
		if(executeIfYellowPattern(c, "uuruuufuu", fr)) {	// End 4
			//System.out.println("End 4");
			return true;
		}
		
		if(executeIfYellowPattern(c, "ubruuulff", s)) {	// End 5
			//System.out.println("End 5");
			return true;
		}

		if(executeIfYellowPattern(c, "ubruuuffu", s)) {	// End 6
			//System.out.println("End 6");
			return true;
		}
		
		if(executeIfYellowPattern(c, "ubuuuufff", s)) {	// End 7
			//System.out.println("End 7");
			return true;
		}
		
		if(executeIfYellowPattern(c, "lbruuufff", s)) {	// End 8
			//System.out.println("End 8");
			return true;
		}
		
		if(executeIfYellowPattern(c, "lbbuuuufr", s)) {	// End 9
			//System.out.println("End 9");
			return true;
		}
		
		if(executeIfYellowPattern(c, "bbuuuuufr", s)) {	// End 10
			//System.out.println("End 10");
			return true;
		}
		
		if(executeIfYellowPattern(c, "bubuurlfr", sI)) {	// End 11
			//System.out.println("End 11");
			return true;
		}
		
		if(executeIfYellowPattern(c, "buruurlfu", sI)) {	// End 12
			//System.out.println("End 12");
			return true;
		}

		if(executeIfYellowPattern(c, "uubuurlfu", sI)) {	// End 13
			//System.out.println("End 13");
			return true;
		}
		
		if(executeIfYellowPattern(c, "bubuurufu", sI)) {	// End 14
			//System.out.println("End 14");
			return true;
		}
		
		if(executeIfYellowPattern(c, "uuuuurfff", sI)) {	// End 15
			//System.out.println("End 15");
			return true;
		}

		if(executeIfYellowPattern(c, "luruurfff", sI)) {	// End 16
			//System.out.println("End 16");
			return true;
		}
		
		if(executeIfYellowPattern(c, "luuuuruff", sI)) {	// End 17
			//System.out.println("End 17");
			return true;
		}
		
		if(executeIfYellowPattern(c, "luuuurffr", sI)) {	// End 18
			//System.out.println("End 18");
			return true;
		}
		
		if(executeIfYellowPattern(c, "lbbuuuffu", sI2)) {	// End 19
			//System.out.println("End 19");
			return true;
		}
		
		if(executeIfYellowPattern(c, "lbruuuufu", sI2)) {	// End 20
			//System.out.println("End 20");
			return true;
		}
		

		if(executeIfYellowPattern(c, "bbuuuulff", sI2)) {	// End 21
			//System.out.println("End 21");
			return true;
		}

		if(executeIfYellowPattern(c, "ubbuuuuff", s)) {	// End 21
			//System.out.println("End 21");
			return true;
		}
		
		return false;
	}
	
	
	
	
	/*
	 * CHECK YELLOW PATTERNS
	 * */
	
	// l, b, r, f, u;
	//	012
	//	345
	//	678
	
	static private boolean executeIfYellowPattern(Cube c, String pattern, String sequence) {
		
		int color = checkYellowPattern(c.getYellowFace(), pattern);
		
		if(color == Block.blackIdx) return false;
		
		c.sequenceFromColor(color, sequence);
		
		return true;
	}
	
	static private int checkYellowPattern(Block[][] fac, String pat) {
		
		if(pat.length()!=9) return Block.blackIdx;
		
		char[] p = pat.toLowerCase().toCharArray();
		
		int[] ans= {Block.redIdx, Block.blueIdx, Block.orangeIdx, Block.greenIdx};


		for(short i=0; i<4; i++) {
			
			boolean correct=true;
			for(short j=0; j<9 && correct; j++) {
				Block b=fac[j%3][((j/3)-2)*(-1)];
				
				switch(p[j]) {
				case 'l':
					correct = (b.get_left()==Block.yellowIdx);
					break;
				case 'r':
					correct = (b.get_right()==Block.yellowIdx);
					break;
				case 'u':
					correct = (b.get_up()==Block.yellowIdx);
					break;
				case 'b':
					correct = (b.get_back()==Block.yellowIdx);
					break;
				case 'f':
					correct = (b.get_front()==Block.yellowIdx);
					break;
				default:
					correct=true;
				}


			}
			if(correct) return ans[i];
			
			p=turnPattern(p);
		}
		
		return Block.blackIdx;
	}
	
	// Clockwise
	static private char[] turnPattern(char[] ori) {
		char[] des = new char[9];
		
		des[0]=turnPatternLetter(ori[6]);
		des[1]=turnPatternLetter(ori[3]);
		des[2]=turnPatternLetter(ori[0]);
		des[3]=turnPatternLetter(ori[7]);
		des[5]=turnPatternLetter(ori[1]);
		des[6]=turnPatternLetter(ori[8]);
		des[7]=turnPatternLetter(ori[5]);
		des[8]=turnPatternLetter(ori[2]);
		
		return des;
	}
	
	static private char turnPatternLetter(char l) {

		switch(l) {
		case 'l':
			return 'b';
		case 'b':
			return 'r';
		case 'r':
			return 'f';
		case 'f':
			return 'l';
		case 'u':
			return 'u';
		}

		return '*';
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	




	



	static private void simple_topLaterals(Cube c){

		simple_topLateralCorners(c);
		
		simple_topLateralEdges(c);
		
		simple_topOrientation(c);
	}

	static private void simple_topLateralCorners(Cube c) {
		Block[][] face = c.getYellowFace();

		int lu= face[0][2].get_left(),
			ul= face[0][2].get_back(),
			ur= face[2][2].get_back(),
			ru= face[2][2].get_right(),
			rd= face[2][0].get_right(),
			dr= face[2][0].get_front(),
			dl= face[0][0].get_front(),
			ld= face[0][0].get_left();
		
		// Already correct
		if(lu==ld && ru==rd && ul==ur) return;
		
		// Already one side
		int col=Block.blackIdx;
		if(ul==ur) {col=Block.redIdx;}
		else if(ru==rd) {col=Block.blueIdx;}
		else if(dl==dr) {col=Block.orangeIdx;}
		else if(lu==ld) {col=Block.greenIdx;}
		
		String tlc= "r\'fr\'bbrf\'r\'bbrr";
		
		if(col!=Block.blackIdx) {
			// End
			c.sequenceFromColor(col, tlc);
			return;

		}else {

			c.sequenceFromColor(Block.redIdx, tlc);

			face = c.getYellowFace();
			lu= face[0][2].get_left();
			ul= face[0][2].get_back();
			ur= face[2][2].get_back();
			ru= face[2][2].get_right();
			rd= face[2][0].get_right();
			dr= face[2][0].get_front();
			dl= face[0][0].get_front();
			ld= face[0][0].get_left();
			
			if(ul==ur) {col=Block.redIdx;}
			else if(ru==rd) {col=Block.blueIdx;}
			else if(dl==dr) {col=Block.orangeIdx;}
			else if(lu==ld) {col=Block.greenIdx;}
			
			if(col!=Block.blackIdx) {
				// End
				c.sequenceFromColor(col, tlc);
			}

			
		}


		
		return;
	}


	static private void simple_topLateralEdges(Cube c) {

		Block[][] face = c.getYellowFace();

		int l= face[0][1].get_left(),
				ul= face[0][2].get_back(),
				u= face[1][2].get_back(),
				ru= face[2][2].get_right(),
				r= face[2][1].get_right(),
				dr= face[2][0].get_front(),
				d= face[1][0].get_front(),
				ld= face[0][0].get_left();


		// Already correct
		if(l==ld && ru==r && ul==u) return;

		String tlecw ="ffulr\'ffrl\'uff", tleccw="ffu\'lr\'ffrl\'u\'ff";
		
		// Already one side
		int col=Block.blackIdx;
		boolean cw=true;
		if(ul==u) {
			col=Block.redIdx;
			cw = (l==ru);
		} else if(ru==r) {
			col=Block.blueIdx;
			cw = (u==dr);
		} else if(d==dr) {
			cw = (r==ld);
			col=Block.orangeIdx;
		} else if(l==ld) {
			col=Block.greenIdx;
			cw = (d==ul);
		}else {

			c.sequenceFromRed(tlecw);
			
			if(ul==u) {
				col=Block.redIdx;
				cw = (l==ru);
			} else if(ru==r) {
				col=Block.blueIdx;
				cw = (u==dr);
			} else if(d==dr) {
				cw = (r==ld);
				col=Block.orangeIdx;
			} else if(l==ld) {
				col=Block.greenIdx;
				cw = (d==ul);
			}
		}


		String def;

		if(col!=Block.blackIdx) {
			def = cw ? tlecw : tleccw;
			c.sequenceFromColor(col, def);
		}
		

		
		face = c.getYellowFace();

		l= face[0][1].get_left();
		ul= face[0][2].get_back();
		u= face[1][2].get_back();
		ru= face[2][2].get_right();
		r= face[2][1].get_right();
		dr= face[2][0].get_front();
		d= face[1][0].get_front();
		ld= face[0][0].get_left();

		// Already correct
		if(l==ld && ru==r && ul==u) return;

		// Already one side
		col=Block.blackIdx;
		cw=true;
		if(ul==u) {
			col=Block.redIdx;
			cw = (l==ru);
		} else if(ru==r) {
			col=Block.blueIdx;
			cw = (u==dr);
		} else if(d==dr) {
			cw = (r==ld);
			col=Block.orangeIdx;
		} else if(l==ld) {
			col=Block.greenIdx;
			cw = (d==ul);
		}



		if(col!=Block.blackIdx) {
			def = cw ? tlecw : tleccw;
			c.sequenceFromColor(col, def);
		}

		return;
	}

	
	static private void simple_topOrientation(Cube c) {
		
		int col = c.getBlock(1, 2, 0).get_front();
		
		switch(col) {
		case Block.redIdx:
			break;
		case Block.greenIdx:
			c.upCounter();
			break;
		case Block.blueIdx:
			c.up();
			break;
		case Block.orangeIdx:
			c.up();
			c.up();
			break;
		}
		
		return;
	}



}











