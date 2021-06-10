package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.pl.exception.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.*;
public class DesignationUI extends JFrame implements DocumentListener
{
private JLabel titleLabel;
private JLabel searchCaptionLabel;
private JTextField searchTextField;
private JButton searchButton;
private JLabel label;
private JScrollPane jsp;
private JPanel buttonsPanel;
private JButton AButton;
private JButton EButton;
private JButton CButton;
private JButton DButton;
private JButton PButton;
private JLabel designationCaptionLabel;
private JLabel designationLabel;
private JTextField designationTextField;
private JPanel designationPanel;
private JTable table;
private Container container;
int flag=0;
DesignationModel designationModel;
public DesignationUI()
{
designationModel=new DesignationModel();
table=new JTable(designationModel);
titleLabel=new JLabel("Designation Master");
searchCaptionLabel=new JLabel("Search");
searchTextField=new JTextField();
searchButton=new JButton(new ImageIcon(getClass().getResource("/images/searchButton.png")));
label=new JLabel("Not Found");
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
AButton=new JButton(new ImageIcon(getClass().getResource("/images/add.png")));
EButton=new JButton(new ImageIcon(getClass().getResource("/images/edit.png")));
CButton=new JButton(new ImageIcon(getClass().getResource("/images/cancel.png")));
DButton=new JButton(new ImageIcon(getClass().getResource("/images/delete.png")));
PButton=new JButton(new ImageIcon(getClass().getResource("/images/pdf.png")));
buttonsPanel=new JPanel();
buttonsPanel.setLayout(null);
buttonsPanel.add(AButton);
buttonsPanel.add(EButton);
buttonsPanel.add(CButton);
buttonsPanel.add(DButton);
buttonsPanel.add(PButton);
designationCaptionLabel=new JLabel("Designation");
designationLabel=new JLabel("");
designationTextField=new JTextField();
designationPanel=new JPanel();
designationPanel.setLayout(null);
designationPanel.add(designationCaptionLabel);
designationPanel.add(designationLabel);
designationPanel.add(designationTextField);
designationPanel.add(buttonsPanel);
designationPanel.add(AButton);
designationPanel.add(EButton);
designationPanel.add(CButton);
designationPanel.add(DButton);
designationPanel.add(PButton);
setTitle("HR Automation System");
Font titleFont=new Font("Verdana",Font.BOLD,24);
titleLabel.setFont(titleFont);
Font dataFont=new Font("Times New Roman",Font.PLAIN,22);
searchCaptionLabel.setFont(dataFont);
searchTextField.setFont(dataFont);
Font labelFont=new Font("Times New Roman",Font.BOLD,12);
label.setFont(labelFont);
label.setForeground(Color.red);
label.setVisible(false);
Font designationFont=new Font("Times New Roman",Font.BOLD,22);
designationCaptionLabel.setFont(designationFont);
Font designationsFont=new Font("Times New Roman",Font.PLAIN,22);
designationLabel.setFont(designationsFont);
designationTextField.setFont(designationsFont);
designationTextField.setVisible(false);
buttonsPanel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.blue));
designationPanel.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.black));
DefaultTableCellRenderer cellRenderer=new DefaultTableCellRenderer();
cellRenderer.setHorizontalAlignment(JLabel.CENTER);
table.setRowHeight(40);
table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
table.getColumnModel().getColumn(0).setPreferredWidth(30);
table.getColumnModel().getColumn(1).setPreferredWidth(300);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getTableHeader().setResizingAllowed(false);
table.getTableHeader().setReorderingAllowed(false);
table.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,20));
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//ListSelectionModel selectedRow=table.getSelectionModel();
//selectedRow.addListSelectionListener(this);
searchTextField.getDocument().addDocumentListener(this);
container=getContentPane();
container.setLayout(null);
table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
public void valueChanged(ListSelectionEvent le)
{
try
{
designationLabel.setText(designationModel.getDesignationAt(table.getSelectedRow()).getTitle());
}
catch(ModelException me)
{
System.out.println(me.getMessage());
}
}
});
searchButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent av)
{
table.setRowSelectionInterval(0,0);
label.setVisible(false);
searchTextField.setText("");
}
});
AButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent av)
{
table.setRowSelectionInterval(0 , 0);
searchTextField.setEnabled(false);
jsp.setEnabled(false);
table.setEnabled(false);
searchButton.setEnabled(false);
EButton.setEnabled(false);
PButton.setEnabled(false);
DButton.setEnabled(false);
if(flag==0)
{
AButton.setIcon(new ImageIcon(getClass().getResource("/images/save.png")));
CButton.setEnabled(true);
designationTextField.setVisible(true);
designationLabel.setVisible(false);
flag=1;
}
else
{
if(designationTextField.getText()==null || designationTextField.getText().length()==0)
{
JOptionPane.showMessageDialog(null,"Designation Required!","Error",JOptionPane.ERROR_MESSAGE);
}
else
{
flag=0;
DesignationInterface designation=new Designation();
try
{
designation.setTitle(designationTextField.getText());
designationModel.addDesignation(designation);
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(null, modelException.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
}
DButton.setEnabled(true);
EButton.setEnabled(true);
PButton.setEnabled(true);
AButton.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
AButton.setEnabled(true);
searchButton.setEnabled(true);
searchTextField.setEnabled(true);
table.setEnabled(true);
try
{
int index=designationModel.IndexOf(designationTextField.getText());
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,true)));
}catch(IllegalArgumentException iae)
{
System.out.println(iae);
}
designationTextField.setText("");
designationTextField.setVisible(false);
flag=0;
designationLabel.setVisible(true);
}
}
}
});
EButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent av)
{
DesignationInterface uInterface=new Designation();
if(table.getSelectionModel().isSelectionEmpty())
{
JOptionPane.showMessageDialog(null,"Select a Designation to Edit!","Error",JOptionPane.ERROR_MESSAGE);
}
else
{
try
{
uInterface=designationModel.getDesignationAt(table.getSelectedRow());
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(null,modelException.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
AButton.setEnabled(false);
DButton.setEnabled(false);
PButton.setEnabled(false);
searchButton.setEnabled(false);
searchTextField.setEnabled(false);
table.setEnabled(false);
label.setEnabled(false);
if(flag==0)
{
try
{
flag=1;
EButton.setIcon(new ImageIcon(getClass().getResource("/images/update.png")));
CButton.setEnabled(true);
designationTextField.setText(designationModel.getDesignationAt(table.getSelectedRow()).getTitle());
designationTextField.setVisible(true);
designationLabel.setVisible(false);
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(null,"Invalid Row index","Error",JOptionPane.ERROR_MESSAGE);
}
}
else
{
int result=JOptionPane.showConfirmDialog(null," Do you want to edit  "+uInterface.getTitle(),"Alert",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE );
if(result==JOptionPane.YES_OPTION)
{
if(designationTextField.getText()==null || designationTextField.getText().length()==0)
{
JOptionPane.showMessageDialog(null,"Designation Required!","Error",JOptionPane.ERROR_MESSAGE);
}
else
{
flag=0;
try
{
Designation dInterface=new Designation();
dInterface.setTitle(designationTextField.getText());
dInterface.setCode(uInterface.getCode());
designationModel.updateDesignation(dInterface);
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(null,modelException.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}
AButton.setEnabled(true);
DButton.setEnabled(true);
CButton.setEnabled(false);
PButton.setEnabled(true);
searchButton.setEnabled(true);
searchTextField.setEnabled(true);
table.setEnabled(true);
try
{
int index=designationModel.IndexOf(designationTextField.getText());
table.setRowSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,true)));
}catch(IllegalArgumentException iae)
{
System.out.println(iae);
}
label.setVisible(false);
designationTextField.setText("");
designationTextField.setVisible(false);
flag=0;
designationLabel.setVisible(true);
EButton.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
EButton.setEnabled(true);
}
}
if(result==JOptionPane.NO_OPTION)
{
jsp.setEnabled(true);
table.setEnabled(true);
AButton.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
AButton.setEnabled(true);
EButton.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
EButton.setEnabled(true);
CButton.setEnabled(false);
DButton.setEnabled(true);
PButton.setEnabled(true);
designationTextField.setText("");
designationTextField.setVisible(false);
searchTextField.setEnabled(true);
searchButton.setEnabled(true);
designationLabel.setVisible(true);
label.setEnabled(false);
table.setRowSelectionInterval(0 ,0);
}
}
}
}	
});
CButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent av)
{
flag=0;
searchTextField.setEnabled(true);
searchButton.setEnabled(true);
jsp.setEnabled(true);
table.setEnabled(true);
designationLabel.setVisible(true);
designationLabel.setEnabled(true);
designationTextField.setVisible(false);
CButton.setEnabled(false);
AButton.setEnabled(true);
AButton.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
EButton.setEnabled(true);
EButton.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
DButton.setEnabled(true);
PButton.setEnabled(true);
table.setRowSelectionInterval(0,0);
}
});
DButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent av)
{
String titleSelectedToDelete=null;
if(table.getSelectionModel().isSelectionEmpty())
{
JOptionPane.showMessageDialog(null,"Select a Designation to delete!","Error",JOptionPane.ERROR_MESSAGE);
}
else
{
table.setEnabled(false);
searchTextField.setEnabled(false);
searchButton.setEnabled(false);
AButton.setEnabled(false);
EButton.setEnabled(false);
PButton.setEnabled(false);
CButton.setEnabled(true);
try
{
titleSelectedToDelete=designationModel.getDesignationAt(table.getSelectedRow()).getTitle();
}catch(ModelException modelException)
{
System.out.println(modelException);
}
int result=JOptionPane.showConfirmDialog(null," Do you want to delete "+titleSelectedToDelete,"Alert",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE );
if(result==JOptionPane.YES_OPTION)
{
try
{
designationModel.deleteDesignation(designationModel.getDesignationAt(table.getSelectedRow()).getCode());
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(null, modelException.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
}
JOptionPane.showMessageDialog(null, "Designation deleted" ,"Confirm Message",JOptionPane.INFORMATION_MESSAGE);
table.setRowSelectionInterval(0 ,0);
table.setEnabled(true);
AButton.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
AButton.setEnabled(true);
EButton.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
EButton.setEnabled(true);
DButton.setEnabled(true);
PButton.setEnabled(true);
designationTextField.setText("");
designationTextField.setVisible(false);
searchTextField.setEnabled(true);
searchButton.setEnabled(true);
designationLabel.setVisible(true);
label.setEnabled(false);
}
if(result==JOptionPane.NO_OPTION)
{
jsp.setEnabled(true);
table.setEnabled(true);
AButton.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
AButton.setEnabled(true);
EButton.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
EButton.setEnabled(true);
CButton.setEnabled(false);
DButton.setEnabled(true);
PButton.setEnabled(true);
designationTextField.setText("");
designationTextField.setVisible(false);
searchTextField.setEnabled(true);
searchButton.setEnabled(true);
designationLabel.setVisible(true);
label.setEnabled(false);
table.setRowSelectionInterval(0 ,0);
}
}
}
});

/*PButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
JFileChooser jfc=new JFileChooser();
int returnValue=jfc.showSaveDialog(null);
if(returnValue==JFileChooser.APPROVE_OPTION)
{
File file=jfc.getSelectedFile();
String fileName=file.getName();
String pathName=file.getAbsolutePath();
if(!(pathName.endsWith(".pdf")))
{
pathName=pathName+".pdf";
}
String path=pathName.substring(0,pathName.lastIndexOf("\\"));
File file1=new File(path);
if(file1.isDirectory()==false)
{
JOptionPane.showMessageDialog(null,"Invalid Path","Error",JOptionPane.ERROR_MESSAGE);
}
else
{
if(file.exists())
{
int yN=JOptionPane.showConfirmDialog(null,"The File Already Exists Do You Want To Override ?","Warning",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
if(yN==JOptionPane.YES_OPTION)
{
if(fileName.endsWith(".pdf")==false)
{
fileName=fileName+".pdf";
}
designationModel.pdfMaker(fileName);
JOptionPane.showMessageDialog(null,"PDF created in"+path,"Information",JOptionPane.INFORMATION_MESSAGE);
}
}
else
{
jsp.setEnabled(true);
table.setEnabled(true);
AButton.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
AButton.setEnabled(true);
EButton.setIcon(new ImageIcon(getClass().getResource("/images/edit.png")));
EButton.setEnabled(true);
CButton.setEnabled(false);
DButton.setEnabled(true);
PButton.setEnabled(true);
designationTextField.setText("");
designationTextField.setVisible(false);
searchTextField.setEnabled(true);
searchButton.setEnabled(true);
designationLabel.setVisible(true);
label.setEnabled(false);
table.setRowSelectionInterval(0 ,0);
}
}
}
}
});
*/

PButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent actionEvent)
{
JFileChooser fileChooser=new JFileChooser();
int status=fileChooser.showSaveDialog(null);
if(status==JFileChooser.APPROVE_OPTION)
{
File file=fileChooser.getSelectedFile();
if(!(new File(file.getParent()).isDirectory()))
{
JOptionPane.showMessageDialog(null,"Invalid path");
return;
}
if(file.exists())
{
int confirm=JOptionPane.showConfirmDialog(null,file.getName()+" already exists! Would you like to overwrite it?","File already exists",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
if(confirm!=JOptionPane.YES_OPTION)return;
}
String filename=file.getPath();
int i=filename.lastIndexOf(".");
if(i==(-1))
{
filename=filename+".pdf";
file = new File(file.getPath() +".pdf");
fileChooser.setSelectedFile(file);
}
else
{

}
FileNameExtensionFilter f1,f2;
fileChooser.setAcceptAllFileFilterUsed(false);
f1=new FileNameExtensionFilter("PDF files","pdf");
fileChooser.addChoosableFileFilter(f1);
designationModel.exportToPdf(file);
JOptionPane.showMessageDialog(null,"PDF created in  "+file.getPath(),"Information",JOptionPane.INFORMATION_MESSAGE);
}
}
});

/*public void show(int minimumMilliseconds) 
{
this.minimumMilliseconds = minimumMilliseconds;
window.setVisible(true);
startTime=System.currentTimeMillis();
}
public void hide() 
{
long elapsedTime = System.currentTimeMillis() - startTime;
try 
{
Thread.sleep(Math.max(minimumMilliseconds - elapsedTime, 0));
}catch (InterruptedException e) 
{
e.printStackTrace();
}
window.setVisible(false);
}

public void valueChanged(ListSelectionEvent lse)
{
ListSelectionModel m=(ListSelectionModel) lse.getSource();
int rowSelected=m.getMinSelectionIndex();
try 
{
designationDisplayLabel.setText(model.getDesignationAt(rowSelected).getTitle());
}catch(ModelException me)
{
System.out.println(me.getMessage());
a=1;
}
}
*/





//mine
int w=600;
int h=700;
int lm,tm;
lm=10;
tm=0;
titleLabel.setBounds(lm+30,tm+10,300,60);
searchCaptionLabel.setBounds(lm+30,tm+10+40+10+5,200,40);
searchTextField.setBounds(lm+30+100,tm+10+40+10+10,350,30);
searchButton.setBounds(lm+30+100+300+50,tm+10+40+10+10,50,30);
label.setBounds(lm+30+100+10+200+30+10+20+10,tm+10+30+10,150,20);
jsp.setBounds(lm+30,tm+10+40+10+30+10+10,400+100,200+60);
AButton.setBounds(lm+20+50,tm+10+40+10+30+10+300+30+50+40,60,50);
EButton.setBounds(lm+20+50+50+20+20,tm+10+40+10+30+10+300+30+50+40,60,50);
CButton.setBounds(lm+20+50+50+20+50+20+40,tm+10+40+10+30+10+300+30+50+40,60,50);
DButton.setBounds(lm+20+50+50+20+50+20+50+20+60,tm+10+40+10+30+10+300+30+50+40,60,50);
PButton.setBounds(lm+30+50+50+20+50+20+50+20+50+20+70,tm+10+40+10+30+10+300+30+50+40,60,50);
buttonsPanel.setBounds(lm+30+20,tm+10+40+10+30+10+300+80,460,140);
designationCaptionLabel.setBounds(lm+30+20,tm+10+40+10+30+10+200+30+10+70,150,40);
designationLabel.setBounds(lm+30+30+150,tm+10+40+10+30+10+200+30+10+70,200,40);
designationTextField.setBounds(lm+30+20+150,tm+10+40+10+30+10+200+30+10+70+5,280,30);
designationPanel.setBounds(lm+30,tm+10+40+10+30+10+200+40+50,400+100,250);

int dataCount=designationModel.getRowCount();
if(dataCount==0)
{
searchTextField.setEnabled(false);
searchButton.setEnabled(false);
table.setEnabled(false);
designationLabel.setEnabled(false);
CButton.setEnabled(false);
EButton.setEnabled(false);
DButton.setEnabled(false);
PButton.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
searchButton.setEnabled(true);
table.setEnabled(true);
designationLabel.setEnabled(true);
CButton.setEnabled(false);
EButton.setEnabled(true);
DButton.setEnabled(true);
PButton.setEnabled(true);
}

container.add(titleLabel);
container.add(searchCaptionLabel);
container.add(searchTextField);
container.add(searchButton);
container.add(label);
container.add(jsp);
container.add(AButton);
container.add(EButton);
container.add(CButton);
container.add(DButton);
container.add(PButton);
container.add(buttonsPanel);
container.add(designationCaptionLabel);
container.add(designationLabel);
container.add(designationTextField);
container.add(designationPanel);
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setDefaultCloseOperation(EXIT_ON_CLOSE);
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
}
public void changedUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent de)
{
searchDesignation();
}
public void searchDesignation()
{
DesignationInterface designationsInterface;
try
{
designationsInterface=designationModel.getDesignation(searchTextField.getText(),false,true);
int ind=designationModel.getIndexOf(designationsInterface);
table.setRowSelectionInterval(ind,ind);
table.scrollRectToVisible(new Rectangle(table.getCellRect(ind,0,true)));
}
catch(ModelException me)
{
table.clearSelection();
label.setVisible(true);
}
}
}
 