import javax.swing.filechooser.FileFilter;
import java.io.File;

public class CSVFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return (f.isDirectory() || f.getName().toLowerCase().endsWith(".csv"))?true:false;
    }
    @Override
    public String getDescription() {
        return "CSV files";
    }
}  