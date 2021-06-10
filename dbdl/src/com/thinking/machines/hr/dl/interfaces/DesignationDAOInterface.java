package com.thinking.machines.hr.dl.interfaces;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public interface DesignationDAOInterface 
{
public final String DESIGNATION_DATA_FILE="designation.data";
public void add(DesignationDTOInterface designationDTO)throws DAOException;
public void update(DesignationDTOInterface designationDTO)throws DAOException;
public void delete(int code)throws DAOException;
public DesignationDTOInterface getByCode(int code)throws DAOException;
public DesignationDTOInterface getByTitle(String title)throws DAOException;
public List<DesignationDTOInterface> getAll()throws DAOException;
public int getCount()throws DAOException;
public boolean exists(int code)throws DAOException;
public boolean exists(String title)throws DAOException;
}
