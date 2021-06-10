package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.tmcommon.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
import java.math.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<Integer,List<EmployeeInterface>> designationWiseEmployees;
private Map<String,EmployeeInterface> employeeIdWiseEmployee;
private Map<String,EmployeeInterface> panNumberWiseEmployee;
private Map<String,EmployeeInterface> aadharNumberWiseEmployee;
private Map<java.util.Date,List<EmployeeInterface>> dateOfBirthWiseEmployees;
private List<EmployeeInterface> employeeIdWiseOrderedList;
private List<EmployeeInterface> employeeNameWiseOrderedList;
final private static EmployeeManagerInterface employeeManager;
private EmployeeManager()
{
populateDataStructures();
}
static
{
employeeManager=new EmployeeManager();
}
public static EmployeeManagerInterface getEmployeeManager()
{
return employeeManager;
}
private void populateDataStructures()
{
designationWiseEmployees=new HashMap<>();
employeeIdWiseEmployee=new HashMap<>();
panNumberWiseEmployee=new HashMap<>();
aadharNumberWiseEmployee=new HashMap<>();
dateOfBirthWiseEmployees=new HashMap<>();
employeeIdWiseOrderedList=new LinkedList<>();
employeeNameWiseOrderedList=new LinkedList<>();
List<EmployeeDTOInterface> dlEmployees;
try
{
dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
int designationCode;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
DesignationInterface designation;
String vPanNumber;
String vAadharCardNumber;
String vEmployeeId;
Date vDateOfBirth;
for(EmployeeDTOInterface dlEmployee :dlEmployees)
{
employee=new Employee();
//POJOCopier.copy(employee,dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designationCode=dlEmployee.getDesignationCode();
designation=designationManager.getByCode(designationCode);
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.isIndian(dlEmployee.isIndian());
String gender=dlEmployee.getGender();
if(gender.equalsIgnoreCase("Male"))employee.setGender(EmployeeInterface.MALE);
else employee.setGender(EmployeeInterface.FEMALE);
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
employee.setPANNumber(dlEmployee.getPANNumber());
vEmployeeId=employee.getEmployeeId();
vAadharCardNumber=employee.getAadharCardNumber();
vPanNumber=employee.getPANNumber();
vDateOfBirth=employee.getDateOfBirth();
employeeIdWiseEmployee.put(vEmployeeId,employee);
panNumberWiseEmployee.put(vPanNumber,employee);
aadharNumberWiseEmployee.put(vAadharCardNumber,employee);
if(designationWiseEmployees.containsKey(designationCode))
{
List<EmployeeInterface> employees;
employees=designationWiseEmployees.get(designationCode);
employees.add(employee);
}
else
{
List<EmployeeInterface> employees=new LinkedList<>();
employees.add(employee);
designationWiseEmployees.put(designationCode,employees);
}
if(dateOfBirthWiseEmployees.containsKey(vDateOfBirth))
{
List<EmployeeInterface> employees;
employees=dateOfBirthWiseEmployees.get(vDateOfBirth);
employees.add(employee);
}
else
{
List<EmployeeInterface> employees=new LinkedList<>();
employees.add(employee);
dateOfBirthWiseEmployees.put(vDateOfBirth,employees);
}
employeeIdWiseOrderedList.add(employee);
employeeNameWiseOrderedList.add(employee);
}

/*Collections.sort(employeeIdWiseOrderedList,new Comparator<EmployeeInterface>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
String leftName=left.getName().toUpperCase();
String rightName=right.getName().toUpperCase();
return leftName.compareTo(
rightName);
}
});*/
Collections.sort(employeeNameWiseOrderedList,new Comparator<EmployeeInterface>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
String leftName=left.getName().toUpperCase();
String rightName=right.getName().toUpperCase();
return leftName.compareTo(rightName);
}
});
}catch(Exception e)
{
System.out.println(e);
}
}
public boolean isDesignationAlloted(int designationCode) throws BLException
{
BLException blException=new BLException();
if(designationCode<=0)blException.addException("designationCode","Invalid Designation Code");
if(blException.hasException("designationCode"))throw blException;
return designationWiseEmployees.containsKey(designationCode);
}
public int countOfEmployeesWithSameDesignation(int designationCode) throws BLException
{
BLException blException=new BLException();
if(designationCode<=0)blException.addException("designationCode","Invalid Designation Code");
if(blException.hasException("designationCode"))throw blException;
List<EmployeeInterface> employees;
employees=designationWiseEmployees.get(designationCode);
if(employees==null)blException.addException("designationCode","DesignationCode not exists against any employees");
if(blException.hasException("designationCode"))throw blException;
return employees.size();
}
public void add(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)blException.addException("employeeId","Required data of employee");
String vEmployeeId="";
String vName=employee.getName();
if(vName==null||vName.trim().length()==0)blException.addException("employeeName","Required employee's name");
vName=vName.trim();
DesignationInterface vDesignation=employee.getDesignation();
int vDesignationCode=vDesignation.getCode();
//boolean designationCodeExists=designationWiseEmployees.containsKey(vDesignationCode);
DesignationManagerInterface designationManager=DesignationManager.getInstance();
boolean designationCodeExists=designationManager.codeExists(vDesignationCode);
if(designationCodeExists==false)blException.addException("designationCode","invalid Designation Code");
Date vDateOfBirth=employee.getDateOfBirth();
BigDecimal vBasicSalary=employee.getBasicSalary();
boolean vIsIndian=employee.isIndian();
String vGender=employee.getGender();
String vPanNumber=employee.getPANNumber();
if(vPanNumber==null||vPanNumber.trim().length()==0)blException.addException("panCardNumber","Required panNumber");
vPanNumber=vPanNumber.trim();
String vAadharCardNumber=employee.getAadharCardNumber();
if(vAadharCardNumber==null||vAadharCardNumber.trim().length()==0)blException.addException("aadharCardNumber","Required AadharCardNumber");
vAadharCardNumber=vAadharCardNumber.trim();
if(aadharNumberWiseEmployee.containsKey(vAadharCardNumber))blException.addException("aadharCardNumber","Aadhar Card Number already exist");
if(panNumberWiseEmployee.containsKey(vPanNumber))blException.addException("panCardNumber","Pan Card Number already exist");
if(blException.hasException("designationCode")||blException.hasException("aadharCardNumber")||blException.hasException("panCardNumber")||blException.hasException("employeeId")||blException.hasException("employeeName"))
{
throw blException;
}
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
//POJOCopier.copy(employeeDTO,employee);
employeeDTO.setName(vName);
employeeDTO.setDesignationCode(vDesignationCode);
employeeDTO.setDateOfBirth(vDateOfBirth);
employeeDTO.setBasicSalary(vBasicSalary);
if(vGender.equalsIgnoreCase("Male"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(vIsIndian);
employeeDTO.setAadharCardNumber(vAadharCardNumber);
employeeDTO.setPANNumber(vPanNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
try
{
employeeDAO.add(employeeDTO);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
vEmployeeId=employeeDTO.getEmployeeId();
EmployeeInterface dsEmployee;
dsEmployee=new Employee();
employee.setEmployeeId(vEmployeeId);
//POJOCopier.copy(dsEmployee,employee);
dsEmployee.setEmployeeId(vEmployeeId);
dsEmployee.setName(vName);
dsEmployee.setDesignation(vDesignation);
dsEmployee.setBasicSalary(vBasicSalary);
dsEmployee.setDateOfBirth(vDateOfBirth);
if(vGender.equalsIgnoreCase("Male"))dsEmployee.setGender(EmployeeInterface.MALE);
else dsEmployee.setGender(EmployeeInterface.FEMALE);
dsEmployee.isIndian(vIsIndian);
dsEmployee.setAadharCardNumber(vAadharCardNumber);
dsEmployee.setPANNumber(vPanNumber);
employeeIdWiseEmployee.put(vEmployeeId,dsEmployee);
panNumberWiseEmployee.put(vPanNumber,dsEmployee);
aadharNumberWiseEmployee.put(vAadharCardNumber,dsEmployee);
//List<EmployeeInterface> employees=designationWiseEmployees.get(vDesignationCode);
if(designationWiseEmployees.containsKey(vDesignationCode))
{
List<EmployeeInterface> employees;
employees=designationWiseEmployees.get(vDesignationCode);
employees.add(dsEmployee);
}
else
{
List<EmployeeInterface> employees=new LinkedList<>();
employees.add(dsEmployee);
designationWiseEmployees.put(vDesignationCode,employees);
}
if(dateOfBirthWiseEmployees.containsKey(vDateOfBirth))
{
List<EmployeeInterface> employees;
employees=designationWiseEmployees.get(vDesignationCode);
employees.add(dsEmployee);
}
else
{
List<EmployeeInterface> employees=new LinkedList<>();
employees.add(dsEmployee);
dateOfBirthWiseEmployees.put(vDateOfBirth,employees);
}
//employees.add(dsEmployee);
employeeIdWiseOrderedList.add(dsEmployee);
employeeNameWiseOrderedList.add(dsEmployee);
if(employeeIdWiseOrderedList.size()==0)
{
employeeIdWiseOrderedList.add(dsEmployee);
}
else
{
Comparator<EmployeeInterface> employeeIdComparator=new Comparator<>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
String leftEmployeeId=left.getEmployeeId();
String rightEmployeeId=right.getEmployeeId();
return leftEmployeeId.compareTo(rightEmployeeId);
}
}; 
EmployeeInterface tmp;
tmp=employeeIdWiseOrderedList.get(0);
if(employeeIdComparator.compare(dsEmployee,tmp)<=0)
{
employeeIdWiseOrderedList.add(0,dsEmployee);
}
else
{
tmp=employeeIdWiseOrderedList.get(employeeIdWiseOrderedList.size()-1);
if(employeeIdComparator.compare(dsEmployee,tmp)>=0)
{
employeeIdWiseOrderedList.add(dsEmployee);
}
else
{
int lb,ub,mid;
lb=0;
ub=employeeIdWiseOrderedList.size()-1;
int x;
while(lb<=ub)
{
mid=(lb+ub)/2;
x=employeeIdComparator.compare(employeeIdWiseOrderedList.get(mid),dsEmployee);
if(x==0)
{
employeeIdWiseOrderedList.add(mid,dsEmployee);
break;
}
else 
{
if(x<0)
{
if(employeeIdComparator.compare(employeeIdWiseOrderedList.get(mid+1),dsEmployee)>0)
{
employeeIdWiseOrderedList.add(mid+1,dsEmployee);
break;
}
lb=mid+1;
}
else
{
if(x>0)
{
if(employeeIdComparator.compare(employeeIdWiseOrderedList.get(mid-1),dsEmployee)<0)
{
employeeIdWiseOrderedList.add(mid,dsEmployee);
break;
}
ub=mid-1;
}
}
}
}
}
}
}
if(employeeNameWiseOrderedList.size()==0)
{
employeeNameWiseOrderedList.add(dsEmployee);
}else 
{
Comparator<EmployeeInterface> nameComparator=new Comparator<>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
return (left.getName().toUpperCase()).compareTo(right.getName().toUpperCase());
}
};
EmployeeInterface tmp=employeeNameWiseOrderedList.get(0);
if(nameComparator.compare(tmp,dsEmployee)>=0)
{
employeeNameWiseOrderedList.add(0,dsEmployee);
}
else
{
tmp=employeeNameWiseOrderedList.get(employeeNameWiseOrderedList.size()-1);
if(nameComparator.compare(tmp,dsEmployee)<=0)
{
employeeNameWiseOrderedList.add(dsEmployee);
}
else
{
int lb1,ub1,mid1;
lb1=0;
ub1=employeeNameWiseOrderedList.size()-1;
int x1;
while(lb1<=ub1)
{
mid1=(lb1+ub1)/2;
x1=nameComparator.compare(employeeNameWiseOrderedList.get(mid1),dsEmployee);
if(x1==0)
{
employeeNameWiseOrderedList.add(mid1,dsEmployee);
break;
}
else
{
if(x1<0)
{
if(nameComparator.compare(employeeNameWiseOrderedList.get(mid1+1),dsEmployee)>0)
{
employeeNameWiseOrderedList.add(mid1+1,dsEmployee);
break;
}
lb1=mid1+1;
}
else
{
if(nameComparator.compare(employeeNameWiseOrderedList.get(mid1-1),dsEmployee)<0)
{
employeeNameWiseOrderedList.add(mid1,dsEmployee);
break;
}
ub1=mid1-1;
}
}
}
}
}
}
}
public void update(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)blException.addException("employeeId","Required data of employee");
String vEmployeeId=employee.getEmployeeId();
if(employeeIdWiseEmployee.containsKey(vEmployeeId)==false)blException.addException("employeeId","Invalid EmployeeId.");
String vName=employee.getName();
if(vName==null||vName.trim().length()==0)blException.addException("employeeName","Required employee's name");
vName=vName.trim();
DesignationInterface vDesignation=employee.getDesignation();
int vDesignationCode=vDesignation.getCode();
//boolean designationCodeExists=designationWiseEmployees.containsKey(vDesignationCode);
DesignationManagerInterface designationManager=DesignationManager.getInstance();
boolean designationCodeExists=designationManager.codeExists(vDesignationCode);
if(designationCodeExists==false)blException.addException("designationCode","Invalid Designation Code");
Date vDateOfBirth=employee.getDateOfBirth();
BigDecimal vBasicSalary=employee.getBasicSalary();
boolean vIsIndian=employee.isIndian();
String vGender=employee.getGender();
String vPanNumber=employee.getPANNumber();
if(vPanNumber==null||vPanNumber.trim().length()==0)blException.addException("panCardNumber","Required panNumber");
vPanNumber=vPanNumber.trim();
String vAadharCardNumber=employee.getAadharCardNumber(); 
if(vAadharCardNumber==null||vAadharCardNumber.trim().length()==0)blException.addException("aadharCardNumber","Required AadharCardNumber");
vAadharCardNumber=vAadharCardNumber.trim();           
if(aadharNumberWiseEmployee.containsKey(vAadharCardNumber) && aadharNumberWiseEmployee.get(vAadharCardNumber).getEmployeeId().equals(vEmployeeId)==false)
{
blException.addException("aadharCardNumber","Aadhar Card Number already exist");
}
if(panNumberWiseEmployee.containsKey(vPanNumber) && aadharNumberWiseEmployee.get(vAadharCardNumber).getEmployeeId().equals(vEmployeeId)==false)
{
blException.addException("panCardNumber","Pan Card Number already exist");
}
if(blException.hasException("designationCode")||blException.hasException("aadharCardNumber")||blException.hasException("panCardNumber")||blException.hasException("employeeId")||blException.hasException("employeeName"))
{
throw blException;
}
//validations ends here
//update dl starts here
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
//POJOCopier.copy(employeeDTO,employee);
employeeDTO.setEmployeeId(vEmployeeId);
employeeDTO.setName(vName);
employeeDTO.setDesignationCode(vDesignationCode);
employeeDTO.setDateOfBirth(vDateOfBirth);
employeeDTO.setBasicSalary(vBasicSalary);
if(vGender.equalsIgnoreCase("Male"))employeeDTO.setGender(EmployeeDTOInterface.MALE);
else employeeDTO.setGender(EmployeeDTOInterface.FEMALE);
employeeDTO.isIndian(vIsIndian);
employeeDTO.setAadharCardNumber(vAadharCardNumber);
employeeDTO.setPANNumber(vPanNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
try
{
employeeDAO.update(employeeDTO);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
//update dl ends here
//update ds starts here
List<EmployeeInterface> employeeToBeRemovedFromList;
EmployeeInterface employeeToBeRemoved=employeeIdWiseEmployee.get(vEmployeeId);
employeeToBeRemovedFromList=designationWiseEmployees.get(employeeToBeRemoved.getDesignation().getCode());
employeeToBeRemovedFromList.remove(employeeToBeRemoved);
employeeIdWiseEmployee.remove(vEmployeeId);
aadharNumberWiseEmployee.remove(employeeToBeRemoved.getAadharCardNumber());
panNumberWiseEmployee.remove(employeeToBeRemoved.getPANNumber());
List<EmployeeInterface> employeeToBeRemovedFromList1;
employeeToBeRemovedFromList1=dateOfBirthWiseEmployees.get(employeeToBeRemoved.getDateOfBirth());
employeeToBeRemovedFromList1.remove(employeeToBeRemoved);
employeeIdWiseOrderedList.remove(employeeToBeRemoved);
employeeNameWiseOrderedList.remove(employeeToBeRemoved);
EmployeeInterface dsEmployee;
dsEmployee=new Employee();

//POJOCopier.copy(dsEmployee,employee);
dsEmployee.setEmployeeId(vEmployeeId);
dsEmployee.setName(vName);
dsEmployee.setDesignation(vDesignation);
dsEmployee.setBasicSalary(vBasicSalary);
dsEmployee.setDateOfBirth(vDateOfBirth);
if(vGender.equalsIgnoreCase("Male"))dsEmployee.setGender(EmployeeInterface.MALE);
else dsEmployee.setGender(EmployeeInterface.FEMALE);
dsEmployee.isIndian(vIsIndian);
dsEmployee.setAadharCardNumber(vAadharCardNumber);
dsEmployee.setPANNumber(vPanNumber);
employeeIdWiseEmployee.put(vEmployeeId,dsEmployee);
panNumberWiseEmployee.put(vPanNumber,dsEmployee);
aadharNumberWiseEmployee.put(vAadharCardNumber,dsEmployee);
//List<EmployeeInterface> employees=designationWiseEmployees.get(vDesignationCode);

if(designationWiseEmployees.containsKey(vDesignationCode))
{
List<EmployeeInterface> employees;
employees=designationWiseEmployees.get(vDesignationCode);
employees.add(dsEmployee);
}
else
{
List<EmployeeInterface> employees=new LinkedList<>();
employees.add(dsEmployee);
designationWiseEmployees.put(vDesignationCode,employees);
}
if(dateOfBirthWiseEmployees.containsKey(vDateOfBirth))
{
List<EmployeeInterface> employees;
employees=dateOfBirthWiseEmployees.get(vDesignationCode);
employees.add(dsEmployee);
}
else
{
List<EmployeeInterface> employees=new LinkedList<>();
employees.add(dsEmployee);
dateOfBirthWiseEmployees.put(vDateOfBirth,employees);
}

//employees.add(dsEmployee);
employeeIdWiseOrderedList.add(dsEmployee);
employeeNameWiseOrderedList.add(dsEmployee);
if(employeeIdWiseOrderedList.size()==0)
{
employeeIdWiseOrderedList.add(dsEmployee);
}
else
{
Comparator<EmployeeInterface> employeeIdComparator=new Comparator<>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
String leftEmployeeId=left.getEmployeeId();
String rightEmployeeId=right.getEmployeeId();
return leftEmployeeId.compareTo(rightEmployeeId);
}
}; 
EmployeeInterface tmp;
tmp=employeeIdWiseOrderedList.get(0);
if(employeeIdComparator.compare(dsEmployee,tmp)<=0)
{
employeeIdWiseOrderedList.add(0,dsEmployee);
}
else
{
tmp=employeeIdWiseOrderedList.get(employeeIdWiseOrderedList.size()-1);
if(employeeIdComparator.compare(dsEmployee,tmp)>=0)
{
employeeIdWiseOrderedList.add(dsEmployee);
}
else
{
int lb,ub,mid;
lb=0;
ub=employeeIdWiseOrderedList.size()-1;
int x;
while(lb<=ub)
{
mid=(lb+ub)/2;
x=employeeIdComparator.compare(employeeIdWiseOrderedList.get(mid),dsEmployee);
if(x==0)
{
employeeIdWiseOrderedList.add(mid,dsEmployee);
break;
}
else 
{
if(x<0)
{
if(employeeIdComparator.compare(employeeIdWiseOrderedList.get(mid+1),dsEmployee)>0)
{
employeeIdWiseOrderedList.add(mid+1,dsEmployee);
break;
}
lb=mid+1;
}
else
{
if(x>0)
{
if(employeeIdComparator.compare(employeeIdWiseOrderedList.get(mid-1),dsEmployee)<0)
{
employeeIdWiseOrderedList.add(mid,dsEmployee);
break;
}
ub=mid-1;
}
}
}
}
}
}
}
if(employeeNameWiseOrderedList.size()==0)
{
employeeNameWiseOrderedList.add(dsEmployee);
}else 
{
Comparator<EmployeeInterface> nameComparator=new Comparator<>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
return (left.getName().toUpperCase()).compareTo(right.getName().toUpperCase());
}
};
EmployeeInterface tmp=employeeNameWiseOrderedList.get(0);
if(nameComparator.compare(tmp,dsEmployee)>=0)
{
employeeNameWiseOrderedList.add(0,dsEmployee);
}
else
{
tmp=employeeNameWiseOrderedList.get(employeeNameWiseOrderedList.size()-1);
if(nameComparator.compare(tmp,dsEmployee)<=0)
{
employeeNameWiseOrderedList.add(dsEmployee);
}
else
{
int lb1,ub1,mid1;
lb1=0;
ub1=employeeNameWiseOrderedList.size()-1;
int x1;
while(lb1<=ub1)
{
mid1=(lb1+ub1)/2;
x1=nameComparator.compare(employeeNameWiseOrderedList.get(mid1),dsEmployee);
if(x1==0)
{
employeeNameWiseOrderedList.add(mid1,dsEmployee);
break;
}
else
{
if(x1<0)
{
if(nameComparator.compare(employeeNameWiseOrderedList.get(mid1+1),dsEmployee)>0)
{
employeeNameWiseOrderedList.add(mid1+1,dsEmployee);
break;
}
lb1=mid1+1;
}
else
{
if(nameComparator.compare(employeeNameWiseOrderedList.get(mid1-1),dsEmployee)<0)
{
employeeNameWiseOrderedList.add(mid1,dsEmployee);
break;
}
ub1=mid1-1;
}
}
}
}
}
}
}
public void delete(String employeeId) throws BLException
{
BLException blException;
blException=new BLException();
if(employeeId==null)blException.addException("employeeId","Required EmployeeId"); 
if(employeeIdWiseEmployee.containsKey(employeeId)==false)blException.addException("employeeId","Invalid employeeId.");
if(blException.hasException("employeeId"))throw blException;
try
{
new EmployeeDAO().delete(employeeId);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
EmployeeInterface employeeToBeRemoved=employeeIdWiseEmployee.get(employeeId);
int designationCode=employeeToBeRemoved.getDesignation().getCode();
Date dateOfBirth=employeeToBeRemoved.getDateOfBirth();
String aadharCardNumber=employeeToBeRemoved.getAadharCardNumber();
String panNumber=employeeToBeRemoved.getPANNumber(); 
List<EmployeeInterface> employeeList;
employeeList=designationWiseEmployees.get(designationCode);
employeeList.remove(employeeToBeRemoved);
employeeIdWiseEmployee.remove(employeeId);
aadharNumberWiseEmployee.remove(aadharCardNumber);
panNumberWiseEmployee.remove(panNumber);
List<EmployeeInterface> empList;
empList=dateOfBirthWiseEmployees.get(dateOfBirth);
empList.remove(employeeToBeRemoved);
employeeIdWiseOrderedList.remove(employeeToBeRemoved);
employeeNameWiseOrderedList.remove(employeeToBeRemoved);
}
public int getCount() throws BLException
{
return employeeIdWiseOrderedList.size();
}
public List<EmployeeInterface> getAll(EmployeeInterface.ATTRIBUTES ...orderBy) throws BLException
{
for(EmployeeInterface e:employeeNameWiseOrderedList)System.out.println(e.getName());
for(EmployeeInterface e:employeeIdWiseOrderedList)System.out.println(e.getEmployeeId());
List<EmployeeInterface> list=null;
if(orderBy.length==0||orderBy[0]==EmployeeInterface.EMPLOYEEID)
{
list=employeeIdWiseOrderedList;
}
else
{
list=employeeNameWiseOrderedList;
}
EmployeeInterface employee;
List<EmployeeInterface> employees=new LinkedList<>();
for(EmployeeInterface dsEmployee:list)
{
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
if(dsEmployee.getGender().equalsIgnoreCase("Male"))employee.setGender(EmployeeInterface.MALE);
else employee.setGender(EmployeeInterface.FEMALE);
employee.isIndian(dsEmployee.isIndian());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setPANNumber(dsEmployee.getPANNumber());
employees.add(employee);
}
return employees;
}
public List<EmployeeInterface>getByDesignation(int designationCode) throws BLException
{
BLException blException=new BLException();
if(designationCode<=0) blException.addException("code"," Invalid code : "+designationCode);
if(designationWiseEmployees.containsKey(designationCode)==false)blException.addException("code"," Code does not exists "+designationCode);
if(blException.hasException("code")==true) throw blException;
EmployeeInterface employee;
List<EmployeeInterface> employees=new LinkedList<>();
List<EmployeeInterface> employeesListByDesignation=new LinkedList<>();
employeesListByDesignation=designationWiseEmployees.get(designationCode);
for(EmployeeInterface empList : employeesListByDesignation)
{
employee=new Employee();
POJOCopier.copy(employee,empList);
employee.setEmployeeId(empList.getEmployeeId());
employee.setName(empList.getName());
employee.setDesignation(empList.getDesignation());
employee.setBasicSalary(empList.getBasicSalary());
employee.setDateOfBirth(empList.getDateOfBirth());
if(empList.getGender().equalsIgnoreCase("Male"))employee.setGender(EmployeeInterface.MALE);
else employee.setGender(EmployeeInterface.FEMALE);
employee.isIndian(empList.isIndian());
employee.setAadharCardNumber(empList.getAadharCardNumber());
employee.setPANNumber(empList.getPANNumber());
employees.add(employee);
}
return employees;
}
public List<EmployeeInterface>getByDateOfBirth(java.util.Date dateOfBirth) throws BLException
{
BLException blException=new BLException();
if(dateOfBirthWiseEmployees.containsKey(dateOfBirth)==false)blException.addException("dateOFBirth"," date does not exists : "+dateOfBirth);
if(blException.hasException("dateOfBirth")==true) throw blException;
EmployeeInterface employee;
List<EmployeeInterface> employees=new LinkedList<>();
List<EmployeeInterface> employeesListByDateOfBirth=new LinkedList<>();
employeesListByDateOfBirth=dateOfBirthWiseEmployees.get(dateOfBirth);
for(EmployeeInterface empList : employeesListByDateOfBirth)
{
employee=new Employee();
POJOCopier.copy(employee,empList);
employee.setEmployeeId(empList.getEmployeeId());
employee.setName(empList.getName());
employee.setDesignation(empList.getDesignation());
employee.setBasicSalary(empList.getBasicSalary());
employee.setDateOfBirth(empList.getDateOfBirth());
if(empList.getGender().equalsIgnoreCase("Male"))employee.setGender(EmployeeInterface.MALE);
else employee.setGender(EmployeeInterface.FEMALE);
employee.isIndian(empList.isIndian());
employee.setAadharCardNumber(empList.getAadharCardNumber());
employee.setPANNumber(empList.getPANNumber());
employees.add(employee);
}
return employees;
}
public EmployeeInterface getByEmployeeId(String employeeId) throws BLException
{
BLException blException;
blException=new BLException();
if(employeeId==null||employeeId.trim().length()==0)blException.addException("employeeId","EmployeeId Required");
if(blException.hasException("employeeId"))throw blException;
EmployeeInterface dsEmployee=employeeIdWiseEmployee.get(employeeId);
if(dsEmployee==null)blException.addException("employeeId","EmployeeId not exists");
if(blException.hasException("employeeId"))throw blException;
EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
if(dsEmployee.getGender().equalsIgnoreCase("Male"))employee.setGender(EmployeeInterface.MALE);
else employee.setGender(EmployeeInterface.FEMALE);
employee.isIndian(dsEmployee.isIndian());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setPANNumber(dsEmployee.getPANNumber());
return employee;
}
public EmployeeInterface getByPANNumber(String panNumber) throws BLException
{
BLException blException;
blException=new BLException();
if(panNumber==null||panNumber.trim().length()==0)blException.addException("panNumber","Pan Number Required");
if(blException.hasException("panNumber"))throw blException;
EmployeeInterface dsEmployee=panNumberWiseEmployee.get(panNumber);
if(dsEmployee==null)blException.addException("panNumber","panNumber not exists");
if(blException.hasException("panNumber"))throw blException;
EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
if(dsEmployee.getGender().equalsIgnoreCase("Male"))employee.setGender(EmployeeInterface.MALE);
else employee.setGender(EmployeeInterface.FEMALE);
employee.isIndian(dsEmployee.isIndian());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setPANNumber(dsEmployee.getPANNumber());
return employee;
}
public EmployeeInterface getByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException;
blException=new BLException();
if(aadharCardNumber==null||aadharCardNumber.trim().length()==0)blException.addException("aadharCardNumber","Aadhar Card Number Required");
if(blException.hasException("aadharCardNumber"))throw blException;
EmployeeInterface dsEmployee=aadharNumberWiseEmployee.get(aadharCardNumber);
if(dsEmployee==null)blException.addException("aadharCardNumber","aadharCardNumber not exists");
if(blException.hasException("aadharCardNumber"))throw blException;
EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
employee.setDesignation(dsEmployee.getDesignation());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setDateOfBirth(dsEmployee.getDateOfBirth());
if(dsEmployee.getGender().equalsIgnoreCase("Male"))employee.setGender(EmployeeInterface.MALE);
else employee.setGender(EmployeeInterface.FEMALE);
employee.isIndian(dsEmployee.isIndian());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employee.setPANNumber(dsEmployee.getPANNumber());
return employee;
}
public boolean employeeIdExists(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null||employeeId.trim().length()==0)blException.addException("employeeId","EmployeeId Required");
if(blException.hasException("employeeId"))throw blException;
return employeeIdWiseEmployee.containsKey(employeeId);
}
public boolean panNumberExists(String panNumber) throws BLException
{
BLException blException=new BLException();
if(panNumber==null||panNumber.trim().length()==0)blException.addException("panNumber","PanNumber Required");
if(blException.hasException("panNumber"))throw blException;
return panNumberWiseEmployee.containsKey(panNumber);
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
if(aadharCardNumber==null||aadharCardNumber.trim().length()==0)blException.addException("aadharCardNumber","Aadhar Card Number Required");
if(blException.hasException("aadharCardNumber"))throw blException;
return aadharNumberWiseEmployee.containsKey(aadharCardNumber);
}
}