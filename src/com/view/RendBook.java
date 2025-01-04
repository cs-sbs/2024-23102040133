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
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RendBook {

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
					RendBook window = new RendBook("root");
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
	String account;
	public RendBook(String account) {
		this.account=account;
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
		
		JButton btnNewButton_2 = new JButton("借书");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				if(textField_5.getText().equals("")) {
					Tools.messageWindows("请输入编号");
				}else {
					String data1[]= {textField_5.getText()};
					ResultSet rs = Mysqld.QueryData("select count(*) from s_book where id=? and sl-1>=0",data1);
					try {
						String a="";
						if(rs.next()) {
							a=rs.getString(1);
						}
						if(a.equals("0")) {
							Tools.messageWindows("借阅失败，请检查编号，或者查看馆藏数量");
						}else {
							
							String data11[]= {textField_5.getText()};
							Mysqld.upDate("update s_book set jsl=jsl+1 where id=?", data11);
							
							String data[]= {account,textField_5.getText()};
							System.out.println("-------------");
							System.out.println(account);
							int ac=Mysqld.upDate("insert into s_yy (s_user,s_id) VALUES(?,?)", data);
							Mysqld.upDate("insert into s_rend (s_user,s_id) VALUES(?,?)", data);
							if(ac==1) {
								Tools.messageWindows("借阅成功,等待管理员审核");
							}else {
								Tools.messageWindows("借阅失败，请检查编号");
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				
				
					
				}
				
				
				
			}
		});
		panel.add(btnNewButton_2);
		
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
