import java.awt.Color;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

public class ChartDemo extends  JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Color color;
    private static String title;
    private static String xLabel;
    private static String yLabel;
    public static WeatherDate date;
    public static WeatherData base;
    public static Date start;
    public static Date end;
    public static int hs;
    public static int he;

    public ChartPanel run(int width,int heigh)
    {
    	XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel p = new ChartPanel(chart,width,heigh,width,heigh,width,heigh,true, true, true, true, true,true);
        p.setZoomAroundAnchor(true);
        p.setMouseZoomable(true, false);
        return p;
    }
    @SuppressWarnings("static-access")
	public ChartDemo(WeatherDate date,WeatherData base,Date start, Date end,int hs,int he) 
    {
    	this.date= date;
    	this.base= base;
    	this.xLabel = "����";
        this.color = base.getColor();
    	this.title = base.getTitle();
    	this.yLabel = base.getYLabel();
        this.start = start;
        this.end = end;
    	this.hs = hs;
        this.he = he;
    }

    private static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
        	title,  			// title
            xLabel,             // x-axis label
            yLabel,   // y-axis label
            dataset,            // data
            true,               // create legend?
            true,               // generate tooltips?
            false               // generate URLs?
        );
 
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        
        plot.getRenderer().setSeriesPaint(0, color);
        
        
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        if ((date.getDate(0)).length() < 11)
        {
        	axis.setDateFormatOverride(new SimpleDateFormat("dd MMMM yyyy", myDateFormatSymbols));
        }
        else if ((date.getDate(0)).length() < 18)
        {
        	axis.setDateFormatOverride(new SimpleDateFormat("dd MMMM yyyy HH:mm", myDateFormatSymbols));
        }
        else
        {
        	axis.setDateFormatOverride(new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", myDateFormatSymbols));
        }
        
      
        return chart;

    }
    
    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    @SuppressWarnings("deprecation")
	private static XYDataset createDataset() {

        TimeSeries s1 = new TimeSeries(title, Second.class);
        TimeSeries s2 = new TimeSeries(title + ": ������� ��������", Second.class);
        int count = 0; double mid = 0; Date start1 = null,end1 = null; 
        try 
    	{
    	for (int i = 0; i < base.getSize();i++)
    	{
            
            if (date.getFullDate(i).after(start) && date.getFullDate(i).before(end) && date.getFullDate(i).getHours() >= hs && date.getFullDate(i).getHours() <= he)
            {
                mid +=  base.getData(i);
                if (count == 0) start1 = date.getFullDate(i);
                end1 = date.getFullDate(i);;
                count++;
                s1.add(new Second( date.getFullDate(i) ),base.getData(i));
            }
    		
    	}
        mid /= count;
        s2.add(new Second(start1),mid);
        s2.add(new Second(end1),mid);
    	}
    	catch (Exception ex) {}

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        
        
        dataset.setDomainIsPointsInTime(true);

        return dataset;

    }

    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    @SuppressWarnings("serial")
	private static final DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"����", "������", "�������", "�����", "������", "������",
                "�����", "������", "�������", "������", "���������", "������"};
        }
        
    };

}
