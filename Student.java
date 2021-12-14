import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Scanner;
import java.text.*;

//Class in which GUI is created.
public class Student extends JFrame implements ActionListener
{
	JLabel no,name,dob,doj,title;
	JTextField t1,t2;
	JButton submit,reset;
	JButton insert,delete,update,display,display_1;
	JComboBox dd,mm,yy;
	JComboBox dd1,mm1,yy1;
	String number,nm,DOB,DOJ;

	public Student()
	{
		setLayout(null);
		no = new JLabel("ROLL NO. : ");
		no.setBounds(10,10,80,30);
		add(no);

		t1 = new JTextField();
		t1.setBounds(110,10,100,30);
		add(t1);

		name = new JLabel("NAME : ");
		name.setBounds(10,60,80,30);
		add(name);

		t2 = new JTextField();
		t2.setBounds(110,60,150,30);
		add(t2);


		dob = new JLabel("DOB : ");
		dob.setBounds(10,120,80,30);
		add(dob);

		String dates[] = {"Date","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		dd = new JComboBox(dates);
		dd.setBounds(110,120,100,30);
		dd.addActionListener(this);
		add(dd);

		String months[]
			        = { "Month","Jan", "feb", "Mar", "Apr",
			            "May", "Jun", "July", "Aug",
		           "Sep", "Oct", "Nov", "Dec" };
		mm = new JComboBox(months);
		mm.setBounds(220,120,100,30);
		mm.addActionListener(this);
		add(mm);

		String years[]
			        = { "Year","1995", "1996", "1997", "1998",
			            "1999", "2000", "2001", "2002",
			            "2003", "2004", "2005", "2006",
			            "2007", "2008", "2009", "2010",
			            "2011", "2012", "2013", "2014",
			            "2015", "2016", "2017", "2018",
		     	     "2019" };
		yy = new JComboBox(years);
		yy.setBounds(330,120,100,30);
		yy.addActionListener(this);
		add(yy);

		doj = new JLabel("DOJ : ");
		doj.setBounds(10,170,80,30);
		add(doj);

		dd1  = new JComboBox(dates);
		dd1.setBounds(110,170,100,30);
		dd1.addActionListener(this);
		add(dd1);

		mm1 = new JComboBox(months);
		mm1.setBounds(220,170,100,30);
		mm1.addActionListener(this);
		add(mm1);

		yy1 = new JComboBox(years);
		yy1.setBounds(330,170,100,30);
		yy1.addActionListener(this);
		add(yy1);

		submit = new JButton("Submit");
		submit.setBounds(10,210,80,30);
		submit.addActionListener(this);
		add(submit);

		reset = new JButton("Reset");
		reset.setBounds(100,210,80,30);
		reset.addActionListener(this);
		add(reset);

		delete = new JButton("Delete");
		delete.setBounds(10,250,80,30);
		delete.addActionListener(this);
		add(delete);

		update = new JButton("Update");
		update.setBounds(100,250,80,30);
		update.addActionListener(this);
		add(update);

		display = new JButton("Display");
		display.setBounds(190,250,80,30);
		display.addActionListener(this);
		add(display);

		display_1 = new JButton("Display Particular");
		display_1.setBounds(280,250,130,30);
		display_1.addActionListener(this);
		add(display_1);

		setSize(800,500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent ae)
	{
		//On pressing the reset button the current entries will be removed
		if(ae.getSource() == reset)
		{
			t1.setText("");
			t2.setText("");
			//dob.clear();
			//doj.clear();
		}

		//On pressing the submit button the record is inserted in the database
		if(ae.getSource() == submit)
		{
			StudentDemo S = new StudentDemo();
			number = t1.getText();
			nm = t2.getText();
			String dob = yy.getSelectedItem()+"-"+mm.getSelectedIndex()+"-"+dd.getSelectedItem();
			String doj = yy1.getSelectedItem()+"-"+mm1.getSelectedIndex()+"-"+dd1.getSelectedItem();

			java.sql.Date udob =  java.sql.Date.valueOf(dob);
			java.sql.Date udoj =  java.sql.Date.valueOf(doj);
			S.Insert(number,nm,udob,udoj);
			JOptionPane.showMessageDialog(null, "Successfully Inserted..");
		}

		//On pressing the delete button the record is deleted from the database
		if(ae.getSource() == delete)
		{
			StudentDemo S = new StudentDemo();
			S.Delete();
			JOptionPane.showMessageDialog(null, "Successfully Deleted...");
		}

		//On pressing display particular button the record with given student id is displayed on the command prompt
		//Input of student id should be taken on command prompt
		if(ae.getSource() == display_1)
		{
			StudentDemo S = new StudentDemo();
			S.Display_1();
		}

		//On pressing display button it will display the list of names of all the students in student table
		if(ae.getSource() == display)
		{
			StudentDemo S = new StudentDemo();
			S.Display();
		}

		//On pressing Update button the record of the student is updated
		//the input for student id and updating fields should be given on command prompt
		if(ae.getSource() == update)
		{
			StudentDemo S = new StudentDemo();
			S.Update();
		}
	}

	//Main() function
	public static void main(String args[])
	{
		Student S = new Student();
	}
}


//Class StudentDemo consists of fuctions required for CRUD operations
class StudentDemo
{

	Connection con;
	Statement st;
	ResultSet rs;

	//Function to insert a new record
	void Insert(String rn,String nm,java.sql.Date db,java.sql.Date dj)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/studentdb","root","root@1234");
			if(con != null)
			{
				System.out.println("Connected...");
			}
			String sql = "INSERT INTO STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_DOB,STUDENT_DOJ) VALUES(?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1,Integer.parseInt(rn));
			pst.setString(2,nm);
			pst.setDate(3,db);
			pst.setDate(4,dj);
			int rw = pst.executeUpdate();
			if(rw>0)
			{
				System.out.println("Record Inserted Succssfully...");
			}
			con.commit();
			rs.close();
			pst.close();
			con.close();
		}
		catch(SQLException se)
		{
			System.out.println(se);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	//Function to delete an existing record
	void Delete()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/studentdb","root","root@1234");
			System.out.println("Enter STUDENT_NO to delete the existing record from the table : ");
			Scanner sc = new Scanner(System.in);
			int n = sc.nextInt();
			String qry = "DELETE FROM Student WHERE STUDENT_NO = ?";
			PreparedStatement pst = con.prepareStatement(qry);
			pst.setInt(1,n);
			int rowCount = pst.executeUpdate();
			System.out.println("Record Deleted Successfully...");
			rs.close();
			pst.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	//Funcion to display the record of particular student
	void Display_1()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/studentdb","root","root@1234");
			System.out.println("Enter STUDENT_NO to get displayed from the table : ");
			Scanner sc = new Scanner(System.in);
			int n = sc.nextInt();
			String sql = "SELECT * FROM STUDENT WHERE STUDENT_NO = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, n);
			rs = pst.executeQuery();
			while (rs.next()) {
			String id = rs.getString(1);
			String Name = rs.getString(2);
			Date dob = rs.getDate(3);
			Date doj = rs.getDate(4);
			System.out.println("ID: " + id + "\nName: " + Name+"\nDOB: "+dob+"\nDOJ: "+doj);
			rs.close();
			pst.close();
			con.close();
		}
	}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}

	//Function to display names of all the students in the student table
	void Display()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/studentdb","root","root@1234");
			String sql = "SELECT STUDENT_NAME FROM STUDENT";
			PreparedStatement pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next())
			{
				String Name = rs.getString(1);
				System.out.println(Name);
			}
			rs.close();
			pst.close();
			con.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}

	//Function to update a record of student
	void Update()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/studentdb","root","root@1234");
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Student_no to update record : ");
			int rn = sc.nextInt();
			System.out.println("Enter Updated Name : ");
			String name = sc.next();

			String sql = "update student set student_name = ? where student_no = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1,name);
			pst.setInt(2,rn);
			int rw = pst.executeUpdate();
			System.out.println("Record Updated....");
			rs.close();
			pst.close();
			con.close();
		}
		catch(SQLException se)
		{
			System.out.println(se);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
//END