/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca1_christmaslist;

import FileOperation.GetListFromFile;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Chia Chun Chu
 */
public class CA1_ChristmasList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //
        // CA1 project - Electronic Christmas List Generator
        //   Task: Read the names of Children from Santa’s Christmas List and assign them a set of 
        //         presents/gifts that you choose at random from a list. You then need to output an updated Present 
        //         Delivery List showing the name of each child followed by the presents they should receive. 
        //       
        ArrayList<String> boysGift;
        ArrayList<String> girlsGift;
        ArrayList<String> kidsList  = new ArrayList<>();
        ArrayList<String> finalList = new ArrayList<>();
        int invalidData = 0;
        final int maxGiftNum = 3;
        
        try {
            //
            // Get the lists which are boys and girls' gift list
            // and the name list from Chistmas.txt by calling methods
            //
            boysGift = GetListFromFile.getGiftList("BoysGift.txt");
            girlsGift = GetListFromFile.getGiftList("GirlsGift.txt");
            ArrayList<String> rawFile = GetListFromFile.getNameList("Christmas.txt");
            
            //
            // Validate the data
            //
            for (String r: rawFile) {
                //
                // Check the data format.
                //   1. The format should be [name, gender]
                // 
                // If the data is invalid, we will disregard it and count it by the variable (invalidData).
                //
                if (r.split(",").length != 2) {
                    invalidData++;
                } else {
                    String name = r.split(",")[0].trim();
                    String gender = r.split(",")[1].trim().toUpperCase();
                    //
                    // Check the name and gender field are valid.
                    //   1. Name should be letter only.
                    //   2. Gender should be either M or F.
                    //
                    // If the data is invalid, we will disregard it and count it.
                    //  
                    if (name.matches("[a-zA-Z ]+") && (gender.equals("M") || gender.equals("F"))) {
                        kidsList.add(name + "," + gender);
                    } else {
                        invalidData++;
                    }                    
                }
            }
            
            //
            // Assign gifts randomly for each child
            //   Rules:
            //   1. Each child can have minimum 1 of gift or maximum 3 of gifts randomly.
            //   2. Each child can not get the same gift in once
            //
            for (String kid: kidsList) {
                
                String name = kid.split(",")[0];
                String gender = kid.split(",")[1];
                ArrayList<String> giftsList;
                int[] giftMem = {-1,-1,-1};
                
                Random myRandom = new Random();
                
                //
                // Decide how many gifts for this kid by random
                //
                int giftNum = myRandom.nextInt(maxGiftNum);
                giftNum+=1;

                //
                // Assign the gift list (boys or girls)
                //
                if (gender.equals("M")) {
                    giftsList = boysGift;
                } else {
                    giftsList = girlsGift;
                }
                
                for (int i = 0; i < giftNum; i++) {
                    //
                    // Assign the gifts by random
                    //
                    int giftIndex = myRandom.nextInt(giftsList.size());                  
                    
                    //
                    // check if the gift repeats
                    //
                    if (i == 1) {
                        while (giftIndex == giftMem[0]) {
                            giftIndex = myRandom.nextInt(giftsList.size());  
                        }
                    } else if (i == 2) {
                        while (giftIndex == giftMem[0] || giftIndex == giftMem[1]) {
                            giftIndex = myRandom.nextInt(giftsList.size());
                        }
                    }
                    giftMem[i] = giftIndex;
                }
                
                //
                // Final step, generate the output list by specific format
                //
                String finalGift="";
                for (int i = 0; i < maxGiftNum; i++) {
                    if (giftMem[i] >= 0) {
                        if (i != 0) {
                            finalGift=finalGift+", ";
                        }
                        finalGift=finalGift+giftsList.get(giftMem[i]).trim();
                    }
                }
                finalList.add(name+" ("+finalGift + ")");
            }

            //
            // Write the final data into the file which name is Deliverids.txt
            //
            BufferedWriter myWriter = new BufferedWriter (new FileWriter("Deliveries.txt", false));
            for (String f: finalList) {
                myWriter.write(f);
                myWriter.newLine();
            }
            myWriter.close();
            
            //
            // Output the final message to user
            //
            System.out.println("List ready for Santa");
            System.out.println("--There were "+ invalidData +" invalid name(s) in the list--");
            
        } catch (FileNotFoundException f) {
            System.out.println("File does not exist.\n" + f);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
