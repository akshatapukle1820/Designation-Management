package com.thinking.machines.hr.dl.dao;
import java.io.*;
import java.math.*;
import java.util.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
String vEmployeeId;
String vName=employeeDTO.getName().trim();
int vDesignationCode=employeeDTO.getDesignationCode();
java.util.Date vDateOfBirth=employeeDTO.getDateOfBirth();
BigDecimal vBasicSalary=employeeDTO.getBasicSalary();
String vGender=employeeDTO.getGender();
boolean vIsIndian=employeeDTO.isIndian();
String vPANNumber=employeeDTO.getPANNumber().trim();
String vAadharCardNumber=employeeDTO.getAadharCardNumber().trim();
boolean designationCodeExists=new DesignationDAO().exists(vDesignationCode);
if(designationCodeExists==false) throw new DAOException("Invalid designation code : "+vDesignationCode);
try
{
File file=new File(EMPLOYEE_DATA_FILE);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
long lastGeneratedEmployeeId=10000;
int count=0;
if(randomAccessFile.length()==0)
{
randomAccessFile.writeBytes(String.format("%10d",lastGeneratedEmployeeId));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.format("%10d",count));
randomAccessFile.writeBytes("\n");
}
randomAccessFile.seek(0);
lastGeneratedEmployeeId=Long.parseLong(randomAccessFile.readLine().trim());
count=Integer.parseInt(randomAccessFile.readLine().trim());
long vNewEmployeeId;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
if(count==0)
{
vNewEmployeeId=lastGeneratedEmployeeId+1;
vEmployeeId="EMP"+vNewEmployeeId;
randomAccessFile.writeBytes(vEmployeeId);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vName);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.valueOf(vDesignationCode));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(sdf.format(vDateOfBirth));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vBasicSalary.toPlainString());
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vGender);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.valueOf(vIsIndian));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vPANNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vAadharCardNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.seek(0);
randomAccessFile.writeBytes(String.format("%10d\n%10d\n",vNewEmployeeId,count+1));
randomAccessFile.close();
employeeDTO.setEmployeeId(vEmployeeId);
return;
}
String fEmployeeId;
String fName;
int fDesignationCode;
java.util.Date fDateOfBirth;
BigDecimal fBasicSalary;
String fGender;
boolean fIsIndian;
String fPANNumber;
String fAadharCardNumber;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
int i;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(i=1;i<=7;i++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberFound==false) panNumberFound=vPANNumber.equalsIgnoreCase(fPANNumber);
if(aadharCardNumberFound==false)
aadharCardNumberFound=vAadharCardNumber.equalsIgnoreCase(fAadharCardNumber);
if(panNumberFound && aadharCardNumberFound) break;
}
if(panNumberFound==false && aadharCardNumberFound==true)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number "+vAadharCardNumber+" exists.");
}
if(panNumberFound==true && aadharCardNumberFound==false)
{
randomAccessFile.close();
throw new DAOException("PAN Number "+vPANNumber+" exists.");
}
if(panNumberFound && aadharCardNumberFound)
{
randomAccessFile.close();
throw new DAOException("PAN Number "+vPANNumber+" and Aadhar card number"+vAadharCardNumber+" exists.");
}
vNewEmployeeId=lastGeneratedEmployeeId+1;
vEmployeeId="EMP"+vNewEmployeeId;
randomAccessFile.writeBytes(vEmployeeId);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vName);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.valueOf(vDesignationCode));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(sdf.format(vDateOfBirth));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vBasicSalary.toPlainString());
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vGender);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.valueOf(vIsIndian));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vPANNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vAadharCardNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.seek(0);
randomAccessFile.writeBytes(String.format("%10d\n%10d\n",vNewEmployeeId,count+1));
randomAccessFile.close();
employeeDTO.setEmployeeId(vEmployeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
String vEmployeeId=employeeDTO.getEmployeeId();
String vName=employeeDTO.getName().trim();
int vDesignationCode=employeeDTO.getDesignationCode();
java.util.Date vDateOfBirth=employeeDTO.getDateOfBirth();
BigDecimal vBasicSalary=employeeDTO.getBasicSalary();
String vGender=employeeDTO.getGender();
boolean vIsIndian=employeeDTO.isIndian();
String vPANNumber=employeeDTO.getPANNumber().trim();
String vAadharCardNumber=employeeDTO.getAadharCardNumber().trim();
try
{
boolean employeeIdFound;
String panNumberFoundAgainst;
String aadharCardNumberFoundAgainst;
File file=new File(EMPLOYEE_DATA_FILE);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id : "+vEmployeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
employeeIdFound=false;
panNumberFoundAgainst=null;
aadharCardNumberFoundAgainst=null;
String fEmployeeId;
String fName;
int fDesignationCode;
java.util.Date fDateOfBirth;
BigDecimal fBasicSalary;
String fGender;
boolean fIsIndian;
String fPANNumber;
String fAadharCardNumber;
int i;
long recordToUpdateIsAtPosition=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) recordToUpdateIsAtPosition=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(i=1;i<=6;i++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(!employeeIdFound) employeeIdFound=fEmployeeId.equals(vEmployeeId);
if(panNumberFoundAgainst==null && fPANNumber.equalsIgnoreCase(vPANNumber))
panNumberFoundAgainst=fEmployeeId;
if(aadharCardNumberFoundAgainst==null &&
fAadharCardNumber.equalsIgnoreCase(vAadharCardNumber))
aadharCardNumberFoundAgainst=fEmployeeId;
if(employeeIdFound && panNumberFoundAgainst!=null && aadharCardNumberFoundAgainst!=null)
break;
}
if(!employeeIdFound)
{
System.out.println("dao id not found");
randomAccessFile.close();
throw new DAOException("Invalid employee Id : "+vEmployeeId);
}
boolean panNumberFound=panNumberFoundAgainst!=null &&
panNumberFoundAgainst.equals(vEmployeeId)==false;
boolean aadharCardNumberFound=aadharCardNumberFoundAgainst!=null &&
aadharCardNumberFoundAgainst.equals(vEmployeeId)==false;
if(panNumberFound==false && aadharCardNumberFound==true)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number "+vAadharCardNumber+" exists.");
}
if(panNumberFound==true && aadharCardNumberFound==false)
{
randomAccessFile.close();
throw new DAOException("PAN Number "+vPANNumber+" exists.");
}
if(panNumberFound && aadharCardNumberFound)
{
randomAccessFile.close();
throw new DAOException("PAN Number "+vPANNumber+" and Aadhar card number"+vAadharCardNumber+" exists.");
}
boolean designationCodeExists=new DesignationDAO().exists(vDesignationCode);
if(designationCodeExists==false)
{
randomAccessFile.close();
throw new DAOException("Invalid designation code : "+vDesignationCode);
}
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(vEmployeeId);
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(vName);
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(String.valueOf(vDesignationCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(sdf.format(vDateOfBirth));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(vBasicSalary.toPlainString());
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(vGender);
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(String.valueOf(vIsIndian));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(vPANNumber);
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(vAadharCardNumber);
tmpRandomAccessFile.writeBytes("\n");
randomAccessFile.seek(recordToUpdateIsAtPosition);
for(i=1;i<=9;i++) randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(recordToUpdateIsAtPosition);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(recordToUpdateIsAtPosition+tmpRandomAccessFile.length());
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
try
{
boolean employeeIdFound;
File file=new File(EMPLOYEE_DATA_FILE);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id : "+employeeId);
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
employeeIdFound=false;
String fEmployeeId;
int i;
long recordToDeleteIsAtPosition=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
recordToDeleteIsAtPosition=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equals(employeeId))
{
employeeIdFound=true;
break;
}
for(i=1;i<=8;i++) randomAccessFile.readLine();
}
if(!employeeIdFound)
{
randomAccessFile.close();
throw new DAOException("Invalid employee Id : "+employeeId);
}
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(recordToDeleteIsAtPosition);
for(i=1;i<=9;i++) randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(recordToDeleteIsAtPosition);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(recordToDeleteIsAtPosition+tmpRandomAccessFile.length());
count--;
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(String.format("%10d",count));
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) throw new DAOException("Invalid employee id. : "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
if(count==0)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fGender;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equals(employeeId))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(sdf.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
// no code required, as date in file will be of valid format
}
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
fGender=randomAccessFile.readLine();
if(fGender.equals("male"))
{
employeeDTO.setGender(EmployeeDTOInterface.MALE);
}
if(fGender.equals("female")) // we can also go for else, instead of if as there are only 2 states
{
employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
}
employeeDTO.isIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) throw new DAOException("Invalid PAN Number : "+panNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid PAN Number : "+panNumber);
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
if(count==0)
{
randomAccessFile.close();
throw new DAOException("Invalid PAN Number : "+panNumber);
}
panNumber=panNumber.trim();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
java.util.Date fDateOfBirth;
BigDecimal fBasicSalary;
String fGender;
boolean fIsIndian;
String fPANNumber;
String fAadharCardNumber;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
fDateOfBirth=null; // this will never happen, but assignment required
// otherwise the code won't compile becuase of unassigned local
// variable on all paths
}
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fGender=randomAccessFile.readLine();
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setBasicSalary(fBasicSalary);
if(fGender.equals("male"))
{
employeeDTO.setGender(EmployeeDTOInterface.MALE);
}
if(fGender.equals("female")) // we can also go for else, instead of if as there are only 2 states
{
employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
}
employeeDTO.isIndian(fIsIndian);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid PAN Number : "+panNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws
DAOException
{
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) throw new DAOException("Invalid Aadhar card Number :"+aadharCardNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card Number : "+aadharCardNumber);
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
if(count==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card Number : "+aadharCardNumber);
}
aadharCardNumber=aadharCardNumber.trim();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
java.util.Date fDateOfBirth;
BigDecimal fBasicSalary;
String fGender;
boolean fIsIndian;
String fPANNumber;
String fAadharCardNumber;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
fDateOfBirth=null; // this will never happen, but assignment required
// otherwise the code won't compile becuase of unassigned local
// variable on all paths
}
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fGender=randomAccessFile.readLine();
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setBasicSalary(fBasicSalary);
if(fGender.equals("male"))
{
employeeDTO.setGender(EmployeeDTOInterface.MALE);
}
if(fGender.equals("female")) // we can also go for else, instead of if as there are only 2 states
{
employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
}
employeeDTO.isIndian(fIsIndian);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card Number : "+aadharCardNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public List<EmployeeDTOInterface> getAll() throws DAOException 
{
List<EmployeeDTOInterface> employees;
employees=new LinkedList<>();
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return employees; 
}
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
java.util.Date fDateOfBirth;
BigDecimal fBasicSalary;
String fGender;
boolean fIsIndian;
String fPANNumber;
String fAadharCardNumber;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException parseException) 
{
fDateOfBirth=null; // this will never happen, but assignment required
// otherwise the code won't compile becuase of unassigned local
// variable on all paths
}
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fGender=randomAccessFile.readLine();
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setBasicSalary(fBasicSalary);
if(fGender.equals("M"))
{
employeeDTO.setGender(EmployeeDTOInterface.MALE);
}
if(fGender.equals("F")) // we can also go for else, instead of if as there are only 2 states
{
employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
}
employeeDTO.isIndian(fIsIndian);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
employees.add(employeeDTO);
}
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
return employees;
}
public List<EmployeeDTOInterface> getByDesignation(int designationCode) throws DAOException
{
if(new DesignationDAO().exists(designationCode)==false)
{
throw new DAOException("Invalid designation code : "+designationCode);
}
List<EmployeeDTOInterface> employees;
employees=new LinkedList<>();
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
java.util.Date fDateOfBirth;
BigDecimal fBasicSalary;
String fGender;
boolean fIsIndian;
String fPANNumber;
String fAadharCardNumber;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
int i;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length()) 
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode!=designationCode) 
{
// doesn't match, we are not concerned about this record
// out of 9, 3 lines have been read, 6 remaning
for(i=1;i<=6;i++) randomAccessFile.readLine();
continue; 
}
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException parseException) 
{
fDateOfBirth=null; // this will never happen, but assignment required
// otherwise the code won't compile becuase of unassigned local
// variable on all paths 
}
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fGender=randomAccessFile.readLine();
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setBasicSalary(fBasicSalary);
if(fGender.equals("male"))
{
employeeDTO.setGender(EmployeeDTOInterface.MALE);
}
if(fGender.equals("female")) // we can also go for else, instead of if as there are only 2 states
{
employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
}
employeeDTO.isIndian(fIsIndian);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
employees.add(employeeDTO);
}
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
return employees;
}
public List<EmployeeDTOInterface> getByDateOfBirth(java.util.Date dateOfBirth) throws
DAOException
{
List<EmployeeDTOInterface> employees;
employees=new LinkedList<>();
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
java.util.Date fDateOfBirth;
BigDecimal fBasicSalary;
String fGender;
boolean fIsIndian;
String fPANNumber;
String fAadharCardNumber;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
int i;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
fDateOfBirth=null; // this will never happen, but assignment required
// otherwise the code won't compile becuase of unassigned local
// variable on all paths
}
if(!(fDateOfBirth.getDate()==dateOfBirth.getDate() &&
fDateOfBirth.getMonth()==dateOfBirth.getMonth() &&
fDateOfBirth.getYear()==dateOfBirth.getYear()))
{
// doesn't match, we are not concerned about this record
// out of 9, 4 lines have been read, 5 remaning
for(i=1;i<=5;i++) randomAccessFile.readLine();
continue;
}
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fGender=randomAccessFile.readLine();
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setBasicSalary(fBasicSalary);
if(fGender.equals("male"))
{
employeeDTO.setGender(EmployeeDTOInterface.MALE);
}
if(fGender.equals("female")) // we can also go for else, instead of if as there are only 2 states
{
employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
}
employeeDTO.isIndian(fIsIndian);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
employees.add(employeeDTO);
}
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
return employees;
}
public int getCount() throws DAOException
{
int count=0;
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) return count;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return count;
}
randomAccessFile.readLine();
count=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return count;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
if(count==0)
{
randomAccessFile.close();
return false;
}
int i;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(randomAccessFile.readLine().equals(employeeId))
{
randomAccessFile.close();
return true;
}
for(i=1;i<=8;i++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
if(count==0)
{
randomAccessFile.close();
return false;
}
int i;
// PAN Number data is in the 8th field of the 9 field record
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(i=1;i<=7;i++) randomAccessFile.readLine();
if(randomAccessFile.readLine().equalsIgnoreCase(panNumber))
{
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
try
{
File file=new File(EMPLOYEE_DATA_FILE);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
if(count==0)
{
randomAccessFile.close();
return false;
}
int i;
// Aadhar Card Number data is in the 9th field of the 9 field record
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(i=1;i<=8;i++) randomAccessFile.readLine();
if(randomAccessFile.readLine().equalsIgnoreCase(aadharCardNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}