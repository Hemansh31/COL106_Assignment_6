

interface PointInterface {
	
   float getX();
   float getY();
   float getZ();

	//[x,y,z]  3 dimensions first is x second y and third is z.
	// This order should be followed

    float [] getXYZcoordinate();

}

class Point implements PointInterface {
	float X_coordinate;
	float Y_coordinate;
	float Z_coordinate;

	Point(float a,float b,float c){
		this.X_coordinate=a;
		this.Y_coordinate=b;
		this.Z_coordinate=c;
	}

	float getX(){
		return X_coordinate;
	}

	float getY(){
		return Y_coordinate;
	}

	float getZ(){
		return Z_coordinate;
	}

	float [] getXYZcoordinate(){
		float [] floatArray=new float[]{X_coordinate,Y_coordinate,Z_coordinate};
		return floatArray;
	}
	@Override
	public boolean equals(Object trythis){
		Point fuck=(Point)trythis;
		if((fuck.getX()==X_coordinate)&&(fuck.getY()==Y_coordinate)&&(fuck.getZ()=Z_coordinate)){
			return true;
		}
		else{
			return false;
		}
	}

}
