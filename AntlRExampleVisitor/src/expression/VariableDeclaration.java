package expression;

public class VariableDeclaration extends Expression {
    public String id;
    public String type;
    public int value;

    public VariableDeclaration(String id, String type, int value){
        this.value=value;
        this.id=id;
        this.type =type;
    }

    @Override
    public String toString() {
        return id;
    }

}
