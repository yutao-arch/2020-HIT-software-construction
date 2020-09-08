package Board;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import EntryState.CourseState;
import EntryState.FlightState;
import PlanningEntry.CourseEntry;
import PlanningEntry.FlightEntry;
import Resource.Teacher;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;

public class CourseEntryBoard extends JFrame {

	private JPanel contentPane;
	private JTable table;
    private Timer time;

    private String classroomlocation;  //该board版的位置字符串
	private List<CourseEntry<Teacher>> allentry=new ArrayList<>();  //储存CourseEntry的一个集合
	private Calendar nowtime; //当前时间
	
	// mutability类
 	// Abstraction function:
 	// AF(classroomlocation)=当前位置
 	// AF(allentry)=该位置所有计划项
	// AF(nowtime)=当前时间
 	// Representation invariant:
 	// 时间转字符串出问题抛出异常
 	// Safety from rep exposure:
 	// 将classroomlocation,allentry,nowtime设置为private
    
	/**
	 * 构造方法
	 */
	public CourseEntryBoard() {
	}
	
	/**
	 * 设置board的位置
	 * @param mm 该board版的位置字符串
	 */
	public void setclassroomlocation(String mm) {
		this.classroomlocation=mm;
	}
	
	/**
	 * 从CourseEntry的一个集合找到与classroomlocation位置相同的CourseEntry并按照每个计划项的起始时间排序
	 * @param courselist 待排序的CourseEntry集合
	 */
	public void getsortallentry(List<CourseEntry<Teacher>> courselist) {
		for(int i=0;i<courselist.size();i++) {
			allentry.add(courselist.get(i));
		}
		Iterator<CourseEntry<Teacher>> iterator=allentry.iterator();
		while(iterator.hasNext()) {
			CourseEntry<Teacher> pe=iterator.next();
			if(!(pe.getlocations().getlocationname().equals(classroomlocation))) {
				iterator.remove();
			}
		}
		Collections.sort(allentry,new Comparator<CourseEntry<Teacher>>() {

			@Override
			public int compare(CourseEntry<Teacher> o1, CourseEntry<Teacher> o2) {
				if(o1.gettimeslot().getbegintime().compareTo(o2.gettimeslot().getbegintime())>0) {
					return 1;
				}
				else if(o1.gettimeslot().getbegintime().compareTo(o2.gettimeslot().getbegintime())==0) {
					return 0;
				}
				return -1;
			}
		});
	}
	
	/**
	 * 显示当前board
	 */
	public void visualize() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 创建frame并储存CourseEntry集合的各种信息
	 * @throws ParseException 
	 */
	public List<CourseEntry<Teacher>> createCourseEntryBoard() throws ParseException {
	
		setTitle("教室占用情况表");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 630, 500);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 600, 480);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");   
		time = new Timer(1000,new ActionListener() {   
		 
		public void actionPerformed(ActionEvent arg0) {  
			lblNewLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+","+classroomlocation);
		}  
		});  
		time.start();   
		lblNewLabel.setBounds(0, 0, 600, 30);
		panel.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 60, 600, 250);
		panel.add(scrollPane);
		
		int i,j;
		CourseEntry<Teacher> course;
		String[][] biao=new String[100][4];
		
		nowtime = Calendar.getInstance();  //当前时间
		String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(nowtime.getTime()); 
	    nowtime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str));
	    Calendar begintime,endtime;
	    
	    //System.out.println(allentry.get(0).getplanningentryname());
	    
		Iterator<CourseEntry<Teacher>> iterator=allentry.iterator();
		while(iterator.hasNext()) {
			CourseEntry<Teacher> pe=iterator.next();
			begintime=pe.gettimeslot().getbegintime();
			if(!((begintime.get(Calendar.YEAR)==(nowtime.get(Calendar.YEAR)))&&
					(begintime.get(Calendar.MONTH)==nowtime.get(Calendar.MONTH))&&
					(begintime.get(Calendar.DAY_OF_MONTH)==nowtime.get(Calendar.DAY_OF_MONTH)))) {
				iterator.remove();
			}
		}
		
		//System.out.println(allentry.get(0).getplanningentryname());
		
		for(i=0;i<100;i++) {
		    for(j=0;j<4;j++) {
				biao[i][j]=null;
			}
		}
		
		for(i=0;i<allentry.size();i++) {
			course=allentry.get(i);
			begintime=course.gettimeslot().getbegintime();
			endtime=course.gettimeslot().getendtime();
			if(course!=null) {
				biao[i][0]=Integer.toString(begintime.get(Calendar.HOUR_OF_DAY))+":"
			            +Integer.toString(begintime.get(Calendar.MINUTE))+"-"+
						Integer.toString(endtime.get(Calendar.HOUR_OF_DAY))
			            +":"+Integer.toString(endtime.get(Calendar.MINUTE));
				biao[i][1]=(String) course.getplanningentryname();
				if(course.getresource()==null) {
					 biao[i][2]="未设置老师资源";
				}
				else {
					 biao[i][2]=course.getresource().getteachername();
				}
			    biao[i][3]=((CourseState)course.getcurrentstate()).getcoursestate();
			}
		}
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			biao,
			new String[] {
				"课程时间", "课程名", "教师", "状态"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		scrollPane.setViewportView(table);
	
		return allentry;
	}	
}

