package com.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.mysqld.DBUtil;
import com.mysqld.Mysqld;
import com.tools.Tools;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Login {

	JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBUtil a=new DBUtil("root","123456","db_book");//数据库账号  数据库密码  数据库名字
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("图书管理系统");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("账号");
		lblNewLabel.setBounds(89, 93, 58, 15);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(157, 90, 174, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("密码");
		lblNewLabel_1.setBounds(89, 136, 58, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(157, 133, 174, 21);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("登录");
		
		btnNewButton.setBounds(157, 200, 88, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("注册");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register window = new Register();
				window.frame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(254, 200, 77, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("图书管理系统");
		lblNewLabel_2.setForeground(new Color(220, 20, 60));
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 23));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 10, 416, 73);
		frame.getContentPane().add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"管理员用户", "普通用户"}));
		comboBox.setBounds(157, 167, 174, 23);
		frame.getContentPane().add(comboBox);
		
		
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField.getText().equals("")) {
					Tools.messageWindows("请输入账号");
				}else if(new String(passwordField.getPassword()).toString().equals("")) {
					Tools.messageWindows("请输入密码");
				}else {
					
					if(comboBox.getSelectedIndex()==0) {
						//管理员
						String data[]= {textField.getText(),new String(passwordField.getPassword()).toString()};
						
						ResultSet rs = Mysqld.QueryData("select * from s_admin where account=? and pwd=? and pow='0'",data);
						try {
							if(rs.next()) {
								Tools.messageWindows("管理员登录成功");
								Manage window = new Manage();
								window.frame.setVisible(true);
								frame.dispose();
							}else {
								Tools.messageWindows("账号号密码错误");
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					if(comboBox.getSelectedIndex()==1) {
						//普通用户
							String data[]= {textField.getText(),new String(passwordField.getPassword()).toString()};
						
						ResultSet rs = Mysqld.QueryData("select * from s_admin where account=? and pwd=? and pow='1'",data);
						try {
							if(rs.next()) {
								Tools.messageWindows("普通用户登录成功");
								StaffManage window = new StaffManage(textField.getText());
								window.frame.setVisible(true);
								frame.dispose();
							}else {
								Tools.messageWindows("账号号密码错误");
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					
				}
				
				
			}
		});
	}
}
