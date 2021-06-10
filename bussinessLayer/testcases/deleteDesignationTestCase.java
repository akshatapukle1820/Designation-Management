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
import java.util.*;
class deleteDesignationTestCase
{
public static void main(String gg[])
{
try
{
int vCode=Keyboard.getInt("Enter code : ");
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
designationManager.delete(vCode);
System.out.println("designation deleted");
}catch(BLException b)
{
List<String> list=b.getExceptions();
for(int i=0;i<list.size();i++)
{
String g=list.get(i);
System.out.println(g);
}
}
}
}

