package P3;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;



public class MyChessAndGoGame {
	public static Game mygame;
	private static String tempchess[][]=new String[8][8];
	private static String tempgo[][]=new String[19][19];
	
	// Abstraction function:
    // AF(mygame)=国际象棋或围棋游戏
	// AF(tempchess)=国际象棋棋盘
	// AF(tempgo)=围棋棋盘
    // Representation invariant:
    // 用户输入的必须是"chess"或者"go"
    // Safety from rep exposure:
    // 将mygame,tempchess,tempgo设置为private

	/**
	 * 初始化棋盘的棋子全为o
	 */
	public static void empty() {
		int i, j;
		for(i=0;i<8;i++) {
			for(j=0;j<8;j++) {
				tempchess[i][j]="o";
			}
		}
		for(i=0;i<19;i++) {
			for(j=0;j<19;j++) {
				tempgo[i][j]="o";
			}
		}
	}
	
	/**
	 * 国际象棋菜单
	 */
	public static void chessmenu(){
		System.out.println("请选择你的操作(输入数字标号即可,输入end结束游戏)：");
		System.out.println("1.移动棋盘上某个位置的棋子至新位置");
		System.out.println("2.吃子（使用己方棋子吃掉对手棋子）");
		System.out.println("3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）");
		System.out.println("4.计算两个玩家分别在棋盘上的棋子总数");
		System.out.println("5.跳过");
		System.out.println("end.结束此次对局");
		
	}

	/**
	 * 围棋菜单
	 */
	public static void gomenu() {
		System.out.println("请选择你的操作(输入数字标号即可,输入end结束游戏)：");
		System.out.println("1.将尚未在棋盘上的一颗棋子放在棋盘上的指定位置");
		System.out.println("2.提子（移除对手的棋子）");
		System.out.println("3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）");
		System.out.println("4.计算两个玩家在棋盘上的棋子总数");
		System.out.println("5.跳过");
		System.out.println("end.结束此次对局");
	}
	
	/**
	 * 打印国际象棋的棋盘
	 */
	public static void printchess() {
		Set<Piece> mm=mygame.getmyboard().getmyboard();
		int i,j;
		empty();
		Iterator<Piece> temp=mm.iterator();
		while(temp.hasNext()) {
			Piece a=temp.next();
			tempchess[a.getpieceposition().gety()][a.getpieceposition().getx()]=a.getpiecename();
		}
		System.out.println("棋盘如下：");
		for(i=0;i<8;i++) {
			for(j=0;j<8;j++) {
				System.out.print(tempchess[i][j]+"\t");
			}
			System.out.print("\n");
		}	
	}
	
	/**
	 * 打印围棋的棋盘
	 */
	public static void printgo() {
		Set<Piece> mm=mygame.getmyboard().getmyboard();
		int i,j;
		empty();
		Iterator<Piece> temp=mm.iterator();
		while(temp.hasNext()) {
			Piece a=temp.next();
			tempgo[a.getpieceposition().gety()][a.getpieceposition().getx()]=a.getpiecename();
		}
		System.out.println("棋盘如下：");
		for(i=0;i<19;i++) {
			for(j=0;j<19;j++) {
				System.out.print(tempgo[i][j]+"\t");
			}
			System.out.print("\n");
		}	
	}
	
	public static void main(String[] args) {
		String type;
		Scanner scanner=new Scanner(System.in);
		while(true) {  //判断游戏类型，输入chess和go之外的无效
			System.out.println("请输入你的游戏类型:输入chess为国际象棋，输入go为围棋");
			type=scanner.nextLine();
			if(type.equals("chess")||type.equals("go")) {
				mygame=new Game(type);
				break;
			}
		}
		System.out.println("请输入第一位棋手的名字");
		String playeronename=scanner.nextLine();
		System.out.println("请输入第二位棋手的名字");
		String playertwoname=scanner.nextLine();
		mygame.setplayernames(playeronename, playertwoname);
		if(type.equals("chess")) {
			System.out.println("国际象棋比赛正式开始!\n");
			chessgame();
		}
		if(type.equals("go")) {
			System.out.println("围棋比赛正式开始!\n");
			gogame();
		}
	}
	
	/**
	 * 国际象棋比赛的界面和操作
	 */
	public static void chessgame() {
		String choice,x1,y1,x2,y2,watch;
		Scanner scanner=new Scanner(System.in);
		String[] temp,tempone,temptwo;
		int turn=0;
		boolean flag=true;
		empty();
		printchess();
		while(flag) {
			System.out.println("现在轮到" + mygame.getplayer(turn).getname() + "的回合");
			chessmenu();
			choice=scanner.nextLine();
			switch(choice) {
			case "1": //移动棋盘上某个位置的棋子至新位置
				System.out.println("选手" + mygame.getplayer(turn).getname()+"你好" + "，请输入需要移动前后的两个棋子的坐标x1,y1和x2,y2：（一个棋子的横纵坐标用逗号隔开，两个棋子之间用空格隔开）");
				temp=(scanner.nextLine()).split(" ");
				tempone=temp[0].split(",");
				temptwo=temp[1].split(","); //两次拆分得到两个坐标
				x1=tempone[0];
				y1=tempone[1];
				x2=temptwo[0];
				y2=temptwo[1];
				if(temp.length==2&&tempone.length==2&&temptwo.length==2) {
					Position p1=new Position(Integer.valueOf(x1),Integer.valueOf(y1));
					Position p2=new Position(Integer.valueOf(x2),Integer.valueOf(y2));
					if(mygame.movepiece(mygame.getplayer(turn),p1,p2)) {
						turn=(turn+1)%2;
						//System.out.println("移动棋子成功\n");
						printchess();
					}
				}
				else {
					System.out.println("输入坐标格式不对\n");
				}
				break;
			case "2": //吃子（使用己方棋子吃掉对手棋子）
				System.out.println("选手" + mygame.getplayer(turn).getname()+"你好" + "，请输入的己方棋子的坐标x1,y1和要吃掉的对方棋子坐标x2,y2：（一个棋子的横纵坐标用逗号隔开，两个棋子之间用空格隔开）");
				temp=(scanner.nextLine()).split(" ");
				tempone=temp[0].split(",");
				temptwo=temp[1].split(","); //两次拆分得到两个坐标
				x1=tempone[0];
				y1=tempone[1];
				x2=temptwo[0];
				y2=temptwo[1];
				if(temp.length==2&&tempone.length==2&&temptwo.length==2) {
					Position p1=new Position(Integer.valueOf(x1),Integer.valueOf(y1));
					Position p2=new Position(Integer.valueOf(x2),Integer.valueOf(y2));
					if(mygame.eatpiece(mygame.getplayer(turn),p1,p2)) {
						turn=(turn+1)%2;
						//System.out.println("吃子成功\n");
						printchess();
					}
				}
				else {
					System.out.println("输入坐标格式不对\n");
				}
				break;
            case "3": //查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）
            	System.out.println("选手" + mygame.getplayer(turn).getname()+"你好" + "，请输入需要查询的棋子位置坐标x,y：（横纵坐标以逗号隔开）");
            	temp=(scanner.nextLine()).split(",");
            	x1=temp[0];
				y1=temp[1];
            	if(temp.length==2) {
            		Position p=new Position(Integer.valueOf(x1),Integer.valueOf(y1));
            		System.out.println(mygame.whethervalidplayer(p));
            	}
            	else {
            		System.out.println("输入坐标格式不对\n");
            	}
				break;
            case "4": //计算两个玩家在棋盘上的棋子总数
            	System.out.println(mygame.countplayerpiece());
            	break;
            case "5": //跳过
            	System.out.println("选手"+ mygame.getplayer(turn).getname()+"跳过\n");
            	turn=(turn+1)%2;
            	break;
            case "end": //结束此次对局
            	System.out.println("国际象棋比赛结束\n");
            	flag=false;
            	break;
            default:
            	System.out.println("请执行正确操作\n");
				break;  		
			}	
		}
		System.out.println("是否查看本次比赛的走棋历史？(输入yes或no)");
		watch=scanner.nextLine();
		switch(watch){
		case "yes":
			mygame.printhistory();
			break;
		case "no":
			break;
		}
		System.out.println("请退出比赛\n");
		
	}
	
	/**
	 * 围棋比赛的界面和操作
	 */
    public static void gogame() {
    	String choice,x1,y1,watch;
		Scanner scanner=new Scanner(System.in);
		String[] temp;
		String[] color=new String[2];
		color[0] = "white";
		color[1] = "black";
		int turn=0;
		boolean flag=true;
		empty();
		printgo();
		while(flag) {
			System.out.println("现在轮到" + mygame.getplayer(turn).getname() + "的回合");
			gomenu();
			choice=scanner.nextLine();
			switch(choice) {
			case "1": //将尚未在棋盘上的一颗棋子放在棋盘上的指定位置
				System.out.println("选手" + mygame.getplayer(turn).getname()+"你好" + "，请输入你要放在棋盘上的棋子的坐标x,y（棋子的横纵坐标用逗号隔开）");
				temp=(scanner.nextLine()).split(",");
            	x1=temp[0];
				y1=temp[1];
				if(temp.length==2) {
					Position p=new Position(Integer.valueOf(x1),Integer.valueOf(y1));
					Piece createpiece=new Piece(color[turn],turn);
					if(mygame.putpiece(mygame.getplayer(turn),createpiece,p)) {
						turn=(turn+1)%2;
						System.out.println("放置棋子成功\n");
						printgo();
					}
				}
				else {
					System.out.println("输入坐标格式不对\n");
				}
				break;
			case "2": //提子（移除对手的棋子）
				System.out.println("选手" + mygame.getplayer(turn).getname()+"你好" + "，请输入你要移除对方棋子的坐标x,y（棋子的横纵坐标用逗号隔开）");
				temp=(scanner.nextLine()).split(",");
            	x1=temp[0];
				y1=temp[1];
				if(temp.length==2) {
					Position p=new Position(Integer.valueOf(x1),Integer.valueOf(y1));
					if(mygame.removepiece(mygame.getplayer(turn), p)) {
						turn=(turn+1)%2;
						//System.out.println("移除对方棋子成功\n");
						printgo();
					}
				}
				else {
					System.out.println("输入坐标格式不对\n");
				}
				break;
            case "3": //查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）
            	System.out.println("选手" + mygame.getplayer(turn).getname()+"你好" + "，请输入需要查询的棋子位置坐标x,y：（横纵坐标以逗号隔开）");
            	temp=(scanner.nextLine()).split(",");
            	x1=temp[0];
				y1=temp[1];
            	if(temp.length==2) {
            		Position p=new Position(Integer.valueOf(x1),Integer.valueOf(y1));
            		System.out.println(mygame.whethervalidplayer(p));
            	}
            	else {
            		System.out.println("输入坐标格式不对\n");
            	}
				break;
            case "4": //计算两个玩家在棋盘上的棋子总数
            	System.out.println(mygame.countplayerpiece());
            	break;
            case "5": //跳过
            	System.out.println("选手"+ mygame.getplayer(turn).getname()+"跳过\n");
            	turn=(turn+1)%2;
            	break;
            case "end": //结束此次对局
            	System.out.println("国际象棋比赛结束\n");
            	flag=false;
            	break;
            default:
            	System.out.println("请执行正确操作\n");
				break;  		
			}	
		}
		System.out.println("是否查看本次比赛的走棋历史？(输入yes或no)\n");
		watch=scanner.nextLine();
		switch(watch){
		case "yes":
			mygame.printhistory();
			break;
		case "no":
			break;
		}
		System.out.println("请退出比赛\n");
	}
}
