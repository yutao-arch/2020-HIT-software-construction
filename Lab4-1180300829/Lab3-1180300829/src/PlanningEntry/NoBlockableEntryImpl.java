package PlanningEntry;

import Timeslot.Timeslot;

public class NoBlockableEntryImpl implements Cloneable,NoBlockableEntry {

	private Timeslot mytime;
	
	// mutability类
	// Abstraction function:
	// AF(mytime)=该时间对
	// Representation invariant:
	// 输入的时间对不能为空
	// Safety from rep exposure:
	// 将mytime设置为private

	// TODO checkRep
    private void checkRep() {  //保证时间对不为空
    	assert mytime!=null;
    }
	
	/**
	 * 判断该类是否可阻塞
	 * @return 该类是否可阻塞
	 */
	@Override
	public boolean whetherblockable() {
		System.out.println("不可阻塞");
		return false;
	}

	/**
	 * 设置该时间对
	 * @param mytime 时间对
	 * @return 是否成功设置该时间对
	 */
	@Override
	public boolean settimeslot(Timeslot time) {
		if(mytime==null){
			this.mytime=time;
			System.out.println("时间设置成功");
			checkRep();
			return true;
		}
		System.out.println("时间只能设置一次");
		return false;
	}

	/**
	 * 得到该时间对
	 * @return 该时间对
	 */
	@Override
	public Timeslot gettimeslot() {
		checkRep();
		return mytime;
	}
	
	@Override
	  public NoBlockableEntryImpl clone() { 
		NoBlockableEntryImpl stu = null; 
	    try{ 
	      stu = (NoBlockableEntryImpl)super.clone(); 
	    }catch(CloneNotSupportedException e) { 
	      e.printStackTrace(); 
	    } 
	    return stu; 
	  } 

}
