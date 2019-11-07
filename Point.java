class Point implements PointInterface {
	public float X_coordinate;
	public float Y_coordinate;
	public float Z_coordinate;

	public Point(float a,float b,float c){
		this.X_coordinate=a;
		this.Y_coordinate=b;
		this.Z_coordinate=c;
	}

	public float getX(){
		return X_coordinate;
	}

	public float getY(){
		return Y_coordinate;
	}

	public float getZ(){
		return Z_coordinate;
	}

	public float [] getXYZcoordinate(){
		float [] floatArray=new float[]{X_coordinate,Y_coordinate,Z_coordinate};
		return floatArray;
	}
	@Override
	public boolean equals(Object trythis){
		Point fuck=(Point)trythis;
		if((fuck.getX()==X_coordinate)&&(fuck.getY()==Y_coordinate)&&(fuck.getZ()==Z_coordinate)){
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public String toString(){
		String answer="( "+(int)X_coordinate+" "+(int)Y_coordinate+" "+(int)Z_coordinate+" )";
		return answer;
	}

}
