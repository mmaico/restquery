package restquery.drivers;


import com.google.common.collect.Lists;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MatchQuery;
import restquery.helpers.ExpressionUtils;
import restquery.parser.dtos.Expression;
import restquery.parser.dtos.LanguageBuilt;
import restquery.parser.dtos.LogicalExpression;
import restquery.parser.enums.ComparationOperator;
import restquery.parser.enums.LogicalOperator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static restquery.helpers.ExpressionUtils.isLogicalExpression;
import static restquery.helpers.ExpressionUtils.isPhraseQuery;
import static restquery.helpers.ExpressionUtils.isPrefixQuery;
import static restquery.parser.enums.ComparationOperator.*;
import static restquery.parser.enums.LogicalOperator.*;

public class ElasticSearchDriver {

    private final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    private final LanguageBuilt languageBuilt;

    private final Map<LogicalOperator, LogicalExpressionConverter> operator = new HashMap<>();
       {
           operator.put(OR, baseQueryBuilder -> boolQuery.should(baseQueryBuilder));
           operator.put(AND, baseQueryBuilder -> boolQuery.must(baseQueryBuilder));
           operator.put(NOT, baseQueryBuilder -> boolQuery.mustNot(baseQueryBuilder));
       }

    private final Map<ComparationOperator, ComparationOperatorConverter> comparations = new HashMap<>();
       {
           comparations.put(CONTAINS, expression -> {
               MatchQueryBuilder queryBuilder;

               if (isPhraseQuery(expression.getValue())) {
                   queryBuilder = QueryBuilders.matchPhraseQuery(expression.getAttribute(), expression.getValue());
               } else {
                   if (isPrefixQuery(expression.getValue())) {
                       queryBuilder = QueryBuilders.matchPhrasePrefixQuery(expression.getAttribute(), expression.getValue());
                   } else {
                       queryBuilder = QueryBuilders.matchQuery(expression.getAttribute(), expression.getValue())
                               .operator(MatchQueryBuilder.Operator.OR);
                   }
               }

               if (isLogicalExpression(expression)) {
                   LogicalExpression logicalExpression = (LogicalExpression) expression;
                   operator.get(logicalExpression.getLogical()).execute(queryBuilder);
               } else {
                   boolQuery.must(queryBuilder);
               }
           });

           comparations.put(CONTAINS_ALL, expression -> {
               MatchQueryBuilder matchQuery = QueryBuilders
                       .matchQuery(expression.getAttribute(), expression.getValue())
                        .operator(MatchQueryBuilder.Operator.AND);

               if (isLogicalExpression(expression)) {
                   LogicalExpression logicalExpression = (LogicalExpression) expression;
                   operator.get(logicalExpression.getLogical()).execute(matchQuery);
               } else {
                   boolQuery.must(matchQuery);
               }
           });

           comparations.put(EQ, expression -> {
               MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(expression.getAttribute(), expression.getValue());
               if (isLogicalExpression(expression)) {
                   LogicalExpression logicalExpression = (LogicalExpression) expression;
                   operator.get(logicalExpression.getLogical()).execute(matchQueryBuilder);
               } else {
                   boolQuery.must(matchQueryBuilder);
               }
           });

           comparations.put(GTE, expression -> {
               RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(expression.getAttribute()).gte(expression.getValue());
               if (isLogicalExpression(expression)) {
                   LogicalExpression logicalExpression = (LogicalExpression) expression;
                   operator.get(logicalExpression.getLogical()).execute(rangeQueryBuilder);
               } else {
                   boolQuery.must(rangeQueryBuilder);
               }
           });

           comparations.put(LTE, expression -> {
               RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(expression.getAttribute()).lte(expression.getValue());
               if (isLogicalExpression(expression)) {
                   LogicalExpression logicalExpression = (LogicalExpression) expression;
                   operator.get(logicalExpression.getLogical()).execute(rangeQueryBuilder);
               } else {
                   boolQuery.must(rangeQueryBuilder);
               }
           });

           comparations.put(GT, expression -> {
               RangeQueryBuilder gtBuilder = QueryBuilders.rangeQuery(expression.getAttribute()).gt(expression.getValue());
               if (isLogicalExpression(expression)) {
                   LogicalExpression logicalExpression = (LogicalExpression) expression;
                   operator.get(logicalExpression.getLogical()).execute(gtBuilder);
               } else {
                   boolQuery.must(gtBuilder);
               }
           });

           comparations.put(LT, expression -> {
               RangeQueryBuilder ltBuilder = QueryBuilders.rangeQuery(expression.getAttribute()).lt(expression.getValue());
               if (isLogicalExpression(expression)) {
                   LogicalExpression logicalExpression = (LogicalExpression) expression;
                   operator.get(logicalExpression.getLogical()).execute(ltBuilder);
               } else {
                   boolQuery.must(ltBuilder);
               }
           });
       }

    public ElasticSearchDriver(LanguageBuilt languageBuilt) {
        this.languageBuilt = languageBuilt;
    }

    public String getElasticQuery() {
        List<Expression> expressions = Lists.newArrayList(languageBuilt.getFirstExpression());
        expressions.addAll(languageBuilt.getLogicalExpressions());
        expressions.stream()
                .forEach(expression -> comparations.get(expression.getComparationOperator()).execute(expression));

        return boolQuery.toString();
    }

    public static String build(LanguageBuilt languageBuilt) {
        return new ElasticSearchDriver(languageBuilt).getElasticQuery();
    }

    private interface ComparationOperatorConverter {
        void execute(Expression context);
    }

    private interface LogicalExpressionConverter {
        void execute(BaseQueryBuilder baseQueryBuilder);
    }

}
