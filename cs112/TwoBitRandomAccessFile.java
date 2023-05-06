import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TwoBitRandomAccessFile implements AutoCloseable{
	
static final byte[] MASK = {(byte)0b11000000, 0b00110000, 0b00001100, 0b00000011 }; // Java doesn't have unsigned byte type
	
	RandomAccessFile raf;
	
	public TwoBitRandomAccessFile(File file) throws FileNotFoundException {
		raf = new RandomAccessFile(file, "rw");
	}
	
	public TwoBitRandomAccessFile(String filename) throws FileNotFoundException {
		raf = new RandomAccessFile(filename, "rw");
	}
	
	public TwoBitRandomAccessFile(File file, int numTwoBits) throws IOException {
		this(file);
		raf.setLength((long) Math.ceil(numTwoBits / 4.0));
	}
	
	public TwoBitRandomAccessFile(String filename, int numTwoBits) throws IOException {
		this(filename);
		raf.setLength((long) Math.ceil(numTwoBits / 4.0));
	}

	public void set(long pos, int val) throws IOException {
		long bytePos = pos / 4; // find byte of file
		int twoBitPos = (int) (pos % 4); // find bit of byte
		//System.out.printf("Byte %d TwoBit %d\n", bytePos, twoBitPos);
		raf.seek(bytePos); // seek byte in file
		byte b = raf.readByte(); // read the byte
		b = (byte)((b ^ MASK[twoBitPos]) & b); // clear the two-bit
		byte value = (byte) val;
		b = (byte)(b | (value << 2 * (3 - twoBitPos)));
		raf.seek(bytePos); // seek bytePos again (previous readByte() advanced to bytePos + 1)
		raf.writeByte(b); 
	}
	
	public int get(long pos) throws IOException {
		long bytePos = pos / 4; // find byte of file
		int twoBitPos = (int) (pos % 4); // find bit of byte
		raf.seek(bytePos);
		byte b = raf.readByte();
		b = (byte)(b & (MASK[twoBitPos])); // clear other bits
		b = (byte)(MASK[3] & (b >> 2 * (3 - twoBitPos)));
		return b;
	}
	
	
	public void clear() throws IOException {
		for (int i = 0; i < raf.length(); i++) {
			raf.seek(i);
			raf.write(0);
		}
	}
	
	@Override
	public void close() throws Exception {
		raf.close();
	}

	public static void main(String[] args) throws Exception {
		int numTwoBits = 25;
		TwoBitRandomAccessFile braf = new TwoBitRandomAccessFile("test-braf.dat", numTwoBits);
		braf.clear();
		for (int i = 0; i < numTwoBits; i++) {
			braf.set(i, 3);
			for (int j = 0; j < numTwoBits; j++)
				System.out.print(braf.get(j));
			System.out.println();
		}
		System.out.println("Bytes:");
		braf.raf.seek(0);
		int value;
		while ((value = braf.raf.read()) != -1)
			System.out.println(value);
//		braf.raf.setLength(4194304); // large file
		braf.clear();
		braf.close();
	}
}
