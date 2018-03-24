package scanner;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private Map<String,Integer> lookup = new HashMap<>();
    private char[] userProgram;
    private int line = 1;

    public List<Triplet<String, String, Integer>> scan(String inputProgram){
        int index = 0;

        initializeClass(inputProgram);
        List<Triplet<String, String, Integer>> tokens = new ArrayList<>();

        return tokens;
    }

    private void initializeClass(String inputProgram){
        initializeLookup();
        initializeUserProgramArray(inputProgram);
    }

    private void initializeUserProgramArray(String inputProgram){
        userProgram = inputProgram.toCharArray();
    }

    private void initializeLookup(){
        //LETTER => 1
        //NUMBER => 2
        //COM_NUM => 3

        lookup.put("bool",10);
        lookup.put("string",11);
        lookup.put("int",12);
        lookup.put("float",13);
        lookup.put("color",14);
        lookup.put("shape",15);
        lookup.put("include",16);
        lookup.put("void",17);
        lookup.put("draw",18);
        lookup.put("build",19);
        lookup.put("hidden",20);
        lookup.put("if",21);
        lookup.put("else",22);
        lookup.put("while",23);
        lookup.put("true",24);
        lookup.put("false",25);
        //ID => 26
        //STRING => 27


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
        lookup.put("!",47);     //
        lookup.put("&&",48);    //X
        lookup.put("||",49);    //X
        lookup.put("++",50);    //X
        lookup.put("--",51);    //X
        lookup.put("<=",52);    //X
        lookup.put(">=",53);    //X
        lookup.put("==",54);    //X
        lookup.put("!=",55);    //
        lookup.put("+=",56);    //X
        lookup.put("-=",57);    //X
        lookup.put("*=",58);    //X
        lookup.put("/=",59);    //X

        //UNKNOWN => 99
    }

    //TODO: Færdiggør metode, og kald den!
    /**
     * INPUT: Lexeme som String.
     * OUTPUT: Token som Integer.
     * Analyserer hvilken lexeme der bliver brugt
     *
     */
    private Pair<Integer,Integer> lexicalAnalyser(int index){
        int i = index;
        int id = 0;

        i = getNextNonBlankChar(i);

        switch(userProgram[i]){
            case '(':
                id = lookup.get("(");
                break;
            case ')':
                id = lookup.get(")");
                break;
            case '.':
                id = lookup.get(".");
                break;
            case ';':                   //TODO: Mangler at tage højde for om det er inde i et for loop.
                id = lookup.get(";");
                line++;
                break;
            case '[':
                id = lookup.get("[");
                break;
            case ']':
                id = lookup.get("]");
                break;
            case '{':
                id = lookup.get("{");
                break;
            case '}':
                id = lookup.get("}");
                break;
            case ',':
                id = lookup.get(",");
                break;
            case '-':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '-')
                    id = lookup.get("--");
                else if(userProgram[i] == '=')
                    id = lookup.get("-=");
                else{
                    id = lookup.get("-");
                    i--;
                }
                break;
            case '+':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '+')
                    id = lookup.get("++");
                else if(userProgram[i] == '=')
                    id = lookup.get("+=");
                else{
                    id = lookup.get("+");
                    i--;
                }
                break;
            case '*':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '=')
                    id = lookup.get("*=");
                else{
                    id = lookup.get("*");
                    i--;
                }
                break;
            case '/':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '=')
                    id = lookup.get("/=");
                else{
                    id = lookup.get("/");
                    i--;
                }
                break;
            case '%':
                id = lookup.get("%");
                break;
            case '=':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '=')
                    id = lookup.get("==");
                else{
                    id = lookup.get("=");
                    i--;
                }
                break;
            case '<':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '<')
                    id = lookup.get("<=");
                else{
                    id = lookup.get("<");
                    i--;
                }
                break;
            case '>':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '=')
                    id = lookup.get(">=");
                else{
                    id = lookup.get(">");
                    i--;
                }
                break;
            case '!':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '=')
                    id = lookup.get("!=");
                else{
                    id = lookup.get("!");
                    i--;
                }
                break;
            case '&':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '&')
                    id = lookup.get("&&");
                else
                    throw new SyntaxException("Expected operator \"&&\"", line);
                break;
            case '|':
                i = getNextNonBlankChar(i);
                if(userProgram[i] == '|')
                    id = lookup.get("||");
                else
                    throw new SyntaxException("Expected operator \"||\"", line);
                break;
            default:
                break;
        }

        Pair<Integer,Integer> ret = new Pair<>(id,i);
        return ret;
    }

    private int getNextNonBlankChar(int index){
        int i = index;
        while(Character.isWhitespace(userProgram[i])){
            i++;
        }

        return i;
    }
}