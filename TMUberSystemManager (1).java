import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
// 
/*  
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private ArrayList<User>   users;
  private ArrayList<Driver> drivers;

  private ArrayList<TMUberService> serviceRequests; 

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    users   = new ArrayList<User>();
    drivers = new ArrayList<Driver>();
    serviceRequests = new ArrayList<TMUberService>(); 
    
    TMUberRegistered.loadPreregisteredUsers(users);
    TMUberRegistered.loadPreregisteredDrivers(drivers);
    
    totalRevenue = 0;
  }

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  // The methods below will set this errMsg string and then return false
  String errMsg = null;

  public String getErrorMessage()
  {
    return errMsg;
  }
  
  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    // Fill in the code
    for (int i = 0; i < users.size(); i++) { // loop through all users and returns user 
			if (users.get(i).getAccountId().equals(accountId)) {
				return users.get(i);
			}
		}
    return null;
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    for (int i = 0; i < users.size(); i++) { // loop through all users and check if user exists
      if (users.get(i).getAccountId().equals(user.getAccountId())) {
        return true;
      }
    }
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   
   for (int i = 0; i < drivers.size(); i++) { // loop through all drivers and check if driver exists
    if (drivers.get(i).getId().equals(driver.getId())) {
      return true;
    }
  }
   return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {

    for (int i = 0; i < serviceRequests.size(); i++) { // loop through all service requests and check if request exists
			if (serviceRequests.get(i).equals(req)) {
				return true;
			}
		}
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    // Fill in the code
    for (int i = 0; i < drivers.size(); i++) { // loop through all drivers and check if driver is available
      if (drivers.get(i).getStatus() == Driver.Status.AVAILABLE) {
        return drivers.get(i);
      }
    }
    return null;
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    // loops through all of the users and prints info about each user using printInfo() 
    for (int i = 0; i < users.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      users.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {

    System.out.println(); // loops through all of the drivers and prints info about each driver using printInfo()
    System.out.println("Number of drivers: " + drivers.size());
    for (int i = 0; i < drivers.size(); i++) { 
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {

    System.out.println(); // loops through all of the service requests then  prints info about each service request using printInfo()
    for (int i = 0; i < serviceRequests.size(); i++) {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      serviceRequests.get(i).printInfo();
      System.out.println();
    }
  }

  // Add a new user to the system
  public boolean registerNewUser(String name, String address, double wallet)
  {
    
    if (name == null) { // if name is null returns message and false
      errMsg = "Invalid Name";
      return false;
    }
    if (address == null) { // if address is null returns message and false
      errMsg = "Invalid Address";
      return false;
    }
    if (wallet < 0) { // if wallet is less than 0 returns message and false
      errMsg = "Invalid Wallet";
      return false;
    }
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getAccountId().equals(name)) { // if user already exists
        errMsg = "User already exists";
        return false;
      }
    }
    User user = new User(Integer.toString(userAccountId), name, address, wallet);
    users.add(user);
    userAccountId++;
    return true;
  }

  // Add a new driver to the system
  public boolean registerNewDriver(String name, String carModel, String carLicencePlate)
  {
    
    if (name == null) { // if name is null returns message and false
      errMsg = "Invalid Name";
      return false;
    }
    if (carModel == null) { // if car model is null returns message and false
      errMsg = "Invalid Car Model";
      return false;
    }
    if (carLicencePlate == null) { // if car licence plate is null returns message and false
      errMsg = "Invalid Licence Plate";
      return false;
    }
    Driver driver = new Driver(Integer.toString(driverId), name, carModel, carLicencePlate);
		if (driverExists(driver)) { // if driver already exists returns message and false
			errMsg = "Driver already exists";
			return false;
		}
    drivers.add(driver);
		driverId++;
    return true;
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public boolean requestRide(String accountId, String from, String to)
  {
    // Check for valid parameters
	// Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user
    double dist = CityMap.getDistance(from, to);
    TMUberRide ride = new TMUberRide(getAvailableDriver(), from, to, getUser(accountId), (int) dist, getRideCost((int) dist));
    if (CityMap.getDistance(from, to) < 1) {
      errMsg = "Invalid distance";
      return false;
    }
    // Check if user has enough money
    if (getDeliveryCost(CityMap.getDistance(from, to)) > getUser(accountId).getWallet()) {
      errMsg = "Insufficient funds";
      return false;
    }
    // Check if user exits
    if (getUser(accountId) == null) {
      errMsg = "User not found";
      return false;
    }
    // Check if user has a ride or no
    if (existingRequest(ride)) {
      errMsg = "Use already has a ride Request";
      return false;
    }
    // if there is no driver available, it send message to user
		if (getAvailableDriver() == null) {
			errMsg = "No available driver";
			return false;
		} 
    getAvailableDriver().setStatus(Driver.Status.DRIVING);
    serviceRequests.add(ride);
    getUser(accountId).addRide();
		return true;
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public boolean requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    // See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    // Increment the number of deliveries the user has had
    double dist = CityMap.getDistance(from, to);
    TMUberDelivery delivery = new TMUberDelivery(getAvailableDriver(), from, to, getUser(accountId), (int) dist, getDeliveryCost((int)dist),restaurant, foodOrderId);
    if (getUser(accountId) == null) { // checks if user exists
      errMsg = "User not found";
      return false;
    }
    // checks if the delivery cost is more than the user's wallet 
    if (getDeliveryCost(CityMap.getDistance(from, to)) > getUser(accountId).getWallet()) {
      errMsg = "Insufficient funds";
      return false;
    }
    if (dist <= 1) {  // checks if distance is less or equal than 1
      errMsg = "distance must be > 1 city block!"; 
      return false;
    }
    
		if (getAvailableDriver() == null) { /// checks if there is an available driver
			errMsg = "No available driver";
			return false;
		}
    getAvailableDriver().setStatus(Driver.Status.DRIVING); 
    serviceRequests.add(delivery);
    getUser(accountId).addDelivery();
    return true;
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public boolean cancelServiceRequest(int request)
  {
    // Check if valid request #
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed
    request--;
    if (!existingRequest(serviceRequests.get(request))) {
      errMsg = "Request not found";
      return false;
    }
    TMUberService serviceRequest = serviceRequests.get(request);

		double serviceCost = serviceRequest.getCost(); 

		Driver driver = serviceRequest.getDriver();
		double driverFee = serviceCost * PAYRATE; 

		totalRevenue -= driverFee;

		driver.setStatus(Driver.Status.AVAILABLE);

		User user = serviceRequest.getUser();
		user.payForService(serviceCost);

		serviceRequests.remove(request);

    User a = serviceRequest.getUser();

    return true;
  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public boolean dropOff(int request)
  {
    // See above method for guidance
    // Get the cost for the service and add to total revenues
    // Pay the driver
    // Deduct driver fee from total revenues
    // Change driver status
    // Deduct cost of service from user
    request--;
		if (request < 0 || request >= serviceRequests.size()) {
			errMsg = "Invalid request number. Please enter a number between 0 and " + (serviceRequests.size());
			return false;
		}
    TMUberService servReq = serviceRequests.get(request);
    double serviceCost = servReq.getCost();
		totalRevenue += serviceCost;

    Driver driver = servReq.getDriver();
		double driverFee = serviceCost * PAYRATE; 
		driver.pay(driverFee);

    totalRevenue -= driverFee;

    driver.setStatus(Driver.Status.AVAILABLE);

    User user = servReq.getUser();
    user.payForService(serviceCost);
    serviceRequests.remove(request);
    return true;
  }


  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    Collections.sort(users, new NameComparator());
    listAllUsers();
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>
  {
    public int compare(User user1, User user2)
    {
      return user1.getName().compareTo(user2.getName());
    }
    
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet()
  {
    Collections.sort(users, new UserWalletComparator());
    listAllUsers();
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User u1, User u2) {
			return Double.compare(u1.getWallet(), u2.getWallet());
		}
  
  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance()
  {
    Collections.sort(serviceRequests, new DistanceComparator());
		listAllServiceRequests();
  }
  private class DistanceComparator implements Comparator<TMUberService> 
  {
		public int compare(TMUberService s1, TMUberService s2) {
			return Integer.compare(s1.getDistance(), s2.getDistance());
		}
	}

}
