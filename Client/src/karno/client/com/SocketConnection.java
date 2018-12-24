package karno.client.com;
/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：3Sep.,2018 8:00:30 pm 
* 
*/
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.net.UnknownHostException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class SocketConnection {
    private int port;
    private String address;
    private Socket s;
    private DataOutputStream dos;
    private DataInputStream dis ;
    
    public SocketConnection(String address, int port) {
    	this.address = address;
    	this.port = port;
    }
    
    public void connect() throws UnknownHostException, IOException {
		s = new Socket(this.address, this.port);
		s.setSoTimeout(2000);
	    dos = new DataOutputStream(s.getOutputStream());
	    dis = new DataInputStream(s.getInputStream());
    	System.out.println("Connect Successfully");
    }
    
    public void close() {
    	try {
    		s.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void sendRequest(Map<String, String> request) {
    	try {
			JSONObject json = new JSONObject(request);
            System.out.println(json.toString());
            dos.writeUTF(json.toString());  
            dos.flush();
    	}catch (NullPointerException e) {
    		System.out.println(e.getMessage());
    	}
    	catch (IOException e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}   	
    }
    
    public Map<String, String> getReply() throws IOException, JSONException{
    	
    	Map<String, String> reply = new HashMap<String, String>();
    		
	    s.setSoTimeout(10000);
		String strReply = dis.readUTF();
		
		JSONObject json = new JSONObject(strReply);
		
		Iterator<?> ite = json.keys();
		
		while(ite.hasNext()) {
			String key = ite.next().toString();
			String value = json.get(key).toString();
			reply.put(key, value);
		}
    	return reply;  	
    }
}
