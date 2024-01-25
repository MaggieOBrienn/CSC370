/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package runlengthencode;

import java.util.*;

public class RunLengthEncode {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String line = keyboard.nextLine();
        System.out.println(encode(line));
    }
    
    public static String encode(String in)
    {
        String out= "";
        if(in.length() > 0)
        {
            char repeat = in.charAt(0);
            int count = 1;
            for(int x=1; x<in.length(); x++)
            {
                if(repeat == in.charAt(x) && x != in.length()-1)
                {
                    count++;
                }
                else
                {
                    if(repeat == in.charAt(x) && x == in.length()-1)
                    {
                        count++;
                    }
                    
                    if(count > 4 && count <10)
                    {
                        out += "/0" + count + repeat;
                        repeat = in.charAt(x);
                        count = 1;
                    }
                    else if(count > 4 && count >= 10)
                    {
                        out += "/" + count + repeat;
                        repeat = in.charAt(x);
                        count = 1;
                    } 
                    else
                    {
                        for(int y=0; y<count;y++)
                            out += repeat;
                        repeat = in.charAt(x);
                        count = 1;
                    }
                }
            
            }
            if(in.length() == 1)
                out += repeat;
        }
            
        return out;
    }
}
