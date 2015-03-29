import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CSVFileFilter extends FileFilter  
{  
@Override
public boolean accept(File f) {
    // Allow directories to be seen.
    if (f.isDirectory()) return true;
    
    // Allows files with .rtf extension to be seen.
    if(f.getName().toLowerCase().endsWith(".csv"))
        return true;

    // Otherwise file is not shown.
    return false;
}

@Override
public String getDescription() {
	// TODO Auto-generated method stub
	return "‘‡ÈÎË CSV";
}  
}  