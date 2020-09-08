package Resource;

import Exception.LessThanZeroException;

public class Carriage {

	private final String carriagenumber;  //车厢编号
	private final String carriagetype;    //车厢类型
	private final int carriageallseat;    //定员数
	private final String carriagbirth;    //出厂年份
	
	// immutability类
	// Abstraction function:
    // AF(carriagenumber)=车厢编号
	// AF(carriagetype)=车厢类型
	// AF(carriageallseat)=定员数
	// AF(carriagbirth)=出厂年份
	// Representation invariant:
    // 定员数是正数,车厢编号,车厢类型,定员数不为空
	// Safety from rep exposure:
	// 将carriagenumber,carriagetype,carriageallseat,carriagbirth设置为private final
	
	// TODO checkRep
    private void checkRep() {  //定员数是正数,车厢编号,车厢类型,定员数不为空
          assert carriageallseat>0:"定员数不为正数\n";
          assert carriagenumber!=null;
          assert carriagetype!=null;
          assert carriagbirth!=null;
    }
	
	/**
	 * 初始化构造方法
	 * @param a 车厢编号
	 * @param b 车厢类型
	 * @param c 定员数:正数
	 * @param d 出厂年份
	 * @throws LessThanZeroException 定员数为负数
	 */
	public Carriage(String a,String b,int c,String d) throws LessThanZeroException {
		if(c<=0) {
			throw new LessThanZeroException();
		}
		this.carriagenumber=a;
		this.carriagetype=b;
		this.carriageallseat=c;
		this.carriagbirth=d;
		checkRep();
	}
	
	/**
	 * 返回车厢编号
	 * @return 车厢编号
	 */
	public String getcarriagenumber() {
		checkRep();
		return carriagenumber;
	}
	
	/**
	 * 返回车厢类型
	 * @return 车厢类型
	 */
	public String getcarriagetype() {
		checkRep();
		return carriagetype;
	}
	
	/**
	 * 返回定员数
	 * @return 定员数
	 */
	public int getcarriageallseat() {
		checkRep();
		return carriageallseat;
	}
	
	/**
	 * 返回车厢出厂年份
	 * @return 车厢出厂年份
	 */
	public String getcarriagbirth() {
		checkRep();
		return carriagbirth;
	}

	/**
	 * 重写hashcode方法
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carriagbirth == null) ? 0 : carriagbirth.hashCode());
		result = prime * result + carriageallseat;
		result = prime * result + ((carriagenumber == null) ? 0 : carriagenumber.hashCode());
		result = prime * result + ((carriagetype == null) ? 0 : carriagetype.hashCode());
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
		Carriage other = (Carriage) obj;
		if (carriagbirth == null) {
			if (other.carriagbirth != null)
				return false;
		} else if (!carriagbirth.equals(other.carriagbirth))
			return false;
		if (carriageallseat != other.carriageallseat)
			return false;
		if (carriagenumber == null) {
			if (other.carriagenumber != null)
				return false;
		} else if (!carriagenumber.equals(other.carriagenumber))
			return false;
		if (carriagetype == null) {
			if (other.carriagetype != null)
				return false;
		} else if (!carriagetype.equals(other.carriagetype))
			return false;
		return true;
	}


}
