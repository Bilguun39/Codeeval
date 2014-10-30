import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {

		List<String> lines = readFile(args[0]);

		for (String line : lines) {
			reverseGivenGroups(line);
		}
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

	public static void reverseGivenGroups(String line) {
		String[] input = line.split(";");

		if (input.length > 0) {
			String values = input[0];

			int k = Integer.valueOf(input[1]);

			input = values.split(",");

			if (input.length > 0) {
				int start = 0, end = start + k - 1;

				int i = 0;

				if (k > 1) {
					while (i < input.length / k) {

						input = reverseByLength(input, start, end);

						start += k;
						end += k;
						i++;
					}
				}

				StringBuilder sb = new StringBuilder();

				for (String val : input) {
					sb.append(val);
					sb.append(",");
				}

				sb.deleteCharAt(sb.length() - 1);

				System.out.println(sb.toString());
			}

		}
	}

	public static String[] reverseByLength(String[] arr, int start, int end) {
		String temp = null;

		if (end < arr.length) {
			for (int i = start, j = end; i <= (start + end) / 2; i++, j--) {
				temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}

		return arr;
	}

}
