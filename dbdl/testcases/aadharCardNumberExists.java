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
class aadharCardNumberExists
{
public static void main(String gg[])
{
try
{
String aadharCardNumber=Keyboard.getString("Enter the Aadhar Card Number : ");
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
boolean aadharCardNumberExists=employeeDAO.aadharCardNumberExists(aadharCardNumber);
System.out.println("Aadhar Card Number : "+aadharCardNumber+" exists : "+aadharCardNumberExists);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}