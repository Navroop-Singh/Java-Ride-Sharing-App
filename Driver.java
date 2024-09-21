  
  public class Driver
  {
    private String id;
    private String name;
    private String carModel;
    private String licensePlate;
    private double wallet;
    private String type;
    
    public static enum Status {AVAILABLE, DRIVING};
    private Status status;
      
    
    public Driver(String id, String name, String carModel, String licensePlate)
    {
      this.id = id;
      this.name = name;
      this.carModel = carModel;
      this.licensePlate = licensePlate;
      this.status = Status.AVAILABLE;
      this.wallet = 0;
      this.type = "";
    }
    // Print Information about a driver
    public void printInfo()
    {
      System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f", 
                        id, name, carModel, licensePlate, wallet);
    }
    
    public String getType()
    {
      return type;
    }
    public void setType(String type)
    {
      this.type = type;
    }
    public String getId()
    {
      return id;
    }
    public void setId(String id)
    {
      this.id = id;
    }
    public String getName()
    {
      return name;
    }
    public void setName(String name)
    {
      this.name = name;
    }
    public String getCarModel()
    {
      return carModel;
    }
    public void setCarModel(String carModel)
    {
      this.carModel = carModel;
    }
    public String getLicensePlate()
    {
      return licensePlate;
    }
    public void setLicensePlate(String licensePlate)
    {
      this.licensePlate = licensePlate;
    }
    public Status getStatus()
    {
      return status;
    }
    public void setStatus(Status status)
    {
      this.status = status;
    }
    public double getWallet() 
    {
      return wallet;
    }
    public void setWallet(double wallet)
    {
      this.wallet = wallet;
    }
  
    public boolean equals(Object other)
    {
      if (other instanceof Driver) { // check if other is a Driver type
        Driver otherDriver = (Driver) other; // cast to Driver type
        return (name.equals(otherDriver.name) && licensePlate.equals(otherDriver.licensePlate)); // compares both name and license plates and returns in boolean
      }
      return false;
    }
    
    // A driver earns a fee for every ride or delivery
    public void pay(double fee)
    {
      wallet += fee; // add fee to wallet
    }
  }
