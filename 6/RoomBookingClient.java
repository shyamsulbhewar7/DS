import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

class RoomBookingClient
{

   /**
    * This is the Client Class. It takes an input from the user, calls the methods available
    * to the client from the server class and gives an ouput depending on the operation performed.
    */

    public static boolean validChoice = true;
    static String [] daysOfWeek = { "Monday   |", "Tuesday  |", "Wednesday|", "Thursday |" , "Friday   |" , "Saturday |" , "Sunday   |" };

    public static void main (String[] args)
    {
      try
      {
        //System.setSecurityManager ( new RMISecurityManager ( ));  //set up the security manager
        //String name = "rmi://localhost:9999/RoomBookingSystem";   //connect on local host on port 9999
 String name="rmi://127.0.0.1/RoomBookingSystem";
        RoomBookingInterface rbi =(RoomBookingInterface) Naming.lookup (name);

        rbi.initRooms();   //set up the room booking system

      while( validChoice != false )
      {
        //A small command line interface for the user to use the system.
        System.out.println(" ");
        System.out.println("*********************Room Booking Service********************");
        System.out.println("");
        System.out.println("                   Please select a service");
        System.out.println("");
        System.out.println("1. List of all rooms.");
        System.out.println("2. Check availability of a room.");
        System.out.println("3. Book a room.");
        System.out.println("4. Display weekly timetable for a room.");
        System.out.println("");

        //A buffered reader to allow input from the command line from the user.
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("");
        System.out.println("Select a number between 1 and 4, 0 to exit");
        System.out.println("");
        System.out.flush();
        String response = input.readLine();

        int i = Integer.parseInt(response);
        RoomList ListOfAllRooms = new RoomList(); //RoomList Object which stores
                                                        //a list of all the rooms available.

        try{
          switch (i)
            {
              case 0: System.out.println("Goodbye");   //User has quit the application.
                      validChoice =false;
                      break;

              case 1: System.out.println("");
                      System.out.println("The full list of rooms is as follows");
                      System.out.println("");
                      System.out.println("Room|Capacity");
                      System.out.println("----|--------");
                      ListOfAllRooms = rbi.allRooms();  //Run the allRooms method which
                                                        //returns the list of all rooms.

                      for(int c = 0; c < 100; c++)        //Print the list.
                      {
                        if (ListOfAllRooms.RoomList[c] ==null)
                        {
       break;
                        }
      System.out.println(ListOfAllRooms.RoomList[c]);
                      }
       System.out.println("");
                      break;

              case 2: System.out.println("");
                      System.out.println("Check a room");
                      System.out.println("Enter the room name");
                      String check_room = input.readLine();

                      System.out.println("Enter the day - ");
       System.out.println("0=Mon , 1=Tues, 3=Wed ,4=Thurs , 5=Fri, 6=Sat, 7=Sun");
                      String check_day = input.readLine();
                      int real_day = Integer.parseInt(check_day);

                      System.out.println("Enter the start time - ");
       System.out.println("0=8am , 1=9am , 2=10am , 3=11am , 4=12pm , 5=1pm , 6=2pm , 7=3pm , 8=4pm , 9=5pm , 10=6pm , 11= 7pm");
                      String check_time = input.readLine();
                      int real_time = Integer.parseInt(check_time);

                      //This checks whether a room is available given the room name, day and time.
                      String temp = rbi.checkRoom(check_room,real_day,real_time);
                      System.out.println(temp);
       System.out.println("");
                      break;

              case 3: System.out.println("Room Booking Service - Rooms can be booked from 8am to 8pm");
       System.out.println("");
       System.out.println("Time slots go from 0 for 8am up to 11 for 7pm - Enter a value in this range");
                      System.out.println("");
                      System.out.println("Enter the room name");
                      String book_room = input.readLine();

                      System.out.println("");
                      System.out.println("Enter the day -"); 
       System.out.println("0=Mon , 1=Tues, 3=Wed ,4=Thurs , 5=Fri, 6=Sat, 7=Sun");
                      String book_day = input.readLine();
                      int real_day2 = Integer.parseInt(book_day);

                      System.out.println("");
                      System.out.println("Enter the start time -"); 
       System.out.println("0=8am , 1=9am , 2=10am , 3=11am , 4=12pm , 5=1pm , 6=2pm , 7=3pm , 8=4pm , 9=5pm , 10=6pm , 11= 7pm");
                      String book_time = input.readLine();
                      int realb_time = Integer.parseInt(book_time);

                      //This checks whether a room is available, if it is it then reserves the room.
                      String resp = rbi.bookRoom(book_room,real_day2,realb_time);
                      System.out.println(resp);
       System.out.println("");
                      break;

              case 4: System.out.println("Enter the Room name");
                      String Room1 = new String();
                      Room1  = input.readLine();

                      //This checks the timetable for a room. A 2D array containing
                      //the timetable is returned from the server.
       
       System.out.println("TimeSlot | 0 1 2 3 4 5 6 7 8 9 10 11");
                      int rtt [][]=(int[][])rbi.roomTimeTable(Room1).clone();
                      for(int f=0;f<7;f++)
                      {
                        System.out.println("");
                        System.out.print(daysOfWeek[f]);

                        for(int j=0;j<12;j++)
                        {
                          System.out.print(" ");
                          System.out.print(rtt[f][j]);
                        }

                      }
       System.out.println("");
       System.out.println(" ");
       System.out.println("The key to start times is as follows... ");
       System.out.println("0 = 8am , 1 = 9am , 2 = 10am , 3 = 11am , 4 = 12pm , 5 = 1pm , 6 = 2pm , 7 = 3pm , 8 = 4pm , 9 = 5pm , 10 = 6pm , 11 = 7pm");
       System.out.println("");
                      break;
                }
              }
              catch(Exception e)
              {
                 System.err.println("Sorry but you have entered one of the fields incorrectly, Please try again ");
              }
          }
    }
    catch (Exception ex)
    {
      System.err.println (ex);
    }
  }
}
