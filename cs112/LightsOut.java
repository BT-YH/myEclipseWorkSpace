import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LightsOut extends Application {
	static final int SIZE = 7;
	static final RadioButton[] buttons = new RadioButton[SIZE];
	static final ToggleGroup group = new ToggleGroup();
	Light[][] lights;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Lights Out Size");

		VBox vbox = new VBox(5);
		vbox.setAlignment(Pos.CENTER);
		
		Label select = new Label("Please select a size:");
		vbox.getChildren().add(select);
		
		for (int i = 0; i < SIZE; i++) {
			buttons[i] = new RadioButton();
			//Allow 1 button to be clicked
			buttons[i].setToggleGroup(group);
			buttons[i].setText(String.valueOf(i + 3));
			vbox.getChildren().add(buttons[i]);
		}
		group.selectToggle(buttons[2]);
		
		Button button = new Button("Create Puzzle");
		button.setOnAction(e -> {
			if (group.getSelectedToggle() != null) {
				if (primaryStage.titleProperty().get().equals("Lights Out Size")) {
					primaryStage.setTitle("Lights Out");
				}
				
				//getting the button size
				String val = group.selectedToggleProperty().toString();
				System.out.println(val);
				int n = Integer.parseInt(val.charAt(val.length()-3)+"");
				//init lights
				lights = new Light[n][n];
				//setting up the new stage
				setUp(n, primaryStage);
			}
		});
		
		vbox.getChildren().add(button);
		Scene scene = new Scene(vbox, 150, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	void setUp(int n, Stage stage) {
		BorderPane rootPane = new BorderPane();
		
		rootPane.setBottom(getHbox(n));
		rootPane.setCenter(getGridPane(n));
		
		Scene scene = new Scene(rootPane, n * 60, n * 60 + 60);

		stage.setWidth(n * 60);
		stage.setMinWidth(250);
		stage.setScene(scene);
		stage.setResizable(false);

		stage.show();
	}
	
	private HBox getHbox(int n) {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
		hbox.setPrefHeight(60);
		
		Button randomize = new Button("Randomize");
		randomize.setOnAction(e -> {
			randomize();
		});
		
		Button chaseLights = new Button("Chase Lights");
		chaseLights.setOnAction(e -> {
			chaseLights();
		});
		
		hbox.getChildren().addAll(randomize, chaseLights);
		return hbox;
	}
	
	private void randomize() {
		for (int i = 0; i < lights.length; i++) {
			for (int j = 0; j < lights.length; j++) {
				double randomizer = Math.random() * 2;
				if (randomizer < 1) {
					conditionalLit(i, j);
				}
			}
		}
	}
	
	private void chaseLights() {
		for (int i = 0; i < lights.length; i++) {
			for (int j = 0; j < lights.length; j++) {
				if (lights[i][j].isLit) {
					conditionalLit(i + 1, j);
				}
			}
		}
	}
	
	private GridPane getGridPane(int n) {
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-background-color: #C0C0C0;");
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setVgap(1);
		gridPane.setHgap(1);
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				//Set up light
				Light light = new Light(i ,j, false);
				light.setPrefSize(50, 50);
				lights[i][j] = light;
				//Set action for light
				light.setOnAction(e -> litting(e));
				gridPane.add(light, j, i);
			}
		}
		randomize();
		return gridPane;
	}
	
	void litting(ActionEvent e) {
		//access row and col
		int row = GridPane.getRowIndex((Node) e.getSource());
		int col = GridPane.getColumnIndex((Node) e.getSource());
		//System.out.printf("%d, %d\n", row, col);
		//litting the light
		conditionalLit(row, col);
	}
	
	void conditionalLit(int row, int col) {
		if (row + 1 < lights.length)
			lights[row + 1][col].setLit(!lights[row + 1][col].isLit);
		if (row - 1 >= 0)
			lights[row - 1][col].setLit(!lights[row - 1][col].isLit);
		if (col + 1 < lights.length)
			lights[row][col + 1].setLit(!lights[row][col + 1].isLit);
		if (col - 1 >= 0)
			lights[row][col - 1].setLit(!lights[row][col - 1].isLit);
	}
	
	class Light extends Button {
		boolean isLit = true;
		int row, col;

		public Light(int row, int col, boolean isLit) {
			this.row = row;
			this.col = col;
			this.setLit(isLit);
		}

		public void setLit(boolean isLit) {
			this.isLit = isLit;
			if (this.isLit) {
				this.setStyle("-fx-background-color: #FFFF00;-fx-background-radius: 10px;");
			} else {
				this.setStyle("-fx-background-color: #000000;-fx-background-radius: 10px;");
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
