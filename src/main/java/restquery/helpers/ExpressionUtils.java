package restquery.helpers;


import restquery.parser.dtos.Expression;
import restquery.parser.dtos.LogicalExpression;

public class ExpressionUtils {

    public static Boolean isLogicalExpression(Expression expression) {
        return expression instanceof LogicalExpression;
    }
}
