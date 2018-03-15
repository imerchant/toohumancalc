import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;
import java.io.*;

public class TooHumanCalc extends JFrame implements ActionListener,Serializable, ListCellRenderer, ChangeListener {
	public static final long serialVersionUID = -86546345547L;
	private ButtonGroup aligngroup,classMenuGroup;
	private ClassPanel classPanel;
	private DecimalFormat format = new DecimalFormat("##.000"), perc = new DecimalFormat("##%");
	private ExtFilefilter filter;
	private FindDialog find;
	private HashMap<String,ClassPanel> cache;
	private HashMap<String,JCheckBoxMenuItem> classMenuButtons,alignbuttons;
	private Icons icons;
	private JCheckBoxMenuItem clearItem, showProg, showItem, iconAlways;
	private JComboBox classCombo,alignCombo;
	private JComponent about,profile;
	private JFileChooser save,open;
	private JFrame readFrame;
	private JLabel progLabel;
	private JProgressBar hitBar, meleeBar, ballisticsBar, armorBar, loadBar,
						 hits[], melees[], ballistics[], armors[];
	private JSplitPane splitpane;
	private NotesArea classdesc, aligndesc;
	private NameField nameField;
	private boolean paintString = false, borderPainted = true, showPanel = false, multibar = true;
	private final String userdir = System.getProperty("user.dir");
	private static final String version = "v4.1";
	private String[] classList = {"Berserker",
								  "Defender",
								  "Champion",
								  "Commando",
								  "BioEngineer"},
					 alignList = {"Human",
					 			  "Cybernetic"};
	public TooHumanCalc(String args[]) {
		super("Too Human Skill Calculator "+version);
		boolean openFile = args.length > 0;
		
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		String classname = /**info[4].getClassName();//*/ UIManager.getSystemLookAndFeelClassName();
		try{UIManager.setLookAndFeel(classname);}catch(Exception e){}
				
/*	*/	String lafs[] = new String[info.length];
		for(int k = 0; k < lafs.length; k++)
			lafs[k] = info[k].getClassName();
		String option = (String)JOptionPane.showInputDialog(null,
			"These are the currently installed look and feels. Choose one.\nClicking \"Cancel\" will use the current system look and feel.",
			"Choose a look and feel.",JOptionPane.QUESTION_MESSAGE,null,lafs,classname);
		try{UIManager.setLookAndFeel(option);}catch(Exception e){}//*/
		
	//	setUndecorated(true);
	//	setResizable(false);
		long time1 = System.nanoTime();
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		java.net.URL iconURL = getClass().getResource("imgs/icon.png");
		if(iconURL != null) setIconImage(getToolkit().createImage(iconURL));
		
		JFrame dialog = new JFrame("Loading...");
	//	dialog.setUndecorated(true);
		dialog.setResizable(false);
		JPanel loadingPanel = new JPanel(new BorderLayout());
		JLabel loadingstatus = new JLabel("Status: ",SwingConstants.RIGHT);
		JLabel loadingLabel = new JLabel("Loading the calc. Please wait.",SwingConstants.CENTER);
		JLabel title = new JLabel("<html><b>Too Human Skill Calculator "+version,SwingConstants.CENTER);
		if(iconURL != null) { 
			title.setIcon(new ImageIcon(iconURL));
			dialog.setIconImage(getToolkit().createImage(iconURL));
		}
		Box loadingBar = Box.createHorizontalBox();
		loadBar = new JProgressBar(SwingConstants.HORIZONTAL,0,18);
		int progress = 0;
		loadBar.setIndeterminate(true);
		loadBar.setBorderPainted(true);
		loadBar.addChangeListener(this);
	//	loadBar.setStringPainted(true);
		Box labelPanel = Box.createHorizontalBox();
		labelPanel.add(Box.createHorizontalStrut(25));
		labelPanel.add(loadingstatus);
		labelPanel.add(loadingLabel);
		loadingBar.add(Box.createHorizontalStrut(15));
		loadingBar.add(loadBar);
		loadingBar.add(Box.createHorizontalStrut(15));
		progLabel = new JLabel("0%");
		loadingBar.add(progLabel);
		loadingBar.add(Box.createHorizontalStrut(15));
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFocusPainted(false);
		((JButton)loadingBar.add(cancelButton)).addActionListener(this);
		loadingBar.add(Box.createHorizontalStrut(15));
		loadingPanel.add(title,BorderLayout.NORTH);
		loadingPanel.add(labelPanel,BorderLayout.CENTER);
		loadingPanel.add(loadingBar,BorderLayout.SOUTH);
		dialog.setContentPane(loadingPanel);
	//	dialog.setSize(new Dimension(225,100));
		dialog.pack();
		loadBar.setSize(new Dimension(loadBar.getWidth(),loadBar.getHeight()+40));
	//	dialog.pack();
		Dimension size = dialog.getSize();
	//	dialog.setSize((int)size.getWidth()+25,(int)size.getHeight());
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		dialog.setVisible(true);
		loadBar.setIndeterminate(false);
		
		loadingLabel.setText("Loading icons...");
		long iconTime1 = System.nanoTime();
		icons = new Icons();
		icons.load();
		long iconTime2 = System.nanoTime();
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building filechoosers...");
		filter = new ExtFilefilter(".thclass","Too Human Skill Calculator files (*.thclass)");
		save = new JFileChooser(userdir);
		save.setDialogType(JFileChooser.SAVE_DIALOG);
		save.setDialogTitle("Save the current character");
		save.setFileFilter(filter);
		open = new JFileChooser(userdir);
		open.setDialogType(JFileChooser.OPEN_DIALOG);
		open.setDialogTitle("Open a character");
		open.setFileFilter(filter);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building menus...");
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
	/*	JMenu openMenu = new JMenu("Open");
		openMenu.setIcon(icons.get("folder"));
		openMenu.setMnemonic(KeyEvent.VK_O);
		fileMenu.add(openMenu);
		JMenu saveMenu = new JMenu("Save");
		saveMenu.setIcon(icons.get("floppy"));
		saveMenu.setMnemonic(KeyEvent.VK_S);
		fileMenu.add(saveMenu);*/
		JMenuItem openNew = new JMenuItem("Open...",KeyEvent.VK_O);
		openNew.setIcon(icons.get("document open"));
		openNew.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		fileMenu.add(openNew).addActionListener(this);
		JMenuItem saveNew = new JMenuItem("Save as...",KeyEvent.VK_S);
		saveNew.setIcon(icons.get("doc save"));
		saveNew.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		fileMenu.add(saveNew).addActionListener(this);
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit",KeyEvent.VK_X);
		exitItem.setIcon(icons.get("log out"));
		exitItem.setAccelerator(KeyStroke.getKeyStroke("alt X"));
		fileMenu.add(exitItem).addActionListener(this);
		menubar.add(fileMenu);
		JMenu classMenu = new JMenu("Class");
		classMenu.setMnemonic(KeyEvent.VK_C);
		menubar.add(classMenu);
		JMenu optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic(KeyEvent.VK_O);
		showProg = new JCheckBoxMenuItem("Show progression bar",icons.get("image loading"),true);
		showProg.setMnemonic(KeyEvent.VK_P);
		showProg.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
		optionsMenu.add(showProg).addActionListener(this);
		showItem = new JCheckBoxMenuItem("Show profile pane",icons.get("view full"),true);
		showItem.setMnemonic(KeyEvent.VK_H);
		showItem.setAccelerator(KeyStroke.getKeyStroke("ctrl alt H"));
		optionsMenu.add(showItem).addActionListener(this);
		iconAlways = new JCheckBoxMenuItem("Skill icons always enabled",icons.get("image missing"),false);
		iconAlways.setMnemonic(KeyEvent.VK_I);
		iconAlways.setDisplayedMnemonicIndex(6);
		iconAlways.setAccelerator(KeyStroke.getKeyStroke("ctrl I"));
		optionsMenu.add(iconAlways).addActionListener(this);
		menubar.add(optionsMenu);
		JMenu toolsMenu = new JMenu("Tools");
		toolsMenu.setMnemonic(KeyEvent.VK_T);
		JMenuItem exportItem = new JMenuItem("Export as PNG...",KeyEvent.VK_E);
		exportItem.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
		exportItem.setIcon(icons.get("image x"));
		toolsMenu.add(exportItem).addActionListener(this);
		JMenuItem findItem = new JMenuItem("Find-a-Skill...",KeyEvent.VK_F);
		findItem.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
		findItem.setIcon(icons.get("find"));
		toolsMenu.add(findItem).addActionListener(this);
		JMenu legacyMenu = new JMenu("Single open/save");
		legacyMenu.setIcon(icons.get("folder"));
		legacyMenu.setMnemonic(KeyEvent.VK_S);
		JMenuItem openItem = new JMenuItem("Open single...",KeyEvent.VK_P);
		openItem.setIcon(icons.get("folder open"));
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl alt O"));
		legacyMenu.add(openItem).addActionListener(this);
		JMenuItem saveItem = new JMenuItem("Save single...",KeyEvent.VK_A);
		saveItem.setIcon(icons.get("doc save as"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl alt S"));
		legacyMenu.add(saveItem).addActionListener(this);
		toolsMenu.add(legacyMenu);
		menubar.add(toolsMenu);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem helpItem = new JMenuItem("Help",KeyEvent.VK_H);
		helpItem.setIcon(icons.get("help"));
		helpItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		helpMenu.add(helpItem).addActionListener(this);
		JMenuItem readMeItem = new JMenuItem("View READ ME",KeyEvent.VK_R);
		readMeItem.setAccelerator(KeyStroke.getKeyStroke("F2"));
		readMeItem.setIcon(icons.get("text x"));
		helpMenu.add(readMeItem).addActionListener(this);
		helpMenu.addSeparator();
		JMenuItem aboutItem = new JMenuItem("About",KeyEvent.VK_A);
		aboutItem.setIcon(icons.get("icon20"));
		helpMenu.add(aboutItem).addActionListener(this);
		menubar.add(helpMenu);
		setJMenuBar(menubar);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building name field...");		
		JLabel nameLabel = new JLabel("Name: ");
		nameField = new NameField("",20);
		nameField.setIcons(icons);
		Box namePanel = Box.createHorizontalBox();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building basic profile box...");
		profile = new JPanel(new BorderLayout());
		profile.setBorder(BorderFactory.createTitledBorder("Character Profile"));
		profile.add(namePanel,BorderLayout.NORTH);
		Box profileBox = Box.createVerticalBox();
		profile.add(profileBox,BorderLayout.CENTER);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building class buttons...");
		JMenuItem resetItem = new JMenuItem("Reset",KeyEvent.VK_R);
		resetItem.setIcon(icons.get("refresh"));
		resetItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		resetItem.addActionListener(this);
		classMenu.add(resetItem);
		classMenu.addSeparator();
		classMenuGroup = new ButtonGroup();
		classMenuButtons = new HashMap<String,JCheckBoxMenuItem>(classList.length);
		JPanel buttonsPanel = new JPanel(new GridLayout(1,0));
		buttonsPanel.setBorder(BorderFactory.createTitledBorder("Class"));
		classCombo = new JComboBox();
		classCombo.addItem(" ");
		for(int k = 0; k < classList.length; k++) {
			String name = classList[k];
			JCheckBoxMenuItem j = new JCheckBoxMenuItem(name,icons.get(name+"_small"));
			j.setAccelerator(KeyStroke.getKeyStroke("ctrl "+(k+1)));
			j.setMnemonic(getMne(name));
			j.addActionListener(this);
			classMenuButtons.put(name,j);
			classMenuGroup.add(j);
			classMenu.add(j);
			classCombo.addItem(name);
		}
		/* This adds an interesting black border to the combobox in the Windows 7 system LAF.
		   Unsure how it translates to the others. How it looks in other LAfs may be
		   why I removed it in the first place.
		*/
		classCombo.setBorder(null);
		classCombo.setRenderer(this);
		classCombo.addActionListener(this);
		buttonsPanel.add(classCombo);
		profileBox.add(buttonsPanel);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building alignment buttons...");
		classMenu.addSeparator();
		aligngroup = new ButtonGroup();
		alignbuttons = new HashMap<String,JCheckBoxMenuItem>(alignList.length);
		JPanel alignButtonPanel = new JPanel(new GridLayout(1,0));
		alignButtonPanel.setBorder(BorderFactory.createTitledBorder("Alignment"));
		alignCombo = new JComboBox();
		alignCombo.addItem("  ");
		for(int k = 0; k < alignList.length; k++) {
			String name = alignList[k];
			JCheckBoxMenuItem j = new JCheckBoxMenuItem(name,icons.get(name+"_small"));
			j.setAccelerator(KeyStroke.getKeyStroke("ctrl "+(k+8)));
			j.addActionListener(this);
			j.setMnemonic(getMne(name));
			alignbuttons.put(name,j);
			aligngroup.add(j);
			classMenu.add(j);
			alignCombo.addItem(name);
		}
		aligngroup.add(clearItem = new JCheckBoxMenuItem("clear"));
		/* This adds an interesting black border to the combobox in the Windows 7 system LAF.
		   Unsure how it translates to the others. How it looks in other LAfs may be
		   why I removed it in the first place.
		*/
		alignCombo.setBorder(null);
		alignCombo.setRenderer(this);
		alignCombo.addActionListener(this);
		alignButtonPanel.add(alignCombo);
		profileBox.add(alignButtonPanel);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building class desc GUI...");
		classdesc = new NotesArea("Select a class to see description.");
		classdesc.setIcons(icons);
		classdesc.setLineWrap(true);
		classdesc.setWrapStyleWord(true);
		classdesc.setEditable(false);
		classdesc.setOpaque(false);
		classdesc.setFont(profileBox.getFont());
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building class stats GUI...");
		JPanel classStats = new JPanel(new BorderLayout());
		JPanel classLabels = new JPanel(new GridLayout(4,0));
		JPanel classBars = new JPanel(new GridLayout(4,0));
	//	JPanel specLabels = new JPanel(new BorderLayout());
		classLabels.add(new JLabel("HIT POINTS  "));
		classLabels.add(new JLabel("MELEE  "));
		classLabels.add(new JLabel("BALLISTICS  "));
		classLabels.add(new JLabel("ARMOR  "));
	//	specLabels.add(new JLabel("SPECS. "),BorderLayout.WEST);
		if(multibar) {
			classBars = new JPanel(new GridLayout(4,5));
			hits = new JProgressBar[5];
			melees = new JProgressBar[5];
			ballistics = new JProgressBar[5];
			armors = new JProgressBar[5];
			for(int k = 0; k < hits.length; k++) {
				hits[k] = new JProgressBar(SwingConstants.HORIZONTAL,0,1);
				melees[k] = new JProgressBar(SwingConstants.HORIZONTAL,0,1);
				ballistics[k] = new JProgressBar(SwingConstants.HORIZONTAL,0,1);
				armors[k] = new JProgressBar(SwingConstants.HORIZONTAL,0,1);
				hits[k].setString("");
				hits[k].setStringPainted(paintString);
				hits[k].setBorderPainted(borderPainted);
				melees[k].setString("");
				melees[k].setStringPainted(paintString);
				melees[k].setBorderPainted(borderPainted);
				ballistics[k].setString("");
				ballistics[k].setStringPainted(paintString);
				ballistics[k].setBorderPainted(borderPainted);
				armors[k].setString("");
				armors[k].setStringPainted(paintString);
				armors[k].setBorderPainted(borderPainted);
			}
			for(int k = 0; k < hits.length; k++)
				classBars.add(hits[k]);
			for(int k = 0; k < melees.length; k++)
				classBars.add(melees[k]);
			for(int k = 0; k < ballistics.length; k++)
				classBars.add(ballistics[k]);
			for(int k = 0; k < armors.length; k++)
				classBars.add(armors[k]);
			Dimension dim = new Dimension(16,15);
			for(int k = 0; k < hits.length; k++) {
				hits[k].setPreferredSize(dim);
				melees[k].setPreferredSize(dim);
				ballistics[k].setPreferredSize(dim);
				armors[k].setPreferredSize(dim);
			}
		} else {
			classBars = new JPanel(new GridLayout(4,0));
			classBars.add(hitBar = new JProgressBar(SwingConstants.HORIZONTAL,0,5));
			classBars.add(meleeBar = new JProgressBar(SwingConstants.HORIZONTAL,0,5));
			classBars.add(ballisticsBar = new JProgressBar(SwingConstants.HORIZONTAL,0,5));
			classBars.add(armorBar = new JProgressBar(SwingConstants.HORIZONTAL,0,5));
			hitBar.setStringPainted(paintString);
			meleeBar.setStringPainted(paintString);
			ballisticsBar.setStringPainted(paintString);
			armorBar.setStringPainted(paintString);
			hitBar.setString("HIT POINTS");
			meleeBar.setString("MELEE");
			ballisticsBar.setString("BALLISTICS");
			armorBar.setString("ARMOR");
			hitBar.setBorderPainted(borderPainted);
			meleeBar.setBorderPainted(borderPainted);
			ballisticsBar.setBorderPainted(borderPainted);
			armorBar.setBorderPainted(borderPainted);
			Dimension dim = new Dimension(80,15);
			hitBar.setPreferredSize(dim);
			meleeBar.setPreferredSize(dim);
			ballisticsBar.setPreferredSize(dim);
			armorBar.setPreferredSize(dim);
		}
	//	specLabels.add(specLabel = new JLabel(),BorderLayout.CENTER);
	/*	Dimension dim = new Dimension(100,hitBar.getPreferredSize().height);
		hitBar.setPreferredSize(dim);
		meleeBar.setPreferredSize(dim);
		ballisticsBar.setPreferredSize(dim);
		armorBar.setPreferredSize(dim);*/
		
		classStats.add(classLabels,BorderLayout.WEST);
		classStats.add(classBars,BorderLayout.CENTER);
	//	classStats.add(specLabels,BorderLayout.SOUTH);
		
		JPanel pClass = new JPanel(new GridLayout(1,0));
		pClass.setBorder(BorderFactory.createTitledBorder("Class description"));
		Box statBox = Box.createVerticalBox();
		statBox.add(classStats);
		statBox.add(Box.createVerticalStrut(6));
		statBox.add(new JSeparator(JSeparator.HORIZONTAL));
		statBox.add(Box.createVerticalStrut(3));
		statBox.add(classdesc);
		pClass.add(statBox);
		profileBox.add(pClass);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building alignment desc GUI...");
		aligndesc = new NotesArea("Select an alignment to see description.");
		aligndesc.setIcons(icons);
		aligndesc.setLineWrap(true);
		aligndesc.setWrapStyleWord(true);
		aligndesc.setEditable(false);
		aligndesc.setOpaque(false);
		aligndesc.setFont(profileBox.getFont());
		JPanel alignBox = new JPanel(new BorderLayout());
		alignBox.setBorder(BorderFactory.createTitledBorder("Alignment description"));
		alignBox.add(aligndesc,BorderLayout.CENTER);
		profileBox.add(alignBox);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building class panels...");
		long cachetime1 = System.nanoTime();
		classPanel = new ClassPanel();
		cache = new HashMap<String,ClassPanel>(5);
		for(String c: classList) {
			loadingLabel.setText("Building class panel: "+c);
			cache.put(c,new ClassPanel(c,icons));
			loadBar.setValue(++progress);
		}
		long cachetime2 = System.nanoTime();
		
		loadingLabel.setText("Creating main GUI...");
	//	splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,null,classPanel);
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,profile,classPanel);
		splitpane.setOneTouchExpandable(false);
		splitpane.setResizeWeight(0);
		splitpane.setDividerSize(0);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Building status bar...");
		JLabel status = new JLabel("For help, press F1. To view the README, F2.");
		status.setOpaque(false);
		status.setBorder(null);
		loadBar.setValue(++progress);
		
		loadingLabel.setText("Packing...");
	/*	JPanel combos = new JPanel();
		combos.add(classCombo);
		combos.add(alignCombo);
		combos.add(classStats);
		pane.add(combos,BorderLayout.NORTH);*/
		pane.add(splitpane,BorderLayout.CENTER);
		pane.add(status,BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocation(40,40);
		loadBar.setValue(++progress);
		
		if(openFile) {
			loadingLabel.setText("Opening file...");
			File f = new File(args[0]);
			if(filter.acceptFile(f))
				open(false,f);
		}
		loadingLabel.setText("Loading complete!");
		dialog.setVisible(false);
		dialog.dispose();
		
		setVisible(true);
		nameField.requestFocusInWindow();
		
		long time2 = System.nanoTime();
		System.out.println("Icon load time: " + (iconTime2-iconTime1)/1000000000.0);  
		System.out.println("Cache load time: " + (cachetime2-cachetime1)/1000000000.0);
		System.out.println("Full load time: " + (time2-time1)/1000000000.0);
		status.setText(status.getText() + " (Startup time: " + format.format((time2-time1)/1000000000.0) + "s)");
	/*	try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_1);
			robot.keyRelease(KeyEvent.VK_1);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch(Exception e) {}*/
	}
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if(name.equals("Exit")) { System.exit(0); }
		else if(name.equals("Cancel")) { System.exit(-1); }
		else if(e.getSource()==classCombo) {
			String c = (String)classCombo.getSelectedItem();
			if(classPanel.getClassString().equals(c) || c.equals(" ")) return;
			if(!c.equals(" ")) classCombo.removeItem(" ");
			setView(c);
		} else if(e.getSource()==alignCombo) {
			if(!showPanel) return;
			String a = (String)alignCombo.getSelectedItem();
	//		if(a == null) return;
			if(a.equals("  ")) {
				if(classPanel.getAlignment() != null)
					alignCombo.setSelectedItem(classPanel.getAlignment());
				return;
			}
			if(a.equals(classPanel.getAlignment())) return;
			classPanel.setAlignment(a);
			aligngroup.setSelected(alignbuttons.get(classPanel.getAlignment()).getModel(),true);
			setAlignDescText(a);
			classPanel.revalidate();
			classPanel.updateUI();
			repaint();
			pack();
		} else if(e.getSource() instanceof JCheckBoxMenuItem
				&& (classMenuButtons.get(name) != null || alignbuttons.get(name) != null)) {
			if(classPanel.getClassString().equals(name)) return;
			if(classMenuButtons.get(name) != null) {
				setView(name);
				if(!name.equals(" ")) classCombo.removeItem(" ");
			}
			else {
				if(!showPanel) return;
				if(name.equals(classPanel.getAlignment())) return;
				classPanel.setAlignment(name);
				alignCombo.setSelectedItem(classPanel.getAlignment());
				setAlignDescText(name);
			}
			classPanel.revalidate();
			classPanel.updateUI();
			repaint();
			pack();
		} else if(name.contains("Save")) {
			if(save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				save(name.contains("single"),save.getSelectedFile());
			}
		} else if(name.contains("Open")) {
			if(open.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				open(name.contains("single"),open.getSelectedFile());
			}
		} else if(name.equals("About")) {
			if(about == null) {
				about = new JPanel(new GridLayout(19,0));
				about.add(new JLabel("<html><u>Too Human Skill Calculator (Java version) "+version+"</u>"));
				about.add(new JLabel("Copyright 2010 Imran Merchant"));
				Box contact = Box.createHorizontalBox();
				contact.add(new JLabel("Contact: "));
				NameField email = new NameField("imerchant@gmail.com",icons);
				email.setSelectAllOnFocus(true);
				contact.add(email);
				about.add(contact);
				Box web = Box.createHorizontalBox();
				web.add(new JLabel("Website: "));
				NameField website = new NameField("http://pyreflies.nu/hench/TooHuman",icons);
				website.setSelectAllOnFocus(true);
				web.add(website);
				about.add(web);
				about.add(new JLabel(" "));
				about.add(new JLabel("<html>All <i>Too Human</i> trademarks and copyrights are retained by"));
				about.add(new JLabel("Silicon Knights and Microsoft."));
				about.add(new JLabel(" "));
				about.add(new JLabel("<html>Special thanks to <b>maawdawg</b>, <b>tmunee</b>, <b>MarkZ3</b>,"));
				about.add(new JLabel("<html>and <b>AKAtheDopeman</b> of the excellent SiliconKnights.net."));
				NameField sknet = new NameField("http://www.siliconknights.net",icons);
				sknet.setSelectAllOnFocus(true);
				about.add(sknet);
				about.add(new JLabel(" "));
				about.add(new JLabel("<html>Skill icons thanks to the hard work of <b>Axeman87</b>,"));
				about.add(new JLabel("another member of the great SK.net community."));
				about.add(new JLabel(" "));
				about.add(new JLabel("This software uses icons from the great Tango Desktop Project."));
				NameField tango = new NameField("http://tango.freedesktop.org/Tango_Desktop_Project",icons);
				tango.setSelectAllOnFocus(true);
				about.add(tango);
				about.add(new JLabel(" "));
				about.add(new JLabel("<html>Skill details taken from the retail version of <i>Too Human</i>."));
			}
			JOptionPane.showMessageDialog(this,about,"About Too Human Skill Calculator "+version,JOptionPane.INFORMATION_MESSAGE,icons.get("icon"));
		} else if(name.equals("Help")) {
			JOptionPane.showMessageDialog(this,"Select a class and alignment to view the trees.\n\n"+
				"To add/remove points from a skill node:\n"+
				"Add points: Left click or mouse wheel up.\n"+
				"Remove points: Right or Control+click or mouse wheel down.\n"+
				"Alt+click (both left and right) for additional options.\n\n"+
				"Save/Open normal vs. single:\n"+
				"Use \"single\" for legacy (below v2.2) plotter files.\n"+
				"New files save all classes, older files only contain one class.",
				"Help: Too Human Skill Calculator "+version,JOptionPane.INFORMATION_MESSAGE,
				icons.get("icon"));
		} else if(name.equals("Reset")) {
			classPanel.reset();
		} else if(name.equals("Show progression bar")) {
			classPanel.showProgressBar(showProg.isSelected());
			repaint();
		} else if(name.contains("Export")) {
			if(!showPanel) return;
			classPanel.exportAsPNG(this);
		} else if(name.contains("READ")) {
			if(readFrame == null) {
				InputStream in = getClass().getResourceAsStream("READ_ME.txt");
				if(in == null) {
					JOptionPane.showMessageDialog(this,"The READ ME file could not be found.",
						"Error reading READ ME",JOptionPane.ERROR_MESSAGE);
					return;
				}
				ArrayList<String> readme = new ArrayList<String>();
				Scanner scan = new Scanner(in);
				for(;scan.hasNextLine();readme.add(scan.nextLine()));
				scan.close();
				readFrame = new JFrame("READ_ME.txt");
				readFrame.setIconImage(icons.get("text x").getImage());
				Container pane = readFrame.getContentPane();
				pane.setLayout(new BorderLayout());
				NotesArea area = new NotesArea();
				area.setIcons(icons);
				area.setFont(getFont());
				for(int k = 0; k < readme.size(); k++)
					area.append(readme.get(k)+"\n");
				area.setCaretPosition(0);
				pane.add(new JScrollPane(area),BorderLayout.CENTER);
				JPanel closePanel = new JPanel();
				JButton closeButton = new JButton("Close",icons.get("stop"));
				closeButton.setFocusPainted(false);
				((JButton)closePanel.add(closeButton)).addActionListener(this);
				pane.add(closePanel,BorderLayout.SOUTH);
			}
			if(readFrame.isVisible())
				readFrame.requestFocus();
			else {
		//		readFrame.setLocation(150,150);
				readFrame.setSize(550,500);
				readFrame.setLocationRelativeTo(this);
				readFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
				readFrame.setVisible(true);
			}
		} else if(name.equals("Close")) {
			readFrame.setVisible(false);
		} else if(name.contains("Find")) {
			if(find == null) {
				find = new FindDialog(cache,icons);
			}
			if(find.isVisible())
				find.requestFocus();
			else {
				find.setLocationRelativeTo(this);
				find.setVisible(true);
			}
		} else if(name.contains("Show profile")) {
			splitpane.setLeftComponent(showItem.isSelected() ? profile : null);
			pack();
		} else if(name.contains("icons always")) {
			classPanel.setSkillIconAlwaysEnabled(iconAlways.isSelected());
			repaint();
		}
	}
	private void setView(String classString) {
		if(!showPanel) showPanel = true;
		splitpane.setRightComponent(null);
		aligngroup.setSelected(clearItem.getModel(),true);
		alignCombo.setSelectedIndex(0);
		if(cache.size() == 0 || cache.get(classString) == null) {
			classPanel = new ClassPanel(classString,icons);
			cache.put(classString,classPanel);
		} else if(cache.get(classString) != null) {
			classPanel = cache.get(classString);
		}
		classPanel.showProgressBar(showProg.isSelected());
		classPanel.setSkillIconAlwaysEnabled(iconAlways.isSelected());
		classPanel.setVisible(true);
		splitpane.setRightComponent(classPanel);
		if(classPanel.getAlignment() != null) {
			aligngroup.setSelected(alignbuttons.get(classPanel.getAlignment()).getModel(),true);
			alignCombo.setSelectedItem(classPanel.getAlignment());
		} else {
			aligngroup.setSelected(clearItem.getModel(),true);
			alignCombo.setSelectedIndex(0);
		}
		setClassDescText(classString);
		setAlignDescText(classPanel.getAlignment());
		classMenuGroup.setSelected(classMenuButtons.get(classString).getModel(),true);
		classCombo.setSelectedItem(classString);
		classPanel.revalidate();
		classPanel.updateUI();
		repaint();
		pack();
	}
	private void save(boolean single, File file) {
		if(!filter.acceptFile(file))
			file = new File(file.getPath()+".thclass");
		String[] choices = new String[] {"Overwrite","Choose another file","Cancel"};
		while(file.exists()) {
			int choice = JOptionPane.showOptionDialog(this,"A file with this name already exists.","Conflict!",
				JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
				icons.get("dialog warning"),choices,choices[1]);
			if(choice == 0) break;
			else if(choice == 1) {
				if(save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
					file = save.getSelectedFile();
					if(!filter.acceptFile(file))
						file = new File(file.getPath()+".thclass");
				} else return;
			} else return;
		}
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			if(single) {
				ClassExport export = classPanel.export();
				export.name = nameField.getText();
		//		System.out.println(export);
				out.writeObject(export);
			} else {
				ClassExport[] export = new ClassExport[5];
				export[0] = classPanel.export();
				export[0].name = nameField.getText();
				int k = 1;
				for(ClassPanel c: cache.values())
					if(!classPanel.equals(c))
						export[k++] = c.export();
				for(ClassExport c: export) {
		//			System.out.println(c);
					out.writeObject(c);
				}
			}
			out.flush();
			out.close();
		} catch(IOException ex){
			JOptionPane.showMessageDialog(this,"There was an error saving the file:\n"+ex.getMessage(),
				"Error saving file!",JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} catch(NullPointerException ex) {
			JOptionPane.showMessageDialog(this,"There was an error saving the file:\n"+ex.getMessage(),
				"Error saving file!",JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	private void open(boolean single, File file) {
		if(!showPanel) showPanel = true;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			if(single) {
				ClassExport export = (ClassExport)in.readObject();
				splitpane.setRightComponent(null);
				classPanel = new ClassPanel(export,icons);
				cache.put(classPanel.getClassString(),classPanel);
				setView(classPanel.getClassString());
				nameField.setText(export.name);
			} else {
				ClassExport[] export = new ClassExport[5];
		/*		int max = 0;
				for(max = 0; max < 5; max++) {
					if(in.available() <= 0) {
						JOptionPane.showMessageDialog(this,"File doesn't contain all classes.\nLoading what we can.",
							"Error reading file!",JOptionPane.ERROR_MESSAGE);
						break;
					}
					Object inobj = in.readObject();
					export[max] = (ClassExport)inobj;
				}*/
				for(int k = 0; k < 5; export[k++] = (ClassExport)in.readObject());
				splitpane.setRightComponent(null);
				classPanel = new ClassPanel(export[0],icons);
				cache.put(classPanel.getClassString(),classPanel);
				for(int k = 1; k < 5; cache.put(export[k].classString,new ClassPanel(export[k++],icons)));
				setView(classPanel.getClassString());
				nameField.setText(export[0].name);
			}
			pack();
			in.close();
		} catch(EOFException e) {
			JOptionPane.showMessageDialog(this,"Reached EOF!","Error!",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch(IOException e){
			JOptionPane.showMessageDialog(this,"There was an IOException opening the file:\n"+e.getMessage(),
				"Error opening file!",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch(ClassNotFoundException e){
			JOptionPane.showMessageDialog(this,"There was a ClassNotFoundException opening the file:\n"+e.getMessage(),
				"Error opening file!",JOptionPane.ERROR_MESSAGE);
		}
	}
	@Override public void pack() {
		if(getExtendedState() != JFrame.NORMAL) return;
		super.pack();
		splitpane.resetToPreferredSizes();
		if(isVisible() && getExtendedState() == JFrame.NORMAL) {
			Dimension size = getSize();
			Point pos = getLocationOnScreen();
			Dimension screen = getToolkit().getScreenSize();
			
			if(size.width + pos.x > screen.width)
				setSize(screen.width - pos.x,size.height);
			size = getSize();
			pos = getLocationOnScreen();
			if(size.height + pos.y > screen.height)
				setSize(size.width,screen.height - pos.y-30);
		}
	}
	private void setBars(JProgressBar[] array, int val) {
		for(int k = 0; k < array.length; k++) {
			array[k].setValue(k < val ? 1 : 0);
		}
	}
	private void setClassDescText(String name) {
		if(name == null) {
			if(multibar) {
				setBars(hits,0);
				setBars(melees,0);
				setBars(ballistics,0);
				setBars(armors,0);
			} else {
				hitBar.setValue(0);
				meleeBar.setValue(0);
				ballisticsBar.setValue(0);
				armorBar.setValue(0);
			}
	//		specLabel.setText("");
			classdesc.setText("Select a class to see description.");
		} else if(name.equals("Berserker")) {
			if(multibar) {
				setBars(hits,2);
				setBars(melees,5);
				setBars(ballistics,1);
				setBars(armors,2);
			} else {
				hitBar.setValue(2);
				meleeBar.setValue(5);
				ballisticsBar.setValue(1);
				armorBar.setValue(2);
			}
	//		specLabel.setText("<html>Damage dealing<p>Dual Wield Weapons");
			classdesc.setText("The Berserker delights in the fury of close combat, forgoing defensive "+
				"strategy in order to adopt all-out offense. Adopting a twin-blade fighting style and infused "+
				"with the spirit of the bear, a Berserker will wade into battle for the glory of ODIN.");
		} else if(name.equals("BioEngineer")) {
			if(multibar) {
				setBars(hits,5);
				setBars(melees,2);
				setBars(ballistics,2);
				setBars(armors,2);
			} else {
				hitBar.setValue(5);
				meleeBar.setValue(2);
				ballisticsBar.setValue(2);
				armorBar.setValue(2);
			}
	//		specLabel.setText("<html>Regeneration<p>Healing");
			classdesc.setText("A master of cybernetics as well as mundane combat, the BioEngineer repairs damage "+
				"sustained on the battlefield, increasing health bonuses of himself and his allies, enabling them "+
				"to take the fight directly into the heart of the enemy.");
		} else if(name.equals("Champion")) {
			if(multibar) {
				setBars(hits,3);
				setBars(melees,3);
				setBars(ballistics,3);
				setBars(armors,3);
			} else {
				hitBar.setValue(3);
				meleeBar.setValue(3);
				ballisticsBar.setValue(3);
				armorBar.setValue(3);
			}
	//		specLabel.setText("<html>Air Combat<p>Critical Strikes");
			classdesc.setText("The Champion represents ODIN\'s divine force of retribution. A strong warrior able "+
				"to deal out a wide variety of caustic force field and anti-gravity based effects, increasing the "+
				"combat effectiveness of his allies. One-handed weapons are the Champion's chosen tools of combat.");
		} else if(name.equals("Commando")) {
			if(multibar) {
				setBars(hits,2);
				setBars(melees,1);
				setBars(ballistics,5);
				setBars(armors,2);
			} else {
				hitBar.setValue(2);
				meleeBar.setValue(1);
				ballisticsBar.setValue(5);
				armorBar.setValue(2);
			}
	//		specLabel.setText("<html>Explosives Master<p>Spider Master");
			classdesc.setText("Favoring technological gadgetry and stand-off methods of warfare, the Commando "+
				"specializes in the use of mines, counter-measures, demolitions, and rifles. Able to support "+
				"his allies through long-range harrying tactics, the Commando is truly a force to be reckoned with.");
		} else if(name.equals("Defender")) {
			if(multibar) {
				setBars(hits,4);
				setBars(melees,2);
				setBars(ballistics,2);
				setBars(armors,4);
			} else {
				hitBar.setValue(4);
				meleeBar.setValue(2);
				ballisticsBar.setValue(2);
				armorBar.setValue(4);
			}
	//		specLabel.setText("<html>Defensive toughness<p>Hammer & Shield</html>");
			classdesc.setText("With the blessings of ODIN and runes of protection, the Defender is the backbone of "+
				"the Aesir\'s defense. Heavy armor enables the Defender to absorb a tremendous amount of damage, "+
				"leaving his allies to take the battle to the enemy unharried.");
		} else {
			if(multibar) {
				setBars(hits,0);
				setBars(melees,0);
				setBars(ballistics,0);
				setBars(armors,0);
			} else {
				hitBar.setValue(0);
				meleeBar.setValue(0);
				ballisticsBar.setValue(0);
				armorBar.setValue(0);
			}
	//		specLabel.setText("");
			classdesc.setText("Select a class to see description.");
		}
	}
	private void setAlignDescText(String name) {
		if(name == null) {
			aligndesc.setText("Select an alignment to see description.");
		} else if(name.equals("Human")) {
			aligndesc.setText("Human: the Combo Mastery path, with combo-driven spiritual ruiners and access to an additional "+
				"combo level. This path provides highly customizable weapons and focuses on combo efficiency.");
		} else if(name.equals("Cybernetic")) {
			aligndesc.setText("Cybernetic: the Damage Mastery path, with access to cannons and bonus health. Ruiners are "+
				"found on weapons. This path is damage focused.");
		} else
			aligndesc.setText("Select an alignment to see description.");
	}
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean hasFocus) {
		String c = (String)value;
	//	if(value == null) return null;
	//	System.out.println(c + " " + index + " " + selected + " " + hasFocus);
		if(c.equals(" ")) {
			JMenuItem label = new JMenuItem("Select a class",icons.get("help"));
			label.setOpaque(selected);
			return label;
		} else if(c.equals("  ")) {
			JMenuItem label = new JMenuItem("Select an alignment",icons.get("help"));
			label.setOpaque(selected);
			return label;
		}
		JMenuItem label = new JMenuItem(c,icons.get(c+"_30"));
	//	label.setOpaque(selected && index != -1);
	//	label.setOpaque(true);
	//	if(selected) label.setBackground(Color.blue);
		if(selected) {
	//		label.setOpaque(index != -1);
			label.setOpaque(true);
			label.setBackground(list.getSelectionBackground());
			label.setForeground(list.getSelectionForeground());
		} else {
			label.setOpaque(false);
			label.setBackground(list.getBackground());
			label.setForeground(list.getForeground());
		}
		return label;
	}
	private int getMne(String s) {
		if(s.equals("Berserker")) return KeyEvent.VK_B;
		else if(s.equals("Defender")) return KeyEvent.VK_D;
		else if(s.equals("Champion")) return KeyEvent.VK_C;
		else if(s.equals("Commando")) return KeyEvent.VK_O;
		else if(s.equals("BioEngineer")) return KeyEvent.VK_E;
		else if(s.equals("Human")) return KeyEvent.VK_H;
		else if(s.equals("Cybernetic")) return KeyEvent.VK_Y;
		else return -1;
	}
	public void stateChanged(ChangeEvent e) {
		progLabel.setText(perc.format(loadBar.getPercentComplete()));
	}
	public static void main(String[] args) {
//		JFrame.setDefaultLookAndFeelDecorated(true);
		new TooHumanCalc(args);
	}
}