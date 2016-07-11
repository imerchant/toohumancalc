import java.util.*;
import javax.swing.*;

public class Icons {
	private HashMap<String,ImageIcon> icons = new HashMap<String,ImageIcon>();
	public Icons() {}
	public void load() {
		Class<? extends Icons> c = getClass();
		icons.put("berserker icon",new ImageIcon(c.getResource("imgs/berserker_icon.png")));
		icons.put("bioengineer icon",new ImageIcon(c.getResource("imgs/bioengineer_icon.png")));
		icons.put("champion icon",new ImageIcon(c.getResource("imgs/champion_icon.png")));
		icons.put("commando icon",new ImageIcon(c.getResource("imgs/commando_icon.png")));
		icons.put("defender icon",new ImageIcon(c.getResource("imgs/defender_icon.png")));
		
		icons.put("Icon",new ImageIcon(c.getResource("imgs/icon.png")));
		icons.put("unknown",new ImageIcon(c.getResource("imgs/unknown.png")));
		icons.put("fenrir",new ImageIcon(c.getResource("imgs/fenrir.png")));
		
		for(int k = 1; k <= 9; k++) {
			icons.put("Cyber"+k,new ImageIcon(c.getResource("imgs/Cyber"+k+".png")));
			icons.put("Human"+k,new ImageIcon(c.getResource("imgs/Human"+k+".png")));
		}
		for(int k = 1; k <= 13; k++) {
			icons.put("berserker"+k,new ImageIcon(c.getResource("imgs/berserker"+k+".png")));
			icons.put("bioengineer"+k,new ImageIcon(c.getResource("imgs/bioengineer"+k+".png")));
			icons.put("champion"+k,new ImageIcon(c.getResource("imgs/champion"+k+".png")));
	//		icons.put("commando"+k,new ImageIcon(c.getResource("imgs/commando"+k+".png")));
			icons.put("defender"+k,new ImageIcon(c.getResource("imgs/defender"+k+".png")));
		}
		icons.put("champion14",new ImageIcon(c.getResource("imgs/champion14.png")));
	}
	public ImageIcon get(String s) {
		return icons.get(s);
	}
}