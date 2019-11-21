import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BookSystem extends JFrame implements ActionListener, ListSelectionListener
{
 JMenuBar menu_bar;
 
 JMenu menu_file;
 JMenu menu_option;
 
 JMenuItem menu_save;
 JMenuItem menu_quit;
 JMenuItem menu_grayskin;
 JMenuItem menu_blueskin;
 JMenuItem menu_greenskin;
 
 FileWriter writer;
 
 Calendar cal;
  
 JPanel centerPanel;
 JPanel leftPanel;
 JPanel rightPanel;
 JPanel centerPanel_0;
 JPanel centerPanel_1;
 JPanel centerPanel_2;
 JPanel centerPanel_3;
 JPanel centerPanel_4;
 JPanel centerPanel_5;
 JPanel centerPanel_6;
 JPanel centerPanel_7;
 
 JLabel book_name;
 JLabel book_author;
 JLabel book_pub;
 JLabel book_code;
 JLabel member_name;
 JLabel member_button;
 JLabel booklist;
 JLabel lend_booklist;
 
 JTextField tf_book_name;
 JTextField tf_book_author;
 JTextField tf_book_pub;
 JTextField tf_book_code;
 
 JComboBox<String> cb_member_name;
 Vector<String> member_list_data = new Vector<>();
 
 JButton member_join;
 JButton member_delete;
 JButton book_lend;
 JButton book_return;
 JButton book_delete;
 JButton book_new;
  
 JList<String> book_list;
 JList<String> lend_list;
 Vector<String> book_list_data = new Vector<>();
 Vector<String> lend_list_data = new Vector<>();
 
 String url = "jdbc:oracle:thin:@localhost:1521:XE";
 String id = "system";
 String password = "nam";
 
 Connection con;
 PreparedStatement ps;
 ResultSet rs;
 
 public BookSystem()
 {  
  this.connection();
  this.getLibraryBook();
  this.getLendBook();
  this.getMember();
  
  menu_bar = new JMenuBar();
  
  menu_file = new JMenu("파일");
  menu_option = new JMenu("옵션");  
  
  menu_save = new JMenuItem("저장");
  menu_quit = new JMenuItem("종료");
  menu_grayskin = new JMenuItem("그레이 스킨");
  menu_blueskin = new JMenuItem("블루 스킨");
  menu_greenskin = new JMenuItem("그린 스킨");
  
  menu_file.add(menu_save);
  menu_file.addSeparator();
  menu_file.add(menu_quit);
  
  menu_option.add(menu_grayskin);
  menu_option.add(menu_blueskin);
  menu_option.add(menu_greenskin);
  
  menu_bar.add(menu_file);
  menu_bar.add(menu_option);
  
  menu_save.addActionListener(this);
  menu_quit.addActionListener(this);
  menu_grayskin.addActionListener(this);
  menu_blueskin.addActionListener(this);
  menu_greenskin.addActionListener(this);
  
  this.setJMenuBar(menu_bar);
  
  centerPanel = new JPanel();
  leftPanel = new JPanel();
  rightPanel = new JPanel();
  centerPanel_0 = new JPanel();
  centerPanel_1 = new JPanel();
  centerPanel_2 = new JPanel();
  centerPanel_3 = new JPanel();
  centerPanel_4 = new JPanel();
  centerPanel_5 = new JPanel();
  centerPanel_6 = new JPanel();
  centerPanel_7 = new JPanel();
  
  book_name = new JLabel("도서명　");;
  book_author = new JLabel("작　 가　");;
  book_pub = new JLabel("출판사　");;
  book_code = new JLabel("코　드　");;
  member_name = new JLabel("회원명　");;
  member_button = new JLabel("　　　　");;
  booklist = new JLabel("대출 가능 도서 목록");
  lend_booklist = new JLabel("회원 대출 도서 목록");
  
  tf_book_name = new JTextField(11);
  tf_book_author = new JTextField(11);
  tf_book_pub = new JTextField(11);
  tf_book_code = new JTextField(11);
  
  cb_member_name = new JComboBox<String>(member_list_data);
  
  member_join = new JButton("가입");
  member_delete = new JButton("삭제");
  book_lend = new JButton("대출");
  book_return = new JButton("반납");
  book_new = new JButton("신규");
  book_delete = new JButton("삭제");
    
  book_list = new JList<String>(book_list_data);
  lend_list = new JList<String>(lend_list_data);
      
  centerPanel_1.add(book_name);
  centerPanel_1.add(tf_book_name);
  
  centerPanel_2.add(book_author);
  centerPanel_2.add(tf_book_author);
  
  centerPanel_3.add(book_pub);
  centerPanel_3.add(tf_book_pub);
  
  centerPanel_4.add(book_code);
  centerPanel_4.add(tf_book_code);
  
  centerPanel_5.add(member_name);
  centerPanel_5.add(cb_member_name);
  
  centerPanel_6.add(member_button);
  centerPanel_6.add(member_join);
  centerPanel_6.add(member_delete);
  
  centerPanel_7.add(book_lend);
  centerPanel_7.add(book_return);
  centerPanel_7.add(book_new);
  centerPanel_7.add(book_delete);
  
  centerPanel.setLayout(new GridLayout(8,1));
  centerPanel.add(centerPanel_1);
  centerPanel.add(centerPanel_2);
  centerPanel.add(centerPanel_3);
  centerPanel.add(centerPanel_4);
  centerPanel.add(centerPanel_5);
  centerPanel.add(centerPanel_6);
  centerPanel.add(centerPanel_0);
  centerPanel.add(centerPanel_7);
  
  leftPanel.setLayout(new BorderLayout());
  leftPanel.add(booklist, "North");
  leftPanel.add(book_list);

  
  rightPanel.setLayout(new BorderLayout());  
  rightPanel.add(lend_booklist, "North");
  rightPanel.add(lend_list);
  
  member_join.addActionListener(this);
  member_delete.addActionListener(this);
  book_lend.addActionListener(this);
  book_return.addActionListener(this);
  book_delete.addActionListener(this);
  book_new.addActionListener(this);
  
  book_list.addListSelectionListener(this);
  lend_list.addListSelectionListener(this);
  
  this.add(leftPanel, "West");
  this.add(rightPanel, "East");
  this.add(centerPanel);
  
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setSize(650, 350);
  this.setVisible(true);
 }

 @Override
 public void valueChanged(ListSelectionEvent e)
 {
  Object source = e.getSource();
  
  if(source == book_list)
  {
   String data = book_list.getSelectedValue();
   
   String split[] = data.split(" ");
   tf_book_name.setText(split[0]);
   tf_book_author.setText(split[1]);
   tf_book_pub.setText(split[2]);
   tf_book_code.setText(split[3].trim());
  }
  else if(source == lend_list)
  {
   String data = lend_list.getSelectedValue();
   
   String split[] = data.split(" ");
   tf_book_name.setText(split[0]);
   tf_book_author.setText(split[1]);
   tf_book_pub.setText(split[2]);
   tf_book_code.setText(split[3].trim());
  }
 }
  
 @Override
 public void actionPerformed(ActionEvent e)
 {
  Object source = e.getSource();
  
  if(source == menu_save)
   this.menuSave();
  else if(source == menu_quit)
   this.menuQuit();
  else if(source == menu_grayskin)
   this.menuSkin(Color.LIGHT_GRAY);
  else if(source == menu_blueskin)
   this.menuSkin(Color.BLUE);
  else if(source == menu_greenskin)
   this.menuSkin(Color.GREEN);
  
  else if(source == member_join)
   new MemberJoin(con);
  else if(source == member_delete)
   new MemberDel(con);
  
  else if(source == book_lend)
   this.bookLend();
  else if(source == book_return)
   this.bookRetrun();
  else if(source == book_delete)
   this.bookDelete();
  else if(source == book_new)
   this.bookNew();
 }
 
 public void connection()
 {  
  try
  {
   Class.forName("oracle.jdbc.driver.OracleDriver");
   con = DriverManager.getConnection(url, id, password);
  }
  catch(Exception e)
  {
   System.out.println("연결 에러");
  }  
 }
 
 public void menuSave()
 {
  String sql1 = "SELECT * from library_book order by book_code";
  String sql2 = "SELECT * from library_lendbook order by book_code";
  String sql3 = "SELECT * from library_member order by id";
  
  cal = Calendar.getInstance();
  
  try
  {   
   writer = new FileWriter("C:\\java\\workspace\\library_list.hwp", true);
   //writer.flush();
 
   ps = con.prepareStatement(sql1);
   rs = ps.executeQuery();
   
   writer.append(cal.get(Calendar.YEAR)+"년 ");
   writer.append(cal.get(Calendar.MONTH)+"월 ");
   writer.append(cal.get(Calendar.DAY_OF_MONTH)+"일 ");
   writer.append(cal.get(Calendar.HOUR_OF_DAY)+"시\n");
   
   writer.append(booklist.getText()+"\n");
   while(rs.next())
   {
    writer.append(rs.getString(1).trim()+"\t\t");
    writer.append(rs.getString(2).trim()+"\t\t");
    writer.append(rs.getString(3).trim()+"\t\t");
    writer.append(rs.getString(4).trim()+"\n");
   }
      
   ps = con.prepareStatement(sql2);
   rs = ps.executeQuery();
   
   writer.append(lend_booklist.getText()+"\n");
   while(rs.next())
   {
    writer.append(rs.getString(1).trim()+"\t\t");
    writer.append(rs.getString(2).trim()+"\t\t");
    writer.append(rs.getString(3).trim()+"\t\t");
    writer.append(rs.getString(4).trim()+"\t\t");
    writer.append(rs.getString(4).trim()+"\n");
   }
   
   ps = con.prepareStatement(sql3);
   rs = ps.executeQuery();
   
   writer.append("전체 회원 목록"+"\n");
   while(rs.next())
   {
    writer.append(rs.getString(1).trim()+"\t\t");
    writer.append(rs.getString(2).trim()+"\t\t");
    writer.append(rs.getString(3).trim()+"\n");
   }
   
   ps.close();
   
   writer.close();
  }
  catch(Exception e)
  {
  }
 }
 
 public void menuQuit()
 {
  System.exit(0);
 }
 
 public void menuSkin(Color color)
 {
  centerPanel.setBackground(color);
  centerPanel_0.setBackground(color);
  centerPanel_1.setBackground(color);
  centerPanel_2.setBackground(color);
  centerPanel_3.setBackground(color);
  centerPanel_4.setBackground(color);
  centerPanel_5.setBackground(color);
  centerPanel_6.setBackground(color);
  centerPanel_7.setBackground(color);
 }
 
 public void memberJoin()
 {
  String sql = "INSERT into library_member values(?)";
  
  try
  {   
   ps = con.prepareStatement(sql);
   
   ps.setString(1, cb_member_name.getSelectedItem().toString().trim());
   
   ps.executeUpdate();
   
   ps.close();
  }
  catch(Exception e)
  {
  }
 }
 
 public void bookLend()
 {
  String sql = "SELECT * from library_book where book_code=?";
  String this_book = "";
  boolean check = true;
  
  try
  {
   ps = con.prepareStatement(sql);
   
   ps.setString(1, tf_book_code.getText());
   
   rs = ps.executeQuery();
   
   while(rs.next())
   {
    this_book += rs.getString(1).trim()+" ";
    this_book += rs.getString(2).trim()+" ";
    this_book += rs.getString(3).trim()+" ";
    this_book += rs.getString(4).trim()+" ";
   }
   
   ps.close();
  }
  catch(Exception e)
  {
  }
    
  sql = "INSERT into library_lendbook values(?, ?, ?, ?, ?)";
  
  try
  {
   ps = con.prepareStatement(sql);
   
   String split[] = this_book.split(" "); 
   
   ps.setString(1, split[0].trim());
   ps.setString(2, split[1].trim());
   ps.setString(3, split[2].trim());
   ps.setString(4, split[3].trim());
   if(cb_member_name.getSelectedItem().toString().trim().equals("회원 목록"))
    JOptionPane.showMessageDialog(null, "회원을 선택해 주세요.");
   else
    ps.setString(5, cb_member_name.getSelectedItem().toString().trim());
   
   ps.executeUpdate();
   
   ps.close();
  }
  catch(Exception e)
  {
   JOptionPane.showMessageDialog(null, "코드를 잘못 입력하셨습니다.");
   check = false;
  }  
  
  if(check)
  {
   sql = "DELETE from library_book where book_code=?";
   
   try
   {
    ps = con.prepareStatement(sql);
    
    String split[] = this_book.split(" "); 
    
    ps.setString(1, split[3].trim());
    
    ps.executeUpdate();
    
    ps.close();
   }
   catch(Exception e)
   {
   }
  }
    
  this.getLibraryBook();
  this.getLendBook();
 }
 
 public void bookRetrun()
 {
  String sql = "SELECT * from library_lendbook where book_code=?";
  String this_book = "";
  boolean check = true;
  
  try
  {
   ps = con.prepareStatement(sql);
   
   ps.setString(1, tf_book_code.getText());
   
   rs = ps.executeQuery();
   
   while(rs.next())
   {
    this_book += rs.getString(1).trim()+" ";
    this_book += rs.getString(2).trim()+" ";
    this_book += rs.getString(3).trim()+" ";
    this_book += rs.getString(4).trim()+" ";
   }
   
   ps.close();
  }
  catch(Exception e)
  {
   System.out.println("입력하신 코드의 책은 이미 반납 되었거나, 존재하지 않습니다.");
  }
    
  sql = "INSERT into library_book values(?, ?, ?, ?)";
  
  try
  {
   ps = con.prepareStatement(sql);
   
   String split[] = this_book.split(" "); 
   
   ps.setString(1, split[0].trim());
   ps.setString(2, split[1].trim());
   ps.setString(3, split[2].trim());
   ps.setString(4, split[3].trim());
   
   ps.executeUpdate();
   
   ps.close();
  }
  catch(Exception e)
  {
   check = false;
  }
  
  if(check)
  {
   sql = "DELETE from library_lendbook where book_code=?";
   
   try
   {
    ps = con.prepareStatement(sql);
    
    String split[] = this_book.split(" "); 
    
    ps.setString(1, split[3].trim());
    
    ps.executeUpdate();
    
    ps.close();
   }
   catch(Exception e)
   {
   }
  }
    
  this.getLibraryBook();
  this.getLendBook();
 } 
 
 public void bookNew()
 {
  String sql = "INSERT into library_book values(?, ?, ?, ?)";
    
  try
  {   
   ps = con.prepareStatement(sql);
   
   ps.setString(1, tf_book_name.getText().trim());
   ps.setString(2, tf_book_author.getText().trim());
   ps.setString(3, tf_book_pub.getText().trim());
   ps.setString(4, tf_book_code.getText().trim());
   
   ps.executeUpdate();
   
   ps.close();
  }
  catch(Exception e)
  {
  }
  
  this.getLibraryBook();
 }
 
 public void bookDelete()
 {
  String sql = "DELETE from library_book where book_code=?";
  
  try
  {
   ps = con.prepareStatement(sql);
   
   ps.setString(1, tf_book_code.getText());
     
   ps.executeUpdate();
   
   ps.close();
  }
  catch(Exception e)
  {
  }
  
  this.getLibraryBook();
 }
 
 public void getLibraryBook()
 {
  String sql = "SELECT * from library_book order by book_code";
  
  book_list_data.removeAllElements();
  
  try
  {
   ps = con.prepareStatement(sql);
   
   rs = ps.executeQuery();
   
   while(rs.next())
   {
    String library_book_list = "";
    
    library_book_list += rs.getString(1)+" ";
    library_book_list += rs.getString(2)+" ";
    library_book_list += rs.getString(3)+" ";
    library_book_list += rs.getString(4)+"\n";
    
    book_list_data.add(library_book_list);
   }
   
   rs.close();
   ps.close();
   
   book_list.setListData(book_list_data);
  }
  catch(Exception e)
  {
  }
 }
 
 public void getLendBook()
 {
  String sql = "SELECT * from library_lendbook order by book_code";
  
  lend_list_data.removeAllElements();
  
  try
  {
   ps = con.prepareStatement(sql);
   
   rs = ps.executeQuery();
   
   while(rs.next())
   {
    String lend_book_list = "";
    
    lend_book_list += rs.getString(1)+" ";
    lend_book_list += rs.getString(2)+" ";
    lend_book_list += rs.getString(3)+" ";
    lend_book_list += rs.getString(4)+" ";
    lend_book_list += rs.getString(5)+"\n";
    
    lend_list_data.add(lend_book_list);
   }
   
   rs.close();
   ps.close();
   
   lend_list.setListData(lend_list_data);
  }
  catch(Exception e)
  {
  }
 }
 
 public void getMember()
 {
  String sql = "SELECT * from library_member order by id";
  
  member_list_data.removeAllElements();
  member_list_data.add("회원 목록");
  
  try
  {
   ps = con.prepareStatement(sql);
   
   rs = ps.executeQuery();
   
   while(rs.next())
   {
    String member_list = "";
    
    member_list += rs.getString(1).trim();
    
    member_list_data.add(member_list);
   }
   
   rs.close();
   ps.close();
   
  }
  catch(Exception e)
  {
  } 
 }
 
 public static void main(String[] args)
 {
  new BookSystem();
 }
}

MemberJoin.java

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MemberJoin extends JFrame implements ActionListener, ListSelectionListener
{
 JPanel centerpanel;
 JPanel panel1;
 JPanel panel2;
 JPanel panel3;
 JPanel panel4;
 JPanel rightpanel;
 
 JLabel l_id;
 JLabel l_name;
 JLabel l_phone;
 JLabel l_list;
 
 JTextField tf_id;
 JTextField tf_name;
 JTextField tf_phone;
 
 JButton ok;
 JButton cancel;
 
 JList<String> list;
 Vector<String> listdata;;
 
 String url = "jdbc:oracle:thin:@localhost:1521:XE";
 String id = "system";
 String phone = "nam";
 
 Connection con;
 PreparedStatement ps;
 ResultSet rs;
 
 public MemberJoin(Connection con)
 {
  this.con = con;
  
  centerpanel = new JPanel();
  panel1 = new JPanel();
  panel2 = new JPanel();
  panel3 = new JPanel();
  panel4 = new JPanel();
  rightpanel = new JPanel();
  
  l_id = new JLabel("아이디　 : ");
  l_name = new JLabel("이　름　 : ");
  l_phone = new JLabel("전화번호 : ");
  l_list = new JLabel("회원 목록");
  
  tf_id = new JTextField(7);
  tf_name = new JTextField(7);
  tf_phone = new JTextField(7);
  
  ok = new JButton("등록");
  cancel = new JButton("취소");
  
  panel1.add(l_id);
  panel1.add(tf_id);
  
  panel2.add(l_name);
  panel2.add(tf_name);
  
  panel3.add(l_phone);
  panel3.add(tf_phone);
  
  panel4.add(ok);
  panel4.add(cancel);
  
  list = new JList<String>(listdata);
  listdata = new Vector<>();
  
  ok.addActionListener(this);
  cancel.addActionListener(this);
  
  centerpanel.setLayout(new GridLayout(4, 1));
  centerpanel.add(panel1);
  centerpanel.add(panel2);
  centerpanel.add(panel3);
  centerpanel.add(panel4);
  
  rightpanel.setLayout(new BorderLayout());
  rightpanel.add("North", l_list);
  rightpanel.add(list);
  
  list.addListSelectionListener(this);
  
  this.add(centerpanel);  
  this.add("East", rightpanel);
  
  this.getAllMember();
  
  this.setSize(360, 200);
  this.setVisible(true);
 }
 
 public void valueChanged(ListSelectionEvent e)
 {
  Object source = e.getSource();
  
  String data = list.getSelectedValue();
  
  String split[] = data.split(" ");
  tf_id.setText(split[0]);
  tf_name.setText(split[1]);
  tf_phone.setText(split[2]);  
 }

 public void actionPerformed(ActionEvent e)
 {
  Object source = e.getSource();
  
  if(source == ok)
  {
   String sql = "INSERT into library_member values(?, ?, ?)";
   
   try
   {   
    ps = con.prepareStatement(sql);
    
    ps.setString(1, tf_id.getText().trim());
    ps.setString(2, tf_name.getText().trim());
    ps.setString(3, tf_phone.getText().trim());
    
    ps.executeUpdate();
    
    ps.close();
   }
   catch(Exception e1)
   {
    JOptionPane.showMessageDialog(null, "잘못 입력하셨습니다.");
   }
   
   tf_id.setText("");
   tf_name.setText("");
   tf_phone.setText("");
   
   this.getAllMember();
  }
  else if(source == cancel)
  {
   tf_id.setText("");
   tf_name.setText("");
   tf_phone.setText("");
  }
 }
 
 public void getAllMember()
 {
  String sql = "SELECT * from library_member order by id";
  listdata.removeAllElements();
  
  try
  {
   ps = con.prepareStatement(sql);
   
   rs = ps.executeQuery();
   
   while(rs.next())
   {
    String AllMember = "";
    
    AllMember += rs.getString(1)+" ";
    AllMember += rs.getString(2)+" ";
    AllMember += rs.getString(3)+" ";
    
    
    listdata.add(AllMember);
   }
   
   rs.close();
   ps.close();
   
   list.setListData(listdata);
  }
  catch(Exception e)
  {
  }
 }
}

MemberDel.java

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MemberDel extends JFrame implements ActionListener, ListSelectionListener
{
 JPanel centerpanel;
 JPanel panel1;
 JPanel panel2;
 JPanel rightpanel;
 
 JLabel l_id;
 JLabel l_list;
 
 JTextField tf_id;
 
 JButton del;
 JButton cancel;
 
 String url = "jdbc:oracle:thin:@localhost:1521:XE";
 String id = "system";
 String password = "nam";
 
 JList<String> list;
 Vector<String> listdata;;
 
 Connection con;
 PreparedStatement ps;
 ResultSet rs;
 
 public MemberDel(Connection con)
 {
  this.con = con;
  
  centerpanel = new JPanel();
  panel1 = new JPanel();
  panel2 = new JPanel();

  rightpanel = new JPanel();
  
  l_id = new JLabel("아이디　 : ");
  l_list = new JLabel("회원 목록");
  
  tf_id = new JTextField(7);
  
  del = new JButton("삭제");
  cancel = new JButton("취소");
  
  panel1.add(l_id);
  panel1.add(tf_id);
  
  panel2.add(del);
  panel2.add(cancel);
  
  list = new JList<String>(listdata);
  listdata = new Vector<>();
  
  del.addActionListener(this);
  cancel.addActionListener(this);
  
  centerpanel.setLayout(new BorderLayout());
  centerpanel.add(panel1);
  centerpanel.add("South", panel2);
   
  rightpanel.setLayout(new BorderLayout());
  rightpanel.add("North", l_list);
  rightpanel.add(list);
  
  list.addListSelectionListener(this);
  
  this.add(centerpanel);  
  this.add("East", rightpanel);
  
  this.getAllMember();
  
  this.setSize(360, 200);
  this.setVisible(true);
 }
 
 public void valueChanged(ListSelectionEvent e)
 {
  Object source = e.getSource();
  
  String data = list.getSelectedValue();
  
  String split[] = data.split(" ");
  tf_id.setText(split[0]);
 }

 public void actionPerformed(ActionEvent e)
 {
  Object source = e.getSource();
  
  if(source == del)
  {
   String sql = "DELETE from library_member where id=?";
   
   try
   {
    ps = con.prepareStatement(sql);
    
    ps.setString(1, tf_id.getText().trim());
    
    ps.executeUpdate();
    
    ps.close();
   }
   catch(Exception e1)
   {
    JOptionPane.showMessageDialog(null, "잘못 입력하셨습니다.");
   }
   
   tf_id.setText("");
  }
  else if(source == cancel)
  {
   tf_id.setText("");
  }
  
  this.getAllMember();
 }
 
 public void getAllMember()
 {
  String sql = "SELECT * from library_member order by id";
  listdata.removeAllElements();
  
  try
  {
   ps = con.prepareStatement(sql);
   
   rs = ps.executeQuery();
   
   while(rs.next())
   {
    String AllMember = "";
    
    AllMember += rs.getString(1)+" ";
    AllMember += rs.getString(2)+" ";
    AllMember += rs.getString(3)+"\n";
    
    listdata.add(AllMember);
   }
   
   rs.close();
   ps.close();
   
   list.setListData(listdata);
  }
  catch(Exception e)
  {
  }
 }
}