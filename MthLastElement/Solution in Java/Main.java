
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bilguun
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String[] arr = null;
            
            int index = 0;
            
            List<String> result = new ArrayList<>();
            
            for(String val : readFile(args[0])){
                arr = val.split("\\s");
                
                index = Integer.valueOf(arr[arr.length - 1]);
                
                if(index <= arr.length - 1){
                    result.add(arr[arr.length - index - 1]);
                    
                    System.out.printf("%-1s\n", arr[arr.length - index - 1]);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<String> readFile(String fileName) throws IOException {
        String sCurrentLine;

        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        List<String> words = new ArrayList<>();

        while ((sCurrentLine = br.readLine()) != null) {
            words.add(sCurrentLine);
        }

        return words;
    }
}
