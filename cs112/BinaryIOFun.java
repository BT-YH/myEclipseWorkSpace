import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class BinaryIOFun {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		// Text IO Review:
		try {
			File file = new File("test.txt");
			
			// Write text
			PrintWriter output = new PrintWriter(file);
			output.println("Testing 1 2 3");
			output.close();
			
			// Read text
			Scanner input = new Scanner(file);
			while (input.hasNextLine()) {
				System.out.println(input.nextLine());
			}
			input.close();
				
			// delete file
			file.delete();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// Java performs binary IO with unsigned bytes ()
		File file = new File("text.dat");
		// try with resources
		try ( FileOutputStream output = new FileOutputStream(file);) {
			for (int i = 1; i >= 0; i *= 2) { // 1, 2, 4, 8 ... until negative overflow
				System.out.println(i);
				output.write(i);
			}
		}
		
		// Binary input
		try ( FileInputStream input = new FileInputStream(file);) { // writing and reading 1 byte
			while(input.available() != 0) {
				System.out.println(input.read());
			}
		}
		file.delete();
		
		
		// Byte output
		file = new File("text.dat");
		try ( FilterOutputStream output = new FilterOutputStream(new FileOutputStream(file));) {
			for (byte b : "Hello, world!\n".getBytes()) {
				output.write(b);
			}
			output.write("Hello again, world!\n".getBytes());
		}
		
		// Byte input
		try ( FilterInputStream input = new BufferedInputStream(new FileInputStream(file));) { // writing and reading 1 byte
			int i = 0;
			while ((i = input.read()) != -1) {
				System.out.print((char) i);
			}
		}
		
		file.delete();
		
		// FilterInputStream/FilterOutputStream wraps byte-based IO Streams
		// BuffedInputStream/BufferedoutputStream are subclasses respectively
		// DataInputStream/DataOutputStream are also subclasses that 
		//   allow reading/writing of Java primitive types and String
		
		file = new File("test.dat");

		try (
				DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
				) {
			output.writeInt(42);
			output.writeDouble(Math.PI);
			output.writeUTF("Hello, world");
		}
		
		try (
				DataInputStream input = new DataInputStream(new FileInputStream(file));
				) {
			System.out.println(input.readInt());
			System.out.println(input.readDouble());
			System.out.println(input.readUTF());
		}
		
		file.delete();
		
		
		// ObjectInputStream/ObjectOutputStream allow reading/writing
		// of Java Objects that implement the Serializable interface
		
		file = new File("test.dat");

		try (
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				) {
			output.writeUTF("Easy as ");
			output.writeObject(new int[] {1, 2, 3});
			
		}
		
		try (
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				) {
			System.out.print(input.readUTF());
			System.out.println(Arrays.toString((int[]) input.readObject()));
		}
		
		file.delete();
		
		// TODO: Exercise - Make a BankAccount subclass that is Serializable
		// and practice writing it out and reading it in
	}

}
