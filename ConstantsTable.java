import java.io.PrintStream;
import java.util.Vector;

public class ConstantsTable {

    public static ConstantsTable table = new ConstantsTable();
    
    private Vector<String> strings;
    private Vector<String> ids;
    private Vector<String> ints;

    public ConstantsTable() {
        this.strings = new Vector<String>();
        this.ids = new Vector<String>();
        this.ints = new Vector<String>();
    }

    public String addString(String str) {
        for(int i=0; i < this.strings.size(); i++) {
            String s = this.strings.elementAt(i);
            if(s.equals(str))
                return s;
        }
        this.strings.addElement(str);
        return str;
    }
    
    public String addId(String str) {
        for(int i=0; i < this.ids.size(); i++) {
            String s = this.ids.elementAt(i);
            if(s.equals(str))
                return s;
        }
        this.ids.addElement(str);
        return str;
    }
    
    public String addInt(String str) {
        for(int i=0; i < this.ints.size(); i++) {
            String s = this.ints.elementAt(i);
            if(s.equals(str))
                return s;
        }
        this.ints.addElement(str);
        return str;
    }
    
    public int getIntIndex(String str) {
        for(int i=0; i < this.ints.size(); i++) {
            String s = this.ints.elementAt(i);
            if(s.equals(str))
                return i;
        }
        return -1;
    }
    
    public int getStringIndex(String str) {
        for(int i=0; i < this.strings.size(); i++) {
            String s = this.strings.elementAt(i);
            if(s.equals(str))
                return i;
        }
        return -1;
    }
    
    public void codeStringConstants(PrintStream p) {
        for(int i=0; i < this.strings.size(); i++) {
            String s = this.strings.elementAt(i);
            CgenSupport.emitLabelDef(CgenSupport.STRCONST_PREFIX + i, p);
            p.println(CgenSupport.ASCIIZ + "\"" + s + "\"");
        }
    }
    
    public void codeIntConstants(PrintStream p) {
        ConstantsTable.table.addInt("0");
        for(int i=0; i < this.ints.size(); i++) {
            String s = this.ints.elementAt(i);
            CgenSupport.emitLabelDef(CgenSupport.INTCONST_PREFIX + i, p);
            p.println(CgenSupport.WORD + s);
        }
    }
    
}
