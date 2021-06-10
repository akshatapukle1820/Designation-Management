import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
class DesignationgetCountpsp
{
public static void main(String gg[])
{
try
{
DesignationManagerInterface designationManger;
designationManager=DesignationManager.getInstance();
int cc=designationManager.getCount();
System.out.println("Number of designation added  is : "+cc);
}catch(BLException blException)
{
System.out.println(blException.getGenericException());
}
}
}
