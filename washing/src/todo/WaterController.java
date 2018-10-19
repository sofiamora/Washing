package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class WaterController extends PeriodicThread {
	private double waterLevel;
	private AbstractWashingMachine myMachine; 
	private WaterEvent waterEvent;
	private int mode;


	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		myMachine=mach;
	}
	
	

	public void perform() {
		waterEvent = (WaterEvent) mailbox.doFetch();
		waterLevel = myMachine.getWaterLevel();
		double targetLevel = waterEvent.getLevel();
		mode = waterEvent.getMode();
		switch( mode) {
			case 0://STOP
				myMachine.setDrain( false );
				myMachine.setFill( false );
				break;
				
			case 1: //FILL
				while( waterLevel < targetLevel ){
					myMachine.setFill( true );
				}
				((RTThread) waterEvent.getSource()).putEvent(new AckEvent(this));
				break;
				
			case 2: //drain
				
				while(waterLevel > 0.0){
					myMachine.setDrain( true );
				}
				break;
		}
		
	}
	
}
