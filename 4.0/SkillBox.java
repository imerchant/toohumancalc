import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.DecimalFormat;

class SkillBox extends JPanel implements ChangeListener, java.io.Serializable, MouseListener, ActionListener {
	public static final long serialVersionUID = -3456235547L;
	private ImageIcon icon;
	private String desc1,desc2,iconSrc,title,levelBonus;
	private double multiplier;
	private int maximum;
	private ActionListener dep;
	private boolean built = false, iconAlwaysEnabled = false;
	private DecimalFormat d = new DecimalFormat("#0.0%");
	private Color bg,back;
	private JLabel percent = new JLabel("Current Bonus: +"+d.format(0.0*0.0)), maxLabel,
					spinnerLabel = new JLabel("0"), iconLabel = new JLabel();
	private JComponent details;
	private JSpinner points = new JSpinner(new SpinnerNumberModel(0,0,10,1));
	private JPopupMenu popup;
	public SkillBox() {
		title = "";
		setEnabled(false);
	}
	public SkillBox(ImageIcon i, String t, String d1, String d2, double multi, int max, Color col) {
		this(i,t,d1,d2,null,multi,max,col);
	}
	public SkillBox(ImageIcon i, String t, String d1, String d2, String lB, double multi, int max, Color col) {
		super(new BorderLayout());
		title = t;
		desc1 = d1;
		desc2 = d2;
		levelBonus = lB;
		multiplier = multi;
		maximum = max;
		bg = col;
		build(i);
		built = true;
	}
	private void build(ImageIcon i) {
		icon = i;
		if(desc2 == null)
			desc2 = "<html></html>";
		points = new JSpinner(new SpinnerNumberModel(0,0,maximum,1));
		points.addChangeListener(this);
		maxLabel = new JLabel("Max Bonus: +"+d.format(maximum/100.0 * multiplier));
		spinnerLabel = new JLabel("  0/" + ((maximum < 10) ? " " : "") + maximum,SwingConstants.RIGHT);
		iconLabel = new JLabel(icon);
		
		details = Box.createVerticalBox();
	//	Box detailBox = Box.createVerticalBox();
		JLabel name = new JLabel("<html><u>"+title+"</u>");
		details.add(name);
		details.add(new JLabel(desc1));
		details.add(new JLabel(desc2));
		details.add(new JLabel("<html></html"));
		if(levelBonus != null)
			details.add(new JLabel("Level Bonus: "+levelBonus));
		details.add(percent);
		details.add(new JLabel("Each Level Bonus: +"+multiplier+"%"));
		details.add(maxLabel);
	//	details.add(Box.createHorizontalGlue());
	//	details.add(name,BorderLayout.NORTH);
	//	details.add(detailBox);
		
		popup = new JPopupMenu("Change points");
		for(int k = 0; k <= maximum; k++)
			popup.add(Integer.toString(k)).addActionListener(this);
		
		add(iconLabel,BorderLayout.CENTER);
		add(spinnerLabel = new JLabel("  0/"+ ((maximum < 10) ? " " : "") + maximum),BorderLayout.EAST);
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
		if(!iconAlwaysEnabled) iconLabel.setEnabled(b);
		if(!b) setPoints(0);
	}
	public boolean isEnabled() { return points.isEnabled(); }
	public int getPoints() { return (Integer)points.getValue(); }
	public void setPoints(int p) {
		points.setValue(p);
		String text = " "+p+"/"+((maximum < 10) ? " " : "")+maximum;
		if(spinnerLabel != null) {
			if(p < 10) text = "  "+text;
			spinnerLabel.setText(text);
		}
	}
	public String getLevelBonus() { return levelBonus; }
	public String getDescription() { return desc1; }
	public String getDescriptionSecondLine() { return desc2; }
	public String getFullDescription() { return desc1+"\n"+desc2; }
	public String getTitle() { return title; }
	public String getSpinnerLabelText() { return spinnerLabel.getText(); }
	public Integer getNextValue() {
		return (Integer)points.getNextValue();
	}
	public Integer getPreviousValue() {
		return (Integer)points.getPreviousValue();
	}
	public Integer getValue() {
		return (Integer)points.getValue();
	}
	public ImageIcon getIcon() { return icon; }
	public JPopupMenu getPopup() { return popup; }
	public int getMax() { return maximum; }
	public boolean equals(Object box) {
		if(!(box instanceof SkillBox) || box == null) return false;
		return title.equals(((SkillBox)box).getTitle());
	}
	public boolean built() { return built; }
	public JComponent getDetails() { return details; }
	public Color getColor() { return bg; }
	public Color getBack() { return back; }
	public void setBack(Color col) { back = col; }
	public void setIconAlwaysEnabled(boolean b) {
		iconAlwaysEnabled = b;
	//	if(b) iconLabel.setEnabled(true);
		iconLabel.setEnabled(b || isEnabled());
	}
	public boolean isIconAlwaysEnabled() {
		return iconAlwaysEnabled;
	}
	public void mouseEntered(MouseEvent e) {
//		if(!isEnabled()) return;
		if(!built) return;
		if(back == null) back = getBackground();
		setBackground(bg);
		if(!isEnabled() && !iconAlwaysEnabled) iconLabel.setEnabled(true);
	}
	public void mouseExited(MouseEvent e) {
//		if(!isEnabled()) return;
		if(!built) return;
		setBackground(back);
		if(!isEnabled() && !iconAlwaysEnabled) iconLabel.setEnabled(false);
	}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JMenuItem) {
			setPoints(Integer.parseInt(e.getActionCommand()));
		}
	}
}