import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
class isAttachedToAnEmployee
{
public static void main(String gg[])
{
try
{
int code=Keyboard.getInt("Enter a code :");
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
boolean exists;
exists=designationManager.isAttachedToAnEmployee(code);
System.out.println(" Is Designation code Attached to an Employee : "+exists);
}catch(BLException b)
{
java.util.List<String> list=b.getExceptions();
for(int i=0;i<list.size();i++)
{
String g=list.get(i);
System.out.println(g);
}
}
}
}