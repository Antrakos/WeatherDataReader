import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
 
public class DataReader {
	String csvFile = "";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ";";
	ArrayList<String> list;
	int size;

	public DataReader(String csvFile)
	{
		this.csvFile = csvFile;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
  public ArrayList<String> run(int param) {
	try {
 		br = new BufferedReader(new FileReader(csvFile));
		list = new ArrayList<String>();
		
		while ((line = br.readLine()) != null) {
			String[] weather = line.split(cvsSplitBy);
			size = weather.length;
			list.add(weather[param]);
		}
	} catch (FileNotFoundException e) {
		e.printStackTrace(); 
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		
		if (br != null) { 
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
  return list;
  }
}
 