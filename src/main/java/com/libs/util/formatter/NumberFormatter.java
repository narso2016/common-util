package com.libs.util.formatter;

public class NumberFormatter {
	
	int decimal;
	char thousandSeparator;
	char decimalSeparator;
	boolean showThousandSeparator;

	public NumberFormatter(char thousandSeparator, char decimalSeparator,int decimal) {
		this(thousandSeparator, decimalSeparator, decimal, true);
	}

	public NumberFormatter(char thousandSeparator, char decimalSeparator,
			int decimal, boolean showThousandSeparator) {
		this.decimal = decimal;
		this.thousandSeparator = thousandSeparator;
		this.decimalSeparator = decimalSeparator;
		this.showThousandSeparator = showThousandSeparator;
	}

	public boolean equals(Object object) {
		if (!(object instanceof NumberFormatter)) {
			return false;
		}
		if (this == object) { 
			return true;
		}
		NumberFormatter other = (NumberFormatter) object;
		return other.thousandSeparator == this.thousandSeparator
				&& other.decimalSeparator == this.decimalSeparator
				&& other.showThousandSeparator == this.showThousandSeparator
				&& other.decimal == this.decimal;
	}

	public int hashCode() {
		int result = 5;
		result = 7 * result + (int) this.thousandSeparator;
		result = 7 * result + (int) this.decimalSeparator;
		result = 7 * result + (this.showThousandSeparator ? 1 : 0);
		result = 7 * result + this.decimal;
		return result;
	}
}
