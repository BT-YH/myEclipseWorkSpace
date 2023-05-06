import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class IFGame {
	boolean echoOn = false;
	Location location;
	ArrayList<Item> inventory = new ArrayList<>();
	ArrayList<Location> locations = new ArrayList<>();
	ArrayList<Item> items = new ArrayList<>();
	int roundCounter;
	
	static final HashMap<String, String> COMMANDS = new HashMap<>();
	
	{	
		// get commands
		COMMANDS.put("get", "get");
		COMMANDS.put("take", "get");
		COMMANDS.put("obtain", "get");
		COMMANDS.put("grab", "get");
		
		// go commands
		COMMANDS.put("go", "go");
		COMMANDS.put("head", "go");
		COMMANDS.put("proceed", "go");
		
		// drop commands
		COMMANDS.put("drop", "drop");
		COMMANDS.put("throw away", "drop");
		COMMANDS.put("remove", "drop");
		COMMANDS.put("toss", "drop");
		
		// examine commands
		COMMANDS.put("look", "examine");
		COMMANDS.put("check", "examine");
		COMMANDS.put("examine", "examine");
		
		// choose commands
		COMMANDS.put("choose", "choose");
		COMMANDS.put("select", "choose");
		
		// directions
		COMMANDS.put("north", "North");
		COMMANDS.put("n", "North");
		COMMANDS.put("south", "South");
		COMMANDS.put("s", "South");
		COMMANDS.put("east", "East");
		COMMANDS.put("e", "East");
		COMMANDS.put("west", "West");
		COMMANDS.put("w", "West");
	}
	
	

	public IFGame(boolean echoOn) {
		this.echoOn = echoOn;
		initialize();
		play();
	}

	private void initialize() {
		
		// Setting up Locations
		
		Location frontDoor= new Location("Front door", "You are at your Home's front door. Dust. Dust everywhere...\nIt's almost impossible to breathe.");
		//Home
		Location home = new Location("Home", "You are in the living room of your home. You can hear wind wuthering.\nYou are relaxed.");
			//sub location of Home
				//Study Room
				Location study = new Location("Study", "You love your study, sometimes it seems this is the only place you belong. \nYour inspiration and existence sparkles.");
		Location garage = new Location("Garage", "You see your trusty old car. You wanted to name him Carl.\nBut it'd be a little strange isn't it?");

		//Corn Field
		Location cornField = new Location("Corn Field", "The corn field, the middle of nowhere. Corns... \nIt's the only crop left on this planet.");
		Location spaceStation = new Location("Spacestation", "NASA!!!!!!");
//		//Spacestation
//		Location spaceStation = new Location("Spacestation", "You meet an old professor who offers you 2 choices:\n"
//				+ "A: Leave and forget about everything\n"
//				+ "B: Take 50 fertilized eggs to the spaceship, create a new world on another ideal planet that \nhas the same living condition as Earth and leave every people on Earth suffer.");
//		
		//spaceship
		Location spaceship = new Location("spaceship", "You are in the spaceship. \nYou are not allowed to come here! Purchase the full game!");
			
		//Dr.Miller's planet
		Location Mplanet = new Location("Dr.Miller's planet", "Top secret stuff. Please purchase the full to continue.");
		//Dr.Edmund's planet
		Location Eplanet = new Location("Dr.Edmund's planet", "Top secret stuff. Please purchase the full to continue.");
		//Dr.Mann's planet
		Location Aplanet = new Location("Dr.Mann's planet", "Top secret stuff. Please purchase the full to continue.");
		//Blackhole
		Location Blackhole = new Location("Blackhole", "Top secret stuff. Please purchase the full to continue.");
		
		locations.add(frontDoor); 		//0
		locations.add(home); 	  		//1
		locations.add(study); 	  		//2
		locations.add(garage); 	  		//3
		locations.add(cornField); 		//4
		locations.add(spaceStation); 	//5
		locations.add(spaceship);		//6
		
		
		// Setting up exits
		
		//FrontDoor
		frontDoor.exits.put("North", home);
		frontDoor.exits.put("East", garage);
		//Home
		home.exits.put("North", study);
		home.exits.put("East", garage);
		home.exits.put("South", frontDoor);
		//garage - paths
		garage.exits.put("North", home);
		garage.exits.put("East", cornField);
		//garage.exits.put("South", spaceStation);
		garage.exits.put("West", home);
		//Corn Field - paths
//		cornField.exits.put("South", spaceStation);
		cornField.exits.put("West", garage);
		//Study Room - paths
		study.exits.put("South", home);
		//Space Station - paths
		spaceStation.exits.put("North", frontDoor);
		spaceStation.exits.put("South", spaceship);
		//Space Ship
		spaceship.exits.put("North", Mplanet);
		spaceship.exits.put("East", Eplanet);
		spaceship.exits.put("South", Aplanet);
		spaceship.exits.put("West", Blackhole);
		
		
		//initial location
		location = frontDoor;
		
		
		//// setting up items
		
		// front Door
		Item frontDoorKey = new Item("HomeKey", "Open the front door of your home.");
		frontDoorKey.gettable = true;
		Item door = new Item("Door", "A very unspecial door.");						
		Item dust = new Item("Dust", "These things are everywhere");				 
		dust.gettable = true;
		
		frontDoor.items.add(frontDoorKey);
		frontDoor.items.add(door);
		frontDoor.items.add(dust);
		
	
		// home
		Item coffeeMug = new Item("CoffeeMug", 									
				"Sorry, you cannot drink coffee in this free-to-play version of the game.");
		coffeeMug.gettable = true;
		Item chair = new Item("Chair", "A chair...");   							  
		Item table = new Item("Table", "A table...");								  		
		home.items.add(coffeeMug);
		home.items.add(chair);
		home.items.add(table);
		
		
		//garage
		Item carKey = new Item("CarKey", "Your mighty key to a horse with the power of over 300."); 
		carKey.gettable = true;
		garage.items.add(carKey);
		
		//cornfield
		Item corn = new Item("Corn", "Tasty corn from gettysburg. Cannot be "
				+ "eaten in this free-to-play version \nPurchase the full game to unlock functionality"); 
		corn.gettable = true;
		for (int i = 0; i < 20 + (new Random(0)).nextInt(30); i++) {
			cornField.items.add(corn);
		}
		
		items.add(frontDoorKey); //0
		items.add(door);		 //1
		items.add(dust);		 //2
		items.add(coffeeMug); 	 //3
		items.add(chair);		 //4
		items.add(table);		 //5
		items.add(carKey);		 //6
		items.add(corn);		 //7
		
		
		
		//Initialize inventory
		inventory.clear();
		roundCounter = 0;
	}


	private void play() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		// Initializing intermediate locations
		
		
		
		System.out.println("\n\nA.C. 2062");
		System.out.println("You are a forlorn man, striving to exist on this dusty, withering planet, Earth.\n");
		
		while (true) {
			//Setting up get and go command
			boolean isGo = false, isTake = false, isDrop = false, isExamine = false;
			roundCounter++;
			
			if (location.name.equals("Spacestation")) {
				System.out.println("Congratuations! You have reached NASA! Now a whole new adventure awaits! "
						+ "\n This is the end of this free-to-play. Purchase the game to unlock full story");
			}
			
			if (roundCounter == 10) {
				System.out.println("You noticed a sandstorm at the horizon...");
				System.out.println("\"Not again...\", you said to yourself.");
				System.out.println("\"I still have a couple hours?\"");
			} 
			if (roundCounter == 20) {
				System.out.println("The sandstorm is here!");
				System.out.println("You realized you forgot to shut your window in the Study.");

				for (Location location : locations) {
					int bound = (int) (5 * Math.random());
					for (int i = 0; i < bound; i++) {
						location.items.add(items.get(2));
					}
				}

				//Study Room
				Item susDust = new Item("SuspiciousDust", "Appeared after the dust storm. It looks like its arranged in some pattern.");
				susDust.gettable = true;
				for (Location location : locations) {
					if (location.name.equals("Study")) {
						location.items.add(susDust);
					}
				}
			}
			
			
			System.out.print("You are at: ");
			System.out.println(location);
			location.isVisited = true;

			System.out.print("Your action: ");
			
			//take in command
			String command = in.nextLine();
//			command = command.toLowerCase();
			//split command
			String[] words = command.split(" "); 
			
			//detecting get and go command
			for (String word : words) {
				if (COMMANDS.containsKey(word.toLowerCase())) {
					String value = COMMANDS.get(word.toLowerCase());
					if (value.equals("get")) {
						isTake = true;
					} else if (value.equals("go")) {
						isGo = true;
					} else if (value.equals("drop")) {
						isDrop = true;
					} else if (value.equals("examine")) {
						isExamine = true;
					}
				}
			}	
			
			
			if (echoOn)
				System.out.println(command);
			//Quit
			if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("give up")) {
				System.out.println("Please have more determination.");
				System.out.println("Would you like to restart? (y/n)");
				String input = in.next();
				if (input.equalsIgnoreCase("y")) {
					restart();
					return;
				} 
				System.exit(0);
			}
			else if (command.equalsIgnoreCase("Restart")) {
				restart();
				return;
			}
			//Look around
			else if (command.equalsIgnoreCase("look"))
				location.isVisited = false;
			//Inventory
			else if (command.equalsIgnoreCase("inventory") || command.equalsIgnoreCase("i")) {
				System.out.print("\nYou are holding: ");
				for (Item item : inventory) 
					System.out.print("-" + item + "- ");
				System.out.println();
			}
			
			//Running up get and go command
			//GO
			else if (isGo) {
				Location nextLocation = location;
				for (String word : words) {
					if (COMMANDS.containsKey(word.toLowerCase())) {
						String value = COMMANDS.get(word.toLowerCase());
						if (location.exits.get(value) != null)
							nextLocation = location.exits.get(value);
					}
				}
				if (nextLocation.equals(location)) {
					System.out.println("\nYou can't go that way");
				} else if (nextLocation.name.equals("Home") && !location.name.equals("Study")) { // check homekey
					boolean haveKey = false;
					for (Item i : inventory) {
						if (i.name.equals("HomeKey")) {
							haveKey = true;
							location = nextLocation;
							break;
						}
					}
					if (!haveKey) {
						System.out.println("You need your key to get in.");
					}
				}
				else 
					location = nextLocation;
			} 
			//GET
			else if (isTake && !location.items.isEmpty()) {
				boolean added = false;
				for (String word : words) {
					for (Item item : location.items) {
						if (word.equalsIgnoreCase(item.toString()) && item.gettable) {
							inventory.add(item);
							location.items.remove(item);
							added = true;
							break;
						}
					}
				}
				if (!added)
					System.out.println("\nYou can't get that item");
			}
			//DROP
			else if (isDrop) {
				for (String word : words) {
					for (Item item : inventory) {
						if (word.equalsIgnoreCase(item.toString())) {
							inventory.remove(item);
							location.items.add(item);
							break;
						}
					}
				}
			}
			else if (isExamine) {
				boolean examined = false;
				for (String word : words) {
					for (Item item : inventory) {
						if (word.equalsIgnoreCase(item.toString())) {
							System.out.println("\n" + item.name + ": " + item.description);
							examined = true;
							if (word.equalsIgnoreCase("SuspiciousDust")) {
								System.out.println("You try to decode the binary. You get a set of coordinates. \n-New location unlocked-");
								locations.get(3).exits.put("South", locations.get(5));
								locations.get(4).exits.put("South", locations.get(5));
							}
							break;
						}
					}
					if (examined) {
						break;
					}
					for (Item item : location.items) {
						if (word.equalsIgnoreCase(item.toString())) {
							System.out.println("\n" + item.name + ": " + item.description);
							examined = true;
							break;
						}
					}
				}
				if (!examined)
					System.out.println("\nI do not know what item you are examining.");
			} 
			//FINAL
			else {
				System.out.println("\nCommand unknown.");
			}
			System.out.println();
		}
	}
	
	private void restart() {
		initialize();
		play();
	}

	public static void main(String[] args) {
		new IFGame(Arrays.binarySearch(args, "-e") >= 0);

	}
}
