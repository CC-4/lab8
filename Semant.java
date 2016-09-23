import java.io.*;

public class Semant {

    public static void main(String[] args) throws IOException {

        if(args.length > 0) {
            File file = new File(args[0]);
            if(file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                fis.close();
                String input = new String(data);
                ViperLexer lexer = new ViperLexer(new StringReader(input));
                ViperParser parser = new ViperParser(lexer);
                try {
                    Program p = (Program)parser.parse().value;
                    p.semant();
                    if(SemantErrors.getSemantErrors() != 0) {
                        System.err.println("Compilation halted due to static semantic errors.");
                        System.exit(1);
                    }
                    p.print(System.out, 0);
                }catch(Exception e) { }
            }
        }

    }

}
