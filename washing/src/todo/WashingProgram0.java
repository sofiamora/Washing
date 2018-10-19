package todo;

import done.AbstractWashingMachine;

public class WashingProgram0 extends WashingProgram {
	public WashingProgram0( AbstractWashingMachine mach, double speed, TemperatureController tempController,
							WaterController waterController, SpinController spinController) {
			
		super(mach, speed, tempController, waterController, spinController);
	}

	// ---------------------------------------------------------- PUBLIC METHODS

	/**
	 * This method contains the actual code for the washing program. Executed
	 * when the start() method is called.
	 */
	protected void wash() throws InterruptedException {
	
		// Switch of temp regulation
		myTempController.putEvent( new TemperatureEvent( this, TemperatureEvent.TEMP_IDLE, 0.0 ));

		// Switch off spin
		mySpinController.putEvent( new SpinEvent( this, SpinEvent.SPIN_OFF ));


		myWaterController.putEvent(new WaterEvent( this, WaterEvent.WATER_IDLE , 0.0 ));
		
		// Unlock
	}
}
