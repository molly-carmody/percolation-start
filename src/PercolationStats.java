
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Compute statistics on Percolation afte performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */

public class PercolationStats {
	 public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	private static final int BLOCKED = 0;
	private static final int OPEN = 1;
	private static final int FULL = 2;
	private int[] openSites;
	private IPercolate percs;
	private int[][] myGrid;
	private int n;
	// TODO Add methods as described in assignment writeup

	public PercolationStats(int N, int T) {
		if(N <= 0 || T <= 0) {
			throw new IllegalArgumentException("Bad N or T");
		}
		n = N;
		
		myGrid = new int[N][N];
		openSites = new int[T];
		for(int i=0; i<myGrid.length; i++) {
			for(int j=0; j<myGrid.length; j++) {
				myGrid[i][j] = BLOCKED;
			}
		}
	
		for(int k=0; k < T; k++) {
			int j;
			int i;
			percs = new PercolationUF(N);
			while(!percs.percolates()) {
				i = ourRandom.nextInt(N);
				j = ourRandom.nextInt(N);
				if(percs.isOpen(i, j)||percs.isFull(i, j)) {
					percs.open(i, j);
				} 
			openSites[k] = percs.numberOfOpenSites();
				
			}
		}
	}

	public double mean() {

		double add=0;
		for(int l=0; l<openSites.length;l++){
			add += openSites[l];
		}
		return (add/openSites.length);
	}
	public double stddev() {
		double sum = 0;
		for(int k:openSites){
			System.out.println(k);
			double hm = k-mean();
			sum+= hm*hm;
		}
		return Math.sqrt(sum/(n-1.0));



	}

	public double confidenceLow() {

		return mean() - ((1.96*stddev())/Math.sqrt(n));
	}

	public double confidenceHigh() {
		return mean() + ((1.96*stddev())/Math.sqrt(n));
	}

	public static void main(String[] args) {
		PercolationStats percc = new PercolationStats(5,5);
		double mea = percc.mean();
		System.out.println(mea);
		double SD = percc.stddev();
		System.out.println(SD);
		double CL = percc.confidenceLow();
		System.out.println(CL);
		double CH = percc.confidenceHigh();
		System.out.println(CH);
	}
}



