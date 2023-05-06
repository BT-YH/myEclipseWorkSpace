import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class MyTwoBitRandomAccessFile implements AutoCloseable {
	static final byte[] MASK = {(byte) 128, 64, 32, 16, 8, 4, 2, 1}; // Java doesn't have unsigned byte type
	
	RandomAccessFile raf;
	static final HashMap<String, Integer> twoBitValues = new HashMap<String, Integer>();
	
	{
		twoBitValues.put("00", 0);
		twoBitValues.put("01", 1);
		twoBitValues.put("10", 2);
		twoBitValues.put("11", 3);
	}
	
	public MyTwoBitRandomAccessFile(File file) throws FileNotFoundException {
		raf = new RandomAccessFile(file, "rw");
	}
	
	public MyTwoBitRandomAccessFile(String filename) throws FileNotFoundException {
		raf = new RandomAccessFile(filename, "rw");
	}
	
	public MyTwoBitRandomAccessFile(File file, int numTwoBits) throws IOException {
		this(file);
		raf.setLength((long) Math.ceil(numTwoBits / 4.0));
	}
	
	public MyTwoBitRandomAccessFile(String filename, int numTwoBits) throws IOException {
		this(filename);
		raf.setLength((long) Math.ceil(numTwoBits / 4.0));
	}
	
	public void set(long pos, int val) throws IOException {
		pos *= 2;
		long bytePos = pos / 8; // find byte of file
		
		int twoBitPos = (int) (pos % 8) / 2; // find two-bit of byte
		int firstBitPos = (int) (pos % 8);
		int secondBitPos = firstBitPos + 1;
		
		System.out.printf("Byte %d TwoBit %d\n", bytePos, twoBitPos);
		raf.seek(bytePos); // seek byte in file
		byte b = raf.readByte(); // read the byte
		int firstBit = (b & MASK[firstBitPos]) != 0 ? 1 : 0; // get the bit
		int secondBit = (b & MASK[secondBitPos]) != 0 ? 1 : 0;
		
		String twoBits = "" + firstBit + secondBit;
		//System.out.println("The two bits at position " + pos + " " + twoBits);
		
		int twoBitValue = twoBitValues.get(twoBits);
		
		if (twoBitValue != val) { // if there's a change
			raf.seek(bytePos); // seek bytePos again (previous readByte() advanced to bytePos + 1)
			if (twoBitValue == 0) {
				if (val == 1) {
					raf.writeByte(b ^ MASK[secondBitPos]); 
				} else if(val == 2) {
					raf.writeByte(b ^ MASK[firstBitPos]); 
				} else if(val == 3) {
					raf.writeByte(b ^ MASK[secondBitPos]); 
					raf.seek(bytePos);
					b = raf.readByte(); // read the byte
					raf.seek(bytePos);
					raf.write(b ^ MASK[firstBitPos]);
				}
			} else if (twoBitValue == 1) {
				if (val == 0) {
					raf.writeByte(b ^ MASK[secondBitPos]); 
				} else if(val == 2) {
					raf.writeByte(b ^ MASK[firstBitPos]); 
					raf.seek(bytePos);
					b = raf.readByte(); // read the byte
					raf.seek(bytePos);
					raf.writeByte(b ^ MASK[secondBitPos]);
				} else if(val == 3) {
					raf.writeByte(b ^ MASK[firstBitPos]); 
					raf.seek(bytePos);
					b = raf.readByte(); // read the byte
					raf.seek(bytePos);
					raf.writeByte(b ^ MASK[secondBitPos]);
				} 
			} else if (twoBitValue == 2) {
				if (val == 0) {
					raf.writeByte(b ^ MASK[firstBitPos]); 
				} else if(val == 1) {
					raf.writeByte(b ^ MASK[firstBitPos]); 
					raf.seek(bytePos);
					b = raf.readByte(); // read the byte
					raf.seek(bytePos);
					raf.writeByte(b ^ MASK[secondBitPos]);
				} else if(val == 3) {
					raf.writeByte(b ^ MASK[secondBitPos]);
				} 
			} else if (twoBitValue == 3) {
				if (val == 0) {
					raf.writeByte(b ^ MASK[firstBitPos]); 
					raf.seek(bytePos);
					b = raf.readByte(); // read the byte
					raf.seek(bytePos);
					raf.writeByte(b ^ MASK[secondBitPos]);
				} else if(val == 1) {
					raf.writeByte(b ^ MASK[firstBitPos]); 
				} else if(val == 2) {
					raf.writeByte(b ^ MASK[secondBitPos]);
				} 
			} 
		}
//		// Test code
//		
//		raf.seek(bytePos); // seek byte in file
//		b = raf.readByte(); // read the byte
//		firstBit = (b & MASK[firstBitPos]) != 0 ? 1 : 0; // get the bit
//		secondBit = (b & MASK[secondBitPos]) != 0 ? 1 : 0;
//		
//		twoBits = "" + firstBit + secondBit;
//		System.out.println("After process, The two bits at position " + pos + " " + twoBits);
	}
	
	public int get(long pos) throws IOException {
		pos *= 2;
		long bytePos = pos / 8; // find byte of file
		
		int twoBitPos = (int) (pos % 8) / 2; // find two-bit of byte
		int firstBitPos = (int) (pos % 8);
		int secondBitPos = firstBitPos + 1;
		
		raf.seek(bytePos); // seek byte in file
		byte b = raf.readByte(); // read the byte
		int firstBit = (b & MASK[firstBitPos]) != 0 ? 1 : 0; // get the bit
		int secondBit = (b & MASK[secondBitPos]) != 0 ? 1 : 0;
		
		String twoBits = "" + firstBit + secondBit;
		
		int twoBitValue = twoBitValues.get(twoBits);
		return twoBitValue;
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
		MyTwoBitRandomAccessFile braf = new MyTwoBitRandomAccessFile("test-braf.dat", numTwoBits);
		braf.clear();
		for (int i = 0; i < numTwoBits; i ++) {
			braf.set(i, 2);
			for (int j = 0; j < numTwoBits; j ++)
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
