// import java.util.Scanner;

import java.util.ArrayList;

class Calculator {

    static ArrayList<String> a = new ArrayList<String>();
    // {"1","+","2","*","3","/","4"}

    ArrayList opening, ending;

    public static void main(String[] args) {
        a.add("1");
        a.add("+");
        a.add("(");
        a.add("2");
        a.add(")");
        a.add("*");
        a.add("3");
        a.add(")");
        a.add("/");
        a.add("4");

        reformat();
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

    public void format() {

    }
}
