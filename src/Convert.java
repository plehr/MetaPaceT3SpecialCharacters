import java.io.IOException;
import java.io.OutputStream;

public class Convert {
    private OutputStream os;
	private byte[] start = {0x1b,'R',0x02};
	private byte[] end = {0x1b,'d',0x10,0x1d,'V',0x00,0x04};
	private byte[] LF = {0x0d,0x0a};
	
	Convert(OutputStream os) throws IOException{
		this.os = os;

		try {
			os.write(start);
		} catch (Exception e) {
		 throw new ChannelClosedException();
		}
	}
	
	void print(String text) throws IOException {
		text = text.replace((char)220, (char)93).replace((char)252,(char)125).replace((char)214, (char)92).replace((char)196, (char)91).replace((char)228, (char)123).replace((char)223, (char)126).replace((char)246,(char)124);
		for (char c : text.toCharArray())
		os.write(c);
		os.write(LF);
	}
	
	void close() throws IOException {
		os.write(end);
	}
}
