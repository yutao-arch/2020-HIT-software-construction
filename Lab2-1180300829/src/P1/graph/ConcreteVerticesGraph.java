/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    // AF(vertices)={图中所有的vertices[i]|0<=i<vertices.sizes()}
    // Representation invariant:
    // vertices中不能有重复点
    // Safety from rep exposure:
    // 设置vertices为private
    // 由于vertices是mutable，所以在返回他们时进行了defensive copies
    
    // TODO constructor
    public ConcreteVerticesGraph() {
    }
    // TODO checkRep
    /**
	 * 测试表示不变性
	 */
	private void checkRep() {
		  Set<Vertex<L>> testvertices = new HashSet<>();
	      testvertices.addAll(vertices);
	      assert testvertices.size() == vertices.size();
	}
    
	 /**
     * Add a vertex to this graph.
     * 
     * @param vertex label for the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    @Override public boolean add(L vertex) {  
    	for(Vertex<L> m:vertices) {
    		if(m.getmark().equals(vertex)) {
    			System.out.println("待加入点已经存在\n");   		
        		return false;
    		}
    	}
    	Vertex<L> xin=new Vertex<L>(vertex);
    	vertices.add(xin);
    	checkRep();
    	return true;
        //throw new RuntimeException("not implemented");
    }
    
    /**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    @Override public int set(L source, L target, int weight) {
    	int fan=0;
    	this.add(source);
		this.add(target);
    	for(Vertex<L> l:vertices) {
			if(l.getmark().equals(source)) {
				fan=l.addtarget(target,weight);  //对于weight的判断在此函数中
			}
			if(l.getmark().equals(target)) {
				fan=l.addsource(source,weight);  //对于weight的判断在此函数中  
			}
		}
    	checkRep();
    	return fan;
    	
    	
        //throw new RuntimeException("not implemented");
    }
    
    /**
     * Remove a vertex from this graph; any edges to or from the vertex are
     * also removed.
     * 
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)
     */
    @Override public boolean remove(L vertex) {
    	Iterator<Vertex<L>> it=vertices.iterator();
    	while(it.hasNext()) {
    		Vertex<L> c=it.next();
    		if(c.getmark().equals(vertex)) {
    			it.remove();
    			checkRep();
    			return true;
    		}
    		else {
    			if (c.getsource().containsKey(vertex)) {
					c.removesource(vertex);
				}
				if (c.gettarget().containsKey(vertex)) {
					c.removetarget(vertex);
				}
    		}
    	}
    	checkRep();
    	return false;
        //throw new RuntimeException("not implemented");
    }
    
    /**
     * Get all the vertices in this graph.
     * 
     * @return the set of labels of vertices in this graph
     */
    @Override public Set<L> vertices() {
    	Set<L> wang=new HashSet<>();
    	L a=null;
    	for(Vertex<L> d:vertices) {
    		a=d.getmark();
    		wang.add(a);
    	}
    	return wang;
        //throw new RuntimeException("not implemented");
    }
    
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer> xiaocui=new HashMap<>();
        for(Vertex<L> f:vertices) {
        	if(f.getmark().equals(target)) {
        		xiaocui=f.getsource();
        		break;
        	}
        }
        Map<L,Integer> cuixiao=new HashMap<L,Integer>(xiaocui);
        checkRep();
        return cuixiao;
    	//throw new RuntimeException("not implemented");
    }
    
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key
     */
    @Override public Map<L, Integer> targets(L source) {
    	Map<L,Integer> xiaotao=new HashMap<>();
        for(Vertex<L> ff:vertices) {
        	if(ff.getmark().equals(source)) {
        		xiaotao=ff.gettarget();
        		break;
        	}
        }
        Map<L,Integer> taoxiao=new HashMap<L,Integer>(xiaotao);
        checkRep();
        return taoxiao;
    	//throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    /**
     * @return 返回整个图连成的字符串
     */
    @Override public String toString() {
    	String mm = "";
		for (Vertex<L> p : vertices) {
			mm = mm + p.toString();
		}
		return mm;
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // TODO fields
	  
	private final L mark;  //储存顶点的名字
	private final Map<L,Integer> allsource; //储存该顶点的源顶点及边的长度的源点Map
	private final Map<L,Integer> alltarget; //储存该顶点终点及边的长度的终点Map
    // Abstraction function:
    // AF(mark)=点的名字
	// AF(allsource)=指向这个点的所有点和边
	// AF(alltarget)=这个点指向的所有点和边
    // Representation invariant:
    // 每个边的权值应该大于0
    // Safety from rep exposure:
    // 将mark,allsource,alltarget设置为private
	
	 // TODO constructor
	/**
	 * 初始化构造方法，用点的名字创建
	 * @param vertex
	 */
	public Vertex(L vertex) {
		this.mark=vertex;
		this.allsource=new HashMap<>();
		this.alltarget=new HashMap<>();	
	}
   
	// TODO checkRep
	/**
	 * 检查表示不变性
	 */
	private void checkRep() {
		Set<L> one = allsource.keySet();
		if (one != null) {
			Iterator<L> iterator = one.iterator();
			while (iterator.hasNext()) {
				L key = iterator.next();
				Integer value = allsource.get(key);
				assert value > 0;
			}
		}
		Set<L> two = alltarget.keySet();
		if (two != null) {
			Iterator<L> iterator = two.iterator();
			while (iterator.hasNext()) {
				L key = iterator.next();
				Integer value = alltarget.get(key);
				assert value > 0;
			}
		}
	}   
  
	/**
	 * 返回点的名字mark
	 * @return 点的名字
	 */
	public L getmark() {
		return mark;
	}
	
	/**
	 * 返回能到达该点的所有点和边构成的Map
	 * @return 到达某个点的所有点和边构成的Map
	 */
	public Map<L,Integer> getsource(){
		return new HashMap<L,Integer>(allsource);
	}
	
	/**
	 * 返回某个点能到达的所有点和边构成的Map
	 * @return 到达某个点的所有点和边构成的Map
	 */
	public Map<L,Integer> gettarget(){
		return new HashMap<L,Integer>(alltarget);
	}
	
	/**
	 * 在源点Map中加入某源点，若weight不为0，则将其加入source中(若源点已存在，则更新其weight并返回原weight，不存在则直接构建新点并返回0)
	 * 若weight为0，则移除源点(不存在返回0，存在返回原weight)
	 * @param newsource 待加入的源点
	 * @param weight  源点到此点的长度
	 * @return 旧的边长，没有则返回0
	 */
	public int addsource(L newsource,int weight) {
		Integer preweight=0;
		if(weight>0) {
			preweight=allsource.put(newsource,weight);
			if(preweight==null) {
				preweight=0;
			}
		}
		if(weight==0){
			preweight=this.removesource(newsource);
		}
		if(weight<0) {
			System.out.println("权值不能为负");
    		return -1;
		}
		checkRep();
		return preweight;
	}
	
	/**
	 * 在终点Map中加入某终点，若weight不为0，则将其加入target中(若终点已存在，则更新其weight并返回原weight，不存在则直接构建新点并返回0)
	 * 若weight为0，则移除终点(不存在返回0，存在返回原weight)
	 * @param newtarget 待加入的终点
	 * @param weight  终点到此点的长度
	 * @return 旧的边长，没有则返回0
	 */
	public int addtarget(L newtarget,int weight) {
		Integer preweight=0;
		if(weight>0) {
			preweight=alltarget.put(newtarget,weight);
			if(preweight==null) {
				preweight=0;
			}
		}
		if(weight==0){
			preweight=this.removetarget(newtarget);
		}
		if(weight<0) {
			System.out.println("权值不能为负");
    		return -1;
		}
		checkRep();
		return preweight;
	}
	
	/**
	 * 在源点表中删除某起始点，并返回旧的边长
	 * @param newsource 某起始点
	 * @return  旧的边长，没有则返回0
	 */
	public int removesource(L newsource) {
		Integer newweight=allsource.remove(newsource);
		if(newweight==null) {
			return 0;
		}
		else {
			return newweight;
		}
	}
	
	/**
	 * 在终点表中删除某终点，并返回旧的边长
	 * @param newtarget 某终点
	 * @return  旧的边长，没有则返回0
	 */
	public int removetarget(L newtarget) {
		Integer newweight=alltarget.remove(newtarget);
		if(newweight==null) {
			return 0;
		}
		else {
			return newweight;
		}
	}
	
	// TODO toString()
	/**
	 * 得到一个点的字符串表示
	 */
	@Override
	public String toString() {
		return "点"+this.mark.toString()+"，指向它的点和边的权值为："+this.allsource.toString()+"，它指向的点和边的权值为："+this.alltarget.toString()+"\n";
	}
}
