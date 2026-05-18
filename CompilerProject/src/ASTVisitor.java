import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

public class ASTVisitor extends TinyParserBaseVisitor<String> {

    @Override
    public String visitProgram(TinyParser.ProgramContext ctx) {
        String result = "";
        for (TinyParser.StatementContext stmt : ctx.statement()) {
            result += visit(stmt) + "\n";
        }
        return "(program\n" + result + ")";
    }

    @Override
    public String visitAssignment(TinyParser.AssignmentContext ctx) {
        return "(assign " + ctx.ID().getText() + " " +
                visit(ctx.expression()) + ")";
    }

    @Override
    public String visitIfStatement(TinyParser.IfStatementContext ctx) {
        String elsePart = "";
        if (ctx.ELSE() != null) {
            elsePart = " (else " + visit(ctx.block(1)) + ")";
        }

        return "(if " +
                visit(ctx.expression()) + " " +
                visit(ctx.block(0)) +
                elsePart +
                ")";
    }

    @Override
    public String visitExpression(TinyParser.ExpressionContext ctx) {
        if (ctx.INT() != null)
            return ctx.INT().getText();

        if (ctx.ID() != null)
            return ctx.ID().getText();

        if (ctx.STRING() != null)
            return ctx.STRING().getText();

        return visitChildren(ctx);
    }
}