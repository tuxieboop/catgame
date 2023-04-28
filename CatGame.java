import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.DijkstraSP;
import java.util.ArrayList;
import java.util.Random;

public class CatGame{
	int n;
	public EdgeWeightedDigraph board;
	boolean[] marked;
	int cati;
	int catj;
	int catpos;
	
	public CatGame(int size){
		n = size;
		board = setUp(size);
		marked = new boolean[size*size + 1];
		for(int i = 0; i <= size*size; i++){
			marked[i] = false;
		}
		Random r = new Random();
		for(int i = 0; i < size; i++){
			markTile(r.nextInt(size), r.nextInt(size), "");
		}
		
		cati = size/2;
		catj = size/2;
		catpos = convert(cati, catj);
		System.out.println(board.toString());
	}
	private void markTile(int row, int col, String s){
		Iterable<DirectedEdge> adj = board.adj(convert(row, col));
		for(DirectedEdge ed : adj){
			CatEdge ce = (CatEdge)ed;
			ce.setWeight(Double.POSITIVE_INFINITY);
		}
		marked[convert(row, col)] = true;
	}
	public void markTile(int row, int col){
		Iterable<DirectedEdge> adj = board.adj(convert(row, col));
		for(DirectedEdge ed : adj){
			CatEdge ce = (CatEdge)ed;
			ce.setWeight(Double.POSITIVE_INFINITY);
		}
		marked[convert(row, col)] = true;
		Stack<DirectedEdge> path = (Stack)pathToFreedom();
		System.out.println(path.peek());
		catpos = path.pop().to();
		int[] pos = convert(catpos);
		cati = pos[0];
		catj = pos[1];
	}
	public boolean marked(int row, int col){
		return marked[convert(row, col)];
	}
	public int[] getCatTile(){
		int[] ans = {cati, catj};
		return ans;
	}
	public boolean catHasEscaped(){
		return marked[n*n];
	}
	public boolean catIsTrapped(){
		Iterable<DirectedEdge> adj = board.adj(catpos);
		for(DirectedEdge ed : adj){
			CatEdge ce = (CatEdge)ed;
			if(ce.weight() != Double.POSITIVE_INFINITY)
				return false;
		}
		return true;
	}
	
	private EdgeWeightedDigraph setUp(int size){
		EdgeWeightedDigraph g = new EdgeWeightedDigraph(size*size + 1);
		CatEdge e;
		// connect across
		for(int i = 0; i < size*size; i++){
			ArrayList<Integer> adjh = adjHexagons(i);
			for(int j = 0; j < adjh.size(); j++){
				addEdges(i, adjh.get(j), 1, g);
			}
		}
		// connect freedom hexagon
		for(int i = 0; i < size; i++){
			addEdges(i, size*size, size*size + 100, g); // add the top
			addEdges(size*(size - 1) + i, size*size, size*size + 100, g); // add the bottom
		}
		for(int i = 1; i < size - 1; i++){
			addEdges((i + 1)*size - 1, size*size, size*size + 100, g); // add the right
			addEdges(i*size, size*size, size*size + 100, g); // add the left
		}
		
		return g;
	}
	private ArrayList<Integer> adjHexagons(int i){
		ArrayList<Integer> al = new ArrayList<Integer>();
		if(i%n != 0) // left
			al.add(i - 1);
		if((i + 1)%n != 0) // right
			al.add(i + 1);
		if(i < n*(n - 1)){
			al.add(i + n);
			if((i/n) % 2 == 0 && (i + 1)%n != 0)
				al.add(i + n + 1);
			else if((i/n) % 2 != 0 && (i%n != 0))
				al.add(i + n - 1);
		}
		return al;
	}
	private void addEdges(int v, int w, int weight, EdgeWeightedDigraph g){
		CatEdge e = new CatEdge(v, w, weight);
		g.addEdge(e);
		e = new CatEdge(w, v, weight);
		g.addEdge(e);
	}
	
	private Iterable<DirectedEdge> pathToFreedom(){
		DijkstraSP spt = new DijkstraSP(board, catpos);
		return spt.pathTo(n*n);
	}
	
	private int convert(int i, int j){
		return i*n + j;
	}
	private int[] convert(int x){
		int[] ans = {x%n, x - x%n};
		return ans;
	}
	
	public static void main(String[] args){
		CatGame cg = new CatGame(5);
		cg.markTile(0, 0);
		cg.markTile(0, 3);
		cg.markTile(0, 9);
	}
}