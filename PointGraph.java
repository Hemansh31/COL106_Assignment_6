class PointGraph {
	public vector<P_vertex> vertices;
	public int vertex_num;
	public PointGraph(){
		vertices=new vector<P_vertex>();
		vertex_num=0;
	}
	public void addVertex(P_vertex addthis){
		vertices.add(addthis);
		vertex_num++;
	}
	public P_vertex search(Point hello){
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
	public Point owner;
	public boolean V_status=false;
	public vector<P_vertex> neighbours;
	public vector<E_vertex> edges;
	public vector<T_vertex> triangles;
	public P_vertex(Point thisistheowner){
		this.owner=thisistheowner;
		neighbours=new vector<P_vertex>();
		edges=new vector<E_vertex>();
		triangles=new vector<T_vertex>();
	}
	public void addNeighbour(P_vertex addthis){
		neighbours.add(addthis);
	}
	public void addEdge(E_vertex addthis){
		edges.add(addthis);
	}
	public void addTricon(T_vertex addthis){
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