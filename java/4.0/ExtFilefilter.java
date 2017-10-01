import java.io.*;

class ExtFilefilter extends javax.swing.filechooser.FileFilter {
	private String extension;
	private String description;
	public ExtFilefilter(String ext, String desc) {
		extension = ext;
		if(!extension.contains("."))
			extension = "."+extension;
		description = desc;
	}
	public boolean accept(File f) {
		return f != null && (f.isDirectory() || f.getName().endsWith(extension));
	}
	public boolean acceptFile(File f) {
		return f!= null && f.getName().endsWith(extension);
	}
	public String getDescription() {
		return description;
	//	return "Too Human Character Plotter files (*.thclass)";
	}
}