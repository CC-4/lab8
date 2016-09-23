import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java_cup.runtime.Symbol;


public class ViperLexer implements java_cup.runtime.Scanner {
    private final int YY_BUFFER_SIZE = 512;
    private final int YY_F = -1;
    private final int YY_NO_STATE = -1;
    private final int YY_NOT_ACCEPT = 0;
    private final int YY_END = 2;
    private final int YY_NO_ANCHOR = 4;
    private final int YY_BOL = 128;
    private final int YY_EOF = 129;

    private static final int MAX_STR_CONST = 1025;
    private String string_buf;
    public int getLineNo() {
        return yyline + 1;
    }
    // Para probar el lexer
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
                Symbol symbol;
                while((symbol = lexer.next_token()).sym != TokenConstants.EOF) {
                    System.out.println(Utils.printToken(symbol));
                }
                System.out.println(Utils.printToken(symbol));
            }
        }
    }
    private java.io.BufferedReader yy_reader;
    private int yy_buffer_index;
    private int yy_buffer_read;
    private int yy_buffer_start;
    private int yy_buffer_end;
    private char yy_buffer[];
    private int yyline;
    private boolean yy_at_bol;
    private int yy_lexical_state;

    public ViperLexer (java.io.Reader reader) {
        this ();
        if (null == reader) {
            throw (new Error("Error: Bad input stream initializer."));
        }
        yy_reader = new java.io.BufferedReader(reader);
    }

    public ViperLexer (java.io.InputStream instream) {
        this ();
        if (null == instream) {
            throw (new Error("Error: Bad input stream initializer."));
        }
        yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
    }

    private ViperLexer () {
        yy_buffer = new char[YY_BUFFER_SIZE];
        yy_buffer_read = 0;
        yy_buffer_index = 0;
        yy_buffer_start = 0;
        yy_buffer_end = 0;
        yyline = 0;
        yy_at_bol = true;
        yy_lexical_state = YYINITIAL;
    }

    private final int STATE3 = 3;
    private final int STATE2 = 2;
    private final int STATE1 = 1;
    private final int YYINITIAL = 0;
    private final int STATE4 = 4;
    private final int yy_state_dtrans[] = {
        0,
        57,
        60,
        62,
        64
    };
    private void yybegin (int state) {
        yy_lexical_state = state;
    }
    private int yy_advance ()
        throws java.io.IOException {
        int next_read;
        int i;
        int j;

        if (yy_buffer_index < yy_buffer_read) {
            return yy_buffer[yy_buffer_index++];
        }

        if (0 != yy_buffer_start) {
            i = yy_buffer_start;
            j = 0;
            while (i < yy_buffer_read) {
                yy_buffer[j] = yy_buffer[i];
                ++i;
                ++j;
            }
            yy_buffer_end = yy_buffer_end - yy_buffer_start;
            yy_buffer_start = 0;
            yy_buffer_read = j;
            yy_buffer_index = j;
            next_read = yy_reader.read(yy_buffer,
                    yy_buffer_read,
                    yy_buffer.length - yy_buffer_read);
            if (-1 == next_read) {
                return YY_EOF;
            }
            yy_buffer_read = yy_buffer_read + next_read;
        }

        while (yy_buffer_index >= yy_buffer_read) {
            if (yy_buffer_index >= yy_buffer.length) {
                yy_buffer = yy_double(yy_buffer);
            }
            next_read = yy_reader.read(yy_buffer,
                    yy_buffer_read,
                    yy_buffer.length - yy_buffer_read);
            if (-1 == next_read) {
                return YY_EOF;
            }
            yy_buffer_read = yy_buffer_read + next_read;
        }
        return yy_buffer[yy_buffer_index++];
    }
    private void yy_move_end () {
        if (yy_buffer_end > yy_buffer_start &&
            '\n' == yy_buffer[yy_buffer_end-1])
            yy_buffer_end--;
        if (yy_buffer_end > yy_buffer_start &&
            '\r' == yy_buffer[yy_buffer_end-1])
            yy_buffer_end--;
    }
    private boolean yy_last_was_cr=false;
    private void yy_mark_start () {
        int i;
        for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
            if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
                ++yyline;
            }
            if ('\r' == yy_buffer[i]) {
                ++yyline;
                yy_last_was_cr=true;
            } else yy_last_was_cr=false;
        }
        yy_buffer_start = yy_buffer_index;
    }
    private void yy_mark_end () {
        yy_buffer_end = yy_buffer_index;
    }
    private void yy_to_mark () {
        yy_buffer_index = yy_buffer_end;
        yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
                    ('\r' == yy_buffer[yy_buffer_end-1] ||
                     '\n' == yy_buffer[yy_buffer_end-1] ||
                     2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
                     2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
    }
    private java.lang.String yytext () {
        return (new java.lang.String(yy_buffer,
            yy_buffer_start,
            yy_buffer_end - yy_buffer_start));
    }

    private char[] yy_double (char buf[]) {
        int i;
        char newbuf[];
        newbuf = new char[2*buf.length];
        for (i = 0; i < buf.length; ++i) {
            newbuf[i] = buf[i];
        }
        return newbuf;
    }
    private final int YY_E_INTERNAL = 0;

    private java.lang.String yy_error_string[] = {
        "Error: Internal error.\n",
        "Error: Unmatched input.\n"
    };
    private void yy_error (int code,boolean fatal) {
        java.lang.System.out.print(yy_error_string[code]);
        java.lang.System.out.flush();
        if (fatal) {
            throw new Error("Fatal Error.\n");
        }
    }
    private int[][] unpackFromString(int size1, int size2, String st) {
        int colonIndex = -1;
        String lengthString;
        int sequenceLength = 0;
        int sequenceInteger = 0;

        int commaIndex;
        String workString;

        int res[][] = new int[size1][size2];
        for (int i= 0; i < size1; i++) {
            for (int j= 0; j < size2; j++) {
                if (sequenceLength != 0) {
                    res[i][j] = sequenceInteger;
                    sequenceLength--;
                    continue;
                }
                commaIndex = st.indexOf(',');
                workString = (commaIndex==-1) ? st :
                    st.substring(0, commaIndex);
                st = st.substring(commaIndex+1);
                colonIndex = workString.indexOf(':');
                if (colonIndex == -1) {
                    res[i][j]=Integer.parseInt(workString);
                    continue;
                }
                lengthString =
                    workString.substring(colonIndex+1);
                sequenceLength=Integer.parseInt(lengthString);
                workString=workString.substring(0,colonIndex);
                sequenceInteger=Integer.parseInt(workString);
                res[i][j] = sequenceInteger;
                sequenceLength--;
            }
        }
        return res;
    }
    private int yy_acpt[] = {
        /* 0 */ YY_NOT_ACCEPT,
        /* 1 */ YY_NO_ANCHOR,
        /* 2 */ YY_NO_ANCHOR,
        /* 3 */ YY_NO_ANCHOR,
        /* 4 */ YY_NO_ANCHOR,
        /* 5 */ YY_NO_ANCHOR,
        /* 6 */ YY_NO_ANCHOR,
        /* 7 */ YY_NO_ANCHOR,
        /* 8 */ YY_NO_ANCHOR,
        /* 9 */ YY_NO_ANCHOR,
        /* 10 */ YY_NO_ANCHOR,
        /* 11 */ YY_NO_ANCHOR,
        /* 12 */ YY_NO_ANCHOR,
        /* 13 */ YY_NO_ANCHOR,
        /* 14 */ YY_NO_ANCHOR,
        /* 15 */ YY_NO_ANCHOR,
        /* 16 */ YY_NO_ANCHOR,
        /* 17 */ YY_NO_ANCHOR,
        /* 18 */ YY_NO_ANCHOR,
        /* 19 */ YY_NO_ANCHOR,
        /* 20 */ YY_NO_ANCHOR,
        /* 21 */ YY_NO_ANCHOR,
        /* 22 */ YY_NO_ANCHOR,
        /* 23 */ YY_NO_ANCHOR,
        /* 24 */ YY_NO_ANCHOR,
        /* 25 */ YY_NO_ANCHOR,
        /* 26 */ YY_NO_ANCHOR,
        /* 27 */ YY_NO_ANCHOR,
        /* 28 */ YY_NO_ANCHOR,
        /* 29 */ YY_NO_ANCHOR,
        /* 30 */ YY_NO_ANCHOR,
        /* 31 */ YY_NO_ANCHOR,
        /* 32 */ YY_NO_ANCHOR,
        /* 33 */ YY_NO_ANCHOR,
        /* 34 */ YY_NO_ANCHOR,
        /* 35 */ YY_NO_ANCHOR,
        /* 36 */ YY_NO_ANCHOR,
        /* 37 */ YY_NO_ANCHOR,
        /* 38 */ YY_NO_ANCHOR,
        /* 39 */ YY_NO_ANCHOR,
        /* 40 */ YY_NO_ANCHOR,
        /* 41 */ YY_NO_ANCHOR,
        /* 42 */ YY_NO_ANCHOR,
        /* 43 */ YY_NO_ANCHOR,
        /* 44 */ YY_NO_ANCHOR,
        /* 45 */ YY_NO_ANCHOR,
        /* 46 */ YY_NO_ANCHOR,
        /* 47 */ YY_NO_ANCHOR,
        /* 48 */ YY_NO_ANCHOR,
        /* 49 */ YY_NO_ANCHOR,
        /* 50 */ YY_NO_ANCHOR,
        /* 51 */ YY_NO_ANCHOR,
        /* 52 */ YY_NO_ANCHOR,
        /* 53 */ YY_NO_ANCHOR,
        /* 54 */ YY_NO_ANCHOR,
        /* 55 */ YY_NO_ANCHOR,
        /* 56 */ YY_NO_ANCHOR,
        /* 57 */ YY_NOT_ACCEPT,
        /* 58 */ YY_NO_ANCHOR,
        /* 59 */ YY_NO_ANCHOR,
        /* 60 */ YY_NOT_ACCEPT,
        /* 61 */ YY_NO_ANCHOR,
        /* 62 */ YY_NOT_ACCEPT,
        /* 63 */ YY_NO_ANCHOR,
        /* 64 */ YY_NOT_ACCEPT,
        /* 65 */ YY_NO_ANCHOR,
        /* 66 */ YY_NO_ANCHOR,
        /* 67 */ YY_NO_ANCHOR,
        /* 68 */ YY_NO_ANCHOR,
        /* 69 */ YY_NO_ANCHOR,
        /* 70 */ YY_NO_ANCHOR,
        /* 71 */ YY_NO_ANCHOR,
        /* 72 */ YY_NO_ANCHOR,
        /* 73 */ YY_NO_ANCHOR,
        /* 74 */ YY_NO_ANCHOR,
        /* 75 */ YY_NO_ANCHOR,
        /* 76 */ YY_NO_ANCHOR,
        /* 77 */ YY_NO_ANCHOR,
        /* 78 */ YY_NO_ANCHOR,
        /* 79 */ YY_NO_ANCHOR,
        /* 80 */ YY_NO_ANCHOR,
        /* 81 */ YY_NO_ANCHOR,
        /* 82 */ YY_NO_ANCHOR,
        /* 83 */ YY_NO_ANCHOR,
        /* 84 */ YY_NO_ANCHOR,
        /* 85 */ YY_NO_ANCHOR,
        /* 86 */ YY_NO_ANCHOR,
        /* 87 */ YY_NO_ANCHOR,
        /* 88 */ YY_NO_ANCHOR,
        /* 89 */ YY_NO_ANCHOR,
        /* 90 */ YY_NO_ANCHOR,
        /* 91 */ YY_NO_ANCHOR,
        /* 92 */ YY_NO_ANCHOR,
        /* 93 */ YY_NO_ANCHOR,
        /* 94 */ YY_NO_ANCHOR,
        /* 95 */ YY_NO_ANCHOR,
        /* 96 */ YY_NO_ANCHOR,
        /* 97 */ YY_NO_ANCHOR,
        /* 98 */ YY_NO_ANCHOR,
        /* 99 */ YY_NO_ANCHOR
    };
    private int yy_cmap[] = unpackFromString(1,130,
"41,36:8,37,38,37:3,36:18,37,29,39,35,36:4,19,20,27,25,32,26,36,28,33:10,21," +
"18,30,24,31,36:2,34:26,36,40,36:2,34,36,17,4,34,8,9,10,34,15,1,34:2,6,34,2," +
"5,11,34,12,13,3,16,7,14,34:3,22,36,23,36:2,0:2")[0];

    private int yy_rmap[] = unpackFromString(1,100,
"0,1,2,1:6,3,1:4,4,5,6,1,7,1:4,8:2,1:4,8:12,1:5,9,1:10,10,11,1,12,13,14,15,1" +
"6,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,4" +
"1,42,8,43,44,45,46,47,48,49,50")[0];

    private int yy_nxt[][] = unpackFromString(51,42,
"1,2,58,87,89,61,91,92,93,94,95,96,97,91,98,91:2,99,3,4,5,6,7,8,9,10,11,12,1" +
"3,14,15,16,17,18,91,19,59,20,21,22,59:2,-1:43,91,63,91:7,23,91:7,-1:15,91:2" +
",-1:31,25,-1:41,26,-1:41,27,-1:41,28,-1:50,18,-1:9,91:17,-1:15,91:2,-1:9,48" +
",49,50,-1:5,51,-1:29,52,-1,1,41:37,42,41:3,-1,91:4,65,91:12,-1:15,91:2,-1:7" +
",1,43:37,44,45,46,47,-1,91:11,24,91:5,-1:15,91:2,-1:7,1,53:40,54,-1,91:2,29" +
",91:14,-1:15,91:2,-1:7,1,55:37,56:2,55,-1:2,91:2,30,91:14,-1:15,91:2,-1:8,9" +
"1:15,74,91,-1:15,91:2,-1:8,91:4,75,91:12,-1:15,91:2,-1:8,76,91:16,-1:15,91:" +
"2,-1:8,91:9,31,91:7,-1:15,91:2,-1:8,91:12,77,91:4,-1:15,91:2,-1:8,91:5,78,9" +
"1:11,-1:15,91:2,-1:8,91:2,80,91:14,-1:15,91:2,-1:8,91:7,32,91:9,-1:15,91:2," +
"-1:8,91:8,33,91:8,-1:15,91:2,-1:8,91:5,34,91:11,-1:15,91:2,-1:8,91:7,35,91:" +
"9,-1:15,91:2,-1:8,91:8,36,91:8,-1:15,91:2,-1:8,91:12,82,91:4,-1:15,91:2,-1:" +
"8,91,83,91:15,-1:15,91:2,-1:8,91:15,84,91,-1:15,91:2,-1:8,91:5,85,91:11,-1:" +
"15,91:2,-1:8,91:8,37,91:8,-1:15,91:2,-1:8,91:2,38,91:14,-1:15,91:2,-1:8,91:" +
"11,86,91:5,-1:15,91:2,-1:8,91:8,39,91:8,-1:15,91:2,-1:8,91,40,91:15,-1:15,9" +
"1:2,-1:8,91:11,66,91:5,-1:15,91:2,-1:8,79,91:16,-1:15,91:2,-1:8,91:4,67,91:" +
"12,-1:15,91:2,-1:8,81,91:16,-1:15,91:2,-1:8,91:4,68,91:12,-1:15,91:2,-1:8,9" +
"1:8,69,91:8,-1:15,91:2,-1:8,91:5,70,91:11,-1:15,91:2,-1:8,91:16,71,-1:15,91" +
":2,-1:8,91:11,88,91:5,-1:15,91:2,-1:8,91:8,72,91:8,-1:15,91:2,-1:8,91:14,90" +
",91:2,-1:15,91:2,-1:8,91,73,91:15,-1:15,91:2,-1:7");

    public java_cup.runtime.Symbol next_token ()
        throws java.io.IOException {
        int yy_lookahead;
        int yy_anchor = YY_NO_ANCHOR;
        int yy_state = yy_state_dtrans[yy_lexical_state];
        int yy_next_state = YY_NO_STATE;
        int yy_last_accept_state = YY_NO_STATE;
        boolean yy_initial = true;
        int yy_this_accept;

        yy_mark_start();
        yy_this_accept = yy_acpt[yy_state];
        if (YY_NOT_ACCEPT != yy_this_accept) {
            yy_last_accept_state = yy_state;
            yy_mark_end();
        }
        while (true) {
            if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
            else yy_lookahead = yy_advance();
            yy_next_state = YY_F;
            yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
            if (YY_EOF == yy_lookahead && true == yy_initial) {

    switch(yy_lexical_state) {
        case YYINITIAL:
            /* Aqui no pasa nada */
        break;
        case STATE2:
            yybegin(YYINITIAL);
            return new Symbol(TokenConstants.ERROR, "EOF in string constant");
    }
    return new Symbol(TokenConstants.EOF);
            }
            if (YY_F != yy_next_state) {
                yy_state = yy_next_state;
                yy_initial = false;
                yy_this_accept = yy_acpt[yy_state];
                if (YY_NOT_ACCEPT != yy_this_accept) {
                    yy_last_accept_state = yy_state;
                    yy_mark_end();
                }
            }
            else {
                if (YY_NO_STATE == yy_last_accept_state) {
                    throw (new Error("Lexical Error: Unmatched Input."));
                }
                else {
                    yy_anchor = yy_acpt[yy_last_accept_state];
                    if (0 != (YY_END & yy_anchor)) {
                        yy_move_end();
                    }
                    yy_to_mark();
                    switch (yy_last_accept_state) {
                    case 1:
                        
                    case -2:
                        break;
                    case 2:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -3:
                        break;
                    case 3:
                        { return new Symbol(TokenConstants.SEMI);             }
                    case -4:
                        break;
                    case 4:
                        { return new Symbol(TokenConstants.LPAREN);           }
                    case -5:
                        break;
                    case 5:
                        { return new Symbol(TokenConstants.RPAREN);           }
                    case -6:
                        break;
                    case 6:
                        { return new Symbol(TokenConstants.COLON);            }
                    case -7:
                        break;
                    case 7:
                        { return new Symbol(TokenConstants.LBRACE);           }
                    case -8:
                        break;
                    case 8:
                        { return new Symbol(TokenConstants.RBRACE);           }
                    case -9:
                        break;
                    case 9:
                        { return new Symbol(TokenConstants.ASSIGN);           }
                    case -10:
                        break;
                    case 10:
                        { return new Symbol(TokenConstants.PLUS);             }
                    case -11:
                        break;
                    case 11:
                        { return new Symbol(TokenConstants.MINUS);            }
                    case -12:
                        break;
                    case 12:
                        { return new Symbol(TokenConstants.MULT);             }
                    case -13:
                        break;
                    case 13:
                        { return new Symbol(TokenConstants.DIV);              }
                    case -14:
                        break;
                    case 14:
                        { return new Symbol(TokenConstants.ERROR, yytext()); }
                    case -15:
                        break;
                    case 15:
                        { return new Symbol(TokenConstants.LT);               }
                    case -16:
                        break;
                    case 16:
                        { return new Symbol(TokenConstants.GT);               }
                    case -17:
                        break;
                    case 17:
                        { return new Symbol(TokenConstants.COMMA);            }
                    case -18:
                        break;
                    case 18:
                        { return new Symbol(TokenConstants.INTCONST, ConstantsTable.table.addInt(yytext())); }
                    case -19:
                        break;
                    case 19:
                        { yybegin(STATE1); }
                    case -20:
                        break;
                    case 20:
                        {  }
                    case -21:
                        break;
                    case 21:
                        {  }
                    case -22:
                        break;
                    case 22:
                        {
                                yybegin(STATE2);
                                string_buf = new String();
                            }
                    case -23:
                        break;
                    case 23:
                        { return new Symbol(TokenConstants.IF); }
                    case -24:
                        break;
                    case 24:
                        { return new Symbol(TokenConstants.OR); }
                    case -25:
                        break;
                    case 25:
                        { return new Symbol(TokenConstants.EQ);               }
                    case -26:
                        break;
                    case 26:
                        { return new Symbol(TokenConstants.NEQ);              }
                    case -27:
                        break;
                    case 27:
                        { return new Symbol(TokenConstants.LEQ);              }
                    case -28:
                        break;
                    case 28:
                        { return new Symbol(TokenConstants.GEQ);              }
                    case -29:
                        break;
                    case 29:
                        { return new Symbol(TokenConstants.INT, Utils.INT_TYPE); }
                    case -30:
                        break;
                    case 30:
                        { return new Symbol(TokenConstants.NOT); }
                    case -31:
                        break;
                    case 31:
                        { return new Symbol(TokenConstants.DEF); }
                    case -32:
                        break;
                    case 32:
                        { return new Symbol(TokenConstants.AND); }
                    case -33:
                        break;
                    case 33:
                        { return new Symbol(TokenConstants.BOOLCONST, true); }
                    case -34:
                        break;
                    case 34:
                        { return new Symbol(TokenConstants.BOOL, Utils.BOOL_TYPE); }
                    case -35:
                        break;
                    case 35:
                        { return new Symbol(TokenConstants.VOID, Utils.VOID_TYPE); }
                    case -36:
                        break;
                    case 36:
                        { return new Symbol(TokenConstants.ELSE); }
                    case -37:
                        break;
                    case 37:
                        { return new Symbol(TokenConstants.BOOLCONST, false); }
                    case -38:
                        break;
                    case 38:
                        { return new Symbol(TokenConstants.PRINT); }
                    case -39:
                        break;
                    case 39:
                        { return new Symbol(TokenConstants.WHILE); }
                    case -40:
                        break;
                    case 40:
                        { return new Symbol(TokenConstants.RETURN); }
                    case -41:
                        break;
                    case 41:
                        { }
                    case -42:
                        break;
                    case 42:
                        { yybegin(YYINITIAL); }
                    case -43:
                        break;
                    case 43:
                        { string_buf += yytext(); }
                    case -44:
                        break;
                    case 44:
                        {
                                yybegin(YYINITIAL);
                                return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
                            }
                    case -45:
                        break;
                    case 45:
                        {
                                yybegin(YYINITIAL);
                                if(string_buf.length() >= MAX_STR_CONST) {
                                    return new Symbol(TokenConstants.ERROR, "String constant too long");
                                }
        
                                return new Symbol(TokenConstants.STRCONST, ConstantsTable.table.addString(string_buf));
                            }
                    case -46:
                        break;
                    case 46:
                        { yybegin(STATE3); }
                    case -47:
                        break;
                    case 47:
                        {
                                yybegin(YYINITIAL);
                                return new Symbol(TokenConstants.ERROR, "String contains null character");
                            }
                    case -48:
                        break;
                    case 48:
                        { string_buf += "\n"; }
                    case -49:
                        break;
                    case 49:
                        { string_buf += "\t"; }
                    case -50:
                        break;
                    case 50:
                        { string_buf += "\b"; }
                    case -51:
                        break;
                    case 51:
                        { string_buf += "\f"; }
                    case -52:
                        break;
                    case 52:
                        { string_buf += "\\"; }
                    case -53:
                        break;
                    case 53:
                        {
                                yybegin(STATE2);
                                string_buf += yytext();
                            }
                    case -54:
                        break;
                    case 54:
                        {
                                yybegin(STATE4);
                                return new Symbol(TokenConstants.ERROR, "String contains null character");
                            }
                    case -55:
                        break;
                    case 55:
                        {  }
                    case -56:
                        break;
                    case 56:
                        { yybegin(YYINITIAL); }
                    case -57:
                        break;
                    case 58:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -58:
                        break;
                    case 59:
                        { return new Symbol(TokenConstants.ERROR, yytext()); }
                    case -59:
                        break;
                    case 61:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -60:
                        break;
                    case 63:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -61:
                        break;
                    case 65:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -62:
                        break;
                    case 66:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -63:
                        break;
                    case 67:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -64:
                        break;
                    case 68:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -65:
                        break;
                    case 69:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -66:
                        break;
                    case 70:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -67:
                        break;
                    case 71:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -68:
                        break;
                    case 72:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -69:
                        break;
                    case 73:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -70:
                        break;
                    case 74:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -71:
                        break;
                    case 75:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -72:
                        break;
                    case 76:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -73:
                        break;
                    case 77:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -74:
                        break;
                    case 78:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -75:
                        break;
                    case 79:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -76:
                        break;
                    case 80:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -77:
                        break;
                    case 81:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -78:
                        break;
                    case 82:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -79:
                        break;
                    case 83:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -80:
                        break;
                    case 84:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -81:
                        break;
                    case 85:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -82:
                        break;
                    case 86:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -83:
                        break;
                    case 87:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -84:
                        break;
                    case 88:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -85:
                        break;
                    case 89:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -86:
                        break;
                    case 90:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -87:
                        break;
                    case 91:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -88:
                        break;
                    case 92:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -89:
                        break;
                    case 93:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -90:
                        break;
                    case 94:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -91:
                        break;
                    case 95:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -92:
                        break;
                    case 96:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -93:
                        break;
                    case 97:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -94:
                        break;
                    case 98:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -95:
                        break;
                    case 99:
                        { return new Symbol(TokenConstants.ID, ConstantsTable.table.addId(yytext())); }
                    case -96:
                        break;
                    default:
                        yy_error(YY_E_INTERNAL,false);
                    case -1:
                    }
                    yy_initial = true;
                    yy_state = yy_state_dtrans[yy_lexical_state];
                    yy_next_state = YY_NO_STATE;
                    yy_last_accept_state = YY_NO_STATE;
                    yy_mark_start();
                    yy_this_accept = yy_acpt[yy_state];
                    if (YY_NOT_ACCEPT != yy_this_accept) {
                        yy_last_accept_state = yy_state;
                        yy_mark_end();
                    }
                }
            }
        }
    }
}
