public abstract class Node {

    private int lineNumber;
    
    public Node(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
}
