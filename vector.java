class vector<T> {
	public v_node<T> head;
	public v_node<T> end;
	public int length;
	public int index=0;
	public vector(){
		head=null;
		end=null;
		length=0;
	}
	public void add(T addthis){
		v_node<T> newnode=new v_node<T>(addthis);
		if(head==null){
			head=newnode;
			end=newnode;
		}
		else{
			end.setNext(newnode);
			newnode.setPrevious(end);
			end=newnode;
		}
		length++;
	}
	public T contains(T checkthis){
		v_node<T> temp=head;
		while(temp!=null){
			if(checkthis.equals(temp.getData())){
				return temp.getData();
			}
			else{
				temp=temp.getNext();
			}
		}
		return null;
	}
	public int size(){
		return length;
	}
	public v_node<T> getNode(T containsthis){
		v_node<T> temp=head;
		while(temp!=null){
			if(containsthis.equals(temp.getData())){
				return temp;
			}
			else{
				temp=temp.getNext();
			}
		}
		return null;
	}
}

class v_node<T>{
	public T data;
	public v_node<T> next;
	public v_node<T> previous;
	public v_node(T init){
		this.data=init;
		next=null;
		previous=null;
	}
	public void setNext(v_node addthis){
		this.next=addthis;
	}
	public void setPrevious(v_node addthis){
		this.previous=addthis;
	}
	public v_node<T> getNext(){
		return next;
	}
	public v_node<T> getPrevious(){
		return previous;
	}
	public T getData(){
		return data;
	}
}