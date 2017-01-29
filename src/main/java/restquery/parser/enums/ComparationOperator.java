package restquery.parser.enums;


import com.google.common.collect.Lists;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public enum ComparationOperator {
    EQ("eq"), GT("gt"), LT("lt"), GTE("gte"), LTE("lte"), CONTAINS("contains"), CONTAINS_ALL("containsAll");

    private String comparation;

    ComparationOperator(String comparation) {
        this.comparation = comparation;
    }

    public static ComparationOperator get(String comparation) {
        Optional<ComparationOperator> comparationOperator = newArrayList(values())
                .stream().filter(item -> item.comparation.equalsIgnoreCase(comparation))
                .findFirst();
        return comparationOperator.get();
    }

    public static Boolean contains(String comparation) {
        return Lists.newArrayList(values()).stream()
                .filter(item -> item.comparation.equalsIgnoreCase(comparation)).findFirst().isPresent();
    }

    public static String getOptions() {
        return newArrayList(values()).stream().map(operator -> operator.comparation)
                .collect(Collectors.toList()).toString();

    }

}
