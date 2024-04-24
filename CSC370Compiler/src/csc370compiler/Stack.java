/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csc370compiler;

/**
 *
 * @author margaretobrien
 */
public class Stack {
    
    public final int stackSize;
    public Lex[] stackArray;
    public int top;
    
    public Stack(int size) 
    {
        stackSize = size;
        stackArray = new Lex[stackSize];
        top = -1;
    }
    
    public void push(Lex x) 
    { 
        if(!isFull())
            {
               stackArray[top+1] = x;
                top++; 
            }
            else
            {
                System.out.println("Stack is full");
            } 
    }
    
    public Lex pop() // take item from top of stack
    { 
        if(!isEmpty())
            {
                Lex out = stackArray[top];
                top--;
                return out;
            }
        Lex error = new Lex(-1.0);
        return error;
    }
    
    public Lex peek() // peek at top of stack
    { 
        return stackArray[top]; 
    }
    
    public boolean isEmpty() // true if stack is empty
    { 
        return (top == -1); 
    }
    
    public boolean isFull() // true if stack is full
    {
        return (top == (stackSize - 1));
    }
    
    public int size() // return size
    { 
        return (top + 1);
    }
    
    public Lex peekAt(int n) // peek at index n
    {       
        return (stackArray[n]);
    }
    
    public void printStack()
    {
        for(int j=0; j<size(); j++)
        {
            if(peekAt(j).token==0)
            {
                System.out.println("Lexeme: " + peekAt(j).op + "   Token: Operator");
            }
            else if(peekAt(j).token==1)
            {
                System.out.println("Lexeme: " + peekAt(j).name + "   Token: Variable");
            }
            else if(peekAt(j).token==2)
            {
                System.out.println("Lexeme: " + peekAt(j).val + "   Token: Literal");
            }
            else
            {
                System.out.println("Lexeme: " + peekAt(j).sOp + "   Token: SpecialCharacter");
            }
        }
    }
    
    public int hasMatch(String n)
    {
        for(int j=0; j<size(); j++)
        {
            if(peekAt(j).token==1)
            {
                if(peekAt(j).name.contentEquals(n))
                {
                    return j;
                }
            }
            
        }
        return -1;
    }
    
}
