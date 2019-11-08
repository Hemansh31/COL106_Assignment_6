

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
		boolean b1=((a+b-c)<((float)(0.001)))&&((a+b-c))>((float)(-0.01));
		boolean b2=((b+c-a)<((float)(0.001)))&&((b+c-a))>((float)(-0.01));
		boolean b3=((c+a-b)<((float)(0.001)))&&((c+a-b))>((float)(-0.01));
		if(b1||b2||b3){
			return false;
		}
		else{
			return true;
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
			//System.out.println("h1");
			return false;
		}
		else{
			P_vertex va=all_vertex.search(a);
			if(va==null){
				//System.out.println("h2");
				va=new P_vertex(a);
				all_vertex.addVertex(va);
			}
			P_vertex vb=all_vertex.search(b);
			if(vb==null){
				//System.out.println("h3");
				vb=new P_vertex(b);
				all_vertex.addVertex(vb);
			}
			P_vertex vc=all_vertex.search(c);
			if(vc==null){
				//System.out.println("h4");
				vc=new P_vertex(c);
				all_vertex.addVertex(vc);
			}
			E_vertex vab=all_edge.search(ab);
			if(vab==null){
				//System.out.println("h5");
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
				//System.out.println("h6");
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
				//System.out.println("h7");
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
				//System.out.println("h8");
				vabc=new T_vertex(abc);
				vabc.addEdgeVertex(vab,vbc,vca);
				vabc.addPointVertex(va,vb,vc);
				va.addTricon(vabc);
				vb.addTricon(vabc);
				vc.addTricon(vabc);
				transfer_triangles(vab,vabc);
				vab.addTricon(vabc);
				transfer_triangles(vbc,vabc);
				vbc.addTricon(vabc);
				transfer_triangles(vca,vabc);
				vca.addTricon(vabc);
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
			//System.out.println(triangle_num);
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
		while(temp2!=null){
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
			/*for(int j=0;j<inter.length;j++){
				System.out.println(inter[j]);
			}*/
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
			Triangle[] sortthis=new Triangle[length];
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
			Triangle[] sortthis=new Triangle[length];
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

	public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates){
		Point a=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		P_vertex wegetthis=all_vertex.search(a);
		if(wegetthis==null){
			return null;
		}
		else{
			vector<P_vertex> padosi=wegetthis.neighbours;
			int length=padosi.size();
			//System.out.println(length);
			PointInterface[] returnthis=new PointInterface[length];
			v_node<P_vertex> temp=padosi.head;
			int index=0;
			while(temp!=null){
				P_vertex trythis=(P_vertex)temp.getData();
				Point fuck=trythis.owner;
				//System.out.println(""+(int)fuck.getX()+(int)fuck.getY()+(int)fuck.getZ());
				returnthis[index]=fuck;
				index++;
				temp=temp.next;
			}
			return returnthis;
		}
	}

	public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates){
		Point a=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		P_vertex wegetthis=all_vertex.search(a);
		if(wegetthis==null){
			return null;
		}
		else{
			vector<E_vertex> padosi=wegetthis.edges;
			int length=padosi.size();
			EdgeInterface[] returnthis=new EdgeInterface[length];
			v_node<E_vertex> temp=padosi.head;
			int index=0;
			while(temp!=null){
				E_vertex trythis=(E_vertex)temp.getData();
				Edge fuck=trythis.owner;
				returnthis[index]=fuck;
				index++;
				temp=temp.next;
			}
			return returnthis;	
		}
	}

	public TriangleInterface [] FACE_NEIGHBORS_OF_POINT(float [] point_coordinates){
		TriangleInterface[] returnthis=INCIDENT_TRIANGLES(point_coordinates);
		return returnthis;
	}

	public boolean breadth_search(T_vertex vabc1,T_vertex vabc2){
		vector<T_vertex> queue=new vector<T_vertex>();
		queue.add(vabc1);
		v_node<T_vertex> temp=queue.head;
		boolean found=false;
		/*vector<T_vertex> tricon1=all_triangle.triangles;
		v_node<T_vertex> fuck1=tricon1.head;
		while(fuck1!=null){
			T_vertex dog=(T_vertex)fuck1.getData();
			dog.V_status=false;
			fuck1=fuck1.next;
		}*/
		while(temp!=null){
			T_vertex searchthis=(T_vertex)temp.getData();
			if(searchthis.equals(vabc2)){
				//System.out.println("e1");
				found=true;
				break;
			}
			else if(searchthis.V_status==true){
				//System.out.println("e2");
				 temp=temp.next;
			}
			else{
				//System.out.println("e3");
				searchthis.V_status=true;
				vector<T_vertex> new_padosi=searchthis.padosi;
				int length=new_padosi.size();
				v_node<T_vertex> temp2=new_padosi.head;
				for(int i=0;i<length;i++){
					T_vertex addthis=(T_vertex)temp2.getData();
					if(addthis.V_status==false){
						queue.add(addthis);
					}					
					temp2=temp2.next;
				}
				temp=temp.next;
			}
		}
		vector<T_vertex> tricon=all_triangle.triangles;
		v_node<T_vertex> fuck=tricon.head;
		while(fuck!=null){
			T_vertex dog=(T_vertex)fuck.getData();
			dog.V_status=false;
			fuck=fuck.next;
		}
		return found;
	}

	public boolean IS_CONNECTED(float [] triangle_coord_1, float [] triangle_coord_2){
		Point a1=new Point(triangle_coord_1[0],triangle_coord_1[1],triangle_coord_1[2]);
		Point b1=new Point(triangle_coord_1[3],triangle_coord_1[4],triangle_coord_1[5]);
		Point c1=new Point(triangle_coord_1[6],triangle_coord_1[7],triangle_coord_1[8]);
		Point a2=new Point(triangle_coord_2[0],triangle_coord_2[1],triangle_coord_2[2]);
		Point b2=new Point(triangle_coord_2[3],triangle_coord_2[4],triangle_coord_2[5]);
		Point c2=new Point(triangle_coord_2[6],triangle_coord_2[7],triangle_coord_2[8]);
		Triangle abc1=new Triangle(a1,b1,c1,0);
		Triangle abc2=new Triangle(a2,b2,c2,0);
		T_vertex vabc1=all_triangle.search(abc1);
		T_vertex vabc2=all_triangle.search(abc2);
		if((vabc1==null)||(vabc2==null)){
			//System.out.println("g1");
			return false;
		}
		else{
			//System.out.println("g2");
			boolean answer=breadth_search(vabc1,vabc2);
			return answer;
		}
	}

	public TriangleInterface [] TRIANGLE_NEIGHBOR_OF_EDGE(float [] edge_coordinates){
		Point a=new Point(edge_coordinates[0],edge_coordinates[1],edge_coordinates[2]);
		Point b=new Point(edge_coordinates[3],edge_coordinates[4],edge_coordinates[5]);
		Edge ab=new Edge(a,b);
		E_vertex vab=all_edge.search(ab);
		if(vab==null){
			return null;
		}
		else{
			vector<T_vertex> bakchod=vab.triangles;
			v_node<T_vertex> temp=bakchod.head;
			int length=bakchod.size();
			int index=0;
			Triangle[] sortthis=new Triangle[length];
			while(temp!=null){
				T_vertex help=(T_vertex)temp.getData();
				sortthis[index]=help.owner;
				index++;
				temp=temp.next;
			}
			Triangle[] interim=sortThisArray_two(sortthis);
			TriangleInterface[] returnthis=interim; //could be a compilation error
			return returnthis;	
		}
	}

	/*public void depth_traversal_2(T_vertex tempo,shape_comp gay){
		if(tempo.V_status==true){
			return;
		}
		else{
			gay.addTricon(tempo);
			vector<T_vertex> helpme=tempo.padosi;
			tempo.V_status=true;
			int iterations=helpme.size();
			v_node<T_vertex> temp=helpme.head;
			for(int i=0;i<iterations;i++){
				T_vertex trythis=(T_vertex)temp.getData();
				depth_traversal_2(trythis,gay);
				temp=temp.next;
			}
		}		
	}*/

	public int bfs(T_vertex vabc1,vector<T_vertex> update){
		//System.out.println(all_triangle.triangles.size());
		vector<T_vertex> queue=new vector<T_vertex>();
		queue.add(vabc1);
		v_node<T_vertex> temp=queue.head;
		int max=0;
		while(temp!=null){
			T_vertex violet=(T_vertex)temp.getData();
			violet.V_status=true;
			vector<T_vertex> wtf=violet.padosi;
				//System.out.println(wtf.size());
				int distance=violet.dist;
				
				if(distance>max){
					max=distance;
				}
				v_node<T_vertex> become=wtf.head;
				while(become!=null){
					T_vertex name=(T_vertex)become.getData();
					if(name.V_status==false){
						queue.add(name);
						name.V_status=true;
						//System.out.println("d"+(distance+1));
						name.dist=distance+1;
					}
					become=become.next;
				}
			temp=temp.next;
		}
			/*if(violet.V_status==true){
				//System.out.println("d");
				temp=temp.next;
			}
			else{
				violet.V_status=true;
				vector<T_vertex> wtf=violet.padosi;
				//System.out.println(wtf.size());
				int distance=violet.dist;
				
				if(distance>max){
					max=distance;
				}
				v_node<T_vertex> become=wtf.head;
				while(become!=null){
					T_vertex name=(T_vertex)become.getData();

					if(name.A_status==false){
						//System.out.println(name.owner);
						queue.add(name);
						name.A_status=true;
						//System.out.println("d"+(distance+1));
						name.dist=distance+1;
					}
					become=become.next;
				}
				temp=temp.next;
			}*/

		
		//System.out.println(max);
		update=queue;
		//System.out.println(queue.size());
		return max;
	}

	public int MAXIMUM_DIAMETER(){
		vector<T_vertex> ctricon=all_triangle.triangles;
		v_node<T_vertex> temp=ctricon.head;
		vector<T_vertex> pass_this=new vector<T_vertex>();
		int max=0;
		int maxsize=0;
		while(temp!=null){
			T_vertex usethis=(T_vertex)temp.getData();
			int getthis=bfs(usethis,pass_this);
			int maxs=pass_this.size();
			if(maxs>maxsize){
				maxsize=maxs;
				max=getthis;
			}
			else if(maxs==maxsize){
				if(getthis>max){
					max=getthis;
				}
			}
			v_node<T_vertex> temp2=ctricon.head;
			while(temp2!=null){
				T_vertex rip=(T_vertex)temp2.getData();
				rip.V_status=false;
				rip.A_status=false;
				rip.dist=0;
				temp2=temp2.next;
			}
			
			temp=temp.next;
		}
			
		//System.out.println(max);
		return max;
	}


	public void depth_traversal_3(T_vertex tempo,Point you,vector<P_vertex> helpmetoo){
		if(tempo.V_status==true){
			return;
		}
		else{
			vector<T_vertex> helpme=tempo.padosi;
			tempo.V_status=true;
			P_vertex p1=tempo.point1;
			P_vertex p2=tempo.point2;
			P_vertex p3=tempo.point3;
			if(p1.V_status==false){
				p1.V_status=true;
				Point owner=p1.owner;
				you.X_coordinate+=owner.getX();
				you.Y_coordinate+=owner.getY();
				you.Z_coordinate+=owner.getZ();
				helpmetoo.add(p1);
			}
			if(p2.V_status==false){
				p2.V_status=true;
				Point owner=p2.owner;
				you.X_coordinate+=owner.getX();
				you.Y_coordinate+=owner.getY();
				you.Z_coordinate+=owner.getZ();
				helpmetoo.add(p2);
			}
			if(p3.V_status==false){
				p3.V_status=true;
				Point owner=p3.owner;
				you.X_coordinate+=owner.getX();
				you.Y_coordinate+=owner.getY();
				you.Z_coordinate+=owner.getZ();
				helpmetoo.add(p3);
			}
			int iterations=helpme.size();
			v_node<T_vertex> temp=helpme.head;
			for(int i=0;i<iterations;i++){
				T_vertex trythis=(T_vertex)temp.getData();
				depth_traversal_3(trythis,you,helpmetoo);
				temp=temp.next;
			}
		}		
	}

	public Point[] P_SORT(vector<Point> under){
		int array_size=under.size();
		Point [] sort_this=new Point[array_size];
		v_node<Point> temp=under.head;
		int index=0;
		while(temp!=null){
			Point db=(Point)temp.getData();
			sort_this[index]=db;
			temp=temp.next;
		}
		Point[] returnthis=sortThisArray_three(sort_this);
		return returnthis;
	}

	public Point[] sortThisArray_three(Point[] sort_this){
		Point[] really=merge_sort_three(sort_this,0,(sort_this.length-1));
		return really;
	}
	public Point[] merge_sort_three(Point[] sort_this,int low,int high){
		if(low==high){
			Point[] onearr=new Point[1];
			onearr[0]=sort_this[low];
			return onearr;
		}
		else if(low<high){
			int middle=(low+high)/2;
			Point[] array1=merge_sort_three(sort_this,low,middle);
			Point[] array2=merge_sort_three(sort_this,middle+1,high);
			Point[] finalarray=merge_three(array1,array2);
			return finalarray;
		}
		return null;
	}
	public Point[] merge_three(Point[] array1,Point[] array2){
		int l1=array1.length;
		int l2=array2.length;
		Point[] mergedarray = new Point[(l1+l2)];
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
				if((array1[cnt1].compare(array2[cnt2]))==-1){
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

	public PointInterface [] CENTROID (){
		vector<T_vertex> ctricon=all_triangle.triangles;
		v_node<T_vertex> temp=ctricon.head;
		vector<Point> all_average=new vector<Point>();
		while(temp!=null){
			vector<P_vertex> jjthomp=new vector<P_vertex>();
			Point record=new Point(0,0,0);
			T_vertex subject=(T_vertex)temp.getData();
			if((subject.V_status)==true){
				temp=temp.next;
			}
			else{
				depth_traversal_3(subject,record,jjthomp);
				int divisor=jjthomp.size();//confirm before submission
				record.X_coordinate/=divisor;
				record.Y_coordinate/=divisor;
				record.Z_coordinate/=divisor;
				all_average.add(record);
				v_node<P_vertex> kk=jjthomp.head;
				while(kk!=null){
					P_vertex bad=(P_vertex)kk.getData();
					bad.V_status=false;
				}
				temp=temp.next;				
			}
		}
		Point[] sortthis=P_SORT(all_average);
		v_node<T_vertex> temp2=ctricon.head;
		while(temp2!=null){
			T_vertex subject=(T_vertex)temp2.getData();
			subject.V_status=false;
			temp2=temp2.next;
		}
		PointInterface[] returnthis=sortthis;
		return returnthis;

	}

	public PointInterface CENTROID_OF_COMPONENT (float [] point_coordinates){
		Point outofnames=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		P_vertex wegetthis=all_vertex.search(outofnames);
		if(wegetthis==null){
			return null;
		}
		else{
			v_node<T_vertex> temp=wegetthis.triangles.head;
			vector<P_vertex> jjthomp=new vector<P_vertex>();
			Point record=new Point((float)0,(float)0,(float)0);
			T_vertex subject=(T_vertex)temp.getData();
			//System.out.println("Hey");
			depth_traversal_3(subject,record,jjthomp);
			//System.out.println("Hey1");
			float divisor=(float)(jjthomp.size());//confirm before submission
			System.out.println(record);
			System.out.println(divisor);
			record.X_coordinate/=divisor;
			record.Y_coordinate/=divisor;
			record.Z_coordinate/=divisor;
			//all_average.add(record);
			v_node<P_vertex> kk=jjthomp.head;
			while(kk!=null){
				P_vertex bad=(P_vertex)kk.getData();
				bad.V_status=false;
				kk=kk.next;
			}
			//System.out.println(record);
			vector<T_vertex> war_cry=all_triangle.triangles;
			v_node<T_vertex> soldier=war_cry.head;
			while(soldier!=null){
			T_vertex weapon=(T_vertex)soldier.getData();
			weapon.V_status=false;
			soldier=soldier.next;
		}
			return record;
		}
		

	}

	public void depth_traversal_final(T_vertex tempo,vector<P_vertex> tuck){
		if(tempo.V_status==true){
			return;
		}
		else{
			vector<T_vertex> helpme=tempo.padosi;
			tempo.V_status=true;
			P_vertex p1=tempo.point1;
			P_vertex p2=tempo.point2;
			P_vertex p3=tempo.point3;
			if(p1.V_status==false){
				p1.V_status=true;
				tuck.add(p1);
			}
			if(p2.V_status==false){
				p2.V_status=true;
				tuck.add(p2);
			}
			if(p3.V_status==false){
				p3.V_status=true;
				tuck.add(p3);
			}
			int iterations=helpme.size();
			v_node<T_vertex> temp=helpme.head;
			while(temp!=null){
				T_vertex trythis=(T_vertex)temp.getData();
				depth_traversal_final(trythis,tuck);
				temp=temp.next;
			}
		}		
	}

	public float calculator(P_vertex outer,P_vertex inner){
		Point owner1=outer.owner;
		Point owner2=inner.owner;
		Edge forget=new Edge(owner1,owner2);
		return forget.getLength();
	}

	public 	PointInterface [] CLOSEST_COMPONENTS(){
		vector<T_vertex> war_cry=all_triangle.triangles;
		v_node<T_vertex> soldier=war_cry.head;
		vector<vector<P_vertex>> major=new vector<vector<P_vertex>>();
		int indexing=0;
		int tree=0;
		//System.out.println(war_cry.size());
		while(soldier!=null){
			T_vertex weapon=(T_vertex)soldier.getData();
			if(weapon.V_status==true){
				tree++;
				soldier=soldier.next;
			}
			else{
				indexing++;
				vector<P_vertex> bomb=new vector<P_vertex>();
				depth_traversal_final(weapon,bomb);
				bomb.index=indexing;
				major.add(bomb);
				v_node<P_vertex> chick=bomb.head;
				while(chick!=null){
					P_vertex gal=(P_vertex)chick.getData();
					gal.V_status=false;
					chick=chick.next;
				}
				
				soldier=soldier.next;
			}
		}
		int size_one=major.size();
		//System.out.println(indexing);
		//System.out.println(tree);
		float g_min=(float)(-9);
		P_vertex lelo=null;
		P_vertex dedo=null;
		v_node<vector<P_vertex>> naah=major.head;
		while(naah!=null){
			//System.out.println("L1");
			vector<P_vertex> list=(vector<P_vertex>)naah.getData();
			v_node<vector<P_vertex>> haan=major.head;
			//System.out.println(list.index);
			while(haan!=null){
				//System.out.println("L2");
				vector<P_vertex> mist=(vector<P_vertex>)haan.getData();
				//System.out.println(list.index);
				if((list.index)==(mist.index)){
					//System.out.println("L5");
					haan=haan.next;
				}
				
				else{
					v_node<P_vertex> wela=list.head;
					while(wela!=null){
						//System.out.println("L3");
						v_node<P_vertex> weli=mist.head;
						P_vertex outer=(P_vertex)wela.getData();
						while(weli!=null){
							//System.out.println("L4");
							P_vertex inner=(P_vertex)weli.getData();
							float comparison=calculator(outer,inner);
							if(g_min<0||lelo==null||dedo==null){
								g_min=comparison;
								//System.out.println(comparison);
								lelo=inner;
								dedo=outer;
							}
							else{
								if(comparison<g_min){
									g_min=comparison;
									lelo=inner;
									dedo=outer;
								}
							}
							weli=weli.next;
						}
						wela=wela.next;
					}
					haan=haan.next;
				}
				//haan=haan.next;			
			}
			naah=naah.next;
		}
		if(lelo==null||dedo==null){
			return null;
		}
		Point r1=lelo.owner;
		Point r2=dedo.owner;
		PointInterface[] returnthis=new PointInterface[2];
		returnthis[0]=r1;
		returnthis[1]=r2;
		//System.out.println(r1);
		//System.out.println(r2);
		return returnthis;
	}
}

