import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;

class SkillBox extends JPanel implements ChangeListener {
	public static final long serialVersionUID = -3456235547L;
	private String description;
	private double multiplier;
	private int maximum;
	ActionListener dep;
	private DecimalFormat d = new DecimalFormat("#00.00%");
	private JLabel percent = new JLabel(/*"Current: "+*/d.format(0.0*0.0)), maxLabel;
	private JSpinner points = new JSpinner(new SpinnerNumberModel(0,0,10,1));
//	private JComboBox points = new JComboBox(new Integer[]
//		{0,1,2,3,4,5,6,7,8,9,10});
//	private JButton plus, minus;
	public SkillBox() {}
	public SkillBox(String d, double m, Color col) {
		this(d,m,col,10);
	}
	public SkillBox(String de, double m, Color col, int max) {
		super(new BorderLayout());
		description = de;
		multiplier = m;
		maximum = max;
		points = new JSpinner(new SpinnerNumberModel(0,0,maximum,1));
		points.addChangeListener(this);
		add(points,BorderLayout.WEST);
		add(new JLabel(description),BorderLayout.CENTER);
		add(percent,BorderLayout.EAST);
	/*	Box statBox = Box.createVerticalBox();
		statBox.add(points);
		statBox.add(percent);
		statBox.add(maxLabel = new JLabel("Max: "+d.format(maximum/100.0 * multiplier)));
		add(statBox,BorderLayout.WEST);
		Box descBox = Box.createVerticalBox();
		descBox.add(new JLabel("[title]"));
		descBox.add(new JLabel(description));
		descBox.add(new JLabel("desc line 2"));
		add(descBox,BorderLayout.CENTER);*/
		setBackground(col);
		setEnabled(false);
	}
	public SkillBox(String d, double m) {
		this(d,m,Color.cyan,10);
	}
/*	public SkillBox(String d, double m, Color col, int max) {
		this(d,m,col);
		points.removeAllItems();
		if(max <= 0) max = 10;
		for(int k = 0; k <= max; k++)
			points.addItem(k);
	}
	public SkillBox(String desc, double multi) {
	//	super(BoxLayout.X_AXIS);
		super(new BorderLayout());
		description = desc;
		multiplier = multi;
	//	points.addItemListener(this);
	//	(plus = new JButton("+")).addActionListener(this);
	//	(minus = new JButton("-")).addActionListener(this);
	/*	Box pointBox = Box.createHorizontalBox();
		pointBox.add(minus);
		pointBox.add(points);
		pointBox.add(plus);
		add(pointBox,BorderLayout.WEST);
	//	add(createHorizontalStrut(3));
		add(new JLabel(description),BorderLayout.CENTER);
	//	add(createHorizontalStrut(3));
		add(percent,BorderLayout.EAST);
		setBackground(Color.cyan);
		setEnabled(false);
	}*/
	public void addActionListener(ActionListener al) {
		dep = al;
	}
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() instanceof JSpinner) {
			percent.setText(/*"Current: "+*/d.format(((Integer)points.getValue())/100.0 * multiplier));
			if(dep != null)
				dep.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
										"Change points"));
		}
	}
	public void clear() { setPoints(0); }
	public void setEnabled(boolean b) {
		points.setEnabled(b);
		percent.setEnabled(b);
		if(!b) points.setValue(0);
	}
	public boolean isEnabled() { return points.isEnabled(); }
	public int getPoints() { return (Integer)points.getValue(); }
	public void setPoints(int p) { points.setValue(p); }
	public String getDescription() { return description; }
	public boolean equals(Object box) {
		if(!(box instanceof SkillBox)) return false;
		return description.equals(((SkillBox)box).getDescription());
	}
}