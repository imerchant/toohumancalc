import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

class JOptionPaneTest extends JFrame implements ActionListener {
	private Icons icons;
	public static void main(String args[]) { new JOptionPaneTest(); }
	public JOptionPaneTest() {
		super("testing!");
		try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e){}
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		JButton go = new JButton("Go!");
		go.addActionListener(this);
		go.setFocusPainted(false);
		pane.add(go);
		icons = new Icons();
		icons.load();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		String comm = e.getActionCommand();
		if(comm.equals("Go!")) {
			String[] options = new String[] {"Include details box","Just the trees","Cancel"};
			CustomDialog cd = new CustomDialog(this,"Export the whole view, or just the trees?","All, some, or nothing?",
				icons.get("dialog warning 32"),options,options[1]);
			int result = cd.showDialog();
			System.out.println(result);
		}
	}
}
class CustomDialog extends JDialog implements ActionListener {
	private Object selected;
	private JButton[] options;
	private int initVal;
	public CustomDialog(Frame owner, String msg, String title, ImageIcon i, String[] opts, String initialValue) {
		super(owner,title,true);
		JPanel panel = new JPanel(new BorderLayout(10,10));
		JLabel message = new JLabel(msg,SwingUtilities.CENTER);
		JLabel icon = new JLabel(i);
		JPanel buttons = new JPanel(new GridLayout(1,3,5,5));
		options = new JButton[opts.length];
		for(int k = 0; k < opts.length; k++) {
			JButton button = new JButton(opts[k]);
			if(initialValue.equals(opts[k])) initVal = k;
			button.addActionListener(this);
			button.setFocusPainted(false);
			options[k] = button;
			buttons.add(button);
		}
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		panel.add(message,BorderLayout.CENTER);
		panel.add(icon,BorderLayout.WEST);
		panel.add(buttons,BorderLayout.SOUTH);
		pane.add(panel);
		pack();
		setLocationRelativeTo(owner);
		setResizable(false);
	}
	public int showDialog() {
		setVisible(true);
		dispose();
		if(selected == null)
			return JOptionPane.CLOSED_OPTION;
		for(int k = 0; k < options.length; k++) {
			if(options[k].equals(selected))
				return k;
		}
		return JOptionPane.CLOSED_OPTION;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			selected = e.getSource();
			setVisible(false);
		}
	}
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		options[initVal].requestFocusInWindow();
	}
}