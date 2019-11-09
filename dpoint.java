class dpoint{
	public double X_coordinate;
	public double Y_coordinate;
	public double Z_coordinate;

	public dpoint(double a,double b,double c){
		this.X_coordinate=a;
		this.Y_coordinate=b;
		this.Z_coordinate=c;
	}

	public double getX(){
		return X_coordinate;
	}

	public double getY(){
		return Y_coordinate;
	}

	public double getZ(){
		return Z_coordinate;
	}
}