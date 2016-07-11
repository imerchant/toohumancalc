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
		put("Berserker",new ImageIcon(c.getResource("imgs/berserker_icon.png")));
		put("BioEngineer",new ImageIcon(c.getResource("imgs/bioengineer_icon.png")));
		put("Champion",new ImageIcon(c.getResource("imgs/champion_icon.png")));
		put("Commando",new ImageIcon(c.getResource("imgs/commando_icon.png")));
		put("Defender",new ImageIcon(c.getResource("imgs/defender_icon.png")));
		
		put("Berserker_small",new ImageIcon(c.getResource("imgs/berserker_icon_small.png")));
		put("BioEngineer_small",new ImageIcon(c.getResource("imgs/bioengineer_icon_small.png")));
		put("Champion_small",new ImageIcon(c.getResource("imgs/champion_icon_small.png")));
		put("Commando_small",new ImageIcon(c.getResource("imgs/commando_icon_small.png")));
		put("Defender_small",new ImageIcon(c.getResource("imgs/defender_icon_small.png")));
		
		put("Berserker_30",new ImageIcon(c.getResource("imgs/berserker_icon_30.png")));
		put("BioEngineer_30",new ImageIcon(c.getResource("imgs/bioengineer_icon_30.png")));
		put("Champion_30",new ImageIcon(c.getResource("imgs/champion_icon_30.png")));
		put("Commando_30",new ImageIcon(c.getResource("imgs/commando_icon_30.png")));
		put("Defender_30",new ImageIcon(c.getResource("imgs/defender_icon_30.png")));
		
		put("icon",new ImageIcon(c.getResource("imgs/icon.png")));
		put("icon20",new ImageIcon(c.getResource("imgs/icon20.png")));
		put("unknown",new ImageIcon(c.getResource("imgs/unknown.png")));
		put("fenrir",new ImageIcon(c.getResource("imgs/fenrir.png")));
		put("ruiner",new ImageIcon(c.getResource("imgs/ruiner.png")));
		
		put("Cybernetic_30",new ImageIcon(c.getResource("imgs/Cyber_icon_30.png")));
		put("Human_30",new ImageIcon(c.getResource("imgs/Human_icon_30.png")));
		
		put("Cybernetic_small",new ImageIcon(c.getResource("imgs/Cyber_icon_small.png")));
		put("Human_small",new ImageIcon(c.getResource("imgs/Human_icon_small.png")));
		
		for(int k = 1; k <= 9; k++) {
			put("Cyber"+k,new ImageIcon(c.getResource("imgs/Cyber"+k+".png")));
			put("Human"+k,new ImageIcon(c.getResource("imgs/Human"+k+".png")));
		}
		for(int k = 1; k <= 13; k++) {
			put("berserker"+k,new ImageIcon(c.getResource("imgs/berserker"+k+".png")));
			put("bioengineer"+k,new ImageIcon(c.getResource("imgs/bioengineer"+k+".png")));
			put("champion"+k,new ImageIcon(c.getResource("imgs/champion"+k+".png")));
			put("commando"+k,new ImageIcon(c.getResource("imgs/commando"+k+".png")));
			put("defender"+k,new ImageIcon(c.getResource("imgs/defender"+k+".png")));
		}
		put("champion14",new ImageIcon(c.getResource("imgs/champion14.png")));
		
		put("help",new ImageIcon(c.getResource("imgs/tango/help-browser.png")));
		put("refresh",new ImageIcon(c.getResource("imgs/tango/view-refresh.png")));
		put("stop",new ImageIcon(c.getResource("imgs/tango/process-stop.png")));
		put("folder",new ImageIcon(c.getResource("imgs/tango/folder.png")));
		put("folder open",new ImageIcon(c.getResource("imgs/tango/folder-open.png")));
		put("floppy",new ImageIcon(c.getResource("imgs/tango/media-floppy.png")));
		put("doc save",new ImageIcon(c.getResource("imgs/tango/document-save.png")));
		put("doc save as",new ImageIcon(c.getResource("imgs/tango/document-save-as.png")));
		put("log out",new ImageIcon(c.getResource("imgs/tango/system-log-out.png")));
		put("image x",new ImageIcon(c.getResource("imgs/tango/image-x-generic.png")));
		put("text x",new ImageIcon(c.getResource("imgs/tango/text-x-generic.png")));
		put("find",new ImageIcon(c.getResource("imgs/tango/edit-find.png")));
		put("search",new ImageIcon(c.getResource("imgs/tango/system-search.png")));
		put("dialog warning",new ImageIcon(c.getResource("imgs/tango/dialog-warning.png")));
		put("document open",new ImageIcon(c.getResource("imgs/tango/document-open.png")));
		put("cut",new ImageIcon(c.getResource("imgs/tango/edit-cut.png")));
		put("copy",new ImageIcon(c.getResource("imgs/tango/edit-copy.png")));
		put("paste",new ImageIcon(c.getResource("imgs/tango/edit-paste.png")));
		put("delete",new ImageIcon(c.getResource("imgs/tango/edit-delete.png")));
		put("select all",new ImageIcon(c.getResource("imgs/tango/edit-select-all.png")));
		put("view full",new ImageIcon(c.getResource("imgs/tango/view-fullscreen.png")));
	}
	public ImageIcon get(String s) {
		return super.get(s);
	}
}