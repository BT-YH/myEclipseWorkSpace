import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.scene.paint.Color;

public class MandelbrotColors {
	public static final int SMOOTH_SPECTRUM = 0, ILLUSION = 1, ULTRACOLOR = 2;
	public static final int NUM_PROCESSORS; // ***
	public static final String[] paletteNames = {"Smooth Spectrum", "Illusion", "UltraColor"};
	
	// Illusion color palette from https://www.moillusions.com/psychedelic-screen-melt-illusion/
	private static Color[] illusion = new Color[] {Color.rgb(0, 0, 0), Color.rgb(147,217,0), Color.rgb(147,217,0), Color.rgb(255, 255, 255), Color.rgb(152, 0, 201), Color.rgb(152, 0, 201)};

	// Ultrafractal color palette based on https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia
	private static Color[] ultraColor;

	private static ExecutorService executor; // ***
	static {
		Scanner in;
		try {
//			in = new Scanner(new File("ultrafractalcolors.txt"));
			in = new Scanner(new URL("http://cs.gettysburg.edu/~tneller/cs112/data/ultrafractalcolors.txt").openStream());
			ultraColor = new Color[in.nextInt()];
			for (int i = 0; i < ultraColor.length; i++)
				ultraColor[i] = Color.rgb(in.nextInt(), in.nextInt(), in.nextInt());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();// *** 
	}
	
	public static Color[][] compute(int width, int height, double centerX, double centerY, double zoom, int maxIter,
			int palette) {
		Color[][] colors = new Color[width][height];
		// ***
		executor = Executors.newFixedThreadPool(NUM_PROCESSORS); 
		
		for (int p = 0; p < NUM_PROCESSORS; p++) {
			final int procNum = p;
			executor.execute(() -> {
				for (int y = procNum; y < height; y += NUM_PROCESSORS) {
					for (int x = 0; x < width; x++) {
						double zx = 0;
						double zy = 0;
						double cX = (x - width / 2) / zoom + centerX;
						double cY = (y - height / 2) / zoom + centerY;
						int iter = maxIter;
						while (zx * zx + zy * zy < 4 && iter > 0) {
							double tmp = zx * zx - zy * zy + cX;
							zy = 2.0 * zx * zy + cY;
							zx = tmp;
							iter--;
						}
						double size = Math.sqrt(zx * zx + zy * zy);
						Color color = Color.WHITE;
						if (palette == SMOOTH_SPECTRUM) {
							// Smooth coloring from http://stackoverflow.com/questions/369438/smooth-spectrum-for-mandelbrot-set-rendering
							double nSmooth = (maxIter - iter) + 1 - Math.log(Math.log(size))/Math.log(2);
							double smoothColor = nSmooth / maxIter;
							color = Color.hsb((float)(3600 * smoothColor), 0.6f, 1.0f);
						}
						else if (palette == ILLUSION) {
							color = illusion[(maxIter - iter + 1) % illusion.length];
						}
						else if (palette == ULTRACOLOR) {
							double paletteSmoothed = Math.log(Math.log(size) / Math.log(2)) / Math.log(2);
							int colorI = (int)(Math.sqrt((maxIter - iter) + 1 - paletteSmoothed) * 256);
							color = ultraColor[colorI % ultraColor.length];
						}
						colors[x][y] = (iter > 0) ? color : Color.BLACK;
					}
				}
			});
		}
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return colors;
	}
}












