

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
			addthis.addTricon(abc);
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
				all_vertex.addVertex(va);
			}
			P_vertex vb=all_vertex.search(b);
			if(vb==null){
				vb=new P_vertex(b);
				all_vertex.addVertex(vb);
			}
			P_vertex vc=all_vertex.search(c);
			if(vc==null){
				vc=new P_vertex(c);
				all_vertex.addVertex(vc);
			}
			E_vertex vab=all_edge.search(ab);
			if(vab==null){
				vab=new E_vertex(ab);
				va.addNeighbour(vb);
				vb.addNeighbour(va);
				va.addEdge(vab);
				vb.addEdge(vab);
				vab.addPointVertex(va,vb);
				all_edge.addEdge(vab);
			}
			E_vertex vbc=all_edge.search(bc);
			if(vbc==null){
				vbc=new E_vertex(bc);
				vb.addNeighbour(vc);
				vc.addNeighbour(vb);
				vb.addEdge(vbc);
				vc.addEdge(vbc);
				vbc.addPointVertex(vb,vc);
				all_edge.addEdge(vbc);
			}
			E_vertex vca=all_edge.search(ca);
			if(vca==null){
				vca=new E_vertex(ca);
				vc.addNeighbour(va);
				va.addNeighbour(vc);
				va.addEdge(vca);
				vc.addEdge(vca);
				vca.addPointVertex(vc,va);
				all_edge.addEdge(vca);
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
				transfer_triangles(vbc,vabc);
				vb.addTricon(vabc);
				transfer_triangles(vca,vabc);
				vc.addTricon(vabc);
				all_triangle.addTriangle(vabc);
			}
			return true;												
		}
	}

	public int TYPE_MESH(){
		vector<E_vertex> edgev=all_edge.edges;
		int length=edgev.size();
		int morethantwo=0;
		int two=0;
		int one=0;
		v_node<E_vertex> temp=edgev.head;
		for(int i=0;i<length;i++){
			E_vertex checkthis=(E_vertex)temp.getData();
			int triangle_num=checkthis.triangles.size();
			if(triangle_num>2){
				morethantwo++;
			}
			else if(triangle_num==2){
				two++;
			}
			else{
				one++;
			}
			temp=temp.next;
		}
		if((two!=0)&&(morethantwo==0)&&(one==0)){
			return 1;
		}
		else if((morethantwo==0)&&(one!=0)){
			return 2;
		}
		else if(morethantwo!=0){
			return 3;
		}
		return 0;
	}

	public Edge[] sortThisArray(Edge[] sort_this){
		Edge[] really=merge_sort(sort_this,0,(sort_this.length-1));
		return really;
	}
	public Edge[] merge_sort(Edge[] sort_this,int low,int high){
		if(low==high){
			Edge[] onearr=new Edge[1];
			onearr[0]=sort_this[low];
			return onearr;
		}
		else if(low<high){
			int middle=(low+high)/2;
			Edge[] array1=merge_sort(sort_this,low,middle);
			Edge[] array2=merge_sort(sort_this,middle+1,high);
			Edge[] finalarray=merge(array1,array2);
			return finalarray;
		}
		return null;
	}
	public Edge[] merge(Edge[] array1,Edge[] array2){
		int l1=array1.length;
		int l2=array2.length;
		Edge[] mergedarray = new Edge[(l1+l2)];
		int cnt1=0;
		int cnt2=0;
		for(int i=0;i<(l1+l2);i++){
			if((cnt1==l1)&&(cnt2<l2)){
				mergedarray[i]=array2[cnt2];
				cnt2++;
			}
			else if((cnt2==l2)&&(cnt1<l1)){
				mergedarray[i]=array1[cnt1];
				cnt1++;
			}
			else if((cnt1<l1)&&(cnt2<l2)){
				if((array1[cnt1].getLength())<(array2[cnt2].getLength())){
					mergedarray[i]=array1[cnt1];
					cnt1++;
				}
				else{
					mergedarray[i]=array2[cnt2];
					cnt2++;
				}
			}
		}
		return mergedarray;
	}

	public EdgeInterface [] BOUNDARY_EDGES(){
		vector<E_vertex> edgev=all_edge.edges;
		int length=edgev.size();
		v_node<E_vertex> temp=edgev.head;
		vector<Edge> edgeb=new vector<Edge>();
		for(int i=0;i<length;i++){
			E_vertex checkthis=(E_vertex)temp.getData();
			int triangle_num= checkthis.triangles.size();
			if(triangle_num==1){
				Edge addthis=checkthis.owner;
				edgeb.add(addthis);
			}
			temp=temp.next;
		}
		int bsize=edgeb.size();
		if(bsize==0){
			return null;
		}
		Edge[] sortthis=new Edge[bsize];
		v_node<Edge> temp2=edgeb.head;
		for(int i=0;i<bsize;i++){
			Edge insertthis=(Edge)temp2.getData();
			sortthis[i]=insertthis;
		}
		Edge[] inter=sortThisArray(sortthis);
		EdgeInterface[] returnthis=inter; //could be a compilation error
		return returnthis;
	}

	public void depth_traversal(T_vertex tempo){
		if(tempo.V_status==true){
			return;
		}
		else{
			vector<T_vertex> helpme=tempo.padosi;
			tempo.V_status=true;
			int iterations=helpme.size();
			v_node<T_vertex> temp=helpme.head;
			for(int i=0;i<iterations;i++){
				T_vertex trythis=(T_vertex)temp.getData();
				depth_traversal(trythis);
				temp=temp.next;
			}
		}
		
	}

	public int COUNT_CONNECTED_COMPONENTS(){
		vector<T_vertex> ctricon=all_triangle.triangles;
		int count=0;
		v_node<T_vertex> temp=ctricon.head;
		while(temp!=null){
			T_vertex subject=(T_vertex)temp.getData();
			if((subject.V_status)==true){
				temp=temp.next;
			}
			else{
				depth_traversal(subject);
				count++;
				temp=temp.next;
			}
		}
		v_node<T_vertex> temp2=ctricon.head;
		while(temp!=null){
			T_vertex subject=(T_vertex)temp2.getData();
			subject.V_status=false;
			temp2=temp2.next;
		}
		return count;
	}

	public Triangle[] sortThisArray_two(Triangle[] sort_this){
		Triangle[] really=merge_sort_two(sort_this,0,(sort_this.length-1));
		return really;
	}
	public Triangle[] merge_sort_two(Triangle[] sort_this,int low,int high){
		if(low==high){
			Triangle[] onearr=new Triangle[1];
			onearr[0]=sort_this[low];
			return onearr;
		}
		else if(low<high){
			int middle=(low+high)/2;
			Triangle[] array1=merge_sort_two(sort_this,low,middle);
			Triangle[] array2=merge_sort_two(sort_this,middle+1,high);
			Triangle[] finalarray=merge_two(array1,array2);
			return finalarray;
		}
		return null;
	}
	public Triangle[] merge_two(Triangle[] array1,Triangle[] array2){
		int l1=array1.length;
		int l2=array2.length;
		Triangle[] mergedarray = new Triangle[(l1+l2)];
		int cnt1=0;
		int cnt2=0;
		for(int i=0;i<(l1+l2);i++){
			if((cnt1==l1)&&(cnt2<l2)){
				mergedarray[i]=array2[cnt2];
				cnt2++;
			}
			else if((cnt2==l2)&&(cnt1<l1)){
				mergedarray[i]=array1[cnt1];
				cnt1++;
			}
			else if((cnt1<l1)&&(cnt2<l2)){
				if((array1[cnt1].timestamp)<(array2[cnt2].timestamp)){
					mergedarray[i]=array1[cnt1];
					cnt1++;
				}
				else{
					mergedarray[i]=array2[cnt2];
					cnt2++;
				}
			}
		}
		return mergedarray;
	}


	public TriangleInterface [] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord){
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Triangle abc=new Triangle(a,b,c,0);
		T_vertex wegetthis=all_triangle.search(abc);
		if(wegetthis==null){
			return null;
		}
		else{
			vector<T_vertex> neighbours=wegetthis.padosi;
			int length=neighbours.size();
			Triangle[] sortthis=new Triangle[length];
			v_node<T_vertex> temp=neighbours.head;
			int index=0;
			while(temp!=null){
				T_vertex trythis=(T_vertex)temp.getData();
				Triangle fuck=trythis.owner;
				sortthis[index]=fuck;
				index++;
				temp=temp.next;
			}
			Triangle[] inter=sortThisArray_two(sortthis);
			TriangleInterface[] returnthis=inter; //could be a compilation error
			return returnthis;
		}
	}

	public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Triangle abc=new Triangle(a,b,c,0);
		T_vertex wegetthis=all_triangle.search(abc);
		if(wegetthis==null){
			return null;
		}
		else{
			E_vertex edge_one=wegetthis.edge1;
			E_vertex edge_two=wegetthis.edge2;
			E_vertex edge_three=wegetthis.edge3;
			EdgeInterface[] returnthis=new EdgeInterface[3];
			returnthis[0]=edge_one.owner;
			returnthis[1]=edge_two.owner;
			returnthis[2]=edge_three.owner;
			return returnthis;
		}
	}

	public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Triangle abc=new Triangle(a,b,c,0);
		T_vertex wegetthis=all_triangle.search(abc);
		if(wegetthis==null){
			return null;
		}
		else{
			Triangle goodforyou=wegetthis.owner;
			PointInterface[] returnthis=new PointInterface[3];
			returnthis[0]=goodforyou.point1;
			returnthis[1]=goodforyou.point2;
			returnthis[2]=goodforyou.point3;
			return returnthis;
		}		
	}

	public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord){
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Triangle abc=new Triangle(a,b,c,0);
		T_vertex wegetthis=all_triangle.search(abc);
		vector<T_vertex> interim=new vector<T_vertex>();
		if(wegetthis==null){
			return null;
		}
		else{
			P_vertex va=wegetthis.point1;
			P_vertex vb=wegetthis.point2;
			P_vertex vc=wegetthis.point3;
			vector<T_vertex> terminal_one=va.triangles;
			v_node<T_vertex> temp_one=terminal_one.head;
			while(temp_one!=null){
				T_vertex love=(T_vertex)temp_one.getData();
				if((love.point1.equals(vb))||(love.point2.equals(vb))||(love.point3.equals(vb))){
					temp_one=temp_one.next;
				}
				else{
					interim.add(love);
					temp_one=temp_one.next;
				}
			}
			vector<T_vertex> terminal_two=vb.triangles;
			v_node<T_vertex> temp_two=terminal_two.head;
			while(temp_two!=null){
				T_vertex love=(T_vertex)temp_two.getData();
				if((love.point1.equals(vc))||(love.point2.equals(vc))||(love.point3.equals(vc))){
					temp_two=temp_two.next;
				}
				else{
					interim.add(love);
					temp_two=temp_two.next;
				}
			}
			vector<T_vertex> terminal_three=vc.triangles;
			v_node<T_vertex> temp_three=terminal_three.head;
			while(temp_three!=null){
				T_vertex love=(T_vertex)temp_three.getData();
				if((love.point1.equals(va))||(love.point2.equals(va))||(love.point3.equals(va))){
					temp_three=temp_three.next;
				}
				else{
					interim.add(love);
					temp_three=temp_three.next;
				}
			}
			int length=interim.size();
			Triangle[] sorthis=new Triangle[length];
			v_node<T_vertex> temp=interim.head;
			int index=0;
			while(temp!=null){
				T_vertex trythis=(T_vertex)temp.getData();
				Triangle fuck=trythis.owner;
				sortthis[index]=fuck;
				index++;
				temp=temp.next;
			}	
			Triangle[] inter=sortThisArray_two(sortthis);
			TriangleInterface[] returnthis=inter; //could be a compilation error
			return returnthis;					
		}		
	}

	public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates){
		Point a=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		P_vertex wegetthis=all_vertex.search(a);
		if(wegetthis==null){
			return null;
		}
		else{
			vector<T_vertex> interim=wegetthis.triangles;
			int length=interim.size();
			Triangle[] sorthis=new Triangle[length];
			v_node<T_vertex> temp=interim.head;
			int index=0;
			while(temp!=null){
				T_vertex trythis=(T_vertex)temp.getData();
				Triangle fuck=trythis.owner;
				sortthis[index]=fuck;
				index++;
				temp=temp.next;
			}	
			Triangle[] inter=sortThisArray_two(sortthis);
			TriangleInterface[] returnthis=inter; //could be a compilation error
			return returnthis;	
		}
	}

}

