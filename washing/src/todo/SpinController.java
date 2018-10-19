package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class SpinController extends PeriodicThread {
	// TODO: add suitable attributes
	private int mode;
	private AbstractWashingMachine myMachine;
	private SpinEvent spinEvent;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		myMachine = mach;

	}	

	public void perform() {
		spinEvent = (SpinEvent) mailbox.doFetch();
		mode = spinEvent.getMode();
		switch ( mode ) {
			case 0:
				myMachine.setSpin(0);
				break;
				
			case 1:		//1 minuto y un minuto
				myMachine.setSpin(1);
				myMachine.setSpin(2);
				break;
			
			case 2:
				myMachine.setSpin(3);
				break;
		}		
	}
}
