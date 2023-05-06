import java.util.Stack;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class CardGridPane extends GridPane {

	public static final double ASPECT_RATIO = 635.0/889.0; 
	public static final int SIZE = 4;
	public int[] begin = {0,0}, end = {0,0};
	public static final char[] RANKS = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
	public static Stack<String> stack = new Stack<>();
	public static final KeyCombination CTRL_Z = new KeyCodeCombination(KeyCode.Z,
			KeyCombination.CONTROL_DOWN);
	public static Scene myScene;

	public CardGridPane(StringProperty[][] cardGrid) {
		super();
		this.setPadding(new Insets(5));
		this.setHgap(3);
		this.setVgap(3);
		// set row constraints
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(25);

		for (int n = 0; n < SIZE; n++) {
			this.getRowConstraints().add(rc);
		}

		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(25);

		for (int m = 0; m < SIZE; m++) {
			this.getColumnConstraints().add(cc);
		}


		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				CardPane card = new CardPane(cardGrid[i][j]);
				//Detecting plays

				card.setOnDragDetected(e -> {
					card.startFullDrag();
					int col= GridPane.getColumnIndex(card);
					int row = GridPane.getRowIndex(card);
					begin[0] = row; 
					begin[1] = col;
				});

				card.setOnMouseDragReleased(e -> {
					int col = GridPane.getColumnIndex(card);
					int row = GridPane.getRowIndex(card);
					end[0] = row;
					end[1] = col;
					if (legalMoves(begin, end, cardGrid)) {	
						String cardInfo = cardGrid[end[0]][end[1]].get() + 
								" " + begin[0] + begin[1] + " " + end[0] + end[1];

						// the actual move
						cardGrid[end[0]][end[1]].setValue(cardGrid[begin[0]][begin[1]].get());
						cardGrid[begin[0]][begin[1]].setValue("");

						stack.push(cardInfo);
						myScene = this.getScene(); // Key

						myScene.setOnKeyPressed(event -> {
							if (CTRL_Z.match(event) && stack.size() != 0) {
								String info = stack.pop();
								String[] infos = info.split(" ");

								int beginRow = Integer.parseInt(infos[1].substring(0,1));
								int beginCol = Integer.parseInt(infos[1].substring(1));
								int endRow = Integer.parseInt(infos[2].substring(0,1));
								int endCol = Integer.parseInt(infos[2].substring(1));

								cardGrid[beginRow][beginCol].setValue(cardGrid[endRow][endCol].get());
								cardGrid[endRow][endCol].set(infos[0]);

							} else if (KeyCode.U.equals(event.getCode()) && stack.size() != 0) {
								String info = stack.pop();
								String[] infos = info.split(" ");

								int beginRow = Integer.parseInt(infos[1].substring(0,1));
								int beginCol = Integer.parseInt(infos[1].substring(1));
								int endRow = Integer.parseInt(infos[2].substring(0,1));
								int endCol = Integer.parseInt(infos[2].substring(1));

								cardGrid[beginRow][beginCol].setValue(cardGrid[endRow][endCol].get());
								cardGrid[endRow][endCol].set(infos[0]);

							}
						});
					}
				});

				this.add(card, j, i);
			}
		}
	}


	public static boolean legalMoves(int[] begin, int[] end, StringProperty[][] cardGrid) {
		String beginS = cardGrid[begin[0]][begin[1]].get();
		String endS = cardGrid[end[0]][end[1]].get();

		int beginRank = -1;
		int endRank = -1;
		for (int i = 0; i < RANKS.length; i++) {
			if (RANKS[i] == beginS.charAt(0)) {
				beginRank = i + 1;
			}

			if (RANKS[i] == endS.charAt(0)) {
				endRank = i + 1;
			}
		}

		if (beginS.equals(endS)) {
			return false;
		} else if (begin[0] == end[0] || begin[1] == end[1]) {
			if (beginS.charAt(1) == endS.charAt(1) ||
					beginS.charAt(0) == endS.charAt(0) ||
					Math.abs(beginRank - endRank) == 1) {
				return true;
			} 
		}

		return false;
	}

}
