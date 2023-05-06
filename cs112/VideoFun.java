import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VideoFun extends Application {

//	public static final String VIDEO_URL = "https://www.nasa.gov/downloadable/videos/nasa_-_twisting_solar_eruption_and_flare.mp4";
	public static final String VIDEO_URL = "http://cs.gettysburg.edu/~tneller/videos/example/nasa_-_twisting_solar_eruption_and_flare.mp4";

	public static final String PLAY_URL = "http://cs.gettysburg.edu/~tneller/images/media-buttons/play.png",
			PAUSE_URL = "http://cs.gettysburg.edu/~tneller/images/media-buttons/pause.png",
			FF_URL = "http://cs.gettysburg.edu/~tneller/images/media-buttons/fast-forward.png",
			BEGIN_URL = "http://cs.gettysburg.edu/~tneller/images/media-buttons/begin.png";
	// Button icons from https://openclipart.org/detail/17304/deck-or-vcr-button

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Code below is adapted from Y. Daniel Liang's MediaDemo.java

		// Video
		// Public domain source: http://svs.gsfc.nasa.gov/goto?11670
		// See also https://www.nasa.gov/multimedia/downloadable-video-page/
		// 1280x720 mp4 video
		
		// Create Media, MediaPlayer, MediaView
		Media media = new Media(VIDEO_URL);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);
		
		// Create control buttons, slider and set handlers
		
		// Begin button
		Button beginButton = new Button();
		beginButton.setGraphic(new ImageView(new Image(BEGIN_URL)));
		beginButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));
		
		// Play/pause button
		Button playButton = new Button();
		ImageView playIV = new ImageView(new Image(PLAY_URL));
		ImageView pauseIV = new ImageView(new Image(PAUSE_URL));
		playButton.setGraphic(playIV);
		playButton.setOnAction(e -> {
			if (playButton.getGraphic().equals(playIV)) {
				mediaPlayer.setRate(1);
				mediaPlayer.play();
				playButton.setGraphic(pauseIV);
			}
			else {
				mediaPlayer.pause();
				playButton.setGraphic(playIV);
			}
		});

		// Fast-forward button
		Button ffButton = new Button();
		ffButton.setGraphic(new ImageView(new Image(FF_URL)));
		double[] rates = {1.0, 2.0, 4.0, 8.0};
		ffButton.setOnAction(e -> {
			mediaPlayer.play();
			if (playButton.getGraphic().equals(playIV))
				playButton.setGraphic(pauseIV);
			double rate = mediaPlayer.getRate();
			int index = Arrays.binarySearch(rates, rate);
			mediaPlayer.setRate(index == -1 ? 2 : rates[(index + 1)
			                                            % rates.length]);
		});

		// Slider volume
		Slider slVolume = new Slider();
		slVolume.setPrefWidth(150);
		slVolume.setMaxWidth(Region.USE_PREF_SIZE);
		slVolume.setMinWidth(30);
		slVolume.setValue(50);
		mediaPlayer.volumeProperty()
			.bind(slVolume.valueProperty().divide(100));

		// Put controls in HBox
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(beginButton, playButton, ffButton,
				new Label("Volume"), slVolume);

		// Place MediaView and HBox in a BorderPane
		BorderPane pane = new BorderPane();
		pane.setCenter(mediaView);
		pane.setBottom(hBox);

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight());

		// Listen for when video loads and resizes media
		InvalidationListener mediaSizeListener = e -> {
			mediaView.setFitHeight(400);
			mediaView.setPreserveRatio(true);
			primaryStage.sizeToScene();
		};
		media.widthProperty().addListener(mediaSizeListener);
		media.heightProperty().addListener(mediaSizeListener);

		// Finalize and show primary stage
		primaryStage.setTitle("JavaFX Video Demo"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}

}
