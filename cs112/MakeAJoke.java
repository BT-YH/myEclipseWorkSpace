import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MakeAJoke {
	private ArrayList<ArrayList<String>> jokes = new ArrayList<>();
	
	public MakeAJoke () {}
	
	public String getJoke() {
		Random random = new Random();
		ArrayList<String> joke = jokes.get(random.nextInt(jokes.size()));
		String jokeString = "";
		for (int i = 1; i < joke.size(); i++) {
			jokeString += (joke.get(i)+ "\n");
		}
		return jokeString;
	}
	
	public String getJoke(int index) {
		ArrayList<String> joke = jokes.get(index);
		String jokeString = "";
		for (int i = 1; i < joke.size(); i++) {
			jokeString += (joke.get(i)+ "\n");
		}
		return jokeString;
	}
	
	public void addJoke(String jokeString) {
		ArrayList<String> joke = new ArrayList<>();
		joke.add("joke:");
		Scanner in = new Scanner(jokeString);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			joke.add(line);
		}
		jokes.add(joke);
	}
	
	public int size() {
		return jokes.size();
	}
	
	public boolean loadJokes(String filename) {
		Scanner in = null; // declare scanner before the try block! good practice
		int index = -1;
		try { 
			in = new Scanner(new File(filename));
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if (line.equals("joke:")) {
					ArrayList<String> joke = new ArrayList<>();
					joke.add(line);
					jokes.add(joke);
					index++;
				} else {
					if (line.contains("joke:")) {
						return false;
					}
					jokes.get(index).add(line);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No such file: " + filename);
			return false;
		} finally {
			if (in != null)
				in.close();
		}
		return true;
	}
	
	public boolean saveJokes(java.lang.String filename) {
		try (PrintWriter out = new PrintWriter(new File(filename))) {
			for (ArrayList<String> joke: jokes) {
				for (String line : joke) {
					out.println(line);
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		String filename = "jokes.txt";
		MakeAJoke jokes = new MakeAJoke();
		jokes.loadJokes(filename);
		System.out.print(jokes.getJoke());
	}
}
