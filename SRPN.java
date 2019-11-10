/*
 * SRPN Program - SRPN is a reverse polish notation calculator with the extra feature that
 * all arithmetic is saturated i.e. when it reaches the maximum value that can be
 * stored in a variable, it stays at the maximum rather than wrapping around.
 * 
 * Reverse Polish Notation is a system of formula notation without brackets or special punctuation.
 * It is performed in many computers and calculators. 
 * In the usual form, operators follow rather than precede their operands.
 * 
 * @author 18181 (candidate number)
 * @version 1.0
 * @release 01/12/2015
*/

// import main libraries required for SRPN loop to function
import java.io.IOException; // library to ensure input/output can be handled without errors

// open a class which creates and calls an SRPN loop object
public class SRPN
{
	public static void main(String[] args) throws IOException // main class from which all program functionality starts
	{
		SRPNloop mySRPNloop = new SRPNloop(); // create a new SRPNloop object
		mySRPNloop.mainLoop(); // call the main loop method of this object to start program
	}
}