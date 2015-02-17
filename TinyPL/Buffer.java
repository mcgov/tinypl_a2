package tinypl;
import java.io.*;

public class Buffer {

		private String line = "";
		private int position = 0;
		private DataInputStream inStream;

		public Buffer(DataInputStream i) {
			this.inStream = i;
		} // Buffer

		public char getChar() {
			position++;
			if (position >= line.length()) {
				try {
					line = inStream.readLine();
				} catch (Exception e) {
					System.err.println("Invalid read operation");
					System.exit(1);
				}
				if (line == null)
					System.exit(0);
				position = 0;
				// System.out.println(line);
				line = line + "\n";
			}
			return line.charAt(position);
		}

	} // class Buffer
 




