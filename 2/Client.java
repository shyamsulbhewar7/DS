import java.sql.*;
import java.rmi.*; 
import java.io.*; 
import java.util.*;
import java.lang.*;
import java.rmi.registry.*;
public class Client
{
	static int name1,name3; 
	public static void main(String args[])
	{
		Client c=new Client();
		Scanner scn = new Scanner(System.in);
		int ch;
		try {
			Registry r1 = LocateRegistry.getRegistry ( "localhost", 1031); 
			DBInterface DI=(DBInterface)r1.lookup("DBServ");
			do{
				System.out.println("1.Input number for factorial\n2.Display Ans \nEnter ur choice"); 
				ch = scn.nextInt();
				switch(ch){ 
				case 1:
					System.out.println(" \n Enter Number:"); 
					name1= scn.nextInt();
					name3=DI.input(name1);
					break; 
				case 2:
                //display
					System.out.println("\n Factorial is : "); int i=0;
					System.out.println(" " +name3+""); break;
				}
			}
			while(ch>0);
		}
		catch (Exception e){ 
			System.out.println("ERROR: " +e.getMessage()); 
		}
	}
}