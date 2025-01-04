package com.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.mysqld.DBUtil;
import com.mysqld.Mysqld;
import com.tools.Table;
import com.tools.Tools;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class BookManage {

	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBUtil a=new DBUtil("root","","db_book");
					BookManage window = new BookManage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookManage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("图书管理系统");
		frame.setBounds(100, 100, 758, 536);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("编号");
		lblNewLabel.setBounds(10, 10, 58, 15);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(78, 7, 96, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("图书名字");
		lblNewLabel_1.setBounds(184, 10, 58, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(252, 7, 96, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("作者");
		lblNewLabel_2.setBounds(358, 10, 58, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(426, 7, 96, 21);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("出版时间");
		lblNewLabel_3.setBounds(10, 56, 58, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		textField_3 = new JTextField();
		textField_3.setBounds(78, 53, 96, 21);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("数量");
		lblNewLabel_4.setBounds(184, 56, 58, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		textField_4 = new JTextField();
		textField_4.setBounds(252, 53, 96, 21);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnNewButton = new JButton("添加图书");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(textField.getText().equals("")) {
					Tools.messageWindows("请输入编号");
				}else if(textField_1.getText().equals("")) {
					Tools.messageWindows("请输入图书名字");
				}else if(textField_2.getText().equals("")) {
					Tools.messageWindows("请输入作者名字");
				}else if(textField_3.getText().equals("")) {
					Tools.messageWindows("请输入出版时间");
				}else if(textField_4.getText().equals("")) {
					Tools.messageWindows("请输入数量");
				}else {
					
					String data[]= {textField.getText(),textField_1.getText(),textField_2.getText(),textField_3.getText(),textField_4.getText()};
					int a=Mysqld.upDate("insert into s_book (id,bname,zname,ctime,sl) VALUES(?,?,?,?,?)", data);
					if(a==1) {
						Tools.messageWindows("添加成功");
					}else {
						Tools.messageWindows("添加失败，请检查编号");
					}
					
					
				}
				
				
			}
		});
		btnNewButton.setBounds(358, 52, 97, 23);
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBorder(new TitledBorder(null, "\u64CD\u4F5C\u9762\u677F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 100, 724, 69);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel_5 = new JLabel("编号");
		panel.add(lblNewLabel_5);
		
		textField_5 = new JTextField();
		panel.add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("删除");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				if(textField_5.getText().equals("")) {
					Tools.messageWindows("请输入编号");
				}else {
					
					String data[]= {textField_5.getText()};
					int a=Mysqld.upDate("DELETE FROM s_book where id=?", data);
					if(a==1) {
						Tools.messageWindows("删除成功");
					}else {
						Tools.messageWindows("删除失败，请检查编号");
					}
				
					
				}
				
				
				
			}
		});
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("更改");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField.getText().equals("")) {
					Tools.messageWindows("请输入编号");
				}else if(textField_1.getText().equals("")) {
					Tools.messageWindows("请输入图书名字");
				}else if(textField_2.getText().equals("")) {
					Tools.messageWindows("请输入作者名字");
				}else if(textField_3.getText().equals("")) {
					Tools.messageWindows("请输入出版时间");
				}else if(textField_4.getText().equals("")) {
					Tools.messageWindows("请输入数量");
				}else if(textField_5.getText().equals("")) {
					Tools.messageWindows("请输入条件编号");
				}else {
					
					String data[]= {textField.getText(),textField_1.getText(),textField_2.getText(),textField_3.getText(),textField_4.getText(),textField_5.getText()};
					int a=Mysqld.upDate("UPDATE s_book set id=?,bname=?,zname=?,ctime=?,sl=? where id=?", data);
					if(a==1) {
						Tools.messageWindows("更改成功");
					}else {
						Tools.messageWindows("更改失败，请检查编号");
					}
					
					
				}
				
				
			}
		});
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("查询");
		
		panel.add(btnNewButton_4);
		
		

		Object columns[] ={"编号","图书名字","作者","出版时间","馆藏数量","借出数量"};//创建表格 表格表头
		Table t1Table=new Table(columns);
		JScrollPane JS = t1Table.getJScrollPane();
		DefaultTableModel model = t1Table.getModel();
		JS.setBounds(10, 179, 724, 310);
		frame.getContentPane().add(JS);
		
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(textField_5.getText().equals("")) {
					ResultSet rs = Mysqld.QueryData("select * from s_book", null);
					Tools.addDataTable(rs,model, 6);
				
				}else {
					
					String data[]= {textField_5.getText()};
					ResultSet rs = Mysqld.QueryData("select * from s_book where id=?",data);
					Tools.addDataTable(rs,model, 6);
				}
				
				
				
				
			}
		});
		
		
	}
}
