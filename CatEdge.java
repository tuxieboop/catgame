import edu.princeton.cs.algs4.Edge;
public class CatEdge extends Edge{
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