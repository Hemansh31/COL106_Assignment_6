

public class Shape implements ShapeInterface
{
	PointGraph all_vertex;
	EdgeGraph all_edge;
	TriangleGraph all_triangle;
	int arrivaltime;

	public Shape(){
		all_vertex=new PointGraph();
		all_edge=new EdgeGraph();
		all_triangle=new TriangleGraph();
		arrivaltime=0;
	}

	public boolean checkValid(Edge ab,Edge bc,Edge ca){
		float a=ab.getLength();
		float b=bc.getLength();
		float c=ca.getLength();
		boolean b1=(a+b>c);
		boolean b2=(a+c>b);
		boolean b3=(b+c>a);
		if(b1&&b2&&b3){
			return true;
		}
		else{
			return false;
		}
	}

	public void transfer_triangles(E_vertex ab,T_vertex abc){
		vector<T_vertex> triangles=ab.triangles;
		v_node<T_vertex> temp=triangles.head;
		while(temp!=null){
			T_vertex addthis=(T_vertex)temp.getData();
			abc.addTricon(addthis);
			temp=temp.next;
		}
	}

	public boolean ADD_TRIANGLE(float [] triangle_coord){
		arrivaltime++;
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Edge ab=new Edge(a,b);
		Edge bc=new Edge(b,c);
		Edge ca=new Edge(c,a);
		boolean validity=checkValid(ab,bc,ca);
		if(!validity){
			return false;
		}
		else{
			P_vertex va=all_vertex.search(a);
			if(va==null){
				va=new P_vertex(a);
				all_vertex.add(va);
			}
			P_vertex vb=all_vertex.search(b);
			if(vb==null){
				vb=new P_vertex(b);
				all_vertex.add(vb);
			}
			P_vertex vc=all_vertex.search(c);
			if(vc==null){
				vc=new P_vertex(c);
				all_vertex.add(vc);
			}
			E_vertex vab=all_edge.search(ab);
			if(vab==null){
				vab=new E_vertex(ab);
				va.addNeighbour(vb);
				vb.addNeighbour(va);
				va.addEdge(vab);
				vb.addEdge(vab);
				vab.addPointVertex(va,vb);
				all_edge.add(vab);
			}
			E_vertex vbc=all_edge.search(bc);
			if(vbc==null){
				vbc=new E_vertex(bc);
				vb.addNeighbour(vc);
				vc.addNeighbour(vb);
				vb.addEdge(vbc);
				vc.addEdge(vbc);
				vbc.addPointVertex(vb,vc);
				all_edge.add(vbc);
			}
			E_vertex vca=all_edge.search(ca);
			if(vca==null){
				vca=new E_vertex(ca);
				vc.addNeighbour(va);
				va.addNeighbour(vc);
				va.addEdge(vca);
				vc.addEdge(vca);
				vca.addPointVertex(vc,va);
				all_edge.add(vca);
			}
			Triangle abc=new Triangle(a,b,c,arrivaltime);
			T_vertex vabc=all_triangle.search(abc);
			if(vabc==null){
				vabc=new T_vertex(abc);
				vabc.addEdgeVertex(vab,vbc,vca);
				vabc.addPointVertex(va,vb,vc);
				va.addTricon(vabc);
				vb.addTricon(vabc);
				vc.addTricon(vabc);
				transfer_triangles(vab,vabc);
				va.addTricon(vabc);
				transfer_triangles(vb,vabc);
				vb.addTricon(vabc);
				transfer_triangles(vc,vabc);
				vc.addTricon(vabc);
			}
			return true;												
		}
	}

}

