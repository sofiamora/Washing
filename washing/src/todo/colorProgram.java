package todo;
import done.*;

public class colorProgram extends WashingProgram {
	//constructor
	public colorProgram(AbstractWashingMachine mach,
						double speed,
						TemperatureController tempController,
						WaterController waterController,
						SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
	}
	
	protected void wash() throws InterruptedException{
		myMachine.setLock(true); //lock
		
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_FILL,10.0));
		mailbox.doFetch();//wait  ACK to be in 10.0
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_IDLE,10.0));
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET,60.0));
		mailbox.doFetch();//wait ACK to be in 10.0
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE,60.0));
		//tiempo por 30 minutos intercalando por 1 minuto
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW )); //derecha izquierda
		//Drainwater
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE,0.0));
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_DRAIN,0.0));
		mailbox.doFetch();//wait  ACK to be in 10.0
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_IDLE,0.0));
		
		//RINSE 5 TIMES FOR 2 MINUTES IN COLD WATER
		for(int cycle=0; cycle <=4; cycle++){
			myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_FILL,10.0));
			mailbox.doFetch();//wait  ACK to be in 10.0
			myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_IDLE,10.0));
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW ));//durante 2 minutos
						
		}
		
		//CENTRIFUGE 5 MINUTES
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST ));
		//VERIFY QUE NO HAYA AGUA
		myMachine.setLock(false); //unlock
	}
}
