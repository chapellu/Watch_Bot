package Communication;

public class DateJson {
	
	private String date_string;
	private java.util.Date date;
	
	
	public DateJson() {
		String format = "yyyy-MM-dd-H-mm-ss";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format ); 
		java.util.Date date = new java.util.Date(); 
		
		setDate_string(formater.format( date ));
		this.setDate(date);
	}


	public String getDate_string() {
		return date_string;
	}


	private void setDate_string(String date_string) {
		this.date_string = date_string;
	}


	public java.util.Date getDate() {
		return date;
	}


	private void setDate(java.util.Date date) {
		this.date = date;
	}
	
}
