class TriangleGraph{
	vector<T_vertex> triangles;
	int triangle_num;
	TriangleGraph(){
		triangles=new vector<T_vertex>();
		triangle_num=0;
	}
	void addTriangle(T_vertex addthis){
		triangles.add(addthis);
	}
	T_vertex search(Triangle hello){
		T_vertex really=new T_vertex(hello);
		if(triangles.contains(really)==null){
			return null;
		}
		else{
			T_vertex returnthis=(T_vertex)triangles.contains(really);
			return returnthis;
		}
	}
}
class T_vertex {
	Triangle owner;
	boolean V_status=false;
	boolean A_status=false;
	int dist=0;
	vector<T_vertex> padosi;
	P_vertex point1;
	P_vertex point2;
	P_vertex point3;
	E_vertex edge1;
	E_vertex edge2;
	E_vertex edge3;
	T_vertex(Triangle thisistheowner){
		this.owner=thisistheowner;
		point1=null;
		point2=null;
		point3=null;
		edge1=null;
		edge2=null;
		edge3=null;
		padosi=new vector<T_vertex>();
	}
	void addTricon(T_vertex addthis){
		padosi.add(addthis);
	}
	void addEdgeVertex(E_vertex a,E_vertex b,E_vertex c){
		this.edge1=a;
		this.edge2=b;
		this.edge3=c;
	}
	void addPointVertex(P_vertex a,P_vertex b,P_vertex c){
		this.point1=a;
		this.point2=b;
		this.point3=c;
	}
	@Override
	public boolean equals(Object o){
		T_vertex fuck=(T_vertex)o;
		if(owner.equals(fuck.owner)){
			return true;
		}
		else{
			return false;
		}
	}
}