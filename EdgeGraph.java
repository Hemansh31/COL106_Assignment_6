class EdgeGraph {
	vector<E_vertex> edges;
	public int edge_num;
	public EdgeGraph(){
		edges=new vector<E_vertex>();
		edge_num=0;
	}
	public void addEdge(E_vertex addthis){
		edges.add(addthis);
	}
	public E_vertex search(Edge hello){
		E_vertex really=new E_vertex(hello);
		if(edges.contains(really)==null){
			return null;
		}
		else{
			E_vertex returnthis=(E_vertex)edges.contains(really);
			return returnthis;
		}
	}
}
class E_vertex{
	public Edge owner;
	public P_vertex point_one;
	public P_vertex point_two;
	public vector<T_vertex> triangles;
	public E_vertex(Edge thisistheowner){
		this.owner=thisistheowner;
		point_one=null;
		point_two=null;
		triangles=new vector<T_vertex>();
	}
	public void addPointVertex(P_vertex poi1,P_vertex poi2){
		point_one=poi1;
		point_two=poi2;
	}
	public void addTricon(T_vertex addthis){
		triangles.add(addthis);
	}
	@Override
	public boolean equals(Object o){
		E_vertex fedup=(E_vertex)o;
		if(owner.equals(fedup.owner)){
			return true;
		}
		else{
			return false;
		}
	}
}