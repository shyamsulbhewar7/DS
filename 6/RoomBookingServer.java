import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

class RoomBookingServer extends UnicastRemoteObject implements RoomBookingInterface
{

 /**
 * This is the Server Class. It contains the working methods which can be used by the client.
 */

  protected int day;
  protected int time;
  protected int room;
  protected String str = new String();

  public String RoomListTemp [] = new String [100];       //Temporary store for list of rooms
  public String temp = new String();
  public Room RoomArray[] = new Room[100];                  //Array of Room Objects

  RoomList tempList = new RoomList();

  public RoomBookingServer ( ) throws RemoteException
  {
    super ( );
  }

  /**
  * This method is called once by the client when the application starts. It reads
  * in the input from the text file and creates an Object for each room with the
  * name and capacity that was specified in the file.
  */

  public void initRooms() throws RemoteException
  {
    String record = null;
    String tempRoom = null;
    String tempCap = null;

    int recCount = 0;
    int num;
    int capacity;

    try
    {
      //This reads in the text from the file and uses that to create the
      //Room Objects. The name is specified first in the text file and the
      //capacity is specified last. This is manipulated in order to take in
      //these parameters when creating the Rooms.

      BufferedReader b = new BufferedReader(new FileReader("Rooms.txt"));
      while((record = b.readLine()) != null)
      {
        num = (record.lastIndexOf (" ", record.length ())) + 1;
        tempRoom = record.substring (0,num -1);                 //Reads in the Room name from file

        tempCap = record.substring  (num,record.length ());
        capacity = Integer.parseInt(tempCap);                   //Reads in the capacity from file

        RoomArray[recCount] = new Room(tempRoom, capacity);     //Fills the array with the created Objects.
        recCount ++;
      }
      b.close();    //close the input stream.

    } catch (IOException e)
      {
        System.out.println("Error!" +e.getMessage());
      }
  }

  /**
  * This method is used to return the list of rooms and there capacity to the client.
  * It returns a RoomList Object which contains the arrayList of Rooms. The Client
  * can then retrieve a full list of rooms.
  */

  public RoomList allRooms() throws RemoteException
  {
    try
    {
      BufferedReader in = new BufferedReader(new FileReader("rooms.txt"));  //read in the text file.
      if((str = in.readLine()) != null)
      {
        tempList.RoomList[0] = str;
        for (int i = 1; i< 100; i++)
        {
          if((str = in.readLine()) != null)
          {
            tempList.RoomList[i] = str;
          }
        }
      }
      in.close();
    }
    catch (IOException e)
    {
    }
    return tempList;
  }


  /**
  * This method takes in a string and then compares that string with the name of each Object
  * in the array of Rooms. If it finds the room it returns the index, -1 otherwise.
  */

  public int compareRoom(String str)
  {
    for(int i=0; i< RoomArray.length; i++)
    {
      if(RoomArray[i].name.equals (str))
      {
        return i;
      }
    }
    return -1;
  }

  /**
  * This method is used to check whether a room is available or not. Firstly it checks
  * for the room in the array, if it finds it it then checks whether the requested
  * time slot on the requested day is available. It returns a string to the client
  * depending on the value of the timeslot.
  */

  public String checkRoom(String r ,int day , int startTime) throws RemoteException
  {
    int i = compareRoom(r);
    if (RoomArray[i].slotAvailable(day, startTime) == true) //calls methos available to Room Object
    {
      String s = "Room is available for booking";
      return s;
    }
    else
    {
      String s = "Sorry the room is not available for booking";
      return s;
    }
  }

  /**
  * This method is used to book a Room. Again it checks whether the slot is available and depending
  * on the result it reserves that slot and informs the client or it informs them that
  * the slot has already been reserved.
  */

  public String bookRoom(String r, int day , int startTime) throws RemoteException
  {
    int i = compareRoom(r);

    if (RoomArray[i].slotAvailable(day, startTime) == true)
    {
      RoomArray[i].book(day,startTime);
      String s = "Room has been successfully booked.";
      return s;
    }
    else
    {
      String s = "Sorry but the Room has already been booked.";
      return s;
    }
  }

  /**
  * This method is used to calculate the timetable for each room. It returns relevant
  * the 2D array to the client displaying the weekly timetable for the requested room.
  */

  public int [][] roomTimeTable(String room) throws RemoteException
  {
    int i;
    System.out.println("TimeTable" + room);
    for ( i = 0; i< RoomArray.length; i++)
    {
      if(RoomArray[i].name.equals(room))
      {
        return RoomArray[i].daySlot;
      }
      else
      {
        System.out.println("Searching for the room");
      }
    }

    return RoomArray[i].daySlot;
  }

  //Main Method
  public static void main (String[] args)
  {
    try
    {
      RoomBookingServer server = new RoomBookingServer ();
      //String name = "rmi://localhost:9999/RoomBookingSystem";
     // Naming.bind (name, server);
 String name = "RoomBookingSystem";
 Naming.bind (name, server);
      System.out.println (name + " is running");
    }
    catch (Exception ex)
    {
      System.err.println (ex);
    }
  }
}
