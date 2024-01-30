#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Jan 30 12:42:29 2024

@author: margaretobrien
"""

class RunLengthEncode:
    
    def encode(self, inputString):
        leng = len(inputString)
        
        if not inputString:
            return ""
        elif leng < 4:
            return inputString
        
        output = ""
        repeat = inputString[0]
        count = 1
        
        for x in range(1, leng):
            if inputString[x] == repeat:
                count += 1
            if x == leng-1 or inputString[x] != repeat:
                if count > 4 and count < 10:
                    output += "/0" + str(count) + repeat
                    repeat = inputString[x];
                    count = 1
                elif count >= 10:
                    output += "/" + str(count) + repeat
                    repeat = inputString[x]
                    count = 1
                else:
                    for y in range(0, count):
                        output += repeat;
                    repeat = inputString[x]
                    count = 1
         
        return output
                
def main():
    runLengCode = RunLengthEncode()    
    userIn = input("Enter the Input: ")
    message = runLengCode.encode(userIn)
    print(message)              


if __name__ == "__main__":
    main()