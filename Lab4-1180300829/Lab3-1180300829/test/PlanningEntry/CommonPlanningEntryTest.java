package PlanningEntry;

import static org.junit.Assert.*;

import org.junit.Test;

import EntryState.CourseWaitingState;
import Resource.Carriage;

public class CommonPlanningEntryTest {

	/* Testing strategy
	 * 由于CommonPlanningEntry方法很简单，不需要分类
	 * 直接创建一个CommonPlanningEntry类，完成对其中所有方法的测试
     */
	@Test
	public void allmethodstest() {
		CommonPlanningEntry tempentry=new CommonPlanningEntry<>();
		tempentry=tempentry.createplanningentry();
		tempentry.setplanningentryname("软件构造");
		CourseWaitingState haha=CourseWaitingState.instance;
		tempentry.setcurrentstate(haha);
		
		assertEquals(true,tempentry.launch("启动"));
		assertEquals(false,tempentry.launch("替他"));
		assertEquals(true,tempentry.cancel("取消"));
		assertEquals(false,tempentry.cancel("其他"));
		assertEquals(true,tempentry.finish("结束"));
		assertEquals(false,tempentry.finish("其他"));
		
		assertEquals("软件构造",tempentry.getplanningentryname());
		assertEquals(haha,tempentry.getcurrentstate());
	}

}
