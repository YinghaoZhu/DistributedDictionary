package karno.server.pk;
/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：3Sep.,2018 8:00:30 pm 
* 
*/
import java.io.*;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;
/*
 * the form of the dictionary in json is "word":"meaning"
 */
public class DictionaryManagement {
	private String path; 
	
	public DictionaryManagement(String path) {
		this.path = path;
	}
    public Map<String, String> readFile() {
    	File file = new File(path);
    	if(!file.exists()) {
    		try {
    			file.createNewFile();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	StringBuilder sb = new StringBuilder();
    	try {
    		FileReader fr = new FileReader(file);
    		BufferedReader reader = new BufferedReader(fr);
    		String s = "";
    		while ((s = reader.readLine()) != null)
                sb.append(s + "\n");
    		reader.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	String str = sb.toString();
    	Map<String, String> words = new HashMap<String, String>();
    	
    	if (str.equals("")) 
    		return words;
    		
    	else {
	    	try {
	    		JSONObject json = new JSONObject(str);
	            Iterator ite = json.keys();
	    		while(ite.hasNext()) {
	    			String key = ite.next().toString();
	    			String value = json.get(key).toString();
	    			words.put(key, value);
	    	    } 
	    	} catch (JSONException e) {
	    		e.printStackTrace();
	        }
    	}
    	return words;
    }
    
    public void writeFile(Map<String, String> words) {
    	
    	JSONObject json = new JSONObject(words);		
		String str = json.toString();  
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			System.out.println(path);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(str);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
