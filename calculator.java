// import java.util.Scanner;

import java.util.ArrayList;

class Calculator {

    static String message;

    static ArrayList<String> a = new ArrayList<String>();
    // {"1","+","2","*","3","/","4"}

    static ArrayList<Integer> opening, ending=new ArrayList<Integer>();

    public static void format() {
        
        //add brackets on end and start
        a.add(0,"(");
        a.add(")");

        //remove excess brackets
        reformat();
        
        //add brackets to prevent wrong use of presedence of operator * and /
        // while () {
            
        // }



        //store all brackets in arraylists


    }

    public static void main(String[] args) {
        a.add("1");
        a.add("+");
        a.add("(");
        a.add("2");
        a.add(")");
        a.add("*");
        a.add("3");
        a.add("/");
        a.add("4");
        

        boolean syntax=syntax_check();

        //if syntax is incorrect
        if(!syntax)
        {
            System.out.println("Error:"+message);
            System.exit(0);
        }

        //if only a single calculation is present solve it here to save computational power
        if(a.size()==3)
        {
            //solve simple calculation

        }
        
        //initial formatting of given equation
        format();

        
        // Scanner in=new Scanner (System.in);
        // String equation="1+2*3/4";

        // while (a.size()==1) //repeat until there is a single number as result left
        // {

        // }

        // for (int i =0;i<a.size();i++) {

        // }

    }
    

    public void operate() {

    }

    public static void reformat() // remove useless brackets
    {
        ArrayList<Integer> remove = new ArrayList<Integer>();
        for (int i = 1; i < a.size() - 1; i++) {
            if (a.get(i - 1) == "(" && a.get(i + 1) == ")") {
                remove.add(i - 1);
                remove.add(i + 1);
            }
        }
        System.out.println(remove);
        for(int i=remove.size()-1; i>=0;i--)
        {
            System.out.println(remove.get(i));
            a.remove((int)remove.get(i));
        }
        System.out.println(a);
    }

    

    //checks if the equation syntax is correct
    public static boolean syntax_check() {
        boolean check=true;
        
        //check if given is a equation or not (it must have at least 3 items to be a solvable expression)
        if(a.size()<3)
        {
            message="Please Enter a solvable expression";
            return false;
        }

        //if 3 items are present then the middle must be an operator
        if(a.size()==3)
        {
            // if middle item is not an operator
            if(!is_operator(1))
            {
                message="Please Enter a solvable expression";
                return false;
            }
            // if 1st and 3rd item is not a number
            if((is_operator(0)||is_bracket(0)) || (is_operator(1)||is_bracket(1)))
            {
                message="Please Enter a solvable expression";
                return false;
            }
        }


        for(int i = 1; i < a.size() - 1; i++)
        {
            //check if the before of a ) bracket or after of a ( bracket is an operator
            if((a.get(i)=="("&&is_operator(i+1) )||(a.get(i)==")"&&is_operator(i-1)))
            {
                // System.out.println("This was the wrong format");
                message="there is a syntax error in expression at "+a.get(i-1)+a.get(i)+a.get(i+1);
                return false;
            }
        }

        //check if brackets opened are closed
        
        int op=0,cl=0;
        for(int i=0; i<a.size(); i++)
        {
            if(a.get(i)=="(")
            op++;
            if(a.get(i)==")")
            cl++;
        }
        if(op!=cl)
        {
            message="The brackets are not closed properly.\nPlease close opened brackets or there can be an error in calculation.";
            return false;
        }

        return check;
    }
    public static double calculate(double first ,char operator ,double second)
    {
        switch (operator) {
            case '+': return(first + second);
            
            case '-': return(first - second);
            
            case '*': return(first * second);
            
            case '/': 
            if(second==0.0)
            {
                System.out.println("Invalid Output : Cannot divide by zero");
                System.exit(0);
            }
            
            return(first / second);
            
        
            default:
            System.out.println("Some error occurred");
                System.exit(0);
                break;
        }
        return 0.0;
    }
    public static boolean is_operator(int position)
    {
        String st=a.get(position);

        if(st.contains("*")||st.contains("/")||st.contains("+")||st.contains("-"))
        {
            //returns true as it is an operator
            return true;
        }
        return false;
    }
    public static boolean is_bracket(int position){
        String st=a.get(position);

        if(st.contains("(")||st.contains(")"))
        {
            //returns true as it is a bracket
            return true;
        }
        return false;
    }
}
