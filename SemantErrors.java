public final class SemantErrors {

    private static int semantErrors = 0;

    private static void semantError(int lineno, String error) {
        System.err.println("line " + lineno + ": " + error);
        System.err.flush();
        SemantErrors.semantErrors++;
    }

    private static void semantError(String error) {
        System.err.println(error);
        System.err.flush();
        SemantErrors.semantErrors++;
    }

    public static int getSemantErrors() {
       return SemantErrors.semantErrors;
    }

    // Cuando una funcion esta definida varias veces
    public static void functionMultiplyDefined(int lineno, String name) {
        SemantErrors.semantError(lineno, "Function " + name + " is multiply defined.");
    }

    // Cuando no hay una funcion main definida en el archivo
    public static void noMainFunction() {
        SemantErrors.semantError("No 'main' function is defined.");
    }

    // Cuando la funcion main tiene declarados formal parameters y no deberia
    public static void mainNoArguments(int lineno) {
        SemantErrors.semantError(lineno, "'main' function should have no arguments.");
    }

    // Cuando un formal parameter se repite
    public static void formalMultiplyDefined(int lineno, String name) {
        SemantErrors.semantError(lineno, "Formal parameter " + name + " is multiply defined.");
    }

    // Cuando una variable esta definida varias veces en un scope
    public static void varMultiplyDefined(int lineno, String name) {
        SemantErrors.semantError(lineno, "Local var " + name + " is multiply defined.");
    }

    // Cuando una funcion tiene un tipo de retorno diferente al declarado
    public static void diffReturnType(int lineno, String name, Type d, Type r) {
        SemantErrors.semantError(lineno, "Inferred return type " + r + " of function " + name + " does not conform to declared return type " + d + ".");
    }

    // Cuando no hay return y se espera uno en una funcion
    public static void noReturn(int lineno, String name, Type t) {
        SemantErrors.semantError(lineno, "Function " + name + " must return a result of type " + t + ".");
    }

    // Cuando el return no es esperado en una funcion
    public static void noReturnExpected(int lineno) {
        SemantErrors.semantError(lineno, "Void functions cannot return a value.");
    }

    // Cuando se crea una variable y lo que se le asigna no es del tipo declarado
    public static void diffInitType(int lineno, String name, Type d, Type i) {
        SemantErrors.semantError(lineno, "Inferred type " + i + " of initialization of  local var " + name + " does not conform to declared type "+d+".");
    }

    // Asignar una expresion a una variable no definida
    public static void assignToUndeclaredVar(int lineno, String name) {
        SemantErrors.semantError(lineno, "Assignment to undeclared variable "+name+".");
    }

    // Asignar una expresion de algun tipo a una variable con otro tipo diferente
    public static void assignDiffType(int lineno, String name, Type d, Type e) {
        SemantErrors.semantError(lineno, "Type "+e+" of assigned expression does not conform to declared type "+d+" of identifier "+name+".");
    }

    // Llamada a una funcion no definida
    public static void callToUndefinedFunc(int lineno, String name) {
        SemantErrors.semantError(lineno, "Call to undefined function "+name+".");
    }

    // Llamada a una funcion con diferente numero de argumentos
    public static void callDiffNumArgs(int lineno, String name) {
        SemantErrors.semantError(lineno, "function "+name+" called with wrong number of arguments.");
    }

    // Llamada a una function con diferente tipo en los argumentos al original
    public static void callDiffTypeArgs(int lineno, String name, String formalName, Type d, Type a) {
        SemantErrors.semantError(lineno, "In call of function "+name+", type "+a+" of parameter "+formalName+" does not conform to declared type "+d+".");
    }

    // Cuando el if no tiene un predicate de tipo bool
    public static void ifNoBoolPredicate(int lineno) {
        SemantErrors.semantError(lineno, "Predicate of 'if' does not have type bool.");
    }

    // Cuando el while no tiene una condicion de tipo bool
    public static void whileNoBoolCondition(int lineno) {
        SemantErrors.semantError(lineno, "While condition does not have type bool.");
    }

    // Cuando se trata de hacer una op binaria con tipos diferentes a float o a int
    public static void opDiffTypes(int lineno, String op, Type e1, Type e2) {
        SemantErrors.semantError(lineno, "non-int arguments: "+e1+" " + op +" "+e2);
    }

    // Cuando se trata de hacer una op logica con tipos diferentes a bool
    public static void boolOpDiffTypes(int lineno, String op, Type e1, Type e2) {
        SemantErrors.semantError(lineno, "non-bool arguments: "+e1+" " + op +" "+e2);
    }

    // Cuando el argument del not no es de tipo bool
    public static void notNoBool(int lineno, Type t) {
        SemantErrors.semantError(lineno, "Argument of 'not' has type "+t+" instead of bool.");
    }

    // Variable no definida
    public static void undeclaredeVar(int lineno, String name) {
        SemantErrors.semantError(lineno, "Undeclared identifier "+name+".");
    }

    // Salir porque hay errores semanticos
    public static void exit() {
        SemantErrors.semantError("Compilation halted due to static semantic errors.");
    }

}
