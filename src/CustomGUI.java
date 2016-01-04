import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Taras on 27.11.2015.
 */
public class CustomGUI extends JFrame {
    public JTable table1;
    private JScrollPane scrollPanel;
    private JPanel chartPanel;
    private JPanel mainPanel;
    private static ArrayList<WeatherData> weatherData;
    private static WeatherDate weatherDate;
    String[] columnNames = {"Дата","Температура","Вологість"};
    String[][] tableData = new String[1][3];

    public CustomGUI() {
        mainPanel = new JPanel();
        chartPanel = new JPanel();
        table1 = new JTable(tableData,columnNames);
        scrollPanel = new JScrollPane(table1);
        mainPanel.add(BorderLayout.EAST, chartPanel);
        mainPanel.add(BorderLayout.WEST, scrollPanel);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMenu();
        pack();
        setVisible(true);
    }
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Open");
        menuItem.addActionListener(e -> openFile());
        menuItem.setIcon(new ImageIcon(UserInreface.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
        JMenuItem menuItem_1 = new JMenuItem("Close");
        menuItem_1.setIcon(new ImageIcon(UserInreface.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
        JMenuItem menuItem_7 = new JMenuItem("Save");
        menuItem_7.addActionListener(e -> saveFile());
        menuItem_7.setIcon(new ImageIcon(UserInreface.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
        JMenuItem menuItem_2 = new JMenuItem("Save as");
        JMenuItem menuItem_4 = new JMenuItem("Table preview");
        JMenuItem menuItem_0 = new JMenuItem("Exit");
        menuItem_0.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        menu.add(menuItem);
        menu.add(menuItem_7);
        menu.add(menuItem_2);
        menu.add(menuItem_4);
        menu.add(menuItem_1);
        menu.addSeparator();
        menu.add(menuItem_0);
        JMenu menu_1 = new JMenu("Chart");
        JMenuItem menuItem_6 = new JMenuItem("Clear");
        JMenuItem menuItem_12 = new JMenuItem("Paint");
        menu_1.add(menuItem_12);
        menu_1.add(menuItem_6);
        JMenu menu_3 = new JMenu("Help");
        JMenuItem menuItem_8 = new JMenuItem("Preferences");
        menuItem_8.setEnabled(false);
        JMenuItem menuItem_9 = new JMenuItem("Help");
        menuItem_9.setIcon(new ImageIcon(UserInreface.class.getResource("/com/sun/java/swing/plaf/windows/icons/Question.gif")));
        menuItem_9.setEnabled(false);
        JMenuItem menuItem_10 = new JMenuItem("About");
        menuItem_10.setIcon(new ImageIcon(UserInreface.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
        menu_3.add(menuItem_8);
        menu_3.add(menuItem_9);
        menu_3.add(menuItem_10);
        JMenu menu_4 = new JMenu("Device");
        JMenuItem menuItem_11 = new JMenuItem("Connect");
        menu_4.add(menuItem_11);
        menuBar.add(menu);
        menuBar.add(menu_1);
        menuBar.add(menu_3);
        menuBar.add(menu_4);
    }
    private static void saveFile() {
        return;
    }
    private void openFile() {
//        UPDATE SMTH BY USING SETTER!!!!!!!!!!!!!!!!!!!
//        table1.setModel(new DefaultTableModel(tableData,columnNames));
//        scrollPanel.removeAll();
//        repaint();

        JFileChooser fileopen = new JFileChooser();
        fileopen.setCurrentDirectory(new File("."));
        fileopen.setFileFilter(
                new CSVFileFilter()
        );
        int ret = fileopen.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            List<ArrayList<String>> data = new ArrayList<>();
            try (BufferedReader br = Files.newBufferedReader(Paths.get(fileopen.getSelectedFile().getAbsolutePath()))) {
                for (String lines :  br.lines().collect(Collectors.toList()))
                {
                    String[] splitLine = lines.split(";");
                    if (data.isEmpty())
                        for (int i=0;i<splitLine.length;i++)
                            data.add(new ArrayList<>());
                    for (int i=0;i<splitLine.length;i++)
                        data.get(i).add(splitLine[i]);
                }
            }
            catch (IOException e) {e.printStackTrace();}
            weatherDate = new WeatherDate(data.get(0));
            weatherData = new ArrayList<>();
            weatherData.add(new WeatherData('t',data.get(1)));
            weatherData.add(new WeatherData('h',data.get(2)));
            Object[][] tableData = new Object[weatherDate.getSize()][3];
            for (int i=0;i<tableData.length;i++)
            {
                tableData[i][0] = weatherDate.getDate(i);
                tableData[i][1] = Double.toString(weatherData.get(0).getData(i));
                tableData[i][2] = Double.toString(weatherData.get(1).getData(i));
            }
            table1.setModel(new DefaultTableModel(tableData,columnNames));
        }
    }

    public static void main(String[] args0) {
        CustomGUI f = new CustomGUI();
    }

}
