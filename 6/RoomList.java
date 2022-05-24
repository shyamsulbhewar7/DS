import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.io.Serializable;

class RoomList implements Serializable 
{
 public String RoomList [] = new String [100];
 //contains an array which holds the maximun number of rooms. To allow for
 //more rooms just increase the size of this array.
}
