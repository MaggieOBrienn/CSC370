/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package accesslevel;

import java.util.*;
        
public class AccessLevel {

    public static String permission(int[] levels, int minPerm, int numUsers)
    {
        String output = "";
        for(int x =0; x < numUsers; x++)
        {
            if(levels[x] < minPerm)
                output += "D";
            else
                output += "A";
        }
        
        return output;
    }
    
    public static void main(String[] args) {
        int[] users = new int[51];
        System.out.println("Enter a space seperated list of user permission level: ");
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.nextLine();
        Scanner in = new Scanner(input);
        int count = 0;
        for(int y=0; y<51; y++)
        {
            if(in.hasNext())
            {
                users[y] = in.nextInt();
                count++;
            }
            else
                break;
        }
        
        System.out.println("Enter the minimum permission: ");
        int perm = keyboard.nextInt();
        
        if(count == 0)
            System.out.println(" ");
        else
            System.out.println(permission(users, perm, count));
    }
    
}
