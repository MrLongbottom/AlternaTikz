package scanner;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private Map<String,Integer> lookup = new HashMap<>();
    private char[] userProgram;
    private int line = 1;
    private int index;

    /**TODO: Mangler at blive testet.
     * INPUT: Hele brugerprogrammet som skal tokeniseres.
     * OUTPUT: Hele brugerprogrammet i form af tokens.
     * Tokeniserer hele brugerprogrammet.
     */
    public List<Triplet<Integer, String, Integer>> scan(String inputProgram){
        index = 0;
        List<Triplet<Integer, String, Integer>> tokens = new ArrayList<>();

        initializeClass(inputProgram);

        while(userProgram.length != index + 1)
            tokens.add(lexer());

        return tokens;
    }

    /**TODO: Mangler at blive testet.
     * INPUT: Ø.
     * OUTPUT: Ø.
     * Hvis en SyntaxException bliver fanget kan denne metode kaldes for at fortsætte lexeren.
     */
    public void errorRecovery(){
        while(userProgram[index] != ';')
            getNextNonBlankChar();
        getNextNonBlankChar();
    }

    /**TODO: Mangler at blive testet.
     * INPUT: Ø.
     * OUTPUT: Ø.
     * Initialiserer alt der skal bruges i klassen.
     */
    private void initializeClass(String inputProgram){
        initializeLookup();
        initializeUserProgramArray(inputProgram);
    }

    /**TODO: Mangler at blive testet.
     * INPUT: Ø.
     * OUTPUT: Ø.
     * Initialiserer userProgram.
     */
    private void initializeUserProgramArray(String inputProgram){
        userProgram = inputProgram.toCharArray();
    }

    /**TODO: Mangler at blive testet.
     * INPUT: Ø.
     * OUTPUT: Ø.
     * Initialiserer lookup.
     */
    private void initializeLookup(){
        //LETTER => 1           //X
        //NUMBER => 2           //X
        //COM_NUM => 3          //X

        lookup.put("bool",10);  //X
        lookup.put("string",11);//X
        lookup.put("int",12);   //X
        lookup.put("float",13); //X
        lookup.put("color",14); //X
        lookup.put("shape",15); //X
        lookup.put("include",16);//X
        lookup.put("void",17);  //X
        lookup.put("draw",18);  //X
        lookup.put("build",19); //X
        lookup.put("hidden",20);//X
        lookup.put("if",21);    //X
        lookup.put("else",22);  //X
        lookup.put("while",23); //X
        lookup.put("true",24);  //X
        lookup.put("false",25); //X
        //ID => 26              //X
        //STRING => 27          //

        lookup.put("=",30);     //X
        lookup.put("(",31);     //X
        lookup.put(")",32);     //X
        lookup.put(".",33);     //X
        lookup.put(";",34);     //X
        lookup.put("[",35);     //X
        lookup.put("]",36);     //X
        lookup.put("{",37);     //X
        lookup.put("}",38);     //X
        lookup.put(",",39);     //X
        lookup.put("-",40);     //X
        lookup.put("+",41);     //X
        lookup.put("*",42);     //X
        lookup.put("/",43);     //X
        lookup.put("%",44);     //X
        lookup.put("<",45);     //X
        lookup.put(">",46);     //X
        lookup.put("!",47);     //X
        lookup.put("&&",48);    //X
        lookup.put("||",49);    //X
        lookup.put("++",50);    //X
        lookup.put("--",51);    //X
        lookup.put("<=",52);    //X
        lookup.put(">=",53);    //X
        lookup.put("==",54);    //X
        lookup.put("!=",55);    //X
        lookup.put("+=",56);    //X
        lookup.put("-=",57);    //X
        lookup.put("*=",58);    //X
        lookup.put("/=",59);    //X

        //EOF => 98             //X
        //UNKNOWN => 99         //X
    }

    /**TODO: Mangler håndtering af strings og fejl, skal også testes.
     * INPUT: Ø, men bruger userProgram og index.
     * OUTPUT: Token som Integer.
     * Analyserer hvilken lexeme der bliver brugt
     */
    private Triplet<Integer, String, Integer> lexer(){
        int id = 98, charClass;
        String lex = "";

        getNextNonBlankChar();
        charClass = charClassGetter();

        switch(charClass){
            case 1:
                lex = String.valueOf(userProgram[index]);
                getNextNonBlankChar();
                charClass = charClassGetter();
                while(charClass == 1 || charClass == 2){
                    lex += String.valueOf(userProgram[index]);
                }
                id = lookupMethod(lex);
                if(id == 98)
                    id = 26;
                break;
            case 2:
                boolean dotFound = false;
                lex = String.valueOf(userProgram[index]);
                getNextNonBlankChar();
                charClass = charClassGetter();
                while(charClass == 2 || userProgram[index] == '.'){
                    if(userProgram[index] == '.')
                        dotFound = true;
                    if(dotFound == true)
                        throw new SyntaxException("Digit expected instead of '.'", line);
                    lex += String.valueOf(userProgram[index]);
                }
                if(dotFound == false)
                    id = 2;
                if(dotFound == true)
                    id = 3;
                break;
            case 99:
                index--;
                id = lookupMethod(String.valueOf(userProgram[index]));
                break;
        }
        return new Triplet<>(id,lex,line);
    }

    /**TODO: Mangler at blive testet.
     * INPUT: Ø.
     * OUTPUT: hvilken form for char der undersøges for.
     * Undersøger hvilken char det er.
     */
    private int charClassGetter(){
        int charClass;
        if(Character.isLetter(userProgram[index]))
            charClass = 1;
        else if(Character.isDigit(userProgram[index]))
            charClass = 2;
        else
            charClass = 99;
        return charClass;
    }

    /**TODO: Tjek om der er flere fejl som skal håndteres, og test metoden.
     * INPUT: Ø, men bruger userProgram og index.
     * OUTPUT: Token som Integer.
     * Finder hvilken lexeme der bliver brugt
     */
    private int lookupMethod(String lex){
        int id;

        getNextNonBlankChar();

        switch(lex){
            case "bool":
                id = lookup.get("bool");
                break;
            case "string":
                id = lookup.get("string");
                break;
            case "int":
                id = lookup.get("int");
                break;
            case "float":
                id = lookup.get("float");
                break;
            case "color":
                id = lookup.get("color");
                break;
            case "shape":
                id = lookup.get("shape");
                break;
            case "include":
                id = lookup.get("include");
                break;
            case "void":
                id = lookup.get("void");
                break;
            case "draw":
                id = lookup.get("draw");
                break;
            case "build":
                id = lookup.get("build");
                break;
            case "hidden":
                id = lookup.get("hidden");
                break;
            case "if":
                id = lookup.get("string");
                break;
            case "else":
                id = lookup.get("string");
                break;
            case "while":
                id = lookup.get("string");
                break;
            case "true":
                id = lookup.get("string");
                break;
            case "false":
                id = lookup.get("string");
                break;
            case "(":
                id = lookup.get("(");
                break;
            case ")":
                id = lookup.get(")");
                break;
            case ".":
                id = lookup.get(".");
                break;
            case ";":                   //TODO: Tjek om AlternaTikz har for loops.
                id = lookup.get(";");
                line++;
                break;
            case "[":
                id = lookup.get("[");
                break;
            case "]":
                id = lookup.get("]");
                break;
            case "{":
                id = lookup.get("{");
                break;
            case "}":
                id = lookup.get("}");
                break;
            case ",":
                id = lookup.get(",");
                break;
            case "-":
                getNextNonBlankChar();
                if(userProgram[index] == '-')
                    id = lookup.get("--");
                else if(userProgram[index] == '=')
                    id = lookup.get("-=");
                else{
                    id = lookup.get("-");
                    index--;
                }
                break;
            case "+":
                getNextNonBlankChar();
                if(userProgram[index] == '+')
                    id = lookup.get("++");
                else if(userProgram[index] == '=')
                    id = lookup.get("+=");
                else{
                    id = lookup.get("+");
                    index--;
                }
                break;
            case "*":
                getNextNonBlankChar();
                if(userProgram[index] == '=')
                    id = lookup.get("*=");
                else{
                    id = lookup.get("*");
                    index--;
                }
                break;
            case "/":
                getNextNonBlankChar();
                if(userProgram[index] == '=')
                    id = lookup.get("/=");
                else{
                    id = lookup.get("/");
                    index--;
                }
                break;
            case "%":
                id = lookup.get("%");
                break;
            case "=":
                getNextNonBlankChar();
                if(userProgram[index] == '=')
                    id = lookup.get("==");
                else{
                    id = lookup.get("=");
                    index--;
                }
                break;
            case "<":
                getNextNonBlankChar();
                if(userProgram[index] == '<')
                    id = lookup.get("<=");
                else{
                    id = lookup.get("<");
                    index--;
                }
                break;
            case ">":
                getNextNonBlankChar();
                if(userProgram[index] == '=')
                    id = lookup.get(">=");
                else{
                    id = lookup.get(">");
                    index--;
                }
                break;
            case "!":
                getNextNonBlankChar();
                if(userProgram[index] == '=')
                    id = lookup.get("!=");
                else{
                    id = lookup.get("!");
                    index--;
                }
                break;
            case "&":
                getNextNonBlankChar();
                if(userProgram[index] == '&')
                    id = lookup.get("&&");
                else
                    throw new SyntaxException("Expected operator \"&&\"", line);
                break;
            case "|":
                getNextNonBlankChar();
                if(userProgram[index] == '|')
                    id = lookup.get("||");
                else
                    throw new SyntaxException("Expected operator \"||\"", line);
                break;
            default:
                id = 98;
                break;
        }

        return id;
    }

    /**TODO: Mangler at blive testet.
     * INPUT: Ø.
     * OUTPUT: Ø.
     * tæller index op indtil en ny char mødes der ikke er whitespace.
     */
    private void getNextNonBlankChar(){
        while(Character.isWhitespace(userProgram[index])){
            index++;
        }
    }
}