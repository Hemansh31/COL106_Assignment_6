class Triangle implements TriangleInterface {

	public Point point1;

	public Point point2;

	public Point point3;

	public int timestamp;
	public Triangle(Point a,Point b,Point c,int arrivaltime){
		this.point1=a;
		this.point2=b;
		this.point3=c;
		this.timestamp=arrivaltime;
	}

	public PointInterface [] triangle_coord(){
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