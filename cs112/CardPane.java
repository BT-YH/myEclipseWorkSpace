import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CardPane extends Pane {	
	/**
	 * Standard Poker card width/height is 635/889.
	 */
	public static final double ASPECT_RATIO = 635.0/889.0; 
	public static final char[] RANKS = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
	public static final char[] SUITS = {'C', 'H', 'S', 'D'};


	public CardPane(StringProperty str) {
		super();

		// Creating rectangle
		Rectangle rectangle = new Rectangle(); // u-l x, y, w, h

		// bind rectangle heightProperty to the pane's heightProperty
		rectangle.heightProperty().bind(this.heightProperty());
		// bind rectangle widthProperty to its heightProperty 
		rectangle.widthProperty().bind(rectangle.heightProperty().multiply(ASPECT_RATIO));

		// x coordinate of the vertex is half the 
		//width of pane minus half the width of the width of the rectangle
		rectangle.xProperty().bind(this.widthProperty().divide(2).subtract(rectangle.widthProperty().divide(2)));
		// y coordinate is simply zero
		rectangle.yProperty().setValue(0); 
		rectangle.setStroke(Color.BLACK);
		rectangle.setFill(Color.WHITE);

		// bind arcWidth and arcLength to one thirteenth of the height
		rectangle.arcWidthProperty().bind(rectangle.heightProperty().divide(13));
		rectangle.arcHeightProperty().bind(rectangle.heightProperty().divide(13));
		
		// importing images
		String path = "/Courses/cs112/images/suits/";
		String[] imageFilenames = {"suit-green-club-100.png", "suit-pink-heart-100.png", "suit-black-spade-100.png", "suit-blue-diamond-100.png"};
		int numImages = imageFilenames.length;
		Image[] images = new Image[numImages];
		ImageView[] imageViews = new ImageView[numImages];
		for (int i = 0; i < numImages; i++) {
			images[i] = new Image(new File(path + imageFilenames[i]).toURI().toString());
			imageViews[i] = new ImageView(images[i]);
		}
		
		//set up the pane
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.TOP_CENTER);
		
//		gridPane.prefHeightProperty().bind(rectangle.heightProperty().divide(2));
		gridPane.prefHeightProperty().bind(this.heightProperty().divide(2));
		gridPane.prefWidthProperty().bind(this.widthProperty());
		gridPane.hgapProperty().bind(gridPane.prefWidthProperty().divide(20));
		gridPane.vgapProperty().bind(gridPane.prefHeightProperty().divide(15));
		
		changeCard(str, gridPane, images);

		//check input
		str.addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> o, String oldValue, String newValue) {
				rectangle.setStroke(Color.BLACK);
				rectangle.setFill(Color.WHITE);
				changeCard(str, gridPane, images);
				if (gridPane.getChildren().size() == 0) {
					rectangle.setFill(Color.TRANSPARENT);
					rectangle.setStroke(Color.TRANSPARENT);
					
				}
			}
		});

		//adding children
		this.getChildren().addAll(rectangle, gridPane);
		this.setPrefSize(635, 889);

	//	public static void main(String[] args) {
	//		CardPane pane = new CardPane();
	//		System.out.println(pane.getHeight());
	//	}
	}
	
	public static void changeCard(StringProperty str, GridPane gridPane, Image[] images) {
		gridPane.getChildren().clear();
		char[] input = str.get().toUpperCase().toCharArray();
		int rank = -1;
		int suit = -1;
		if (input.length == 2) {
			for (int i = 0; i < RANKS.length; i++) {
				if (input[0] == RANKS[i]) {
					//find the input rank
					rank = i + 1;
					break;
				}
			}

			for (int i = 0; i < SUITS.length; i++) {
				if (input[1] == SUITS[i]) {
					//find the input suit
					suit = i;
					break;
				}
			}
		} else {

			return;
		}
		
		if (rank != -1 && suit != -1) {

			//add images to the pane
			int num = 0;
			for (int i = 0; i < (rank / 3 + 1); i++) {
				for (int j = 0; j < 3; j++) {
					if (num == rank) {
						break;
					}
					ImageView temp = new ImageView(images[suit]);
					temp.setPreserveRatio(true);
					temp.fitHeightProperty().bind(gridPane.prefHeightProperty().divide(4));
					
					gridPane.hgapProperty().bind(temp.fitHeightProperty().divide(3));
					gridPane.vgapProperty().bind(temp.fitHeightProperty().divide(4));
					
					gridPane.paddingProperty().bind(Bindings.createObjectBinding(() -> 
					  new Insets(temp.fitHeightProperty().doubleValue()), temp.fitHeightProperty()));
					
					gridPane.add(temp, j, i);
					num++;
//					System.out.println(num);
				}
			}
		} else {
			gridPane.getChildren().clear();
		}
	}
}


