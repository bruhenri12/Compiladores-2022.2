import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class RPNStacker {
    public static void main(String[] args) {
        try {
            System.out.println("Insira o caminho do arquivo teste:");
            Scanner usrInput = new Scanner(System.in);
            String fileName = usrInput.nextLine();

            File rpnFile = new File(fileName);

            Scanner rpnRead = new Scanner(rpnFile);

            Stack<Double> pilha = new Stack<>();

            System.out.println("Entrada:");
            while (rpnRead.hasNextLine()) {
                String readData = rpnRead.nextLine();

                System.out.println(readData);

                if (checkNumeric(readData)){
                    pilha.push(Double.parseDouble(readData));
                } else {
                    double operando2 = pilha.pop();
                    double operando1 = pilha.pop();

                    switch (readData) {
                        case "+" -> pilha.push(operando1 + operando2);
                        case "-" -> pilha.push(operando1 - operando2);
                        case "*" -> pilha.push(operando1 * operando2);
                        case "/" -> pilha.push(operando1 / operando2);
                        case "^" -> pilha.push(Math.pow(operando1, operando2));
                    }
                }
            }
            rpnRead.close();
            double resultado = pilha.pop();
            System.out.println(resultado);
        } catch (FileNotFoundException excpt) {
            System.out.println("Algo de errado não está certo o.O");
            excpt.printStackTrace();
        }
    }

    public static boolean checkNumeric(String inputStr) {
        if (inputStr == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(inputStr);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}