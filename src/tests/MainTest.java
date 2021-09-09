package tests;


import java.util.Arrays;

import block.Block;
import cube.Cube;
import solvers.RubikSimpleSolver;

public class MainTest {

	public static void main(String[] args) {
		
		test_solver(10000000);
		
		//test_yellowSolver(1000);
	}
	
	
	static void test_solver(int tries) {

		System.out.println("test_solver - simpleSolve -> " + tries + " tries\n");
		
		RubikSimpleSolver rss = new RubikSimpleSolver();
		long t = 0, mvs=0, minM=100, maxM=0; 
		
		int[] nMoves = new int[tries];
		
		boolean error=false;
		for(int i=0; i<tries; i++) {
			Cube cub = new Cube();
			
			cub.mix();
			String mix = Cube.compressSequence(cub.getMovements());
			cub.resetMoves();
			
			long prev = System.currentTimeMillis();
			rss.solve(cub);
			t += (System.currentTimeMillis()-prev);
			int m=cub.getMoves();
			nMoves[i]=m;
			mvs+=m;
			
			if(minM>m) {
				minM=m;
			}
			if(maxM<m) {
				maxM=m;
			}
			
			
			if(!cub.is_bottomSolved()) {
				System.out.println(" > Wrong bottom layer");
				error=true;
			}
			if(!cub.is_middleSolved()) {
				System.out.println(" > Wrong middle layer on try " + (i+1));
				error=true;
			}
			if(!cub.is_topSolved()) {
				System.out.println(" > Wrong top layer");
				error=true;
			}
			
			
			if(error) {
				System.out.println("\nMix: \n" + mix);
				System.out.println("Wrong Solution: \n" + Cube.compressSequence(cub.getMovements()));
				
				System.out.println(cub);
				return;
			}
		}
		Arrays.sort(nMoves);
		

		System.out.println("Total time= " + t + " ms -> ~" + (((double)t)/((double)tries)) + " ms/try");
		System.out.println("Mean " + (((double)mvs)/((double)tries)) + " moves/try");
		System.out.println("Median " + nMoves[tries/2] + " moves");
		System.out.println("Min= " + minM + " // Max= " + maxM);
		
		System.out.println("Succesful exit");
		

	}
	
	
	static void test_yellowSolver(int tries) {


		System.out.println("test_yellowSolver - simpleSolve -> " + tries + " tries\n");

		RubikSimpleSolver rss = new RubikSimpleSolver();
		long t = 0, mvs=0, minM=100, maxM=0; 

		int[] nMoves = new int[tries];

		boolean error=false;
		int solved=0;
		for(int i=0; i<tries; i++) {
			Cube cub = new Cube();

			cub.mix();
			String mix = Cube.compressSequence(cub.getMovements());
			cub.resetMoves();

			long prev = System.currentTimeMillis();
			rss.solve(cub);
			t += (System.currentTimeMillis()-prev);
			int m=cub.getMoves();
			nMoves[i]=m;
			mvs+=m;
			
			if(minM>m) {
				minM=m;
			}
			if(maxM<m) {
				maxM=m;
			}
			
			
			if(!cub.is_bottomSolved()) {
				System.out.println(" > Wrong bottom layer");
				error=true;
			}
			if(!cub.is_middleSolved()) {
				System.out.println(" > Wrong middle layer on try " + (i+1));
				error=true;
			}
			if(cub.is_colorSolved(Block.yellowIdx)) {
				//System.out.println(" > Wrong top layer");
				solved++;
			}else {
				cub.sequenceFromRed("frur\'u\'f\'");
				error=true;
			}
			
			
			if(error) {
				System.out.println("\nMix: \n" + mix);
				System.out.println("Wrong Solution: \n" + Cube.compressSequence(cub.getMovements()));
				
				System.out.println(cub);
				return;
			}
		}
		
		Arrays.sort(nMoves);
		
		System.out.println("Yellow solved / total = " + solved + " / " + tries + " = " + 100*(((double)solved)/((double)tries)) + "%");
		
		
		System.out.println("Total time= " + t + " ms -> ~" + (((double)t)/((double)tries)) + " ms/try");
		System.out.println("Mean " + (((double)mvs)/((double)tries)) + " moves/try");
		System.out.println("Median " + nMoves[tries/2] + " moves");
		System.out.println("Min= " + minM + " // Max= " + maxM);
		
		System.out.println("Succesful exit");
		
		
		
	}

}
