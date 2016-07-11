import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;

class AlignPanel extends JPanel implements ActionListener {
	public static final long serialVersionUID = 32453467547L;
	private String alignment;
	private SkillBox skills[][];
	public AlignPanel(String align) {
		super(new GridLayout(6,3,3,0));
		alignment = align;
		setBorder(BorderFactory.createTitledBorder(align + " Alignment tree"));

		skills = new SkillBox[6][3];
		for(int k = 0; k < 6; k++)
			for(int j = 0; j < 3; j++)
				skills[k][j] = new SkillBox();

		if(align.equals("Human")) {
			skills[0][1] = new SkillBox("Decrease combo fuel cost",2,Color.cyan,5);
			skills[1][1] = new SkillBox("Increase melee attack speed",2);

			skills[2][0] = new SkillBox("Increase reload speed",2.5);
			skills[3][0] = new SkillBox("Increase Ballistic rate of fire",2,Color.blue);
			skills[4][0] = new SkillBox("Increase chance of shot ricochet",1,Color.yellow);

			skills[2][2] = new SkillBox("Increase fierce attack range",2);
			skills[3][2] = new SkillBox("Increase Ruiner attack damage",2,Color.blue);
			skills[4][2] = new SkillBox("Decrease Spider recharge time",5,Color.yellow);

			skills[5][1] = new SkillBox("Increase combo mete growth rate",11);
		} else {
			skills[0][1] = new SkillBox("Increase attack damage by hit counter",1,Color.cyan,5);
			skills[1][1] = new SkillBox("Increase slide attack damage",1);
			
			skills[2][0] = new SkillBox("Increase ammunition capacity",5);
			skills[3][0] = new SkillBox("Increase Ballistic weapon range",1,Color.blue);
			skills[4][0] = new SkillBox("Increase shot penetration chance",1,Color.yellow);
			
			skills[2][2] = new SkillBox("Increase Ruiner radius",1);
			skills[3][2] = new SkillBox("Increase bonus effect rate",0.5,Color.blue);
			skills[4][2] = new SkillBox("Increase Spider deploy time",5,Color.yellow);
			
			skills[5][1] = new SkillBox("Increase defensive bonuses of armor",1);
		}
		skills[0][1].setEnabled(true);
		for(int k = 0; k < 6; k++)
			for(int j = 0; j < 3; j++) {
				skills[k][j].addActionListener(this);
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
	}
	public void zero() {
		skills[0][1].clear();
	}
}