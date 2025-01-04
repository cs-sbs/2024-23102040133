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

public class Record {

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
					Record window = new Record("root");
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
	public Record(String account) {
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
		
		JButton btnNewButton = new JButton("还书");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				if(textField_5.getText().equals("")) {
					Tools.messageWindows("请输入编号");
				}else {
					String data[]= {textField_5.getText(),account};
					ResultSet rs = Mysqld.QueryData("select count(*) from s_rend where s_zt='0' and id=? and s_user=?", data);
					try {
						String a=null;;
						if(rs.next()) {
							a=rs.getString(1);
						}
						if(a.equals("0")) {
							//没有这个编号
							Tools.messageWindows("当前图书已经还了，或者编号错误");
						}else {
							int ac=Mysqld.upDate("update s_rend set s_zt='1' where id=? and s_user=?", data);
							if(ac==1) {
								String data2[]= {textField_5.getText()};
								
								Mysqld.upDate("update s_book set jsl=jsl-1 where id=(select s_id from s_rend  where id=?)", data2);
								Tools.messageWindows("还书成功");
							}else {
								Tools.messageWindows("还书失败");
							}
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_4 = new JButton("查询");
		
		panel.add(btnNewButton_4);
		
		

		Object columns[] ={"编号","用户","作者","状态"};//创建表格 表格表头
		Table t1Table=new Table(columns);
		JScrollPane JS = t1Table.getJScrollPane();
		DefaultTableModel model = t1Table.getModel();
		JS.setBounds(10, 179, 724, 310);
		frame.getContentPane().add(JS);
		
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String data1[]= {account};
				
				ResultSet rs = Mysqld.QueryData("\r\n"
						+ "select s_rend.id,s_rend.s_user,s_book.bname,if(s_rend.s_zt=0,'借阅中','已归还') from s_rend LEFT JOIN s_book on s_book.id=s_rend.s_id where s_rend.s_user=?", data1);
				Tools.addDataTable(rs,model, 4);
				
			
				
				
				
				
			}
		});
		
		
	}
}
