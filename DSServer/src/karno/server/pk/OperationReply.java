package karno.server.pk;
/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：3Sep.,2018 8:00:30 pm 
* 
*/
import java.util.Map;

public class OperationReply {
	private Map<String, String> words; // words and meaning collection
	
	public OperationReply (Map<String, String> words) {
		this.words = words;
	}
	
	//get meaning for query operation
    public String query (String word) {
    	String meaning = "";
    	if (words.containsKey(word)) 
    		meaning =  words.get(word);
    	else
    		meaning = "Word not found!";
    	return meaning;  	   
    }
    
    public synchronized String remove (String word) {
    	String result = "";
    	if (words.containsKey(word)) {
    		words.remove(word);
    		result = word + " is successfully removed";
    	}
    	else
    		result = word +  " not exists.";
    	return result;		
    }
    
    public synchronized String addWord(String word, String meaning)
    {
    	String result = "";
    	if (words.containsKey(word))
    		result = word + " already exists in dictionary.";
    	else {
    		words.put(word, meaning);
    		result = word + " is added.";
    	}
    	return result;
    }
}
