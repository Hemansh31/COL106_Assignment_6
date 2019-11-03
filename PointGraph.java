class PointGraph {
	vector<P_vertex> vertices;
	int vertex_num;
	PointGraph(){
		vertices=new vector<P_vertex>();
		vertex_num=0;
	}
	void addVertex(P_vertex addthis){
		vertices.add(addthis);
		vertex_num++;
	}
	P_vertex search(Point hello){
		P_vertex temporary=new P_vertex(hello);
		if(vertices.contains(temporary)==null){
			return null;
		}
		else{
			P_vertex returnthis=(P_vertex)vertices.contains(temporary);
			return returnthis;
		}
	}
}
class P_vertex {
	Point owner;
	boolean V_status=false;
	vector<P_vertex> neighbours;
	vector<E_vertex> edges;
	vector<T_vertex> triangles;
	P_vertex(Point thisistheowner){
		this.owner=thisistheowner;
		neighbours=new vector<P_vertex>();
		edges=new vector<E_vertex>();
		triangles=new vector<T_vertex>;
	}
	void addNeighbour(P_vertex addthis){
		neighbours.add(addthis);
	}
	void addEdge(E_vertex addthis){
		edges.add(addthis);
	}
	void addTricon(T_vertex addthis){
		triangles.add(addthis);
	}
	@Override
	public boolean equals(Object o){
		P_vertex letsdoit=(P_vertex)o;
		if(owner.equals(letsdoit.owner)){
			return true;
		}
		else{
			return false;
		}
	}
}