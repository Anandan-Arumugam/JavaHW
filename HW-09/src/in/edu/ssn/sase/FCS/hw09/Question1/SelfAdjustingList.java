package in.edu.ssn.sase.FCS.hw09.Question1;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class SelfAdjustingList<T extends Comparable<T>> implements Comparator<T> {

	private static int Capacity;
	private static int length;
	private T[] elements;
	private boolean[] isDeleted;
	boolean isFull;

	public boolean isFull() {
		return (Capacity==length);
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public SelfAdjustingList(Class<T> type){

		isFull=false;
		Capacity=1000;
		length=0;
		elements = (T[])Array.newInstance(type, Capacity);
		isDeleted = new boolean[Capacity];
//		Arrays.fill(isDeleted, Boolean.FALSE);
	}

	public SelfAdjustingList (Class<T> type, int length){

		this.length=0;
		isFull=false;
		Capacity=length; 
		elements = (T[])Array.newInstance(type, Capacity);
		isDeleted = new boolean[length];
//		Arrays.fill(isDeleted, Boolean.FALSE);

	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int end(){
		return Capacity;
	}
	public int begin(){
		if(Capacity-length>=0)
		return Capacity-length;
		else
			return -1;
	}
	public int next(int Position){
		if(Position+1 > this.end()||Position +1 < this.begin()){
			return -1;
		}
		else 
			return Position+1;
	}
	public int previous(int Position){
		if(Position-1 < this.begin()||Position -1 > this.end()){
			return -1;
		}
		else 
			return Position-1;
	}
	public synchronized void locate(T element){

		int Position=this.begin();
		while(Position<Capacity)
		{
			if(this.compare(elements[Position], element)==0)
			{
				T temp=elements[Position];
				for (int k=Position;k>this.begin();k--)
				{
					elements[k]=elements[this.previous(k)];
				}
				elements[this.begin()]=temp;
			}
			Position++;
		}
	}
	public T retrieve(int Position){
		return elements[this.begin()+Position];

	}
	public synchronized void insert(T element){

		if(!(this.isFull()))
		{
			elements[this.begin()-1]=element;
			isDeleted[this.begin()-1]=false;
			length++;
		}
//		else
//			this.setFull(true);
	}

	public synchronized void delete(int Position){
		if((this.isFull()))
		{
			isDeleted[this.begin()+Position]=true;
			this.setFull(false);
		}
		else
			isDeleted[this.begin()+Position]=true;
	}
	public synchronized void purge(){
		
		int Position=this.begin();
		while (Position<Capacity){
			if(isDeleted[Position])
			{
				boolean temp=isDeleted[Position];
				for (int k=Position;k>this.begin();k--)
				{
					elements[k]=elements[this.previous(k)];
					isDeleted[k]=isDeleted[this.previous(k)];
				}
				isDeleted[Position]=temp;
				length--;
			}
			Position++;
		}
	}

	public void printList(){

		int Position=this.begin();
		System.out.print("[");
		while (Position<Capacity)
		{
			System.out.print(elements[Position]+" ");
			Position++;
		}
		System.out.println("]");
	}

	public boolean isEmpty(){
		if(length==0)
			return true;
		else
			return  false;
	}
	@Override
	public int compare(T a, T b) {
		return a.compareTo(b);
	}
}
