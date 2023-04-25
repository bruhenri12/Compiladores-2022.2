import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import stacker.rpn.lexer.Regex;
import stacker.rpn.lexer.Token;
import stacker.rpn.lexer.TokenType;

public class RPNStacker {
    public static void main(String[] args) {
        try {
            Scanner usrInput = new Scanner(System.in);
            String fileName = usrInput.nextLine();
            usrInput.close();

            File rpnFile = new File(fileName);

            Scanner rpnRead = new Scanner(rpnFile);

            Stack<Double> rpnStack = new Stack<>();

            List<Token> tokens = new ArrayList<>();

            System.out.println("Entrada:");
            try {
                while (rpnRead.hasNextLine()) {
                    String readData = rpnRead.nextLine();
                    System.out.println(readData);
                    TokenType token;

                    if (!(Regex.isNum(readData) ||  Regex.isOP(readData))) {
                        rpnRead.close();
                        throw new EOFException("Erro - Caractere não válido: " + readData);
                    } else if (Regex.isNum(readData)) {
                        token = TokenType.NUM;
                    } else {
                        token = Regex.getTokenType(readData);
                    }

                    Token getToken = new Token(token, readData);
                    tokens.add(getToken);
                }

                rpnRead.close();

                System.out.println("-------------\nTokens gerados:");

                for (Token tokenOut : tokens) {
                    System.out.println(tokenOut);

                    if (tokenOut.type.equals(TokenType.NUM)) {
                        rpnStack.push(Double.parseDouble(tokenOut.lexeme));
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
                System.out.println("-------------\nResultado: " + resultado);
            } catch (EOFException eofExc) {
                System.out.println(eofExc.getMessage());
            }
            
        } catch (FileNotFoundException fnfExcpt) {
            System.out.println("Algo de errado não está certo o.O");
            fnfExcpt.printStackTrace();
        }
    }
}
