package eu.ase.iosd;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/*
class SimpleObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static int n1;
	public String name = "test";
	private transient int n2;
	public transient int n3;
	private int n4;
	
	public SimpleObject(String namep, int n1p, int n2, int n3, int n4) {
		this.name = namep;
		SimpleObject.n1 = n1p;
		this.n2 = n2; 
		this.n3 = n3;
		this.n4=n4;
	}
	
	@Override
	public String toString() {
		return ""+this.name+" "+this.n1+" "+this.n2+" "+this.n3+" "+this.n4+"\n";
	}
	
	public void Display() {
		System.out.println(this.toString());
	}
	
	
	
}
*/

class ProgMainIoSave {

	public static void main(String[] args) {
		SimpleObject o1 = new SimpleObject("Obj1", 11, 12, 13, 14);
		SimpleObject o2 = new SimpleObject("Obj2",21,22,23,24);
		
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("fisier1.txt");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(o1);
			out.writeObject(o2);
			//o1.Display();
			//o2.Display();
			
			out.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		

	}

}
