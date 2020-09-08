package P3;

import java.util.List;
import java.util.ArrayList;

public class Person {
	private String myname;  //本人名字
	private List<Person> friendofmyname;   //本人的朋友
	private static ArrayList<String> hisallperson =new ArrayList<String>(); //定义全局名字表，储存本人所有朋友的名字

	public Person(String name) {     //构造方法
		if(hisallperson.contains(name)) {       //当名字在名字表中已存在时
			System.out.println("此名字已存在");
			System.exit(0);
		}
		else {   //有新名字时
			this.myname=name;
			friendofmyname=new ArrayList<>();
			hisallperson.add(name);
		}
	}
	
	public void addnewfriend(Person one) {  //增加本人的新朋友
		this.friendofmyname.add(one);
	}
	
	public String getmyname() {   //得到本人名字
		return this.myname;
	}
	
	public List<Person> gethisfriend(){  //得到本人的朋友列表
		return this.friendofmyname;
	}
}
