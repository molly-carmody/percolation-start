import java.util.Random;

/**
 * Compute statistics on Percolation afte performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	public static final int BLOCKED = 0;
	public static final int OPEN = 1;
	public static final int FULL = 2;

	private IPercolate perc;
	private int[] openSite;
	public int[][] myGrid;

	// TODO Add methods as described in assignment writeup

	public PercolationStats(int N, int T) {
		if(N <= 0 || T <= 0) {
			throw new IllegalArgumentException("Bad N or T");
		}
		openSite = new int[T];
		myGrid = new int[N][N];

		for(int i=0; i<myGrid.length; i++) {
			for(int j=0; j<myGrid.length; j++) {
				myGrid[i][j] = BLOCKED;
			}
		}
		for(int k=0; k < T; k++) {
			perc = new PercolationUF(N);
			int j;
			int i;
			while(!perc.percolates()) {
				j = ourRandom.nextInt(N);
				i = ourRandom.nextInt(N);
				if(!perc.isOpen(i, j)) {
					perc.open(i, j);
				} else {
					openSite[k] = perc.numberOfOpenSites();
				}
			}
		}
	}
	// perform T independent experiments on an N-by-N grid
	public double mean(){
		double add=0;
		for(int l=0; l<openSite.length;l++){
			add += openSite[l];
		}
		return (add/openSite.length);
		// sample mean of percolation threshold
	}
	public double stddev(){
	//	double mean = mean();
		double sum = 0;
		for(int k:openSite){
			double hm = k-mean();
			sum+= hm*hm;
		}
		return Math.sqrt(sum/(openSite.length-1.0));
	

		// sample standard deviation of percolation threshold
	}
	public double confidenceLow(){
		return mean() - 2*stddev();
		// low  endpoint of 95% confidence interval
	}
	public double confidenceHigh(){
		return mean() + 2*stddev();
		// high endpoint of 95% confidence interval
	}
	public static void main(String[] args){
		PercolationStats percc = new PercolationStats(5,5);
		double mea = percc.mean();
		System.out.println(mea);
		double SD = percc.stddev();
		System.out.println(SD);
		double CL = percc.confidenceLow();
		System.out.println(CL);
		double CH= percc.confidenceHigh();
		System.out.println(CH);


		// print out values for testing &  analysis
	}
}

