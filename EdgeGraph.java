class EdgeGraph {
	vector<E_vertex> edges;
	int edge_num;
	EdgeGraph(){
		edges=new vector<E_vertex>();
		edge_num=0;
	}
	void addEdge(E_vertex addthis){
		edges.add(addthis);
	}
	E_vertex search(Edge hello){
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
	Edge owner;
	P_vertex point_one;
	P_vertex point_two;
	vector<T_vertex> triangles;
	E_vertex(Edge thisistheowner){
		this.owner=thisistheowner;
		point_one=null;
		point_two=null;
		triangles=new vector<T_vertex>();
	}
	void addPointVertex(P_vertex poi1,P_vertex poi2){
		point_one=poi1;
		point_two=poi2;
	}
	void addTricon(T_vertex addthis){
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