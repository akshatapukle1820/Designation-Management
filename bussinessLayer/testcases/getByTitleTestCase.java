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
class getByTitleTestCasepsp
{
public static void main(String gg[])
{
try
{
String title=Keyboard.getString("Enter designation title : ");
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
DesignationInterface designation;
designation=new Designation();
designation=designationManager.getByTitle(title);
int code=designation.getCode();
String vTitle=designation.getTitle();
System.out.println(vTitle+" is with designation code as : "+code);
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
