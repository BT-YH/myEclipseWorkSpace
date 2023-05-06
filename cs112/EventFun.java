import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class EventFun extends Application {
	
	double pressX, pressY;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane rootPane = new Pane();
		Pane drawPane = new Pane();
		drawPane.prefWidthProperty().bind(rootPane.widthProperty());
		drawPane.prefHeightProperty().bind(rootPane.heightProperty());
		rootPane.getChildren().add(drawPane);
		Scene scene = new Scene(rootPane, 500, 500);
		primaryStage.setTitle("Draw Application");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Add handlers for mouse events...
		
		// (1) using inner class handler defined below
		drawPane.setOnMousePressed(new MyMousePressedHandler());
		
		
		// (2) using anonymous inner class defined here
		drawPane.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.printf("Mouse released (%f, %f)\n", event.getX(), event.getY());
				Line line = new Line(pressX, pressY, event.getX(), event.getY());
				drawPane.getChildren().add(line);
			}
			
		});
		
		// Add a keyboard listener for Control-z undo event
		// (3) using lambda expression
		
		final KeyCombination CTRL_Z = new KeyCodeCombination(KeyCode.Z,
				KeyCombination.CONTROL_DOWN);
		scene.setOnKeyPressed(e -> {
			System.out.println("Key pressed");
			if (CTRL_Z.match(e)) {
				System.out.println("Undo");
				int numLines = drawPane.getChildren().size();
				if (numLines > 0) {
					drawPane.getChildren().remove(numLines - 1);
				}
			}
		});
		
		drawPane.requestFocus();
	}
	
	class MyMousePressedHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			System.out.printf("Mouse pressed (%f, %f)\n", event.getX(), event.getY());
			System.out.println("Event source: " + event.getSource());
			System.out.println("Event: " + event);
			System.out.println("Event handler: " + this);
			pressX = event.getX();
			pressY = event.getY();
			
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}
	
	
}
