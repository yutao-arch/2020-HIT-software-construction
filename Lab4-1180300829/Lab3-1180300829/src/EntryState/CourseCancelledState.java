package EntryState;


public class CourseCancelledState implements CourseState{

	static CourseCancelledState instance=new CourseCancelledState();
    private String nowstate="课程已取消(Cancelled)";
 
    // inmutability类
 	// Abstraction function:
 	// AF(instance)=该状态的类
 	// AF(nowstate)=描述计划项当前状态的字符串
 	// Representation invariant:
 	// 对输入的指令进行检查，不符合要求抛出异常
 	// Safety from rep exposure:
 	// 将instance,nowstate设置为private
    
	private CourseCancelledState() {
	};
	
	/**
	 * 改变当前状态
	 * @param c 改变指令
	 * @return 新的状态
	 */
	@Override
	public CourseState move(char c) {
		return null;
	}

	/**
	 * 得到当前状态
	 * @return 描述当前状态的字符串
	 */
	@Override
	public String getcoursestate() {
		return this.nowstate;
	}

	
}
