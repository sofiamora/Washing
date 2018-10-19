package todo;
import done.*;

public class whiteProgram extends WashingProgram {
	
	public whiteProgram(AbstractWashingMachine mach,
			double speed,
			TemperatureController tempController,
			WaterController waterController,
			SpinController spinController) {
		
		super(mach, speed, tempController, waterController, spinController);
	}
	
	
	protected void wash() throws InterruptedException {
		
		myMachine.setLock(true); //lock
		
		//prewash 15
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_FILL,10.0));
		mailbox.doFetch();//wait  ACK to be in 10.0
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_IDLE,10.0));
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET,40.0));
		mailbox.doFetch();//wait ACK to be in 10.0
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE,40.0));
		//altenar ciclos derecha e izquierda por 15 minutos
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_DRAIN,0.0));
		mailbox.doFetch();//wait  ACK to be in 10.0
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_IDLE,0.0));
		
		//MAIN WASH 90 C
		
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_FILL,10.0));
		mailbox.doFetch();//wait  ACK to be in 10.0
		myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_IDLE,10.0));
		
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET,90.0));
		mailbox.doFetch();//wait ACK to be in 10.0
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE,90.0));
		//altenar ciclos derecha e izquierda por 30 minutos
		
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
