package com.kteam.bukkit.kapital;

public class StandardException extends Exception {
	private static final long serialVersionUID = 3038814227709867742L;
	public String error;

	public StandardException() {
		super();
		error = "Not Specified";
	}

	public StandardException(String errorThrown) {
		super(errorThrown);
		error = errorThrown;
	}

	public String getError() {
		return error;
	}
}
