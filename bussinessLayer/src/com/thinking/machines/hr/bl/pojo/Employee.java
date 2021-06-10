
package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.*;
import java.math.*;
import java.io.*;
import java.util.*;
public class Employee implements EmployeeInterface
{
private String employeeId;
private String name;
private DesignationInterface designation;
private java.util.Date dateOfBirth;
private BigDecimal basicSalary;
private String gender;
private boolean isIndian;
private String panNumber;
private String aadharCardNumber;
public void setEmployeeId(String employeeId)
{
this.employeeId=employeeId;
}
public String getEmployeeId()
{
return this.employeeId;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
}

public DesignationInterface getDesignation()
{
return this.designation;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
return dateOfBirth;
}
public void setBasicSalary(BigDecimal basicSalary)
{
this.basicSalary=basicSalary;
} 
public BigDecimal getBasicSalary()
{
return this.basicSalary;
}
public void setGender(GENDER gender)
{
if(gender==GENDER.MALE)
{
this.gender="male";
}
if(gender==GENDER.FEMALE)
{
this.gender="female";
}
}
public String getGender()
{
return gender;
}
public void isIndian(boolean isIndian)
{
this.isIndian=isIndian;
}
public boolean isIndian()
{
return this.isIndian;
}
public void setPANNumber(String panNumber)
{
this.panNumber=panNumber;
}
public String getPANNumber()
{
return this.panNumber;
}
public void setAadharCardNumber(String aadharCardNumber)
{
 this.aadharCardNumber=aadharCardNumber;
}
public String getAadharCardNumber()
{
return this.aadharCardNumber;
}
public int hashCode()
{
return this.employeeId.toUpperCase().hashCode();
}
public boolean eqauls(Object object)
{
if(!(object instanceof EmployeeInterface)) return false;
EmployeeInterface employee=(EmployeeInterface)object;
return this.employeeId.equalsIgnoreCase(employee.getEmployeeId());
}
public int compareTo(EmployeeInterface employee)
{
return this.employeeId.toUpperCase().compareTo(employee.getEmployeeId());
}
}
