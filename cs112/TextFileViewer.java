
import java.io.File;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class TextFileViewer extends javafx.application.Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		
		TextArea text = new TextArea();
		text.setWrapText(true);
		text.setEditable(false);
		
		if (selectedFile != null) {
			StringBuilder texts = new StringBuilder();
			Scanner in =  new Scanner(selectedFile);
			while (in.hasNextLine()) {
				texts.append(in.nextLine());
				texts.append("\n");
			}
			
			text.setText(texts.toString());
			in.close();
		}
		text.setFont(new Font("Verdana", 20));
		
		Scene scene = new Scene(text);
		primaryStage.setTitle("TextFileViewer");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
