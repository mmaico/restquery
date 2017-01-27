package restquery.parser.visitors;

import restquery.parser.antlr.LanguageBaseVisitor;
import restquery.parser.antlr.LanguageParser;


public class ValueVisitor extends LanguageBaseVisitor <String> {


    @Override public String visitValue(LanguageParser.ValueContext ctx) {
        return ctx.getText();
    }


}
