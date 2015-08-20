package System.Algorithms.Paging;

import System.Algorithms.Interface.IServer;
import System.Algorithms.Interface.PageAlgo;
import System.Hardware.RAM;

public class NULLAlgo extends PageAlgo implements IServer{

	RAM _tempRAM = RAM.Instance;
	
	public static final NULLAlgo Instance = new NULLAlgo();
}
