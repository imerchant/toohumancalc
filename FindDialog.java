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
		JLabel searchLabel = new JLabel("Enter a search term:  ");
		searchPanel.add(searchLabel,BorderLayout.WEST);
		field = new JTextField("",35);
		field.addActionListener(this);
		searchPanel.add(field,BorderLayout.CENTER);
		JButton search = new JButton("Search");
		search.setIcon(icons.get("search"));
		search.addActionListener(this);
		searchPanel.add(search,BorderLayout.EAST);
		
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
		splitpane.setBorder(BorderFactory.createTitledBorder("Results: Title / Description"));
		
		Box box = Box.createHorizontalBox();
		box.add(new JLabel("Left: Results from titles",SwingConstants.LEFT));
		box.add(Box.createHorizontalGlue());
		box.add(new JLabel("Right: Results from descriptions",SwingConstants.RIGHT));
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Search for a skill"));
		panel.add(searchPanel,BorderLayout.NORTH);
		panel.add(splitpane,BorderLayout.CENTER);
		panel.add(box,BorderLayout.SOUTH);
		
		pane.add(panel,BorderLayout.CENTER);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		pack();
		setSize(500,400);
	}
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if(name.equals("Search") || e.getSource() instanceof JTextField) {
			String srch = field.getText().toLowerCase().trim();
			titleResults.clear();
			descResults.clear();
			titleList.setListData(emptyVector);
			descList.setListData(emptyVector);
			HashMap<String,AlignPanel> alignCache = null;
			
			for(String c: cache.keySet()) {
				ImageIcon classIcon = icons.get(c+"_30");
				SkillBox[][] skills = cache.get(c).getSkillBoxes();
				alignCache = cache.get(c).getCache();
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
		}
	}
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean hasFocus) {
		JPanel panel = (JPanel)value;
		panel.setOpaque(selected && index != -1);
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
		splitpane.resetToPreferredSizes();
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