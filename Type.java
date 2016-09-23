public final class Type {

    // Los 6 tipos basicos del lenguaje Viper
    public static final int NOTYPE = -1;
    public static final int INT   =  0;
    public static final int BOOL  =  1;
    public static final int STRING = 2;
    public static final int VOID  =  3;
    
    private int id;

    // Constructor de la clase Type
    public Type(int id) {
        this.id = id;
    }

    // Metodo para saber si es un int
    public boolean isInt() {
        return this.id == Type.INT;
    }

    // Metodo para saber si es un bool
    public boolean isBool() {
        return this.id == Type.BOOL;
    }

    // Metodo para saber si es voy
    public boolean isVoid() {
        return this.id == Type.VOID;
    }
    
    // Metodo para saber si es string
    public boolean isString() {
        return this.id== Type.STRING;
    }
    
    // Metodo para saber si es notype
    public boolean isNotype() {
        return this.id == Type.NOTYPE;
    }
    
    // para obtener el id
    public int getId() {
        return this.id;
    }
    
    // para comparar types
    @Override
    public boolean equals(Object other) {
        return (other instanceof Type) && this.id== ((Type) other).getId();
    }

    // representacion de un type
    @Override
    public String toString() {
        switch(this.id) {
            case Type.INT:
                return "int";
            case Type.BOOL:
                return "bool";
            case Type.VOID:
                return "void";
            case Type.STRING:
                return "string";
            default:
                return "notype";
        }
    }

}