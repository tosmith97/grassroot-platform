package za.org.grassroot.core.domain;

/**
 * Contains info about Log book completion confirmations.
 */
public class LogBookCompletionStatus {
	private final int confirmationsCount;
	private final int membersCount;

	public LogBookCompletionStatus(int confirmationsCount, int membersCount) {
		this.confirmationsCount = confirmationsCount;
		this.membersCount = membersCount;
	}

	public int getConfirmationsCount() {
		return confirmationsCount;
	}

	public int getMembersCount() {
		return membersCount;
	}

	public double getPercentage() {
		return 100 * confirmationsCount / (double) membersCount;
	}
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("LogBookCompletionStatus{");
		sb.append("confirmationsCount=").append(confirmationsCount);
		sb.append(", membersCount=").append(membersCount);
		sb.append(", percentage=").append(getPercentage()).append('%');
		sb.append('}');
		return sb.toString();
	}
}
