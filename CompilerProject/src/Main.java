import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws Exception {

        String inputFile = "test.tiny";

        InputStream is = new FileInputStream(inputFile);

        CharStream input = CharStreams.fromStream(is);

        // LEXICAL ANALYSIS (PA1)

        TinyLexer lexer = new TinyLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        tokens.fill();
        System.out.println("===== TOKENS =====");
        for (Token token : tokens.getTokens()) {
            String tokenName = TinyLexer.VOCABULARY.getSymbolicName(token.getType());
            if (tokenName != null) {
                System.out.println(token.getText() + " -> " + tokenName);
            }
        }


        // PARSING (PA2)
        TinyParser parser = new TinyParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer,
                                    Object offendingSymbol,
                                    int line,
                                    int charPositionInLine,
                                    String msg,
                                    RecognitionException e) {
                System.err.println("Syntax Error at line " + line + ": " + msg);
                System.exit(1);
            }
        });

        ParseTree tree = parser.program();

        // AST OUTPUT
        System.out.println("\n===== AST =====");

        ASTVisitor visitor = new ASTVisitor();
        String ast = visitor.visit(tree);

        System.out.println(ast);
    }
}