package restquery.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import restquery.drivers.ElasticSearchDriver;
import restquery.parser.antlr.LanguageParser;
import restquery.parser.dtos.LanguageBuilt;
import restquery.parser.visitors.LanguageVisitor;
import restquery.parser.antlr.LanguageLexer;

import java.io.IOException;


public class LangNtlr {


    public static void main(String args[]) throws IOException {
        String value = "name.containsAll(marcelo maico de jesus)"
                + ".and(description.contains(\"ola mundo\"))"
                + ".and(age.gt(40)).or(tradingName.contains(company*))";

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
