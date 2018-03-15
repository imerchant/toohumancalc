import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class FindDialog extends JFrame implements ActionListener,ListCellRenderer {
	public static final long serialVersionUID = -436598723475l;
	private HashMap<String,ClassPanel> cache;
	private JTextField field;
//	private JPanel titlePanel,descPanel;
//	private Box titlePanel,descPanel;
	private JList titleList,descList;
	private Vector<JPanel> titleResults,descResults;
	private final Vector<JPanel> emptyVector, noResultsVector;
	private Icons icons;
	private JSplitPane splitpane;
	private JCheckBox[] restrictions;
	public FindDialog(HashMap<String,ClassPanel> c,Icons i) {
		super("Too Human Skill Calculator Find-a-Skill");
		icons = i;
		setIconImage(icons.get("find").getImage());
		cache = c;
		titleResults = new Vector<JPanel>();
		descResults = new Vector<JPanel>();
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem searchItem = new JMenuItem("Search",KeyEvent.VK_S);
		searchItem.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
		searchItem.setIcon(icons.get("search"));
		fileMenu.add(searchItem).addActionListener(this);
		fileMenu.addSeparator();
		JMenuItem closeItem = new JMenuItem("Close",KeyEvent.VK_C);
		closeItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		closeItem.setIcon(icons.get("log out"));
		fileMenu.add(closeItem).addActionListener(this);
		menubar.add(fileMenu);
		setJMenuBar(menubar);
		
		JPanel searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBorder(BorderFactory.createTitledBorder("Enter a search term"));
	//	JLabel searchLabel = new JLabel("Enter a search term:  ");
	//	searchPanel.add(searchLabel,BorderLayout.WEST);
		field = new JTextField("",35);
		field.addActionListener(this);
		searchPanel.add(field,BorderLayout.CENTER);
		JButton search = new JButton("Search",icons.get("search"));
		search.addActionListener(this);
		searchPanel.add(search,BorderLayout.EAST);
		JPanel restr = new JPanel();
		restr.setBorder(BorderFactory.createTitledBorder("Search restrictions"));
		restrictions = new JCheckBox[8];
		restr.add(restrictions[0] = new JCheckBox("All",true));
		restr.add(restrictions[1] = new JCheckBox("Berserker",true));
		restr.add(restrictions[2] = new JCheckBox("Defender",true));
		restr.add(restrictions[3] = new JCheckBox("Champion",true));
		restr.add(restrictions[4] = new JCheckBox("Commando",true));
		restr.add(restrictions[5] = new JCheckBox("BioEngineer",true));
		restr.add(restrictions[6] = new JCheckBox("Human",true));
		restr.add(restrictions[7] = new JCheckBox("Cybernetic",true));
		for(JCheckBox r: restrictions)
			r.addActionListener(this);
		searchPanel.add(restr,BorderLayout.SOUTH);
		
		titleList = new JList();
		titleList.setCellRenderer(this);
		descList = new JList();
		descList.setCellRenderer(this);
		
		emptyVector = new Vector<JPanel>(1);
		noResultsVector = new Vector<JPanel>(1);
		JPanel temp = new JPanel();
		temp.add(new JLabel("No results"));
		noResultsVector.add(temp);
		
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,
					new JScrollPane(titleList),
					new JScrollPane(descList));
		splitpane.setResizeWeight(0.5);
		splitpane.setDividerLocation(0.5);
	//	splitpane.setDividerSize(0);
		splitpane.setOneTouchExpandable(true);
	//	splitpane.setBorder(BorderFactory.createTitledBorder("Results: Title / Description"));
	
	//	Box box = Box.createHorizontalBox();
	//	box.add(new JLabel("Left: Results from titles",SwingConstants.LEFT));
	//	box.add(Box.createHorizontalGlue());
	//	box.add(new JLabel("Right: Results from descriptions",SwingConstants.RIGHT));
		JPanel box = new JPanel(new GridLayout(1,2));
		JLabel titleLabel = new JLabel("Titles",SwingConstants.CENTER);
	//	titleLabel.setBorder(BorderFactory.createTitledBorder(""));
		JLabel descLabel = new JLabel("Descriptions",SwingConstants.CENTER);
	//	descLabel.setBorder(BorderFactory.createTitledBorder(""));
		box.add(titleLabel);
		box.add(descLabel);
		JPanel center = new JPanel(new BorderLayout());
		center.add(box,BorderLayout.NORTH);
		center.add(splitpane,BorderLayout.CENTER);
		center.setBorder(BorderFactory.createTitledBorder("Results"));
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Find-a-Skill"));
		panel.add(searchPanel,BorderLayout.NORTH);
		panel.add(center,BorderLayout.CENTER);
	//	panel.add(splitpane,BorderLayout.CENTER);
	//	panel.add(box,BorderLayout.SOUTH);
		
		pane.add(panel,BorderLayout.CENTER);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		pack();
	//	setSize(500,400);
	}
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if(name.equals("Search") || e.getSource() instanceof JTextField) {
			String srch = field.getText().toLowerCase().trim();
			titleResults.clear();
			descResults.clear();
			titleList.setListData(emptyVector);
			descList.setListData(emptyVector);
			HashMap<String,AlignPanel> alignCache = cache.get("Defender").getCache();
			
			for(String c: cache.keySet()) {
				if(!getBox(c).isSelected()) continue;
				ImageIcon classIcon = icons.get(c+"_30");
				SkillBox[][] skills = cache.get(c).getSkillBoxes();
				for(int k = 0; k < skills.length; k++)
					for(int j = 0; j < skills[k].length; j++) {
						if(!skills[k][j].built()) continue;
						if(skills[k][j].getTitle().toLowerCase().contains(srch)) {
							titleResults.add(getSkillPanel(classIcon,skills[k][j]));
						}
						if(skills[k][j].getFullDescription().toLowerCase().contains(srch)) {
							descResults.add(getSkillPanel(classIcon,skills[k][j]));
						}
					}
			}
			if(alignCache != null) {
				for(String a: alignCache.keySet()) {
					if(!getBox(a).isSelected()) continue;
					ImageIcon alignIcon = icons.get(a+"_30");
					SkillBox[][] skills = alignCache.get(a).getSkills();
					for(int k = 0; k < skills.length; k++)
						for(int j = 0; j < skills[k].length; j++) {
							if(!skills[k][j].built()) continue;
							if(skills[k][j].getTitle().toLowerCase().contains(srch)) {
								titleResults.add(getSkillPanel(alignIcon,skills[k][j]));
							}
							if(skills[k][j].getFullDescription().toLowerCase().contains(srch)) {
								descResults.add(getSkillPanel(alignIcon,skills[k][j]));
							}
						}
				}
			}
			
			titleList.setListData(titleResults.isEmpty() ? noResultsVector : titleResults);
			descList.setListData(descResults.isEmpty() ? noResultsVector : descResults);
			
			titleList.revalidate();
			titleList.repaint();
			descList.revalidate();
			descList.repaint();
			repaint();
			
			pack();
		} else if(name.equals("Close")) {
			setVisible(false);
		} else if(e.getSource() instanceof JCheckBox) {
			if(name.equals("All") && restrictions[0].isSelected()) {
				for(int k = 1; k < restrictions.length; k++)
					restrictions[k].setSelected(true);
			} else if(!name.equals("All")) {
				restrictions[0].setSelected(allSelected(1,7));
			}
		}
	}
	private boolean allSelected(int start, int end) {
		for(int k = start; k <= end; k++)
			if(!restrictions[k].isSelected())
				return false;
		return true;
	}
	private JCheckBox getBox(String s) {
		for(int k = 1; k < restrictions.length; k++)
			if(s.equals(restrictions[k].getText()))
				return restrictions[k];
		return null;
	}
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean hasFocus) {
		JPanel panel = (JPanel)value;
		if(selected) {
			panel.setOpaque(index != -1);
            panel.setBackground(list.getSelectionBackground());
            panel.setForeground(list.getSelectionForeground());
        } else {
        	panel.setOpaque(false);
            panel.setBackground(list.getBackground());
            panel.setForeground(list.getForeground());
        }
	//	panel.setOpaque(selected && index != -1);
		return panel;
	}
	private JPanel getSkillPanel(ImageIcon classIcon, SkillBox skill) {
		JPanel skillPanel = new JPanel(new BorderLayout());
		skillPanel.add(new JLabel(classIcon),BorderLayout.WEST);
		Box box = Box.createVerticalBox();
		box.add(new JLabel("<html><u>"+skill.getTitle()+"</u>"));
		box.add(new JLabel(skill.getDescription()));
		box.add(new JLabel(skill.getDescriptionSecondLine()));
		skillPanel.add(box,BorderLayout.CENTER);
		skillPanel.add(new JLabel(skill.getIcon()),BorderLayout.EAST);
		return skillPanel;
	}
	public void pack() {
		if(getExtendedState() != JFrame.NORMAL) return;
	//	splitpane.resetToPreferredSizes();
		super.pack();
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
}