package stacker.rpn.lexer;

import java.util.Arrays;
import java.util.Map;

public class Regex {
    private static final String REGEX_NUM = "(\\d)+";
    private static final String REGEX_PLUS = "(\\+)";
    private static final String REGEX_MINUS = "(\\-)";
    private static final String REGEX_MULT = "(\\*)";
    private static final String REGEX_DIV = "(/)";

    private static final String[] REGEX_TYPES = {
        REGEX_NUM, REGEX_PLUS, REGEX_MINUS, REGEX_MULT, REGEX_DIV
    };

    private static final Map<String, TokenType> MAP_REGEX = Map.ofEntries(
            Map.entry(REGEX_NUM, TokenType.NUM),
            Map.entry(REGEX_PLUS, TokenType.PLUS),
            Map.entry(REGEX_MINUS, TokenType.MINUS),
            Map.entry(REGEX_MULT, TokenType.STAR),
            Map.entry(REGEX_DIV, TokenType.SLASH)
    );

    private static String regexType(String token) {
        return Arrays.stream(REGEX_TYPES)
                .filter(token::matches).findFirst()
                .orElse(null);
    }

    public static TokenType getTokenType(String token) {
        String regexType = regexType(token);
        return MAP_REGEX.get(regexType);
    }

    public static boolean isNum(String token) {
        return token.matches(REGEX_NUM);
    }

    public static boolean isOP(String token) {
        return token.matches(REGEX_PLUS) || 
                token.matches(REGEX_MINUS) || 
                token.matches(REGEX_MULT) || 
                token.matches(REGEX_DIV);
    }
}
