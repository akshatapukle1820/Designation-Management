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
class codeExistspsp
{
public static void main(String gg[])
{
try
{
int vCode=Keyboard.getInt("Enter code : ");
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
boolean exists;
exists=designationManager.codeExists(vCode);
if(exists==true)
{
System.out.println(" Designation code : "+vCode+" exists.");
}
else
{
System.out.println(" Designation code : "+vCode+" does not exists.");
}
}catch(BLException blException)
{
System.out.println(blException.getGenericException());
}
}
}

