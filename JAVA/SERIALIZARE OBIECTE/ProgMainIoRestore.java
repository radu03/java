package eu.ase.iosd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
	
	//public void Display() {
	//	System.out.println("Another version = " +this.toString());
	//}
	
	public void f() {
		System.out.println("Another version = " +this.toString());
	}
	
	
	
}


class ProgMainIoRestore {

	public static void main(String[] args) {
		SimpleObject or1 = null, or2=null;
		
		FileInputStream fis=null;
		try {
			fis = new FileInputStream("fisier1.txt");
			ObjectInputStream in = new ObjectInputStream(fis);
			or1 = (SimpleObject) in.readObject();
			or2 = (SimpleObject) in.readObject();
			or1.f();
			or2.f();
			
			in.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}
	}
		
		
		
		

	


