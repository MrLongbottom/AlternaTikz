package scanner;

public class SyntaxException extends Exception {
    int line;
    public SyntaxException(String message, int line){
        super(message);
        this.line = line;
    }
}
