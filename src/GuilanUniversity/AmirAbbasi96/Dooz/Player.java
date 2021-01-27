package GuilanUniversity.AmirAbbasi96.Dooz;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Player {
	static ArrayList<Player> players=new ArrayList<>();//player ha
private String name;
private double record;
/**
 * Costructore asli
 * @param name
 * @param record
 */
public Player(String name,double record) {
	this.name=name;
	this.record=record;
}
/**
 * Constroctor alaki va dar moghayese karboed darad
 * @param name
 */
public Player(String name) {
	this.name=name;
}
/**
 * hazfe player
 * @param i
 */
public static void delPlayer(int i) {
	players.remove(i);
}
/**
 * playere jadid
 * @param name
 * @param record
 */
public static void newPlayer(String name,double record) {
	players.add(new Player(name, record));
}
/**
 * update players.data
 */
public static void writePlayers() {
	try {
FileOutputStream writer=new FileOutputStream("players.data");
for(int i=0;i<players.size();i++) {
	String h=players.get(i)+"#";
	for(int j=0;j<h.length();j++)
	writer.write((int)h.charAt(j));
}
writer.close();
	}
	catch (IOException e) {
JOptionPane.showMessageDialog(null, "Couldn't write on players.data!");
	}
}
public String toString() {
	String str=String.format("%.2f", record);
	return name+":"+str;
}
/**
 * load karadne player ha
 */
public static void loadPlayers() {
	try {
	FileInputStream reader=new FileInputStream("players.data");
	String input="";
	int buffer=0;
	while(buffer!=-1) {
		buffer=reader.read();
		input+=(char)buffer;
	}
	input=input.substring(0, input.length()-1);
	if(!input.equals("")) {
	String[] playerss=input.split("#");
	for(int i=0;i<playerss.length;i++) {
		String details[]=playerss[i].split(":");
		newPlayer(details[0], Double.parseDouble(details[1]));
		
		
	}
	}
	reader.close();
	}catch (Exception e) {
	File file=new File("players.data");
	try {
	file.createNewFile();}
	catch (IOException g) {
		// TODO: handle exception
	}
	}
}
public String namgeGetter() {
return this.name;
}
/**
 * update record
 * @param record
 */
public void updateRecord(double x) {
	this.record=this.record+x;
}
public double getRecord() {
	return record;
}
/**
 * moghayese ba asase name
 */
public  boolean equals(Object x) {
	if(x instanceof Player) {
		if(((Player) x).namgeGetter().equals(this.name))
			return true;
		else
			return false;
	}
	else
		return false;
}
/**
 * check kardane inke player vojood darad ya na
 * @param player
 * @return
 */
public static boolean exist(Player player) {
	boolean found=false;
	for(int i=0;i<players.size();i++)
	{
		if(players.get(i).equals(player)) {
		 found=true;
		 break;}
	}
	return found;
}

}
