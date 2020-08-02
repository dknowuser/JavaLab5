package edu.java.lab2;
/** 
 * @author ������*/

//����������� ����������� ���������
import java.awt.BorderLayout;
import java.awt.FileDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.lang.Math;
import java.io.*;

public class InterForm {
	//������ ����������
	/** 
	 * ����� ����������, ������������ 
	 * ��� ���������� ����� �� �����
	 * ��� ���������� ������
	 * */
	private class NotEnoughDiskSpace extends Exception
	{
		public NotEnoughDiskSpace()
		{
			super("������������ ����� �� ����� ��� �������� ������.");
		};
	};
	
	/**
	 * ����� ����������, ������������,
	 * ����� ��� �������� ���������� ������
	 * �� ������ ����� ��������������� ������.  
	 * */
	private class NotFound extends Exception
	{
		public NotFound()
		{
			super("�� ������� ������ �� �������� ����������.");
		};
	};	
	
	//���������� ����������� �����������
	private JFrame InterForm;
	private DefaultTableModel Model;
	private JButton Open;
	private JButton Save;
	private JButton New;
	private JButton Edit;
	private JButton Delete;
	private JToolBar ToolBar;
	private JScrollPane Scroll;
	private JTable Groups;
	private JComboBox Year;
	private JTextField GroupName;
	private JButton Filter;
	
	private void CheckDiskSpace() throws NotEnoughDiskSpace
	{
		if(Math.random() < 0.5)
			throw new NotEnoughDiskSpace();
	};
	
	private void FindByParams() throws NotFound
	{
		if(Math.random() >= 0.5)
			throw new NotFound();
	};
	
	//����� �������� ����
	public void Show()
	{
		InterForm = new JFrame("�������� ����������� �����");
		InterForm.setSize(500, 300);
		InterForm.setLocation(100, 100);
		InterForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�������� ������ � ������������ ������
		Open = new JButton(new ImageIcon("./img/open.png"));
		Save = new JButton(new ImageIcon("./img/save.png"));
		New = new JButton(new ImageIcon("./img/new.png"));
		Edit = new JButton(new ImageIcon("./img/edit.png"));
		Delete = new JButton(new ImageIcon("./img/delete.png"));
		
		//��������� ��������� ��� ������
		Open.setToolTipText("�������");
		Save.setToolTipText("���������");
		New.setToolTipText("����� ������");
		Edit.setToolTipText("������������� ������");
		Delete.setToolTipText("������� ������");
		
		//���������� ������ �� ������ ������������
		ToolBar = new JToolBar("������ ������������");
		ToolBar.add(Open);
		ToolBar.add(Save);
		ToolBar.add(New);
		ToolBar.add(Edit);
		ToolBar.add(Delete);
		
		//���������� ������ ������������
		InterForm.setLayout(new BorderLayout());
		InterForm.add(ToolBar, BorderLayout.NORTH);
		
		//�������� ������� � �������
		String[] Columns = {"������", "��� ���������", "��������� � ���-������"};
		String[][] Data = {{"Paul Van Dyk", "1994", "105"}, {"ATB", "1998", "54"}};
		Model = new DefaultTableModel(Data, Columns);
		Groups = new JTable(Model);
		Scroll = new JScrollPane(Groups);		
		
		//���������� ������� � �������
		InterForm.add(Scroll, BorderLayout.CENTER);
		
		//���������� ����������� ������
		Year = new JComboBox(new String[] {"���", "1994", "1998"});		
		GroupName = new JTextField("�������� ������");
		Filter = new JButton("�����");
		
		//���������� ����������� �� ������
		JPanel FilterPanel = new JPanel();
		FilterPanel.add(GroupName);
		FilterPanel.add(Year);
		FilterPanel.add(Filter);		
		
		//���������� ������ ������ ����� ����
		InterForm.add(FilterPanel, BorderLayout.SOUTH);
		
		//������������ �������� �����
		InterForm.setVisible(true);
		
		//���������� ����������
		New.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**����� ��������� � ����� �� ������� ������ "����� ������". */
				String group = "Doom";
				String year = "1993";
				String chart = "12";
				Model.addRow(new String[] {group, year, chart});
			}});
		
		Filter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**����� ��������� � ����� �� ������� ������ "�����". */
				try
				{
					FindByParams();
				}
				catch(NotFound ex)
				{
					JOptionPane.showMessageDialog(InterForm, "�� ������� ������ � ��������� ����������� ������.");
				};
				JOptionPane.showMessageDialog(InterForm, "�������� ������� �� ������ \"�����\".");
			}});
		
		Year.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**����� ��������� � ����� �� ����� ������ � ������ "���". */
				JOptionPane.showMessageDialog(InterForm, "�������� ������ ������ ��������������� ������ \"���\".");
			}});
		
		Open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				//�������� ����������� ���� ������ ����� ��� �������� ������
				FileDialog open = new FileDialog(InterForm, "�������� ������", FileDialog.LOAD);
				open.setFile("*.txt");
				open.setVisible(true);
				
				//��������, ������� �� ����
				String FileName = open.getDirectory() + open.getFile();
				if(FileName == null)
					return;
				
				//������� �������
				int rows = Model.getRowCount();
				for(int i = 0; i < rows; i++)
					Model.removeRow(0);
				
				//��������� ������
				try
				{
					BufferedReader reader = new BufferedReader(new FileReader(FileName));
					String group = null;
					do
					{
						group = reader.readLine();
						if(group != null)
						{
							String year = reader.readLine();
							String chart = reader.readLine();
							Model.addRow(new String[] {group, year, chart});
						};
					}while(group != null);
					reader.close();
				}
				catch(FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}			
			}});
		
		Save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//�������� ����������� ���� ������ ����� ��� ���������� ������
				FileDialog open = new FileDialog(InterForm, "���������� ������", FileDialog.SAVE);
				open.setFile("*.txt");
				open.setVisible(true);
				
				//��������, ������� �� ����
				String FileName = open.getDirectory() + open.getFile();
				if(FileName == null)
					return;
				
				//��������� ������
				try
				{
					BufferedWriter writer = new BufferedWriter(new FileWriter(FileName));
					for(int i = 0; i < Model.getRowCount(); i++)
						for(int j = 0; j < Model.getColumnCount(); j++)
						{
							writer.write((String)Model.getValueAt(i, j));
							writer.write("\r\n");
						};
					writer.close();			
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				};
			}});
	};	
	
	public static void main(String[] args)
	{
		//�������� � ����������� �������� �����
		new InterForm().Show();
	};
};
