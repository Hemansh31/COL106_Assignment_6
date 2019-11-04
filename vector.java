class vector<T> {
	v_node<T> head;
	v_node<T> end;
	int length;
	vector(){
		head=null;
		end=null;
		length=0;
	}
	void add(T addthis){
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
	T contains(T checkthis){
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
	int size(){
		return length;
	}
	v_node<T> getNode(T containsthis){
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
	T data;
	v_node<T> next;
	v_node<T> previous;
	v_node(T init){
		this.data=init;
		next=null;
		previous=null;
	}
	void setNext(v_node addthis){
		this.next=addthis;
	}
	void setPrevious(v_node addthis){
		this.previous=addthis;
	}
	v_node<T> getNext(){
		return next;
	}
	v_node<T> getPrevious(){
		return previous;
	}
	T getData(){
		return data;
	}
}