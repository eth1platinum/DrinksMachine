//Ethan Duckett 201260731

package ac.liv.csc.comp201;

import java.text.DecimalFormat;

import ac.liv.csc.comp201.controllers.Heater;
import ac.liv.csc.comp201.controllers.Recipe;
import ac.liv.csc.comp201.controllers.UseCoins;
import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.model.IMachineController;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;

public class MachineController  extends Thread implements IMachineController {
	
	private boolean running=true;
	
	private IMachine machine;
	
	private Recipe recipes;
	
	private Heater heat;
	
	private UseCoins coin;
	
	private String state;
	
	private static final String version="1.22";
	
	private String padInput = "";
	
	public void startController(IMachine machine) {
		
		state = "idle";
		this.heat = new Heater(machine);
		this.coin = new UseCoins(machine);
		this.recipes = new Recipe(machine);
		this.machine=machine;				// Machine that is being controlled
		machine.getKeyPad().setCaption(0,"0");
		machine.getKeyPad().setCaption(1,"1");
		machine.getKeyPad().setCaption(2,"2");
		machine.getKeyPad().setCaption(3,"3");
		machine.getKeyPad().setCaption(4,"4");
		machine.getKeyPad().setCaption(5,"5");
		machine.getKeyPad().setCaption(6,"6");
		machine.getKeyPad().setCaption(7,"7");
		machine.getKeyPad().setCaption(8,"8");
		machine.getKeyPad().setCaption(9,"Return change");
		
		super.start();
	}
	
	
	public MachineController() {
					
	}

	
	private synchronized void runStateMachine() {
		
		int keyCode=machine.getKeyPad().getNextKeyCode();
		String coinCode=machine.getCoinHandler().getCoinKeyCode();
		DecimalFormat df = new DecimalFormat("#.00"); 
		machine.getDisplay().setTextString("Balance: £" + df.format((double) machine.getBalance() / 100) + ", Current Code: " + padInput);
		
		if (coinCode!=null) {
			
			coin.identifyCoin(coinCode);

		}

		if (state == "idle") {
		
		switch (keyCode) {
		case 0 :
			padInput = padInput + "0";
			break;
		case 1 :
			padInput = padInput + "1";
			break;
		case 2 :
			padInput = padInput + "2";
			break;
		case 3 :
			padInput = padInput + "3";
			break;
		case 4 :
			padInput = padInput + "4";
			break;
		case 5 :
			padInput = padInput + "5";
			break;
		case 6 :
			padInput = padInput + "6";
			break;
		case 7 :
			padInput = padInput + "7";
			break;
		case 8 :
			padInput = padInput + "8";
			break;
		case 9 :
			coin.dispenseChange(machine.getBalance());
			break;
	}
	}
		
		boolean changeIdle = recipes.checkPadInput(padInput);
		if (changeIdle == true && state == "idle") {
			boolean validRecipe = recipes.findRecipe(padInput);
			padInput = "";
			String message = recipes.validateMachine();
			if (message == "true") {
				if (validRecipe == true) {
					machine.vendCup(recipes.getCupType());
					state = "preheat";
				}
			}
			else if (message == "Invalid balance in machine") {
				machine.getDisplay().setTextString(message);
			}
			else {
				machine.getDisplay().setTextString(message);
				machine.getCoinHandler().lockCoinHandler();
			}
			
		}
		
		if (heat.checkTemp(state, recipes.getWaterTemp()) == true && state == "preheat") {
			String message = recipes.validateMachine();
				recipes.makeDrink(message);
				machine.getCoinHandler().unlockCoinHandler();
				state = "pouring";
			}
		
		if (recipes.maintainDrink() == true && state == "pouring") {
			state = "idle";
		}
		
		heat.checkTemp(state, recipes.getWaterTemp());
		recipes.maintainDrink();
	
		
		
		
	}

	
	
	public void run() {
		// Controlling thread for coffee machine
		int counter=1;
		while (running) {
			//machine.getDisplay().setTextString("Running drink machine controller "+counter);
			counter++;
			try {
				Thread.sleep(10);		// Set this delay time to lower rate if you want to increase the rate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runStateMachine();
		}		
	}
	
	public void updateController() {
		//runStateMachine();
	}
	
	public void stopController() {
		running=false;
	}
	
}

