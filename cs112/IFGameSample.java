import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class IFGameSample {
	boolean echoOn = false;
	Location location;
	ArrayList<Item> inventory = new ArrayList<>();
	
	public IFGameSample(boolean echoOn) {
		this.echoOn = echoOn;
		initialize();
		play();
	}
	
	private void play() {
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println(location);
			location.isVisited = true;
			System.out.println("? ");
			String command = in.nextLine();
			if (echoOn) {
				System.out.println(command);
			}
			command = command.toLowerCase();
			String[] words = command.split(" ");
			if (command.equals("quit")) {
				System.exit(0);
			} else if (command.equals("look")) {
				location.isVisited = false;
			} else {
				Location nextLocation = location.exits.get(command);
				if (nextLocation == null) {
					System.out.println("You can't go that way");
				} else {
					location = nextLocation;
				}
			}
		}
	}

	private void initialize() {
		// creating our locations
		Location glatfelter = new Location("Glatfelter", "A.k.a. \"Hogwarts\", this is where the cool kids hang out.");
		Location mccreary = new Location("McCreary", "Where the turtles hang out.");
		Location path = new Location("Path between Glat and McCreary", "You're between the mighty castle Glatfetler Hall, and ... McCreary");
		glatfelter.exits.put("w", path);
		mccreary.exits.put("e", path);
		path.exits.put("e", glatfelter);
		path.exits.put("w", mccreary);
		location = path;
		
		// ... our items
		Item computer = new Item("computer", "I don't know. it could be anything");
		Item squirrel = new Item("squirrel", "The public domain pets of Gburg College");
		glatfelter.items.add(computer);
		path.items.add(squirrel);
		inventory.clear();
	}

	public static void main(String[] args) {
		new IFGameSample(Arrays.binarySearch(args, "-e") >= 0);
	}

}
