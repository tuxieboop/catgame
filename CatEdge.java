import edu.princeton.cs.algs4.DirectedEdge;
public class CatEdge extends DirectedEdge{
	private double weight;
	public CatEdge(int v, int w, double weight){
		super(v, w, weight);
		this.weight = weight;
	}
	public void setWeight(double w){
		this.weight = w;
	}
	public double getWeight(){
		return weight;
	}
}