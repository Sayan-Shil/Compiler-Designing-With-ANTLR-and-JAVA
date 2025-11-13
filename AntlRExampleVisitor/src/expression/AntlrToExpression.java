package expression;

import antlr.ExprBaseVisitor;
import antlr.ExprParser;
import org.antlr.v4.runtime.Token;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AntlrToExpression extends ExprBaseVisitor<Expression> {

    /*
    Given All The visit_* methods are in a top-down fashion
    we can be assured the order in which we add declared variables in the `vars`
    is identical to how they appeared
    */


    private Set<String> vars; // stores all the variable declared in the variable
    private List<String> semanticErrors; // 1. Same Variable Declared 2. Type not defined

    public AntlrToExpression(List<String> semanticErrors){
        this.semanticErrors = semanticErrors;
        vars=new HashSet<>();
    }

    @Override
    public Expression visitDeclaration(ExprParser.DeclarationContext ctx) {
        // ID() is method corresponding to the
        Token idToken = ctx.ID().getSymbol();
        int line = idToken.getLine();
        int column = idToken.getCharPositionInLine();

        String id  = ctx.getChild(0).getText();
        if(vars.contains(id)){
          semanticErrors.add("Error: Variable "+id+ " is already defined  (" +line + " Column " +column+ " )");
        }else {
            vars.add(id);
        }
        String type = ctx.getChild(0).getText();
        int value= Integer.parseInt(ctx.NUM().getText());

        return new VariableDeclaration(id,type,value);

    }

    @Override
    public Expression visitMultiplication(ExprParser.MultiplicationContext ctx) {
        Expression left = visit(ctx.getChild(0)); //Recursively visit left subtree of current context
        Expression right = visit(ctx.getChild(2)); //Recursively visit right subtree of current context
        return new Multiplication(left,right);
    }

    @Override
    public Expression visitAddition(ExprParser.AdditionContext ctx) {
        Expression left = visit(ctx.getChild(0)); //Recursively visit left subtree of current context
        Expression right = visit(ctx.getChild(2)); //Recursively visit right subtree of current context
        return new Addition(left,right);
    }

    @Override
    public Expression visitVariable(ExprParser.VariableContext ctx) {
        Token idToken = ctx.ID().getSymbol();
        int line = idToken.getLine();
        int column = idToken.getCharPositionInLine();

        String id= ctx.getChild(0).getText();
        if(!vars.contains(id)){
            semanticErrors.add("Oops ! Variable Not Declared ( " + line+ "\n Column "+column + ")");
        }

        return new Variable(id);
    }

    @Override
    public Expression visitNumber(ExprParser.NumberContext ctx) {
        String numText = ctx.getChild(0).getText();

        return new Number(Integer.parseInt(numText));
    }
}
