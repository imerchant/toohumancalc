import java.util.*;

class ClassExport implements java.io.Serializable, Comparable<ClassExport> {
	private static final long serialVersionUID = 50078927L;
	public String classString,alignString,name;
	public Vector<Integer> classPoints,alignPoints;
	public ClassExport() {
		classString = "";
		alignString = "";
		classPoints = new Vector<Integer>();
		alignPoints = new Vector<Integer>();
	}
	public String toString() {
		String ret = classString + " " + alignString + " " + name + "[";
		ret += classPoints.size() + " " + alignPoints.size() + "]";
		return ret;
	}
	public boolean equals(Object o) {
		if(!(o instanceof ClassExport)) return false;
		ClassExport c = (ClassExport)o;
		return c.classString.equals(classString) && c.alignString.equals(alignString);
	}
	public int compareTo(ClassExport ce) {
		if(ce == null) return -999;
		return new String(classString+alignString).compareTo(new String(ce.classString+ce.alignString));
	}
}