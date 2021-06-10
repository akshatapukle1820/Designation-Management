import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
import java.sql.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.util.*;
class panNumberExists
{
public static void main(String gg[])
{
try
{
String panNumber=Keyboard.getString("Enter the Pan Number : ");
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
boolean panNumberExists=employeeDAO.panNumberExists(panNumber);
System.out.println("Pan Number : "+panNumber+" exists : "+panNumberExists);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}