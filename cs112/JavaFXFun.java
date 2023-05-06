import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class JavaFXFun extends Application {
	
	DoubleProperty paneAngle = new SimpleDoubleProperty(0);
	StringProperty paneAngleStr = new SimpleStringProperty("Angle 0");
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button button = new Button("New Stage");
		Pane pane = new Pane();
		// getChildren() returns an instance of javafx.collections.ObservableList
		// behaves like ArrayList
		pane.getChildren().add(button); // button is a node
		
		Scene scene = new Scene(pane, 200, 200);
		primaryStage.setTitle("Primary Stage Title");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// java functional programming
		// lambda expression - function
		button.setOnAction(e -> {
			Button button2 = new Button("New Stage 2");
			Pane pane2 = new Pane();
			pane2.getChildren().add(button2); // button is a node
			Scene scene2 = new Scene(pane2, 200, 200);
			Stage stage2 = new Stage();
			stage2.setTitle("Stage2 Title");
			stage2.setScene(scene2);
			stage2.setResizable(false);
			stage2.show();
		});
		
		Circle circle = new Circle();
//		circle.setCenterX(100);
//		circle.setCenterY(100);
		circle.setRadius(50);
		circle.setFill(Color.ORANGE);
		circle.setStroke(Color.BLUE);
		
		pane.getChildren().add(0, circle);

		// bind centerX/Y properties to be relative to the width/height of the stage
		circle.centerXProperty().bind(pane.widthProperty().divide(2));
		circle.centerYProperty().bind(pane.heightProperty().divide(2));
		
		Pane subpane = new StackPane();
		subpane.setPrefSize(100, 100);
		subpane.setTranslateX(100);
		subpane.setTranslateY(100);
		subpane.rotateProperty().bind(paneAngle);
		pane.getChildren().add(subpane);
		
		Rectangle square = new Rectangle(100, 100);
		square.setStyle("-fx-stroke: black; -fx-fill: red"); // in the style of CSS
		subpane.getChildren().add(square);
		
		Label label = new Label();
		label.textProperty().bind(paneAngleStr);
		subpane.getChildren().add(label);
		
		Button angleBtn = new Button("Increase Angle");
		pane.getChildren().add(angleBtn);
		angleBtn.setTranslateY(35);
		angleBtn.setOnAction(e -> {
			paneAngle.set(paneAngle.get() + 5);
			paneAngleStr.set("Angle " + (int) paneAngle.get());
			square.setFill(Color.hsb(paneAngle.get(), 1, 1)); // factory method: hue, saturation, brightness
		});
		
		label.setFont(Font.font("serif", FontWeight.BOLD,
				FontPosture.ITALIC, 16)); // factory method: static contructor
	}

	public static void main(String[] args) {
		launch(args); // creating a stage object, calls start()
	
	}

}
