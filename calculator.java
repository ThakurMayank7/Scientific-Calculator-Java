import java.util.Scanner;

import java.util.ArrayList;

class Calculator {

    static String message;

    static ArrayList<String> a = new ArrayList<String>();

    public static void main(String[] args) {
        String input;
        Scanner sc = new Scanner(System.in); // Create a Scanner object
        input=sc.nextLine();
        sc.close();
        System.out.println("The Given Equarion is : "+input);
        input=input+"?";
        String number="";
        for(int i=0;i<input.length();i++)
        {
            // if it is an operator or bracket
            if(is_operator_or_bracket(input.charAt(i)) || input.charAt(i) == '?')
            {
                if(number!="")
                {
                    a.add(number);
                }      
                a.add(String.valueOf(input.charAt(i)));
                number="";       
            }
            else {
                number=number+input.charAt(i);
            }
        }
        a.remove(a.size()-1);

        boolean syntax = syntax_check();
        // if syntax is incorrect
        if (!syntax) {
            System.out.println("Error:" + message);
            System.exit(0);
        }

        // if only a single calculation is present solve it here to save computational power
        if (a.size() == 3) {
            // solve simple calculation
            System.out.println("The answer is : "
                    + calculate(Double.parseDouble(a.get(0)), a.get(1).charAt(0), Double.parseDouble(a.get(2))));
            System.exit(0);
        }

        // add brackets on end and start
        a.add(0, "(");
        a.add(")");

        while (true) // repeat until there is a single number as result left
        {
            if (a.size() == 1) {
                break;
            }
            reformat();
            operate();
        }
        System.out.println("The answer is : "+a.get(0));
    }
    public static void operate() {
        // extracting bounds of simple expression to solve
        boolean check = false;
        int start = 999, end = 999;

        // stores start and end bounds of a simple expression in start and end
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) == "(") {
                start = i;
            }
            if (a.get(i) == ")") {
                end = i;
                break;
            }
            if (check) {
                break;
            }
        }
        double result = 0.0;
        // true-->addition false-->subtraction
        char pre_operator = '9';
        result = Double.parseDouble(a.get(start + 1));
        for (int i = start + 2; i <= end - 1; i++) {
            if (is_operator(i)) {
                pre_operator = a.get(i).charAt(0);
                continue;
            }
            result = calculate(result, pre_operator, Double.parseDouble(a.get(i)));
        }
        for (int i = end; i >= start; i--) {
            a.remove(i);
        }
        a.add(start, String.valueOf(result));
    }
    public static void reformat() {
        int count = 1;
        while (true) {
            if (count == a.size() - 2) {
                break;
            }
            // remove useless brackets
            if (a.get(count - 1) == "(" && a.get(count + 1) == ")") {
                a.remove(count + 1);
                a.remove(count - 1);
                continue;
            }
            count++;
        }
        count = 1;
        while (true) {
            if (count == a.size() - 1) {
                break;
            }
            // adds multiplication if not present
            if (is_number(a.get(count)) && is_number(a.get(count + 1))) {
                a.add(count + 1, "*");
            }
            count++;
        }
        // adds brackets to divisions
        count = 2;
        while (true) {
            if (count == a.size() - 2) {
                break;
            }
            if (a.get(count).equals("/") && (a.get(count + 2) != ")" || a.get(count - 2) != "(") && is_number(a.get(count - 1)) && is_number(a.get(count + 1))) {
                a.add(count + 2, ")");
                a.add(count - 1, "(");
                return;
            }
            count++;
        }
        // adds brackets to multiplication if some operation is there before division
        count = 2;
        while (true) {
            if (count == a.size() - 2) {
                break;
            }
            if (a.get(count).equals("*") && (a.get(count + 2) != ")" || a.get(count - 2) != "(") && is_number(a.get(count - 1)) && is_number(a.get(count + 1))) {
                a.add(count + 2, ")");
                a.add(count - 1, "(");
                return;
            }
            count++;
        }
    }

    // checks if the equation syntax is correct
    public static boolean syntax_check() {
        boolean check = true;

        // check if given is a equation or not (it must have at least 3 items to be a solvable expression
        if (a.size() < 3) {
            message = "Please Enter a solvable expression";
            return false;
        }

        // if 3 items are present then the middle must be an operator
        if (a.size() == 3) {
            // if middle item is not an operator
            if (!is_operator(1)) {
                message = "Please Enter a solvable expression";
                return false;
            }
            // if 1st and 3rd item is not a number
            if ((is_operator(0) || is_bracket(0)) || (is_operator(2) || is_bracket(2))) {
                message = "Please Enter a solvable expression";
                return false;
            }
        }

        for (int i = 1; i < a.size() - 1; i++) {
            // check if the before of a ) bracket or after of a ( bracket is an operator
            if ((a.get(i) == "(" && is_operator(i + 1)) || (a.get(i) == ")" && is_operator(i - 1))) {
                message = "there is a syntax error in expression at " + a.get(i - 1) + a.get(i) + a.get(i + 1);
                return false;
            }
        }

        // check if brackets opened are closed

        int op = 0, cl = 0;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) == "(")
                op++;
            if (a.get(i) == ")")
                cl++;
        }
        if (op != cl) {
            message = "The brackets are not closed properly.\nPlease close opened brackets or there can be an error in calculation.";
            return false;
        }

        return check;
    }
    public static double calculate(double first, char operator, double second) {
        switch (operator) {
            case '+':
                return (first + second);
            case '-':
                return (first - second);
            case '*':
                return (first * second);
            case '/':
                if (second == 0.0) {
                    System.out.println("Invalid Output : Cannot divide by zero");
                    System.exit(0);
                }
                return (first / second);
            default:
                System.out.println("Some error occurred");
                System.exit(0);
                break;
        }
        return 0.0;
    }
    public static boolean is_operator(int position) {
        String st = a.get(position);
        if (st.contains("*") || st.contains("/") || st.contains("+") || st.contains("-")) {
            // returns true as it is an operator
            return true;
        }
        return false;
    }
    public static boolean is_number(String str) {
        if (Character.isDigit(str.charAt(0))) {
            return true;
        }
        return false;
    }
    public static boolean is_bracket(int position) {
        String st = a.get(position);
        if (st.contains("(") || st.contains(")")) {
            // returns true as it is a bracket
            return true;
        }
        return false;
    }
    public static boolean is_operator_or_bracket(char c)
    {
        if(c=='(' || c==')' || c=='+' || c=='-' || c=='*' ||c=='/')
        {
            return true;
        }
        return false;
    }
}
