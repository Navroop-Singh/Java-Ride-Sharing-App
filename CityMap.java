import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)


public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
  
    String[] parts = getParts(address); // get parts of address
    if (parts.length != 3) { // checks if length of parts is 3 or no
      return false; 
    } 
    if (!allDigits(parts[0])&& parts[0].length() != 2) { // checks if first part is a number or not
     return false;
    }
    if (!parts[2].equalsIgnoreCase("Street") && !parts[2].equalsIgnoreCase("Avenue")) { // check if last part is not street or avenue
      return false;
    } else{ // if last part is street or avenue returns true
      return true;
    }
  }

 
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1}; // default value


    String[] parts = getParts(address);
    if (!validAddress(address)) { // checks if address is valid
      return block;
    } 
		block[0] = Integer.parseInt(parts[0].substring(0, 1));  // takes only the first digit as integer
    block[1] = Integer.parseInt(parts[1].substring(0, 1));  // takes only the second digit as integer
		
    return block;
    
  }
  
  
  public static int getDistance(String from, String to)
  {
    
    int fromBlock1 = getCityBlock(from)[0];  // takes only the first digit as integer of the from address 
		int toBlock1 = getCityBlock(to)[0];  // takes only the first digit as integer of the to address
    int fromBlock2 = getCityBlock(from)[1];  // takes only the second digit as integer of the from address
    int toBlock2 = getCityBlock(to)[1];  // takes only the second digit as integer of the to address
    if (fromBlock1 == -1 || fromBlock2 == -1 || toBlock1 == -1 || toBlock2 == -1) { // checks if those values ae -1
      return 0;
    }

    int distance = Math.abs(toBlock1 - fromBlock1) + Math.abs(toBlock2 - fromBlock2); // distance in city blocks
    return distance; // returns distance
  }
}
