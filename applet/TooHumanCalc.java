import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;
import java.io.*;

public class TooHumanCalc extends JFrame implements ActionListener, java.io.Serializable {
	public static final long serialVersionUID = -86546345547L;
//	private JComboBox classBox;
	private ClassPanel classPanel;
//	private JScrollPane scrollPane;
//	private JViewport viewport;
	private JSplitPane splitpane;
	private HashMap<String,JRadioButtonMenuItem> classMenuButtons,classbuttons,alignbuttons;
	private JRadioButtonMenuItem clearItem;
	private ButtonGroup classgroup, aligngroup,classMenuGroup;
	private JTextArea classdesc, aligndesc;
	private JTextField nameField;
	private JFileChooser save,open;
	private HashMap<String,ClassPanel> cache;
	private THFileFilter filter = new THFileFilter();
	private JProgressBar hitBar,meleeBar,ballisticsBar,armorBar;
	private JProgressBar[] hits, melees, ballistics, armors;
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
	public TooHumanCalc() {
		super("Too Human Character Plotter "+version);
	//	long time1 = System.nanoTime();
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		String classname = /**info[3].getClassName();//*/ UIManager.getSystemLookAndFeelClassName();
		try{UIManager.setLookAndFeel(classname);}catch(Exception e){}
		
	//	setUndecorated(true);
	//	setResizable(false);
		
		java.net.URL iconURL = getClass().getResource("imgs/icon.png");
		if(iconURL != null) setIconImage(getToolkit().createImage(iconURL));
		
		JFrame dialog = new JFrame("Loading...");
		dialog.setUndecorated(true);
		dialog.setResizable(false);
		JPanel loadingPanel = new JPanel(new BorderLayout());
		JLabel loadingstatus = new JLabel("Status: ",SwingConstants.RIGHT);
		JLabel loadingLabel = new JLabel("Loading. Please wait.",SwingConstants.CENTER);
		JLabel title = new JLabel("<html><b>Too Human Character Plotter "+version,SwingConstants.CENTER);
		if(iconURL != null) { 
			title.setIcon(new ImageIcon(iconURL));
			dialog.setIconImage(getToolkit().createImage(iconURL));
		}
		JPanel loadingBar = new JPanel();
		JProgressBar loadBar = new JProgressBar(SwingConstants.HORIZONTAL,0,15);
		loadBar.setIndeterminate(true);
		loadBar.setBorderPainted(false);
	//	loadBar.setStringPainted(true);
		loadingBar.add(loadBar);
		JButton cancel = new JButton("Cancel");
		((JButton)loadingBar.add(cancel)).addActionListener(this);
		JPanel labelPanel = new JPanel();
		labelPanel.add(loadingstatus);
		labelPanel.add(loadingLabel);
		loadingPanel.add(title,BorderLayout.NORTH);
		loadingPanel.add(labelPanel,BorderLayout.CENTER);
		loadingPanel.add(loadingBar,BorderLayout.SOUTH);
		dialog.setContentPane(loadingPanel);
	//	dialog.setSize(new Dimension(225,100));
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		loadBar.setIndeterminate(false);
		
		loadingLabel.setText("Loading icons...");
		icons = new Icons();
		icons.load();
		loadBar.setValue(1);
		
		loadingLabel.setText("Building filechoosers...");
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		save = new JFileChooser();
		save.setDialogType(JFileChooser.SAVE_DIALOG);
		save.setDialogTitle("Save the current character");
		save.setFileFilter(filter);
		open = new JFileChooser();
		open.setDialogType(JFileChooser.OPEN_DIALOG);
		open.setDialogTitle("Open a character");
		open.setFileFilter(filter);
		loadBar.setValue(2);
		
		loadingLabel.setText("Building menus...");
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenu openMenu = new JMenu("Open");
		openMenu.setMnemonic(KeyEvent.VK_O);
		fileMenu.add(openMenu);
		JMenu saveMenu = new JMenu("Save");
		saveMenu.setMnemonic(KeyEvent.VK_S);
		fileMenu.add(saveMenu);
		JMenuItem openNew = new JMenuItem("Open...",KeyEvent.VK_O);
		openNew.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		openMenu.add(openNew).addActionListener(this);
		JMenuItem openItem = new JMenuItem("Open single...",KeyEvent.VK_P);
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl shift O"));
		openMenu.add(openItem).addActionListener(this);
		JMenuItem saveNew = new JMenuItem("Save as...",KeyEvent.VK_S);
		saveNew.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		saveMenu.add(saveNew).addActionListener(this);
		JMenuItem saveItem = new JMenuItem("Save single...",KeyEvent.VK_A);
		saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl shift S"));
		saveMenu.add(saveItem).addActionListener(this);
		JMenuItem exportItem = new JMenuItem("Export class...",KeyEvent.VK_E);
		exportItem.setAccelerator(KeyStroke.getKeyStroke("ctrl e"));
		fileMenu.addSeparator();
		fileMenu.add(exportItem).addActionListener(this);
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit",KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke("alt X"));
		fileMenu.add(exitItem).addActionListener(this);
		menubar.add(fileMenu);
		JMenu classMenu = new JMenu("Class");
		classMenu.setMnemonic(KeyEvent.VK_C);
		menubar.add(classMenu);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem helpItem = new JMenuItem("Help",KeyEvent.VK_H);
		helpItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		helpMenu.add(helpItem).addActionListener(this);
		helpMenu.addSeparator();
		helpMenu.add(new JMenuItem("About",KeyEvent.VK_A)).addActionListener(this);
		menubar.add(helpMenu);
		setJMenuBar(menubar);
		loadBar.setValue(3);
		
		loadingLabel.setText("Sorting class list...");
		Arrays.sort(classList);
		loadBar.setValue(4);
		
		loadingLabel.setText("Building name field...");		
		JLabel nameLabel = new JLabel("Name: ");
		nameField = new JTextField("",20);
		Box namePanel = Box.createHorizontalBox();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		loadBar.setValue(5);
		
		loadingLabel.setText("Building basic profile box...");
		JPanel profile = new JPanel(new BorderLayout());
		profile.setBorder(BorderFactory.createTitledBorder("Character Profile"));
		profile.add(namePanel,BorderLayout.NORTH);
		Box profileBox = Box.createVerticalBox();
		profile.add(profileBox,BorderLayout.CENTER);
		loadBar.setValue(6);
		
		loadingLabel.setText("Building class buttons...");
		classgroup = new ButtonGroup();
		classMenuGroup = new ButtonGroup();
		classbuttons = new HashMap<String,JRadioButtonMenuItem>(classList.length);
		classMenuButtons = new HashMap<String,JRadioButtonMenuItem>(classList.length);
		JPanel buttonsPanel = new JPanel(new GridLayout(classList.length,0));
		buttonsPanel.setBorder(BorderFactory.createTitledBorder("Choose class"));
		for(int k = 0; k < classList.length; k++) {
			String name = classList[k];
			JRadioButtonMenuItem j = new JRadioButtonMenuItem(name,icons.get(name+"_30"));
			j.addActionListener(this);
			classbuttons.put(name,j);
			classgroup.add(j);
			buttonsPanel.add(j);
			j.setOpaque(false);
			JRadioButtonMenuItem ji = new JRadioButtonMenuItem(name,icons.get(name+"_small"));
			ji.setAccelerator(KeyStroke.getKeyStroke("ctrl F"+(k+1)));
			ji.addActionListener(this);
			classMenuButtons.put(name,ji);
			classMenuGroup.add(ji);
			classMenu.add(ji);
		}
		profileBox.add(buttonsPanel);
		loadBar.setValue(7);
		
		loadingLabel.setText("Building alignment buttons...");
		aligngroup = new ButtonGroup();
		alignbuttons = new HashMap<String,JRadioButtonMenuItem>(alignList.length);
		JPanel alignButtonPanel = new JPanel(new GridLayout(alignList.length,0));
		alignButtonPanel.setBorder(BorderFactory.createTitledBorder("Choose alignment"));
		for(int k = 0; k < alignList.length; k++) {
			String name = alignList[k];
			JRadioButtonMenuItem j = new JRadioButtonMenuItem(name,icons.get(name+"_30"));
			j.addActionListener(this);
			alignbuttons.put(name,j);
			alignButtonPanel.add(j);
			aligngroup.add(j);
			j.setOpaque(false);
		}
		aligngroup.add(clearItem = new JRadioButtonMenuItem("clear"));
		profileBox.add(alignButtonPanel);
		loadBar.setValue(8);
		
		loadingLabel.setText("Building class desc GUI...");
		classdesc = new JTextArea("Select a class to see description.");
		classdesc.setLineWrap(true);
		classdesc.setWrapStyleWord(true);
		classdesc.setEditable(false);
		classdesc.setOpaque(false);
		classdesc.setFont(profileBox.getFont());
		loadBar.setValue(9);
		
	/*	hits = new JProgressBar[5];
		melees = new JProgressBar[5];
		ballistics = new JProgressBar[5];
		armors = new JProgressBar[5];
		for(int k = 0; k < hits.length; k++) {
			hits[k] = new JProgressBar(SwingConstants.HORIZONTAL,0,1);
			melees[k] = new JProgressBar(SwingConstants.HORIZONTAL,0,1);
			ballistics[k] = new JProgressBar(SwingConstants.HORIZONTAL,0,1);
			armors[k] = new JProgressBar(SwingConstants.HORIZONTAL,0,1);
			hits[k].setString("");
		//	hits[k].setStringPainted(true);
			hits[k].setBorderPainted(true);
			melees[k].setString("");
		//	melees[k].setStringPainted(true);
			melees[k].setBorderPainted(true);
			ballistics[k].setString("");
		//	ballistics[k].setStringPainted(true);
			ballistics[k].setBorderPainted(true);
			armors[k].setString("");
		//	armors[k].setStringPainted(true);
			armors[k].setBorderPainted(true);
		}*/
		loadingLabel.setText("Building class stats GUI...");
		JPanel classStats = new JPanel(new BorderLayout());
		JPanel classLabels = new JPanel(new GridLayout(4,0));
		JPanel classBars = new JPanel(new GridLayout(4,0));
	//	JPanel classBars = new JPanel(new GridLayout(4,5));
		JPanel specLabels = new JPanel(new GridLayout(0,2));
		classLabels.add(new JLabel("HIT POINTS  "));
		classLabels.add(new JLabel("MELEE  "));
		classLabels.add(new JLabel("BALLISTICS  "));
		classLabels.add(new JLabel("ARMOR  "));
		specLabels.add(new JLabel("SPECIALIZATIONS "));
	/*	for(int k = 0; k < hits.length; k++)
			classBars.add(hits[k]);
		for(int k = 0; k < melees.length; k++)
			classBars.add(melees[k]);
		for(int k = 0; k < ballistics.length; k++)
			classBars.add(ballistics[k]);
		for(int k = 0; k < armors.length; k++)
			classBars.add(armors[k]);*/
		classBars.add(hitBar = new JProgressBar(SwingConstants.HORIZONTAL,0,5));
		classBars.add(meleeBar = new JProgressBar(SwingConstants.HORIZONTAL,0,5));
		classBars.add(ballisticsBar = new JProgressBar(SwingConstants.HORIZONTAL,0,5));
		classBars.add(armorBar = new JProgressBar(SwingConstants.HORIZONTAL,0,5));
		specLabels.add(specLabel = new JLabel());
	/*	hitBar.setStringPainted(true);
		meleeBar.setStringPainted(true);
		ballisticsBar.setStringPainted(true);
		armorBar.setStringPainted(true);
		hitBar.setString(" ");
		meleeBar.setString(" ");
		ballisticsBar.setString(" ");
		armorBar.setString(" ");
		hitBar.setBorderPainted(false);
		meleeBar.setBorderPainted(false);
		ballisticsBar.setBorderPainted(false);
		armorBar.setBorderPainted(false);*/
	/*	hitBar.setString("HIT POINTS");
		meleeBar.setString("MELEE");
		ballisticsBar.setString("BALLISTICS");
		armorBar.setString("ARMOR");*/
		
	/*	Dimension dim = new Dimension(20,hits[0].getPreferredSize().height);
		for(int k = 0; k < hits.length; k++) {
			hits[k].setPreferredSize(dim);
			melees[k].setPreferredSize(dim);
			ballistics[k].setPreferredSize(dim);
			armors[k].setPreferredSize(dim);
		}*/
		
		classStats.add(classLabels,BorderLayout.WEST);
		classStats.add(classBars,BorderLayout.CENTER);
		classStats.add(specLabels,BorderLayout.SOUTH);
		
		Box pClass = Box.createVerticalBox();
		pClass.setBorder(BorderFactory.createTitledBorder("Class description"));
		pClass.add(classStats);
		pClass.add(classdesc);
		loadBar.setValue(10);
		
		loadingLabel.setText("Building alignment desc GUI...");
		aligndesc = new JTextArea("Select an alignment to see description.");
		aligndesc.setLineWrap(true);
		aligndesc.setWrapStyleWord(true);
		aligndesc.setEditable(false);
		aligndesc.setBorder(BorderFactory.createTitledBorder("Alignment description"));
		aligndesc.setOpaque(false);
		aligndesc.setFont(profileBox.getFont());
		loadBar.setValue(11);
			
		Box descBox = Box.createVerticalBox();
		descBox.add(pClass);
		descBox.add(aligndesc);
		
		profileBox.add(descBox);
		
		loadingLabel.setText("Preloading class panels...");
		classPanel = new ClassPanel();
		cache = new HashMap<String,ClassPanel>(5);
		for(String c: classList)
			cache.put(c,new ClassPanel(c,this,icons));
		loadBar.setValue(12);
		
		loadingLabel.setText("Creating main GUI...");
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,profile,classPanel);
		splitpane.setOneTouchExpandable(false);
		splitpane.setResizeWeight(0);
		splitpane.setDividerSize(0);
		loadBar.setValue(13);
		
		loadingLabel.setText("Building status bar...");
		JLabel status = new JLabel("Strike F1 for help.");
		status.setOpaque(false);
		loadBar.setValue(14);
		
		loadingLabel.setText("Packing...");
		pane.add(splitpane,BorderLayout.CENTER);
		pane.add(status,BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocation(0,50);
		loadBar.setValue(15);
		
		loadingLabel.setText("Loading complete!");
		dialog.setVisible(false);
		dialog.dispose();
		
		setVisible(true);
		
	//	long time2 = System.nanoTime();
	//	System.out.println((time2-time1)/1000000000.0);
	}
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if(name.equals("Exit")) { System.exit(0); }
		else if(name.equals("Cancel")) { System.exit(-1); }
		else if(e.getSource() instanceof JRadioButton || e.getSource() instanceof JRadioButtonMenuItem ||
			classbuttons.get(name) != null) {
			if(classPanel.getClassString().equals(name)) return;
			if(classbuttons.get(name) != null) {
				aligngroup.setSelected(clearItem.getModel(),true); // Workaround for ButtonGroup.clearSelection()
				setView(name);
				if(classPanel.getAlignment() != null)
					aligngroup.setSelected(alignbuttons.get(classPanel.getAlignment()).getModel(),true);
				setClassDescText(name);
				setAlignDescText(classPanel.getAlignment());
				classMenuGroup.setSelected(classMenuButtons.get(name).getModel(),true);
				classgroup.setSelected(classbuttons.get(name).getModel(),true);
			}
			else {
				if(name.equals(classPanel.getAlignment())) return;
				classPanel.setAlignment(name);
				setAlignDescText(name);
			}
			classPanel.revalidate();
			classPanel.updateUI();
			repaint();
			pack();
		} else if(name.equals("Save single...")) {
			if(save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File file = new File(save.getSelectedFile().getPath()+".thclass");
				try {
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
					ClassExport export = classPanel.export();
					export.name = nameField.getText();
			//		System.out.println(export);
					out.writeObject(export);
					out.flush();
					out.close();
				} catch(IOException ex){
					JOptionPane.showMessageDialog(this,"There was an error saving the file:\n"+ex.getMessage(),
						"Error saving file!",JOptionPane.ERROR_MESSAGE);
				} catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(this,"There was an error saving the file:\n"+ex.getMessage(),
						"Error saving file!",JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if(name.equals("Save as...")) {
			if(save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File file = new File(save.getSelectedFile().getPath()+".thclass");
				try {
					ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
					ClassExport[] export = new ClassExport[5];
					export[0] = classPanel.export();
					export[0].name = nameField.getText();
					int k = 1;
					for(ClassPanel c: cache.values())
						if(!classPanel.equals(c))
							export[k++] = c.export();
					for(ClassExport c: export) {
		//				System.out.println(c);
						out.writeObject(c);
					}
					out.flush();
					out.close();
				} catch(IOException ex){
					JOptionPane.showMessageDialog(this,"There was an error saving the file:\n"+ex.getMessage(),
						"Error saving file!",JOptionPane.ERROR_MESSAGE);
				} catch(NullPointerException ex) {
					JOptionPane.showMessageDialog(this,"There was an error saving the file:\n"+ex.getMessage(),
						"Error saving file!",JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if(name.equals("Open...")) {
			if(open.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File file = open.getSelectedFile();
				try {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
					ClassExport[] export = new ClassExport[5];
					for(int k = 0; k < 5; export[k++] = (ClassExport)in.readObject());
					splitpane.setRightComponent(null);
					classPanel = new ClassPanel(export[0],this,icons);
					cache.put(classPanel.getClassString(),classPanel);
					for(int k = 1; k < 5; cache.put(export[k].classString,new ClassPanel(export[k++],this,icons)));
					classgroup.setSelected(classbuttons.get(classPanel.getClassString()).getModel(),true);
					classMenuGroup.setSelected(classMenuButtons.get(classPanel.getClassString()).getModel(),true);
					if(classPanel.getAlignment() != null)
						aligngroup.setSelected(alignbuttons.get(classPanel.getAlignment()).getModel(),true);
					classPanel.setVisible(true);
					splitpane.setRightComponent(classPanel);
					nameField.setText(export[0].name);
					setClassDescText(export[0].classString);
					setAlignDescText(export[0].alignString);
					pack();
					in.close();
				} catch(IOException ex){
					JOptionPane.showMessageDialog(this,"There was an error opening the file:\n"+ex.getMessage(),
						"Error opening file!",JOptionPane.ERROR_MESSAGE);
				} catch(ClassNotFoundException ex){
					JOptionPane.showMessageDialog(this,"There was an error opening the file:\n"+ex.getMessage(),
						"Error opening file!",JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if(name.equals("Open single...")) {
			if(open.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File file = open.getSelectedFile();
				try {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
					ClassExport export = (ClassExport)in.readObject();
					splitpane.setRightComponent(null);
					classPanel = new ClassPanel(export,this,icons);
					cache.put(classPanel.getClassString(),classPanel);
					classgroup.setSelected(classbuttons.get(classPanel.getClassString()).getModel(),true);
					classMenuGroup.setSelected(classMenuButtons.get(classPanel.getClassString()).getModel(),true);
					if(classPanel.getAlignment() != null)
						aligngroup.setSelected(alignbuttons.get(classPanel.getAlignment()).getModel(),true);
					classPanel.setVisible(true);
					splitpane.setRightComponent(classPanel);
					nameField.setText(export.name);
					setClassDescText(export.classString);
					setAlignDescText(export.alignString);
					pack();
					in.close();
				} catch(IOException ex){
					JOptionPane.showMessageDialog(this,"There was an error opening the file:\n"+ex.getMessage(),
						"Error opening file!",JOptionPane.ERROR_MESSAGE);
				} catch(ClassNotFoundException ex){
					JOptionPane.showMessageDialog(this,"There was an error opening the file:\n"+ex.getMessage(),
						"Error opening file!",JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if(name.equals("About")) {
			JOptionPane.showMessageDialog(this,"Too Human Character Plotter (Java version) "+version+"\n"+
				"Copyright 2008 Imran Merchant\nContact: imerchant@gmail.com\n\n"+
				"Too Human Copyright 2008 Silicon Knights\n\n"+
				"Special thanks to maawdawg, tmunee, and MarkZ3 \nof the excellent TooHuman.net forums.\n\n"+
				"Skill icons courtesy of the equally awesome \nToo Human wiki (http://toohuman.wikia.com).\n\n"+
				"Skill descriptions and stats taken from\nthe official Too Human demo available\n"+
				"now on the Xbox Live Marketplace!",
				"About TH Character Plotter",JOptionPane.INFORMATION_MESSAGE,icons.get("Icon"));
		} else if(name.equals("Help")) {
			JOptionPane.showMessageDialog(this,"Select a class and alignment to view the trees.\n\n"+
				"To add/remove points from a skill node:\n"+
				"Add points: Left click or mouse wheel up.\n"+
				"Remove points: Right or Control click or mouse wheel down.\n\n"+
				"Save/Open normal vs. single:\n"+
				"Use \"single\" for legacy (below v2.2) plotter files.\n"+
				"New files save all classes, older files only contain one class."
				,"Help",JOptionPane.INFORMATION_MESSAGE,
				icons.get("Icon"));
		} else if(name.contains("Export")) {
			
		}
	}
	private void setView(String classString) {
		splitpane.setRightComponent(null);
		if(cache.size() == 0 || cache.get(classString) == null) {
			classPanel = new ClassPanel(classString,this,icons);
			cache.put(classString,classPanel);
		} else if(cache.get(classString) != null) {
			classPanel = cache.get(classString);
		}
		classPanel.setVisible(true);
		splitpane.setRightComponent(classPanel);
	}
/*	private void showSelectPanel() {
		JPanel selectPanel = new JPanel();
		selectPanel.add(new JLabel("Select a class"));
		scrollPane.setViewportView(selectPanel);
		selectPanel.setVisible(true);
	}*/
	public void pack() {
		if(getExtendedState() != JFrame.NORMAL) return;
		super.pack();
		splitpane.resetToPreferredSizes();
		if(isVisible() && getExtendedState() == JFrame.NORMAL) {
			Dimension size = getSize();
			Point pos = getLocationOnScreen();
			Dimension screen = getToolkit().getScreenSize();
			
			if(size.width + pos.x > screen.width)
				setSize(screen.width - pos.x,size.height-30);
			size = getSize();
			pos = getLocationOnScreen();
			if(size.height + pos.y > screen.height)
				setSize(size.width,screen.height - pos.y-30);
		}
	}
	private void setBars(JProgressBar[] array, int val) {
		for(int k = 0; k < array.length; k++) {
		/*	if(k <= (val - 1))
				array[k].setIndeterminate(true);
			else {
				array[k].setIndeterminate(false);
				array[k].setValue(0);
			}*/
			array[k].setValue((k <= (val - 1)) ? 1 : 0);
		}
	}
	private void setClassDescText(String name) {
		if(name == null) {
		/*	setBars(hits,0);
			setBars(melees,0);
			setBars(ballistics,0);
			setBars(armors,0);*/
			hitBar.setValue(0);
			meleeBar.setValue(0);
			ballisticsBar.setValue(0);
			armorBar.setValue(0);
			specLabel.setText("");
			classdesc.setText("Select a class to see description.");
		} else if(name.equals("Berserker")) {
		/*	setBars(hits,2);
			setBars(melees,5);
			setBars(ballistics,1);
			setBars(armors,2);*/
			hitBar.setValue(2);
			meleeBar.setValue(5);
			ballisticsBar.setValue(1);
			armorBar.setValue(2);
			specLabel.setText("<html>Damage dealing<p>Dual Wield Weapons");
			classdesc.setText("The Berserker delights in the fury of close combat, forgoing defensive "+
				"strategy in order to adopt all-out offense. Adopting a twin-blade fighting style and infused "+
				"with the spirit of the bear, a Berserker will wade into battle for the glory of ODIN.");
		} else if(name.equals("BioEngineer")) {
		/*	setBars(hits,5);
			setBars(melees,2);
			setBars(ballistics,2);
			setBars(armors,2);*/
			hitBar.setValue(5);
			meleeBar.setValue(2);
			ballisticsBar.setValue(2);
			armorBar.setValue(2);
			specLabel.setText("<html>Regeneration<p>Healing");
			classdesc.setText("A master of cybernetics as well as mundane combat, the BioEngineer repairs damage "+
				"sustained on the battlefield, increasing health bonuses of himself and his allies, enabling them "+
				"to take the fight directly into the heart of the enemy.");
		} else if(name.equals("Champion")) {
		/*	setBars(hits,3);
			setBars(melees,3);
			setBars(ballistics,3);
			setBars(armors,3);*/
			hitBar.setValue(3);
			meleeBar.setValue(3);
			ballisticsBar.setValue(3);
			armorBar.setValue(3);
			specLabel.setText("<html>Air Combat<p>Critical Strikes");
			classdesc.setText("The Champion represents ODIN's divine force of retribution. A strong warrior, able "+
				"to deal out a wide variety of caustic force field and anti-gravity based effects, increasing the "+
				"combat effectiveness of his allies. One-handed weapons are the Champion's chosen tools of combat.");
		} else if(name.equals("Commando")) {
		/*	setBars(hits,2);
			setBars(melees,1);
			setBars(ballistics,5);
			setBars(armors,2);*/
			hitBar.setValue(2);
			meleeBar.setValue(1);
			ballisticsBar.setValue(5);
			armorBar.setValue(2);
			specLabel.setText("<html>Explosives Master<p>Spider Master");
			classdesc.setText("Favoring technological gadgetry and stand-off methods of warfare, the Commando "+
				"specializes in the use of mines, counter-measures, demolitions, and rifles. Able to support "+
				"his allies through long-range harrying tactics, the Commando is truly a force to be reckoned with.");
		} else if(name.equals("Defender")) {
		/*	setBars(hits,4);
			setBars(melees,2);
			setBars(ballistics,2);
			setBars(armors,4);*/
			hitBar.setValue(4);
			meleeBar.setValue(2);
			ballisticsBar.setValue(2);
			armorBar.setValue(4);
			specLabel.setText("<html>Defensive toughness<p>Hammer & Shield</html>");
			classdesc.setText("With the blessings of ODIN and runes of protection, the Defender is the backbone of "+
				"the Aesir�s defense. Heavy armor enables the Defender to absorb a tremendous amount of damage, "+
				"leaving his allies to take the battle to the enemy unhindered.");
		} else {
		/*	setBars(hits,0);
			setBars(melees,0);
			setBars(ballistics,0);
			setBars(armors,0);*/
			hitBar.setValue(0);
			meleeBar.setValue(0);
			ballisticsBar.setValue(0);
			armorBar.setValue(0);
			specLabel.setText("");
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
	class THFileFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File f) {
			return f != null && (f.isDirectory() || f.getName().contains(".thclass"));
		}
		public String getDescription() {
			return "Too Human Character Plotter files (*.thclass)";
		}
	}
	public static void main(String...args) {
//		JFrame.setDefaultLookAndFeelDecorated(true);
		new TooHumanCalc();
	}
}