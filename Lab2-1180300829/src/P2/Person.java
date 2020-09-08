package P2;

import java.util.ArrayList;


public class Person {
	private String myname;  //本人名字
	private static ArrayList<String> allperson =new ArrayList<String>(); //储存所有人的名字
	
	 // Abstraction function:
    // AF(myname)=人的名字
    // Representation invariant:
    // 没有重复名字
    // Safety from rep exposure:
    // 将myname设置为private
    // 由于myname是inmutable，所以在返回时不需要进行defensive copies
	
	public Person(String name) {     //构造方法
		if(allperson.contains(name)) {       //当名字在名字表中已存在时
			System.out.println("此名字已存在");
		}
		else {   //有新名字时
			this.myname=name;
			allperson.add(name);
		}
	}
	
	/**
	 * 返回本人名字
     * @return 本人名字
     */
	public String getmyname() {  
		return this.myname;
	}
}
