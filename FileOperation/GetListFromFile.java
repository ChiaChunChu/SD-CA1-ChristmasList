/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileOperation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Chia Chun Chu
 */
public class GetListFromFile {
    
    /**
     * getGiftList - Get the data from the file which has a specific format definition
     * 
     * @param fileName - The file to get the data from
     * @return An ArrayList which with the the file content 
     */
    public static ArrayList<String> getGiftList (String fileName) throws FileNotFoundException, IOException {
        
        ArrayList<String> giftList = new ArrayList<String>();

        BufferedReader myFile = new BufferedReader (new FileReader(fileName));
        String myLine = myFile.readLine();
        for (String i: myLine.split(",")) {
            giftList.add(i);
        }

        return giftList; 
    }
    
    /**
     * getNameList - Get the data from the file which has a specific format definition
     * 
     * @param fileName - The file to get the data from
     * @return An ArrayList which with the the file content
     */
    public static ArrayList<String> getNameList (String fileName) throws FileNotFoundException, IOException {
        
        ArrayList<String> nameList = new ArrayList<String>();

        BufferedReader myFile = new BufferedReader (new FileReader(fileName));
        String myLine;
        while ((myLine = myFile.readLine())!=null) {
            nameList.add(myLine);
        }
        
        return nameList;
    }
    
}
