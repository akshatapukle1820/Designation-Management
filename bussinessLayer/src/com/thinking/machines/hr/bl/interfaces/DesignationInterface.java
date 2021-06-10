package com.thinking.machines.hr.bl.interfaces;
public interface DesignationInterface extends java.io.Serializable,Comparable<DesignationInterface>
{
public enum ATTRIBUTES{CODE,TITLE};
final static public ATTRIBUTES CODE=ATTRIBUTES.CODE;
final static public ATTRIBUTES TITLE=ATTRIBUTES.TITLE;
public void setCode(int code);
public int getCode();
public void setTitle(String title);
public String getTitle();
}