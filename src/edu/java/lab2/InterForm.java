package edu.java.lab2;
/** 
 * @author Марчук*/

//Подключение графических библиотек
import java.awt.BorderLayout;
import java.awt.FileDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.lang.Math;
import java.io.*;

public class InterForm {
	//Классы исключений
	/** 
	 * Класс исключения, возникающего 
	 * при недостатке места на диске
	 * для сохранения записи
	 * */
	private class NotEnoughDiskSpace extends Exception
	{
		public NotEnoughDiskSpace()
		{
			super("Недостаточно места на диске для создания записи.");
		};
	};
	
	/**
	 * Класс исключения, возникающего,
	 * когда при заданных параметрах поиска
	 * не удаётся найти соответствующие группы.  
	 * */
	private class NotFound extends Exception
	{
		public NotFound()
		{
			super("Не найдены группы по заданным параметрам.");
		};
	};	
	
	//Объявления графических компонентов
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
	
	//Метод создания окна
	public void Show()
	{
		InterForm = new JFrame("Менеджер музыкальных групп");
		InterForm.setSize(500, 300);
		InterForm.setLocation(100, 100);
		InterForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Создание кнопок и прикрепление иконок
		Open = new JButton(new ImageIcon("./img/open.png"));
		Save = new JButton(new ImageIcon("./img/save.png"));
		New = new JButton(new ImageIcon("./img/new.png"));
		Edit = new JButton(new ImageIcon("./img/edit.png"));
		Delete = new JButton(new ImageIcon("./img/delete.png"));
		
		//Настройка подсказок для кнопок
		Open.setToolTipText("Открыть");
		Save.setToolTipText("Сохранить");
		New.setToolTipText("Новая запись");
		Edit.setToolTipText("Редактировать запись");
		Delete.setToolTipText("Удалить запись");
		
		//Добавление кнопок на панель инструментов
		ToolBar = new JToolBar("Панель инструментов");
		ToolBar.add(Open);
		ToolBar.add(Save);
		ToolBar.add(New);
		ToolBar.add(Edit);
		ToolBar.add(Delete);
		
		//Размещение панели инструментов
		InterForm.setLayout(new BorderLayout());
		InterForm.add(ToolBar, BorderLayout.NORTH);
		
		//Создание таблицы с данными
		String[] Columns = {"Группа", "Год основания", "Положение в хит-параде"};
		String[][] Data = {{"Paul Van Dyk", "1994", "105"}, {"ATB", "1998", "54"}};
		Model = new DefaultTableModel(Data, Columns);
		Groups = new JTable(Model);
		Scroll = new JScrollPane(Groups);		
		
		//Размещение таблицы с данными
		InterForm.add(Scroll, BorderLayout.CENTER);
		
		//Подготовка компонентов поиска
		Year = new JComboBox(new String[] {"Год", "1994", "1998"});		
		GroupName = new JTextField("Название группы");
		Filter = new JButton("Поиск");
		
		//Добавление компонентов на панель
		JPanel FilterPanel = new JPanel();
		FilterPanel.add(GroupName);
		FilterPanel.add(Year);
		FilterPanel.add(Filter);		
		
		//Размещение панели поиска внизу окна
		InterForm.add(FilterPanel, BorderLayout.SOUTH);
		
		//Визуализация экранной формы
		InterForm.setVisible(true);
		
		//Добавление слушателей
		New.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**Вывод сообщения в ответ на нажатие кнопки "Новая запись". */
				String group = "Doom";
				String year = "1993";
				String chart = "12";
				Model.addRow(new String[] {group, year, chart});
			}});
		
		Filter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**Вывод сообщения в ответ на нажатие кнопки "Поиск". */
				try
				{
					FindByParams();
				}
				catch(NotFound ex)
				{
					JOptionPane.showMessageDialog(InterForm, "Не найдены группы с заданными параметрами поиска.");
				};
				JOptionPane.showMessageDialog(InterForm, "Проверка нажатия на кнопку \"Поиск\".");
			}});
		
		Year.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				/**Вывод сообщения в ответ на выбор пункта в списке "Год". */
				JOptionPane.showMessageDialog(InterForm, "Проверка выбора пункта раскрывающегося списка \"Год\".");
			}});
		
		Open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				//Создание диалогового окна выбора файла для загрузки данных
				FileDialog open = new FileDialog(InterForm, "Загрузка данных", FileDialog.LOAD);
				open.setFile("*.txt");
				open.setVisible(true);
				
				//Проверка, выбрали ли файл
				String FileName = open.getDirectory() + open.getFile();
				if(FileName == null)
					return;
				
				//Очищаем таблицу
				int rows = Model.getRowCount();
				for(int i = 0; i < rows; i++)
					Model.removeRow(0);
				
				//Считываем данные
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
				
				//Создание диалогового окна выбора файла для сохранения данных
				FileDialog open = new FileDialog(InterForm, "Сохранение данных", FileDialog.SAVE);
				open.setFile("*.txt");
				open.setVisible(true);
				
				//Проверка, выбрали ли файл
				String FileName = open.getDirectory() + open.getFile();
				if(FileName == null)
					return;
				
				//Сохраняем данные
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
		//Создание и отображение экранной формы
		new InterForm().Show();
	};
};
