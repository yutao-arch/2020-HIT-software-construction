package EntryState;



public class CourseAllocatedState implements CourseState{

	static CourseAllocatedState instance=new CourseAllocatedState();
    private String nowstate="课程已分配老师(Allocated)";
 
    // inmutability类
 	// Abstraction function:
 	// AF(instance)=该状态的类
 	// AF(nowstate)=描述计划项当前状态的字符串
 	// Representation invariant:
 	// 对输入的指令进行检查，不符合要求抛出异常
 	// Safety from rep exposure:
 	// 将instance,nowstate设置为private
    
	private CourseAllocatedState() {
	};
	
	/**
	 * 改变当前状态
	 * @param c 改变指令
	 * @return 新的状态
	 */
	@Override
	public CourseState move(char c) {
		switch(c) {
		case 'a':
			return CourseRunningState.instance;
		case 'b':
			return CourseCancelledState.instance;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 得到当前状态
	 * @return 描述当前状态的字符串
	 */
	@Override
	public String getcoursestate() {
		return nowstate;
	}

	
}
