package EntryState;

import static org.junit.Assert.*;

import org.junit.Test;

public class CourseContextTest {
		
	/* Testing strategy
	 * 测试CourseContext类中的所有方法即可
     */
	@Test
	public void allmethodtest() {
		CourseWaitingState state=CourseWaitingState.instance;
		CourseContext temp=new CourseContext(state);
		assertEquals(state,temp.getstate());
		assert temp.move('a')!=null;
	}

}
