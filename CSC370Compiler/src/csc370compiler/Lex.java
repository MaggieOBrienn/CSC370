/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csc370compiler;

/**
 *
 * @author margaretobrien
 */
public class Lex {
    public String name;
    public int val;
    public char op;
    public char sOp;
    public int token;
    public Lex right;
    public Lex left;
    public Lex parent;
    public String temp;
    
    public Lex(char o)
    {
        if(o == '=')
        {
            sOp=o;
            token=3;
            right=null;
            left=null;
            parent=null;
            temp=null;
        }
        else
        {
            op=o;
            token=0; 
            right=null;
            left=null;
            parent=null;
            temp=null;
        }
    }
    public Lex(String n)
    {
        name=n;
        token=1;
        right=null;
        left=null;
        parent=null;
        temp=null;
    }
    public Lex(int v)
    {
        val=v;
        token=2;
        right=null;
        left=null;
        parent=null;
        temp=null;
    }
    public Lex(double er)
    {
        if(er==-1.0)
        {
            System.out.println("Error: Stack Empty");
            System.exit(0);
        }
    }
    public boolean hasRight()
    {
        if(this.right!=null)
        {
            return true;
        }
        else
            return false;
    }
    public boolean hasParent()
    {
        if(this.parent!=null)
        {
            return true;
        }
        else
            return false;
    }
    public boolean hasTemp()
    {
        if(this.temp!=null)
        {
            return true;
        }
        else
            return false;
    }
    public void setRight(Lex r)
    {
        right = r;
    }    
    public void setLeft(Lex l)
    {
        left = l;
    }   
    public void setParent(Lex p)
    {
        parent = p;
    }
    public void setTemp(String t)
    {
        temp = t;
    }
}
