public class Helper<T> {
	T[] array;
	int size;

	public Helper() {
		array = (T[]) (new Object[50]);
		size=0;
	}

	public void add(T val) {
		if(size==array.length){
			T[] temp= (T[]) (new Object[array.length*2]);
			for(int i=0;i<array.length;i++)
				temp[i]=array[i];
			array=temp;
			temp=null;
		}
		array[size]=val;
		size++;
	}
	
	public int size(){
		return size;
	}
	public T get(int i){
		return array[i];
	}
	public void set(int ind, T val){
		array[ind]=val;
	}
}
