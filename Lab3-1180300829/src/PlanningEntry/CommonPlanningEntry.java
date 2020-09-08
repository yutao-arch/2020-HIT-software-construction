package PlanningEntry;


public class CommonPlanningEntry<R> implements PlanningEntry<R> {

	private String planningentryname; //计划项名字
	private R currentstate;  //计划项当前状态
	
	// mutability类
	// Abstraction function:
	// AF(planningentryname)=计划项名字
	// AF(currentstate)=计划项当前状态
	// Representation invariant:
	// 对输入的指令进行检查
	// Safety from rep exposure:
	// 将planningentryname,currentstate设置为private
	
	/**
	 * 构造方法
	 */
	public CommonPlanningEntry(){
	}
	
	/**
	 * 设置计划项的名字
	 * @param planningentryname 待设置的名字
	 */
	@Override
	public void setplanningentryname(String planningentryname) {
		this.planningentryname=planningentryname;
	}
	
	/**
	 * 设置计划项当前的状态
	 * @param currentstate 待设置的状态
	 */
	@Override
	public void setcurrentstate(R currentstate) {
		this.currentstate=currentstate;
	}
	
	/**
	 * 创造一个计划项
	 * @return 创造的计划项
	 */
	@Override
	public CommonPlanningEntry<R> createplanningentry() {
		return new CommonPlanningEntry<R>();
	}

	/**
	 * 启动一个计划项
	 * @param a 启动的指令
	 * @return 是否成功启动
	 */
	@Override
	public boolean launch(R a) {
		if(a.equals("启动")) {
			System.out.println("计划项已启动");
			return true;
		}
		else {
			System.out.println("请输入正确指令");
			return false;
		}
	}

	/**
	 * 取消一个计划项
	 * @param a 取消的指令
	 * @return 是否成功取消
	 */
	@Override
	public boolean cancel(R a) {
		if(a.equals("取消")) {
			System.out.println("计划项已取消");
			return true;
		}
		else {
			System.out.println("请输入正确指令");
			return false;
		}
	}

	/**
	 * 结束一个计划项
	 * @param a 结束的指令
	 * @return 是否成功结束
	 */
	@Override
	public boolean finish(R a) {
		if(a.equals("结束")) {
			System.out.println("计划项已结束");
			return true;
		}
		else {
			System.out.println("请输入正确指令");
			return false;
		}
	}

	/**
	 * 得到计划项的名字
	 * @return 计划项的名字
	 */
	@Override
	public String getplanningentryname() {
		return planningentryname;
	}

	/**
	 * 得到当前计划项的状态
	 * @return
	 */
	@Override
	public R getcurrentstate() {
		return currentstate;
	}

}
