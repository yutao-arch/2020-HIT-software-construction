/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    /* Testing strategy
     * 
     */
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    

    /* Testing strategy
     * 按加入点的类型划分：点已存在，点不存在(重复输入)
     * 覆盖每个取值如下：
     */
    @Test
    public void testAdd() {
    	Graph<String> graph =emptyInstance();
    	String a="yu";
    	String b="tao";
    	graph.add(a);
    	graph.add(b);
    	assertEquals(true,graph.vertices().contains(a));
    	assertEquals(true,graph.vertices().contains(b));
    	assertEquals(false,graph.add(a));
    }
    
    /* Testing strategy
     * 按加入边的类型划分：边已经存在，边不存在
     * 按输入边的权值划分：权值为0，权值大于0，权值小于0
     * 覆盖每个取值如下：
     */
    @Test
    public void testSet() {
    	Graph<String> graph =emptyInstance();
    	assertEquals(0,graph.set("a", "b", 5));
    	assertEquals(0,graph.set("b", "c", 4));
    	assertEquals(5,graph.set("a", "b", 6));
    	assertEquals(6,graph.set("a", "b", 0));
    	assertEquals(0,graph.set("a", "c", 0));
    	assertEquals(-1,graph.set("a", "b", -3));	
    }
    
    /* Testing strategy
     * 按加入点的类型划分：点已经存在，点不存在
     * 覆盖每个取值如下：
     */
    @Test
    public void testRemove() {
    	Graph<String> graph =emptyInstance();
    	String a="yu";
    	String b="tao";
    	String c="shuai";
    	graph.add(a);
    	graph.add(b);
    	graph.add(c);
    	graph.set(a, b, 5);
    	graph.set(b, c, 4);
    	assertEquals(true,graph.remove(a));
    	assertEquals(false,graph.remove(a));  	
    }
    
    /* Testing strategy
     * 两种情况：空图，不是空图
     */
    @Test
    public void testVertices() {
    	testAdd();
    	Graph<String> graph =emptyInstance();
    	String a="yu";
    	String b="tao";
    	String c="shuai";
    	graph.add(a);
    	graph.add(b);
    	graph.add(c);
    	assertEquals(true,graph.vertices().contains(a));
    	assertEquals(true,graph.vertices().contains(b));
    	assertEquals(true,graph.vertices().contains(c));
    }
    
    /* Testing strategy
     * 两种情况：边已经存在，边不存在
     */
    @Test
    public void testSources() {
    	testAdd();
        testSet();
    	Graph<String> graph =emptyInstance();
    	String a="yu";
    	String b="tao";
    	String c="shuai";
    	graph.add(a);
    	graph.add(b);
    	graph.add(c);
    	graph.set(a, b, 5);
    	graph.set(b, c, 4);
    	graph.set(a, c, 3);
    	Map<String,Integer> map=new HashMap<String,Integer>();
    	map.put(a,3);
    	map.put(b,4);
    	assertEquals(map,graph.sources(c)); 	
    }
    
    /* Testing strategy
     *  两种情况：边已经存在，边不存在
     */
    @Test
    public void testTargets() {
    	testAdd();
        testSet();
    	Graph<String> graph =emptyInstance();
    	String a="yu";
    	String b="tao";
    	String c="shuai";
    	graph.add(a);
    	graph.add(b);
    	graph.add(c);
    	graph.set(a, b, 5);
    	graph.set(b, c, 4);
    	graph.set(a, c, 3);
    	Map<String,Integer> map=new HashMap<String,Integer>();
    	map.put(c,3);
    	map.put(b,5);
    	assertEquals(map,graph.targets(a)); 	
    }
}
