import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.tmcommon.*;
import java.math.*;
import java.text.*;
class DesignationGetByTitleTestCase
{
public static void main(String gg[])
{
try
{
String vTitle=Keyboard.getString("Enter title : ");
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO=designationDAO.getByTitle(vTitle);
int code=designationDTO.getCode();
String title=designationDTO.getTitle();
System.out.println("Designation title : "+title+" is with code as : "+code);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}