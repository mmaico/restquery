package restquery.helpers;


import restquery.parser.dtos.Expression;
import restquery.parser.dtos.LogicalExpression;

import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.startsWith;

public class ExpressionUtils {

    public static Boolean isLogicalExpression(Expression expression) {
        return expression instanceof LogicalExpression;
    }

    public static Boolean isPhraseQuery(String value) {
        return startsWith(value, "\"") && endsWith(value, "\"");
    }

    public static Boolean isPrefixQuery(String value) {
        return value.contains("*");
    }
}
