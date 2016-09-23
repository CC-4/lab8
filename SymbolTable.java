import java.util.Stack;
import java.util.Hashtable;

class SymbolTable {
    
    protected Stack<Hashtable<String, Object>> tbl;
    
    public SymbolTable() {
        this.tbl = new Stack<Hashtable<String,Object>>();
    }
    
    public void enterScope() {
        this.tbl.push(new Hashtable<String, Object>());
    }
    
    public void exitScope() {
        if(this.tbl.empty()){
            Utils.fatalError("existScope: can't remove scope from an empty symbol table.");
        }
        this.tbl.pop();
    }
    
    public void addId(String id, Object info) {
        if(this.tbl.empty()) {
            Utils.fatalError("addId: can't add a symbol without a scope.");
        }
        this.tbl.peek().put(id, info);
    }
    
    public Object lookup(String sym) {
        if(this.tbl.empty()) {
            Utils.fatalError("lookup: no scope in symbol table.");
        }
        for(int i= this.tbl.size() - 1; i >= 0; i--) {
            Object info = this.tbl.elementAt(i).get(sym);
            if(info != null)
                return info;
        }
        return null;
    }
    
    public Object probe(String sym) {
        if(this.tbl.isEmpty()) {
            Utils.fatalError("lookup: no scope in symbol table.");
        }
        return this.tbl.peek().get(sym);
    }
    
    @Override
    public String toString() {
        String res = "";
        for(int i = this.tbl.size() - 1, j = 0; i >= 0; i--, j++) {
            res += "Scope " + j + ": " + this.tbl.elementAt(i) + "\n";
        }
        return res;
    }
    
}
    
    
    
