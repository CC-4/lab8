import java.io.PrintStream;

public final class CgenSupport {

    public static final String DATA = "\t.data";
    public static final String ALIGN = "\t.align\t2";
    public static final String GLOBL = "\t.globl\t";
    public static final String WORD = "\t.word\t";
    public static final String ASCIIZ = "\t.asciiz\t";
    
    public static final String LABEL = ":";
    
    public static final String STRCONST_PREFIX = "str_const_";
    public static final String INTCONST_PREFIX = "int_const_";
    public static final String BOOLCONST_PREFIX = "bool_const_";
    public static final String FUNCTION_PREFIX = "function_";
    
    public static final String $ZERO = "$zero";
    public static final String $V0 = "$v0";
    public static final String ACC = "$a0";
    public static final String $A1 = "$a1";
    public static final String $T0 = "$t0";
    public static final String $T1 = "$t1";
    public static final String $T2 = "$t2";
    public static final String $T3 = "$t3";
    public static final String $T4 = "$t4";
    public static final String $T5 = "$t5";
    public static final String $T6 = "$t6";
    public static final String $T7 = "$t7";
    public static final String $SP = "$sp";
    public static final String $FP = "$fp";
    public static final String $RA = "$ra";
    
    public static final String JALR = "\tjalr\t";
    public static final String JAL = "\tjal\t";               
    public static final String RET = "\tjr\t" + $RA + "\t";
    
    public static final String SW   = "\tsw\t";
    public static final String LW   = "\tlw\t";
    public static final String LI   = "\tli\t";
    public static final String LA   = "\tla\t";

    public static final String MOVE = "\tmove\t";
    public static final String ADD  = "\tadd\t";
    public static final String ADDI = "\taddi\t";
    public static final String ADDU = "\taddu\t";
    public static final String ADDIU= "\taddiu\t";
    public static final String DIV  = "\tdiv\t";
    public static final String MUL  = "\tmul\t";
    public static final String SUB  = "\tsub\t";
    public static final String SLL  = "\tsll\t";
    public static final String BEQZ = "\tbeqz\t";
    public static final String BRANCH  = "\tb\t";
    public static final String BEQ     = "\tbeq\t";
    public static final String BNE     = "\tbne\t";
    public static final String BLEQ    = "\tble\t";
    public static final String BLT     = "\tblt\t";
    public static final String BGT     = "\tbgt\t";
       
    public static void codeHeader(PrintStream p) {
        p.println(DATA);
        p.println(ALIGN);
        p.println(GLOBL + FUNCTION_PREFIX + "main");
    }
    
    
    public static void emitLabelDef(String labelName, PrintStream p) {
        p.println(labelName + LABEL);
    }
    
    public static void codeConstants(PrintStream p) {
        ConstantsTable.table.codeStringConstants(p);
        ConstantsTable.table.codeIntConstants(p);
        boolConstDef(true, p);
        p.println(LABEL);
        p.println(WORD + 1);
        boolConstDef(false, p);
        p.println(LABEL);
        p.println(WORD + 0);
    }
    
    public static void boolConstDef(boolean b, PrintStream p) {
        p.print(BOOLCONST_PREFIX+b);
    }
    
    public static void intConstDef(String i, PrintStream p) {
        p.print(INTCONST_PREFIX+ConstantsTable.table.getIntIndex(i));
    }
    
    public static void strConstDef(String s, PrintStream p) {
        p.print(STRCONST_PREFIX+ConstantsTable.table.getStringIndex(s));
    }
    
    public static void emitLoad(String dest_reg, int offset, String src_reg, PrintStream p) {
        p.println(LW + dest_reg + " " + (offset * 4) + "(" + src_reg + ")");
    }
    
    public static void emitStore(String src_reg, int offset, String dest_reg, PrintStream p) {
        p.println(SW + src_reg + " " + (offset * 4) + "(" + dest_reg + ")");
    }
    
    public static void emitLoadImm(String dest_reg, int val, PrintStream p) {
        p.println(LI + dest_reg + " " + val);
    }
    
    public static void emitLoadAddress(String dest_reg, String address, PrintStream p) {
        p.println(LA + dest_reg + " " + address);
    }
    
    public static void emitPartialLoadAddress(String dest_reg, PrintStream p) {
        p.print(LA + dest_reg + " ");
    }
    
    public static void emitLoadBool(String dest_reg, boolean b, PrintStream p) {
        emitPartialLoadAddress(dest_reg, p);
        boolConstDef(b, p);
        p.println("");
    }
    
    public static void emitLoadInt(String dest_reg, String i, PrintStream p) {
        emitPartialLoadAddress(dest_reg, p);
        intConstDef(i, p);
        p.println("");
    }
    
    public static void emitLoadString(String dest_reg, String s, PrintStream p) {
        emitPartialLoadAddress(dest_reg, p);
        strConstDef(s, p);
        p.println("");
    }
    
    public static void emitMove(String dest_reg, String src_reg, PrintStream p) {
        p.println(MOVE + dest_reg + " " + src_reg);
    }
    
}
