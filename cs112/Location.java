import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Location {
	
	public String name, description; // brief, long descriptions
	// mapping of direction Strings to other locations
	public Map<String, Location> exits = new TreeMap<>();
	public boolean isVisited = false;
	public ArrayList<Item> items = new ArrayList<>();
	
	public Location(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		
		if (!isVisited) {
			sb.append("\n" + description);
		}
		
		sb.append("\nPossible exits:");
		for (String direction : exits.keySet()) { // build exits
			sb.append(" " + direction);
		}
		
		sb.append("\nYou see:");
		for (Item item : items) { // build exits
			sb.append(" " + item);
		}
		return sb.toString();
	}
}
