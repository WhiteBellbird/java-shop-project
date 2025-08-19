package domain;

import java.time.LocalDateTime;

public class Log {

	private LocalDateTime addedDate;
	private LocalDateTime removedDate;

	LocalDateTime added() {
		this.addedDate = LocalDateTime.now();
		return this.addedDate;
	}

	LocalDateTime removed() {
		this.removedDate = LocalDateTime.now();
		return this.removedDate;
	}
}
