import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class AlignPanel extends JPanel implements ActionListener, java.io.Serializable {
	public static final long serialVersionUID = 32453467547L;
	private Color CYAN = Color.cyan, YELLOW = Color.yellow, BLUE = Color.blue;
	private String alignment;
	private SkillBox skills[][];
	private ActionListener listener;
	private Icons icons;
	public AlignPanel(ClassExport ex, ActionListener al, MouseListener ml, Icons i) {
		this(ex.alignString,al,ml,i);
		Iterator<Integer> iter = ex.alignPoints.iterator();
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				if(iter.hasNext())
					skills[k][j].setPoints(iter.next());
	}
	public AlignPanel(String align, ActionListener al, MouseListener ml, Icons i) {
		super(new GridLayout(7,3,3,0));
		alignment = align;
		listener = al;
		icons = i;
		setBorder(BorderFactory.createTitledBorder(align + " Alignment tree"));

		skills = new SkillBox[7][3];
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				skills[k][j] = new SkillBox();

		if(align.equals("Human")) {
			skills[0][1] = new SkillBox(icons.get("Human1"),"The Everburning Heart","Decrease combo fuel cost by hit counter.",
				"Bonuses applied at 25, 50, 75, 100, and 150.",2,5,CYAN);
			skills[1][1] = new SkillBox(icons.get("Human2"),"Hero's Heart","Increases melee attack speed.",null,2);
			
			skills[2][0] = new SkillBox(icons.get("Human3"),"Calm Under Fire","Increases reload speed.",null,2.5);
			skills[3][0] = new SkillBox(icons.get("Human5"),"A Need To Survive","Increases Ballistic rate of fire.",
				null,2,10,CYAN);
			skills[4][0] = new SkillBox(icons.get("Human7"),"Daring Shot","Increases chance of shots ricocheting",
				"and hitting additional enemies.",1,10,CYAN);
			
			skills[2][2] = new SkillBox(icons.get("Human4"),"Desire to Overcome","Increases Fierce attack range.",
				null,2);
			skills[3][2] = new SkillBox(icons.get("Human6"),"Call of Ruin","Increases Ruiner attack damage.",
				null,2,10,CYAN);
			skills[4][2] = new SkillBox(icons.get("Human8"),"Spirit Renewal","Decreases Spider recharge time.",
				null,5,10,CYAN);
				
			skills[5][1] = new SkillBox(icons.get("Human9"),"Quick to Anger","Level 1-9: Increases combo meter growth rate.",
				"Level 10: Increases maximum combo level.",11,10,CYAN);
		} else {
			skills[0][1] = new SkillBox(icons.get("Cyber1"),"Augmented Musculature","Increases attack damage by hit counter.",
				"Bonuses applied at 25, 50, 75, 100, and 150.",1,5,CYAN);
			skills[1][1] = new SkillBox(icons.get("Cyber2"),"Enhanced Mobility Actuators","Increases Slide attack damage.",
				null,1,10,CYAN);
			
			skills[2][0] = new SkillBox(icons.get("Cyber3"),"Retooled Munitions Dispenser","Increases ammunition capacity",
				"for all guns.",5,10,CYAN);
			skills[3][0] = new SkillBox(icons.get("Cyber5"),"Ballistic Telemetry Feedback","Increases Ballisitc weapon range.",
				null,1,10,CYAN);
			skills[4][0] = new SkillBox(icons.get("Cyber7"),"Terminal Ballistics Analysis","Increases chance that shots will penetrate",
				"the target and hit additional enemies.",1,10,CYAN);
			
			skills[2][2] = new SkillBox(icons.get("Cyber4"),"Spreader of Ruin","Increases radius of Ruiner attacks.",
				null,1,10,CYAN);
			skills[3][2] = new SkillBox(icons.get("Cyber6"),"Submunition Distributor","Increases the rate at which bonus",
				"effects occur on attacks.",.5,10,CYAN);
			skills[4][2] = new SkillBox(icons.get("Cyber8"),"Everflowing Source","Increases the Spider's max deployment time.",
				null,5,10,CYAN);
			
			skills[5][1] = new SkillBox(icons.get("Cyber9"),"Hardened Carbotanium Chassis","Increases the defensive bonus of",
				"equipped armor pieces.",1,10,CYAN);
		}
		skills[0][1].setEnabled(true);
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++) {
				skills[k][j].addActionListener(this);
				skills[k][j].addMouseListener(ml);
				skills[k][j].addMouseWheelListener((MouseWheelListener)ml);
				add(skills[k][j]);
			}
	}
	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof SkillBox)) return;
		SkillBox source = (SkillBox)e.getSource();
		int points = source.getPoints();
		if(source == skills[0][1]) {
			skills[1][1].setEnabled(points >= 2);
		} else if(source == skills[1][1]) {
			skills[2][0].setEnabled(points >= 6);
			skills[2][2].setEnabled(points >= 6);
		} else if(source == skills[2][0]) {
			skills[3][0].setEnabled(points >= 6);
		} else if(source == skills[3][0]) {
			skills[4][0].setEnabled(points >= 6);
		} else if(source == skills[2][2]) {
			skills[3][2].setEnabled(points >= 6);
		} else if(source == skills[3][2]) {
			skills[4][2].setEnabled(points >= 6);
		} else if(source == skills[4][0] || source == skills[4][2]) {
			skills[5][1].setEnabled(skills[4][0].getPoints() >= 6
								||  skills[4][2].getPoints() >= 6);
		}
		if(listener != null)
			listener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"Update count"));
	}
	public void zero() {
	//	skills[0][1].clear();
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				skills[k][j].setPoints(0);
	}
	public int getTotalPoints() {
		int total = 0;
		for(int k = 0; k < skills.length; k++)
			for(int j = 0; j < skills[k].length; j++)
				total += skills[k][j].getPoints();
		return total;
	}
	public SkillBox[][] getSkills() { return skills; }
}