package eu.ase.iosd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;

class ObjectsGraph implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private URL o1;
	private URL o2;
	
	public ObjectsGraph(URL o1, URL o2) {
		this.o1=o1;
		this.o2=o2;
	}
	
	@Override
	public String toString() {
		return "o1= "+this.o1+" o2= "+this.o2;
	}

	public URL getO1() {
		return o1;
	}

	public URL getO2() {
		return o2;
	}
	

}

class SaveObjects {
	public static void main(String[] args) {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream("fisier2.txt");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			URL o1 = new URL("https://www.amazon.com");
			URL o2 = o1;
			URL o3 = o1;
			
			ObjectsGraph og = new ObjectsGraph(o1, o2);
			out.writeObject(og);
			out.reset(); //resets the address
			out.writeObject(o3);
			out.flush();
			System.out.println("it has been written og ="+og+" , o3= " + o3.toString());
			System.out.println("(og.o1 == og.o2) && (og.o1 == o3) =" + ((og.getO1() == og.getO2()) && (og.getO1() == o3)));
			
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

class RestoreObjects {
	public static void main(String[] args) {
		ObjectsGraph og = null;
		try {
			FileInputStream fis = new FileInputStream("fisier2.txt");
			ObjectInputStream in = new ObjectInputStream(fis);
			og = (ObjectsGraph)in.readObject();
			System.out.println("og= "+og);
			URL o3 = (URL)in.readObject();
			System.out.println("o3= "+o3);
			System.out.println("(og.o1 == og.o2) && (og.o1 == o3) =" + ((og.getO1() == og.getO2()) && (og.getO1() == o3)));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

