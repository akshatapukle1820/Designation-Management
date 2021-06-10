import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.tmcommon.*;
class DesignationDeleteTestCase
{
public static void main(String gg[])
{
try
{
int code=Keyboard.getInt("Enter Designation code to delete : ");
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.delete(code);
System.out.println("designation  code : "+code+" deleted ");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}