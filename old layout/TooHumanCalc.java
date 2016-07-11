import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;

public class TooHumanCalc extends JFrame implements ActionListener, MouseListener {
	public static final long serialVersionUID = -86546345547L;
	private JComboBox classBox;
	private ClassPanel classPanel;
	private JScrollPane scrollPane;
	private String[] classList = {//" ",
								  "Berserker",
								  "BioEngineer",
								  "Champion",
								  "Commando",
								  "Defender"};
	public TooHumanCalc() {
		super("Too Human Character Plotter");
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		String classname = /*info[3].getClassName();*/ UIManager.getSystemLookAndFeelClassName();
		try{UIManager.setLookAndFeel(classname);}catch(Exception e){}

		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem exitItem = new JMenuItem("Exit",KeyEvent.VK_X);
		fileMenu.add(exitItem).addActionListener(this);
		menubar.add(fileMenu);
		JMenu aboutMenu = new JMenu("About");
		aboutMenu.setMnemonic(KeyEvent.VK_A);
		aboutMenu.add(new JMenuItem("About",KeyEvent.VK_A)).addActionListener(this);
		menubar.add(aboutMenu);
		setJMenuBar(menubar);

		Arrays.sort(classList);
	//	classBox = new JComboBox(classList);
	//	classBox.addActionListener(this);
		JLabel classLabel = new JLabel("  Class:  ");
		Box topBox = Box.createHorizontalBox();
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
		}
		
	/*	Box profileBox = Box.createVerticalBox();
		profileBox.setBorder(BorderFactory.createTitledBorder("Character Profile"));
		JLabel nameLabel = new JLabel("Name: ");
		JTextField nameField = new JTextField("",20);
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		profileBox.add(namePanel);
		ButtonGroup group = new ButtonGroup();
		Vector<JRadioButton> buttons = new Vector<JRadioButton>(classList.length);
		JPanel buttonsPanel = new JPanel(new GridLayout(classList.length,0));
		buttonsPanel.setBorder(BorderFactory.createTitledBorder("Choose class"));
		for(int k = 0; k < classList.length; k++) {
			JRadioButton j = new JRadioButton(classList[k]);
			j.addActionListener(this);
			buttons.add(j);
			group.add(j);
			buttonsPanel.add(j);
		}
		profileBox.add(buttonsPanel);*/
		
		classPanel = new ClassPanel();

		scrollPane = new JScrollPane(classPanel);
		classPanel.setVisible(false);

		JPanel content = new JPanel(new BorderLayout());
		content.add(topBox,BorderLayout.NORTH);
		content.add(scrollPane,BorderLayout.CENTER);
	//	content.add(profileBox,BorderLayout.WEST);
	//	content.add(scrollPane,BorderLayout.CENTER);
		pane.add(content,BorderLayout.CENTER);
		showSelectPanel();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocation(100,100);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if(e.getSource() instanceof JRadioButton)
			setView(name);
	/*	if(e.getSource() == classBox) {
			if(classBox.getSelectedIndex() == 0)
				showSelectPanel();
			else
				setView((String)classBox.getSelectedItem());
		}*/
		if(name.equals("Exit")) { System.exit(0);
		} else if(name.equals("About")) {
			JOptionPane.showMessageDialog(this,"Too Human Character Plotter (Java version)\n\n"+
				"Copyright 2008 Imran Merchant\n\n\nToo Human Copyright 2008 Silicon Knights",
				"About TH Character Plotter",JOptionPane.INFORMATION_MESSAGE);
		}
		pack();
	}
	private void setView(String classString) {
		classPanel = new ClassPanel(classString,this);
		scrollPane.setViewportView(classPanel);
	}
	private void showSelectPanel() {
		JPanel selectPanel = new JPanel();
		selectPanel.add(new JLabel("Select a class"));
		scrollPane.setViewportView(selectPanel);
		selectPanel.setVisible(true);
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public static void main(String...args) { new TooHumanCalc(); }
}