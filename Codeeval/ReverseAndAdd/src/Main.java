import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
	
	public static void main(String[] args) throws IOException{
		List<String> inputs = readFile("E:\\values.txt");
		
		int value = 0;
		String result;
		
		for(String input : inputs){
			value = Integer.valueOf(input);
			
			if(value < 10000){
				result = countIteration(value);
				
				if(Integer.parseInt(result.split("\\s")[0]) < 100){
					System.out.println(result);
				}
			}
		}
	}
	
	public static String countIteration(int value){
		int iteration = 1;
		
		StringBuilder sb = new StringBuilder();
		int reverse = 0;
		
		while(iteration <= 101){
			reverse = getReverse(value);
			
			value += reverse;
			
			if(value == getReverse(value)){
				
				break;
			}
			
			iteration++;
			
		}
		
		sb.append(iteration).append(" ").append(value);
		
		return sb.toString();
	}
	
	public static int getReverse(int value){
		assert(value >= 0);
		
		int palindrome = 0;
		
		while (value != 0){
			palindrome = palindrome * 10 + value % 10;
			value /= 10;
		}
		
		return palindrome;
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
}
