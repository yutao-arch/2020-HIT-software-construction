package PlanningEntry;

public interface PlanningEntry<R> {

	/**
	 * 创造一个计划项
	 * @return 创造的计划项
	 */
	public CommonPlanningEntry<R> createplanningentry();
	
	/**
	 * 启动一个计划项
	 * @param a 启动的指令
	 * @return 是否成功启动
	 */
	public boolean launch(R a);
	
	/**
	 * 取消一个计划项
	 * @param a 取消的指令
	 * @return 是否成功取消
	 */
	public boolean cancel(R a);
	
	/**
	 * 结束一个计划项
	 * @param a 结束的指令
	 * @return 是否成功结束
	 */
	public boolean finish(R a);
	
	/**
	 * 得到计划项的名字
	 * @return 计划项的名字
	 */
	public String getplanningentryname();
	
	/**
	 * 得到当前计划项的状态
	 * @return
	 */
	public R getcurrentstate();
	
	/**
	 * 设置计划项的名字
	 * @param planningentryname 待设置的名字
	 */
	public void setplanningentryname(String planningentryname);

	/**
	 * 设置计划项当前的状态
	 * @param currentstate 待设置的状态
	 */
	public void setcurrentstate(R currentstate);

}
