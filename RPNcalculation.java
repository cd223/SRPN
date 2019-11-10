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
import java.util.Stack; // import stack library to include stack functionality allowing operands to be pushed and popped
import static java.lang.Math.pow; // include library allowing calculations involving powers to be executed
import java.util.EmptyStackException; // include library to handle exception when a stack is empty
import java.lang.OutOfMemoryError; // include library to handle memory usage errors 

// open a class which processes the calculation using the operator and operands passed to it from the mainLoop method in the SRPNloop class
public class RPNcalculation
{
	// declare and instantiate a new stack which holds data of Integer() type to store the operands pushed to it
	private Stack<Integer> myStack = new Stack<Integer>();
	
	// declare an index variable to store the current count through a loop
	int index;
	
	// declare an empty constructor method to ensure the class compiles
	public RPNcalculation()
	{
	}
	
	// declare the main calculation method which takes a string input, converts it to an integer and performs the necessary calculation
	public void includeinCalculation(String op)
	{
		// declare and instantiate two integer variables to store the two operands - set to a default value of null
		Integer op_1 = null;
		Integer op_2 = null;
		
		// if the op string passed is an operator
		if(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%") || op.equals("^"))
		{
			try // attempt to process the operator input
			{
				if(op.equals("+")) // if the input is a plus sign
				{
					op_2 = (int) myStack.pop(); // extract the previous two operands from the top of the stack - it uses a LIFO structure so this means op_2 on top then op_1
					op_1 = (int) myStack.pop();
					
					if ((long) op_1 + (long) op_2 > 2147483647) // if the result of the operation exceeds the limit for a number stored as an integer, push this maximum/ceiling value to the stack
					{
						myStack.push(2147483647);
					}
					else if((long) op_1 + (long) op_2 < -2147483648) // if the result of the operation is lower than the minimum limit for a number stored as an integer, push this minimum/floor value to the stack
					{
						myStack.push(-2147483648);
					}
					else // otherwise, push the result of the addition onto the stack
					{
						myStack.push(op_1 + op_2);
					}
				}
				else if(op.equals("-")) // if the input is a minus sign
				{
					op_2 = (int) myStack.pop(); // extract the previous two operands from the top of the stack - it uses a LIFO structure so this means op_2 on top then op_1
					op_1 = (int) myStack.pop();
					
					if ((long) op_1 - (long) op_2 > 2147483647) // if the result of the operation exceeds the limit for a number stored as an integer, push this maximum/ceiling value to the stack
					{
						myStack.push(2147483647);
					}
					else if((long) op_1 - (long) op_2 < -2147483648) // if the result of the operation is lower than the minimum limit for a number stored as an integer, push this minimum/floor value to the stack
					{
						myStack.push(-2147483648);
					}
					else // otherwise, push the result of the subtraction onto the stack
					{
						myStack.push(op_1 - op_2);
					}
				}
				else if(op.equals("*")) // if the input is a multiply sign
				{
					op_2 = (int) myStack.pop(); // extract the previous two operands from the top of the stack - it uses a LIFO structure so this means op_2 on top then op_1
					op_1 = (int) myStack.pop();
					
					if ((long) op_1 * (long) op_2 > 2147483647) // if the result of the operation exceeds the limit for a number stored as an integer, push this maximum/ceiling value to the stack
					{
						myStack.push(2147483647);
					}
					else if((long) op_1 * (long) op_2 < -2147483648) // if the result of the operation is lower than the minimum limit for a number stored as an integer, push this minimum/floor value to the stack
					{
						myStack.push(-2147483648);
					}
					else
					{
						myStack.push(op_1 * op_2); // otherwise, push the result of the multiplication onto the stack
					}
				}
				else if(op.equals("/")) // if the input is a divide sign
				{
					op_2 = (int) myStack.pop(); // extract the previous two operands from the top of the stack - it uses a LIFO structure so this means op_2 on top then op_1
					op_1 = (int) myStack.pop();
					
					if(op_2!=0)
					{
						if ((long) op_1 / (long) op_2 > 2147483647) // if the result of the operation exceeds the limit for a number stored as an integer, push this maximum/ceiling value to the stack
						{
							myStack.push(2147483647);
						}
						else if((long) op_1 / (long) op_2 < -2147483648) // if the result of the operation is lower than the minimum limit for a number stored as an integer, push this minimum/floor value to the stack
						{
							myStack.push(-2147483648);
						}
						else
						{
							myStack.push(op_1 / op_2); // otherwise, push the result of the division onto the stack
						}
					}
					else if(op_2 == 0) // if the second operand is negative, the result of the calculation is undefined, so print a division by zero error
					{
						System.err.println("Divide by 0.");
						myStack.push(op_1); // push each operand back onto the stack
						myStack.push(op_2);
					}
				}
				
				else if(op.equals("%")) // if the input is a modulo sign
				{
					op_2 = (int) myStack.pop(); // extract the previous two operands from the top of the stack - it uses a LIFO structure so this means op_2 on top then op_1
					op_1 = (int) myStack.pop();
					
					if(op_2!=0)
					{
							if ((long) op_1 % (long) op_2 > 2147483647) // if the result of the operation exceeds the limit for a number stored as an integer, push this maximum/ceiling value to the stack
							{
								myStack.push(2147483647);
							}
							else if((long) op_1 % (long) op_2 < -2147483648) // if the result of the operation is lower than the minimum limit for a number stored as an integer, push this minimum/floor value to the stack
							{
								myStack.push(-2147483648);
							}
							else
							{
								myStack.push(op_1 % op_2); // otherwise, push the result of the modular division onto the stack
							}
					}
					else if(op_2 == 0) // if the second operand is negative, the result of the calculation is undefined, so print a division by zero error
					{
						System.err.println("Divide by 0.");
						myStack.push(op_1); // push each operand back onto the stack
						myStack.push(op_2);
					}
				}
				
				else if(op.equals("^")) // if the input is a power sign
				{
					op_2 = (int) myStack.pop(); // extract the previous two operands from the top of the stack - it uses a LIFO structure so this means op_2 on top then op_1
					op_1 = (int) myStack.pop();
					
					if(op_2 >= 33) // if the second operand is greater than or equal to 33, convert the result to a positive number 
					{
						if(Math.abs(op_1) == 1) // if the first operand is 1 or -1, output 1 as the answer by pushing it to the stack
						{
						myStack.push(1);
						}
						else // otherwise push the maximum positive value that an integer can hold to the stack
						{
							myStack.push(2147483647);
						}
					}
					else if(op_2 < 0) // if the second operand is negative, print negative power error message as the stack can only hold integer values not fractional ones
					{
						System.err.println("Negative power.");
						myStack.push(op_1); // push each operand back onto the stack
						myStack.push(op_2);
					}
					else
					{
						if (pow((long) op_1, (long) op_2) > 2147483647) // if the result of the operation exceeds the limit for a number stored as an integer, push this maximum/ceiling value to the stack
						{
							myStack.push(2147483647);
						}
						else if(pow((long) op_1, (long) op_2) < -2147483648) // if the result of the operation is lower than the minimum limit for a number stored as an integer, push this minimum/floor value to the stack
						{
							myStack.push(-2147483648);
						}
						else
						{
							myStack.push((int) pow((double) op_1, (double) op_2)); // otherwise, push the result of the power operation onto the stack
						}
					}
				}
			}
			catch(EmptyStackException exception_1) // if the operator input can't be handled because of an empty stack
			{
				System.err.println("Stack underflow."); // print a stack underflow error message
				
				if(op_1 == null) // if the first op variable is occupied, push it back onto the stack
				{
				}
				else
				{
					myStack.push(op_1);
				}
				
				if(op_2 == null) // if the second op variable is occupied, push it back onto the stack
				{	
				}
				else
				{
					myStack.push(op_2);
				}
			}
			catch(OutOfMemoryError exception_2) // if the operator input can't be handled because of an out of memory error
			{	
				op_1 = null; // reset each op variable to null
				op_2 = null;
				
				System.err.printf("Out of memory."); // print an error message showing that the program is out of memory
				return; // return to the previous instruction before the method was called
			}
		}
		
		// if the op string passed is an equals or display command, perform stack manipulation to display the top or the whole of the stack's contents
			else if(op.equals("=") | op.equals("d"))
			{
				if (op.equals("=")) // if the op string is an equals and the stack isn't empty (0 elements), print the top level of the stack containing the most recent operand or answer
				{
					if(myStack.size()>0)
					{
						System.out.println(myStack.peek());
					}
					else // otherwise, print an error saying that the stack is empty (thus nothing can be displayed)
					{
						System.err.println("Stack empty.");
					}
				}
				else if(op.equals("d")) // if the op string is a 'd', print the contents of the stack in reverse order (FIFO instead of LIFO)
					{
					
						for(index=0; index< myStack.size(); index++) // for each element of the stack, print the contents of element
						{
							System.out.println(myStack.get(index));
						}
					}
			}
			else // otherwise if the stack size is greater than or equal to 23, print a stack overflow error
			{
				if(myStack.size()>=23)
				{
					System.err.println("Stack overflow.");
					return; // return to the next instruction in the function previous to this one being called
				}
				else // otherwise, convert the input into an integer and push it to the stack
				{
				myStack.push(convertToInt(op));
				return; // return to the next instruction in the function previous to this one being called
				}	
			}
	}
	
	// a method which converts a given string of digits as an input into an integer value after checking for saturation
	public int convertToInt(String stringOp)
	{
		// declare and instantiate a variable for the string length
		int integerLength;
		integerLength = stringOp.length(); // measure number of characters the number takes up
		
			if(integerLength > 12) // if this is greater than 12 (maximum for an integer)
			{
				if(stringOp.startsWith("-")) // if the input string is negative, return the minimum integer value
				{
					return -2147483648;
				}
				else // else if the input string is positive, return the maximum integer value
				{
					return 2147483647;
				}
			}
			else // if the input string is less than 12 characters
			{
				if(stringOp.startsWith("-")) // if the string is negative
				{
					long equivalentLong = Long.parseLong(stringOp); // convert the string to a long data type
					if(equivalentLong <  -2147483648) // if the input string is below the minimum integer value, return the minimum integer value
					{
						return -2147483648;
					}
					else // otherwise return the equivalent integer value of the string
					{
						return Integer.parseInt(stringOp); 
					}
				}
				else // if the string is positive
				{
					long equivalentLong = Long.parseLong(stringOp); // convert the string to a long data type
					if(equivalentLong > 2147483647) // if the input string is above the maximum integer value, return the maximum integer value
					{
						return 2147483647;
					}
					else
					{
						return Integer.parseInt(stringOp); // otherwise return the equivalent integer value of the string
					}
				}
			}
	}
}