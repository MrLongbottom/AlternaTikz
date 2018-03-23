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

        lookup.put("(",30);
        lookup.put(")",31);
        lookup.put(".",32);
        lookup.put(";",33);
        lookup.put("[",34);
        lookup.put("]",35);
        lookup.put("{",36);
        lookup.put("}",37);
        lookup.put(",",38);
        lookup.put("-",39);
        lookup.put("+",40);
        lookup.put("*",41);
        lookup.put("/",42);
        lookup.put("%",43);
        lookup.put("&&",44);
        lookup.put("||",45);
        lookup.put("++",46);
        lookup.put("--",47);
        lookup.put("<=",48);
        lookup.put(">=",49);
        lookup.put("==",50);
        lookup.put("!=",51);
        lookup.put("+=",52);
        lookup.put("-=",53);
        lookup.put("*=",54);
        lookup.put("/=",55);
        lookup.put("=",56);

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
                id = lookup.get('(');
                break;
            case ')':
                id = lookup.get(')');
                break;
            case '.':
                id = lookup.get('.');
                break;
            case ';':
                id = lookup.get(';');
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