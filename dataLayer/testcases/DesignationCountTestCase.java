import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
class DesignationCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
int cc=designationDAO.getCount();
System.out.println("Number of designation added :"+cc);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}