import com.sun.jdi.StackFrame;

import java.util.*;

class EquationParser {
    private String equation;
    private ArrayList<String> operatorList = new ArrayList<String>();

    EquationParser(String equation) {
        this.equation = equation;
        operatorList.add("+");
        operatorList.add("-");
        operatorList.add("*");
        operatorList.add("/");
        operatorList.add(")");
        operatorList.add("(");
    }

    public static void main(String[] args) {
        String equation = "986-(8)";
        EquationParser p = new EquationParser(equation);
        System.out.println(p.generateRPN());
        System.out.println(p.evaluateRPN(p.generateRPN()));
    }

    public int getPrecedence(Character currentChar) {
        if(currentChar == '+' || currentChar == '-' || currentChar == '/' || currentChar == '*') {
            return 1;
        }
        else {
            return -1;
        }
    }

    public Queue<String> generateRPN() {
        Queue<String> stringQueue = new LinkedList<String>();
        Stack<Character> operatorStack = new Stack<Character>();
        for (int i = 0; i < equation.length(); i++) {
            Character currentChar = equation.charAt(i);
            //System.out.println(currentChar + "     " + operatorStack + "      " + stringQueue);
                if(getPrecedence(currentChar) == 1) {
                    if (operatorStack.isEmpty()) {
                        operatorStack.push(currentChar);
                    } else {
                        if (getPrecedence(operatorStack.peek()) == 1) {
                            stringQueue.add(Character.toString(operatorStack.pop()));
                            operatorStack.push(currentChar);
                        }
                        else {
                            operatorStack.push(currentChar);
                        }
                    }
                }
            else if(currentChar == '(') {
                    operatorStack.push(currentChar);
                }
            else if (currentChar == ')') {
                while(operatorStack.peek() != '(') {
                    stringQueue.add(Character.toString(operatorStack.pop()));
                }
                operatorStack.pop();
            } else {
                Map<String, String> returnedMap = extractNumber(i);
                stringQueue.add(returnedMap.get("extractedNumber"));
                i = Integer.parseInt(returnedMap.get("currentIndex")) - 1;
            }
        }
        while (!operatorStack.empty()) {
            stringQueue.add(Character.toString(operatorStack.pop()));
        }
        return stringQueue;
    }

    public Map<String, String> extractNumber(int currentIndex) {
        String number = "";
        while(currentIndex < equation.length() && !operatorList.contains(Character.toString(equation.charAt(currentIndex)))) {
            number = number + equation.charAt(currentIndex);
            currentIndex++;
        }
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("extractedNumber", number);
        returnMap.put("currentIndex", Integer.toString(currentIndex));
        return returnMap;
    }

    public int evaluateRPN(Queue<String> rpnNotation) {
        Stack<Integer> operationStack = new Stack<Integer>();
        while(!rpnNotation.isEmpty()) {
            String currentQueueCharacter = rpnNotation.remove();
            if(operatorList.contains(currentQueueCharacter)) {
                int operand2 = operationStack.pop();
                int operand1 = operationStack.pop();
                int result = performOperation(currentQueueCharacter, operand1, operand2);
                System.out.println(operationStack + "    "  + operand1 + " " + operand2 + " " + result);
                operationStack.push(result);
            }
            else {
                operationStack.push(Integer.valueOf(currentQueueCharacter));
            }
        }
        return operationStack.pop();
    }

    public int performOperation(String currentQueueCharacter, int operand1, int operand2) {
        int result = 0;
        if(currentQueueCharacter.equals("+")) {
            result = operand1 + operand2;
        }
        if(currentQueueCharacter.equals("*")) {
            result = operand1 * operand2;
        }
        if(currentQueueCharacter.equals("-")) {
            result = operand1 - operand2;
        }
        if(currentQueueCharacter.equals("/")) {
            result = operand1 / operand2;
        }
        return result;
    }

    public boolean isOperator(Character stringChar) {
        if(stringChar == '+' || stringChar == '-' || stringChar == '*' || stringChar == '/' || stringChar == '(' || stringChar == ')') {
            return true;
        }
        else {
            return false;
        }
    }

    public String getEquation() {
        return equation;
    }
}