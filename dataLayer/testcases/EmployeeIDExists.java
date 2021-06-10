import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
public class EmployeeIDExists
{
public static void main(String gg[])
{
String EmployeeIdExists=Keyboard.getString("Enter Employee ID : ");
try
{
System.out.println("Is Employee Id Exists :"+new EmployeeDAO().employeeIdExists(EmployeeIdExists));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}