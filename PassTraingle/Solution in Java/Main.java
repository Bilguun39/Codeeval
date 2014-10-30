import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
	
	public static void main(String[] args) throws IOException{
		
		List<String> input = readFile("E:\\values.txt");
		
		int size = input.size();
		
		int[][] traingle = new int[size][size];
		String[] singleRow;
		
		for(int i = 0; i < size; i++){
			singleRow = input.get(i).split("\\s");
			
			for(int j = 0; j < singleRow.length; j++){
				traingle[i][j] = Integer.parseInt(singleRow[j]);
			}
		}
		
		System.out.println(maximumSum(traingle));
		
	}

	public static List<String> readFile(String fileName) throws IOException {
		String sCurrentLine;

		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));

		List<String> lines = new ArrayList<>();

		while ((sCurrentLine = br.readLine()) != null) {
			lines.add(sCurrentLine);
		}

		br.close();

		return lines;
	}
	
	public static int maximumSum(int[][] traingle){
		for (int i = traingle.length - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
            	traingle[i][j] += Math.max(traingle[i + 1][j], traingle[i + 1][j + 1]);
            }
        }
        return traingle[0][0];
	}
}
