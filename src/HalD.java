import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.jibble.jmegahal.JMegaHal;

public class HalD {
	static JMegaHal hal;
	
	public HalD() throws IOException{
		   hal = new JMegaHal();
		   BufferedReader br = new BufferedReader(new FileReader("/home/floby/dev/sadface/brain_file.txt"));
		   while(br.ready()){
			   hal.add(br.readLine());
		   }

	}
	public static void main(String[] args) throws IOException {
		//na
	}
	public String plsRespond(String input){
		return hal.getSentence(input);
	}

}
