import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.util.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
class updateEmployeepsp
{
public static void main(String gg[])
{
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String vEmployeeId=Keyboard.getString("Enter Employee ID : ");
String vName=Keyboard.getString("Enter name : ");
String title=Keyboard.getString("Enter designation : ");
DesignationInterface designation;
designation=new Designation();
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
designation=designationManager.getByTitle(title);
Date vDateOfBirth=new Date();
try{
vDateOfBirth=sdf.parse(Keyboard.getString("Enter date of Birth(dd/MM/yyyy) : "));
}catch(ParseException parseException)
{
System.out.println(parseException);
}
BigDecimal vBasicSalary=new BigDecimal(Keyboard.getString("Enter basic salary :"));
String vGender=Keyboard.getString("Enter gender (male/female) :");
if(vGender.equalsIgnoreCase("male")!=true && vGender.equalsIgnoreCase("female")!=true)
{
System.out.println("Invalid gender : "+vGender);
}
boolean vIsIndian;
String in=Keyboard.getString("Is Indian (Y/N) : ");
if(in.equals("Y")!=true && in.equals("N")!=true)
{
System.out.println("Invalid Indian factor :"+in);
}
vIsIndian=(in.equals("Y"))?true:false;
String vPANNumber=Keyboard.getString("Enter PAN Number : ");
String vAadharCardNumber=Keyboard.getString("Enter Aadhar card number : ");
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId(vEmployeeId);
employee.setName(vName);
employee.setDesignation(designation);
if(vGender.equalsIgnoreCase("male")) employee.setGender(Employee.MALE);
if(vGender.equalsIgnoreCase("female")) employee.setGender(Employee.FEMALE);
employee.setDateOfBirth(vDateOfBirth);
employee.isIndian(vIsIndian);
employee.setBasicSalary(vBasicSalary);
employee.setPANNumber(vPANNumber);
employee.setAadharCardNumber(vAadharCardNumber);
employeeManager.update(employee);
System.out.println(" Employee updated");
}catch(BLException b)
{
List<String> list=b.getExceptions();
for(int i=0;i<list.size();i++)
{
String g=list.get(i);
System.out.println(g);
}
}
}
}
