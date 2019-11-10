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
import java.io.BufferedReader; // library to ensure BufferedReader can interpret input
import java.io.InputStreamReader; // library to ensure InputStreamReader can read input
import java.io.UnsupportedEncodingException; // library to ensure encoding can be handled in case of errors
import java.io.IOException; // library to ensure input/output can be handled without errors

// open a class which handles the SRPN loop checking for different command, operator or operand inputs for the BufferedReader to interpret
public class SRPNloop {

	// declare public variables and objects required for the program to accept and interpret input	
	// declare new RPNcalculation object and BufferedReader
	RPNcalculation myRPNcalculation;
	BufferedReader br;
	
	// declare variables to store the total input, string to be sent to the calculation method and current character
	String UserInput;
	String stringChunk;
	Character currentCharacter;

	// declare variables to store the predetermined array of random numbers, indexes of current characters and their integer value
	Integer[] randNumber;
	int charCounter;
	int randCounter;
	int index;
	int charNumber;
	
	// declare variables to store information regarding conversion from the Decimal to Octal number system
	String octalString;
	int octalStringLength;
	int octalPower;
	int octalNumEquivalent;
	boolean isOctal;
	
	// a constructor method which initialises and instantiates all of the above variables and objects
	public SRPNloop()
	{
		myRPNcalculation = new RPNcalculation(); // create a new RPNcalculation object to work with
		
		try // create  new BufferedReader object which uses the input stream and ISO character encoding
		{
			br = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));
		} 
		catch (UnsupportedEncodingException e) // if an unsupported encoding type is used, an IO error is flagged
		{
		}
		
		// instantiate all strings to null strings
		UserInput = "";
		stringChunk = "";
		octalString = "";
		// instantiate the array of random numbers to the following 100 numbers, and set the current chosen number index counter to 0
		randNumber = new Integer[] {1804289383, 846930886, 1681692777, 1714636915, 1957747793, 424238335, 719885386, 1649760492, 596516649, 1189641421,1025202362,1350490027,783368690,1102520059,2044897763,1967513926,1365180540,1540383426,304089172,1303455736,35005211,521595368,294702567,1726956429,336465782,861021530,278722862,233665123,2145174067,468703135,1101513929,1801979802,1315634022,635723058,1369133069,1125898167,1059961393,2089018456,628175011,1656478042,1131176229,1653377373,859484421,1914544919,608413784,756898537,1734575198,1973594324,149798315,2038664370,1129566413,184803526,412776091,1424268980,1911759956,749241873,137806862,42999170,982906996,135497281,511702305,2084420925,1937477084,1827336327,572660336,1159126505,805750846,1632621729,1100661313,1433925857,1141616124,84353895,939819582,2001100545,1998898814,1548233367,610515434,1585990364,1374344043,760313750,1477171087,356426808,945117276,1889947178,1780695788,709393584,491705403,1918502651,752392754,1474612399,2053999932,1264095060,1411549676,1843993368,943947739,1984210012,855636226,1749698586,1469348094,1956297539};
		randCounter = 0;
		// set Octal number to 0 before calculations and assume input is not Octal (in most cases input is Decimal)
		octalNumEquivalent = 0;
		isOctal = false;
	}
	
	
	// a method which checks to see if the current character input by the user is an operator (any bar the '-' sign which is dealt with separately)
	public boolean isOperatorBarMinus(Character currChar) // pass the current character into the method
	{
		switch(currChar) // check each possible case for if the current character is an operator 
		{
		default: // assume that the character entered is not an operator by default
			return false;
		case  '+':  // otherwise, if any of '+', '*', '/', '%', '^', '=', 'd' is entered, pass true back to the mainLoop method
			return true;
		case  '*': 
			return true;
		case  '/': 
			return true;
		case  '%': 
			return true;
		case  '^': 
			return true;
		case  '=': 
			return true;
		case  'd': 
			return true;
		}
	}
	
	// method for checking if the current character entered is a random number operator 'r'
	public void checkForRandom()
	{
		if(stringChunk.equals("")) // if the string to be pushed to the calculation is already empty
		{
			stringChunk = Integer.toString(randNumber[randCounter]); // set stringChunk to the value of the next random number in the array
			randCounter = randCounter + 1; // increment randCounter to point towards next index in random numbers array
			
			if(randCounter>99) // if counter exceeds maximum element index, reset the counter to 0 so the array contents effectively wrap around
			{
				randCounter = 0;
			}
			
			myRPNcalculation.includeinCalculation(stringChunk); // pass the string to the calculation before resetting the string to null
			stringChunk = "";
		}
		else // otherwise if the string to be pushed to the calculation is not empty
		{
				checkOctal();	// check if it contains an octal number - if so, push the Octal number to the calculation
			
				if(isOctal == false) // if not,  push the current contents of the string to the calculation method
				{
					myRPNcalculation.includeinCalculation(stringChunk);
					stringChunk = ""; // reset value of stringChunk
				}
				
				stringChunk = Integer.toString(randNumber[randCounter]); // set stringChunk to the value of the next random number in the array
				randCounter = randCounter + 1; // increment randCounter to point towards next index in random numbers array
				
				if(randCounter>99) // if counter exceeds maximum element index, reset the counter to 0 so the array contents effectively wrap around
				{
					randCounter = 0;
				}
				
				myRPNcalculation.includeinCalculation(stringChunk); // pass the current string to push to the calculation before resetting the string to null
				stringChunk = "";
		}
	}
	
	
	// method for checking if a number entered is in Octal format - if so, the number is converted and pushed to the calculation method
	public void checkOctal()
	{
		if(stringChunk.startsWith("0") || stringChunk.startsWith("-0")) // if the string to be pushed starts with '0' or '-0' it must be Octal
		{
			isOctal = true; // set boolean check for Octal values to true for later processing
			
			for(index = 0; index<stringChunk.length(); index++) // for each character in the string to be pushed
				{
					try 
					{
						charNumber = Integer.parseInt(Character.toString(stringChunk.charAt(index))); // extract integer equivalent of number in octal string
						
						if(charNumber<8) // if the converted integer is less than or equal to the maximum Octal digit of 7, add the character to a string of octal numbers
						{
							octalString = octalString + Character.toString(stringChunk.charAt(index));
						}
						else // otherwise if a number greater than 7 appears, break the loop and stop processing further characters
						{
							break;
						}
					}
					catch(NumberFormatException nfe) // if the parseInt routine fails, throw an exception routine
					{
					}
				}
			
				octalStringLength = octalString.length(); // calculate the length of the Octal string just created (how many digits exist in the Octal number)
				octalPower = octalStringLength-1; // set the maximum Octal power to equal the (length -1). EG: 2^7 is the maximum power for 8 bits.
				
				for(index=0; index<octalString.length(); index++) // for each character in the new Octal number string
				{
					// calculate value of Octal number using (current digit * 8^n) where n is the current power
					octalNumEquivalent = octalNumEquivalent + (Integer.parseInt(Character.toString(octalString.charAt(index))) * (int) Math.pow(8, octalPower));
					octalPower--; //decrement power as string is traversed from left to right
				}
				
				if(stringChunk.startsWith("-0")) // if the Octal number is negative, multiply the sum by -1 to make it negative
				{
					octalNumEquivalent = octalNumEquivalent * -1;
				}
				
				if(Integer.toString(octalNumEquivalent).length() > 12)
				{
					if(Integer.toString(octalNumEquivalent).startsWith("-"))
					{
						octalNumEquivalent = -2147483648;
					}
					else
					{
						octalNumEquivalent = 2147483647;
					}
				}
				else
				{
					if(Integer.toString(octalNumEquivalent).startsWith("-"))
					{
						long equivalentLong = Long.parseLong(Integer.toString(octalNumEquivalent));
						if(equivalentLong <  -2147483648)
						{
							octalNumEquivalent = -2147483648;
						}
						else
						{
							octalNumEquivalent =  Integer.parseInt(Integer.toString(octalNumEquivalent));
						}
					}
					else
					{
						long equivalentLong = Long.parseLong(Integer.toString(octalNumEquivalent));
						if(equivalentLong > 2147483647)
						{
							octalNumEquivalent = 2147483647;
						}
						else
						{
							octalNumEquivalent =  Integer.parseInt(Integer.toString(octalNumEquivalent));
						}
					}
				}
				
					stringChunk = Integer.toString(octalNumEquivalent); // set string to be pushed to equal the converted Octal number
					myRPNcalculation.includeinCalculation(stringChunk); // pass string to the calculation method
					
					// reset the string and integer fields for the next check to be run with new data
					octalString = "";
					octalStringLength = 0;
					octalPower = 0;
					octalNumEquivalent = 0;
		}
	}
	

	// a method for running when any symbol is input (operator or operand) - manages contents of string to be pushed to the calculation method
	public void checkForSymbolInput()
	{
		if (stringChunk.equals("")) // if the string to be pushed to the calculation is already empty, convert the current character to a string and pass it to the calculation method
		{
			stringChunk = Character.toString(currentCharacter);
			myRPNcalculation.includeinCalculation(stringChunk);
			stringChunk = ""; // reset the contents of the string to be passed 
		}
		else 
		{
				checkOctal(); // run the Octal checker to see if the number entered is in Octal format - if so then process accordingly and push decimal equivalent to the calculation 
				
				if(isOctal == false)  // if number isn't octal, push the contents of the input string into the calculation method
				{
					myRPNcalculation.includeinCalculation(stringChunk);
				}
				
				stringChunk = Character.toString(currentCharacter); // set the string to be pushed to equal the current character before sending that operator or operand to the calculation method
				myRPNcalculation.includeinCalculation(stringChunk);
				
				// empty the string to be pushed and reset the isOctal check to false
				isOctal = false;
				stringChunk = "";
		}
	}
	
	// a method for running when checking when no new symbol is input (operator or operand) - manages contents of string to be pushed to the calculation method
	public void checkForRemainingPush()
	{
		if (stringChunk.equals("")) // if the string to be pushed to the calculation is already empty, don't perform any action as no new operator or operand has been added to stringChunk
		{
		}
		else // otherwise if the stringChunk variable is occupied
		{
				checkOctal(); // run the Octal checker to see if the number entered is in Octal format - if so then process accordingly and push decimal equivalent to the calculation

				if(isOctal == false) // if number isn't octal, push the contents of the input string into the calculation method
				{
					myRPNcalculation.includeinCalculation(stringChunk);
				}
				
				// empty the string to be pushed and reset the isOctal check to false
				isOctal = false; 
				stringChunk = "";
		}
	}
	
	// a main method where all program input is processed accordingly depending on which character is detected in the input stream
	public void mainLoop()
	{
			try // try to handle the input being entered into the BufferedReader
			{ 
				while((UserInput=br.readLine())!=null) // whilst there is an input to be read from the terminal, deal with that input
				{
					for(charCounter = 0; charCounter < (UserInput.length()); charCounter++) // for each character in the current input stream
					{
						currentCharacter = UserInput.charAt(charCounter); // store the current character in a Character variable
						
						// if the current character is a digit, concatenate the current string to be pushed with the digit (to form a number)
						if(Character.isDigit(currentCharacter))
						{
							stringChunk = stringChunk + currentCharacter;
						}
						
						// otherwise if the current character is an operator bar the minus sign (dealt with separately), run the checkForSymbolInput() method which adds this to the calculation
						else if(isOperatorBarMinus(currentCharacter))
						{
							checkForSymbolInput();
						}
						
						// otherwise if the current character is a space or null character, run the checkForRemainingPush() method which pushes the remaining contents of the string into the calculation
						else if(currentCharacter == ' ' || currentCharacter == null)
						{
							checkForRemainingPush();
						}
						
						// otherwise if the current character is a minus sign, deal with it separately as there is ambiguity between negative numbers and the subtraction operator
						else if (currentCharacter	== '-')
						{
							// if the minus occurs as the first character
							if(charCounter == 0)
							{
								// if the only character entered is a minus sign, run the checkForSymbolInput() method - process the operator and include it in the calculation
								if(UserInput.length() == 1)
								{
									checkForSymbolInput();
								}
								
								// otherwise, if the next character is a digit, concatenate the string to be pushed with the next digit to form a number
								else if(Character.isDigit(UserInput.charAt(charCounter+1)))
								{
									stringChunk = stringChunk + currentCharacter;
								}
								
								// otherwise, check if the string to be pushed has any remaining contents and push those
								else 
								{
									checkForRemainingPush();
								}
							}
							
							// else if the minus is neither the first or last character entered
							else if (charCounter !=0 && charCounter != (UserInput.length()-1))
							{
								// if the minus occurs between two digits, push the contents of the string and add the minus sign to the new empty string 
								if(Character.isDigit(UserInput.charAt(charCounter-1)) || Character.isDigit(UserInput.charAt(charCounter+1)))
								{
									checkForRemainingPush();
									stringChunk = stringChunk + currentCharacter;
								}
								
								// if the minus occurs after a space and before a digit, concatenate the minus to the empty string
								else if(UserInput.charAt(charCounter-1) == ' ' & Character.isDigit(UserInput.charAt(charCounter+1)))
								{
									stringChunk = stringChunk + currentCharacter;
								}
								
								// if the minus occurs after a digit and before a space, run the checkForSymbolInput() method - process the operator and include it in the calculation
								else if((Character.isDigit(UserInput.charAt(charCounter-1)) & (UserInput.charAt(charCounter+1) == ' ')))
								{
									checkForSymbolInput();
								}
								
								// if the minus occurs between two spaces, convert it to a string and pass it to the calculation method before emptying the string
								else if(UserInput.charAt(charCounter-1) == ' ' & UserInput.charAt(charCounter+1) == ' ')
								{
									stringChunk = Character.toString(currentCharacter);
									myRPNcalculation.includeinCalculation(stringChunk);
									stringChunk = "";
								}
								
								// otherwise, push the contents of the string to the calculation, convert the minus to a string and pass it to the calculation method before emptying the string
								else
								{
									myRPNcalculation.includeinCalculation(stringChunk);
									stringChunk = Character.toString(currentCharacter);
									myRPNcalculation.includeinCalculation(stringChunk);
									stringChunk = "";
								}
							}
							
							// else if the minus is the last character to be entered, push it to the calculation method
							else if(charCounter == (UserInput.length()-1))
							{
								checkForSymbolInput();
							}	
					}
					
						// otherwise if the current character is an r, call the checkForRandom() which produces a random number and adds it to the stack
							else if	(currentCharacter == 'r')
							{
									checkForRandom();
							}
						
						// otherwise if the current character is a hash, handle this input as a comment - push any remaining contents to the calculation and ignore all following ones
							else if(currentCharacter == '#')
							{
								checkForRemainingPush();
								break; // break the for loop as no new characters need be processed if they follow a # sign
							}
						
						// otherwise, if the current character is none of the above, print an error message to the terminal showing that the character is not recognisable
						else
						{
								System.err.printf("Unrecognised operator or operand \"%c\".\n", (char)currentCharacter);
						}
					}
						checkForRemainingPush(); // check if the string to be pushed has any remaining contents and push those
				}	
			}
			catch(IOException e) // otherwise if there is an input or output handling error, exit the program safely instead of making the program crash
			{
				System.exit(0);
			}
	}
}