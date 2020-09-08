package EntryState;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseStateTest {

	/* Testing strategy
	 * 通过状态的转换中完成所有Course的state的方法的测试
     */
	@Test
	public void allmethodtest() {
		CourseState state=CourseWaitingState.instance;
	    assertEquals("课程未分配老师(Waiting)",state.getcoursestate());
	    
	    state=state.move('a');
	    assertEquals("课程已分配老师(Allocated)",state.getcoursestate());
	    
	    state=state.move('a');
	    assertEquals("课程已开始上课(Running)",state.getcoursestate());
	    
	    state=state.move('a');
	    assertEquals("课程已下课(Ended)",state.getcoursestate());
	    
	    assertEquals(null,state.move('a'));
	    
	    state=CourseWaitingState.instance;
	    state=state.move('b');
	    assertEquals("课程已取消(Cancelled)",state.getcoursestate());
	    
	    assertEquals(null,state.move('a'));
	    
	    state=CourseWaitingState.instance;
	    state=state.move('a');
	    state=state.move('b');
	    assertEquals("课程已取消(Cancelled)",state.getcoursestate());
	}

}
