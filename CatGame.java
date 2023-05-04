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
      int a = r.nextInt(size);
      int b = r.nextInt(size);
      if(!(a == size/2 && b == size/2))
        markTile(a, b, "");
    }
    
    catpos = convert(size/2, size/2);
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
    markTile(row, col, "");
    Stack<DirectedEdge> path = (Stack)pathToFreedom();
    System.out.println(path.peek());
    catpos = path.pop().to();
    System.out.println("catpos = " + catpos);
  }
  public boolean marked(int row, int col){
    return marked[convert(row, col)];
  }
  public int[] getCatTile(){
    return convert(catpos);
  }
  public boolean catHasEscaped(){
    return catpos == n*n;
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
      al.add(i + n); // upper left/right
      if((i/n) % 2 == 0 && (i + 1)%n != 0) // 
        al.add(i + n - 1);
      else if((i/n) % 2 != 0 && (i%n != 0))
        al.add(i + n + 1);
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
    int[] ans = new int[2];
    ans[0] = x/n;
    ans[1] = x%n;
    return ans;
  }
  
  public static void main(String[] args){
    Random rand = new Random();
    CatGame cg = new CatGame(5);
    while(!cg.catIsTrapped() && !cg.catHasEscaped()){
      System.out.println("curr: " + cg.getCatTile()[0] + " " + cg.getCatTile()[1]);
      int a = rand.nextInt(5);
      int b = rand.nextInt(5);
      System.out.println("mark " + a + " " + b);
      cg.markTile(a, b);
    }
  }
}