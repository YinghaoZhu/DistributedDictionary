package karno.server.pk;
/** 
* @author 887402 Yinghao Zhu E-mail: zhu156323225@icloud.com
* @version time：5Sep.,2018 3:29:29 pm 
* 
*/
public class Main {
	public static void main (String[] args) {
		MainWindow mw = new MainWindow();
		mw.showView(Integer.parseInt(args[0]), args[1]);
	}
      
}
