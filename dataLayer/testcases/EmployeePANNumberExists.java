import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
public class EmployeePANNumberExists
{
public static void main(String gg[])
{
String EmployeePanNumberExists=Keyboard.getString("Enter PAN Number: ");
try
{
System.out.println("Is PAN Number Exists :"+new EmployeeDAO().panNumberExists(EmployeePanNumberExists));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}