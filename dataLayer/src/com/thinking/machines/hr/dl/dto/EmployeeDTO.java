package com.thinking.machines.hr.dl.dto;
import java.math.*;
import com.thinking.machines.hr.dl.interfaces.*;
public class EmployeeDTO implements EmployeeDTOInterface
{
private String employeeId;
private String name;
private int designationCode;
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
public void setDesignationCode(int designationCode)
{
this.designationCode=designationCode;
}
public int getDesignationCode()
{
return this.designationCode;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
return this.dateOfBirth;
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
this.gender="M";
}
if(gender==GENDER.FEMALE)
{
this.gender="F";
}
}
public String getGender()
{
return this.gender;
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
return this.panNumber=panNumber;
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
public boolean equals(Object object)
{
if(!(object instanceof EmployeeDTOInterface)) return false;
EmployeeDTOInterface employeeDTO=(EmployeeDTOInterface)object;
return this.employeeId.equalsIgnoreCase(employeeDTO.getEmployeeId());
}
public int compareTo(EmployeeDTOInterface employeeDTO)
{
return this.employeeId.toUpperCase().compareTo(employeeDTO.getEmployeeId().toUpperCase());
}
}