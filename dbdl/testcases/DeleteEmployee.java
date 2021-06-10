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
class DeleteEmployee
{
public static void main(String gg[])
{
try
{
String vEmployeeId=Keyboard.getString("Enter employee id : ");
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(vEmployeeId);
System.out.println("Employee Deleted");
System.out.println("------------------------------------------------------");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}