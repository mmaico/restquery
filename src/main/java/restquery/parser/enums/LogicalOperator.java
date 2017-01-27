package restquery.parser.enums;


import com.google.common.collect.Lists;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public enum LogicalOperator {
    AND("and"), OR("or"), NOT("not");

    private String logical;

    LogicalOperator(String logical) {
        this.logical = logical;
    }

    public static LogicalOperator get(String logical) {
        Optional<LogicalOperator> result = Lists.newArrayList(values()).stream()
                .filter(item -> item.logical.equalsIgnoreCase(logical)).findFirst();
        return result.get();
    }

    public static Boolean contains(String logical) {
        return Lists.newArrayList(values()).stream()
                .filter(item -> item.logical.equalsIgnoreCase(logical)).findFirst().isPresent();
    }

    public static String getOptions() {
        return newArrayList(values()).stream().map(operator -> operator.logical)
                .collect(Collectors.toList()).toString();

    }
}
