import jssc.SerialPort;
import jssc.SerialPortList;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

@SuppressWarnings("unused")
public class COMTest {
	String COMName;
	static SerialPort serialPort;
	static String tmp="";
	static boolean finished=false;
	static String t;
	
	public COMTest (String COMName)
	{
		this.COMName = COMName;
		 
	}
	
	public String getString() {
		int i=0;
		while ((!finished)&&(i<20))
		{
			i++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	
	public String getT() throws SerialPortException {
		tmp="";
		finished=false;
		serialPort.writeBytes("temp\r\n".getBytes());
		String str=getString();
		return new StringBuilder(str).insert(str.length()-1, ".").toString();
	}
	public String getH() throws SerialPortException {
		tmp="";
		finished=false;
		serialPort.writeBytes("humid\r\n".getBytes());
		String str=getString();
		return new StringBuilder(str).insert(str.length()-1, ".").toString();
		
	}
	public String getP() throws SerialPortException {
		tmp="";
		finished=false;
		serialPort.writeBytes("press\r\n".getBytes()); 
		return getString();
	}
	public void run (String baud,String data,String stop)
	{
		serialPort = new SerialPort(COMName);
		try {
            serialPort.openPort();//Open port
            serialPort.setParams(Integer.parseInt(baud) , Integer.parseInt(data), Integer.parseInt(stop), 0);//Set params
            int mask = SerialPort.MASK_RXCHAR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
            
        }
        catch (SerialPortException ex) {
            ex.printStackTrace();
        }
	}
	 static class SerialPortReader implements SerialPortEventListener {

	        public void serialEvent(SerialPortEvent event) {
	        	if (event.isRXCHAR() && event.getEventValue() > 0)
	                    try {
	                    	for (byte b:serialPort.readBytes())
	                    	{
	                    		System.out.println(b);
		                    	if (b=='\r')
		                    	{
		                    	}
		                    	else if (b=='\n')
		                    	{
		                    		finished=true;
		                    	}
		                    	else
		                    	{
		                    		tmp=tmp.concat(new String(new byte[] {b}));
		                    	}
	                    	}
	                    }
	                    catch (Exception ex) {
	                        System.out.println(ex);
	                    }
	        }
	 }


}

