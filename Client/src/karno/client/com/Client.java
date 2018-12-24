package karno.client.com;
/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：3Sep.,2018 8:00:30 pm 
* 
*/
import java.io.IOException;
import java.rmi.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

public class Client  {
    SocketConnection sc;
    
    public Client(String address, int port) throws UnknownHostException, IOException{
    	sc = new SocketConnection(address, port);
    	sc.connect();
    }
    
    /*
     * There are two arguments for query and remove a word, the first one is opertion and the
     * second one is the word.
     * If do edition it need three arguments, except these two arguments, a third parameter shoud be 
     * the new meaning.
     */
    public String operation(String[] args) throws IOException, JSONException {
    	Map<String, String> action = new HashMap<String, String>();
    	action.put("Operation", args[0]);
    	action.put("Word", args[1]);
		if (args.length == 3)
			action.put("Meaning", args[2]);
		sc.sendRequest(action);
		Map<String, String> reply = sc.getReply();
		
		String result = reply.get("Result");
		return result;
    }
	public String query(String word) throws IOException, JSONException {
		String[] args = {"Query", word};
		String result = operation(args);
		return result;
	}
	
	public String remove(String word) throws IOException, JSONException {
		String[] args = {"Remove", word};
		String result = operation(args);
		return result;	
	}
	
	public String addWord(String word, String meaning) throws IOException, JSONException {
		String[] args = {"Add", word, meaning};
		String result = operation(args);
		return result;
	}
	
	public void close() {
		sc.close();
	}
}
