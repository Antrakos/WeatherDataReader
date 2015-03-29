import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeatherDate {
    private ArrayList<Date> data = null;
    private SimpleDateFormat dateFormat;
    private static String reg;
    @SuppressWarnings("serial")
	private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{1,2}.\\d{1,2}.\\d{4}$", "dd.MM.yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}.\\d{1,2}.\\d{4}\\s\\d{1,2}:\\d{2}$", "dd.MM.yyyy HH:mm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{1,2}.\\d{1,2}.\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd.MM.yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
        
    }};

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    public static String determineDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                reg = regexp;
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null; // Unknown format.
    }
    
    public WeatherDate (){this.data = new ArrayList<Date>();}
    
    public WeatherDate (ArrayList<String> d)
    {
    	this.data = new ArrayList<>();
        dateFormat = new SimpleDateFormat(determineDateFormat(d.get(0)));
        for (int i = 0; i < d.size(); i++)
        	try
        	{
                if (!d.get(i).toLowerCase().matches(reg)) 
                {dateFormat = new SimpleDateFormat(determineDateFormat(d.get(i))); System.out.println("123");}
        	this.data.add(dateFormat.parse(d.get(i)));
        	} catch (Exception ex) {ex.printStackTrace();}
    	
    }
    public String getDate (int i)
    {
        return  dateFormat.format(data.get(i));
    }
    public int getSize ()
    {
        return this.data.size();
    }
    public Date getFullDate (int i)
    {
        return  data.get(i);
    }
    
    public void setDate (int i, String d)
    {
        try {this.data.set(i,new SimpleDateFormat(determineDateFormat(d)).parse(d));} catch (Exception ex) {}
    }
    public void addDate (String d)
    {
        try 
        {
        	
        	this.data.add(new SimpleDateFormat(determineDateFormat(d)).parse(d));
        	if (dateFormat == null) {dateFormat = new SimpleDateFormat(determineDateFormat(d));}
        } catch (Exception ex) {}
    }
    public void removeDate (int i)
    {
    	this.data.remove(i);
    }
    
    public ArrayList<Date> getDate ()
    {
        return data;
    }

}
