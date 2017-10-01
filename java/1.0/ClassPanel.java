import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;

class ClassPanel extends JPanel implements ActionListener, java.io.Serializable,Scrollable {
	public static final long serialVersionUID = 386546547L;
//	private JComboBox alignmentBox;
	private JLabel pointsLabel,error,levelLabel;
	private JScrollPane scrollpane;
	private JPanel trees;
	private HashMap<String,AlignPanel> cache;
	private SkillBox[][] skills;
	private AlignPanel alignTree;
	private ActionListener listener;
	private Icons icons;
	private boolean built = false;
	private String classString = "none", alignString;
	private String[] alignments = new String[] {"Human","Cybernetic"};
	public String toString() { return classString; }
	public ClassPanel() {}
	@Deprecated public ClassPanel(String classS) { this(classS,null,new Icons()); }
	public ClassPanel(ClassExport ex, ActionListener al,Icons i) {
		this(ex.classString,al,i);
		Iterator<Integer> iter = ex.classPoints.iterator();
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				skills[k][j].setPoints(iter.next());
		setAlignment(ex);
		
	}
	public ClassPanel(String classS, ActionListener al,Icons i) {
		super(new BorderLayout());
		classString = classS;
	//	System.out.println("New ClassPanel initialization: "+classString);
		addActionListener(al);
		setBorder(BorderFactory.createTitledBorder(classString+" trees"));
		icons = i;
		cache = new HashMap<String,AlignPanel>(2);
		
	//	alignmentBox = new JComboBox(new String[] {"Human","Cybernetic"});
	//	alignmentBox.addActionListener(this);
	/*	JPanel profile = new JPanel(new BorderLayout());
		Box name = Box.createHorizontalBox();
		name.add(new JLabel("  Name:  "));
		name.add(new JTextField(" ",35));
		profile.add(name,BorderLayout.NORTH);*/
		Box topBox = Box.createHorizontalBox();
	//	alignBox.add(new JLabel("  Name:  "));
	//	alignBox.add(new JTextField(" ",35));
	//	alignBox.add(Box.createHorizontalStrut(10));
	/*	JLabel alignmentLabel = new JLabel("  Alignment:  ");
		alignBox.add(alignmentLabel);
		ButtonGroup group = new ButtonGroup();
		Vector<JRadioButton> buttons = new Vector<JRadioButton>(alignments.length);
		for(int k = 0; k < alignments.length; k++) {
			JRadioButton j = new JRadioButton(alignments[k]);
			buttons.add(j);
			j.addActionListener(this);
			group.add(j);
			alignBox.add(j);
		}*/
		topBox.add(new JLabel("Character points used:  "));
		topBox.add(pointsLabel = new JLabel("0/107"));
		error = new JLabel("   **TOO MANY CHARACTER POINTS USED!**");
		error.setForeground(Color.red);
		error.setVisible(false);
		topBox.add(error);
		Box textBox = Box.createVerticalBox();
		textBox.add(topBox);
		textBox.add(levelLabel = new JLabel("Level: 1"));
		JPanel topPanel = new JPanel(new BorderLayout());
	//	topPanel.add(new JLabel(icons.get(classString+"_30")),BorderLayout.WEST);
		topPanel.add(textBox,BorderLayout.CENTER);
		JButton reset = new JButton("Reset");
		reset.addActionListener(this);
		topPanel.add(reset,BorderLayout.EAST);
	//	topBox.add(Box.createHorizontalGlue());
	//	topBox.add(reset);
	//	alignBox.add(alignmentBox);
	//	profile.add(alignBox,BorderLayout.CENTER);
	//	add(profile,BorderLayout.NORTH);
	//	add(topBox,BorderLayout.NORTH);
	//	add(topPanel,BorderLayout.WEST);
		Box box = Box.createHorizontalBox();
		box.add(topPanel);
		box.add(Box.createHorizontalGlue());
		add(box,BorderLayout.NORTH);

		int rows = classString.equals("Champion") ? 8 : 7;
		JPanel skillTree = new JPanel(new GridLayout(rows,3,3,0));
		skillTree.setBorder(BorderFactory.createTitledBorder("Skill tree"));
		skills = new SkillBox[rows][3];
		for(int k = 0; k < rows; k++)
			for(int j = 0; j < 3; j++)
				skills[k][j] = new SkillBox();//Box.createHorizontalBox();
		skills[0][2] = new SkillBox(icons.get("ruiner"),"Human Ruiner","Increases Ruiner radius and damage.",
			"Ruiners require 1 combo level to activate.",5,5,Color.lightGray);
		int off = 0;
		if(classString.equals("Berserker")) {
			skills[1][1] = new SkillBox(icons.get("berserker1"),"A Capacity for Rage (Group Skill)","Level 1-9: Increases combo meter growth rate.",
				"Level 10: Additional combo level",11,10,Color.green);
			
			skills[2][0] = new SkillBox(icons.get("berserker2"),"The Bear's Boiling Blood","Increases attack speed with hit counter.",
				null,1,10,Color.cyan);
			skills[3][0] = new SkillBox(icons.get("berserker5"),"Loki's Kiss","Spider: Deploys an enemy-seeking sticky bomb.",null,5,10,Color.yellow);
			skills[4][0] = new SkillBox(icons.get("berserker8"),"Swift of Claw","Battle Cry: Increases melee attack speed.",
				"Also affects co-op party.",3,10,Color.blue);
			skills[5][0] = new SkillBox(icons.get("berserker11"),"Unrelenting Blades","Increases Dual-Wield weapon attack speed.",
				null,3,10,Color.cyan);
			
			skills[2][1] = new SkillBox(icons.get("berserker3"),"Onslaught of Claws","Increases Fierce attack speed.",
				null,5,10,Color.cyan);
			skills[3][1] = new SkillBox(icons.get("berserker6"),"Ankle Biter","Spider: Deploys a turret which slows enemies",
				"hit by its fire.",5,10,Color.yellow);
			skills[4][1] = new SkillBox(icons.get("berserker9"),"Engulfing Rage","Battle Cry: Increases movements speed and adds",
				"knockback to all attacks. Affects party.",5,10,Color.blue);
			skills[5][1] = new SkillBox(icons.get("berserker12"),"Weapon Recovery","Increases slide attack speed.",null,1,10,Color.cyan);
			
			skills[2][2] = new SkillBox(icons.get("berserker4"),"Brutality","Increases chance of channeling last damage taken",
				"into bonus damage on the next attack.",5,10,Color.cyan);
			skills[3][2] = new SkillBox(icons.get("berserker7"),"Sleep-Storm of Steel","Spider: Deploys a mine that explodes on contact.",
				null,5,10,Color.yellow);
			skills[4][2] = new SkillBox(icons.get("berserker10"),"Shield Biter","Battle Cry: Increases melee attack damage.",
				"Also affects co-op party.",6,10,Color.blue);
			skills[5][2] = new SkillBox(icons.get("berserker13"),"Warrior of the Twinned-Claw","Increases Dual-Wield Weapon damage.",
				null,3,10,Color.cyan);
		} else if(classString.equals("BioEngineer")) {
			skills[1][1] = new SkillBox(icons.get("bioengineer1"),"Idunn's Touch (Group Skill)","Increases health regeneration over time.",
				null,.1,8,Color.green);
			
			skills[2][0] = new SkillBox(icons.get("bioengineer2"),"Skuld's Embrace","Increases chance that enemies hit with attacks will",
				"turn on their allies.",1,10,Color.cyan);
			skills[3][0] = new SkillBox(icons.get("bioengineer5"),"Wrack of Lightning Mine","Spider: Deploys an EMP mine that disables machine",
				"foes for a period of time.",5,10,Color.yellow);
			skills[4][0] = new SkillBox(icons.get("bioengineer8"),"Idunn's Boon","Battle Cry: Immediately performs a minor heal and clears",
				"negative effects.",4,10,Color.blue);
			skills[5][0] = new SkillBox(icons.get("bioengineer11"),"Ascent to Valhalla","Increases juggle attack launch height.",
				null,2,10,Color.cyan);
			
			skills[2][1] = new SkillBox(icons.get("bioengineer3"),"Warrior of the Battle-Oar","Increases 2-Handed weapon damage.",
				null,3,10,Color.cyan);
			skills[3][1] = new SkillBox(icons.get("bioengineer6"),"Ward of the NORNs","Spider: Deploys a protective shield that absorbs",
				"damage.",5,10,Color.yellow);
			skills[4][1] = new SkillBox(icons.get("bioengineer9"),"Idunn's Favor","Battle Cry: Performs a large, delayed heal and clears",
				"negative effects.",6,10,Color.blue);
			skills[5][1] = new SkillBox(icons.get("bioengineer12"),"Cellular Rebonding (Group Skill)","Increases maximum hit points.",
				null,1,10,Color.green);
			
			skills[2][2] = new SkillBox(icons.get("bioengineer4"),"Warriors of Tyr's Way","Increases 1-Handed weapon damage.",
				null,3,10,Color.cyan);
			skills[3][2] = new SkillBox(icons.get("bioengineer7"),"Gifts of Idunn","Spider: Deploys a mine that heals any allies",
				"in detonation range.",5,10,Color.yellow);
			skills[4][2] = new SkillBox(icons.get("bioengineer10"),"Idunn's Wish","Battle Cry: Enhances resistances, clears negative",
				"effects, and enhances offensive stats.",5,10,Color.blue);
			skills[5][2] = new SkillBox(icons.get("bioengineer13"),"Electrified Blade","Increases chance of inflicting Lightning",
				"damage during melee attacks.",1,10,Color.cyan);
		} else if(classString.equals("Champion")) {
			skills[1][1] = new SkillBox(icons.get("champion1"),"Unerring Strike (Group Skill)","Increases chance of critical strikes.",
				null,5,10,Color.green);
			
			skills[2][0] = new SkillBox(icons.get("champion2"),"Immolating Blade","Increases chance of inflicting fire damage",
				"during melee attacks.",1,10,Color.cyan);
			skills[3][0] = new SkillBox(icons.get("champion5"),"Lament for the Battle-Slain","Increases Finisher move damage.",
				null,3,10,Color.cyan);
			skills[4][0] = new SkillBox(icons.get("champion6"),"Thermal Induction Mine","Spider: Deploys a mine that explodes",
				"on contact and does fire damage.",5,10,Color.yellow);
			skills[5][0] = new SkillBox(icons.get("champion9"),"One Will Rise Above","Battle Cry: Increases air melee attack damage.",
				"Also affects co-op party.",15,10,Color.blue);
			skills[6][0] = new SkillBox(icons.get("champion12"),"Warrior of Blood-Eel","Increases sword damage.",null,3,10,Color.cyan);
			
			skills[2][1] = new SkillBox(icons.get("champion3"),"Asgard's Fury","Increases air melee attack damage.",
				null,3,10,Color.cyan);
			skills[4][1] = new SkillBox(icons.get("champion7"),"Feeder of Ravens","Spider: Deploys a concussion mine that explodes",
				"on contact, launching enemies airborne.",5,10,Color.yellow);
			skills[5][1] = new SkillBox(icons.get("champion10"),"Valiant's Might","Battle Cry: All melee attacks cause enemy juggle.",
				"Also affects co-op party.",5,10,Color.blue);
			skills[6][1] = new SkillBox(icons.get("champion13"),"Ascent to Valhalla","Increases juggle attack launch height.",
				null,2,10,Color.cyan);
			
			skills[2][2] = new SkillBox(icons.get("champion4"),"Kinship of Gungnir","Increases Pistol weapon damage.",
				null,4,10,Color.cyan);
			skills[4][2] = new SkillBox(icons.get("champion8"),"Tree of Raining-Iron","Spider: Deploys a chain turret.",
				null,5,10,Color.yellow);
			skills[5][2] = new SkillBox(icons.get("champion11"),"Storm of Mortal Wounds","All enemies will be softened and exposed",
				"to critical strikes. Affects party.",5,10,Color.blue);
			skills[6][2] = new SkillBox(icons.get("champion14"),"Stopping Power","Increases Slug ammunition damage.",null,4,10,Color.cyan);
			
			off = 1;
		} else if(classString.equals("Commando")) {
			skills[1][1] = new SkillBox(icons.get("commando1"),"Wreaker of Mead Halls (Group Skill)","Increases damage and radius of all",
				"secondary fire.",2.5,10,Color.green);
			
			skills[2][0] = new SkillBox(icons.get("commando2"),"Pinning Shot","Increases chance that Ballisitc shots will",
				"temporarily root enemies hit.",1,10,Color.cyan);
			skills[3][0] = new SkillBox(icons.get("unknown"),"Bullet-Tree","Spider: Deploys point-defense turret capable of",
				"shooting down incoming missiles.",5,10,Color.yellow);
			skills[4][0] = new SkillBox(icons.get("unknown"),"Smoothbore","Battle Cry: All Ballistic shots have knockback.",
				"Also affects co-op party.",5,10,Color.blue);
			skills[5][0] = new SkillBox(icons.get("unknown"),"Ballistic Telemetry Feedback","Increases Ballisitic weapon range.",
				null,1,10,Color.cyan);
			
			skills[2][1] = new SkillBox(icons.get("commando3"),"Rain of Iron","Increases Slug ammunition rate of fire.",
				null,3,10,Color.cyan);
			skills[3][1] = new SkillBox(icons.get("unknown"),"Cluster Munitions","Spider: Deploys a cluster mine that explodes",
				"on contact.",5,10,Color.yellow);
			skills[4][1] = new SkillBox(icons.get("unknown"),"Lightning Cascade","Battle Cry: Attacks will include delayed detionation",
				"explosive damage. Affects party.",10,10,Color.blue);
			skills[5][1] = new SkillBox(icons.get("unknown"),"Delayed Fragmentation Warheads","Increases chance Ballistic shots will do",
				"explosive damage.",1,10,Color.cyan);
			
			skills[2][2] = new SkillBox(icons.get("commando4"),"Adept of Burning Spear","Increases Plasma ammunition damage.",
				null,3,10,Color.cyan);
			skills[3][2] = new SkillBox(icons.get("unknown"),"Tree of Shrieking Flame","Spider: Deploys a Plasma turret.",
				null,5,10,Color.yellow);
			skills[4][2] = new SkillBox(icons.get("unknown"),"Cut to the Bone","Battle Cry: Increases Ballisitc damage.",
				"Also affects co-op party.",10,10,Color.blue);
			skills[5][2] = new SkillBox(icons.get("unknown"),"Gift of Gungnir","Increases Rifle damage.",
				null,3,10,Color.cyan);
		} else if(classString.equals("Defender")) {
			skills[1][1] = new SkillBox(icons.get("defender1"),"Defender's Resilience (Group Skill)","Decreases the amount of damage taken.",
				null,.5,10,Color.green);
			
			skills[2][0] = new SkillBox(icons.get("defender2"),"Enthalpy Reduction Attack","Increases chance of infliciting",
				"Ice damage during melee attacks.",1,10,Color.cyan);
			skills[3][0] = new SkillBox(icons.get("defender5"),"Enthalpy Reduction Mines","Spider: Deploys an Ice mine which freezes",
				"all in range upon detonation.",5,10,Color.yellow);
			skills[4][0] = new SkillBox(icons.get("defender8"),"Fimbulwinter's Numbing Touch","Battle Cry: Produces a shield which freezes",
				"enemies upon contact.",3,10,Color.blue);
			skills[5][0] = new SkillBox(icons.get("defender11"),"Adept of the Light Spear","Increases Laser weapon damage.",
				null,3,10,Color.cyan);
			
			skills[2][1] = new SkillBox(icons.get("defender3"),"Grim Resolve","Increases chance that damage from a killing blow",
				"will be completely deflected.",5,10,Color.cyan);
			skills[3][1] = new SkillBox(icons.get("defender6"),"Ward of the NORNs","Spider: Deploys a protective shield that absorbs",
				"damage.",5,10,Color.yellow);
			skills[4][1] = new SkillBox(icons.get("defender9"),"Reversal of Wyrd's","Battle Cry: Enemy status effects are reflected back.",
				"Also affects co-op party.",10,10,Color.blue);
			skills[5][1] = new SkillBox(icons.get("defender12"),"Tyr's Best Work","Increases the defensive bonus of equipped armor.",
				null,1,10,Color.cyan);
			
			skills[2][2] = new SkillBox(icons.get("defender4"),"The Berserker's Grief","Increases chance that 100% of melee damage",
				"is reflected back to the attacker.",1,10,Color.cyan);
			skills[3][2] = new SkillBox(icons.get("defender7"),"Tree of Scorching Light","Spider: Deploys a Laser turret.",
				null,5,10,Color.yellow);
			skills[4][2] = new SkillBox(icons.get("defender10"),"Egil's Blessing","Battle Cry: Taunts all neaby enemies to draw aggro.",
				null,10,10,Color.blue);
			skills[5][2] = new SkillBox(icons.get("defender13"),"Warrior of the Iron Fist","Increases Hammer weapon damage.",
				null,3,10,Color.cyan);
		}
		skills[6+off][1] = new SkillBox(icons.get("fenrir"),"Spirit of Fenrir","Sentient Power: gain massive amounts of combo by",
				"unleashing the power of Fenrir on nearby enemies.",5,10,Color.cyan);
		skills[0][2].setEnabled(true);
		skills[0][2].setVisible(false);
		skills[1][1].setEnabled(true);
		for(int k = 0; k < rows; k++)
			for(int j = 0; j < 3; j++) {
				skills[k][j].addActionListener(this);
				skillTree.add(skills[k][j]);
			}
			
		trees = new JPanel(new BorderLayout());
		
		trees.add(skillTree,BorderLayout.CENTER);

	//	if(alignmentBox.getSelectedIndex() == 1) skills[0][2].setVisible(false);
		
		alignTree = new AlignPanel("Human",this,icons);
		trees.add(alignTree,BorderLayout.SOUTH);
		alignTree.setVisible(false);
	//	buttons.get(0).setSelected(true);
		
		scrollpane = new JScrollPane(trees);
		scrollpane.setBorder(null);
		scrollpane.setMinimumSize(new Dimension(1,1));
		
		add(scrollpane,BorderLayout.CENTER);
		
		built = true;
	}
	public boolean setAlignment(ClassExport ex) {
		String align = ex.alignString;
		if(!built || (!align.equals("Human") && !align.equals("Cybernetic"))) return false;
		skills[0][2].setVisible(align.equals("Human"));
		alignString = align;
		trees.remove(alignTree);
		alignTree = new AlignPanel(ex,this,icons);
		trees.add(alignTree,BorderLayout.SOUTH);
	//	scrollpane.setViewportView(alignTree);
		alignTree.setVisible(true);
		updateUI();
		updatePoints();
		if(listener != null)
			listener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
									"Pack request"));
		return true;
	}
	public boolean setAlignment(String align) {
		if(!built || (!align.equals("Human") && !align.equals("Cybernetic"))) return false;
		if(skills != null) skills[0][2].setVisible(align.equals("Human"));
		alignString = align;
		trees.remove(alignTree);
		if(cache.size() == 0 || cache.get(alignString) == null) {
			alignTree = new AlignPanel(alignString,this,icons);
			cache.put(alignString,alignTree);
		} else if(cache.get(alignString) != null) {
			alignTree = cache.get(alignString);
		}
	//	alignTree = new AlignPanel(align,this,icons);
		trees.add(alignTree,BorderLayout.SOUTH);
	//	scrollpane.setViewportView(alignTree);
		alignTree.setVisible(true);
		updateUI();
		updatePoints();
		if(listener != null)
			listener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
									"Pack request"));
		return true;
	}
	public String getAlignment() { return alignString; }
	public String getClassString() { return classString; }
	public void actionPerformed(ActionEvent e){
	/*	if(e.getSource() == alignmentBox) {
			boolean human = alignmentBox.getSelectedIndex() == 0;
			skills[0][2].setVisible(human);
			remove(alignTree);
			alignTree = new AlignPanel((String)alignmentBox.getSelectedItem());
			add(alignTree,BorderLayout.SOUTH);
		}*/
	/*	if(e.getSource() instanceof JRadioButton) {
			skills[0][2].setVisible(e.getActionCommand().equals("Human"));
			remove(alignTree);
			alignTree = new AlignPanel(e.getActionCommand());
			add(alignTree,BorderLayout.SOUTH);
			updateUI();
			if(listener != null)
				listener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
											"Pack request"));
		}*/
		if(e.getSource() instanceof JButton)
			zero();
		if(e.getSource() instanceof SkillBox) {
			SkillBox source = (SkillBox)e.getSource();
			int points = source.getPoints();
			int off = classString.equals("Champion") ? 1 : 0;
			
			if(source == skills[1][1]) {
				skills[2][0].setEnabled(points >= 6);
				skills[2][1].setEnabled(points >= 6);
				skills[2][2].setEnabled(points >= 6);
			} else if(source == skills[2][0]) {
				if(off == 1) skills[3][0].setEnabled(points >= 1);
				if(!(skills[3+off][1].getPoints() > 0 || skills[3+off][2].getPoints() > 0))
					skills[3+off][0].setEnabled(points >= 6);
			} else if(source == skills[2][1]) {
				if(!(skills[3+off][0].getPoints() > 0 || skills[3+off][2].getPoints() > 0))
					skills[3+off][1].setEnabled(points >= 6);
			} else if(source == skills[2][2]) {
				if(!(skills[3+off][0].getPoints() > 0 || skills[3+off][1].getPoints() > 0))
					skills[3+off][2].setEnabled(points >= 6);
			} else if(source == skills[3+off][0]) {
				if(points == 0) {
					actionPerformed(new ActionEvent(skills[2][1],ActionEvent.ACTION_PERFORMED,"Reset"));
					actionPerformed(new ActionEvent(skills[2][2],ActionEvent.ACTION_PERFORMED,"Reset"));
				}
				if(points > 0) {
					skills[3+off][1].setEnabled(false);
					skills[3+off][2].setEnabled(false);
				}
				skills[4+off][0].setEnabled(points >= 4);
			} else if(source == skills[3+off][1]) {
				if(points == 0) {
					actionPerformed(new ActionEvent(skills[2][0],ActionEvent.ACTION_PERFORMED,"Reset"));
					actionPerformed(new ActionEvent(skills[2][2],ActionEvent.ACTION_PERFORMED,"Reset"));
				}
				if(points > 0) {
					skills[3+off][0].setEnabled(false);
					skills[3+off][2].setEnabled(false);
				}
				skills[4+off][1].setEnabled(points >= 4);
			} else if(source == skills[3+off][2]) {
				if(points == 0) {
					actionPerformed(new ActionEvent(skills[2][0],ActionEvent.ACTION_PERFORMED,"Reset"));
					actionPerformed(new ActionEvent(skills[2][1],ActionEvent.ACTION_PERFORMED,"Reset"));
				}
				if(points > 0) {
					skills[3+off][0].setEnabled(false);
					skills[3+off][1].setEnabled(false);
				}
				skills[4+off][2].setEnabled(points >= 4);
			} else if(source == skills[4+off][0]) {
				skills[5+off][0].setEnabled(points >= 6);
			} else if(source == skills[4+off][1]) {
				skills[5+off][1].setEnabled(points >= 6);
			} else if(source == skills[4+off][2]) {
				skills[5+off][2].setEnabled(points >= 6);
			} else if(source == skills[5+off][0] || source == skills[5+off][1] || source == skills[5+off][2]) {
				skills[6+off][1].setEnabled(skills[5+off][0].getPoints() >= 8
										 || skills[5+off][1].getPoints() >= 8
										 || skills[5+off][2].getPoints() >= 8);
			}
		}
		updatePoints();
	}
	public void addActionListener(ActionListener al) { listener = al; }
	public void zero() {
		skills[0][2].clear();
		skills[1][1].clear();
		alignTree.zero();
	}
	private void updatePoints() {
		int total = getTotalPoints();
		pointsLabel.setText(total+"/107");
		if(total > 107) {
			error.setVisible(true);
			pointsLabel.setForeground(Color.red);
			levelLabel.setForeground(Color.red);
		} else {
			error.setVisible(false);
			pointsLabel.setForeground(getForeground());
			int level = (int)Math.ceil((total <= 27) ? total/3.0 + 1.0 : (total-27)/2.0 + 10.0);
			levelLabel.setText("Level: "+level);
			levelLabel.setForeground(getForeground());
		}
	}
	public int getTotalPoints() {
		int total = 0;
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				total += skills[k][j].getPoints();
		if(alignString != null && alignString.equals("Cybernetic")) total -= skills[0][2].getPoints();
		return total + alignTree.getTotalPoints();
	}
	public ClassExport export() {
		ClassExport export = new ClassExport();
		export.classString = classString;
		export.alignString = alignString;
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				export.classPoints.add(skills[k][j].getPoints());
		SkillBox[][] alignSkills = alignTree.getSkills();
		for(int k = 0; k < alignSkills.length; k++)
			for(int j = 0; j < alignSkills[k].length; j++)
				export.alignPoints.add(alignSkills[k][j].getPoints());
		return export;
	}
	public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
	public boolean getScrollableTracksViewportHeight() { return false; }
	public boolean getScrollableTracksViewportWidth() { return false; }
	public int getScrollableBlockIncrement(Rectangle r, int ort, int direct) { return 100; }
	public int getScrollableUnitIncrement(Rectangle r, int ort, int direct) { return 100; }
	
}
/*
			skills[1][1] = new SkillBox("
			
			skills[2][0] = new SkillBox("
			skills[3][0] = new SkillBox("Spider:
			skills[4][0] = new SkillBox("Battle Cry: 
			skills[5][0] = new SkillBox("
			
			skills[2][1] = new SkillBox("
			skills[3][1] = new SkillBox("Spider: 
			skills[4][1] = new SkillBox("Battle Cry: 
			skills[5][1] = new SkillBox("
			
			skills[2][2] = new SkillBox("
			skills[3][2] = new SkillBox("Spider: 
			skills[4][2] = new SkillBox("Battle Cry: 
			skills[5][2] = new SkillBox("
			
			skills[6][1] = new SkillBox("Sentient Weapon",5);
 */