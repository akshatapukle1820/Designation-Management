import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
class EmployeeDeleteTestCase
{
public static void main(String gg[])
{
try
{
String vEmployeeId=Keyboard.getString("Enter employee Id : ");
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(vEmployeeId);
System.out.println("Employee deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}