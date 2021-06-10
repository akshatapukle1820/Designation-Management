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
class employeeIdExists
{
public static void main(String gg[])
{
try
{
String employeeId=Keyboard.getString("Enter the Employee Id : ");
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
boolean EmployeeIdExists=employeeDAO.employeeIdExists(employeeId);
System.out.println("Employee Id : "+employeeId+" exists : "+EmployeeIdExists);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}