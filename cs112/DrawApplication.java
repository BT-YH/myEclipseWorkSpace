import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DrawApplication extends Application {
	double pressX, pressY;
	Pane drawPane = new Pane(); // *** Create a new draw pane (in case we want to add other root components). 
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {

		// Create scene bottom-up
		BorderPane rootPane = new BorderPane(); // Create a new root pane. 
		// ***
		drawPane.prefWidthProperty().bind(rootPane.widthProperty()); // resize drawPane with rootPane
		drawPane.prefHeightProperty().bind(rootPane.heightProperty());
		
		rootPane.setCenter(drawPane); // *** Add draw pane to root pane's children
		rootPane.setTop(getMenu());
		
		Scene scene = new Scene(rootPane, 500, 500); // Create a scene with the root pane
		primaryStage.setTitle("Draw Application"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		// Add handlers for mouse events...
		
		// (1) using inner class handler defined below
		drawPane.setOnMousePressed(new MyMousePressedHandler()); 

		// (2) using anonymous inner class handler defined here
		drawPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Mouse released (" + e.getX() + "," + e.getY() + ").");
				SerializableLine line = new SerializableLine(pressX, pressY, e.getX(), e.getY()); // ***
				drawPane.getChildren().add(line);
			}
		});
		
		// Add keyboard listener for Control-z undo event (3) using lambda expression
		final KeyCombination CTRL_Z = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
		drawPane.setOnKeyPressed(e -> {
			System.out.println("Key pressed.");
			if (CTRL_Z.match(e)) {
				System.out.println("Undo.");
				int numLines = drawPane.getChildren().size();
				if (numLines > 0)
					drawPane.getChildren().remove(numLines - 1);
			}
		});
		
		drawPane.requestFocus();
	}
	
	class MyMousePressedHandler implements EventHandler<MouseEvent> { // inner class
		@Override
		public void handle(MouseEvent e) {
			System.out.println("Mouse pressed (" + e.getX() + ", " + e.getY() + ").");
			System.out.println("Event source: " + e.getSource());
			System.out.println("Event: " + e);
			System.out.println("Event handler: " + this);
			pressX = e.getX();
			pressY = e.getY();
		}
	}

	private Node getMenu() { // *** From Mandelbrot.java
		MenuBar menuBar = new MenuBar();

		// --- Menu File
		Menu menuFile = new Menu("File");
		MenuItem itemOpen = new MenuItem("Open");
		final FileChooser fileChooser = new FileChooser();
		itemOpen.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(null);
            if (file != null) {
            	try (
        				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
        			) {
        			@SuppressWarnings("unchecked")
					ArrayList<SerializableLine> lines = (ArrayList<SerializableLine>) input.readObject();
        			drawPane.getChildren().clear();
        			for (SerializableLine SerializableLine : lines)
        				drawPane.getChildren().add(SerializableLine); 
        		} catch (Exception ex) {
        			ex.printStackTrace();
        		}
            }
		});
		MenuItem itemSave = new MenuItem("Save");
		itemSave.setOnAction(e -> {
			File file = fileChooser.showSaveDialog(null);
            if (file != null) {
            	try (
        				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
        			) {
            		ArrayList<SerializableLine> lines = new ArrayList<>();
            		for (Node node : drawPane.getChildren())
            			lines.add((SerializableLine) node);
            		output.writeObject(lines);
        		} 
            	catch (IOException ex) {
            		ex.printStackTrace();
            	}
            }
		});
		MenuItem itemExit = new MenuItem("Exit");
		itemExit.setOnAction(e -> {System.exit(0);});
		menuFile.getItems().addAll(itemOpen, itemSave, itemExit);
		
		menuBar.getMenus().add(menuFile);
		return menuBar;
	}

	// TODO: Create and use SerializableLine that extends Line and implements Serializable
	class SerializableLine extends Line implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8458703635112503912L;
		public SerializableLine() {
			super();
		}
		
		public SerializableLine(double startX, double startY, 
								double endX, double endY) {
			super(startX, startY, endX, endY);
		}
		
		private void writeObject(java.io.ObjectOutputStream out)
				throws IOException {
			out.writeDouble(startXProperty().get());
			out.writeDouble(startYProperty().get());
			out.writeDouble(endXProperty().get());
			out.writeDouble(endYProperty().get());

		}
		private void readObject(java.io.ObjectInputStream in)
				throws IOException, ClassNotFoundException {
			startXProperty().set(in.readDouble());
			startYProperty().set(in.readDouble());
			endXProperty().set(in.readDouble());
			endYProperty().set(in.readDouble());
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}
