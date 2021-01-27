package GuilanUniversity.AmirAbbasi96.Dooz;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;

import GuilanUniversity.AmirAbbasi96.Dooz.Draw.mouse;

public class Dooz extends JFrame{
	static JFrame frame=new JFrame();
	static 	JPanel GamePanel=new JPanel();
static Color[][] board;//araye do bodi az khane ha(baraye kar haye analyz ast)
static Color[][] CopyOfBoard;//copy az board[][]
static 	int turn=0;//nobat
static int m,n,p;//n:tedade satr  //m:tedade sotoon //p:tedade nobat
static 	JButton[][] bt;//araye az noe JButton az khane ha(baraye namayesh)
static 	boolean[][] status;//araye do bodi az vaziat khane ha dar mored nahie dooz.dar peyda kardane nahie ha be kar miravad
static int killedTime;//zamane talaf shode tavasote Thread:RmHomes
static int analyzCount;//tedade khane haye dooz
static Draw startG;//panel menu
static Timer ti=new Timer();//timer
static int score=0;//emtiaz
static JLabel timerLable=new JLabel("00:");//lable daqiqe
static JLabel scoreLbl=new JLabel();//lable emtiaz
static Player whoPlayer;//playere dar hale bazi
/**
 * 
 * @param args
 */

public static void main(String[] args) {
	Player.loadPlayers();
///////////////////check mishavad se pooshe mored niaze barname vojood dashte bashad va agar nabashad misazad
File checkFiles=new File("sounds");
if(!checkFiles.exists())
checkFiles.mkdirs();
checkFiles=new File("icon");
if(!checkFiles.exists())
checkFiles.mkdirs();
checkFiles=new File("pictures");
if(!checkFiles.exists())
checkFiles.mkdirs();
//////////////////////////////////////////////////////////////////
Doozer();
}
	public static void Doozer() {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
String names[]=new String [Player.players.size()];//araye az name player ha
for(int i=0;i<names.length;i++)
	names[i]=Player.players.get(i).namgeGetter();
	 startG=new Draw(names);//sakhte panel shamele menu az name karbar ha va ...
	 startG.underIt2.setText("Scores");
	 startG.underIt2.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			showScores();
			
		}
	});
	 startG.newO.setText("New Player");//playere jadid
	 startG.underIt.setText("Change picture");//namayeshe score
	 startG.newO.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
	String name=JOptionPane.showInputDialog("Please enter your name");	
	boolean rose=true;
		while(rose==true) {//check mishavad name tekrari nabashad
			Player tmp=new Player(name);
			if(Player.exist(tmp))
				name=JOptionPane.showInputDialog("This player already exist!Please enter another name");
			else
				rose=false;
		}
File picAddress=FileChooser();		
		
		try {
			Image im=ImageIO.read(new File(picAddress.getPath()));
		ImageIcon img=new ImageIcon(im.getScaledInstance(150, 150, 150));
			Player.newPlayer(name, 0);
			startG.barValues(4, Player.players.size());
			startG.adder(name);
		
			File out=new File("pictures/"+name+".jpg");
			try {
			Files.copy(picAddress.toPath(), out.toPath());}
			catch (IOException e) {
				
			}
			startG.images.get(startG.images.size()-1).setIcon(img);
			final Player player=Player.players.get(Player.players.size()-1);
			ActionListener action1=new ActionListener() {//action baraye morde ezafe shode
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.remove(startG);
					frame.repaint();
					play(player);
				}
			};
			startG.lastButtonAction(action1);//action ezafe mishavad
			
			startG.repaint();
			Player.writePlayers();//update players.data
		}
		catch (NullPointerException e) {
			
		}
		catch (IOException e) {
			// TODO: handle exception
		}
		}
	});
	
	 startG.underIt.addActionListener(new ActionListener() {//namayeshe emtiaz
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String menu[]=new String [Player.players.size()];
			for(int i=0;i<Player.players.size();i++)
				menu[i]=Player.players.get(i).namgeGetter();
			Object ob=JOptionPane.showInputDialog(null, "Please choose", "", 1, null, menu, null);
			if(ob!=null) {
			File file=Dooz.FileChooser();
			if(file!=null) {
			int index=Player.players.indexOf(new Player(ob.toString()));
			try {
				File f1=new File("pictures/"+ob.toString()+".jpg");
				if(f1.exists())
					f1.delete();
			File out=new File("pictures/"+ob.toString()+".jpg");
			ImageIcon img=new ImageIcon(file.getPath());
			Files.copy(file.toPath(), out.toPath());
startG.images.get(index).setIcon(img);
	
			
			}
			catch (IOException e) {
				// TODO: handle exception
			}
			}
		}}
	});
	 startG.delete.setText("Delete Player");//hazfe player
	 startG.delete.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			startG.delPanel();
			startG.repaint();
			startG.del.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int [] indexes=startG.delAction();
					if(indexes!=null) {
					for(int i=0;i<indexes.length;i++)
						Player.delPlayer(indexes[i]);
					
					Player.writePlayers();
					}
				}
			});
			
			
		}
	});
	startG.setLayout(null);
	frame.setLayout(null);
	frame.add(startG);
	frame.setBounds(0, 0, 1000, 800);
	frame.setVisible(true);
	////////////////////////////////actionListener ha be tamame mored ha ezafe mishavad
		for(int i=0;i<startG.images.size();i++)
		{
			final Player player=Player.players.get(i);
			startG.list.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
				frame.remove(startG);
				frame.dispose();
					frame.repaint();
					play(player);
				
				}
			});
			startG.images.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {

frame.remove(startG);
frame.dispose();
frame.repaint();
play(player);}
			});
		}
		////////////////////////////////////////////////////////////
		

	}
	/**
	 * analyz mikonad che tedad khane dooz atrafe khane gofte shode vojood darad.az CopyOfBoard[][] estefade mikonad chon karash fari ast.
	 * ba raveshe bazgashti
	 * agar flag==true yani analyz az noe peyda kardane nahie ha ast va dar check() seda zade shode
	 * agar flag==false yani analyz bararye peyda kardane khane haye dooz estefade shode va az play() seda zade shode 
	 * @param i
	 * @param j
	 * @param flag
	 * @return tedade khane haye peyda shode
	 */
	public static int analyz(int i,int j,boolean flag) {
if(flag==true)
	status[i][j]=false;
		analyzCount++;
		Color color=CopyOfBoard[i][j];//rang sabt mishavad
		CopyOfBoard[i][j]=null;//rang in khane hazf mishavad(chon ravesh bazgashti ast halghe binahayat mishavad)
		if(j<m-1) {
		if(CopyOfBoard[i][j+1]==color)//khaneye rast
			analyz(i,j+1,false);}
		if(i<n-1) {
		if(CopyOfBoard[i+1][j]==color)//khaneye payin
			analyz(i+1,j,false);}
		if(j>0) {
		if(CopyOfBoard[i][j-1]==color)//khaneye chap
			analyz(i,j-1,false);}
		if(i>0) {
		if(CopyOfBoard[i-1][j]==color)//khaneye bala
			analyz(i-1,j,false);}
		if(i<n-1 && j<m-1) {
		if(CopyOfBoard[i+1][j+1]==color)//khaneye rast va payin
			analyz(i+1,j+1,false);}
		if(i>0 && j<m-1) {
		if(CopyOfBoard[i-1][j+1]==color)//khane rast va bala
			analyz(i-1,j+1,false);}
		if(i<n-1 && j>0) {
		if(CopyOfBoard[i+1][j-1]==color)//khane payin va chap
			analyz(i+1,j-1,false);}
		if(i>0 && j>0) {
		if(CopyOfBoard[i-1][j-1]==color)//khane bala va chap
			analyz(i-1,j-1,false);}
	return analyzCount;
	}
	/**
	 * kare peyda kardane khane haye dooz ra bar ohde darad ama tafavotash ba analyz() in ast ke khane hara tagir midahad
	 * @param i
	 * @param j
	 */
	public static void find(int i,int j) {

		
		try {
		Thread.sleep(100);
		sound("sounds/a.wav");
}
		
	catch(Exception e) {
	
	}
			score++;
	scoreLbl.setText(String.valueOf(score));//update lable score
		
	
		Color color=board[i][j];//rang sabt mishavad
		board[i][j]=null;//rang hazf mishavad
		bt[i][j].setBackground(Color.black);//range in khane dar khode panel siah mishavad
		
		if(j<m-1) {
		if(board[i][j+1]==color)//khane rast
			find(i,j+1);}
		if(i<n-1) {
		if(board[i+1][j]==color)//khaneye payin
			find(i+1,j);}
		if(j>0) {
		if(board[i][j-1]==color)//khaneye chap
			find(i,j-1);}
		if(i>0) {
		if(board[i-1][j]==color)//khaneye bala
			find(i-1,j);}
		if(i<n-1 && j<m-1) {
		if(board[i+1][j+1]==color)//khaneye payin va rast
			find(i+1,j+1);}
		if(i>0 && j<m-1) {
		if(board[i-1][j+1]==color)//khaneye bala va rast
			find(i-1,j+1);}
		if(i<n-1 && j>0) {
		if(board[i+1][j-1]==color)//khaneye payin va chap
			find(i+1,j-1);}
		if(i>0 && j>0) {
		if(board[i-1][j-1]==color)//khaneye bala va chap
			find(i-1,j-1);}
	

	}
/**
 * 
 * @param player
 */
public static void play(Player player) {
whoPlayer=player;//playere dar hale bazi


	

frame.repaint();

	frame.setResizable(true);
	int c=0;
	boolean error=false;
	try {
	n=Integer.parseInt(JOptionPane.showInputDialog("please enter n"));
	while(n<=0)
		n=Integer.parseInt(JOptionPane.showInputDialog("bad input!please enter n"));
	m=Integer.parseInt(JOptionPane.showInputDialog("please enter m"));
	while(m<=0)
		m=Integer.parseInt(JOptionPane.showInputDialog("bad input!please enter m"));
	p=Integer.parseInt(JOptionPane.showInputDialog("please enter p"));
	while(p<=0)
		p=Integer.parseInt(JOptionPane.showInputDialog("bad input!please enter p"));
	c=Integer.parseInt(JOptionPane.showInputDialog("please enter c(max=30)"));
	while(c<=0 || c>30)
		c=Integer.parseInt(JOptionPane.showInputDialog("bad input!please enter c(max=30)"));}
	catch (NumberFormatException e) {
		error=true;
		Doozer();
	}
	if(error==false) {
		JPanel header=new JPanel();
		header.setLayout(new BorderLayout(5*m,0));
		JPanel tt=new JPanel();
		tt.setLayout(new BorderLayout(0,0));
		GamePanel.setLayout(new GridLayout(n,m,0,0));
		GamePanel.setBackground(Color.black);
header.setBackground(Color.yellow);

	frame.setContentPane(tt);
	tt.add(GamePanel,BorderLayout.CENTER);
	tt.add(header,BorderLayout.NORTH);
		board=new Color[n][m];
		CopyOfBoard=new Color [n][m];
		bt=new JButton [n][m];
		Random rnd=new Random();//random
		Color[] color=new Color [c];//araye rang ha 
	boolean found=false;//flage baraye moshakhas shode inke jadvale sakhte shode hadeaqal yek nahiye dooz darad
	while(found==false) {
		for(int i=0;i<color.length;i++)//color[] ba rang haye random por mishavad
			color[i]=new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255));
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				Color t=color[rnd.nextInt(color.length)];//range be soorate random az color[] entekhab mishavad
				bt[i][j]=new JButton();
				board[i][j]=t;//dar board[][] gozashte mishavad
			bt[i][j].setBackground(t);//dar bt[][] gozashte mishavad

			
		}}
		if(check("making")==false) //check mishavad hedaqal yek nahiye dooz dashte ya na;agar false nadashte
			found=false;

	else
				found=true;
	}
	
		for(int i=0;i<n;i++)//dar nahayat bt[][] be GamePanel ezafe mishavand
			for(int j=0;j<m;j++)
				GamePanel.add(bt[i][j]);
	
	reset();//killedTime,score,turn va lable haye daqiqe va saniye reset mishavand
JButton newGame=new JButton("New Game!");
ti.start();//saniye shomar shoroo be kar mikonad
JLabel turnCount=new JLabel();//lable tedade nobat haye anjam shode

if(m<9) {
timerLable.setFont(new Font("Hobo Std", Font.PLAIN, 10*m));//font

turnCount.setFont(new Font("Hobo Std", Font.PLAIN, (40/7)*m));//font
}
else {
	timerLable.setFont(new Font("Hobo Std", Font.PLAIN, 80));//font

	turnCount.setFont(new Font("Hobo Std", Font.PLAIN, (40/7)*13));//font
}

JButton showScore=new JButton("Scores");//lable emtiaz
showScore.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		showScores();
		
	}
});
newGame.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {//bazi jadid
ti.flag=false;
ti=new Timer();
		GamePanel=new JPanel();
		frame.dispose();
		frame=new JFrame();
		play(player);
		
	}
});
JButton exit=new JButton("Main menu");
exit.setFont(new Font("Hobo Std", Font.PLAIN, 20));
newGame.setFont(new Font("Hobo Std", Font.PLAIN, 20));
showScore.setFont(new Font("Hobo Std", Font.PLAIN, 20));
exit.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	int bit=JOptionPane.showConfirmDialog(null, "Are you sure?", "Back to main menu", 0, 0, null);
		if(bit==0) {
		ti.flag=false;
		ti=new Timer();
				GamePanel=new JPanel();
				frame.dispose();
				frame=new JFrame();
				Doozer();}
		
	}
});
JLabel watchLbl=new JLabel();
JPanel first=new JPanel();
first.setBackground(Color.yellow);
first.setLayout(new BorderLayout(0,0));
try {
Image im2=ImageIO.read(new File("icon/timer.png"));
watchLbl.setIcon(new ImageIcon(im2.getScaledInstance(70, 70, 70)));
first.add(watchLbl,BorderLayout.WEST);}
catch (IOException e) {
	// TODO: handle exception
}
first.add(timerLable,BorderLayout.EAST);
header.add(first,BorderLayout.WEST);

header.add(turnCount);


/////////tasvire seke////////
JLabel coinPic=new JLabel();
coinPic.setBounds(70*m, 15, 70, 70);
try {
Image im=ImageIO.read(new File("icon/star.png"));
ImageIcon img=new ImageIcon(im.getScaledInstance(70, 70, 70));
coinPic.setIcon(img);}
catch (Exception e) {
	// TODO: handle exception
}

JPanel ender=new JPanel();
ender.setBackground(Color.yellow);
ender.setLayout(new GridLayout(0,2,0,0));
ender.add(coinPic);

/////////////////////////////////
scoreLbl.setFont(new Font("Hobo Std", Font.PLAIN, 40));
scoreLbl.setBounds(70*m+70, 8, 100, 100);
scoreLbl.setText(String.valueOf(score));
ender.add(scoreLbl);
header.add(ender,BorderLayout.EAST);
int w,ho;
if(n>8)
	ho=1000;
else
	ho=100*n+250;
if(m>20)
	w=2000;
else
	w=150*m+20;
	frame.setBounds(0,0, w,ho);
	JPanel btJ=new JPanel();
	btJ.setBackground(Color.yellow);
	btJ.setLayout(new GridLayout(0,3,0,0));
	btJ.add(showScore);
	btJ.add(newGame);
	btJ.add(exit);
	header.add(btJ,BorderLayout.SOUTH);
	frame.repaint();
	frame.setVisible(true);


//actionListenere khane haye jadval sakhte mishavad
for(int o=0;o<n;o++) {
for(int l=0;l<m;l++) {
	final int h=o;
	final int d=l;
bt[o][l].addActionListener(new ActionListener() {
	
@Override
public void actionPerformed(ActionEvent arg0) {
	for(int i=0;i<n;i++) {//CopyOfBoard[][] az rooye board[][] kopi mishavad
		for(int j=0;j<m;j++)
		CopyOfBoard[i][j]=board[i][j];	
	}
	analyzCount=0;

if(analyz(h, d,false)>3) {//yani atarafe in khane tedade khane haye dooz az se bishtar ast?
		turn++;
	turnCount.setText("Turn:"+String.valueOf(turn));//lable nobat update mishavad
RmHomes io=new RmHomes(h, d, n, m);//khane haye dooz hazf mishavand
io.start();}
else
	sound("sounds/error.wav");

}
});
	
	
}}

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.repaint();
	}
}
/**
 * tabe karhaye tamam shodane bazi
 */
public static void finish() {
	frame.setVisible(false);
	frame=new JFrame();
	double record=(m*n*score)/(turn*Math.pow(ti.n+ti.m*60-killedTime, 1/3));//emtiaz hesab mishavad
	whoPlayer.updateRecord(record);//emtiaz player update mishavad
	Player.writePlayers();//players.data update mishavad
	JFrame wi=new JFrame();
	JPanel p=new JPanel();
	wi.setContentPane(p);
	p.setBackground(Color.yellow);
	JButton newG=new JButton("New Game");//bazi jadid
	JButton mainPage=new JButton("Main page");//safhe menu player ha
	JButton scores=new JButton("Scores");
	JButton exit=new JButton("Exit");
	scores.setFont(new Font("Hobo Std", Font.PLAIN, 13));
	newG.setFont(new Font("Hobo Std", Font.PLAIN, 13));
	mainPage.setFont(new Font("Hobo Std", Font.PLAIN, 13));
	exit.setFont(new Font("Hobo Std", Font.PLAIN, 13));
	wi.setLayout(new BorderLayout(0,0));
	wi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	newG.setBounds(20, 380, 100, 50);
	mainPage.setBounds(130, 380, 100, 50);
	scores.setBounds(240, 380, 100, 50);
	exit.setBounds(350, 380, 100, 50);
	p.add(newG);
	p.add(mainPage);
	p.add(scores);
	p.add(exit);
	scores.addActionListener(new  ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
		showScores();
			
		}
	});
	newG.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.remove(GamePanel);
			GamePanel=new JPanel();
			ti.flag=false;
			ti=new Timer();
			wi.dispose();
			play(whoPlayer);
			
		}
	});
	exit.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
			
		}
	});
	mainPage.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JPanel j=new JPanel();
			frame.setContentPane(j);
			GamePanel=new  JPanel();
			wi.dispose();
			ti.flag=false;
			ti=new Timer();
		Doozer();
			
		}
	});
			wi.setLayout(null);
	wi.setBounds(40, 40, 500, 500);
	
	JLabel lbl2=new JLabel();
	lbl2.setBounds(125, 100, 400, 400);
	lbl2.setFont(new Font("Hobo Std", Font.BOLD, 40));//font
	lbl2.setText("Score:"+String.format("%.2f", record));
	JLabel pic=new JLabel();
	pic.setBounds(125, 20, 250, 250);
	ImageIcon img=new ImageIcon("icon/finish.png");//tasvire seke
	pic.setIcon(img);
	p.add(pic);
	p.add(lbl2);

	wi.setVisible(true);
	sound("sounds/coin.wav");

}
/**
 * namayeshe emtiaz ha
 */
public static void showScores() {

JFrame fr=new JFrame();
fr.setLayout(new BorderLayout(0,0));
fr.setBounds(0, 0, 400, 400);
JLabel scr=new JLabel("Scores");
scr.setFont(new Font("Hobo Std", Font.BOLD, 40));
scr.setBounds(100, 10, 250, 50);
fr.add(scr,BorderLayout.NORTH);
String[][] names=new String[Player.players.size()][2];
for(int i=0;i<names.length;i++) {
	names[i][0]=Player.players.get(i).namgeGetter();
names[i][1]=String.format("%.2f",Player.players.get(i).getRecord());}
String jk[]= {"names","score"};


JScrollPane pan = new JScrollPane();

pan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
pan.setBounds(0, 60,380	, 290);
fr.add(pan,BorderLayout.CENTER);

JTable table=new JTable(names,jk);
pan.setViewportView(table);

table.setRowHeight(50);

fr.setVisible(true);

}
/**
 * reset kardane moteghayer haye lazem dar har dast bazi
 */
public static void reset() {
	turn=0;
	killedTime=0;
timerLable.setText("00:00");
	score=0;
	
}
/**
 * check mikonad dar jadval hadeaqal yek nahiye dooz vojooddashte bashad
 * 
 * @param cond
 * @return
 */
public static boolean check(String cond) {
 status=new boolean [n][m];
	boolean flag=false;
	for(int i=0;i<n;i++) //vaziat ha true mishavad
		for(int j=0;j<m;j++) 
			status[i][j]=true;
	
/**
 * takb be take khane ha check mishavand
 * agar vaziate khane dar status[][] true bashad yani ghablan ozve nahiyeei naboode va bayad check shavad vagarna check nemishavad
 */
	outer:for(int i=0;i<n;i++) {
	
			for(int j=0;j<m;j++) {
				analyzCount=0;
				///////////CopyOfBoard[][] update mishavad
				for(int t=0;t<n;t++) 
					for(int o=0;o<m;o++)
						Dooz.CopyOfBoard[t][o]=Dooz.board[t][o];
				//////////
				if(status[i][j]==true && CopyOfBoard[i][j]!=null) {
					if(analyz(i, j,true)>3) {//yani khaneei peyda shode ke nahiye misazad
						flag=true;
						break outer;
					}
				}
				
			}
		}
	
	if(flag==false && cond.equals("making")) {//yani peyda nashode va az play() seda zade shode baraye sakte jadval
		return false;
	}
	if(flag==false && cond.equals("checking")) {//yani peyda nashode va az Thread:RMHomes seda zade shode baraye payane bazi
		Dooz.finish();
	}
	return true;
}
/**
 * JFileChooser
 * faghat jpg ghabool mikonad
 * @return
 */
public static File FileChooser() {
//////////////////JFileChooser va kar haye tasvir/////////////////////
	File picAddress=null;
JFileChooser choose=new JFileChooser();
choose.setAcceptAllFileFilterUsed(false);
choose.setFileFilter(new FileFilter() {

@Override
public String getDescription() {
	
	return ".jpg (150*150 pixels)";
}

@Override
public boolean accept(File file) {
	if(file.isDirectory())
	return true;
	else {
		if(file.getName().lastIndexOf(".jpg")!=-1)
			return true;
	}
	return false;
}
});
choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
if(choose.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION) {
picAddress=choose.getSelectedFile();

}
return picAddress;
}
public static void sound(String address) {
	try {
		File file=new File(address);
		Clip clip=AudioSystem.getClip();
		AudioInputStream stream=AudioSystem.getAudioInputStream(file);
		clip.open(stream);
		clip.start();
	}catch (Exception e) {
		System.out.println(e);
	 //norhing to do
	}
}
}

