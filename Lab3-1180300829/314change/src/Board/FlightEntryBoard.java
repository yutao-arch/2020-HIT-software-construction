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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import EntryState.FlightState;
import EntryState.TrainState;
import Location.FlightTrainLocation;
import PlanningEntry.FlightEntry;
import Resource.Flight;

public class FlightEntryBoard extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private Timer time;

	private FlightTrainLocation airportlocation;  //该board版的位置
	private List<FlightEntry<Flight>> comeentry=new ArrayList<>(); //储存抵达的FlightEntry的一个集合
	private List<FlightEntry<Flight>> toentry=new ArrayList<>(); //储存起飞的FlightEntry的一个集合
	private Calendar nowtime; //当前时间
	private String weidu="北纬40度",jingdu="东经112度";
	
	// mutability类
	// Abstraction function:
	// AF(airportlocation)=当前位置
	// AF(comeentry)=该位置所有抵达计划项
	// AF(toentry)=该位置所有起飞计划项
	// AF(nowtime)=当前时间
	// Representation invariant:
	// 时间转字符串出问题抛出异常
	// Safety from rep exposure:
	// 将airportlocation,comeentry,toentry,nowtime设置为private

	/**
	 * 构造方法
	 */
	public FlightEntryBoard() {
	}
	
	/**
	 * 设置board的位置
	 * @param mm 该board版的位置字符串
	 */
	public void setairportlocation(String mm) {
		this.airportlocation=new FlightTrainLocation(weidu,jingdu,mm);
	}
	
	/**
	 * 从FlightEntry的一个集合找到与airportlocation位置相同的FlightEntry并按照每个计划项的抵达时间排序
	 * @param courselist 待排序的FlightEntry集合
	 */
	public void getsortcomeentry(List<FlightEntry<Flight>> flightlist) {
		for(int i=0;i<flightlist.size();i++) {
			comeentry.add(flightlist.get(i));
		}
		Iterator<FlightEntry<Flight>> iterator=comeentry.iterator();
		while(iterator.hasNext()) {
			FlightEntry<Flight> pe=iterator.next();
			 if((!pe.getlocations().contains(airportlocation))||(pe.getlocations().indexOf(airportlocation)==0)) {  //进来的entey需要除去该站为第一个站的
				iterator.remove();
			}
		}
		Collections.sort(comeentry,new Comparator<FlightEntry<Flight>>() {

			@Override
			public int compare(FlightEntry<Flight> o1, FlightEntry<Flight> o2) {
				if(o1.gettimeslot().get(o1.getlocations().indexOf(airportlocation)-1).getendtime().compareTo(o2.gettimeslot().get(o2.getlocations().indexOf(airportlocation)-1).getendtime())>0) {
					return 1;
				}
				else if(o1.gettimeslot().get(o1.getlocations().indexOf(airportlocation)-1).getendtime().compareTo(o2.gettimeslot().get(o2.getlocations().indexOf(airportlocation)-1).getendtime())==0) {
					return 0;
				}
				return -1;
			}
		});
	}
	
	/**
	 * 从FlightEntry的一个集合找到与airportlocation位置相同的FlightEntry并按照每个计划项的起飞时间排序
	 * @param courselist 待排序的FlightEntry集合
	 */
	public void getsorttoentry(List<FlightEntry<Flight>> flightlist) {
		for(int i=0;i<flightlist.size();i++) {
			toentry.add(flightlist.get(i));
		}
		Iterator<FlightEntry<Flight>> iterator=toentry.iterator();
		while(iterator.hasNext()) {
			FlightEntry<Flight> pe=iterator.next();
			 if(!pe.getlocations().contains(airportlocation)||(pe.getlocations().indexOf(airportlocation)==pe.getlocations().size()-1)) {  ////除去的entey需要除去该站为最后一个站的
				iterator.remove();
			}
		}
		Collections.sort(toentry,new Comparator<FlightEntry<Flight>>() {
			@Override
			public int compare(FlightEntry<Flight> o1, FlightEntry<Flight> o2) {
				if(o1.gettimeslot().get(o1.getlocations().indexOf(airportlocation)).getbegintime().compareTo(o2.gettimeslot().get(o2.getlocations().indexOf(airportlocation)).getbegintime())>0) {
					return 1;
				}
				else if(o1.gettimeslot().get(o1.getlocations().indexOf(airportlocation)).getbegintime().compareTo(o2.gettimeslot().get(o2.getlocations().indexOf(airportlocation)).getbegintime())==0) {
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
	 * 创建frame并储存FlightEntry集合的各种信息
	 * @throws ParseException 
	 */
	public List<FlightEntry<Flight>> createFlightEntryBoard() throws ParseException  {
		setTitle("航班状态显示屏");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 630, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 600, 730);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");   
		time = new Timer(1000,new ActionListener() {   
		 
		public void actionPerformed(ActionEvent arg0) {  
			lblNewLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"(当地时间)"+","+airportlocation+"机场");
		}  
		});  
		time.start();   
		lblNewLabel.setBounds(0, 0, 600, 30);
		panel.add(lblNewLabel);
		
		nowtime = Calendar.getInstance();  //当前时间
		String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(nowtime.getTime()); 
	    nowtime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str));
		
		
		int i,j;
		FlightEntry<Flight> flight;
		String[][] comebiao=new String[100][4];
		String[][] tobiao=new String[100][4];
		
		Iterator<FlightEntry<Flight>> iterator=comeentry.iterator();
		while(iterator.hasNext()) {
			FlightEntry<Flight> pe=iterator.next();
			if(((pe.gettimeslot().get(pe.getlocations().indexOf(airportlocation)-1).getendtime().getTime().getTime()-nowtime.getTime().getTime())/1000)>3600
					||((nowtime.getTime().getTime()-pe.gettimeslot().get(pe.getlocations().indexOf(airportlocation)-1).getendtime().getTime().getTime())/1000)>3600) {
					iterator.remove();
			}
		}
			
		for(i=0;i<100;i++) {
		    for(j=0;j<4;j++) {
				comebiao[i][j]=null;
			}
		}
		
		for(i=0;i<comeentry.size();i++) {
			flight=comeentry.get(i);
			if(flight!=null) {
				comebiao[i][0]=(new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(flight.gettimeslot().get(flight.getlocations().indexOf(airportlocation)-1).getendtime().getTime());  ;
				comebiao[i][1]=(String) flight.getplanningentryname();
			    comebiao[i][2]=flight.getlocations().get(0).getlocationname()+"—"+flight.getlocations().get(flight.getlocations().size()-1).getlocationname();
			    comebiao[i][3]=((FlightState)flight.getcurrentstate()).getflightstate();
			}
		}
		
		JLabel lblNewLabel_1 = new JLabel("                                  抵达航班");
		lblNewLabel_1.setBounds(0, 30, 600, 30);
		panel.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 60, 600, 200);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			comebiao,
			new String[] {
				"计划降落时间", "航班号", "起始和终点", "状态"
			}
		));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_2 = new JLabel("                                  出发航班");
		lblNewLabel_2.setBounds(0, 280, 600, 30);
		panel.add(lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 310, 600, 200);
		panel.add(scrollPane_1);
		
		Iterator<FlightEntry<Flight>> iterator1=toentry.iterator();
		while(iterator1.hasNext()) {
			FlightEntry<Flight> lpe=iterator1.next();
			if(((lpe.gettimeslot().get(lpe.getlocations().indexOf(airportlocation)).getbegintime().getTime().getTime()-nowtime.getTime().getTime())/1000)>3600
					||((nowtime.getTime().getTime()-lpe.gettimeslot().get(lpe.getlocations().indexOf(airportlocation)).getbegintime().getTime().getTime())/1000)>3600) {
					iterator1.remove();
				}
		}
			
		for(i=0;i<100;i++) {
		    for(j=0;j<4;j++) {
				tobiao[i][j]=null;
			}
		}
		
		for(i=0;i<toentry.size();i++) {
			flight=toentry.get(i);
			if(flight!=null) {
				tobiao[i][0]=(new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(flight.gettimeslot().get(flight.getlocations().indexOf(airportlocation)).getbegintime().getTime());  ;
				tobiao[i][1]=(String) flight.getplanningentryname();
			    tobiao[i][2]=flight.getlocations().get(0).getlocationname()+"—"+flight.getlocations().get(flight.getlocations().size()-1).getlocationname();
			    tobiao[i][3]=((FlightState)flight.getcurrentstate()).getflightstate();
			}
		}
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			tobiao,
			new String[] {
					"计划起飞时间", "航班号", "起始地", "状态"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(150);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(150);
		scrollPane_1.setViewportView(table_1);
		
		List<FlightEntry<Flight>> allentry=new ArrayList<>();
		allentry.addAll(comeentry);
		allentry.addAll(toentry);
		return allentry;
	}
}

