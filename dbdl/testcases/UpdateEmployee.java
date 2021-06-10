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
class UpdateEmployee
{
public static void main(String gg[])
{
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy");
String vEmployeeId=Keyboard.getString("Enter employee Id : ");
String vName=Keyboard.getString("Enter name :");
int vDesignationCode=Keyboard.getInt("Enter designationCode :");
java.util.Date vDateOfBirth=sdf.parse(Keyboard.getString("Enter date of birth(dd/MM/yyyy) :"));
BigDecimal vBasicSalary=new BigDecimal(Keyboard.getString("Enter basic salary :"));
String vGender=Keyboard.getString("Enter gender (M/F) :");
if(vGender.equalsIgnoreCase("M")!=true && vGender.equalsIgnoreCase("F")!=true)
{
throw new DAOException("Invalid gender : "+vGender);
}
boolean vIsIndian;
String in=Keyboard.getString("Is Indian (Y/N) :");
if(in.equals("Y")!=true && in.equals("N")!=true)
{
throw new DAOException("Invalid Indian factor :"+in);
}
vIsIndian=(in.equals("Y"))?true:false;
String vPANNumber=Keyboard.getString("Enter PAN Number :");
String vAadharCardNumber=Keyboard.getString("Enter Aadhar card number :");
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(vEmployeeId);
employeeDTO.setName(vName);
employeeDTO.setDesignationCode(vDesignationCode);
employeeDTO.setDateOfBirth(vDateOfBirth);
employeeDTO.setBasicSalary(vBasicSalary);
if(vGender.equalsIgnoreCase("M")) employeeDTO.setGender(EmployeeDTOInterface.MALE);
if(vGender.equalsIgnoreCase("F")) employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(vIsIndian);
employeeDTO.setPANNumber(vPANNumber);
employeeDTO.setAadharCardNumber(vAadharCardNumber);
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
System.out.println("Employee updated ");
System.out.println("------------------------------------------------------");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
}
}
}