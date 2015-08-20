package System.Algorithms.Framing;

import System.Algorithms.Interface.FrameAlgo;

public class NullAlgo extends FrameAlgo{
	
	public static final NullAlgo Instance = new NullAlgo();
	
	private NullAlgo(){ // equals i proportional powinien zwracac proporcje, 
		super();
	}

	@Override
	public Object SupplyPush() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void calculate() {
		// TODO Auto-generated method stub
		
	}
}
