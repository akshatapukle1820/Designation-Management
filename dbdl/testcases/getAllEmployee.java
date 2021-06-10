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
class getAllEmployee
{
public static void main(String gg[])
{
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
List<EmployeeDTOInterface> list;
list=employeeDAO.getAll();
for(EmployeeDTOInterface employees :list)
{
System.out.println("Employee Id : "+employees.getEmployeeId());
System.out.println("Name : "+employees.getName());
System.out.println("Designation Code : "+employees.getDesignationCode());
System.out.println("Date of Birth : "+employees.getDateOfBirth());
System.out.println("Basic Salary : "+employees.getBasicSalary());
System.out.println("Gender : "+employees.getGender());
System.out.println("Is Indian : "+employees.isIndian());
System.out.println("Pan Number : "+employees.getPANNumber());
System.out.println("Aadhar Card Number : "+employees.getAadharCardNumber());
System.out.println("---------------------------------------------------------------------");
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}