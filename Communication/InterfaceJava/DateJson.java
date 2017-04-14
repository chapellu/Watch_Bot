package Communication;

import java.util.Date;

public class DateJson {
	
	private String date_string;
	private java.util.Date date;
	
	
	public DateJson() {
		String format = "yyyy-MM-dd-H-mm-ss";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format ); 
		java.util.Date date = new java.util.Date(); 
		
		date_string = formater.format( date );
		this.date = date;
	}
	
}
