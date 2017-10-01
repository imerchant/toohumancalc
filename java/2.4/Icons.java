import java.util.*;
import javax.swing.*;
import java.io.File;

public class Icons extends HashMap<String,ImageIcon> {
//	private HashMap<String,ImageIcon> icons = new HashMap<String,ImageIcon>();
	private static final long serialVersionUID = 1209340945L;
	private final String sep = "/";//File.separator;
	public Icons() { super(); }
	public void load() {
		Class<? extends Icons> c = getClass();
		put("Berserker",new ImageIcon(c.getResource("imgs"+sep+"berserker_icon.png")));
		put("BioEngineer",new ImageIcon(c.getResource("imgs"+sep+"bioengineer_icon.png")));
		put("Champion",new ImageIcon(c.getResource("imgs"+sep+"champion_icon.png")));
		put("Commando",new ImageIcon(c.getResource("imgs"+sep+"commando_icon.png")));
		put("Defender",new ImageIcon(c.getResource("imgs"+sep+"defender_icon.png")));
		
		put("Berserker_small",new ImageIcon(c.getResource("imgs"+sep+"berserker_icon_small.png")));
		put("BioEngineer_small",new ImageIcon(c.getResource("imgs"+sep+"bioengineer_icon_small.png")));
		put("Champion_small",new ImageIcon(c.getResource("imgs"+sep+"champion_icon_small.png")));
		put("Commando_small",new ImageIcon(c.getResource("imgs"+sep+"commando_icon_small.png")));
		put("Defender_small",new ImageIcon(c.getResource("imgs"+sep+"defender_icon_small.png")));
		
		put("Berserker_30",new ImageIcon(c.getResource("imgs"+sep+"berserker_icon_30.png")));
		put("BioEngineer_30",new ImageIcon(c.getResource("imgs"+sep+"bioengineer_icon_30.png")));
		put("Champion_30",new ImageIcon(c.getResource("imgs"+sep+"champion_icon_30.png")));
		put("Commando_30",new ImageIcon(c.getResource("imgs"+sep+"commando_icon_30.png")));
		put("Defender_30",new ImageIcon(c.getResource("imgs"+sep+"defender_icon_30.png")));
		
		put("Icon",new ImageIcon(c.getResource("imgs"+sep+"icon.png")));
		put("unknown",new ImageIcon(c.getResource("imgs"+sep+"unknown.png")));
		put("fenrir",new ImageIcon(c.getResource("imgs"+sep+"fenrir.png")));
		put("ruiner",new ImageIcon(c.getResource("imgs"+sep+"ruiner.png")));
		
		put("Cybernetic_30",new ImageIcon(c.getResource("imgs"+sep+"Cyber_icon_30.png")));
		put("Human_30",new ImageIcon(c.getResource("imgs"+sep+"Human_icon_30.png")));
		
		for(int k = 1; k <= 9; k++) {
			put("Cyber"+k,new ImageIcon(c.getResource("imgs"+sep+"Cyber"+k+".png")));
			put("Human"+k,new ImageIcon(c.getResource("imgs"+sep+"Human"+k+".png")));
		}
		for(int k = 1; k <= 13; k++) {
			put("berserker"+k,new ImageIcon(c.getResource("imgs"+sep+"berserker"+k+".png")));
			put("bioengineer"+k,new ImageIcon(c.getResource("imgs"+sep+"bioengineer"+k+".png")));
			put("champion"+k,new ImageIcon(c.getResource("imgs"+sep+"champion"+k+".png")));
			put("commando"+k,new ImageIcon(c.getResource("imgs"+sep+"commando"+k+".png")));
			put("defender"+k,new ImageIcon(c.getResource("imgs"+sep+"defender"+k+".png")));
		}
		put("champion14",new ImageIcon(c.getResource("imgs"+sep+"champion14.png")));
	}
	public ImageIcon get(String s) {
		return super.get(s);
	}
}