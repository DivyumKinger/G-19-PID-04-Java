public class Book implements Identifiable {
	private final int id;
	private final String title;
	private final String author;
	private boolean isIssued;
	private Integer issuedToUserId; // null if not issued
	
	public Book(int id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.isIssued = false;
		this.issuedToUserId = null;
	}
	
	public int getId() { return id; }
	public String getTitle() { return title; }
	public String getAuthor() { return author; }
	public boolean isIssued() { return isIssued; }
	public Integer getIssuedToUserId() { return issuedToUserId; }
	
	public void issueTo(int userId) {
		isIssued = true;
		issuedToUserId = userId;
	}
	
	public void returnBook() {
		isIssued = false;
		issuedToUserId = null;
	}
	
	@Override
	public String toString() {
		if (isIssued) {
			return id + ": " + title + " by " + author + " [Issued to User " + issuedToUserId + "]";
		} else {
			return id + ": " + title + " by " + author + " [Available]";
		}
		
	}
}
