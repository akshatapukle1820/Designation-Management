 package com.thinking.machines.hr.bl.pojo;//dto ji jagah
import com.thinking.machines.hr.bl.interfaces.*;
public class Designation implements DesignationInterface
{
private int code;
private String title;
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(String title)
{
this.title=title;
}
public String getTitle()
{
return this.title;
}
public boolean equals(Object object)
{
if(!(object instanceof DesignationInterface))return false;
DesignationInterface designation;
designation=(DesignationInterface)object;
return this.code==designation.getCode();
}
public int compareTo(DesignationInterface designation)
{
return this.code-designation.getCode();
}
public int hashCode()
{
return this.code;
}
}