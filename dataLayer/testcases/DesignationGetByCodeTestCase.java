import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
class DesignationGetByCodeTestCase
{
public static void main(String gg[])
{
try
{
int vCode=Keyboard.getInt("Enter code : ");
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO=designationDAO.getByCode(vCode);
int code=designationDTO.getCode();
String title=designationDTO.getTitle();
System.out.println("Designation code : "+code+" is with title as : "+title);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}