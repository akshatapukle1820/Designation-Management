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
class getByCodeTestCasepsp
{
public static void main(String gg[])
{
try
{
int code=Keyboard.getInt("Enter designation code : ");
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
DesignationInterface designation;
designation=new Designation();
designation=designationManager.getByCode(code);
String title=designation.getTitle();
int vCode=designation.getCode();
System.out.println("Designation code : "+vCode+" is with title as : "+title);
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

