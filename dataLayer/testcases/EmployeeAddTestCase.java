import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.util.*;
class EmployeeAddTestCase
{
public static void main(String gg[])
{
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String vEmployeeId;
String vName=Keyboard.getString("Enter name :");
int vDesignationCode=Keyboard.getInt("Enter designationCode :");
java.util.Date vDateOfBirth=sdf.parse(Keyboard.getString("Enter date of birth(dd/MM/yyyy) :"));
BigDecimal vBasicSalary=new BigDecimal(Keyboard.getString("Enter basic salary :"));
String vGender=Keyboard.getString("Enter gender (male/female) :");
if(vGender.equals("male")!=true && vGender.equals("female")!=true)
{
throw new RuntimeException("Invalid gender :+vGender");
}
boolean vIsIndian;
String in=Keyboard.getString("Is Indian (Y/N) :");
if(in.equals("Y")!=true && in.equals("N")!=true)
{
throw new RuntimeException("Invalid Indian factor :"+in);
}
vIsIndian=(in.equals("Y"))?true:false;
String vPANNumber=Keyboard.getString("Enter PAN Number :");
String vAadharCardNumber=Keyboard.getString("Enter Aadhar card number :");
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setName(vName);
employeeDTO.setDesignationCode(vDesignationCode);
employeeDTO.setDateOfBirth(vDateOfBirth);
employeeDTO.setBasicSalary(vBasicSalary);
if(vGender.equals("male")) employeeDTO.setGender(EmployeeDTOInterface.MALE);
if(vGender.equals("female")) employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(vIsIndian);
employeeDTO.setPANNumber(vPANNumber);
employeeDTO.setAadharCardNumber(vAadharCardNumber);
new EmployeeDAO().add(employeeDTO);
System.out.println("Employee added with id as :"+employeeDTO.getEmployeeId());
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