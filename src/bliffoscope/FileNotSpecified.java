package bliffoscope;

/*
 * User defined exception class to catch exception when image file or target list
 * is not specified in properties file
 */
public class FileNotSpecified extends Exception{
	
	private static final long serialVersionUID = 1L;

	public FileNotSpecified() {
		// TODO Auto-generated constructor stub
	}
	
	public FileNotSpecified(String msg){
		super(msg);
	}
}
