package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PairProgramming8Controller {

    @FXML
    private TextField nameInput;

    @FXML
    private CheckBox checkBox1;

    @FXML
    private CheckBox checkBox2;

    @FXML
    private Label checkedOut;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label comboOut;

    @FXML
    void selectCheck1(ActionEvent event) {
    	if (checkBox1.isSelected())checkedOut.setText("Hi " + nameInput.getText() + ", you love Java!" );
    }
    @FXML
    void selectCheck2(ActionEvent event) {
    	if (checkBox2.isSelected())checkedOut.setText("Hi " + nameInput.getText() + ", I guess you would rather not say!" );
    }
    @FXML
    void selectCombo(ActionEvent event) {
    		comboOut.setText("Your favorite programming language is " + comboBox.getValue() + "!");
    }
  	@FXML
    void initialize(){
    comboBox.getItems().addAll("Java", "C++", "RPG", "Ruby", "HTML5", "SQL");
    }
}
