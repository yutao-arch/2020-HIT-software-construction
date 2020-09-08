package PlanningEntryAPIs;

import java.util.ArrayList;
import java.util.List;

import PlanningEntry.CourseEntry;
import PlanningEntry.FlightEntry;
import PlanningEntry.PlanningEntry;
import PlanningEntry.TrainEntry;
import Resource.Teacher;
import Timeslot.Timeslot;

public class Strategy1checkLocationConflict implements StrategycheckLocationConflict{

	/**
	 * 检测一组计划项之间是否存在位置独占冲突
	 * @param entries 计划项集合
	 * @return 是否存在位置独占冲突
	 */
	@Override
	public boolean checkLocationConflict(List<PlanningEntry> entries) {
		int i,j;
		Timeslot b1,b2;
		String l1,l2,m1,m2;
		boolean flag;
		if(entries.get(0) instanceof CourseEntry) {
			flag=false;
			List<CourseEntry<Teacher>> courseentries=new ArrayList<>();  //向下转型后的list
			for(i=0;i<entries.size();i++) {
				courseentries.add((CourseEntry<Teacher>)entries.get(i));
			}
			for(i=0;i<courseentries.size()-1;i++) {   //将储存Entry的list每一项与它后面的所有项的地址和时间进行比较，判断是否时间冲突
				b1=courseentries.get(i).gettimeslot();
				m1=(String) courseentries.get(i).getplanningentryname();
				l1=courseentries.get(i).getlocations().getlocationname();
				for(j=i+1;j<courseentries.size();j++) {
					b2=courseentries.get(j).gettimeslot();
					m2=(String) courseentries.get(j).getplanningentryname();
					l2=courseentries.get(j).getlocations().getlocationname();
					if(l1.equals(l2)) {
						if(!(b1.getendtime().compareTo(b2.getbegintime())<=0||b2.getendtime().compareTo(b1.getbegintime())<=0)) {
							System.out.println("计划项“"+m1+"”与计划项“"+m2+"”存在时间冲突且使用了同一间教室"+"“"+l2+"”");
							flag=true;
						}
					}
				}
			}
			if(flag==true) {
				return true;
			}
			else {
				System.out.println("没有计划项在重叠时间使用同一教室");
				return false;
			}
		}
		if(entries.get(0) instanceof FlightEntry) {
			System.out.println("机场位置可共享，不存在冲突");
			return false;
		}
		if(entries.get(0) instanceof TrainEntry) {
			System.out.println("高铁站位置可共享，不存在冲突");
			return false;
		}
	   System.out.println("请输入正确信息");
	   return false;
	}

}
