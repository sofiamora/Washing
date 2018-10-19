package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes
	private int mode ;
	private double tempIni ;
	private double tempTarget ;
	private AbstractWashingMachine myMachine ;
	private TemperatureEvent tempEvent ;
	
	
	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		myMachine = mach ;
		tempIni = myMachine.getTemperature() ;
	}

	public void perform() {
		tempEvent= (TemperatureEvent) mailbox.doFetch();
		mode = tempEvent.getMode();
		tempTarget= tempEvent.getTemperature();
		if ( mode == 0 )
			myMachine.setHeating( false );
		
		else {
			
			while ( tempIni < tempTarget) {
				myMachine.setHeating( true );
			}
			
		}
		
		((RTThread) tempEvent.getSource()).putEvent(new AckEvent(tempEvent.getSource()));
		
	}
}
