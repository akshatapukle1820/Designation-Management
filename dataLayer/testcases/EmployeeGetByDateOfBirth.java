import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeGetByDateOfBirth
{
public static void main(String gg[])
{
try
{
SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=sdf.parse(Keyboard.getString("Enter date of birth : "));
List<EmployeeDTOInterface> employees;
employees=new EmployeeDAO().getByDateOfBirth(dateOfBirth);
for(EmployeeDTOInterface employee:employees)
{
System.out.println("Employee ID :"+employee.getEmployeeId());
System.out.println("Name :"+employee.getName());
System.out.println("Designation code :"+employee.getDesignationCode());
System.out.println("Basic Salary :"+employee.getBasicSalary().toPlainString());
System.out.println("Is Indian :"+employee.isIndian());
System.out.println("Gender :"+employee.getGender());
System.out.println("PAN number :"+employee.getPANNumber());
System.out.println("Aadhar card number :"+employee.getAadharCardNumber());
System.out.println("--------------------------------------------------------------");
}
}catch(ParseException parseException)
{
System.out.println(parseException);
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}