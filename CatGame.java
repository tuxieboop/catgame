import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;

public class CatGame{
	int n;
	public EdgeWeightedGraph board;
	boolean[] marked;
	int cati;
	int catj;
	int catpos;
	double[] distTo;
	CatEdge[] edgeTo;
	
	public CatGame(int size){
		n = size;
		board = setUp(size);
		marked = new boolean[size*size + 1];
		cati = size/2;
		catj = size/2;
		catpos = cati*size + catj;
		distTo = new double[size*size + 1];
		distTo[0] = 0;
		for(int i = 1; i < size*size + 1; i++){
			distTo[i] = Double.POSITIVE_INFINITY;
		}
		edgeTo = new CatEdge[size*size + 1];
		System.out.println(board.toString());
	}
	public void markTile(int row, int col){
		marked[row*n + col] = true;
	}
	public boolean marked(int row, int col){
		return marked[row*n + col];
	}
	public int[] getCatTile(){
		return ans;
	}
	public boolean catHasEscaped(){
		return marked[n*n];
	}
	public boolean catIsTrapped(){
		return distTo[size*size] == Double.POSITIVE_INFINITY;
	}
	
	private EdgeWeightedGraph setUp(int size){
		EdgeWeightedGraph g = new EdgeWeightedGraph(size*size + 1);
		// connect across
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size - 1; j++){
				CatEdge e = new CatEdge(i*size + j, i*size + j + 1, 1);
				g.addEdge(e);
			}
		}
		// connect up/down
		for(int i = 0; i < size - 1; i++){
			for(int j = 0; j < size; j++){
				CatEdge e = new CatEdge(i*size + j, i*size + j + size, 1);
				if(j != 0)
					g.addEdge(e);
				e = new CatEdge(i*size + j, i*size + j + size + 1, 1);
				if(j != size - 1)
					g.addEdge(e);
			}
		}
		// connect freedom hexagon
		for(int i = 0; i < size; i++){
			CatEdge e = new CatEdge(i, size*size, size*size + 100);
			g.addEdge(e);
			e = new CatEdge(size*(size - 1) + i, size*size, size*size + 100);
			g.addEdge(e);
			e = new CatEdge(i*size, size*size, size*size + 100);
			e = new CatEdge(i*size  + size - 1, size*size, size*size + 100);
		}
		
		return g;
	}
	
	private int constructSPT(){
		
		return 0;
	}
	
	public static void main(String[] args){
		CatGame cg = new CatGame(11);
	}
}