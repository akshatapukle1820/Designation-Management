package com.thinking.machines.hr.dl.interfaces;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public interface EmployeeDAOInterface
{
public final String EMPLOYEE_DATA_FILE="employee.data";
public void add(EmployeeDTOInterface employeeDTO)throws DAOException;
public void update(EmployeeDTOInterface employeeDTO)throws DAOException;
public void delete(String employeeId) throws DAOException;
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException;
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException;
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException;
public List<EmployeeDTOInterface>getAll() throws DAOException;
public List<EmployeeDTOInterface>getByDesignation(int designationCode) throws DAOException;
public List<EmployeeDTOInterface>getByDateOfBirth(java.util.Date dateOfBirth) throws DAOException;
public int getCount() throws DAOException;
public boolean employeeIdExists(String employeeId) throws DAOException;
public boolean panNumberExists(String panNumber) throws DAOException;
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException;
}
