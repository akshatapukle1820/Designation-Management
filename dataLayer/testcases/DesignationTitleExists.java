import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
class DesignationTitleExists
{
public static void main(String gg[])
{
try
{
String title=Keyboard.getString("Enter title : ");
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
boolean exists=designationDAO.exists(title);
if(exists) System.out.println("Designation title  : "+title+" exists. ");
else
{
System.out.println("Designation title  : "+title+" does not exists. ");
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}