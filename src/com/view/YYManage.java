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

public class YYManage {

	JFrame frame;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBUtil a=new DBUtil("root","","db_book");
					YYManage window = new YYManage();
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
	public YYManage() {
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
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBorder(new TitledBorder(null, "\u64CD\u4F5C\u9762\u677F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 724, 69);
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
					int a=Mysqld.upDate("DELETE FROM s_yy where id=?", data);
					if(a==1) {
						Tools.messageWindows("删除成功");
					}else {
						Tools.messageWindows("删除失败，请检查编号");
					}
				
					
				}
				
				
				
			}
		});
		panel.add(btnNewButton_2);
		JButton btnNewButton_4 = new JButton("查询");
		panel.add(btnNewButton_4);
		
		JButton btnNewButton = new JButton("同意");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(textField_5.getText().equals("")) {
					Tools.messageWindows("请输入编号");
				}else {
					
					String data[]= {textField_5.getText()};
					int a=Mysqld.upDate("UPDATE s_yy set s_zt='1' where id=?", data);
					if(a==1) {
						Tools.messageWindows("更改成功");
					}else {
						Tools.messageWindows("更改失败，请检查编号");
					}
				
				}
			
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("拒绝");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField_5.getText().equals("")) {
					Tools.messageWindows("请输入编号");
				}else {
					String data[]= {textField_5.getText()};
					int a=Mysqld.upDate("UPDATE s_yy set s_zt='2' where id=?", data);
					if(a==1) {
						Tools.messageWindows("更改成功");
					}else {
						Tools.messageWindows("更改失败，请检查编号");
					}
				}
				
			
			}
		});
		panel.add(btnNewButton_1);
		
		

		Object columns[] ={"编号","图书名字","借书人","状态"};//创建表格 表格表头
		Table t1Table=new Table(columns);
		JScrollPane JS = t1Table.getJScrollPane();
		DefaultTableModel model = t1Table.getModel();
		JS.setBounds(10, 179, 724, 310);
		frame.getContentPane().add(JS);
		
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(textField_5.getText().equals("")) {
					ResultSet rs = Mysqld.QueryData("select s_yy.id ,s_book.bname ,s_yy.s_user   ,if(s_yy.s_zt=0,'未处理',if(s_yy.s_zt=1,'同意','拒绝')) from s_yy  LEFT JOIN s_book  on s_book.id=s_yy.s_id", null);
					Tools.addDataTable(rs,model, 4);
				
				}else {
					
					String data[]= {textField_5.getText()};
					ResultSet rs = Mysqld.QueryData("select s_yy.id ,s_book.bname ,s_yy.s_user   ,if(s_yy.s_zt=0,'未处理',if(s_yy.s_zt=1,'同意','拒绝')) from s_yy  LEFT JOIN s_book  on s_book.id=s_yy.s_id where s_yy.id=?",data);
					Tools.addDataTable(rs,model, 4);
				}
				
				
				
				
			}
		});
		
		
	}
}
