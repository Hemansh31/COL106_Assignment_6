class TriangleGraph{
	public vector<T_vertex> triangles;
	public int triangle_num;
	public TriangleGraph(){
		triangles=new vector<T_vertex>();
		triangle_num=0;
	}
	public void addTriangle(T_vertex addthis){
		triangles.add(addthis);
	}
	public T_vertex search(Triangle hello){
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
	public Triangle owner;
	public boolean V_status=false;
	public boolean A_status=false;
	public int dist=0;
	public vector<T_vertex> padosi;
	public P_vertex point1;
	public P_vertex point2;
	public P_vertex point3;
	public E_vertex edge1;
	public E_vertex edge2;
	public E_vertex edge3;
	public T_vertex(Triangle thisistheowner){
		this.owner=thisistheowner;
		point1=null;
		point2=null;
		point3=null;
		edge1=null;
		edge2=null;
		edge3=null;
		padosi=new vector<T_vertex>();
	}
	public void addTricon(T_vertex addthis){
		padosi.add(addthis);
	}
	public void addEdgeVertex(E_vertex a,E_vertex b,E_vertex c){
		this.edge1=a;
		this.edge2=b;
		this.edge3=c;
	}
	public void addPointVertex(P_vertex a,P_vertex b,P_vertex c){
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