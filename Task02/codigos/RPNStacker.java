import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import stacker.rpn.lexer.Token;
import stacker.rpn.lexer.TokenType;

public class RPNStacker {
    public static void main(String[] args) {
        try {
            Scanner usrInput = new Scanner(System.in);
            String fileName = usrInput.nextLine();

            File rpnFile = new File(fileName);

            Scanner rpnRead = new Scanner(rpnFile);

            Stack<Double> rpnStack = new Stack<>();

            List<Token> tokens = new ArrayList<>();

            String errorChar;

            System.out.println("Entrada:");
            while (rpnRead.hasNextLine()) {
                String readData = rpnRead.nextLine();
                Token getToken;

                System.out.println(readData);

                if (readData.equals("+")) {
                    getToken = new Token(TokenType.PLUS, readData);
                } else if (readData.equals("-")) {
                    getToken = new Token(TokenType.MINUS, readData);
                } else if (readData.equals("*")) {
                    getToken = new Token(TokenType.STAR, readData);
                } else if (readData.equals("/")) {
                    getToken = new Token(TokenType.SLASH, readData);
                } else if (checkNumeric(readData)) {
                    getToken = new Token(TokenType.NUM, readData);
                } else {
                    getToken = new Token(TokenType.EOF, readData);
                }
                tokens.add(getToken);
            }

            rpnRead.close();

            System.out.println("-------------\nTokens gerados:");

            try {
                for (Token tokenOut : tokens) {
                    System.out.println(tokenOut);

                    if (tokenOut.type.equals(TokenType.NUM)) {
                        rpnStack.push(Double.parseDouble(tokenOut.lexeme));
                    } else if (tokenOut.type.equals(TokenType.EOF)) {
                        throw new EOFException("Erro - Caractere não válido: " + tokenOut.lexeme);
                    } else {
                        double oper2 = rpnStack.pop();
                        double oper1 = rpnStack.pop();

                        switch (tokenOut.lexeme) {
                            case "+" -> rpnStack.push(oper1 + oper2);
                            case "-" -> rpnStack.push(oper1 - oper2);
                            case "*" -> rpnStack.push(oper1 * oper2);
                            case "/" -> rpnStack.push(oper1 / oper2);
                            case "^" -> rpnStack.push(Math.pow(oper1, oper2));
                        }
                    }
                }

                double resultado = rpnStack.pop();
                System.out.println("-------------\nResultado:\n" + resultado);
            } catch (EOFException eofExc) {
                System.out.println(eofExc.getMessage());
            }
        } catch (FileNotFoundException fnfExcpt) {
            System.out.println("Algo de errado não está certo o.O");
            fnfExcpt.printStackTrace();
        }
    }

    public static boolean checkNumeric(String inputStr) {
        if (inputStr == null) {
            return false;
        }
        try {
            Integer.parseInt(inputStr);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
