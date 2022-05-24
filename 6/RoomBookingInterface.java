
import java.rmi.*;
import java.rmi.server.*;

/**
* This is the interface, it contains the 5 methods which the Client can use.
*/

public interface RoomBookingInterface extends Remote
{
  public void initRooms() throws RemoteException;
  public RoomList allRooms () throws RemoteException;
  public String checkRoom (String r, int day , int startTime) throws RemoteException;
  public String bookRoom (String r, int day , int startTime) throws RemoteException;
  public int[][] roomTimeTable (String room) throws RemoteException;
}
