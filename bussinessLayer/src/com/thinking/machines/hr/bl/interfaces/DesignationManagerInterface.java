package com.thinking.machines.hr.bl.interfaces;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface DesignationManagerInterface 
{
public void add(DesignationInterface designation)throws BLException;
public void update(DesignationInterface designation)throws BLException;
public void delete(int code)throws BLException;
public int getCount() throws BLException;
public List<DesignationInterface> getDesignations(DesignationInterface.ATTRIBUTES ...orderBy)throws BLException;
public DesignationInterface getByCode(int code)throws BLException;
public DesignationInterface getByTitle(String title)throws BLException;
public boolean codeExists(int code)throws BLException;
public boolean titleExists(String title)throws BLException;
public boolean isAttachedToAnEmployee(int code) throws BLException;
public int getEmployeesCountWithDesignation(int code) throws BLException;
}