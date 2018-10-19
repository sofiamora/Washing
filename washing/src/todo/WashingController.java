package todo;

import done.*;

public class WashingController implements ButtonListener {	
	// TODO: add suitable attributes
	private AbstractWashingMachine myMachine;
	private double speed;
	private SpinController sc;
	private WaterController wc;
	private TemperatureController tc;
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		// TODO: implement this constructor
    	myMachine= theMachine;
    	 this.sc= new SpinController(myMachine, speed);
    	 this.tc= new TemperatureController(myMachine, speed);
    	 this.wc = new WaterController(myMachine, speed);    	    	
    }
    
    

    public void processButton(int theButton) {
		// TODO: implement this method
    	if (theButton == 0){
    		//new WashingProgram0( myMachine, speed, tc, wc, sc);
    		
    	}
    	if (theButton == 1){
    		new colorProgram(myMachine, speed, tc, wc, sc );
    	}
    	
    	if (theButton == 2){
    		new whiteProgram(myMachine, speed, tc, wc, sc );
    	}
    	
    	if (theButton == 3){
    		new WashingProgram3(myMachine, speed, tc, wc, sc );
    	}
    }
}
