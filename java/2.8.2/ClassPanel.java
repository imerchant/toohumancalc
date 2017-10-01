import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

class ClassPanel extends JPanel implements ActionListener, java.io.Serializable,MouseListener,MouseWheelListener {
	public static final long serialVersionUID = 386546547L;
	private Color CYAN = Color.cyan, YELLOW = Color.yellow, BLUE = Color.blue, GREEN = Color.green;
	private JProgressBar progress;
	private JLabel pointsLabel,error,levelLabel;
	private JScrollPane scrollpane,details;
	private JSplitPane splitpane;
	private JPanel trees,blank;
	private HashMap<String,AlignPanel> cache;
	private SkillBox[][] skills;
	private AlignPanel alignTree;
	private Icons icons;
	private boolean built = false;
	private int total = 0;
	private JFileChooser saveImage;
	private ExtFilefilter filter;
	private String classString = "none", alignString;
	private String[] alignments = new String[] {"Human","Cybernetic"};
	public String toString() { return classString; }
	public ClassPanel() {}
//	@Deprecated public ClassPanel(String classS) { this(classS,null,new Icons()); }
	public ClassPanel(ClassExport ex,Icons i) {
		this(ex.classString,i);
		Iterator<Integer> iter = ex.classPoints.iterator();
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				if(iter.hasNext())
					skills[k][j].setPoints(iter.next());
		setAlignment(ex);
		
	}
	public ClassPanel(String classS,Icons i) {
		super(new BorderLayout());
		classString = classS;
		setBorder(BorderFactory.createTitledBorder(classString+" trees"));
		icons = i;
		cache = new HashMap<String,AlignPanel>(2);
		
		filter = new ExtFilefilter(".png","PNG images (*.png)");
		saveImage = new JFileChooser(System.getProperty("user.dir"));
		saveImage.setDialogType(JFileChooser.SAVE_DIALOG);
		saveImage.setDialogTitle("Save trees as a PNG");
		saveImage.setFileFilter(filter);
		
		Box topBox = Box.createHorizontalBox();
		topBox.add(new JLabel("Character points used:  "));
		topBox.add(pointsLabel = new JLabel("0/95"));
		error = new JLabel("   **TOO MANY CHARACTER POINTS USED!**");
		error.setForeground(Color.red);
		error.setVisible(false);
		topBox.add(error);
		Box textBox = Box.createVerticalBox();
		textBox.add(topBox);
		textBox.add(levelLabel = new JLabel("Level: 1"));
		progress = new JProgressBar(JProgressBar.HORIZONTAL,0,95);
		progress.setStringPainted(true);
		progress.setBorderPainted(false);
		JButton export = new JButton("Export");
		export.setToolTipText("Export trees as a PNG");
		export.setIcon(icons.get("image x"));
		export.addActionListener(this);
		JButton reset = new JButton("Reset");
		reset.setToolTipText("Reset trees to zero");
		reset.setIcon(icons.get("refresh"));
		reset.addActionListener(this);
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(progress,BorderLayout.SOUTH);
		topPanel.add(textBox,BorderLayout.CENTER);
		topPanel.add(export,BorderLayout.WEST);
		topPanel.add(reset,BorderLayout.EAST);
		add(topPanel,BorderLayout.NORTH);

		int rows = classString.equals("Champion") ? 8 : 7;
		JPanel skillTree = new JPanel(new GridLayout(rows,3,3,0));
		skillTree.setBorder(BorderFactory.createTitledBorder(classString + " skill tree"));
		skills = new SkillBox[rows][3];
		for(int k = 0; k < rows; k++)
			for(int j = 0; j < 3; j++)
				skills[k][j] = new SkillBox();
		skills[0][2] = new SkillBox(icons.get("ruiner"),"Human Spiritual Ruiner","Unleash your devestating Human spiritual ruiner.",
			"Ruiners require 1 combo level to activate.","Increases Ruiner radius.",100,5,Color.lightGray);
		int off = 0;
		if(classString.equals("Berserker")) {
			skills[1][1] = new SkillBox(icons.get("berserker1"),"A Capacity for Rage (Group Skill)","Level 1-9: Increases combo meter growth rate.",
				"Level 10: Additional combo level.",11,10,GREEN);
			
			skills[2][0] = new SkillBox(icons.get("berserker2"),"The Bear's Boiling Blood","Increases attack speed with hit counter.",
				null,1,10,CYAN);
			skills[3][0] = new SkillBox(icons.get("berserker5"),"Loki's Kiss","Spider: Deploys an enemy-seeking sticky bomb."
				,null,"Increases damage.",5,10,YELLOW);
			skills[4][0] = new SkillBox(icons.get("berserker8"),"Swift of Claw","Battle Cry: Increases melee attack speed.",
				"Also affects co-op party.","Increases speed and duration.",3,10,BLUE);
			skills[5][0] = new SkillBox(icons.get("berserker11"),"Unrelenting Blades","Increases Dual-Wield weapon attack speed.",
				null,2,10,CYAN);
			
			skills[2][1] = new SkillBox(icons.get("berserker3"),"Onslaught of Claws","Increases Fierce attack speed.",
				null,5,10,CYAN);
			skills[3][1] = new SkillBox(icons.get("berserker6"),"Ankle Biter","Spider: Deploys a turret which slows enemies",
				"hit by its fire.","Increases damage and snare duration.",5,10,YELLOW);
			skills[4][1] = new SkillBox(icons.get("berserker9"),"Engulfing Rage","Battle Cry: Increases movement speed and adds",
				"knockback to all attacks. Affects party.","Increases speed and duration.",5,10,BLUE);
			skills[5][1] = new SkillBox(icons.get("berserker12"),"Weapon Recovery","Increases slide attack speed.",null,1,10,CYAN);
			
			skills[2][2] = new SkillBox(icons.get("berserker4"),"Brutality","Increases chance of channeling last damage",
				"taken into bonus damage on the next attack.",5,10,CYAN);
			skills[3][2] = new SkillBox(icons.get("berserker7"),"Sleep-Storm of Steel","Spider: Deploys a mine that explodes on contact.",
				null,"Increases damage and radius.",5,10,YELLOW);
			skills[4][2] = new SkillBox(icons.get("berserker10"),"Shield Biter","Battle Cry: Increases melee attack damage.",
				"Also affects co-op party.","Increases damage and duration.",6,10,BLUE);
			skills[5][2] = new SkillBox(icons.get("berserker13"),"Warrior of the Twinned-Claw","Increases Dual-Wield Weapon damage.",
				null,3,10,CYAN);
		} else if(classString.equals("BioEngineer")) {
			skills[1][1] = new SkillBox(icons.get("bioengineer1"),"Idunn's Touch (Group Skill)","Increases health regeneration over time.",
				null,.1,8,GREEN);
			
			skills[2][0] = new SkillBox(icons.get("bioengineer2"),"Skuld's Embrace","Increases chance that enemies hit with",
				"attacks will turn on their allies.",.5,10,CYAN);
			skills[3][0] = new SkillBox(icons.get("bioengineer5"),"Wrack of Lightning Mine","Spider: Deploys an EMP mine that",
				"disables machine foes for a period of time.","Increases damage and radius.",5,10,YELLOW);
			skills[4][0] = new SkillBox(icons.get("bioengineer8"),"Idunn's Boon","Battle Cry: Immediately performs a minor",
				"heal and clears negative effects.","Increases heal amount.",3.5,10,BLUE);
			skills[5][0] = new SkillBox(icons.get("bioengineer11"),"Ascent to Valhalla","Increases juggle attack launch height.",
				null,2.5,10,CYAN);
			
			skills[2][1] = new SkillBox(icons.get("bioengineer3"),"Warrior of the Battle-Oar","Increases 2-Handed weapon damage.",
				null,2.5,10,CYAN);
			skills[3][1] = new SkillBox(icons.get("bioengineer6"),"Ward of the NORNs","Spider: Deploys a protective shield that",
				"absorbs damage.","Increases deployment duration.",3,10,YELLOW);
			skills[4][1] = new SkillBox(icons.get("bioengineer9"),"Idunn's Favor","Battle Cry: Performs a large delayed heal",
				"and clears negative effects.","Increases heal amount.",5,10,BLUE);
			skills[5][1] = new SkillBox(icons.get("bioengineer12"),"Cellular Rebonding (Group Skill)","Increases maximum hit points.",
				null,1,10,GREEN);
			
			skills[2][2] = new SkillBox(icons.get("bioengineer4"),"Warrior of Tyr's Way","Increases 1-Handed weapon damage.",
				null,2.5,10,CYAN);
			skills[3][2] = new SkillBox(icons.get("bioengineer7"),"Gifts of Idunn","Spider: Deploys a mine that heals any allies",
				"in detonation range.","Increases heal amount.",5,10,YELLOW);
			skills[4][2] = new SkillBox(icons.get("bioengineer10"),"Idunn's Wish","Battle Cry: Enhances resistances, clears negative",
				"effects, and enhances offensive stats.","Increases bonus and duration.",2,10,BLUE);
			skills[5][2] = new SkillBox(icons.get("bioengineer13"),"Electrified Blade","Increases chance of inflicting Lightning",
				"damage during melee attacks.",1,10,CYAN);
		} else if(classString.equals("Champion")) {
			skills[1][1] = new SkillBox(icons.get("champion1"),"Unerring Strike (Group Skill)","Increases chance of critical strikes.",
				null,.5,10,GREEN);
			
			skills[2][0] = new SkillBox(icons.get("champion2"),"Immolating Blade","Increases chance of inflicting fire damage",
				"during melee attacks.",1,10,CYAN);
			skills[3][0] = new SkillBox(icons.get("champion5"),"Lament for the Battle-Slain","Increases Finisher move damage.",
				null,3,10,CYAN);
			skills[4][0] = new SkillBox(icons.get("champion6"),"Thermal Induction Mine","Spider: Deploys a mine that explodes",
				"on contact and does fire damage.","Increases damage and radius.",5,10,YELLOW);
			skills[5][0] = new SkillBox(icons.get("champion9"),"One Will Rise Above","Battle Cry: Increases air melee attack damage.",
				"Also affects co-op party.","Increases damage and duration.",15,10,BLUE);
			skills[6][0] = new SkillBox(icons.get("champion12"),"Warrior of Blood-Eel","Increases sword damage.",null,3,10,CYAN);
			
			skills[2][1] = new SkillBox(icons.get("champion3"),"Asgard's Fury","Increases air melee attack damage.",
				null,3,10,CYAN);
			skills[4][1] = new SkillBox(icons.get("champion7"),"Feeder of Ravens","Spider: Deploys a concussion mine that explodes",
				"on contact, launching enemies airborne.","Increases launch height.",2.5,10,YELLOW);
			skills[5][1] = new SkillBox(icons.get("champion10"),"Valiant's Might","Battle Cry: All melee attacks cause enemy juggle.",
				"Also affects co-op party.","Increases duration.",3,10,BLUE);
			skills[6][1] = new SkillBox(icons.get("champion13"),"Ascent to Valhalla","Increases juggle attack launch height.",
				null,2.5,10,CYAN);
			
			skills[2][2] = new SkillBox(icons.get("champion4"),"Kinship of Gungnir","Increases Pistol weapon damage.",
				null,4,10,CYAN);
			skills[4][2] = new SkillBox(icons.get("champion8"),"Tree of Raining-Iron","Spider: Deploys a chain turret.",
				null,"Increases damage.",5,10,YELLOW);
			skills[5][2] = new SkillBox(icons.get("champion11"),"Storm of Mortal Wounds","Battle Cry: All enemies will be softened and",
				"exposed to critical strikes. Affects party.","Increases duration.",2,10,BLUE);
			skills[6][2] = new SkillBox(icons.get("champion14"),"Stopping Power","Increases Slug ammunition damage.",null,4,10,CYAN);
			
			off = 1;
		} else if(classString.equals("Commando")) {
			skills[1][1] = new SkillBox(icons.get("commando1"),"Wreaker of Mead Halls (Group Skill)","Increases damage and radius of all",
				"secondary fire.",2.5,10,GREEN);
			
			skills[2][0] = new SkillBox(icons.get("commando2"),"Pinning Shot","Increases chance that Ballistic shots will",
				"temporarily root enemies hit.",1,10,CYAN);
			skills[3][0] = new SkillBox(icons.get("commando5"),"Bullet-Tree","Spider: Deploys point-defense turret capable of",
				"shooting down incoming missiles.","Increases damage and duration.",5,10,YELLOW);
			skills[4][0] = new SkillBox(icons.get("commando8"),"Smoothbore","Battle Cry: All Ballistic shots have knockback.",
				"Also affects co-op party.","Increases duration.",20,10,BLUE);
			skills[5][0] = new SkillBox(icons.get("commando11"),"Ballistic Telemetry Feedback","Increases Ballistic weapon range.",
				null,2,10,CYAN);
			
			skills[2][1] = new SkillBox(icons.get("commando3"),"Rain of Iron","Increases Slug ammunition rate of fire.",
				null,3,10,CYAN);
			skills[3][1] = new SkillBox(icons.get("commando6"),"Cluster Munitions","Spider: Deploys a cluster mine that explodes",
				"upon contact.","Increases radius and duration.",5,10,YELLOW);
			skills[4][1] = new SkillBox(icons.get("commando9"),"Lightning Cascade","Battle Cry: Attacks will include delayed detionation",
				"explosive damage. Affects party.","Increases damage and duration.",10,10,BLUE);
			skills[5][1] = new SkillBox(icons.get("commando12"),"Delayed Fragmentation Warheads","Increases chance Ballistic shots will do",
				"explosive damage.",1,10,CYAN);
			
			skills[2][2] = new SkillBox(icons.get("commando4"),"Adept of the Burning Spear","Increases Plasma ammunition damage.",
				null,3,10,CYAN);
			skills[3][2] = new SkillBox(icons.get("commando7"),"Tree of Shrieking-Flame","Spider: Deploys a Plasma turret.",
				null,"Increases damage.",5,10,YELLOW);
			skills[4][2] = new SkillBox(icons.get("commando10"),"Cut to the Bone","Battle Cry: Increases Ballistic damage.",
				"Also affects co-op party.","Increases damage and duration.",10,10,BLUE);
			skills[5][2] = new SkillBox(icons.get("commando13"),"Gift of Gungnir","Increases Rifle damage.",
				null,3,10,CYAN);
		} else if(classString.equals("Defender")) {
			skills[1][1] = new SkillBox(icons.get("defender1"),"Defender's Resilience (Group Skill)","Decreases the amount of damage taken.",
				null,.5,10,GREEN);
			
			skills[2][0] = new SkillBox(icons.get("defender2"),"Enthalpy Reduction Attack","Increases chance of infliciting",
				"Ice damage during melee attacks.",.5,10,CYAN);
			skills[3][0] = new SkillBox(icons.get("defender5"),"Enthalpy Reduction Mines","Spider: Deploys an Ice mine that explodes",
				"upon contact, freezing all in radius.","Increases radius and duration.",5,10,YELLOW);
			skills[4][0] = new SkillBox(icons.get("defender8"),"Fimbulwinter's Numbing Touch","Battle Cry: Produces a shield which freezes",
				"enemies upon contact. Affects party.","Increases duration.",6,10,BLUE);
			skills[5][0] = new SkillBox(icons.get("defender11"),"Adept of the Light-Spear","Increases Laser weapon damage.",
				null,3,10,CYAN);
			
			skills[2][1] = new SkillBox(icons.get("defender3"),"Grim Resolve","Increases chance that damage from a killing",
				"blow will be completely deflected.",5,10,CYAN);
			skills[3][1] = new SkillBox(icons.get("defender6"),"Ward of the NORNs","Spider: Deploys a protective shield that",
				"absorbs damage.","Increases deployment duration.",3,10,YELLOW);
			skills[4][1] = new SkillBox(icons.get("defender9"),"Reversal of Wyrds","Battle Cry: Enemy status effects are reflected back.",
				"Also affects co-op party.","Increases duration.",10,10,BLUE);
			skills[5][1] = new SkillBox(icons.get("defender12"),"Tyr's Best Work","Increases the defensive bonus of",
				"equipped armor.",1,10,CYAN);
			
			skills[2][2] = new SkillBox(icons.get("defender4"),"The Berserker's Grief","Increases chance that 100% of melee damage",
				"is reflected back to the attacker.",1,10,CYAN);
			skills[3][2] = new SkillBox(icons.get("defender7"),"Tree of Scorching Light","Spider: Deploys a Laser turret.",
				null,"Increases damage.",3,10,YELLOW);
			skills[4][2] = new SkillBox(icons.get("defender10"),"Egil's Blessing","Battle Cry: Taunts all neaby enemies to focus",
				"their attacks on you.","Increases effectiveness.",10,10,BLUE);
			skills[5][2] = new SkillBox(icons.get("defender13"),"Warrior of the Iron Fist","Increases Hammer weapon damage.",
				null,3,10,CYAN);
		}
		skills[6+off][1] = new SkillBox(icons.get("fenrir"),"Spirit of Fenrir","Sentient Power: gain massive amounts of combo by",
				"unleashing the power of Fenrir on nearby enemies.","Increases duration.",30,10,CYAN);
		skills[0][2].setEnabled(true);
		skills[0][2].setVisible(false);
		skills[1][1].setEnabled(true);
		for(int k = 0; k < rows; k++)
			for(int j = 0; j < 3; j++) {
				skills[k][j].addActionListener(this);
				skills[k][j].addMouseListener(this);
				skills[k][j].addMouseWheelListener(this);
				skillTree.add(skills[k][j]);
			}
			
		trees = new JPanel(new BorderLayout());
		
		trees.add(skillTree,BorderLayout.CENTER);
		
		for(String a: alignments)
			cache.put(a,new AlignPanel(a,this,this,icons));
		alignTree = cache.get("Human");
		trees.add(alignTree,BorderLayout.EAST);
		alignTree.setVisible(false);
		
	/*	scrollpane = new JScrollPane(trees);
		scrollpane.setBorder(null);
		scrollpane.setMinimumSize(new Dimension(1,1));*/
		
		blank = new JPanel();
		blank.add(new JLabel("Hover a skill to view details."));
		Dimension pref = trees.getPreferredSize();
		blank.setPreferredSize(new Dimension(pref.width+20,pref.height/2));
		
		details = new JScrollPane(blank);
		details.setBorder(BorderFactory.createTitledBorder("Skill details"));
	/*	JPanel crazyIdea = new JPanel(new GridLayout(2,0));
	//	crazyIdea.setBorder(BorderFactory.createTitledBorder("Skill details"));
		crazyIdea.add(details);
		JTextArea area = new JTextArea();
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(getFont());
		area.setPreferredSize(new Dimension(pref.width+20,pref.height/2));
		JScrollPane areaPane = new JScrollPane(area);
		areaPane.setBorder(BorderFactory.createTitledBorder("Notes"));
		crazyIdea.add(areaPane);*/
		
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,trees,details);
		splitpane.setBorder(null);
		splitpane.setDividerSize(0);
		
		add(splitpane,BorderLayout.CENTER);
		
		built = true;
	}
	public boolean setAlignment(ClassExport ex) {
		String align = ex.alignString;
		if(!built || align == null || (!align.equals("Human") && !align.equals("Cybernetic"))) return false;
		skills[0][2].setVisible(align.equals("Human"));
		alignString = align;
		trees.remove(alignTree);
		alignTree = new AlignPanel(ex,this,this,icons);
		cache.put(alignString,alignTree);
		trees.add(alignTree,BorderLayout.EAST);
		alignTree.setVisible(true);
		updateUI();
		updatePoints();
		splitpane.resetToPreferredSizes();
		updateUI();
		revalidate();
		repaint();
		return true;
	}
	public boolean setAlignment(String align) {
		if(!built || (!align.equals("Human") && !align.equals("Cybernetic"))) return false;
		if(skills != null) skills[0][2].setVisible(align.equals("Human"));
		alignString = align;
		trees.remove(alignTree);
		if(cache.size() == 0 || cache.get(alignString) == null) {
			alignTree = new AlignPanel(alignString,this,this,icons);
			cache.put(alignString,alignTree);
		} else if(cache.get(alignString) != null) {
			alignTree = cache.get(alignString);
		}
		trees.add(alignTree,BorderLayout.EAST);
		alignTree.setVisible(true);
		updateUI();
		updatePoints();
		splitpane.resetToPreferredSizes();
		updateUI();
		revalidate();
		repaint();
		return true;
	}
	public String getAlignment() { return alignString; }
	public String getClassString() { return classString; }
	public HashMap<String,AlignPanel> getCache() { return cache; }
	public void actionPerformed(ActionEvent e){
		String name = e.getActionCommand();
		if(name.equals("Export"))
			exportAsPNG(getParent());
		if(name.equals("Reset"))
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
					actionPerformed(new ActionEvent(skills[2][1],ActionEvent.ACTION_PERFORMED,"Update"));
					actionPerformed(new ActionEvent(skills[2][2],ActionEvent.ACTION_PERFORMED,"Update"));
				}
				if(points > 0) {
					skills[3+off][1].setEnabled(false);
					skills[3+off][2].setEnabled(false);
				}
				skills[4+off][0].setEnabled(points >= 4);
			} else if(source == skills[3+off][1]) {
				if(points == 0) {
					actionPerformed(new ActionEvent(skills[2][0],ActionEvent.ACTION_PERFORMED,"Update"));
					actionPerformed(new ActionEvent(skills[2][2],ActionEvent.ACTION_PERFORMED,"Update"));
				}
				if(points > 0) {
					skills[3+off][0].setEnabled(false);
					skills[3+off][2].setEnabled(false);
				}
				skills[4+off][1].setEnabled(points >= 4);
			} else if(source == skills[3+off][2]) {
				if(points == 0) {
					actionPerformed(new ActionEvent(skills[2][0],ActionEvent.ACTION_PERFORMED,"Update"));
					actionPerformed(new ActionEvent(skills[2][1],ActionEvent.ACTION_PERFORMED,"Update"));
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
	public void reset() { zero(); }
	public void zero() {
	/*	skills[0][2].clear();
		skills[1][1].clear();
		total = 0;*/
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				skills[k][j].setPoints(0);
		alignTree.zero();
		updatePoints();
	}
	private void updatePoints() {
		total = getTotalPoints();
		pointsLabel.setText(total+"/95");
		progress.setValue(total);
		if(total == 95)  {
			pointsLabel.setText("95/95  **TOTAL REACHED**");
			pointsLabel.setForeground(GREEN);
			levelLabel.setText("Level: 50");
			levelLabel.setForeground(getForeground());
			levelLabel.setForeground(GREEN);
			error.setVisible(false);
		} else if(total > 95) {
			error.setVisible(true);
			pointsLabel.setForeground(Color.red);
			levelLabel.setForeground(Color.red);
		} else {
			error.setVisible(false);
			pointsLabel.setForeground(getForeground());
		//	double level = 1;
		/*	if(total <= 24)
				level = Math.ceil(total/3.0 + 1.0);
			else if(total <= 84)
				level = Math.ceil((total-24)/2.0 + 9.0);
			else if(total <= 95)
				level = Math.ceil((total-84)/1.0 + 39.0);*/
			int level = (int)Math.ceil((total <= 24) ? total/3.0 + 1.0 : (total <= 84) ? (total-24)/2.0 + 9.0 : total-84 + 39.0);
			levelLabel.setText("Level: "+level);
			levelLabel.setForeground(getForeground());
		}
	}
	public int getTotalPoints() {
		total = 0;
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				total += skills[k][j].getPoints();
		if(alignString != null && alignString.equals("Cybernetic")) total -= skills[0][2].getPoints();
		return (total += alignTree.getTotalPoints());
	}
	public ClassExport export() {
		ClassExport export = new ClassExport();
		export.classString = classString;
	//	System.out.println(classString + " " + export.classString);
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
	public boolean exportAsPNG(Component parent) {
		if(saveImage.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File file = saveImage.getSelectedFile();
			if(!filter.acceptFile(file))
				file = new File(file.getPath()+".png");
			if(file.exists()) {
				String[] choices = new String[] {"Overwrite","Choose another file","Cancel"};
				int choice = JOptionPane.showOptionDialog(this,"A file with this name already exists.","Conflict!",
					JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
					icons.get("dialog warning"),choices,choices[1]);
				if(choice == 2 || choice == JOptionPane.CLOSED_OPTION) return false;
				if(choice == 1) return exportAsPNG(this);
			}
			try {
				Dimension treesSize = trees.getSize();
				BufferedImage image = new BufferedImage(treesSize.width,treesSize.height,BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				trees.paint(g);
				g.dispose();
				ImageIO.write(image,"png",file);
				return true;
			} catch(java.io.IOException ex) {
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Error saving PNG!",JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else return false;
	}
	public boolean equals(Object o) {
		if(!(o instanceof ClassPanel)) return false;
		return classString.equals(((ClassPanel)o).getClassString());
	}
	public void mouseEntered(MouseEvent e) {
		SkillBox box = (SkillBox)e.getComponent();
		JComponent det = box.getDetails();
	//	details.setViewportView((det == null) ? blank : det);
		if(det != null)  {
			details.setViewportView(det);
	//		if(box.getBack() == null) box.setBack(box.getBackground());
	//		box.setBackground(box.getColor());
		}
	}
	public void mouseExited(MouseEvent e) {
	//	details.setViewportView(blank);
	//	SkillBox box = (SkillBox)e.getComponent();
	//	if(box.isEnabled()) box.setBackground(box.getBack());
	}
	public void mouseClicked(MouseEvent e) {
	//	increasePoints(!(e.isMetaDown() || e.isControlDown()),(SkillBox)e.getSource());
	}
	public void mousePressed(MouseEvent e) {
		SkillBox box = (SkillBox)e.getSource();
		if(!box.built() || !box.isEnabled() || !box.isVisible()){ return;
		} else if(!e.isAltDown()) {
			increasePoints(!(e.isMetaDown() || e.isControlDown()),(SkillBox)e.getSource());
		} else if(e.isAltDown() && (e.isMetaDown() || e.isControlDown())) {
			box.getPopup().show(box,e.getX(),e.getY());
		} else if(e.isAltDown() && (!e.isMetaDown() && !e.isControlDown())) {
			Object input = JOptionPane.showInputDialog(
				box,
				box.getFullDescription()+"\n"+((box.getLevelBonus() != null) ? "\nLevel Bonus: "+box.getLevelBonus() : "")
					+"\nSet point value to?  Current:"+box.getSpinnerLabelText(),
				box.getTitle(),
				JOptionPane.QUESTION_MESSAGE,box.getIcon(),null,box.getValue());
			int in = 0;
			if(input == null || ((String)input).trim().equals(""))
				return;
			try {
				in = Integer.parseInt(((String)input).trim());
			} catch(NumberFormatException ex) {
				getToolkit().beep();
				JOptionPane.showMessageDialog(box,"Invalid input!","Error!",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(in > box.getMax() || in < 0) {
				getToolkit().beep();
				JOptionPane.showMessageDialog(box,"Invalid input!","Error!",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(in > box.getPoints() && total + in > 95)
				in = in - (total+in-95) + box.getPoints();
			box.setPoints(in);
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseWheelMoved(MouseWheelEvent e) {
		increasePoints(e.getWheelRotation() == -1,(SkillBox)e.getSource());
	}
	private void increasePoints(boolean up, SkillBox box) {
		if(!box.isEnabled() || !box.built()) return;
		Integer next = null;
		if(!up)
			next = box.getPreviousValue();
		else {
			if(total >= 95) return;
			next = box.getNextValue();
		}
		if(next != null)
			box.setPoints(next.intValue());
	}
	public void showProgressBar(boolean show) {
		if(progress != null) progress.setVisible(show);
	}
	public SkillBox[][] getSkillBoxes() {
		return skills;
	}
}