
import java.io.File;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFXShapeFun extends Application {

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {

		// Default Pane layout: Create each Node (ImageView/Shape) and add to a default Pane layout one by one.	
		Pane pane = new Pane(); // Create a new pane. 

		// image
		String path = "/Accounts/turing/faculty/tneller/public_html/cs112/examples/images/";
		Image image = new Image(new File(path + "bulbOnSmall.gif").toURI().toString());
		ImageView imageView = new ImageView(image);
		// technically _not_ a Shape; like a Shape, it's a Node in the Scene graph
		
		// text
		Text text = new Text(50, 100, "text"); // x,y-coordinates, text 
		
		// line
		Line line = new Line(0, 0, 100, 50);
		line.setStroke(Color.RED);
		
		// rectangle
		Rectangle rectangle = new Rectangle(0, 150, 100, 50); // u-l x, y, w, h
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(Color.ORANGE);
		rectangle.setArcWidth(15);
		rectangle.setArcHeight(25);
		rectangle.setRotate(45);

		// circle
		Circle circle = new Circle(150, 50, 30); // center x, y, r
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.YELLOW);
		
		// ellipse
		Ellipse ellipse = new Ellipse(150, 150, 50, 25); // center x, y, x-radius, y-radius
		ellipse.setStroke(Color.BLACK);
		ellipse.setFill(Color.GREEN);
		
		// arc
		Arc arc = new Arc(150, 100, 80, 80, 120, 70); // center x, y, x-radius, y-radius, start degrees, length
		arc.setStroke(Color.AQUA);
		arc.setFill(Color.TRANSPARENT);
		arc.setType(ArcType.OPEN); // also try ROUND, CHORD
		
		// polygon
		Polygon polygon = new Polygon();
		polygon.setStroke(Color.BLUE);
		polygon.setFill(Color.TRANSPARENT);
		ObservableList<Double> list = polygon.getPoints();
		double[] coords = {100, 100, 190, 10, 10, 10};
		for (double coord : coords)
			list.add(coord);
		
		// polyline
		Polyline polyline = new Polyline();
		polyline.setStroke(Color.PURPLE);
		polyline.setFill(Color.TRANSPARENT);
		list = polyline.getPoints();
		for (double coord : coords)
			list.add(coord);
		
		pane.getChildren().add(imageView); 
		pane.getChildren().add(text); 
		pane.getChildren().add(line);
		pane.getChildren().add(rectangle);
		pane.getChildren().add(circle);
		pane.getChildren().add(ellipse);
		pane.getChildren().add(arc);
		pane.getChildren().add(polygon);
		pane.getChildren().add(polyline);
		
		Scene scene = new Scene(pane, 200, 200); // Create a scene with the pane
		primaryStage.setTitle("Shape Demo"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage


	}

	public static void main(String[] args) {
		launch(args);
	}

}
