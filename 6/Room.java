import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.io.Serializable;

class Room implements Serializable
{
    /*
    * This is the Room class. Each Room Object has a name and a capacity. It also
    * contains a 7 * 12 array which represents the 7 days of the week and the
    * 12 hours between 8 am and 8pm(The valid hours for booking a room).
    */

    int daySlot[][] = new int[7][12]; //represents days and hours

    String name;
    int capacity;

    public Room(String n , int cap) //constructor that sets all slots to zero - unbooked
    {
      this.name =n;
      this.capacity = cap;

      for (int i=0; i<7; i++)
      {
        for (int j=0; j<11; j++)
          {
            this.daySlot[i][j] = 0;
            }
          }
    }

    /**
    * This Method is used to check whether a particular timeslot on a particular day
    * has already been booked. If the slot contains a 1 then it has already been booked.
    * If it contains a 0 then it is available. The method returns a true or false value.
    */

    public boolean slotAvailable(int day, int slot)
    {
      if (daySlot[day][slot] == 1)
      {
        return false;
      }
      else
      {
        return true;
      }
    }

    /**
    * This Method is used to book a slot. It sets the relevant slot to a 1.
    */

    public void book(int day , int slot)
    {
      this.daySlot[day][slot] = 1;
    }
}
