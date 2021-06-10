import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.io.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
class addDesignationTestCase
{
public static void main(String gg[])
{
try
{
String title=Keyboard.getString("Enter designation : ");
DesignationInterface designation;
designation=new Designation();
designation.setTitle(title);
DesignationManagerInterface d=DesignationManager.getInstance();
d.add(designation);
System.out.println(title+" added with code as :"+designation.getCode());
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

