import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeGetByPANNumber
{
public static void main(String gg[])
{
String PanNumber=Keyboard.getString("Enter PAN number:");
try
{
EmployeeDTOInterface employee;
employee=new EmployeeDAO().getByPANNumber(PanNumber);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.println("Name :"+employee.getName());
System.out.println("Designation code :"+employee.getDesignationCode());
System.out.println("Date Of Birth :"+sdf.format(employee.getDateOfBirth()));
System.out.println("Basic Salary :"+employee.getBasicSalary().toPlainString());
System.out.println("Is Indian :"+employee.isIndian());
System.out.println("Gender :"+employee.getGender());
System.out.println("PAN number :"+employee.getPANNumber());
System.out.println("Aadhar card number :"+employee.getAadharCardNumber());
System.out.println("--------------------------------------------------------------");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}