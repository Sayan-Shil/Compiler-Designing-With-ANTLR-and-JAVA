package app;

import antlr.ExprLexer;
import antlr.ExprParser;
import expression.AntlrToProgram;
import expression.ExpressionProcessor;
import expression.MySyntaxErrorListener;
import expression.Program;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class ExpressionApp {
    static void main(String[] args) {
        if(args.length !=1){
            System.err.println("Usage: file name ");
        } else{
            String fileName= args[0];
            ExprParser parser = getParser(fileName);

            //  Tell Antlr to parse tree
            // parge from start symbol prog

            ParseTree antlrAST = parser.prog();

            if(MySyntaxErrorListener.hasError){

                /* Syntax Error IS Here */


            } else{

                // Create a visitor for converting ParseTree to Expression/Program Object
                AntlrToProgram progVisitor = new AntlrToProgram();
                Program prog = progVisitor.visit(antlrAST);

                if(progVisitor.semanticErrors.isEmpty()){
                    ExpressionProcessor processor = new ExpressionProcessor(prog.expressions);
                    for(String evaluation : processor.getEvaluationResults()){
                        System.out.println(evaluation);
                    }

                } else{
                    for(String err : progVisitor.semanticErrors){
                        System.err.println(err);
                    }
                }

            }


        }
    }

    public static ExprParser getParser(String filename){
        ExprParser parser = null;

        try {
            CharStream input = CharStreams.fromFileName(filename);
            ExprLexer lexer = new ExprLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            parser = new ExprParser(tokens);

            //Syntax Error Handler
            parser.addErrorListener(new MySyntaxErrorListener());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parser;
    }
}
