import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;
import java.io.*;

public class TooHumanCalc extends JFrame implements ActionListener, java.io.Serializable {
	public static final long serialVersionUID = -86546345547L;
	private JComboBox classBox;
	private ClassPanel classPanel;
	private JScrollPane scrollPane;
	private HashMap<String,JRadioButton> classbuttons, alignbuttons;
	private ButtonGroup classgroup, aligngroup;
	private JTextArea classdesc, aligndesc;
	private JTextField nameField;
	private JFileChooser save,open;
	private HashMap<String,ClassPanel> cache;
	private THFileFilter filter = new THFileFilter();
	private static final String version = "v0.8";
	private String[] classList = {//" ",
								  "Berserker",
								  "BioEngineer",
								  "Champion",
								  "Commando",
								  "Defender"},
					 alignList = {"Human",
					 			  "Cybernetic"};
	public TooHumanCalc() {
		super("Too Human Character Plotter "+version);
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		String classname = /*info[3].getClassName();*/ UIManager.getSystemLookAndFeelClassName();
		try{UIManager.setLookAndFeel(classname);}catch(Exception e){}

		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		save = new JFileChooser();
		save.setDialogType(JFileChooser.SAVE_DIALOG);
		save.setDialogTitle("Save the current character");
		save.setFileFilter(filter);
		open = new JFileChooser();
		open.setDialogType(JFileChooser.OPEN_DIALOG);
		open.setDialogTitle("Open a character");
		save.setFileFilter(filter);
		
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem openItem = new JMenuItem("Open...",KeyEvent.VK_O);
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		fileMenu.add(openItem).addActionListener(this);
		JMenuItem saveItem = new JMenuItem("Save as...",KeyEvent.VK_S);
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		fileMenu.add(saveItem).addActionListener(this);
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit",KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke("alt X"));
		fileMenu.add(exitItem).addActionListener(this);
		menubar.add(fileMenu);
		JMenu aboutMenu = new JMenu("About");
		aboutMenu.setMnemonic(KeyEvent.VK_A);
		aboutMenu.add(new JMenuItem("About",KeyEvent.VK_A)).addActionListener(this);
		menubar.add(aboutMenu);
		setJMenuBar(menubar);
		
		saveItem.setEnabled(false);
		openItem.setEnabled(false);

		Arrays.sort(classList);
	//	classBox = new JComboBox(classList);
	//	classBox.addActionListener(this);
		JLabel classLabel = new JLabel("  Class:  ");
	/*	Box topBox = Box.createHorizontalBox();
		topBox.add(classLabel);
	//	topBox.add(classBox);
		ButtonGroup group = new ButtonGroup();
		Vector<JRadioButton> buttons = new Vector<JRadioButton>(classList.length);
		for(int k = 0; k < classList.length; k++) {
			JRadioButton j = new JRadioButton(classList[k]);
			buttons.add(j);
			j.addActionListener(this);
			group.add(j);
			topBox.add(j);
		}*/
		
		Box profileBox = Box.createVerticalBox();
		profileBox.setBorder(BorderFactory.createTitledBorder("Character Profile"));
		
		JLabel nameLabel = new JLabel("Name: ");
		nameField = new JTextField("",20);
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		profileBox.add(namePanel);
		
		classgroup = new ButtonGroup();
		classbuttons = new HashMap<String,JRadioButton>(classList.length);
		JPanel buttonsPanel = new JPanel(new GridLayout(classList.length,0));
		buttonsPanel.setBorder(BorderFactory.createTitledBorder("Choose class"));
		for(int k = 0; k < classList.length; k++) {
			JRadioButton j = new JRadioButton(classList[k]);
			j.addActionListener(this);
			classbuttons.put(classList[k],j);
			classgroup.add(j);
			buttonsPanel.add(j);
		}
		profileBox.add(buttonsPanel);
		
		aligngroup = new ButtonGroup();
		alignbuttons = new HashMap<String,JRadioButton>(alignList.length);
		JPanel alignButtonPanel = new JPanel(new GridLayout(alignList.length,0));
		alignButtonPanel.setBorder(BorderFactory.createTitledBorder("Choose alignment"));
		for(int k = 0; k < alignList.length; k++) {
			JRadioButton j = new JRadioButton(alignList[k]);
			j.addActionListener(this);
			alignbuttons.put(alignList[k],j);
			alignButtonPanel.add(j);
			aligngroup.add(j);
		}
		profileBox.add(alignButtonPanel);
		
		classdesc = new JTextArea("Select a class to see description.");
		classdesc.setLineWrap(true);
		classdesc.setWrapStyleWord(true);
		classdesc.setEditable(false);
		classdesc.setBorder(BorderFactory.createTitledBorder("Class description"));
		classdesc.setOpaque(false);
		classdesc.setFont(profileBox.getFont());
	//	profileBox.add(new JScrollPane(classdesc));
		profileBox.add(classdesc);
		
		aligndesc = new JTextArea("Select an alignment to see description.");
		aligndesc.setLineWrap(true);
		aligndesc.setWrapStyleWord(true);
		aligndesc.setEditable(false);
		aligndesc.setBorder(BorderFactory.createTitledBorder("Alignment description"));
		aligndesc.setOpaque(false);
		aligndesc.setFont(profileBox.getFont());
	//	profileBox.add(new JScrollPane(classdesc));
		profileBox.add(aligndesc);
		
		classPanel = new ClassPanel();
		cache = new HashMap<String,ClassPanel>(5);

		scrollPane = new JScrollPane(classPanel);
		scrollPane.setVisible(false);
		

		JPanel content = new JPanel(new BorderLayout());
	/*	content.add(topBox,BorderLayout.NORTH);
		content.add(scrollPane,BorderLayout.CENTER);*/
		content.add(profileBox,BorderLayout.WEST);
		content.add(scrollPane,BorderLayout.CENTER);
		pane.add(content,BorderLayout.CENTER);
	//	showSelectPanel();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocation(100,100);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if(e.getSource() instanceof JRadioButton) {
		//	System.out.println(classbuttons.get((JRadioButton)e.getSource()));
		//	System.out.println(((JRadioButton)e.getSource()).getText());
			if(classbuttons.get(name) != null) {
				aligngroup.clearSelection();
				setView(name);
				if(classPanel.getAlignment() != null)
					aligngroup.setSelected(alignbuttons.get(classPanel.getAlignment()).getModel(),true);
				setClassDescText(name);
				setAlignDescText(classPanel.getAlignment());
			}
			else {
				classPanel.setAlignment(name);
				setAlignDescText(name);
			}
			pack();
		}
	/*	if(e.getSource() == classBox) {
			if(classBox.getSelectedIndex() == 0)
				showSelectPanel();
			else
				setView((String)classBox.getSelectedItem());
		}*/
		if(name.equals("Save as...")) {
			try {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("outest.thclass")));
			} catch(IOException ex){}
		}
		if(name.equals("Exit")) { System.exit(0);
		} else if(name.equals("About")) {
			JOptionPane.showMessageDialog(this,"Too Human Character Plotter (Java version) "+version+"\n"+
				"Copyright 2008 Imran Merchant\nContact: imerchant@gmail.com\n\n"+
				"Too Human Copyright 2008 Silicon Knights\n\n"+
				"Basic layout credit goes to maawdawg and \ntmunee of the excellent TooHuman.net forums.",
				"About TH Character Plotter",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private void setView(String classString) {
		if(cache.size() == 0 || cache.get(classString) == null) {
			classPanel = new ClassPanel(classString,this);
			cache.put(classString,classPanel);
		} else if(cache.get(classString) != null) {
			classPanel = cache.get(classString);
		}
		classPanel.setVisible(true);
		scrollPane.setVisible(true);
		scrollPane.setViewportView(classPanel);
	}
	private void showSelectPanel() {
		JPanel selectPanel = new JPanel();
		selectPanel.add(new JLabel("Select a class"));
		scrollPane.setViewportView(selectPanel);
		selectPanel.setVisible(true);
	}
	private void setClassDescText(String name) {
		if(name == null) {
			classdesc.setText("Select a class to see description.");
		} else if(name.equals("Berserker")) {
			classdesc.setText("ZERKER!");
		} else if(name.equals("BioEngineer")) {
			classdesc.setText("BIOENGINEER!");
		} else if(name.equals("Champion")) {
			classdesc.setText("CHAMPION!");
		} else if(name.equals("Commando")) {
			classdesc.setText("GUNS!");
		} else if(name.equals("Defender")) {
			classdesc.setText("TANK!");
		} else
			classdesc.setText("Select a class to see description.");
	}
	private void setAlignDescText(String name) {
		if(name == null) {
			aligndesc.setText("Select an alignment to see description.");
		} else if(name.equals("Human")) {
			aligndesc.setText("MAN!");
		} else if(name.equals("Cybernetic")) {
			aligndesc.setText("MACHINE!");
		} else
			aligndesc.setText("Select an alignment to see description.");
	}
	class THFileFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File f) {
			return f != null && (f.isDirectory() || f.getName().contains(".thclass"));
		}
		public String getDescription() {
			return "Too Human Character Plotter files (*.thclass)";
		}
	}
	public static void main(String...args) { new TooHumanCalc(); }
}