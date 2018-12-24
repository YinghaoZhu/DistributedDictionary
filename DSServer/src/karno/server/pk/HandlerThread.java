package karno.server.pk;
/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：3Sep.,2018 8:00:30 pm 
* 
*/
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class HandlerThread implements Runnable {
    private Socket socket;
    private Map<String, String> words; // words and meaning collection
    private DataOutputStream dos;
    private DataInputStream dis;
    public HandlerThread(Socket client, Map<String, String> words) {
    	this.words = words; // initialize dictionary   	
    	socket = client;
    	System.out.println("*****************");
    	try {
    		dos = new DataOutputStream(socket.getOutputStream());
       		dis = new DataInputStream(socket.getInputStream());
    	} catch (IOException e) {
    		e.printStackTrace();
    	} 	
    	//new Thread(this).start();
    }
    
 
    public void run () {
    		while (true) {
    			try {
    				String str = dis.readUTF();
    				System.out.println(str);
            		JSONObject json = new JSONObject(str);		
            		//reply 
            		handleRequest(json);
            		System.out.println(words);
    			} catch (IOException e) {
    				try {
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    				break;
    			} catch (JSONException e) {
    				System.out.println(e.getMessage());
    			}
    		}
    		try {
    			dis.close();
        		dos.close();
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    		
    }
    /*decode request from JSON, the structure of a request like that
     * {"Operation": operation,
     *   "Word": word
     *   "Result": result
     * }
     */
    public Map<String, String> decodeRequest(JSONObject json) {
 	   Map<String, String> reply = new HashMap<String, String>();
	       try {
	    	   
	       		Iterator ite = json.keys();
	       		
	       		while(ite.hasNext()) {
	       			String key = ite.next().toString();
	       			String value = json.get(key).toString();
	       			reply.put(key, value);
	       		}
	       	} catch (JSONException e) {
	       		e.printStackTrace();
	       	}
	       	return reply;  	
    }
    
    // handle the request and reply
    public void handleRequest(JSONObject json) {
    	try {
    		//Create an instance of OperationReply to reply for a request
    		OperationReply or = new OperationReply(words);
    		Map<String, String> reply = new HashMap<String, String>();//the result of an operation
    		// get the request map
            Map<String, String> request = decodeRequest(json);
            String result = "";
            switch (request.get("Operation")) {
                // Add a new word
                case "Add":
                	result = or.addWord(request.get("Word"), request.get("Meaning"));
                	break;
                case "Query":
                	//System.out.println("***" + .get("Word"));
                	result = or.query(request.get("Word"));
                	break;
                case "Remove":
                	result = or.remove(request.get("Word"));
                	break;
                default:
                	result = "Illegal operation!";
                	break;
            }
            reply.put("Result", result);
            //encrypt to JSON 
            JSONObject jsonReply = new JSONObject(reply);
            System.out.println(jsonReply.toString());
            dos.writeUTF(jsonReply.toString());
            dos.flush();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} 
    }
    
}
