import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
class DesignationCodeExists
{
public static void main(String gg[])
{
try
{
int code=Keyboard.getInt("Enter code : ");
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
boolean exists=designationDAO.exists(code);
if(exists) System.out.println("Designation code  : "+code+" exists. ");
else
{
System.out.println("Designation code  : "+code+" does not exists. ");
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}