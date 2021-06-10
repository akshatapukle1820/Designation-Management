import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import java.sql.*;
class titleExists
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean titleExists=designationDAO.exists(title);
System.out.println("Designation title "+title+" exists : "+titleExists);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}