import java.io.PrintStream;
import java.util.Hashtable;

public class CgenMachine extends SymbolTable {

    private PrintStream p;
    private String currentFunction;
    private Hashtable<String, Integer> locals;
    private Hashtable<String, Integer> localsCount;

    public CgenMachine(PrintStream p, Functions functions) {
        this.p = p;
        this.locals = new Hashtable<String, Integer>();
        this.localsCount = new Hashtable<String, Integer>();
        for(int i=0; i < functions.size(); i++) {
            Function f = (Function)functions.getNth(i);
            this.locals.put(f.name, f.getNumLocals());
            this.localsCount.put(f.name, 0);
            if(i == 0) {
                this.currentFunction = f.name;
            }
        }
    }

    public void codeGlobalData() {
        CgenSupport.codeHeader(this.p);
        CgenSupport.codeConstants(this.p);
    }

    public void setCurrentFunction(String name) {
        this.currentFunction = name;
    }

    public PrintStream getPrintStream() {
        return this.p;
    }

    public int addLocal() {
        int a = this.localsCount.get(this.currentFunction);
        int b = this.locals.get(this.currentFunction);
        if(a == b) {
            Utils.fatalError("addLocal: no more locals available.");
        }
        this.localsCount.put(this.currentFunction, a + 1);
        return a + 1;
    }

    @Override
    public void exitScope() {
        if(this.tbl.empty()){
            Utils.fatalError("existScope: can't remove scope from an empty symbol table.");
        }
        this.localsCount.put(this.currentFunction, this.localsCount.get(this.currentFunction) - this.tbl.peek().size());
        this.tbl.pop();
    }

}
