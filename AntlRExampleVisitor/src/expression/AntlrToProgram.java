package expression;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;

import java.util.ArrayList;
import java.util.List;

public class AntlrToProgram extends ExprBaseVisitor<Program> {

    private List<String> semanticErrors; // to be accessed by main access program



    @Override
    public Program visitProgram(ExprParser.ProgramContext ctx) {
        Program prog = new Program();
        semanticErrors = new ArrayList<>();

        AntlrToExpression exprVisitor = new AntlrToExpression(semanticErrors); // A Helper Visitor
        for (int i = 0; i < ctx.getChildCount() ; i++) {

            if(i== ctx.getChildCount()-1){
                /* End Of Line */
                /* Do Not Visit Child And COnvert to Expression */
            } else{
                prog.addExpression(exprVisitor.visit(ctx.getChild(i)));
            }


        }



        return prog;
    }
}
