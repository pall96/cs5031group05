import java.util.Queue;
import java.util.Random;

public class EquationGenerator {
    private int equationLength;
    private static String operators = "+-/*()";
    private String equation;
    private static String operatorWithoutParan = "+-/*";

    EquationGenerator(int equationLength) {
        this.equationLength = equationLength;
    }

    public static void main(String[] args) {
        EquationGenerator e = new EquationGenerator(7);
        String equation = e.generateEquation();
        /*for(int i = 0; i < 50; i++) {
            System.out.println(e.generateEquation());
        }*/
        System.out.print(equation + "  ");
        EquationParser p = new EquationParser(equation);
        Queue<String> RPN = p.generateRPN();
        System.out.print(p.evaluateRPN(RPN));

    }

    String generateEquation() {
        equation = "";
        Random randomGenerator = new Random();
        int currOpenParan = 0;

        while (equation.length() <= equationLength) {
            if (equation.length() != 0 && equation.charAt(equation.length() - 1) == '/') {
                equation = equation + getNonZeroRandomNumber(randomGenerator);
            }
            else {
                equation = equation + getRandomNumber(randomGenerator);
            }
                if (equation.length() < equationLength) {
                    char randomOperator = getRandomOperator(randomGenerator);

                    if (randomOperator == '(') {
                        equation = equation + randomOperator;
                        currOpenParan++;
                    } else if (randomOperator == ')') {
                        if (currOpenParan == 0) {
                            equation = equation + '(';
                            currOpenParan++;
                        } else {
                            equation = equation + ')';
                            currOpenParan--;
                        }
                    } else {
                        equation = equation + randomOperator;
                    }
                }
            }


            String replacingString = operatorWithoutParan.charAt(randomGenerator.nextInt(4)) + "" + '(' + "";
            //System.out.println("String that will replace" + replacingString);
            equation = equation.replace("(", replacingString);
            String temp = equation;
            int lastIndex = equationLength - 1 - currOpenParan;
            char charAtLastIndex = equation.charAt(lastIndex);


            String removedString = equation.substring(lastIndex + 1);

            for (int i = 0; i < removedString.length(); i++) {
                if (removedString.charAt(i) == '(') {
                    currOpenParan--;
                }
            }

            if (operatorWithoutParan.indexOf(charAtLastIndex) != -1) {
                int randomNumber = randomGenerator.nextInt(9);
                equation = equation.substring(0, lastIndex) + randomNumber;
                while(equation.length() < equationLength) {
                    equation = equation + randomGenerator.nextInt(9);
                }
            } else if (charAtLastIndex == '(') {
                int randomNumber1 = randomGenerator.nextInt(9);
                int randomNumber2 = randomGenerator.nextInt(9);
                equation = equation.substring(0, lastIndex) + randomNumber1 + randomNumber2;
                currOpenParan--;
            } else {
                equation = equation.substring(0, lastIndex + 1);
            }

            while (currOpenParan > 0) {
                //System.out.println(currOpenParan);
                equation = equation + ')';
                currOpenParan--;
            }

            //System.out.println("Equation before chopping off " + temp + " Final equation " + equation);
            return equation;
        }


    int getNonZeroRandomNumber(Random randomGenerator) {
      int generatedNumber = getRandomNumber(randomGenerator);
      while(generatedNumber == 0) {
          generatedNumber = getRandomNumber(randomGenerator);
      }
      return generatedNumber;
    }


        int getRandomNumber (Random randomGenerator){
            int randomNumber = randomGenerator.nextInt(getUpperBound());
            return randomNumber;
        }

        int getUpperBound () {
            String upperBoundString = "";
            for (int i = 0; i < equationLength - 4; i++) {
                upperBoundString = upperBoundString + 9;
            }
            return Integer.parseInt(upperBoundString);
        }

        char getRandomOperator (Random randomGenerator){
            int randomIndex = randomGenerator.nextInt(operators.length());
            char randomOperator = operators.charAt(randomIndex);
            return randomOperator;
        }
    }



