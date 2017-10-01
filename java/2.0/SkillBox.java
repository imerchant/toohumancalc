import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.DecimalFormat;

class SkillBox extends JPanel implements ChangeListener, java.io.Serializable, MouseListener {
	public static final long serialVersionUID = -3456235547L;
	private ImageIcon icon;
	private String desc1,desc2,iconSrc,title;
	private double multiplier;
	private int maximum;
	private ActionListener dep;
	private boolean built = false;
	private DecimalFormat d = new DecimalFormat("#0.0%");
	private Color bg,back;
	private JLabel percent = new JLabel("Current Bonus: +"+d.format(0.0*0.0)), maxLabel,spinnerLabel;
	private JPanel details;
	private JSpinner points = new JSpinner(new SpinnerNumberModel(0,0,10,1));
	public SkillBox() {}
	public SkillBox(ImageIcon i, String t, String d1, String d2, double multi) {
		this(i,t,d1,d2,multi,10,Color.cyan);
	}
	public SkillBox(String iconSource, String t, String d1, String d2, double multi) {
		this(iconSource,t,d1,d2,multi,10,Color.cyan);
	}
	public SkillBox(ImageIcon i, String t, String d1, String d2, double multi, int max, Color col) {
		super(new BorderLayout());
		title = t;
		desc1 = d1;
		desc2 = d2;
		multiplier = multi;
		maximum = max;
		bg = col;
		build(i);
		built = true;
	}
	public SkillBox(String iconSource, String t, String d1, String d2, double multi, int max, Color col) {
		super(new BorderLayout());
		build(new ImageIcon(getClass().getResource(iconSrc)));
	}
	public SkillBox(String d, double m, Color col) {
		this(d,m,col,10);
	}
	public SkillBox(String de, double m, Color col, int max) {
		this("unknown.png","title",de,null,m,max,col);
	}
	public SkillBox(String d, double m) {
		this(d,m,Color.cyan,10);
	}
	private void build(ImageIcon i) {
		icon = i;
		if(desc2 == null)
			desc2 = "<html></html>";
		points = new JSpinner(new SpinnerNumberModel(0,0,maximum,1));
		points.addChangeListener(this);
		maxLabel = new JLabel("Max Bonus: +"+d.format(maximum/100.0 * multiplier));
		spinnerLabel = new JLabel("0/"+maximum);
		JLabel iconLabel = new JLabel(icon);
		
		details = new JPanel(new BorderLayout());
		JLabel name = new JLabel("<html><u>"+title+"</u>");
		Box detailBox = Box.createVerticalBox();
		detailBox.add(new JLabel(desc1));
		detailBox.add(new JLabel(desc2));
		detailBox.add(new JLabel("<html></html"));
		detailBox.add(percent);
		detailBox.add(new JLabel("Each Level Bonus: +"+multiplier+"%"));
		detailBox.add(maxLabel);
		details.add(name,BorderLayout.NORTH);
		details.add(detailBox,BorderLayout.CENTER);
		
		add(iconLabel,BorderLayout.CENTER);
		add(spinnerLabel = new JLabel("  0/"+maximum),BorderLayout.EAST);
	//	setToolTipText(title+": "+desc1+" "+desc2);
		addMouseListener(this);
		setOpaque(true);
		setEnabled(false);
	}
	public void addActionListener(ActionListener al) {
		dep = al;
	}
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() instanceof JSpinner) {
			percent.setText("Current Bonus: +"+d.format(((Integer)points.getValue())/100.0 * multiplier));
			if(dep != null)
				dep.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,
										"Change points"));
		}
	}
	public void clear() { setPoints(0); }
	public void setEnabled(boolean b) {
		points.setEnabled(b);
		spinnerLabel.setEnabled(b);
		percent.setEnabled(b);
		if(!b) setPoints(0);
	}
	public boolean isEnabled() { return points.isEnabled(); }
	public int getPoints() { return (Integer)points.getValue(); }
	public void setPoints(int p) {
		points.setValue(p);
		String text = " "+p+"/"+maximum;
		if(spinnerLabel != null) {
			if(p < 10) text = " "+text;
			spinnerLabel.setText(text);
		}
	}
	public String getDescription() { return desc1; }
	public String getTitle() { return title; }
	public Integer getNextValue() {
		return (Integer)points.getNextValue();
	}
	public Integer getPreviousValue() {
		return (Integer)points.getPreviousValue();
	}
	public boolean equals(Object box) {
		if(!(box instanceof SkillBox)) return false;
		return title.equals(((SkillBox)box).getTitle());
	}
	public boolean built() { return built; }
	public JPanel getDetails() { return details; }
	public Color getColor() { return bg; }
	public Color getBack() { return back; }
	public void setBack(Color col) { back = col; }
	public void mouseEntered(MouseEvent e) {
//		if(!isEnabled()) return;
		if(!built) return;
		if(back == null) back = getBackground();
		setBackground(bg);
	}
	public void mouseExited(MouseEvent e) {
//		if(!isEnabled()) return;
		if(!built) return;
		setBackground(back);
	}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}