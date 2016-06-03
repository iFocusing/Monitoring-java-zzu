package util;

import java.sql.DriverManager;

public class databaseURL
{

	static String url="jdbc:mysql://localhost:3306/monitoring";
	static String name="root";
	static String password="811824bm";
	public static String lian_url()
	{
		return url;
	}
	public static String lian_name()
	{
		return name;
	}
	
	
	public static String lian_password()
	{
		return password;
	}
}
 