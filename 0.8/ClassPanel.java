import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;

class ClassPanel extends JPanel implements ActionListener, java.io.Serializable {
	public static final long serialVersionUID = 386546547L;
	private JComboBox alignmentBox;
	private SkillBox[][] skills;
	private AlignPanel alignTree;
	private ActionListener listener;
	private String classString, alignString;
	private String[] alignments = new String[] {"Human","Cybernetic"};
	public String toString() { return classString; }
	public ClassPanel() {}
	public ClassPanel(String classS) { this(classS,null); }
	public ClassPanel(String classS, ActionListener al) {
		super(new BorderLayout());
		classString = classS;
		System.out.println("New ClassPanel initialization: "+classString);
		addActionListener(al);
		setBorder(BorderFactory.createTitledBorder(classString+" trees"));
		
	//	alignmentBox = new JComboBox(new String[] {"Human","Cybernetic"});
	//	alignmentBox.addActionListener(this);
	/*	JPanel profile = new JPanel(new BorderLayout());
		Box name = Box.createHorizontalBox();
		name.add(new JLabel("  Name:  "));
		name.add(new JTextField(" ",35));
		profile.add(name,BorderLayout.NORTH);*/
		Box alignBox = Box.createHorizontalBox();
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
		JButton reset = new JButton("Reset");
		reset.addActionListener(this);
		alignBox.add(Box.createHorizontalGlue());
		alignBox.add(reset);
	//	alignBox.add(alignmentBox);
	//	profile.add(alignBox,BorderLayout.CENTER);
	//	add(profile,BorderLayout.NORTH);
		add(alignBox,BorderLayout.NORTH);

		int rows = classString.equals("Champion") ? 8 : 7;
		JPanel skillTree = new JPanel(new GridLayout(rows,3,3,0));
		skillTree.setBorder(BorderFactory.createTitledBorder("Skill tree"));
		skills = new SkillBox[rows][3];
		for(int k = 0; k < rows; k++)
			for(int j = 0; j < 3; j++)
				skills[k][j] = new SkillBox();//Box.createHorizontalBox();
		skills[0][2] = new SkillBox("Human - Adds Ruiner Damage",5);
		if(classString.equals("Berserker")) {
			skills[1][1] = new SkillBox("Group Skill: Increase combo meter growth rate",11,Color.green);
	
			skills[2][0] = new SkillBox("Increase attack speed with hit counter",1);
			skills[3][0] = new SkillBox("Spider: Sticky bomb",5,Color.yellow);
			skills[4][0] = new SkillBox("Battle Cry: Increase melee attack speed",3,Color.blue);
			skills[5][0] = new SkillBox("Increase Dual-Wield attack speed",3);
	
			skills[2][1] = new SkillBox("Increase fierce attack speed",5);
			skills[3][1] = new SkillBox("Spider: Slow enemy turret",5,Color.yellow);
			skills[4][1] = new SkillBox("Battle Cry: Increase movement speed",5,Color.blue);
			skills[5][1] = new SkillBox("Increase slide attack speed",1);
	
			skills[2][2] = new SkillBox("Increase damage channeling chance",5);
			skills[3][2] = new SkillBox("Spider: Proximity mine",5,Color.yellow);
			skills[4][2] = new SkillBox("Battle Cry: Increase melee damage",6,Color.blue);
			skills[5][2] = new SkillBox("Increase Dual-Wield damage",3);
	
			skills[6][1] = new SkillBox("Sentient Weapon",5);
		} else if(classString.equals("BioEngineer")) {
			skills[1][1] = new SkillBox("Group Skill: Increase health regen over time",.1,Color.green,8);
			
			skills[2][0] = new SkillBox("Increase chance that attacked enemies will turn",1);
			skills[3][0] = new SkillBox("Spider: EMP mine",5,Color.yellow);
			skills[4][0] = new SkillBox("Battle Cry: Minor heal",4,Color.blue);
			skills[5][0] = new SkillBox("Increase juggle attack launch height",2);
			
			skills[2][1] = new SkillBox("Increase 2-Handed weapon damage",3);
			skills[3][1] = new SkillBox("Spider: Protective shield",5,Color.yellow);
			skills[4][1] = new SkillBox("Battle Cry: Large delayed heal",6,Color.blue);
			skills[5][1] = new SkillBox("Group Skill: Increase max hit points",1,Color.green);
			
			skills[2][2] = new SkillBox("Increase 1-Handed weapon damage",3);
			skills[3][2] = new SkillBox("Spider: Healing mine",5,Color.yellow);
			skills[4][2] = new SkillBox("Battle Cry: Enhance resistances and stats",5,Color.blue);
			skills[5][2] = new SkillBox("Increase chance of Lightening proc",1);
			
			skills[6][1] = new SkillBox("Sentient Weapon",5);
		} else if(classString.equals("Champion")) {
			skills[1][1] = new SkillBox("Group Skill: Increase chance of critical strike",.5,Color.green);
			
			skills[2][0] = new SkillBox("Increase chance of fire proc",1);
			skills[3][0] = new SkillBox("Increase Finisher damage",3);
			skills[4][0] = new SkillBox("Spider: Fire mine",5,Color.yellow);
			skills[5][0] = new SkillBox("Battle Cry: Increase air melee damage",15,Color.blue);
			skills[6][0] = new SkillBox("Increase sword damage",3);
			
			skills[2][1] = new SkillBox("Increase air melee damage",15);
			skills[4][1] = new SkillBox("Spider: Concussive mine",5,Color.yellow);
			skills[5][1] = new SkillBox("Battle Cry: All melee cause juggle",5,Color.blue);
			skills[6][1] = new SkillBox("Increase juggle attack height",2);
			
			skills[2][2] = new SkillBox("Increase Pistol damage",4);
			skills[4][2] = new SkillBox("Spider: Deploy chain turret",5,Color.yellow);
			skills[5][2] = new SkillBox("Battle Cry: Enemy soften",5,Color.blue);
			skills[6][2] = new SkillBox("Increase Slug ammunition damage",4);
			
			skills[7][1] = new SkillBox("Sentient Weapon",5);
		} else if(classString.equals("Commando")) {
			skills[1][1] = new SkillBox("Group Skill: Increase secondary fire power",2.5,Color.green);
			
			skills[2][0] = new SkillBox("Increase chance of Ballistic rooting",1);
			skills[3][0] = new SkillBox("Spider: Point defense turret",5,Color.yellow);
			skills[4][0] = new SkillBox("Battle Cry: Adds Ballistic knock-back",5,Color.blue);
			skills[5][0] = new SkillBox("Increase Ballistic range",1);
			
			skills[2][1] = new SkillBox("Increase Slug ammo rate of fire",3);
			skills[3][1] = new SkillBox("Spider: Cluster mine",5,Color.yellow);
			skills[4][1] = new SkillBox("Battle Cry: Attacks include detonation",10,Color.blue);
			skills[5][1] = new SkillBox("Increase chance of explosive Ballistic impact",1);
			
			skills[2][2] = new SkillBox("Increase Plasma ammo damage",3);
			skills[3][2] = new SkillBox("Spider: Plasma turret",5,Color.yellow);
			skills[4][2] = new SkillBox("Battle Cry: Increased Ballistic damage",10,Color.blue);
			skills[5][2] = new SkillBox("Increase Rifle damage",3);
			
			skills[6][1] = new SkillBox("Sentient Weapon",5);
		} else if(classString.equals("Defender")) {
			skills[1][1] = new SkillBox("Group Skill: Decrease damage taken",.5,Color.green);
			
			skills[2][0] = new SkillBox("Increase chance of Ice Damage",1);
			skills[3][0] = new SkillBox("Spider: Ice mine",5,Color.yellow);
			skills[4][0] = new SkillBox("Battle Cry: Freezing shield",3,Color.blue);
			skills[5][0] = new SkillBox("Increase Laser damage",3);
			
			skills[2][1] = new SkillBox("Increase chance killing blow will be deflected",5);
			skills[3][1] = new SkillBox("Spider: Protective shield",5,Color.yellow);
			skills[4][1] = new SkillBox("Battle Cry: Reflect enemy status",10,Color.blue);
			skills[5][1] = new SkillBox("Increase defensive bonus on armor",1);
			
			skills[2][2] = new SkillBox("Increase chance of damage reciprication",1);
			skills[3][2] = new SkillBox("Spider: Laser turret",5,Color.yellow);
			skills[4][2] = new SkillBox("Battle Cry: Taunt",10,Color.blue);
			skills[5][2] = new SkillBox("Increase Hammer damage",3);
			
			skills[6][1] = new SkillBox("Sentient Weapon",5);
		}
		skills[0][2].setEnabled(true);
		skills[1][1].setEnabled(true);
		for(int k = 0; k < rows; k++)
			for(int j = 0; j < 3; j++) {
				skills[k][j].addActionListener(this);
				skillTree.add(skills[k][j]);
			}
		add(skillTree,BorderLayout.CENTER);

	//	if(alignmentBox.getSelectedIndex() == 1) skills[0][2].setVisible(false);

		alignTree = new AlignPanel("Human");
		add(alignTree,BorderLayout.SOUTH);
		alignTree.setVisible(false);
//		buttons.get(0).setSelected(true);
	}
	public boolean setAlignment(String align) {
		if( !align.equals("Human") && !align.equals("Cybernetic")) return false;
		skills[0][2].setVisible(align.equals("Human"));
		alignString = align;
		remove(alignTree);
		alignTree = new AlignPanel(align);
		add(alignTree,BorderLayout.SOUTH);
		alignTree.setVisible(true);
		updateUI();
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
				if(skills[3+off][1].getPoints() > 0 || skills[3+off][2].getPoints() > 0)
					return;
				skills[3+off][0].setEnabled(points >= 6);
			} else if(source == skills[2][1]) {
				if(skills[3+off][0].getPoints() > 0 || skills[3+off][2].getPoints() > 0)
					return;
				skills[3+off][1].setEnabled(points >= 6);
			} else if(source == skills[2][2]) {
				if(skills[3+off][0].getPoints() > 0 || skills[3+off][1].getPoints() > 0)
					return;
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
	}
	public void addActionListener(ActionListener al) { listener = al; }
	public void zero() {
		skills[0][2].clear();
		skills[1][1].clear();
		alignTree.zero();
	}
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