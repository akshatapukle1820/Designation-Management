package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
import java.sql.*;
import java.math.*;
import java.io.*;
import java.util.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
public void add(EmployeeDTOInterface employeeDTO)throws DAOException
{
String vEmployeeId=null;
String vName=employeeDTO.getName().trim();
int vDesignationCode=employeeDTO.getDesignationCode();
java.util.Date DateOfBirth=employeeDTO.getDateOfBirth();
java.sql.Date dob=new java.sql.Date(DateOfBirth.getYear(),DateOfBirth.getMonth(),DateOfBirth.getDate());
BigDecimal vBasicSalary=employeeDTO.getBasicSalary();
String vGender=employeeDTO.getGender();
boolean vIsIndian=employeeDTO.isIndian();
String vPANNumber=employeeDTO.getPANNumber().trim();
String vAadharCardNumber=employeeDTO.getAadharCardNumber().trim();
boolean designationCodeExists=new DesignationDAO().exists(vDesignationCode);
if(designationCodeExists==false) throw new DAOException("Invalid designation code : "+vDesignationCode);
if(vName.trim()==null)throw new DAOException("Name should not be null");
if(vName.trim().length()==0 || vName.trim().length()>35)throw new DAOException("Invalid Name : "+vName);
String Name=vName.trim().substring(0,1).toUpperCase()+vName.trim().substring(1).toLowerCase();
if(vPANNumber.trim()==null)throw new DAOException("Pan Number should not be null");
if(vPANNumber.trim().length()==0 || vPANNumber.trim().length()>20)throw new DAOException("Invalid Pan Number : "+vPANNumber);
if(vPANNumber.startsWith("AMP")==false)throw new DAOException("Pan Number should be starts with AMP");
if(vAadharCardNumber.trim()==null)throw new DAOException("Aadhar Card Number should not be null");
if(vAadharCardNumber.trim().length()==0 || vAadharCardNumber.trim().length()>20)throw new DAOException("Invalid Aadhar Card Number : "+vAadharCardNumber);
String AadharCardNumber=vAadharCardNumber.substring(1,4).toUpperCase()+vAadharCardNumber.substring(4);
if(vAadharCardNumber.startsWith("A"+AadharCardNumber)==false)throw new DAOException("Invalid Aadhar Card Number");
long lastGeneratedEmployeeId=10000;
int count=0;
long vNewEmployeeId;
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,vPANNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Pan Number already exists : "+vPANNumber);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,vAadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar Card Number already exists : "+vAadharCardNumber);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where pan_number=? and aadhar_card_number=?");
preparedStatement.setString(1,vPANNumber);
preparedStatement.setString(2,vAadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException(" Pan Number : "+vPANNumber+" and Aadhar Card Number : "+vAadharCardNumber+" already exists ");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,gender,is_indian,pan_number,aadhar_card_number) values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,Name);
preparedStatement.setInt(2,vDesignationCode);
preparedStatement.setDate(3,dob);
preparedStatement.setBigDecimal(4,vBasicSalary);
preparedStatement.setString(5,vGender);
preparedStatement.setBoolean(6,vIsIndian);
preparedStatement.setString(7,vPANNumber);
preparedStatement.setString(8,vAadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int employeeId=resultSet.getInt(1);
vNewEmployeeId=lastGeneratedEmployeeId+employeeId;
vEmployeeId="EMP"+vNewEmployeeId;
employeeDTO.setEmployeeId(vEmployeeId);	 
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO)throws DAOException
{
SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
String vEmployeeId=employeeDTO.getEmployeeId();
if(vEmployeeId.trim()==null)throw new DAOException("Employee Id should not be null");
if(vEmployeeId.startsWith("EMP")==false)throw new DAOException("Invalid employee Id : "+vEmployeeId);
String EmployeeId=vEmployeeId.substring(3);
int employeeID=Integer.parseInt(EmployeeId)-10000;
String vName=employeeDTO.getName().trim();
int vDesignationCode=employeeDTO.getDesignationCode();
java.util.Date DateOfBirth=employeeDTO.getDateOfBirth();
java.sql.Date dob=new java.sql.Date(DateOfBirth.getYear(),DateOfBirth.getMonth(),DateOfBirth.getDate());
String vDateOfBirth=sdf.format(DateOfBirth);
BigDecimal vBasicSalary=employeeDTO.getBasicSalary();
String vGender=employeeDTO.getGender();
boolean vIsIndian=employeeDTO.isIndian();
String vPANNumber=employeeDTO.getPANNumber().trim();
String vAadharCardNumber=employeeDTO.getAadharCardNumber().trim();
boolean designationCodeExists=new DesignationDAO().exists(vDesignationCode);
if(vEmployeeId.trim()==null)throw new DAOException("Employee Id should not be null");
if(vEmployeeId.trim().startsWith("EMP")==false)throw new DAOException("Employee Id should be starts with EMP");
if(designationCodeExists==false) throw new DAOException("Invalid designation code : "+vDesignationCode);
if(vName.trim()==null)throw new DAOException("Name should not be null");
if(vName.trim().length()==0 || vName.trim().length()>35)throw new DAOException("Invalid Name : "+vName);
String Name=vName.trim().substring(0,1).toUpperCase()+vName.trim().substring(1).toLowerCase() ;
if(vPANNumber.trim()==null)throw new DAOException("Pan Number should not be null");
if(vPANNumber.trim().length()==0 || vPANNumber.trim().length()>20)throw new DAOException("Invalid Pan Number : "+vPANNumber);
if(vPANNumber.startsWith("AMP")==false)throw new DAOException("Pan Number should be starts with AMP");
if(vAadharCardNumber.trim()==null)throw new DAOException("Aadhar Card Number should not be null");
if(vAadharCardNumber.trim().length()==0 || vAadharCardNumber.trim().length()>20)throw new DAOException("Invalid Aadhar Card Number : "+vAadharCardNumber);
String AadharCardNumber=vAadharCardNumber.substring(1,4).toUpperCase()+vAadharCardNumber.substring(4);
if(vAadharCardNumber.startsWith("A"+AadharCardNumber)==false)throw new DAOException("Invalid Aadhar Card Number");
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,employeeID);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("EmployeeId does not exists : "+vEmployeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where pan_number=? and employee_id!=?");
preparedStatement.setString(1,vPANNumber);
preparedStatement.setInt(2,employeeID);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Pan number : "+vPANNumber+" already exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=? and employee_id!=?");
preparedStatement.setString(1,vAadharCardNumber);
preparedStatement.setInt(2,employeeID);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar Card Number : "+vAadharCardNumber+" already exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,Name);
preparedStatement.setInt(2,vDesignationCode);
preparedStatement.setDate(3,dob);
preparedStatement.setBigDecimal(4,vBasicSalary);
preparedStatement.setString(5,vGender);
preparedStatement.setBoolean(6,vIsIndian);
preparedStatement.setString(7,vPANNumber);
preparedStatement.setString(8,vAadharCardNumber);
preparedStatement.setInt(9,employeeID);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId.trim()==null)throw new DAOException("Empoyee Id should not be null");
if(employeeId.trim().startsWith("EMP")==false)throw new DAOException("Employee Id should be starts with EMP ");
String EmployeeId=employeeId.substring(3);
int employeeID=Integer.parseInt(EmployeeId)-10000;
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,employeeID);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("EmployeeId does not exists : "+employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,employeeID);
preparedStatement.executeUpdate();		
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId.trim()==null)throw new DAOException("Employee Id should not be null");
if(employeeId.trim().startsWith("EMP")==false)throw new DAOException("Employee Id should be starts with EMP ");
String EmployeeId=employeeId.substring(3);
int employeeID=Integer.parseInt(EmployeeId)-10000;
Connection connection;
connection=DAOConnection.getConnection();
EmployeeDTOInterface employeeDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,employeeID);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Employee Id does not exists : "+employeeID);
}
String name="";
int designationCode=0;
java.sql.Date dateOfBirth;
BigDecimal basicSalary=new BigDecimal(0);
String gender="";
boolean isIndian=false;
String panNumber="";
String aadharCardNumber="";
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
dateOfBirth=resultSet.getDate("date_of_birth");
basicSalary=resultSet.getBigDecimal("basic_salary");
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
panNumber=resultSet.getString("pan_number");
aadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("EMP"+String.valueOf(10000+resultSet.getInt("employee_id")));
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
if(gender.equalsIgnoreCase("M"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(isIndian);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return employeeDTO;
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber.trim()==null)throw new DAOException("Pan Number should not be null");
if(panNumber.trim().length()==0 || panNumber.trim().length()>20)throw new DAOException("Invalid Pan Number : ");
if(panNumber.startsWith("AMP")==false)throw new DAOException("Pan Number should be starts with AMP");
Connection connection;
connection=DAOConnection.getConnection();
EmployeeDTOInterface employeeDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Pan Number does not exists : "+panNumber);
}
String name="";
int designationCode=0;
java.sql.Date dateOfBirth;
BigDecimal basicSalary=new BigDecimal(0);
String gender="";
boolean isIndian=false;
String aadharCardNumber="";
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
dateOfBirth=resultSet.getDate("date_of_birth");
basicSalary=resultSet.getBigDecimal("basic_salary");
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
panNumber=resultSet.getString("pan_number");
aadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("EMP"+String.valueOf(10000+resultSet.getInt("employee_id")));
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
if(gender.equalsIgnoreCase("M"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(isIndian);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return employeeDTO;
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber.trim()==null)throw new DAOException("Aadhar Card Number should not be null");
if(aadharCardNumber.trim().length()==0 || aadharCardNumber.trim().length()>20)throw new DAOException("Invalid Aadhar Card Number : "+aadharCardNumber);
String AadharCardNumber=aadharCardNumber.substring(1,4).toUpperCase()+aadharCardNumber.substring(4);
if(aadharCardNumber.startsWith("A"+AadharCardNumber)==false)throw new DAOException("Invalid Aadhar Card Number");
Connection connection;
connection=DAOConnection.getConnection();
EmployeeDTOInterface employeeDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar Card Number does not exists : "+aadharCardNumber);
}
String name="";
int designationCode=0;
java.sql.Date dateOfBirth;
BigDecimal basicSalary=new BigDecimal(0);
String gender="";
boolean isIndian=false;
String panNumber="";
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
dateOfBirth=resultSet.getDate("date_of_birth");
basicSalary=resultSet.getBigDecimal("basic_salary");
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
panNumber=resultSet.getString("pan_number");
aadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("EMP"+String.valueOf(10000+resultSet.getInt("employee_id")));
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
if(gender.equalsIgnoreCase("M"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(isIndian);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return employeeDTO;
}
public List<EmployeeDTOInterface>getAll() throws DAOException
{
Connection connection;
connection=DAOConnection.getConnection();
List<EmployeeDTOInterface> list;
EmployeeDTOInterface employeeDTO;
try
{
list=new LinkedList<>();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from employee");
String name="";
int designationCode=0;
java.sql.Date dateOfBirth;
BigDecimal basicSalary=new BigDecimal(0);
String gender="";
boolean isIndian=false;
String panNumber="";
String aadharCardNumber="";
while(resultSet.next())
{
name=resultSet.getString("name").trim();
designationCode=resultSet.getInt("designation_code");
dateOfBirth=resultSet.getDate("date_of_birth");
basicSalary=resultSet.getBigDecimal("basic_salary");
gender=resultSet.getString("gender");
isIndian=resultSet.getBoolean("is_indian");
panNumber=resultSet.getString("pan_number");
aadharCardNumber=resultSet.getString("aadhar_card_number");
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("EMP"+String.valueOf(10000+resultSet.getInt("employee_id")));
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setBasicSalary(basicSalary);
if(gender.equalsIgnoreCase("M"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
list.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close(); 
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return list;
}
public List<EmployeeDTOInterface>getByDesignation(int designationCode) throws DAOException
{
if(designationCode==0)throw new DAOException("Designation Code should not be zero");
Connection connection;
connection=DAOConnection.getConnection();
List<EmployeeDTOInterface> list;
EmployeeDTOInterface employeeDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation Code does not exists : "+designationCode);
}
list=new LinkedList<>();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("EMP"+String.valueOf(10000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
if((resultSet.getString("gender")).equalsIgnoreCase("M"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
list.add(employeeDTO);
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("EMP"+String.valueOf(10000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
if((resultSet.getString("gender")).equalsIgnoreCase("M"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
list.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close(); 
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return list;
}
public List<EmployeeDTOInterface>getByDateOfBirth(java.util.Date dateOfBirth) throws DAOException
{
if(dateOfBirth==null)throw new DAOException("Date of birth should not be null");
Connection connection;
connection=DAOConnection.getConnection();
List<EmployeeDTOInterface> list;
EmployeeDTOInterface employeeDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where date_of_birth=?");
java.sql.Date dob=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(1,dob);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Date of Birth does not exists : "+dateOfBirth);
}
list=new LinkedList<>();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("EMP"+String.valueOf(10000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
if((resultSet.getString("gender")).equalsIgnoreCase("M"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
list.add(employeeDTO);
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("EMP"+String.valueOf(10000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(resultSet.getDate("date_of_birth"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
if((resultSet.getString("gender")).equalsIgnoreCase("M"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
list.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close(); 
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return list;
}
public int getCount() throws DAOException
{
Connection connection;
connection=DAOConnection.getConnection();	
int count=0;
try
{
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) as no_of_employee from employee");
resultSet.next();
count=resultSet.getInt("no_of_employee");
resultSet.close();
statement.close();
connection.close(); 
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return count;
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId.trim()==null)throw new DAOException("Employee id should not be null ");
if(employeeId.trim().startsWith("EMP")==false)throw new DAOException("Employee Id should be starts with EMP ");
String vEmployeeId=employeeId.substring(3);
int employeeID=Integer.parseInt(vEmployeeId)-10000;
boolean EmployeeIdExists=false;
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,employeeID);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
EmployeeIdExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return EmployeeIdExists;
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber.trim()==null)throw new DAOException("Pan Number should not be null");
if(panNumber.trim().length()==0 || panNumber.trim().length()>20)throw new DAOException("Invalid Pan Number : "+panNumber.trim());
if(panNumber.startsWith("AMP")==false)throw new DAOException("Pan Number should be starts with AMP");
boolean panNumberExists=false;
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return panNumberExists;
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber.trim()==null)throw new DAOException("Pan Number should not be null");
if(aadharCardNumber.trim().length()==0 || aadharCardNumber.trim().length()>20)throw new DAOException("Invalid Aadhar Card Number : "+aadharCardNumber.trim());
String AadharCardNumber=aadharCardNumber.substring(1,4).toUpperCase()+aadharCardNumber.substring(4);
if(aadharCardNumber.startsWith("A"+AadharCardNumber)==false)throw new DAOException("Invalid Aadhar Card Number");
boolean aadharCardNumberExists=false;
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return aadharCardNumberExists;
}
}