
interface TriangleInterface {

	//Point1, Point2, Point3

	//[(x1,y1,z1),(x2,y2,z2),(x3,y3,z3)]

	// No specific order between points
	// MUST CONTAIN EXACTLY 3 points. 
    	PointInterface [] triangle_coord();

}

class Triangle implements TriangleInterface {

	Point point1;

	Point point2;

	Point point3;

	int timestamp;
	Triangle(Point a,Point b,Point c,int arrivaltime){
		this.point1=a;
		this.point2=b;
		this.point3=c;
		this.timestamp=arrivaltime;
	}

	PointInterface [] triangle_coord(){
		PointInterface[] pointarray=new PointInterface[]{point1,point2,point3};
		return pointarray;
	}
	@Override
	public boolean equals(Object o){
		Triangle fuck=(Triangle)o;
		if((point1.equals(fuck.point1))&&(point2.equals(fuck.point2))&&(point3.equals(fuck.point3))){
			return true;
		}
		else if((point1.equals(fuck.point1))&&(point3.equals(fuck.point2))&&(point2.equals(fuck.point3))){
			return true;
		}
		else if((point2.equals(fuck.point1))&&(point1.equals(fuck.point2))&&(point3.equals(fuck.point3))){
			return true;
		}
		else if((point2.equals(fuck.point1))&&(point3.equals(fuck.point2))&&(point1.equals(fuck.point3))){
			return true;
		}
		else if((point3.equals(fuck.point1))&&(point2.equals(fuck.point2))&&(point1.equals(fuck.point3))){
			return true;
		}
		else if((point3.equals(fuck.point1))&&(point1.equals(fuck.point2))&&(point2.equals(fuck.point3))){
			return true;
		}
		else{
			return false;
		}
	}
}