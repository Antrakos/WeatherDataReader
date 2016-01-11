import jssc.SerialPortList;
import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

//Main class
public class UserInreface {
    private static final String[][] dataBase = {{"", "", ""}};
    public static String version = "2.1.2";
    static COMTest connection;
    static String humidity;
    static String pressure;
    static String temperature;
    //Declare variables
    private static JFrame frame1;
    private static Container pane;
    private static DataReader dataReader;
    private static Map<String, WeatherData> weatherData;
    private static ArrayList<WeatherData> usedWeatherData;
    private static String[] dataNames = {"Температура", "Вологість", "Тиск", "Пропустити"};
    private static char[] charNames = {'t', 'h', 'p'};
    private static WeatherDate weatherDate;
    private static JMenuBar menuBar;
    private static JMenu menu;
    private static JMenuItem menuItem;
    private static JMenuItem menuItem_1;
    private static JMenuItem menuItem_2;
    private static JMenuItem menuItem_3;
    private static JTable table_1;
    private static int tableWidth = 350;
    private static DefaultTableModel dtm;
    private static JPanel pan;
    private static JPanel panel;
    private static JScrollPane scrollPane;
    private static File openedFile;
    private static JLabel lab;
    private static String[] columnNames = {
            "Дата",
            "Температура",
            "Вологість"
    };
    private static TableModelListener tableModelListener;
    private static JMenu menu_1;
    private static JMenu menu_2;

    private static JMenuItem menuItem_6;
    private static JMenuItem menuItem_7;
    private static JMenu menu_3;
    private static JMenuItem menuItem_8;
    private static JMenuItem menuItem_9;
    private static JMenuItem menuItem_10;
    private static JMenu menu_4;
    private static JMenuItem menuItem_11;
    private static JMenuItem menuItem_12;
    private static JMenuItem menuItem_4;


    public static void main(String args[]) {
        //Set Look and Feel
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            }
        }

        //Create the frame
        frame1 = new JFrame("Weather Data Reader");
        frame1.setMinimumSize(new Dimension(800, 600));
        pane = frame1.getContentPane();
        pane.getInsets();
        pane.setLayout(new BorderLayout());

        CloseListener cl = new CloseListener(
                "Ви впевнені, що хочете вийти",
                "Вихід");
        frame1.addWindowListener(cl);

        // add MenuBar
        menuBar = new JMenuBar();
        frame1.setJMenuBar(menuBar);
        menu = new JMenu("\u0424\u0430\u0439\u043B");
        menu_1 = new JMenu("\u0413\u0440\u0430\u0444\u0456\u043A");

        menu_3 = new JMenu("\u0414\u043E\u043F\u043E\u043C\u043E\u0433\u0430");

        menuItem = new JMenuItem("\u0412\u0456\u0434\u043A\u0440\u0438\u0442\u0438");
        menuItem.setIcon(new ImageIcon(UserInreface.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
        menuItem_1 = new JMenuItem("\u0417\u0430\u043A\u0440\u0438\u0442\u0438 \u0444\u0430\u0439\u043B");
        menuItem_1.setIcon(new ImageIcon(UserInreface.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
        menuItem_7 = new JMenuItem("\u0417\u0431\u0435\u0440\u0435\u0433\u0442\u0438");
        menuItem_7.setIcon(new ImageIcon(UserInreface.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
        menuItem_2 = new JMenuItem("\u0417\u0431\u0435\u0440\u0435\u0433\u0442\u0438 \u044F\u043A");
        //menuItem_3 = new JMenuItem("\u0421\u0442\u0432\u043E\u0440\u0438\u0442\u0438");
        //menuItem_3.setIcon(new ImageIcon(UserInreface.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));

        //menu.add(menuItem_3);
        menu.add(menuItem);
        menu.add(menuItem_7);
        menu.add(menuItem_2);

        menuItem_4 = new JMenuItem("\u0412\u0438\u0433\u043B\u044F\u0434 \u0442\u0430\u0431\u043B\u0438\u0446\u0456");
        menu.add(menuItem_4);
        menu.add(menuItem_1);
        menu.addSeparator();
        menu.add(new ExitAction());


        menuItem_6 = new JMenuItem("\u0421\u0442\u0435\u0440\u0442\u0438");
        menuItem_12 = new JMenuItem("Намалювати графік");

        menu_1.add(menuItem_12);
        menu_1.add(menuItem_6);

        menuItem_8 = new JMenuItem("\u041D\u0430\u043B\u0430\u0448\u0442\u0443\u0432\u0430\u043D\u043D\u044F");
        menuItem_8.setEnabled(false);
        menuItem_9 = new JMenuItem("\u0414\u043E\u043F\u043E\u043C\u043E\u0433\u0430");
        menuItem_9.setIcon(new ImageIcon(UserInreface.class.getResource("/com/sun/java/swing/plaf/windows/icons/Question.gif")));
        menuItem_9.setEnabled(false);
        menuItem_10 = new JMenuItem("\u041F\u0440\u043E \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u0443");
        menuItem_10.setIcon(new ImageIcon(UserInreface.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));


        menu_3.add(menuItem_8);
        menu_3.add(menuItem_9);
        menu_3.add(menuItem_10);

        menuBar.add(menu);
        menuBar.add(menu_1);
        menuBar.add(menu_3);

        menu_4 = new JMenu("\u041F\u0440\u0438\u0441\u0442\u0440\u0456\u0439");
        menuBar.add(menu_4);
        menuItem_11 = new JMenuItem("\u041D\u043E\u0432\u0435 \u0432\u0456\u043A\u043D\u043E");
        menuItem_11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                updateTable(new DefaultTableModel(dataBase, columnNames));
                lab = new JLabel("Choose the right COM");
                dtm = new DefaultTableModel(dataBase, columnNames);
                dtm.removeRow(0);
                table_1 = new JTable(dtm);
                weatherDate = new WeatherDate();
                usedWeatherData = new ArrayList<WeatherData>();
                usedWeatherData.add(new WeatherData('t'));
                usedWeatherData.add(new WeatherData('h'));
                usedWeatherData.add(new WeatherData('p'));


                humidity = "null";
                temperature = "null";
                pressure = "null";

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (connection != null)
                            try {
                                humidity = connection.getH();
                                temperature = connection.getT();
                                pressure = connection.getP();
                                lab.setText("Температура " + temperature + " Вологість " + humidity + " Тиск " + pressure);
                            } catch (Exception ex) {
                            }


                    }
                }, 0, 500);

                String listData[] = SerialPortList.getPortNames();
                @SuppressWarnings({"unchecked", "rawtypes"})
                JComboBox listbox = new JComboBox(listData);
                listbox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        @SuppressWarnings("rawtypes")
                        JComboBox cb = (JComboBox) e.getSource();
                        String COMName = (String) cb.getSelectedItem();

                        JFrame comFrame = new JFrame("COM Settings");
                        comFrame.getContentPane().setLayout(new VerticalLayout());
                        JTextField baud = new JTextField();
                        JTextField data = new JTextField();
                        JTextField stop = new JTextField();
                        comFrame.getContentPane().add(new JLabel("baud"));
                        comFrame.getContentPane().add(baud);
                        comFrame.getContentPane().add(new JLabel("data"));
                        comFrame.getContentPane().add(data);
                        comFrame.getContentPane().add(new JLabel("stop"));
                        comFrame.getContentPane().add(stop);
                        JButton b1 = new JButton("За замовчуванням");
                        b1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                baud.setText("38400");
                                data.setText("8");
                                stop.setText("1");
                                comFrame.revalidate();
                                comFrame.repaint();
                            }
                        });
                        JButton b2 = new JButton("Готово");
                        b2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                connection = new COMTest(COMName);
                                connection.run(baud.getText(), data.getText(), stop.getText());
                                comFrame.setVisible(false);
                            }
                        });
                        JPanel l = new JPanel(new GridLayout(1, 2));
                        l.add(b1);
                        l.add(b2);
                        comFrame.getContentPane().add(l);
                        comFrame.pack();
                        comFrame.setLocationRelativeTo(null);
                        comFrame.setVisible(true);
                    }
                });

                scrollPane = new JScrollPane(table_1);
                scrollPane.setPreferredSize(new Dimension(tableWidth, 0));
                scrollPane.setName("table");
                panel = new JPanel();
                panel.setName("panel");
                panel.setLayout(new BorderLayout());

                JPanel listPanel = new JPanel();
                listPanel.setLayout(new BorderLayout());
                JButton btnAdd = new JButton("Додати");
                JButton btnDel = new JButton("Видалити");
                btnDel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int numRows = table_1.getSelectedRows().length;
                        for (int i = 0; i < numRows; i++) {
                            int rem = table_1.getSelectedRow();
                            dtm.removeRow(rem);
                            weatherDate.removeDate(rem);
                            for (WeatherData g : usedWeatherData)
                                g.removeData(rem);
                        }
                    }
                });
                btnAdd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {

                        Date curretndate = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        String date = sdf.format(curretndate);
                        boolean b = false;
                        try {

                            b = (dtm.getValueAt(dtm.getRowCount() - 1, 0)).equals(date);
                        } catch (Exception ex) {
                        }
                        if (!b) {
                            weatherDate.addDate(date);
                            usedWeatherData.get(0).addData(temperature);
                            usedWeatherData.get(1).addData(humidity);
                            usedWeatherData.get(2).addData(pressure);
                            dtm.addRow(new Object[]{date, temperature, humidity, pressure});

                            dtm.fireTableDataChanged();
                        }

                    }
                });

                lab.setFont(new Font("Calibri", Font.ITALIC, 24));
                JPanel listPanel1 = new JPanel();
                JPanel listPanel2 = new JPanel();

                listPanel1.add(listbox);
                listPanel1.add(btnAdd);
                listPanel1.add(btnDel);
                listPanel2.add(lab);
                listPanel.add(listPanel1, BorderLayout.NORTH);
                listPanel.add(listPanel2, BorderLayout.CENTER);
                panel.add(listPanel, BorderLayout.NORTH);

                panel.add(scrollPane, BorderLayout.CENTER);
                pan = new JPanel();
                pan.setName("chart");
                menuItem_12.setEnabled(true);
                pane.add(BorderLayout.CENTER, pan);
                pane.add(BorderLayout.WEST, panel);
                pane.revalidate();
                pane.repaint();

            }
        });
        menu_4.add(menuItem_11);

        // add ActionListeners for menuItems
        //closeFile
        menuItem_1.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent arg0) {
                 Component[] comp = pane.getComponents();
                 int count = 0;
                 for (Component c : comp) {
                     if (c.getName() == pan.getName()) {
                         pane.remove(count);
                         count--;
                     }
                     if (c.getName() == panel.getName()) {
                         pane.remove(count);
                         count--;
                     }
                     count++;
                 }
                 updateTable(new DefaultTableModel(dataBase, columnNames));
                 frame1.setTitle("Weather Data Reader");

                 menuItem_12.setEnabled(false);
                 menuItem_4.setEnabled(false);
                 openedFile = null;
                 connection = null;
             }
         }
        );
        //saveAs
        menuItem_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }

        });
        //saveFile
        menuItem_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
                    String[][] tableData = new String[nRow][nCol];
                    for (int i = 0; i < nRow; i++)
                        for (int j = 0; j < nCol; j++) {
                            tableData[i][j] = (String) dtm.getValueAt(i, j);
                            if (j == 0) weatherDate.setDate(i, tableData[i][j]);
                            else usedWeatherData.get(j - 1).setData(i, tableData[i][j]);
                        }
                    write(openedFile.toString(), tableData, nRow, nCol);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Помилка збереження файлу!");
                }
            }
        });
        //openFile
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                openFile();
            }
        });
        //Advance
        menuItem_12.addActionListener(new ActionListener() {
            @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
            @Override
            public void actionPerformed(ActionEvent arg0) {
                final JFrame f = new JFrame();
                f.setMaximumSize(new Dimension(100, 100));
                f.getContentPane().setLayout(new VerticalLayout());
                String[] nameList = new String[]{usedWeatherData.get(0).getName(), usedWeatherData.get(1).getName()};
                final JComboBox list1 = new JComboBox(nameList);
                boolean h = false;

                ArrayList<String> hList = new ArrayList<String>();
                String[] dateList = new String[weatherDate.getSize()];
                for (int i = 0; i < weatherDate.getSize(); i++) {
                    dateList[i] =
                            weatherDate.getDate(i);
                    if (weatherDate.getFullDate(i).getHours() != 0) {
                        h = true;
                        if (i > 0) {
                            hList.add(Integer.toString(weatherDate.getFullDate(i).getHours()));
                        } else {
                            hList.add(Integer.toString(weatherDate.getFullDate(i).getHours()));
                        }

                    }
                }
                HashSet<String> hs = new HashSet<String>();
                hs.addAll(hList);
                hList.removeAll(hList);
                hList.addAll(hs);
                Collections.sort(hList);

                String[] hoList = new String[hList.size()];
                hoList = hList.toArray(hoList);
                final String[] hourList = hoList;


                final JComboBox list2 = new JComboBox(dateList);
                final JComboBox list3 = new JComboBox(dateList);
                list3.setSelectedIndex(dateList.length - 1);
                f.getContentPane().add(new JLabel("Виберіть графік та кількість точок(мінімально 4)"));
                f.getContentPane().add(new JLabel("Графік:"));
                f.getContentPane().add(list1);
                f.getContentPane().add(new JLabel("Дата початку:"));
                f.getContentPane().add(list2);
                f.getContentPane().add(new JLabel("Дата кінця:"));
                f.getContentPane().add(list3);
                final boolean k = h;
                final JComboBox hourList1 = new JComboBox(hourList);
                final JComboBox hourList2 = new JComboBox(hourList);
                hourList2.setSelectedIndex(hourList.length - 1);
                if (h) {

                    f.getContentPane().add(new JLabel("Година початку:"));
                    f.getContentPane().add(hourList1);
                    f.getContentPane().add(new JLabel("Година кінця:"));
                    f.getContentPane().add(hourList2);
                }

                JButton b1 = new JButton("Обновити");
                b1.setToolTipText("Дізнатись кількість точок");
                JButton b2 = new JButton("Готово");
                JPanel l = new JPanel(new GridLayout(1, 2));
                l.add(b1);
                l.add(b2);
                b1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        if (k) {
                            int count = 0;
                            for (int i = list2.getSelectedIndex(); i <= list3.getSelectedIndex(); i++) {

                                if (weatherDate.getFullDate(i).getHours() >=
                                        Integer.parseInt(hourList[hourList1.getSelectedIndex()]) &&
                                        weatherDate.getFullDate(i).getHours() <=
                                                Integer.parseInt(hourList[hourList2.getSelectedIndex()])) {
                                    count++;
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Вибрано точок: " + count);
                        } else
                            JOptionPane.showMessageDialog(null, "Вибрано точок: " + (list3.getSelectedIndex() - list2.getSelectedIndex() + 1));


                    }
                });

                b2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        int s = list2.getSelectedIndex();
                        int e = list3.getSelectedIndex();
                        int c = list1.getSelectedIndex();
                        int hs = 0, he = 24;
                        int count = 4;
                        if (k) {
                            hs = Integer.parseInt(hourList[hourList1.getSelectedIndex()]);
                            he = Integer.parseInt(hourList[hourList2.getSelectedIndex()]);
                            count = 0;
                            for (int i = list2.getSelectedIndex(); i <= list3.getSelectedIndex(); i++) {

                                if (weatherDate.getFullDate(i).getHours() >=
                                        Integer.parseInt(hourList[hourList1.getSelectedIndex()]) &&
                                        weatherDate.getFullDate(i).getHours() <=
                                                Integer.parseInt(hourList[hourList2.getSelectedIndex()])) {
                                    count++;
                                }
                            }
                        }
                        if ((s + 3) > e || he < hs || count < 4) {
                            JOptionPane.showMessageDialog(null, "Помилково вибраний період: мінамально 4 точки");
                        } else {
                            f.setVisible(false);
                            ChartDemo chart = null;

                            try {
                                chart = new ChartDemo(weatherDate, usedWeatherData.get(c), weatherDate.getFullDate(s), weatherDate.getFullDate(e), hs, he);
                                int k = 200;
                                if ((pane.getWidth() - scrollPane.getWidth()) > k) {
                                    k = pane.getWidth() - scrollPane.getWidth();
                                }
                                if (pan.getWidth() > k) {
                                    k = pan.getWidth();
                                }
                                pan.add(chart.run((int) Math.round(k * 0.95), (int) Math.round(pane.getHeight() * 0.45)));


                                frame1.repaint();
                                frame1.revalidate();
                            } catch (Exception ex) {
                            }


                        }
                    }
                });

                f.getContentPane().add(l);
                f.setLocationRelativeTo(frame1);
                f.pack();
                f.setVisible(true);

            }
        });
        //Close chartPanel
        menuItem_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                pan = new JPanel();
                pan.setName("chart");
                Component[] comp = pane.getComponents();
                int count = 0;
                for (Component c : comp) {
                    if (c.getName() == pan.getName()) {
                        pane.remove(count);
                        c = pan;
                        pane.add(BorderLayout.CENTER, c);
                    }
                    count++;
                }
                menuItem_12.setEnabled(true);
                pane.repaint();

            }
        });
        //AboutUs
        menuItem_10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrame p = new JFrame("Про програму");
                JLabel l = new JLabel("Про програму");
                l.setHorizontalAlignment(SwingConstants.CENTER);
                l.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 44));
                p.getContentPane().add(BorderLayout.NORTH, l);
                p.getContentPane().add(BorderLayout.CENTER, new JLabel("Version: " + version));
                p.getContentPane().add(BorderLayout.SOUTH, new JLabel("Author: Zubrei Taras"));
                p.setLocationRelativeTo(null);
                p.pack();
                p.setVisible(true);
            }
        });
        //TableView
        menuItem_4.setEnabled(false);
        menuItem_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFrame openFrame = new JFrame("Data Settings");
                openFrame.setMinimumSize(new Dimension(230, 100));
                openFrame.getContentPane().setLayout(new VerticalLayout());
                String[][] data = new String[weatherDate.getSize()][3];

                JComboBox<String> col1 = new JComboBox<String>(dataNames);
                JComboBox<String> col2 = new JComboBox<String>(dataNames);
                openFrame.getContentPane().add(new JLabel("Перший стовпець"));
                openFrame.getContentPane().add(col1);
                openFrame.getContentPane().add(new JLabel("Другий стовпець"));
                openFrame.getContentPane().add(col2);
                JButton b2 = new JButton("Готово");
                b2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (col1.getSelectedIndex() == col2.getSelectedIndex() || col1.getSelectedIndex() == 3 || col2.getSelectedIndex() == 3)
                                throw new Exception();
                            usedWeatherData = new ArrayList<WeatherData>();
                            usedWeatherData.add(weatherData.get(String.valueOf(charNames[col1.getSelectedIndex()])));
                            usedWeatherData.add(weatherData.get(String.valueOf(charNames[col2.getSelectedIndex()])));
                            columnNames[1] = usedWeatherData.get(0).getName();
                            columnNames[2] = usedWeatherData.get(1).getName();
                            columnNames[2] = usedWeatherData.get(1).getName();
                            for (int i = 0; i < weatherDate.getSize(); i++) {
                                data[i][0] = weatherDate.getDate(i);
                                data[i][1] = Double.toString(usedWeatherData.get(0).getData(i));
                                data[i][2] = Double.toString(usedWeatherData.get(1).getData(i));
                            }

                            dtm = new DefaultTableModel(data, columnNames);
                            updateTable(dtm);
                            openFrame.setVisible(false);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Неправильно обрані дані!");
                        }
                    }
                });
                openFrame.getContentPane().add(b2);
                openFrame.pack();
                openFrame.setLocationRelativeTo(null);
                openFrame.setVisible(true);
            }
        });


        JLabel southLabel = new JLabel("Developed by Taras Zubrei. 2014-2015");
        southLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 10));
        southLabel.setAlignmentX(0);
        pane.add(BorderLayout.SOUTH, southLabel);

        //createTable
        dtm = new DefaultTableModel(dataBase, columnNames);
        table_1 = new JTable(dtm);


        scrollPane = new JScrollPane(table_1);
        scrollPane.setPreferredSize(new Dimension(tableWidth, 0));
        scrollPane.setName("table");
        pane.add(BorderLayout.WEST, scrollPane);
        setTableModelListener();
        panel = new JPanel();
        panel.setName("panel");
        pan = new JPanel();
        pan.setName("chart");

        //Set frame visible
        frame1.setLocationRelativeTo(null);
        frame1.setBackground(Color.WHITE);
        frame1.pack();
        frame1.setVisible(true);
    }

    //saveFile()
    private static void saveFile() {
        try {
            int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
            String[][] tableData = new String[nRow][nCol];
            for (int i = 0; i < nRow; i++)
                for (int j = 0; j < nCol; j++) {
                    tableData[i][j] = (String) dtm.getValueAt(i, j);
                    if (j == 0) weatherDate.setDate(i, tableData[i][j]);
                    else {
                        usedWeatherData.get(j - 1).setData(i, tableData[i][j]);
                    }
                }

            JFileChooser filesave = new JFileChooser();
            filesave.setCurrentDirectory(new File("."));
            filesave.setFileFilter(
                    new CSVFileFilter()
            );
            int ret = filesave.showDialog(null, "Зберегти файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = filesave.getSelectedFile();
                if (!file.getName().matches("[a-zA-Z_0-9].csv")) {
                    file.renameTo(new File(file.getName() + ".csv"));
                }
                write(file.toString(), tableData, nRow, nCol);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Помилка збереження файлу!");
        }
    }

    //openFile()
    private static void openFile() {
        JFileChooser fileopen = new JFileChooser();
        fileopen.setCurrentDirectory(new File("."));
        fileopen.setFileFilter(
                new CSVFileFilter()
        );
        int ret = fileopen.showDialog(null, "Відкрити файл");

        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            openedFile = file;
            dataReader = new DataReader(file.toString());
            weatherDate = new WeatherDate(dataReader.run(0));
            weatherData = new HashMap<String, WeatherData>();
            usedWeatherData = new ArrayList<WeatherData>();
            String[][] data = new String[weatherDate.getSize()][3];

            //asd
            JFrame openFrame = new JFrame("Data Settings");
            openFrame.setMinimumSize(new Dimension(230, 100));
            openFrame.getContentPane().setLayout(new VerticalLayout());

            JLabel jl = new JLabel();

            jl.setFont(new Font("Calibri", Font.BOLD, 20));
            jl.setText("Виберіть тип даних у файлі: ");
            openFrame.add(jl);


            @SuppressWarnings("rawtypes")
            ArrayList<JComboBox> dataChoosers = new ArrayList<JComboBox>();
            for (int i = 0; i < dataReader.getSize(); i++) {
                JComboBox<String> cb = new JComboBox<String>(dataNames);
                cb.setSelectedIndex(3);
                dataChoosers.add(cb);
            }

            for (int i = 1; i < dataReader.getSize(); i++) {
                openFrame.getContentPane().add(new JLabel((i) + "рядок:"));
                openFrame.getContentPane().add(dataChoosers.get(i - 1));
            }
            JComboBox<String> col1 = new JComboBox<String>(dataNames);
            JComboBox<String> col2 = new JComboBox<String>(dataNames);
            JLabel jk = new JLabel();

            jk.setFont(new Font("Calibri", Font.BOLD, 20));
            jk.setText("Виберіть дані для роботи: ");
            openFrame.add(jk);
            openFrame.getContentPane().add(new JLabel("Перший стовпець"));
            openFrame.getContentPane().add(col1);
            openFrame.getContentPane().add(new JLabel("Другий стовпець"));
            openFrame.getContentPane().add(col2);
            JButton b1 = new JButton("Тест");
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int[] elements = new int[dataChoosers.size() - 1];

                    for (int i = 0; i < dataReader.getSize() - 1; i++)
                        if (dataChoosers.get(i).getSelectedIndex() != 3) {
                            elements[i] = dataChoosers.get(i).getSelectedIndex();
                            weatherData.put(String.valueOf(charNames[elements[i]]), new WeatherData(charNames[elements[i]], dataReader.run(i + 1)));
                        }
                    try {
                        if (col1.getSelectedIndex() == col2.getSelectedIndex()) throw new Exception();
                        //if (!uniq(elements)) throw new Exception();
                        JFrame f = new JFrame("Тест");
                        JScrollPane p = new JScrollPane(new JTable(new String[][]{{weatherDate.getDate(0),
                                Double.toString(weatherData.get(String.valueOf(charNames[col1.getSelectedIndex()])).getData(0)),
                                Double.toString(weatherData.get(String.valueOf(charNames[col2.getSelectedIndex()])).getData(0))
                        }}, new String[]{"Дата",
                                weatherData.get(String.valueOf(charNames[col1.getSelectedIndex()])).getName(),
                                weatherData.get(String.valueOf(charNames[col2.getSelectedIndex()])).getName()}));
                        p.setPreferredSize(new Dimension(400, 100));
                        f.add(p);
                        f.pack();
                        f.setLocationRelativeTo(null);
                        f.setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Неправильно обрані дані!");
                        ex.printStackTrace();
                    }
                }
            });
            JButton b2 = new JButton("Готово");
            b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Integer> elements = new ArrayList<Integer>();

                    int i = 0;
                    while (i < dataReader.getSize() - 1) {
                        if (dataChoosers.get(i).getSelectedIndex() != 3) {
                            elements.add(dataChoosers.get(i).getSelectedIndex());
                            weatherData.put(String.valueOf(charNames[elements.get(i)]), new WeatherData(charNames[elements.get(i)], dataReader.run(i + 1)));

                        }
                        i++;
                    }

                    try {
                        if (col1.getSelectedIndex() == col2.getSelectedIndex()) throw new Exception();
                        if (!uniq(elements)) throw new Exception();
                        usedWeatherData = new ArrayList<WeatherData>();
                        usedWeatherData.add(weatherData.get(String.valueOf(charNames[col1.getSelectedIndex()])));
                        usedWeatherData.add(weatherData.get(String.valueOf(charNames[col2.getSelectedIndex()])));
                        columnNames[1] = usedWeatherData.get(0).getName();
                        columnNames[2] = usedWeatherData.get(1).getName();
                        for (i = 0; i < weatherDate.getSize(); i++) {
                            data[i][0] = weatherDate.getDate(i);
                            data[i][1] = Double.toString(usedWeatherData.get(0).getData(i));
                            data[i][2] = Double.toString(usedWeatherData.get(1).getData(i));
                        }
                        frame1.setTitle("Weather Data Reader - [" + file.getAbsolutePath() + "]");
                        menuItem_12.setEnabled(true);
                        menuItem_4.setEnabled(true);

                        dtm = new DefaultTableModel(data, columnNames);
                        updateTable(dtm);
                        pan = new JPanel();
                        pan.setName("chart");
                        Component[] comp = pane.getComponents();
                        int count = 0;
                        for (Component c : comp) {
                            if (c.getName() == pan.getName()) {
                                pane.remove(count);
                                count--;
                            }
                            if (c.getName() == panel.getName()) {
                                pane.remove(count);
                                count--;
                            }
                            count++;
                        }
                        pane.add(BorderLayout.CENTER, pan);
                        openFrame.setVisible(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Неправильно обрані дані!");
                        ex.printStackTrace();
                    }
                }
            });
            JPanel l = new JPanel(new GridLayout(1, 2));
            l.add(b1);
            l.add(b2);
            openFrame.getContentPane().add(l);
            openFrame.pack();
            openFrame.setLocationRelativeTo(null);
            openFrame.setVisible(true);

            //asd


        }
    }

    private static void write(String fileName, String[][] text, int nRow, int nCol) {
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                for (int i = 0; i < nRow; i++) {
                    for (int j = 0; j < nCol; j++)
                        out.print(text[i]
                                [j] + ";");
                    //out.println();
                }

            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //JTable cell listener
    private static void setTableModelListener() {
        tableModelListener = new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {

                    int j = e.getColumn();
                    int i = e.getFirstRow();
                    String value = (String) table_1.getModel().getValueAt(e.getFirstRow(), e.getColumn());
                    if (isNumeric(value)) usedWeatherData.get(j - 1).setData(i, value);
                    else {
                        JOptionPane.showMessageDialog(null, "Помилково введені дані!");
                        table_1.setValueAt(Double.toString(usedWeatherData.get(j - 1).getData(i)), i, j);
                    }
                }
            }
        };
        table_1.getModel().addTableModelListener(tableModelListener);
    }

    // Create table
    private static JTable createTable(DefaultTableModel dtm) {

        table_1 = new JTable(dtm) {

            private static final long serialVersionUID = 1L;

            @SuppressWarnings({"rawtypes", "unchecked"})
            @Override// Returning the Class of each column will allow different renderers
            public Class getColumnClass(int column) { // to be used based on Class
                return getValueAt(0, column).getClass();
            }


            @Override //  The Cost is not editable
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 0:
                        return false;
                    default:
                        return true;
                }
            }

        };
        table_1.getColumnModel().getColumn(0).setPreferredWidth((int) Math.round(tableWidth * 0.4));
        table_1.getColumnModel().getColumn(1).setPreferredWidth((int) Math.round(tableWidth * 0.3));
        table_1.getColumnModel().getColumn(2).setPreferredWidth((int) Math.round(tableWidth * 0.3));

        if (pane.getWidth() * 0.35 > tableWidth)
            tableWidth = (int) Math.round(pane.getWidth() * 0.3);
        setTableModelListener();

        return table_1;
    }

    private static void updateTable(DefaultTableModel dtm) {
        Component[] comp = pane.getComponents();
        int count = 0;
        for (Component c : comp) {
            if (c.getName() == scrollPane.getName()) {
                JViewport viewport = ((JScrollPane) c).getViewport();
                JTable tab = (JTable) viewport.getView();
                tab = createTable(dtm);
                pane.remove(count);
                scrollPane = new JScrollPane(tab);
                scrollPane.setPreferredSize(new Dimension(tableWidth, 0));
                scrollPane.setName("table");
                pane.add(BorderLayout.WEST, scrollPane);
            }
            count++;
        }

        pane.repaint();
        pane.revalidate();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static boolean uniq(ArrayList<Integer> arr) {
        boolean arg = true;
        for (int i = 0; i < arr.size(); i++)
            for (int j = i + 1; j < arr.size(); j++)
                if (arr.get(i) == arr.get(j)) arg = false;
        return arg;
    }
}