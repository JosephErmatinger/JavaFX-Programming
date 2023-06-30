/*
 * File Name: MyFunFXController.java
 * Programmer: Isaac Oxendale & Joe Ermatinger
 * Date Created: 2019-04-26
 */
package application;

import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyFunFXController {

	double totalLow, totalHigh, multiplier = 1.00;
	int styleCost, speciesCost, potCost;
	
	//Create Image Objects for Bonsai Style
	Image broomImage 		    = new Image("file:bin/application/broom.jpg");
	Image cascadeStyleImage     = new Image("file:bin/application/cascade.jpg");
	Image forestImage 		    = new Image("file:bin/application/forest.jpg");
	Image formalUprightImage    = new Image("file:bin/application/formal-upright.jpg");
	Image informalUprightImage  = new Image("file:bin/application/informal-upright.jpg");
	Image semiCascadeStyleImage = new Image("file:bin/application/semi-cascade.jpg");
	Image windsweptImage 		= new Image("file:bin/application/windswept.jpg");
	
	//Create Image Objects for Bonsai Species
	Image beechImage    = new Image("file:bin/application/beech.jpg");
	Image birchImage    = new Image("file:bin/application/birch.jpg");
	Image boxwoodImage  = new Image("file:bin/application/boxwood.jpg");
	Image elmImage      = new Image("file:bin/application/elm.jpg");
	Image ficusImage     = new Image("file:bin/application/ficus.jpg");
	Image hornbeamImage = new Image("file:bin/application/hornbeam.jpg");
	Image pineImage     = new Image("file:bin/application/pine.jpg");
	
	//Create Image Objects for Bonsai Pots
	Image cascadePotImage 	  = new Image("file:bin/application/cascadePot.jpg");
	Image lotusImage 		  = new Image("file:bin/application/lotusPot.jpg");
	Image ovalImage 		  = new Image("file:bin/application/ovalPot.jpg");
	Image rectangleImage 	  = new Image("file:bin/application/rectanglePot.jpg");
	Image roundImage  		  = new Image("file:bin/application/roundPot.jpg");
	Image semiCascadePotImage = new Image("file:bin/application/semicascadePot.jpg");
	Image squareImage 		  = new Image("file:bin/application/squarePot.jpg");
	Image wideShallowImage 	  = new Image("file:bin/application/wideshallowPot.jpg");
	
    @FXML private Label todayDate;
    @FXML private ComboBox<String> speciesComboBox;
    @FXML private ImageView speciesImage;
    @FXML private ComboBox<String> styleComboBox;
    @FXML private ImageView styleImage;
    @FXML private ComboBox<String> potComboBox;
    @FXML private ComboBox<Integer> ageComboBox;
    @FXML private ImageView potImage;
    @FXML private Button submitButton;
    @FXML private Label valueOut;
   
    
    @FXML void selectPot(ActionEvent event) 
    {
    	String potType = potComboBox.getValue();
    	
    		   if(potType=="Cascade")
    	{
    		potImage.setImage(cascadePotImage);
    		potCost = 50;
    		multiplier += .5;
    	} else if(potType=="Lotus") 
    	{
    		potImage.setImage(lotusImage);
    		potCost = 40;
    		multiplier += .2;
    	} else if(potType=="Oval") 
    	{
    		potImage.setImage(ovalImage);
    		potCost = 20;
    		multiplier += .1;
    	} else if(potType=="Rectangle") 
    	{
    		potImage.setImage(rectangleImage);
    		potCost = 25;
    		multiplier += .1;
    	} else if(potType=="Round") 
    	{
    		potImage.setImage(roundImage);
    		potCost = 35;
    		multiplier += .35;
    	} else if(potType=="Semi-Cascade") 
    	{
    		potImage.setImage(semiCascadePotImage);
    		potCost = 45;
    		multiplier += .45;
    	} else if(potType=="Square") 
    	{
    		potImage.setImage(squareImage);
    		potCost = 30;
    		multiplier += .3;
    	} else if(potType=="Wide-Shallow") 
    	{
    		potImage.setImage(wideShallowImage);
    		potCost = 100;
    		multiplier += .6;
    	}
    }

    @FXML void selectSpecies(ActionEvent event) 
	{
		String 	speciesType = speciesComboBox.getValue();  
    	
    		   if(speciesType=="Beech")
		{
			speciesImage.setImage(beechImage);
    		speciesCost = 50;
    		multiplier += 3;
		} else if(speciesType=="Birch") 
		{
			speciesImage.setImage(birchImage);
    		speciesCost = 45;
    		multiplier += 2;
		} else if(speciesType=="Boxwood") 
		{
			speciesImage.setImage(boxwoodImage);
    		speciesCost = 100;
    		multiplier += 5;
		} else if(speciesType=="Elm") 
		{
			speciesImage.setImage(elmImage);
    		speciesCost = 50;
    		multiplier += 2;
		} else if(speciesType=="Ficus") 
		{
			speciesImage.setImage(ficusImage);
    		speciesCost = 25;
    		multiplier += 2;
		} else if(speciesType=="Hornbeam") 
		{
			speciesImage.setImage(hornbeamImage);
    		speciesCost = 150;
    		multiplier += 4;
		} else if(speciesType=="Pine") 
		{
			speciesImage.setImage(pineImage);
    		speciesCost = 125;
    		multiplier += 4;
		} 
    }

    @FXML void selectStyle(ActionEvent event) 
    {
		String styleType = styleComboBox.getValue();
    	
    		   if(styleType=="Broom")
		{
			styleImage.setImage(broomImage);
    		styleCost = 100;
    		multiplier += 3;
		} else if(styleType=="Cascade") 
		{
			styleImage.setImage(cascadeStyleImage);
    		styleCost = 200;
    		multiplier += 5;
		} else if(styleType=="Forest") 
		{
			styleImage.setImage(forestImage);
    		styleCost = 150;
    		multiplier += 2;
		} else if(styleType=="Formal-Upright") 
		{
			styleImage.setImage(formalUprightImage);
    		styleCost = 50;
    		multiplier += 1.5;
		} else if(styleType=="Informal-Upright") 
		{
			styleImage.setImage(informalUprightImage);
    		styleCost = 50;
    		multiplier += 1.75;
		} else if(styleType=="Semi-Cascade") 
		{
			styleImage.setImage(semiCascadeStyleImage);
    		styleCost = 175;
    		multiplier += 4.5;
		} else if(styleType=="Windswept") 
		{
			styleImage.setImage(windsweptImage);
    		styleCost = 125;
    		multiplier += 3.5;
		}
}
    
    @FXML void calculateValue(ActionEvent event) 
    {	
    	if(speciesComboBox.getValue() != null && styleComboBox.getValue() != null && potComboBox.getValue() != null && ageComboBox.getValue() != null) 
    	{
    		totalLow  =  potCost + styleCost + speciesCost + ageComboBox.getValue()*10;
    		totalHigh = (potCost + styleCost + speciesCost) * multiplier + ageComboBox.getValue()*10;
    		valueOut.setText(String.format("$%,.2f-$%,.2f", totalLow, totalHigh));
    	}
    }
    
    @FXML void initialize()
    {
    	Date today = new Date();
    	todayDate.setText("It is currently " + today);
    	
    	speciesComboBox.getItems().addAll("Beech", 
    									  "Birch", 
    									  "Boxwood",
    									  "Elm", 
    									  "Ficus", 
    									  "Hornbeam", 
    									  "Pine"); 
    	
    	styleComboBox.getItems().addAll("Broom",
				  						"Cascade",
				  						"Forest",
				  						"Formal-Upright",
				  						"Informal-Upright",
				  						"Semi-Cascade",
				  						"Windswept");
    	
    	potComboBox.getItems().addAll("Cascade",
    								  "Lotus", 
									  "Oval", 
									  "Rectangle", 
									  "Round", 
									  "Semi-Cascade",  
									  "Square", 
									  "Wide-Shallow"); 
    	
    	ageComboBox.getItems().addAll(0,
									  10, 
									  20, 
									  30, 
									  40, 
									  50,  
									  60, 
									  70);
    	

    }


}
