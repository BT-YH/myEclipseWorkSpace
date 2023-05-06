/* Usage: java ModularPascalsTrianglePlot <rows and columns> <mod>
 * Example: java ModularPascalsTrianglePlot 400 5
 * Try mod 2 - 11, primes (2, 3, 5, 7, 11, 107). */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

// NOTE: DO NOT EDIT.  This is for visual testing of your ModularPascalsTriangle implementation.  If an error occurs here, 
// it should be corrected _there_.

public class ModularPascalsTrianglePlot extends JPanel {
	private static final long serialVersionUID = 5946063796147234013L;
	private ModularPascalsTriangle mpt;
	private int rows, cols, mod; 

	/**
	 * Plot the given ModularPascalsTriangle data
	 * @param mpt - ModularPascalsTriangle data
	 */
	public ModularPascalsTrianglePlot(ModularPascalsTriangle mpt) {
		this.mpt = mpt;
		this.rows = mpt.getRows(); 
		this.cols = mpt.getCols();
		this.mod = mpt.getMod();
		if (rows == 0) throw new RuntimeException("Error: Your ModularPascalsTriangle object returns 0 from getRows()");
		if (cols == 0) throw new RuntimeException("Error: Your ModularPascalsTriangle object returns 0 from getCols()");
		if (mod == 0) throw new RuntimeException("Error: Your ModularPascalsTriangle object returns 0 from getMod()");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = getWidth();
		int height = getHeight();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);

		// Compute colors
		Color[] colors = new Color[mod];
		colors[0] = Color.WHITE;
		for (int i = 1; i < mod; i++) {
			float frac = (mod == 2) ? 1f : (float) (i - 1) / (mod - 2);
			if (frac < 1.0f / 3) { // red to yellow
				frac *= 3;
				colors[i] = new Color(1f, frac, 0f);
			}
			else if (frac < 2.0f / 3) { // yellow to green
				frac *= 3;
				frac -= 1;
				colors[i] = new Color((1f - frac), 1f, 0f);
			}
			else { // green to blue
				frac *= 3;
				frac -= 2;
				colors[i] = new Color(0f, (1f - frac), frac);
			}
		}

		// Compute units
		double widthDiameters = cols + Math.cos(Math.PI / 3) * (rows - 1);
		double heightDiameters = 1 + Math.sin(Math.PI / 3) * (rows - 1);
		if (rows == 0 || cols == 0) {
			System.err.println("Zero dimension in data");
			System.exit(1);
		}
		if (width == 0 || height == 0)
			return;
		double circleRadius = (widthDiameters / heightDiameters > (double) width / height) ? width / widthDiameters / 2.0 : height / heightDiameters / 2.0;

		// Plot rows
		double rowX = 2 * circleRadius * Math.cos(Math.PI / 3) * (rows - 1) + circleRadius;
		double rowY = circleRadius;
		for (int r = 0; r < rows; r++) {

			// Plot columns
			double centerX = rowX;
			double centerY = rowY;
			for (int c = 0; c < cols; c++) {
				int value = mpt.getValue(r, c);
				if (value < 0) throw new RuntimeException(String.format("Error: ModularPascalsTriangle returns a negative value from getValue(%d,%d)", r, c));
				if (value >= mod) throw new RuntimeException(String.format("Error: ModularPascalsTriangle is returning a value >= mod from getValue(%d,%d)", r, c));
				g2.setColor(colors[value]);
				g2.fill(new Ellipse2D.Double(centerX - circleRadius, centerY - circleRadius, 2 * circleRadius, 2 * circleRadius));
				centerX += 2 * circleRadius;
			}

			// Calculate next row start	
			rowX -= Math.cos(Math.PI / 3) * 2 * circleRadius;
			rowY += Math.sin(Math.PI / 3) * 2 * circleRadius;
		}

	}

	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			int size, mod;
			if (args.length > 0) 
				size = Integer.parseInt(args[0]);
			else {
				System.out.print("Size (>= 1)? ");
				size = in.nextInt();
			}
			if (args.length > 1) 
				mod = Integer.parseInt(args[1]);
			else {
				System.out.print("Modulus (>= 2)? ");
				mod = in.nextInt();
			}
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			int frameHeight = 500;
			frame.setSize(frameHeight + (int) (frameHeight * Math.cos(Math.PI / 3)), frameHeight);
			frame.setTitle(String.format("Pascal's Triangle Size:%d Modulus:%d", size, mod));
			frame.add(new ModularPascalsTrianglePlot(new ModularPascalsTriangle(size, size, mod)));
			frame.setVisible(true);
		}
		catch (Exception e) {
			System.err.println("Usage: java ModularPascalsTrianglePlot <size> <modulus>");
			e.printStackTrace();
		}
	}


}
