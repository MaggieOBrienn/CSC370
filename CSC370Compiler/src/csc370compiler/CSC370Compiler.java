/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package csc370compiler;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class CSC370Compiler {
    protected static int[] tokens = new int[4]; //operators=0, variables=1, literals=2, specChar=3
    //create stack of tokens
    protected static Stack tokStack;
    protected static int temp=0;
    protected static Stack variables;
    
    public static void main(String[] args){
        int lines=0;
        //initialize token array count at 0
        for(int t=0; t<4; t++) 
        {
            tokens[t]=0;
        }
        Lex[] starters = new Lex[10];
        variables = new Stack(50);
        //read file one line at a time and print and count tokens  
        try{
            //File myFile = new File("/Users/margaretobrien/NetBeansProjects/CSC370Compiler/src/csc370compiler/test1.txt");
            //File myFile = new File("/Users/margaretobrien/NetBeansProjects/CSC370Compiler/src/csc370compiler/test2.txt");
            //File myFile = new File("/Users/margaretobrien/NetBeansProjects/CSC370Compiler/src/csc370compiler/testFile.txt");
            //File myFile = new File("/Users/margaretobrien/NetBeansProjects/CSC370Compiler/src/csc370compiler/test3.txt");
            File myFile = new File("/Users/margaretobrien/NetBeansProjects/CSC370Compiler/src/csc370compiler/test4.txt");
            Scanner inFile = new Scanner(myFile);
            while(inFile.hasNextLine())
            {
                tokStack = new Stack(50);
                lines++;
                String data = inFile.nextLine();
                tokenizer(data);
                System.out.println("Lexical Analyzer and tree for line "+lines);
                tokStack.printStack();
                Lex starting= absSynTree();
                printTree(starting);
                starters[lines-1]=starting;
                System.out.println("\n\n");
            }
        }catch(FileNotFoundException e)
        {
            System.out.println("no file");
        }
        
        int i;
        for(i=0; i<lines; i++)
        {
            System.out.println("Three Address code for line "+ (i+1) + ": ");
            threeAddCode(starters[i]);
            System.out.println("\n");
        }
        
        for(int s=(i-1); s>=0; s--)
        {
            boolean okay = synAnalyzer(starters[s]);
            if(!okay)
            {
                System.out.println("Error: Undefined variable in line " + (s+1));
                System.exit(0);
                break;
            }
            
        }
        
    }
    
    public static void threeAddCode(Lex begin)
    {
        Lex current = begin;
        while(current.hasRight())
        {
            current=current.right; //iterating through the tree to get to the last node
        }
        current=current.parent;
        while(current.hasParent())
        {
            String tempVariable = "t" + temp;
            System.out.println(tempVariable +" = " +printName(current.left)+
                    printName(current)+printName(current.right));
            current.setTemp(tempVariable);
            current=current.parent;
            temp++;
        }
        
        //setting the variable to a temp
        String tempVariable = "t" + temp;
        System.out.println(tempVariable +" = " +printName(current.left));
        current.left.setTemp(tempVariable);
        temp++;
        
        //this is where a variable would be defined, add to list
        variables.push(current.left);
        
        //setting the variable to the expression
        System.out.println(printName(current.left)+" = "+printName(current.right));
    }
    
    public static boolean synAnalyzer(Lex start) //look at the last line and ensure that all variables have been previously defined in other lines
    {
        Lex current=start;
        while(current.hasRight())
        {
            current=current.right; //iterating through the tree to get to the last node
        }
        current=current.parent;
        while(current.hasParent()) //look at children nodes for variable
        {
            if(current.right.token==1)
            {
                int ind = variables.hasMatch(current.right.name); //look for match in variable lookup
                if(ind == -1)//no match
                {
                    return false;
                }
                //just checking for a match, don't need an else statement
            }
            if(current.left.token==1)
            {
                int ind = variables.hasMatch(current.left.name); //look for match in variable lookup
                if(ind == -1)//no match
                {
                    return false;
                }
                //just checking for a match, don't need an else statement
            }
            current=current.parent;
        }
        return true;
    }
    
    public static Lex absSynTree()
    {
        int count=0;
        int start=tokStack.top;
        
        for(int x= tokStack.top; x>=0; x--)
        {   
            if(count%2==0) //should be operand
            {
                if(tokStack.peekAt(x).token == 1 || tokStack.peekAt(x).token == 2) //its a variable or a literal
                {   }
                else
                {
                    System.out.println("Error: invalid sentence ending in an operator");
                    System.exit(0);
                    break;
                }
                count++;
            }
            else //should be operand or specChar
            {
                if(tokStack.peekAt(x).token==3) //its an equal sign, the tree is done
                {
                    start = x;
                    //break;
                    if(count==1)
                    {
                        tokStack.peekAt(x).setRight(tokStack.peekAt(x+1));
                        tokStack.peekAt(x+1).setParent(tokStack.peekAt(x));
                    }
                    else
                    {
                        tokStack.peekAt(x).setRight(tokStack.peekAt(x+2));
                        tokStack.peekAt(x+2).setParent(tokStack.peekAt(x));
                    }
                    
                    if(tokStack.peekAt(x-1).token == 1 || tokStack.peekAt(x-1).token == 2)//valid bc operand
                    {
                        tokStack.peekAt(x).setLeft(tokStack.peekAt(x-1));
                        tokStack.peekAt(x-1).setParent(tokStack.peekAt(x));
                        x--;
                        count++;
                    }
                    else //two operators in a row or equal sign then operator. neither are valid
                    {
                        System.out.println("Error: invalid sentence two operators in a row");
                        System.exit(0);
                        break;
                    }
                }
                else //it's an operand
                {   
                    
                    if(count==1)
                    {
                        tokStack.peekAt(x).setRight(tokStack.peekAt(x+1));
                        tokStack.peekAt(x+1).setParent(tokStack.peekAt(x));
                    }
                    else
                    {
                        tokStack.peekAt(x).setRight(tokStack.peekAt(x+2));
                        tokStack.peekAt(x+2).setParent(tokStack.peekAt(x));
                    }
                    if(tokStack.peekAt(x-1).token == 1 || tokStack.peekAt(x-1).token == 2)//valid bc operand
                    {
                        tokStack.peekAt(x).setLeft(tokStack.peekAt(x-1));
                        tokStack.peekAt(x-1).setParent(tokStack.peekAt(x));
                        x--;
                        count++;
                    }
                    else //two operators in a row or equal sign then operator. neither are valid
                    {
                        System.out.println("Error: invalid sentence two operators in a row");
                        System.exit(0);
                        break;
                    }
                    count++;
                }
            }
        }

        return tokStack.peekAt(start);
    }
    
    public static void printTree(Lex start)
    {
        Lex current = start;
        int c=0;
        while(current.hasRight())
        {
            if(c==0)
            {
                System.out.println(printName(current));
            }
            for(int x=0; x<c; x++)
            {
                System.out.print("\t");
            }
            System.out.println(printName(current.left) +"\t"+ printName(current.right));
            current=current.right;
            c++;
        }
    }
    
    public static String printName(Lex cur)
    {
        String st ="";
        if(cur.hasTemp())
        {
            st += cur.temp;
        }
        else if(cur.token==0)
        {
            st += cur.op;
        }
        else if(cur.token==1)
        {
            st +=cur.name;
        }
        else if(cur.token==2)
        {
            st += String.valueOf(cur.val);
        }
        else
        {
            st += cur.sOp;
        }
        return st;
    }
    
    public static void tokenizer(String input)
    {
        String temp = "";
        for(int x=0; x<input.length(); x++)
        {
            if(input.charAt(x) == ' ')
            {
                
            }
            else if(input.charAt(x) == '*' || input.charAt(x) == '/' ||
                    input.charAt(x) == '+' || input.charAt(x) == '-')
            {
                tokens[0]++;
                Lex add = new Lex(input.charAt(x));
                tokStack.push(add);
            }
            else if(input.charAt(x) == '=')
            {
                tokens[3]++;
                Lex add = new Lex(input.charAt(x));
                tokStack.push(add);
            }
            else if(input.charAt(x) >= '0' && input.charAt(x) <= '9')
            {
                temp += input.charAt(x);
                while(x+1<input.length() && input.charAt(x+1) >= '0' && input.charAt(x+1) <= '9')
                {
                    temp += input.charAt(x+1);
                    x++;
                }
                tokens[2]++;
                Lex add = new Lex(Integer.valueOf(temp));
                tokStack.push(add);
                temp="";
            }
            else if((input.charAt(x) >= 'a' && input.charAt(x) <= 'z')||(input.charAt(x) >= 'A' && input.charAt(x) <= 'Z'))
            {
                temp += input.charAt(x);
                while((x+1<input.length())&&((input.charAt(x+1) >= 'a' && input.charAt(x+1) <= 'z')||(input.charAt(x+1) >= 'A' && input.charAt(x+1) <= 'Z')))
                {
                    temp += input.charAt(x+1);
                    x++;
                }
                tokens[1]++;
                Lex add = new Lex(temp);
                tokStack.push(add);
                temp="";
            }    
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
