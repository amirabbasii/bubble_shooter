package GuilanUniversity.AmirAbbasi96.Dooz;
import java.awt.Color;
import java.util.ArrayList;
/**
 *   
 * Vazife siah karddane masire dooz va sepas hazf va Gravity ra bar ohde darad
 *
 */
public class RmHomes extends Thread {
	int h,d,n,m;
	int analyzCount;
/**
 * Cunstractor
 * @param h
 * @param d
 * @param n
 * @param m
 */
	public RmHomes(int h,int d,int n,int m) {
		this.h=h;
		this.d=d;
		this.n=n;
		this.m=m;
	
	}
public void run() {
	Dooz.frame.setEnabled(false);
	int tmp1=Dooz.ti.m*60+Dooz.ti.n;//zaman ghable kar

	Dooz.find(h,d);//farainde dooz anjam mishavad
	
	///////////////Gravity/////////////////
	for(int j=0;j<m;j++) {
		Color tmp=null;
		for(int z=0;z<n;z++) {
	for(int i=n-1;i>0;i--) {
		if(Dooz.board[i][j]==null) {
			
			tmp=Dooz.board[i][j];
		Dooz.board[i][j]=Dooz.board[i-1][j];
		Dooz.board[i-1][j]=tmp;}
	}}
	}
	for(int i=0;i<n;i++) {
		Color tmp=null;
		for(int z=0;z<m;z++) {
	for(int j=0;j<m-1;j++) {
		if(Dooz.board[i][j]==null) {
			tmp=Dooz.board[i][j];
			Dooz.board[i][j]=Dooz.board[i][j+1];
			Dooz.board[i][j+1]=tmp;
		}
}}
}
	///////////////////////////////////
	
	for(int i=0;i<n;i++)//bt[][] update mishavad
		for(int j=0;j<m;j++)
			Dooz.bt[i][j].setBackground(Dooz.board[i][j]);
	
	for(int i=0;i<n;i++) {//dokme haye bedoone rang az panel pak mishavad
		for(int j=0;j<m;j++) {
			if(Dooz.board[i][j]==null) {
//				Dooz.GamePanel.remove(Dooz.bt[i][j]);
				Dooz.bt[i][j].setVisible(false);
				}
		}}
	int s=0;//tedade khali ha
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<m;j++) {
			if(Dooz.board[i][j]==null)
				s++;
		}
	}
Dooz.killedTime+=Dooz.ti.m*60+Dooz.ti.n-tmp1;//
	if(s==m*n)//yani hame hali ast
		Dooz.finish();
	else if(Dooz.turn==Dooz.p)//yano nobat ha tamam shode
		Dooz.finish();
	else {
	Dooz.check("checking");//cehcke inke hadeaqal yek nahiye dooz vojood darad
		
		
	}
	Dooz.GamePanel.repaint();
	Dooz.frame.setEnabled(true);
}

}
