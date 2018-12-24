package karno.server.pk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.omg.CORBA.portable.OutputStream;
/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：3Sep.,2018 8:00:30 pm 
* 
*/
public class Server {

	private Map<String, String> words;
	private ServerSocket serversocket;
	private Socket client;
	private DictionaryManagement dm;
	private int port;
	private String path;
	//private ThreadPoolExecutor executor;
	private ExecutorService executor;
	public Server(int port, String path) {
		this.port = port;
		this.path = path;
		//this.executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS, 
				//new LinkedBlockingQueue<Runnable>(5));
		this.executor = Executors.newFixedThreadPool(100);
	}
	
	public void init() {
		//initialized dictionary
		dm = new DictionaryManagement(path);
		words = dm.readFile();
		
		try {
			serversocket = new ServerSocket(port);
			
			while (!serversocket.isClosed()) {
				client = serversocket.accept();
			    executor.execute(new HandlerThread(client, words));
				System.out.println("线程池中线程数目："+((ThreadPoolExecutor) executor).getPoolSize()+"，队列中等待执行的任务数目："+
			             ((ThreadPoolExecutor) executor).getQueue().size()+"，已执行玩别的任务数目："+((ThreadPoolExecutor) executor).getCompletedTaskCount());
				//new HandlerThread(client, words);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void closeServer() {
		// write back to dict.json
		dm.writeFile(words);
		try {
			serversocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
