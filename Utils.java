import java.util.Vector;
import java_cup.runtime.Symbol;

public final class Utils {
    
    // Tipos
    public static final Type NO_TYPE = new Type(Type.NOTYPE);
    public static final Type INT_TYPE =  new Type(Type.INT);
    public static final Type BOOL_TYPE =  new Type(Type.BOOL);
    public static final Type STRING_TYPE = new Type(Type.STRING);
    public static final Type VOID_TYPE =  new Type(Type.VOID);
    
    public static String tabs(int n) {
        String s = "";
        for(int i=0; i < n; i++) {
            s += " ";
        }
        return s;
    }
      
    public static void fatalError(String msg) {
        (new Throwable(msg)).printStackTrace();
        System.exit(1);
    }
    
    public static int max(Vector<Integer> v) {
        int max = 0;
        for(int i=0; i < v.size(); i++) {
            int e = v.get(i);
            if(e > max)
                max = e;
        }
        return max;
    }
    
    public static int max(Vector<Integer> a, Vector<Integer> b) {
        return Math.max(max(a), max(b));
    }
    
    public static String printToken(Symbol s) {
        switch(s.sym) {
            case TokenConstants.EOF:
                return "EOF";
            case TokenConstants.error:
                return "error";
            case TokenConstants.DEF:
                return "DEF";
            case TokenConstants.PRINT:
                return "PRINT";
            case TokenConstants.IF:
                return "IF";
            case TokenConstants.ELSE:
                return "ELSE";
            case TokenConstants.WHILE:
                return "WHILE";
            case TokenConstants.RETURN:
                return "RETURN";
            case TokenConstants.AND:
                return "AND";
            case TokenConstants.OR:
                return "OR";
            case TokenConstants.NOT:
                return "NOT";
            case TokenConstants.INT:
                return "INT";
            case TokenConstants.BOOL:
                return "BOOL";
            case TokenConstants.VOID:
                return "VOID";
            case TokenConstants.ID:
                return "ID = " + (String)s.value;
            case TokenConstants.INTCONST:
                return "INTCONST = " + s.value;
            case TokenConstants.BOOLCONST:
                return "BOOLCONST = " + (boolean)s.value;
            case TokenConstants.STRCONST:
                return "STRCONST = " + (String)s.value;
            case TokenConstants.SEMI:
                return ";";
            case TokenConstants.LPAREN:
                return "(";
            case TokenConstants.RPAREN:
                return ")";
            case TokenConstants.COLON:
                return ":";
            case TokenConstants.LBRACE:
                return "{";
            case TokenConstants.RBRACE:
                return "}";
            case TokenConstants.ASSIGN:
                return "=";
            case TokenConstants.PLUS:
                return "+";
            case TokenConstants.MINUS:
                return "-";
            case TokenConstants.MULT:
                return "*";
            case TokenConstants.DIV:
                return "/";
            case TokenConstants.EQ:
                return "==";
            case TokenConstants.NEQ:
                return "!=";
            case TokenConstants.LEQ:
                return "<=";
            case TokenConstants.GEQ:
                return ">=";
            case TokenConstants.LT:
                return "<";
            case TokenConstants.GT:
                return ">";
            case TokenConstants.COMMA:
                return ",";
            default:
                return (String)s.value;
        }
    }

}
