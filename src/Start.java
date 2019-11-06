import java.io.IOException;
import java.io.OutputStream;
import com.fazecast.jSerialComm.SerialPort;
import com.github.terrytsai.escpos.serial.SerialFactory;
import com.github.terrytsai.escpos.serial.config.SerialConfig;


public class Start {

	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length < 2)
			throw new RuntimeException("No arguments set. Call with: java -jar file.jar <COM-Port> \"text\"");
		SerialPort port = SerialFactory.com(Integer.parseInt(args[0]), SerialConfig.CONFIG_9600_8N1());

		OutputStream out;
		Convert c;
		int count = 0;
		int maxTries = 20;
		boolean handled = false;
		while (!handled)
			try {
				port.openPort();
				out = port.getOutputStream();
				c = new Convert(out);
				for (int i = 1; i < args.length; i++)
					c.print(args[i]);

				c.close();
				out.close();
				port.closePort();
				handled = true;
			} catch (ChannelClosedException e) {
				Thread.sleep(2000);
				handled = false;
				if (++count == maxTries)
					handled = true;
		}
	}
}
