package restquery.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import restquery.drivers.ElasticSearchDriver;
import restquery.parser.antlr.LanguageLexer;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.dtos.LanguageBuilt;
import restquery.parser.visitors.LanguageVisitor;

import java.io.IOException;


public class LangNtlr {


    public static void main(String args[]) throws IOException {
        String value = "name.contains(marcelo*).and(age.gt(40)).or(tradingName.eq(company*))";
        System.out.println("FROM: " + value);
        ANTLRInputStream input  = new ANTLRInputStream(value);

        LanguageLexer lexer = new LanguageLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        LanguageParser parser = new LanguageParser(tokens);
        parser.removeErrorListeners();
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
        LanguageVisitor lang = new LanguageVisitor();
        LanguageBuilt languageBuilt = lang.visitLanguage(parser.language());

        String query = ElasticSearchDriver.build(languageBuilt);

        System.out.println("TO: " + query);

    }
}
