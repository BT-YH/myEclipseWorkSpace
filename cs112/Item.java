
public class Item {
	public String name, description;
	boolean gettable = false;

	public Item(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String toString() {
		return name;
	}
}
