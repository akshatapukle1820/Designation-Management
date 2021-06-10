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
class titleExistspsp
{
public static void main(String gg[])
{
try
{
String vTitle=Keyboard.getString("Enter title : ");
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
boolean exists;
exists=designationManager.titleExists(vTitle);
if(exists==true)
{
System.out.println(" Designation title : "+vTitle+" exists.");
}
else
{
System.out.println(" Designation title : "+vTitle+" does not exists.");
}
}catch(BLException blException)
{
System.out.println(blException.getGenericException());
}
}
}

