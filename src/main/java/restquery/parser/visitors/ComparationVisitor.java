package restquery.parser.visitors;

import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.enums.ComparationOperator;

import static restquery.helpers.ExceptionMessageUtils.message;
import static restquery.parser.enums.ComparationOperator.contains;


public class ComparationVisitor extends LanguageBaseVisitor <ComparationOperator> {


    @Override public ComparationOperator visitComparation(LanguageParser.ComparationContext ctx) {
        if (!contains(ctx.getText())) {
            throw new RuntimeException(message(ctx.getStart().getText(), ComparationOperator.getOptions()));
        }
        String comparation = ctx.getText();
        return ComparationOperator.get(comparation);
    }

}
