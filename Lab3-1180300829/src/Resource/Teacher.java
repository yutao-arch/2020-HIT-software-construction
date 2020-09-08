package Resource;

public class Teacher {

	private final String idnumber;       //教师身份证号
	private final String teachername;    //教师姓名
	private final String teachergender;  //教师性别
	private final String teachertitle;   //教师职称
	
	// immutability类
	// Abstraction function:
    // AF(idnumber)=教师身份证号
	// AF(teachername)=教师姓名
	// AF(teachergender)=教师性别
	// AF(teachertitle)=教师职称
	// Representation invariant:
    // 性别只有两种
	// Safety from rep exposure:
	// 将idnumber,teachername,teachergender,teachertitle设置为private final
	
	
	/**
	 * 初始化构造方法
	 * @param a 教师身份证号
	 * @param b 教师姓名
	 * @param c 教师性别
	 * @param d 教师职称
	 */
	public Teacher(String a,String b,String c,String d) {
		this.idnumber=a;
		this.teachername=b;
		this.teachergender=c;
		this.teachertitle=d;
	}
	
	/**
	 * 返回教师身份证号
	 * @return 教师身份证号
	 */
	public String getidnumber() {
		return idnumber;
	}
	
	/**
	 * 返回教师姓名
	 * @return 教师姓名
	 */
	public String getteachername() {
		return teachername;
	}
	
	/**
	 * 返回教师性别
	 * @return 教师性别
	 */
	public String getteachergender() {
		return teachergender;
	}
	
	/**
	 * 返回教师职称
	 * @return 教师职称
	 */
	public String getteachertitle() {
		return teachertitle;
	}

	/**
	 * 重写hashcode方法
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idnumber == null) ? 0 : idnumber.hashCode());
		result = prime * result + ((teachergender == null) ? 0 : teachergender.hashCode());
		result = prime * result + ((teachername == null) ? 0 : teachername.hashCode());
		result = prime * result + ((teachertitle == null) ? 0 : teachertitle.hashCode());
		return result;
	}

	/**
	 * 重写equals方法
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (idnumber == null) {
			if (other.idnumber != null)
				return false;
		} else if (!idnumber.equals(other.idnumber))
			return false;
		if (teachergender == null) {
			if (other.teachergender != null)
				return false;
		} else if (!teachergender.equals(other.teachergender))
			return false;
		if (teachername == null) {
			if (other.teachername != null)
				return false;
		} else if (!teachername.equals(other.teachername))
			return false;
		if (teachertitle == null) {
			if (other.teachertitle != null)
				return false;
		} else if (!teachertitle.equals(other.teachertitle))
			return false;
		return true;
	}
}
