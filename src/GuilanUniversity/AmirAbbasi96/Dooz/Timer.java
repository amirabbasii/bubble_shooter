package GuilanUniversity.AmirAbbasi96.Dooz;
public class Timer extends Thread{
	int n=0,m=0;//n:sanie //m:daqiqe
	boolean flag=true;//flag baraye kill kardane Thread
public void run() {

	while(flag==true) {
		String minute=String.valueOf(m);
		String second=String.valueOf(n);
		try {
		Thread.sleep(1000);}
		catch (Exception e) {
			
		}
		if(m<10)
			minute="0"+String.valueOf(m);
		if(n==60) {//tabdil
			n=0;
			m++;
			//////////////////tanzime sefre ghable adad
			if(m<10)
			minute="0"+String.valueOf(m);
			}
		if(n<10)
		second="0"+String.valueOf(n);
		else
			second=String.valueOf(n);
		Dooz.timerLable.setText(minute+":"+second);
		///////////////////////////////////////////////
		n++;
		Dooz.GamePanel.repaint();
	}
}
}
