package dibugger.FileHandler.Exceptions;

public class InvalidLineTypeExeption extends FileHandlerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidLineTypeExeption() {

	}
	
	@Override
	public String getID() {
		return "exc_invalid_line_type";
	}

}