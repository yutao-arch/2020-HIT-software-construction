package EntryState;



public class FlightAllocatedState implements FlightState{

    static FlightAllocatedState instance=new FlightAllocatedState();
    private String nowstate="航班已分配飞机(Allocated)";
 
    // inmutability类
 	// Abstraction function:
 	// AF(instance)=该状态的类
 	// AF(nowstate)=描述计划项当前状态的字符串
 	// Representation invariant:
 	// 对输入的指令进行检查，不符合要求抛出异常
 	// Safety from rep exposure:
 	// 将instance,nowstate设置为private
    
	private FlightAllocatedState() {
	};
	
	/**
	 * 改变当前状态
	 * @param c 改变指令
	 * @return 新的状态
	 */
	@Override
	public FlightState move(char c) {
		switch(c) {
		case 'a':
			return FlightRunningState.instance;
		case 'b':
			return FlightCancelledState.instance;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 得到当前状态
	 * @return 描述当前状态的字符串
	 */
	@Override
	public String getflightstate() {
		return this.nowstate;
	}


}
