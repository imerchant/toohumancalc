import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;
import java.io.*;

public class TooHumanApplet extends JApplet implements ActionListener,java.io.Serializable {
	public static final long serialVersionUID = -86546345547L;
	private  ClassPanel classPanel;
	private JSplitPane splitpane;
	private HashMap<String,JRadioButtonMenuItem> classMenuButtons,classbuttons,alignbuttons;
	private JRadioButtonMenuItem clearItem;
	private ButtonGroup classgroup, aligngroup,classMenuGroup;
	private JTextArea classdesc, aligndesc;
	private JTextField nameField;
	private HashMap<String,ClassPanel> cache;
	private JProgressBar hitBar,meleeBar,ballisticsBar,armorBar;
		private JLabel specLabel;
	private Icons icons;
	private static final String version = "v2.2";
	private String[] classList = {"Berserker",
								  "BioEngineer",
								  "Champion",
								  "Commando",
								  "Defender"},
					 alignList = {"Human",
					 			  "Cybernetic"};
	public TooHumanApplet() {
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		String classname = /**info[3].getClassName();//*/ UIManager.getSystemLookAndFeelClassName();
		try{UIManager.setLookAndFeel(classname);}catch(Exception e){}
		
		
	}
	public static void main(String arg[]) { new TooHumanApplet(); }
}