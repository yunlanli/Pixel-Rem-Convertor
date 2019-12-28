import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class PxConvert {

    public static String decimalFormat(String decimal){

        for (int i = decimal.length()-1; i>=0; i--){
            if (decimal.charAt(i)>48 && decimal.charAt(i)<58) {
                return decimal;
            }else if (decimal.charAt(i) == '0'){
                decimal = decimal.substring(0, i);
            }else if (decimal.charAt(i) == '.'){
                decimal = decimal.substring(0, i);
                return decimal;
            }
        }

        return "invalid input";
    }

    public static void main(String[] args) throws FileNotFoundException{
        File old = new File(args[0]);
        Scanner in = new Scanner(old);
        PrintWriter out = new PrintWriter(args[1]);

        // variable to keep track of the line number and number of changes
        int lineNum = 1;
        int total = 0;
        ArrayList<Integer> changed = new ArrayList<>();

        // Reads in line by line from the old file
        while (in.hasNextLine()){
            String temp = in.nextLine();
            
            // writes the line to the output file if 
            // 1. it doesn't contain the "px" keyword
            // 2. It isn't media query
            // Convert px to rem other-wise
            if (!temp.contains("px") || temp.contains("@media") ){
                out.println(temp);
            }else{
                //Since usually #px comes after ':', split the sentence around ':'
                String[] match = temp.split(":");
                String condition = match[1];

                //variable to keep track of number of px
                int count = 0;

                // rem[] and px[] to store px and rem
                String[] rem = new String[5];
                String[] px = new String[5];

                while (condition.contains("px")){
                    String[] pxInfo = condition.split("px",2);
                    condition = pxInfo[1];

                    for (int i=0; i<pxInfo[0].length(); i++){
                        char current = pxInfo[0].charAt(i);

                        // replace all non-integer chars with whitespace
                        if (current == '.'){
                            pxInfo[0] = pxInfo[0].replace(current, 'x');
                        }else if (current<48 || current>57){
                            pxInfo[0] = pxInfo[0].replace(current, ' ');
                        }

                    }

                    String pxVal = pxInfo[0].trim();
                    px[count] = pxInfo[0].replace('x','.').trim();  
                    double pixel;                  

                    // retrieve and store the pixel value in px
                    if (pxVal.contains("x")){
                        String[] pxNums = pxVal.split("x");
                        pixel = Integer.decode(pxNums[0]) + 
                        Integer.decode(pxNums[1])/Math.pow(10,pxNums[1].length());
                    }else{
                        pixel = Integer.decode(pxVal);
                    }

                    // convert px to rem
                    double remVal = pixel/16.0;
                    rem[count] = decimalFormat(String.valueOf(remVal));

                    count++;
                }

                temp = temp.replace("px", "rem");

                for (int i=0; i<count; i++){
                    temp = temp.replace(px[i], rem[i]);
                }

                //output the new line with px converted to rem
                out.println(temp);

                total++;
                changed.add(lineNum);
            }

            lineNum++;
        }

        in.close();
        out.close();

        System.out.printf("\n---------------\n\n");
        System.out.printf("Conversion successful!\n" + 
            "%d conversions made at:\nLine: ", total);

        for (int i=0; i<total; i++){

            if (i%5 == 4){
                System.out.print(changed.get(i) + "\nLine: ");
            }else if( i == total-1){
                System.out.printf("%d\n", changed.get(i));
            }else{
                System.out.printf("%d, ", changed.get(i));
            }
        }
        
        System.out.printf("\n---------------\n\n");
    }
}

