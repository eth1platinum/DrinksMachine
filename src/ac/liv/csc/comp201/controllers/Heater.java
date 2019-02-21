package ac.liv.csc.comp201.controllers;
import ac.liv.csc.comp201.model.IMachine;

public class Heater {
	
	IMachine machine;
	
	public Heater(IMachine machine) {
		this.machine = machine;
	}

	public boolean checkTemp(String state, double waterTemp) {
		
		if (state == "idle" || state == "pouring") {
			if (machine.getWaterHeater().getTemperatureDegreesC() >= 78) {
				machine.getWaterHeater().setHeaterOff();
			}
			
			if (machine.getWaterHeater().getTemperatureDegreesC() < 78) {
				machine.getWaterHeater().setHeaterOn();
			}	
						
			if (machine.getWaterHeater().getTemperatureDegreesC() >= 100) {
				machine.getDisplay().setTextString("Error: Entering shutdown mode");
				machine.shutMachineDown();
			}
			
			return false;
			
		}
		
		else {
						
			if (machine.getWaterHeater().getTemperatureDegreesC() < waterTemp) {
				machine.getWaterHeater().setHeaterOn();
				return false;
			}
			else {
				return true;
			}
		}
	}
	
}
