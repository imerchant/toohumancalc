import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.datatransfer.*;

class NotesArea extends JTextArea implements MouseListener,Scrollable {
	private static final long serialVersionUID = 90348905L;
	private Icons icons;
	public NotesArea() {
		super();
		initialize();
	}
	public NotesArea(Document doc) {
		super(doc);
		initialize();
	}
	public NotesArea(String text, int rows, int cols) {
		super(text,rows,cols);
		initialize();
	}
	public NotesArea(int rows, int cols) {
		super(rows,cols);
		initialize();
	}
	public NotesArea(String text) {
		super(text);
		initialize();
	}
	protected void initialize() {
		addMouseListener(this);
	}
	public void setIcons(Icons i) {
		icons = i;
	}
	public void mousePressed(MouseEvent e) {
		handleEvent(e);
	}
	public void mouseReleased(MouseEvent e) {
		handleEvent(e);
	}
	public void mouseClicked(MouseEvent e) {
		handleEvent(e);
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	protected void handleEvent(MouseEvent e) {
		requestFocusInWindow();
		if(e.isPopupTrigger()) {
			JPopupMenu popup = new JPopupMenu();
			popup.add(new Cut(this,icons.get("cut")));
			popup.add(new Copy(this,icons.get("copy")));
			popup.add(new Paste(this,icons.get("paste")));
			popup.add(new Delete(this,icons.get("delete")));
			popup.addSeparator();
			popup.add(new SelectAll(this,icons.get("select all")));
			popup.show(this,e.getX(),e.getY());
		}
	}
}
class NameField extends JTextField implements MouseListener,FocusListener {
	private static final long serialVersionUID = -9283434L;
	private Icons icons;
	public NameField() {
		super();
		initialize();
	}
	public NameField(String text, Icons i) {
		super(text);
		initialize();
		setIcons(i);
	}
	public NameField(String text, int cols) {
		super(text,cols);
		initialize();
	}
	public NameField(int cols) {
		super(cols);
		initialize();
	}
	public NameField(Document doc, String text, int cols) {
		super(doc,text,cols);
		initialize();
	}
	protected void initialize() {
		addMouseListener(this);
	}
	public void setIcons(Icons i) {
		icons = i;
	}
	public void mousePressed(MouseEvent e) {
		handleEvent(e);
	}
	public void mouseReleased(MouseEvent e) {
		handleEvent(e);
	}
	public void mouseClicked(MouseEvent e) {
		handleEvent(e);
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	protected void handleEvent(MouseEvent e) {
		requestFocusInWindow();
		if(e.isPopupTrigger()) {
			JPopupMenu popup = new JPopupMenu();
			popup.add(new Cut(this,icons.get("cut")));
			popup.add(new Copy(this,icons.get("copy")));
			popup.add(new Paste(this,icons.get("paste")));
			popup.add(new Delete(this,icons.get("delete")));
			popup.addSeparator();
			popup.add(new SelectAll(this,icons.get("select all")));
			popup.show(this,e.getX(),e.getY());
		}
	}
	public void focusGained(FocusEvent e) {
		selectAll();
	}
	public void focusLost(FocusEvent e) {}
	public void setSelectAllOnFocus(boolean on) {
		if(on) addFocusListener(this);
		else removeFocusListener(this);
	}
}
class Cut extends AbstractAction {
	private static final long serialVersionUID = 90348915L;
	private JTextComponent comp;
	public Cut(JTextComponent c, ImageIcon i) {
		super("Cut",i);
		comp = c;
		putValue("AcceleratorKey",KeyStroke.getKeyStroke("ctrl X"));
		putValue("MnemonicKey",KeyEvent.VK_T);
	}
	public void actionPerformed(ActionEvent e) {
		comp.cut();
	}
	public boolean isEnabled() {
		return comp.isEnabled() && comp.isEditable() && comp.getSelectedText() != null;
	}
}
class Copy extends AbstractAction {
	private static final long serialVersionUID = 90348925L;
	private JTextComponent comp;
	public Copy(JTextComponent c, ImageIcon i) {
		super("Copy",i);
		comp = c;
		putValue("AcceleratorKey",KeyStroke.getKeyStroke("ctrl C"));
		putValue("MnemonicKey",KeyEvent.VK_C);
	}
	public void actionPerformed(ActionEvent e) {
		comp.copy();
	}
	public boolean isEnabled() {
		return comp.isEnabled() && comp.getSelectedText() != null;
	}
}
class Paste extends AbstractAction {
	private static final long serialVersionUID = 90348935L;
	private JTextComponent comp;
	public Paste(JTextComponent c, ImageIcon i) {
		super("Paste",i);
		comp = c;
		putValue("AcceleratorKey",KeyStroke.getKeyStroke("ctrl V"));
		putValue("MnemonicKey",KeyEvent.VK_P);
	}
	public void actionPerformed(ActionEvent e) {
		comp.paste();
	}
	public boolean isEnabled() {
		if(comp.isEditable() && comp.isEnabled()) {
			Transferable contents = comp.getToolkit().getSystemClipboard().getContents(null);
			return contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		} else return false;
	}
}
class Delete extends AbstractAction {
	private static final long serialVersionUID = 90348945L;
	private JTextComponent comp;
	public Delete(JTextComponent c, ImageIcon i) {
		super("Delete",i);
		comp = c;
		putValue("AcceleratorKey",KeyStroke.getKeyStroke("DELETE"));
		putValue("MnemonicKey",KeyEvent.VK_D);
	}
	public void actionPerformed(ActionEvent e) {
		comp.replaceSelection(null);
	}
	public boolean isEnabled() {
		return comp.isEnabled() && comp.isEditable() && comp.getSelectedText() != null;
	}
}
class SelectAll extends AbstractAction {
	private static final long serialVersionUID = 90348955L;
	private JTextComponent comp;
	public SelectAll(JTextComponent c, ImageIcon i) {
		super("Select All",i);
		comp = c;
		putValue("AcceleratorKey",KeyStroke.getKeyStroke("ctrl A"));
		putValue("MnemonicKey",KeyEvent.VK_A);
	}
	public void actionPerformed(ActionEvent e) {
		comp.selectAll();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { comp.selectAll(); }
		});
	}
	public boolean isEnabled() {
		return comp.isEnabled() && comp.getText().length() > 0;
	}
}