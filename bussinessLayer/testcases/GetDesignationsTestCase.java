import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
class getDesignationsTestCasepsp
{
public static void main(String gg[])
{
try
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getInstance();
List<DesignationInterface> designations;
designations=designationManager.getDesignations(DesignationInterface.CODE);
for(DesignationInterface designation :designations)
{
System.out.printf("Code %d, Title %s\n",designation.getCode(),designation.getTitle());
}
}catch(BLException blException)
{
System.out.println(blException.getGenericException());
}
}
}