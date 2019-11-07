class shape_comp{
	public vector<T_vertex>  dhillo;
	public int choot;
	public shape_comp(){
		dhillo=new vector<T_vertex>();
		choot=0;
	}
	public void addTricon(T_vertex addthis){
		dhillo.add(addthis);
		choot++;
	}
	public int size(){
		return choot;
	}
}