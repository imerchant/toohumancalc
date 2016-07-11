import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.text.DecimalFormat;

class SkillBox extends JPanel implements ChangeListener, java.io.Serializable, MouseListener {
	public static final long serialVersionUID = -3456235547L;
	private ImageIcon icon;
	private String desc1,desc2,iconSrc,title;
	private double multiplier;
	private int maximum;
	ActionListener dep;
	private DecimalFormat d = new DecimalFormat("#00.00%");
	private Color bg;
	private JLabel percent = new JLabel(/*"Current: "+*/d.format(0.0*0.0)), maxLabel;
	private JSpinner points = new JSpinner(new SpinnerNumberModel(0,0,10,1));
//	private JComboBox points = new JComboBox(new Integer[]
//		{0,1,2,3,4,5,6,7,8,9,10});
//	private JButton plus, minus;
	public SkillBox() {}
	public SkillBox(ImageIcon i, String t, String d1, String d2, double multi) {
		this(i,t,d1,d2,multi,10,Color.cyan);
	}
	public SkillBox(String iconSource, String t, String d1, String d2, double multi) {
		this(iconSource,t,d1,d2,multi,10,Color.cyan);
	}
	public SkillBox(ImageIcon i, String t, String d1, String d2, double multi, int max, Color col) {
		super(new BorderLayout());
		icon = i;
		title = t;
		desc1 = d1;
		desc2 = d2;
		multiplier = multi;
		maximum = max;
		bg = col;
		
	//	setBorder(BorderFactory.createLineBorder(Color.black,2));
		points = new JSpinner(new SpinnerNumberModel(0,0,maximum,1));
		points.addChangeListener(this);
		maxLabel = new JLabel("Max: "+d.format(maximum/100.0 * multiplier));
	//	java.net.URL imgURL = getClass().getResource(iconSrc);
	//	if(imgURL != null) icon = new ImageIcon(imgURL);
		JLabel iconLabel = new JLabel(icon);
		Box descBox = Box.createVerticalBox();
		descBox.add(new JLabel("<html><u>"+title+"</u>"));
		descBox.add(new JLabel(desc1));
		descBox.add(new JLabel(desc2));
		Box percBox = Box.createVerticalBox();
		percBox.add(points);
		percBox.add(percent);
		percBox.add(maxLabel);
		percBox.setToolTipText("Each level: +"+multiplier+"%");
		
		add(iconLabel,BorderLayout.WEST);
		add(descBox,BorderLayout.CENTER);
		add(percBox,BorderLayout.EAST);
		
		setBackground(bg);
		setEnabled(false);
		addMouseListener(this);
	}
	public SkillBox(String iconSource, String t, String d1, String d2, double multi, int max, Color col) {
		super(new BorderLayout());
		iconSrc = iconSource;
		title = t;
		desc1 = d1;
		desc2 = d2;
		multiplier = multi;
		maximum = max;
		bg = col;
		
		points = new JSpinner(new SpinnerNumberModel(0,0,maximum,1));
		points.addChangeListener(this);
		maxLabel = new JLabel("Max: "+d.format(maximum/100.0 * multiplier));
		java.net.URL imgURL = getClass().getResource(iconSrc);
		if(imgURL != null) icon = new ImageIcon(imgURL);
		JLabel iconLabel = new JLabel(icon);
		Box descBox = Box.createVerticalBox();
		descBox.add(new JLabel("<html><u>"+title+"</u>"));
		descBox.add(new JLabel(desc1));
		descBox.add(new JLabel(desc2));
		Box percBox = Box.createVerticalBox();
		percBox.add(points);
		percBox.add(percent);
		percBox.add(maxLabel);
		percBox.setToolTipText("Each level: +"+multiplier+"%");
		
		add(iconLabel,BorderLayout.WEST);
		add(descBox,BorderLayout.CENTER);
		add(percBox,BorderLayout.EAST);
		
		setBackground(bg);
		setEnabled(false);
		addMouseListener(this);
	}
	public SkillBox(String d, double m, Color col) {
		this(d,m,col,10);
	}
	public SkillBox(String de, double m, Color col, int max) {
		this("imgs/Cyber1.png","title",de,null,m,max,col);
	/*	super(new BorderLayout());
		description = de;
		multiplier = m;
		maximum = max;
		points = new JSpinner(new SpinnerNumberModel(0,0,maximum,1));
		points.addChangeListener(this);
	/*	add(points,BorderLayout.WEST);
		add(new JLabel(description),BorderLayout.CENTER);
		add(percent,BorderLayout.EAST);
		maxLabel = new JLabel("Max: "+d.format(maximum/100.0 * multiplier));
	/* 	Box statBox = Box.createVerticalBox();
		statBox.add(points);
		statBox.add(percent);
		statBox.add(maxLabel);
		add(statBox,BorderLayout.WEST);
		Box descBox = Box.createVerticalBox();
		descBox.add(new JLabel("[title]"));
		descBox.add(new JLabel(description));
		descBox.add(new JLabel("desc line 2"));
		add(descBox,BorderLayout.CENTER);*/
		
	/*	java.net.URL imgURL = getClass().getResource("imgs\\Cyber1.png");
		ImageIcon icon = null;
		if(imgURL != null) icon = new ImageIcon(imgURL);
		JLabel iconLabel = new JLabel(icon);
		add(iconLabel,BorderLayout.WEST);
		Box descBox = Box.createVerticalBox();
		descBox.add(new JLabel("[title]"));
		descBox.add(new JLabel(description));
		descBox.add(new JLabel("desc line 2"));
		add(descBox,BorderLayout.CENTER);
		Box east = Box.createVerticalBox();
	//	Box topeast = Box.createHorizontalBox();
	//	topeast.add(percent);
	//	topeast.add(points);
	//	east.add(topeast);
		east.add(points);
		east.add(percent);
		east.add(maxLabel);
		add(east,BorderLayout.EAST);
		
		setBackground(col);
		setEnabled(false);*/
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
	public String getDescription() { return desc1; }
	public String getTitle() { return title; }
	public boolean equals(Object box) {
		if(!(box instanceof SkillBox)) return false;
		return title.equals(((SkillBox)box).getTitle());
	}
	public void mouseEntered(MouseEvent e) {
	//	setBorder(BorderFactory.createLineBorder(Color.red));
	}
	public void mouseExited(MouseEvent e) {
		setBorder(null);
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
}