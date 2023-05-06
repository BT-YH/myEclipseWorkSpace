import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ClockDemo extends Application {

	private static final double RADIUS = 100, CIRCLE_RADIUS = .95 * RADIUS, BACK_END_FRACTION = .1;
	private static final double[] HAND_LENGTHS = {.5 * RADIUS, .8 * RADIUS, .9 * RADIUS}; // hour, minute, second hand lengths
	private static final Color[] COLORS = {Color.BLACK, Color.BLACK, Color.RED}; // hour, minute, second colors
	private static final int[] STROKE_WIDTHS = {5, 3, 1}; // hour, minute, second widths

	private Pane pane;
	private Line[] hands = new Line[HAND_LENGTHS.length]; // hour, minute, second hands
	// hour, minute, second hand end points
	private DoubleProperty[] x1s = new DoubleProperty[hands.length];
	private DoubleProperty[] y1s = new DoubleProperty[hands.length];
	private DoubleProperty[] x2s = new DoubleProperty[hands.length];
	private DoubleProperty[] y2s = new DoubleProperty[hands.length];
	
	public void start(Stage primaryStage) {
		pane = new Pane();
		Circle circle = new Circle(RADIUS, RADIUS, CIRCLE_RADIUS);
		circle.setFill(Color.WHITE);
		pane.getChildren().add(circle);
		for (int i = 0; i < hands.length; i++) {
			hands[i] = new Line();
			hands[i].setStroke(COLORS[i]);
			hands[i].setStrokeWidth(STROKE_WIDTHS[i]);
			x1s[i] = hands[i].startXProperty();
			y1s[i] = hands[i].startYProperty();
			x2s[i] = hands[i].endXProperty();
			y2s[i] = hands[i].endYProperty();
			pane.getChildren().add(hands[i]);
		}

		new Thread(() -> {
			try {
				while (true) {
					// compute time and continuous hand angles
					LocalDateTime now = LocalDateTime.now();
					int hour = now.getHour();
					int minute = now.getMinute();
					int second = now.getSecond();
					int millis = now.get(ChronoField.MILLI_OF_SECOND);
					double dSecond = (second * 1000 + millis) / 1000.0;
					double dMinute = (minute * 60 + dSecond) / 60.0;
					double dHour =  (hour * 60  + dMinute) / 60.0;
					double hAngle =  (Math.PI / 2) - dHour * 2 * Math.PI  / 12;
					double mAngle =  (Math.PI / 2) - dMinute * 2 * Math.PI  / 60;
					double sAngle = (Math.PI / 2) - dSecond * 2 * Math.PI / 60;
					double[] angles = {hAngle, mAngle, sAngle};
					
					// change hand positions via their properties
					for (int i = 0; i < hands.length; i++) {
						x1s[i].set(RADIUS + Math.cos(angles[i]) * HAND_LENGTHS[i]);
						x2s[i].set(RADIUS - Math.cos(angles[i]) * HAND_LENGTHS[i] * BACK_END_FRACTION);
						y1s[i].set(RADIUS - Math.sin(angles[i]) * HAND_LENGTHS[i]);
						y2s[i].set(RADIUS + Math.sin(angles[i]) * HAND_LENGTHS[i] * BACK_END_FRACTION);
					}

					// Delay until next update so as to not swamp GUI with updates.
					// (Try sleep(1) to see why. Modern processors will likely fail after a bit.)
					Thread.sleep(1000 / 30); // 30 fps (frames per second) - OK for slow, static scenes
					
					// if we want to see the second hand tick once per second, we can comment the previous sleep
					// and uncomment this:
//					Thread.sleep(1000 - System.currentTimeMillis() % 1000); // seeks to synch with seconds
				}
			}
			catch (InterruptedException e) {
			}
		}).start();

		Scene scene = new Scene(pane, 2 * RADIUS, 2 * RADIUS); 
		primaryStage.setTitle("Clock"); 
		primaryStage.setScene(scene); 
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
