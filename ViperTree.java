import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

class Statements extends ListNode {

    public Statements(int lineNumber) {
        super(lineNumber);
    }

    public Statements appendElement(Statement statement) {
        this.addElement(statement);
        return this;
    }

    public void print(PrintStream p, int n) {
        for(Enumeration<Node> e = this.getElements(); e.hasMoreElements(); ) {
            Statement s = (Statement)e.nextElement();
            s.print(p, n);
        }
    }

}

abstract class Statement extends Node {

    public Statement(int lineNumber) {
        super(lineNumber);
    }

    public abstract void semant(Environments OM);

    public abstract void cgen(CgenMachine S);

    public abstract void print(PrintStream p, int n);

}

class Actuals extends ListNode {

    public Actuals(int lineNumber) {
        super(lineNumber);
    }

    public Actuals appendElement(Expression e) {
        this.addElement(e);
        return this;
    }

    public void print(PrintStream p, int n) {
        for(Enumeration<Node> e = this.getElements(); e.hasMoreElements();) {
            Expression f = (Expression)e.nextElement();
            f.print(p, n);
        }
    }

}

abstract class Expression extends Statement {

    protected Type type;

    public Expression(int lineNumber) {
        super(lineNumber);
        this.type = new Type(Type.NOTYPE);
    }

    public void setType(Type t) {
        this.type = t;
    }

    public Type getType() {
       return this.type;
    }

}

class Functions extends ListNode {

    public Functions(int lineNumber) {
        super(lineNumber);
    }

    public Functions appendElement(ViperFunction feature) {
        this.addElement(feature);
        return this;
    }

    public void print(PrintStream p, int n) {
        for(Enumeration<Node> e = this.getElements(); e.hasMoreElements(); ) {
            ViperFunction f = (ViperFunction)e.nextElement();
            f.print(p, n);
        }
    }

}

abstract class ViperFunction extends Node {

    public ViperFunction(int lineNumber) {
        super(lineNumber);
    }

    public abstract void semant(Environments OM);

    public abstract void cgen(CgenMachine S);

    public abstract void print(PrintStream p, int n);

}

class Formals extends ListNode {

    public Formals(int lineNumber) {
        super(lineNumber);
    }

    public Formals appendElement(Formal formal) {
        this.addElement(formal);
        return this;
    }

    public void print(PrintStream p, int n) {
        for(Enumeration<Node> e = this.getElements(); e.hasMoreElements();) {
            Formal f = (Formal)e.nextElement();
            f.print(p, n);
        }
    }

}

class Formal extends Node {

    protected String name;
    protected Type type;

    public Formal(int lineNumber, String name, Type type) {
       super(lineNumber);
       this.name = name;
       this.type = type;
    }

    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Formal");
        p.println(Utils.tabs(n + 2) + this.name);
        p.println(Utils.tabs(n + 2) + this.type);
    }

}

class Program extends Node {

    protected String filename;
    protected Functions functions;

    public Program(int lineNumber, Functions functions) {
        super(lineNumber);
        this.functions = functions;
        this.filename = "file";
    }

    public void setFileName(String name) {
        this.filename = name;
    }

    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Program (" + this.filename + ")");
        p.println(Utils.tabs(n + 2) + "(");
        this.functions.print(p, n + 2);
        p.println(Utils.tabs(n + 2) + ")");
    }

  
    public void semant(){
        Environments env = new Environments();
        env.enterScope();
        for(int count = 0; count < functions.size();count++){
            Function f = (Function)functions.getNth(count);
            if(env.lookupFunction(f.name) != null){
                SemantErrors.functionMultiplyDefined(f.getLineNumber(),f.name);
            }else{
                env.addFunction(f.name,f);
            }
        }

        if(env.lookupFunction("main") == null){
            SemantErrors.noMainFunction();
        }

        for(int count = 0; count < functions.size();count++){
            Function f = (Function)functions.getNth(count);
            f.semant(env);
        }
    }

    public void cgen(PrintStream p) {
        // TODO: Hacer cgen de Program
    }

}

class Function extends ViperFunction {

    protected String name;
    protected Formals formals;
    protected Type rtype;
    protected Statements statements;
    protected Expression return_;

    public Function(int lineNumber, String n, Formals f, Type t, Statements s, Expression r) {
        super(lineNumber);
        this.name = n;
        this.formals = f;
        this.rtype = t;
        this.statements = s;
        this.return_ = r;
    }

    public int getNumLocals() {
        Vector<Integer> statementsc = new Vector<Integer>();
        int count = 0;
        for(int i=0; i < this.statements.size(); i++) {
            Node n = this.statements.getNth(i);
            if(n instanceof Declaration) count++;
        }
        for(int i=0; i < this.statements.size(); i++) {
            Node n = this.statements.getNth(i);
            if(n instanceof If)
                statementsc.addElement(((If) n).getNumLocals());
            else if(n instanceof While)
                statementsc.addElement(((While) n).getNumLocals());
        }
        return count + Utils.max(statementsc) + this.formals.size();
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Function");
        p.println(Utils.tabs(n + 2) + this.name);
        this.formals.print(p, n + 2);
        p.println(Utils.tabs(n + 2) + "Return Type: " + this.rtype);
        this.statements.print(p, n + 2);
        this.return_.print(p, n + 2);
    }

    @Override
    public void semant(Environments OM) {
        if(this.name.equals("main") && this.formals.size() > 0){
            SemantErrors.mainNoArguments(this.formals.getLineNumber());
        }else{
            OM.enterScope();
            for(int count = 0; count < formals.size();count++){
                Formal f = (Formal)formals.getNth(count);
                if(OM.lookup(f.name) != null){
                    SemantErrors.formalMultiplyDefined(f.getLineNumber(),f.name);
                }else{
                    OM.addId(f.name,f.type);
                }
            }

            for(int count = 0; count < statements.size();count++){
                Statement s = (Statement)statements.getNth(count);
                s.semant(OM);
            }
            return_.semant(OM);
            OM.exitScope();
            if(rtype.equals(Utils.VOID_TYPE)){
                if(! return_.getType().equals(Utils.NO_TYPE)){
                    SemantErrors.noReturnExpected(this.return_.getLineNumber());
                }
            }else{
                if(return_.getType().equals(Utils.NO_TYPE)){
                    SemantErrors.noReturn(return_.getLineNumber(),this.name,this.rtype);
                }else if(! rtype.equals(return_.getType())){
                    SemantErrors.diffReturnType(return_.getLineNumber(), this.name, this.rtype,this.return_.getType());
                }
            }
        }
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Function
    }

}

class Return extends Expression {

    protected Expression e;

    public Return(int lineNumber, Expression e) {
        super(lineNumber);
        this.e = e;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Return");
        this.e.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        e.semant(OM);
        this.setType(e.getType());
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Return
    }

}

class NoReturn extends Expression {

    public NoReturn(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "NoReturn");
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        this.setType(Utils.NO_TYPE);
    }

    @Override
    public void cgen(CgenMachine S) {
        // NADA
    }

}

class NoExpression extends Expression {

    public NoExpression(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public void print(PrintStream p, int n) {
       p.println(Utils.tabs(n) + "#" + this.getLineNumber());
       p.println(Utils.tabs(n) + "NoExpression");
       p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        this.setType(Utils.NO_TYPE);
    }

    @Override
    public void cgen(CgenMachine S) {
        // NADA
    }

}

class Assign extends Statement {

    protected String name;
    protected Expression e;

    public Assign(int lineNumber, String name, Expression e){
        super(lineNumber);
        this.name = name;
        this.e = e;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Assign");
        p.println(Utils.tabs(n + 2) + this.name);
        this.e.print(p, n + 2);
    }

    @Override
    public void semant(Environments OM) {
        //TODO: Hacer semant de Assign
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Assign
    }

}

class Declaration extends Statement {

    protected Type type;
    protected String name;
    protected Expression init;

    public Declaration(int lineNumber, Type t, String n, Expression i) {
        super(lineNumber);
        this.type = t;
        this.name = n;
        this.init = i;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Declaration");
        p.println(Utils.tabs(n + 2) + this.name);
        p.println(Utils.tabs(n + 2) + this.type);
        this.init.print(p, n + 2);
    }

    @Override
    public void semant(Environments OM) {
        if(OM.probe(name) != null){
            SemantErrors.varMultiplyDefined(this.getLineNumber(),this.name);
        }else{
            init.semant(OM);
            OM.addId(name,type);
            if(! init.getType().equals(this.type)){
                SemantErrors.diffInitType(init.getLineNumber(), this.name, type, init.getType());
            }
        }
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Declaration
    }

}

class Print extends Statement {

    protected Expression e;

    public Print(int lineNumber, Expression e) {
        super(lineNumber);
        this.e = e;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Print");
        this.e.print(p, n + 2);
    }

    @Override
    public void semant(Environments OM) {
       this.e.semant(OM);
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Print
    }

}

class If extends Statement {

    protected Expression pred;
    protected Statements thenpart;
    protected Statements elsepart;

    public If(int lineNumber, Expression p, Statements t, Statements e) {
        super(lineNumber);
        this.pred = p;
        this.thenpart = t;
        this.elsepart = e;

    }

    public int getNumLocals() {
        Vector<Integer> thenc = new Vector<Integer>();
        Vector<Integer> elsec = new Vector<Integer>();
        int countt = 0;
        for(int i=0; i < this.thenpart.size(); i++) {
            Node n = this.thenpart.getNth(i);
            if(n instanceof Declaration) countt++;
        }
        for(int i=0; i < this.thenpart.size(); i++) {
            Node n = this.thenpart.getNth(i);
            if(n instanceof If)
                thenc.addElement(((If) n).getNumLocals());
            else if(n instanceof While)
                thenc.addElement(((While) n).getNumLocals());
        }
        int counte = 0;
        for(int i=0; i < this.elsepart.size(); i++) {
            Node n = this.elsepart.getNth(i);
            if(n instanceof Declaration) counte++;
        }
        for(int i=0; i < this.elsepart.size(); i++) {
            Node n = this.elsepart.getNth(i);
            if(n instanceof If)
                elsec.addElement(((If) n).getNumLocals());
            else if(n instanceof While)
                elsec.addElement(((While) n).getNumLocals());
        }
        return Math.max(countt + Utils.max(thenc), counte + Utils.max(elsec));
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "If");
        this.pred.print(p, n + 2);
        this.thenpart.print(p, n + 2);
        this.elsepart.print(p, n + 2);
    }

    @Override
    public void semant(Environments OM) {
        //TODO: Hacer semant de If
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de If
    }

}

class While extends Statement {

    protected Expression pred;
    protected Statements body;

    public While(int lineNumber, Expression p, Statements b) {
        super(lineNumber);
        this.pred = p;
        this.body = b;
    }

    public int getNumLocals() {
        Vector<Integer> bodyc = new Vector<Integer>();
        int count = 0;
        for(int i=0; i < this.body.size(); i++) {
            Node n = this.body.getNth(i);
            if(n instanceof Declaration) count++;
        }
        bodyc.addElement(count);
        for(int i=0; i < this.body.size(); i++) {
            Node n = this.body.getNth(i);
            if(n instanceof If)
                bodyc.addElement(((If) n).getNumLocals());
            else if(n instanceof While)
                bodyc.addElement(((While) n).getNumLocals());
        }
        return Utils.max(bodyc);
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "While");
        this.pred.print(p, n + 2);
        this.body.print(p, n + 2);
    }

    @Override
    public void semant(Environments OM) {
        OM.enterScope();
        this.pred.semant(OM);
        for(int count = 0; count < this.body.size();count++){
            Statement s = (Statement)this.body.getNth(count);
            s.semant(OM);
        }
        OM.exitScope();
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de While
    }

}

class IntConst extends Expression {

    protected String number;

    public IntConst(int lineNumber, String number) {
        super(lineNumber);
        this.number = number;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "IntConst");
        p.println(Utils.tabs(n + 2) + this.number);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        this.setType(Utils.INT_TYPE);
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de IntConst
    }

}

class BoolConst extends Expression {

    protected boolean b;

    public BoolConst(int lineNumber, boolean b) {
        super(lineNumber);
        this.b = b;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "BoolConst");
        p.println(Utils.tabs(n + 2) + this.b);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        this.setType(Utils.BOOL_TYPE);
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de BoolConst
    }

}

class StrConst extends Expression {

    protected String s;

    public StrConst(int lineNumber, String s) {
        super(lineNumber);
        this.s = s;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "StrConst");
        p.println(Utils.tabs(n + 2) + this.s);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de StrConst
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de StrConst
    }

}

class Id extends Expression {

    protected String name;

    public Id(int lineNumber, String name) {
        super(lineNumber);
        this.name = name;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Id");
        p.println(Utils.tabs(n + 2) + this.name);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        Type t = (Type)OM.probe(name);
        if(t == null){
            t = (Type)OM.lookup(name);
            if(t == null){
                this.setType(Utils.NO_TYPE);
                SemantErrors.undeclaredeVar(this.getLineNumber(),this.name);
            }else{
                this.setType(t);
            }
        }else{
            this.setType(t);
        }
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Id
    }

}

class Plus extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Plus(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Plus");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Plus
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Plus
    }

}

class Minus extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Minus(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Minus");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Minus
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Minus
    }

}

class Mult extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Mult(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Mult");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Mult
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Mult
    }

}

class Div extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Div(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Div");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Div
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Div
    }

}

class Eq extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Eq(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Eq");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Eq
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Eq
    }

}

class Neq extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Neq(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Neq");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Neq
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Neq
    }

}

class Leq extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Leq(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Leq");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Leq
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Leq
    }

}

class Geq extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Geq(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Geq");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Geq
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Geq
    }

}

class Lt extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Lt(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Lt");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Lt
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Lt
    }

}

class Gt extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Gt(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Gt");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Gt
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Gt
    }

}

class And extends Expression {

    protected Expression e1;
    protected Expression e2;

    public And(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "And");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de And
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de And
    }

}

class Or extends Expression {

    protected Expression e1;
    protected Expression e2;

    public Or(int lineNumber, Expression e1, Expression e2) {
        super(lineNumber);
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Or");
        this.e1.print(p, n + 2);
        this.e2.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Or
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Or
    }

}

class Not extends Expression {

    protected Expression e;

    public Not(int lineNumber, Expression e) {
        super(lineNumber);
        this.e = e;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Not");
        this.e.print(p, n + 2);
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer semant de Not
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Not
    }

}

class Call extends Expression {

    protected String name;
    protected Actuals actuals;

    public Call(int lineNumber, String name, Actuals actuals) {
        super(lineNumber);
        this.name = name;
        this.actuals = actuals;
    }

    @Override
    public void print(PrintStream p, int n) {
        p.println(Utils.tabs(n) + "#" + this.getLineNumber());
        p.println(Utils.tabs(n) + "Call");
        p.println(Utils.tabs(n + 2) + this.name);
        p.println(Utils.tabs(n + 2) + "(");
        this.actuals.print(p, n + 2);
        p.println(Utils.tabs(n + 2) + ")");
        p.println(Utils.tabs(n) + ": " + this.getType());
    }

    @Override
    public void semant(Environments OM) {
        // TODO: Hacer analisis semantico de Call
    }

    @Override
    public void cgen(CgenMachine S) {
        // TODO: Hacer cgen de Call
    }

}
