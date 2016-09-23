import java.util.Vector;
import java.util.Enumeration;

public abstract class ListNode extends Node {

    private Vector<Node> list;
    
    public ListNode(int lineNumber) {
        super(lineNumber);
        this.list = new Vector<Node>();
    }
    
    public void addElement(Node node) {
        this.list.addElement(node);
    }
    
    public int size() {
        return this.list.size();
    }
    
    public Node getNth(int n) {
        return this.list.elementAt(n);
    }

    public Enumeration<Node> getElements() {
        return this.list.elements();
    }
    
}
