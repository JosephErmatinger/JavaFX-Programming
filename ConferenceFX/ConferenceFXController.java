/*
 * File Name: ConferenceFXController.java
 * Programmer: Isaac Oxendale & Joe Ermatinger
 * Date Created: 2019-04-26
 */
package application;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;

public class ConferenceFXController {
		  int total;
	final int STUDENTCOST = 495;
	final int GENERALCOST = 895;
	final int KEYNOTECOST =  30;
	final int WORKSHOP1 =   295;
	final int WORKSHOP2 =   295;
	final int WORKSHOP3 =   395;
	final int WORKSHOP4 =   395;
    
	@FXML
    private Label todayDate;

    @FXML
    private RadioButton studentRadio;

    @FXML
    private RadioButton generalRadio;

    @FXML
    private CheckBox keynoteCheck;

    @FXML
    private ListView<String> listview;

    @FXML
    private Label costOut;
    
    @FXML
    private Button submitButton;

    @FXML
    void calculateTotal(ActionEvent event) {
    	total = 0;
    	if (studentRadio.isSelected())
    		total += STUDENTCOST;
    	if (generalRadio.isSelected())
    		total += GENERALCOST;
    	if (keynoteCheck.isSelected())
    		total += KEYNOTECOST;
    	ObservableList<Integer> indices = FXCollections.observableArrayList(listview.getSelectionModel().getSelectedIndices());
    	indices.forEach(index -> {
    		if (index==1||index==2)
    			total += WORKSHOP1;
    		if (index==3||index==4)
    			total += WORKSHOP3;
    	});
    	costOut.setText(String.format("$%,d", total));
    }
    @FXML
    void initialize(){
	Date today = new Date();
	todayDate.setText("It is currently " + today);
	
	listview.getItems().addAll("None","Introduction to E-commerce","The Future of the Web","Advanced Java Programming", "Network Security");
 	listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 	
    }
}
    
