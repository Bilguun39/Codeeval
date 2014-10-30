
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bilguun
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LinkedHashMap<Integer, List<Items>> data = new LinkedHashMap<>();
        String[] arr;
        String[] itm;
        String[] itms;
        
        List<Items> items;
        List<Integer> indicies = new ArrayList<>();;
        Items item;
        
        try {

            //Getting input from files and separate by index and values.
            for (String value : readFile(args[0])) {
                items = new ArrayList<>();
                arr = value.split("\\:");
                if (arr.length >= 2) {
                    arr[1] = arr[1].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\$", "").replaceAll("\\s", "\\:");
                    
                    itm = arr[1].split("\\:");
                    
                    for (String val : itm) {
                        itms = val.split("\\,");
                        if (itms.length >= 2) {
                            
                            item = new Items(Integer.valueOf(itms[0]), (double) (Integer.valueOf(itms[2]) / Double.valueOf(itms[1])), Integer.valueOf(itms[2]), Double.valueOf(itms[1]));
                            
                            items.add(item);
                        }
                    }
                    
                    data.put(Integer.valueOf(arr[0].replaceAll("\\s", "")), items);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int n;
        int subset[][];
        boolean printed = false;
        
        for (Integer maxWeight : data.keySet()) {
            items = data.get(maxWeight);
            
            n = data.get(maxWeight).size();
            
            subset = new int[n + 1][maxWeight + 1];
            
            for (int i = 0; i <= n; i++) {
                subset[i][0] = 0;
            }
            
            for (int i = 1; i <= n; i++) {
                item = items.get(i - 1);
                for (int j = 0; j <= maxWeight; j++) {
                    if (item.getWeight() > j) {
                        subset[i][j] = subset[i - 1][j];
                    } else {
                        subset[i][j] = Math.max(item.getPrice() + subset[i - 1][j - ((Double) item.getWeight()).intValue()], subset[i - 1][j]);
                    }
                }
            }

            for (int i = n, j = maxWeight; i > 0 && j >= 0; i--) {
                item = items.get(i - 1);
                
                if (subset[i][j] != subset[i - 1][j]) {
                    indicies.add(item.getIndex());
                    
                    j = j - ((Double) item.getWeight()).intValue();
                }
            }
            
            printed = false;
            
            if (indicies.size() > 0) {
                for (int i = indicies.size() - 1; i >= 0; i--) {
                    if (printed) {
                        System.out.print(",");
                    }
                    System.out.print(indicies.get(i));
                    printed = true;
                }
            } else {
                System.out.print("-");
            }
            
            System.out.print("\n");
            
            indicies = new ArrayList<>();
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

class Items {
    
    private int index;
    private double rate;
    private int price;
    private double weight;
    
    public Items(int index, double rate, int price, double weight) {
        this.index = index;
        this.rate = rate;
        this.price = price;
        this.weight = weight;
    }
    
    public int getIndex() {
        return index;
    }
    
    public double getRate() {
        return rate;
    }
    
    public int getPrice() {
        return price;
    }
    
    public double getWeight() {
        return weight;
    }
}
