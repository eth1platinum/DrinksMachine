package ac.liv.csc.comp201.controllers;

import java.util.Arrays;
import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;


public class Recipe {
	
	private int cost;
	private double coffeeAmount;
	private double milkAmount;
	private double chocolateAmount;
	private double sugarAmount;
	private double cupSize;
	private int cupType;
	private double scale;
	private double waterTemp;
	private IMachine machine;
	
	public Recipe(IMachine machine) {
		this.machine=machine;
	}
	
	public boolean findRecipe(String drinkCode) {
		boolean error = false;
		String prefix = "";
		String lastThree = "";
		
		try {
			prefix = drinkCode.substring(0, drinkCode.length()-3);
			lastThree = drinkCode.substring((drinkCode.length())-3, drinkCode.length());
		}
		catch (StringIndexOutOfBoundsException e) {
			machine.getDisplay().setTextString("Error: code not recognised");
			error = true;
		}
		
	switch (prefix) {
		case "":
			cupSize = Cup.SMALL;
			cupType = Cup.SMALL_CUP;
			scale = 1.0;
			cost = 0;
			break;
		case "5":
			cupSize = Cup.MEDIUM;
			cupType = Cup.MEDIUM_CUP;
			scale = 0.45 / 0.34;
			cost = 20;
			break;
		case "6":
			cupSize = Cup.LARGE;
			cupType = Cup.LARGE_CUP;
			scale = 0.56 / 0.34;
			cost = 25;
			break;
		default:
			machine.getDisplay().setTextString("Error: code not recognised");
			error = true;
			break;
	}
		
	switch (lastThree) {
		case "101":
			
			cost += 120;
			coffeeAmount = 2*scale;
			milkAmount = 0*scale;
			chocolateAmount = 0*scale;
			sugarAmount = 0*scale;
			waterTemp = 95.9;
			break;
			
		case "102":
			
			cost += 130;
			coffeeAmount = 2*scale;
			milkAmount = 0*scale;
			chocolateAmount = 0*scale;
			sugarAmount = 5*scale;
			waterTemp = 95.9;
			break;
			
		case "201":
			
			cost += 120;
			coffeeAmount = 2*scale;
			milkAmount = 3*scale;
			chocolateAmount = 0*scale;
			sugarAmount = 0*scale;
			waterTemp = 95.9;
			break;
			
		case "202":
			
			cost += 130;
			coffeeAmount = 2*scale;
			milkAmount = 3*scale;
			chocolateAmount = 0*scale;
			sugarAmount = 5*scale;
			waterTemp = 95.9;
			break;
			
		case "300":
			
			cost += 110;
			coffeeAmount = 0*scale;
			milkAmount = 0*scale;
			chocolateAmount = 28*scale;
			sugarAmount = 0*scale;
			waterTemp = 90.0;
			break;
			
		default:
			machine.getDisplay().setTextString("Error: code not recognised");
			error = true;
			break;
		
	}

		if (error == false) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void makeDrink(String message) {
		
		if (message.equals("true")) {
			
			if (coffeeAmount > 0) {
				machine.getHoppers().setHopperOn(Hoppers.COFFEE);
			}
			if (milkAmount > 0) {
				machine.getHoppers().setHopperOn(Hoppers.MILK);
			}
			if (sugarAmount > 0) {
				machine.getHoppers().setHopperOn(Hoppers.SUGAR);
			}
			if (chocolateAmount > 0) {
				machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
			}
		
		machine.getWaterHeater().setHotWaterTap(true);
		
		int balance = machine.getBalance();
		balance -= cost;
		machine.setBalance(balance);
		
		}
		else {
			machine.getDisplay().setTextString(message);
		}
		
		
	}
	
	public String validateMachine() {

		if (machine.getHoppers().getHopperLevelsGrams(0) < coffeeAmount) {
			return "Not enough coffee in machine";
		}
		else if (machine.getHoppers().getHopperLevelsGrams(1) < milkAmount) {
			return "Not enough milk in machine";
		}
		else if (machine.getHoppers().getHopperLevelsGrams(2) < sugarAmount) {
			return "Not enough sugar in machine";
		}
		else if (machine.getHoppers().getHopperLevelsGrams(3) < chocolateAmount) {
			return "Not enough chocolate in machine";
		}
		else if (cost > machine.getBalance()) { 
			return "Invalid balance in machine";
		}
		else {
			return "true";
		}
	}
	
	public boolean maintainDrink() {

		Cup cup = machine.getCup();
		boolean valid1 = false;
		boolean valid2 = false;
		boolean valid3 = false;
		boolean valid4 = false;
		boolean valid5 = false;
				
		try {
			
			if (cup.getCoffeeGrams()>=coffeeAmount) {
				machine.getHoppers().setHopperOff(Hoppers.COFFEE);
				valid1 = true;
			}
			if (cup.getMilkGrams()>=milkAmount) {
				machine.getHoppers().setHopperOff(Hoppers.MILK);
				valid2 = true;
			}
			if (cup.getChocolateGrams()>=chocolateAmount) {
				machine.getHoppers().setHopperOff(Hoppers.CHOCOLATE);
				valid3 = true;
			}
			if (cup.getSugarGrams()>=sugarAmount) {
				machine.getHoppers().setHopperOff(Hoppers.SUGAR);
				valid4 = true;
			}
			if (cup.getWaterLevelLitres()>=cupSize) {
				machine.getWaterHeater().setHotWaterTap(false);
				valid5 = true;
			}
		}
		catch (NullPointerException e) {

		}
		
		if (valid1 && valid2 && valid3 && valid4 && valid5 == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkPadInput(String padInput) {
		boolean valid = false;
		if (padInput.length() == 4) {
			valid = true;
		}
		else if (padInput.length() == 3) {
			String[] prefixes = new String[3];
			prefixes[0] = "";
			prefixes[1] = "5";
			prefixes[2] = "6";
			if (Arrays.asList(prefixes).contains(padInput.substring(0,1))) {
				valid = false;
			}
			
			
			else {
				valid = true;
			}
		}
		return valid;
	}
	
	public double getWaterTemp() {
		return waterTemp;

	}
	
	public int getCupType() {
		return cupType;
	}
}

