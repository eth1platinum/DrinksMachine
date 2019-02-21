package ac.liv.csc.comp201.controllers;

import ac.liv.csc.comp201.model.IMachine;

public class UseCoins {
	
	private IMachine machine;
	
	public UseCoins(IMachine machine) {
		this.machine = machine;
	}
		
		public void identifyCoin(String code) {
			
				if (code == "ef") {
					int balance = machine.getBalance();
					balance += 100;
					machine.setBalance(balance);
				}
				else if (code == "bd") {
					int balance = machine.getBalance();
					balance += 50;
					machine.setBalance(balance);
				}
				else if (code == "bc") {
					int balance = machine.getBalance();
					balance += 20;
					machine.setBalance(balance);
				}
				else if (code == "ba") {
					int balance = machine.getBalance();
					balance += 10;
					machine.setBalance(balance);
				}
				else if (code == "ac") {
					int balance = machine.getBalance();
					balance += 5;
					machine.setBalance(balance);
				}
				else if (code == "ab") {
					int balance = machine.getBalance();
					balance += 1;
					machine.setBalance(balance);
				}
				
		}
		
		public void dispenseChange(int amount) {
			
			boolean error = false;
			while (amount > 0 && error == false) {
				if (amount >= 100 && machine.getCoinHandler().coinAvailable("ef") == true ) {
					amount -= 100;
					machine.getCoinHandler().dispenseCoin("ef");
				}
				else if (amount >= 50 && machine.getCoinHandler().coinAvailable("bd") == true ) {
					amount -= 50;
					machine.getCoinHandler().dispenseCoin("bd");
				}
				else if (amount >= 20 && machine.getCoinHandler().coinAvailable("bc") == true  ) {
					amount -= 20;
					machine.getCoinHandler().dispenseCoin("bc");
				}
				else if (amount >= 10 && machine.getCoinHandler().coinAvailable("ba") == true ) {
					amount -= 10;
					machine.getCoinHandler().dispenseCoin("ba");
				}
				else if (amount >= 5 && machine.getCoinHandler().coinAvailable("ac") == true  ) {
					amount -= 5;
					machine.getCoinHandler().dispenseCoin("ac");
				}
				else if (amount >= 1 && machine.getCoinHandler().coinAvailable("ab") == true ) {
					amount -= 1;
					machine.getCoinHandler().dispenseCoin("ab");
				}
				else {
					error = true;
				}
			}
			
			if (error == true) {
				machine.setBalance(amount);
			}
			
			else {
			machine.setBalance(0);
			}
			
		}
}