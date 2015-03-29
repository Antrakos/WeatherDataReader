import jssc.SerialPort;
import jssc.SerialPortList;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

@SuppressWarnings("unused")
public class COMTest {
	String COMName;
	static SerialPort serialPort;
	static String tmp;
	static String t;
	
	public COMTest (String COMName)
	{
		this.COMName = COMName;
		 
	}
	public String getT() throws SerialPortException {
		serialPort.writeBytes("temp\r\n".getBytes()); 
		return tmp;
		
	}
	public String getH() throws SerialPortException {
		serialPort.writeBytes("humid\r\n".getBytes()); 
		return tmp;
		
	}
	public String getP() throws SerialPortException {
		serialPort.writeBytes("press\r\n".getBytes()); 
		return tmp;
	}
	public void run (String baud,String data,String stop)
	{
		serialPort = new SerialPort(COMName);
		try {
            serialPort.openPort();//Open port
            serialPort.setParams(Integer.parseInt(baud) , Integer.parseInt(data), Integer.parseInt(stop), 0);//Set params
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
            
        }
        catch (SerialPortException ex) {
            ex.printStackTrace();
        }
	}
	 static class SerialPortReader implements SerialPortEventListener {

	        public void serialEvent(SerialPortEvent event) {
	            if(event.isRXCHAR()){//If data is available
	               tmp = "";
	                    try {
	                    	{
	                    	t = serialPort.readString(1);
	                    	System.out.println(t);
	                    	}
	                    	while (t != "n");
	                    	
	                    	t = serialPort.readString(1);
	                    	{
	                    	t = serialPort.readString(1);
	                    	if (t != "\n" && t != "\r" ) tmp.concat(t);
	                    	}
	                    	while (t != "\n");
	                    }
	                    catch (Exception ex) {
	                        System.out.println(ex);
	                    }
	                
	            }
	           
	    }
	        }


}

