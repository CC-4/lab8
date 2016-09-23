import java.util.Hashtable;

public class Environments extends SymbolTable {

    private Hashtable<String, Function> functions;
    
    public Environments() {
        super();
        this.functions = new Hashtable<String, Function>();
    }
    
    public void addFunction(String name, Function f) {
        this.functions.put(name, f);
    }
    
    public Function lookupFunction(String name) {
        return this.functions.get(name);
    }
    
}
