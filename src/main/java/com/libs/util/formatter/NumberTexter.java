package com.libs.util.formatter;

import java.math.BigDecimal;

public abstract class NumberTexter {
	private static final String SPACE = " ";

	public static final NumberTexter LANG_ID = new NumberTexterId();
	public static final NumberTexter LANG_EN = new NumberTexterEn();

	private NumberTexter() {
	}

	public abstract String numberToString(long number);

	@SuppressWarnings("deprecation")
	public String[] numberToString(double number) {
		String[] result = new String[2];
		long integer = (long) Math.abs(number);
		BigDecimal bd = new BigDecimal(number - integer);
		result[0] = numberToString(integer);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		int decimal = bd.movePointRight(2).intValue();
		result[1] = numberToString(decimal);
		return result;
	}

	private static int[] getDigits(int number) {
		int[] result = new int[3];
		result[0] = number / 100;
		result[1] = (number % 100) / 10;
		result[2] = (number % 10);
		return result;
	}

	private static class NumberTexterId extends NumberTexter {
		private static final String[] ID_NUMBERS = { "nol", "satu", "dua",
				"tiga", "empat", "lima", "enam", "tujuh", "delapan",
				"sembilan", "sepuluh", "sebelas", "dua belas", "tiga belas",
				"empat belas", "lima belas", "enam belas", "tujuh belas",
				"delapan belas", "sembilan belas" };

		private static final String ID_SE = "se";
		private static final String[] ID_SUFFIX = { "", "puluh", "ratus" };
		private static final String[] ID_THOUSAND_SUFFIX = { "", "ribu",
				"juta", "milyar", "trilyun", "bilyun" };

		public String numberToString(long number) {
			StringBuffer result = new StringBuffer();
			int ratusan = 0;
			String strNumber = String.valueOf(number);
			int limit = (int) Math.ceil(strNumber.length() / 3d);

			for (int n = limit - 1; n >= 0; n--) {
				long multiply = (long) Math.pow(10, 3 * n);
				ratusan = (int) ((number % (1000 * multiply)) / multiply);

				if (ratusan == 1 && n == 1) {
					result.append(ID_SE + ID_THOUSAND_SUFFIX[n] + SPACE);
				} else if (ratusan == 0 && n == 0 && result.length() == 0) {
					result.append(ID_NUMBERS[0]);
				} else {
					String spell = numberRatusToString(ratusan);
					if (spell.length() > 0) {
						spell += SPACE + ID_THOUSAND_SUFFIX[n];
						result.append(spell + SPACE);
					}
				}
			}
			return result.toString().trim();
		}

		private String numberRatusToString(int number) {
			int[] tmp = getDigits(number);
			int digitRatus = tmp[0];
			int digitPuluh = tmp[1];
			int digit = tmp[2];
			String ratus = "";
			String puluh = "";
			String satu = "";
			StringBuffer buff = new StringBuffer();
			if (digitRatus > 0) {
				buff.delete(0, buff.length());
				if (digitRatus == 1) {
					ratus = buff.append(ID_SE).append(ID_SUFFIX[2]).toString();
				} else {
					ratus = buff.append(ID_NUMBERS[digitRatus]).append(SPACE)
							.append(ID_SUFFIX[2]).toString();
				}
			}

			int puluhan = 10 * digitPuluh + digit;
			if (puluhan < 20 && puluhan > 0) {
				puluh = ID_NUMBERS[puluhan];
			} else {
				if (digitPuluh > 0) {
					buff.delete(0, buff.length());
					puluh = buff.append(ID_NUMBERS[digitPuluh]).append(SPACE)
							.append(ID_SUFFIX[1]).toString();
				}

				if (digit > 0) {
					buff.delete(0, buff.length());
					if (digitPuluh == 1 && digit == 1) {
						satu = buff.append(ID_NUMBERS[10]).toString();
					} else {
						satu = buff.append(ID_NUMBERS[digit]).toString();
					}
				}
			}

			buff.delete(0, buff.length());
			buff.append(ratus).append(puluh.length() > 0 ? SPACE : "")
					.append(puluh).append(satu.length() > 0 ? SPACE : "")
					.append(satu);
			return buff.toString().trim();
		}
	}

	private static class NumberTexterEn extends NumberTexter {
		private static final String[] EN_NUMBERS = { "zero", "one", "two",
				"three", "four", "five", "six", "seven", "eight", "nine",
				"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
				"sixteen", "seventeen", "eighteen", "nineteen" };

		private static final String[] EN_NUMBERS_TEEN = { "", "ten", "twenty",
				"thirty", "forty", "fifty", "sixty", "seventy", "eighty",
				"ninety" };

		private static final String EN_PLURAL = "s";
		private static final String EN_HUNDRED = "hundred";
		private static final String[] EN_THOUSAND_SUFFIX = { "", "thousand",
				"million", "billion", "trillion", "zillion", "jillion" };

		public String numberToString(long number) {
			StringBuffer result = new StringBuffer();
			int ratusan = 0;
			String strNumber = String.valueOf(number);
			int limit = (int) Math.ceil(strNumber.length() / 3d);

			for (int n = limit - 1; n >= 0; n--) {
				long multiply = (long) Math.pow(10, 3 * n);
				ratusan = (int) ((number % (1000 * multiply)) / multiply);

				if (ratusan == 0 && n == 0 && result.length() == 0) {
					result.append(EN_NUMBERS[0]);
				} else {
					String spell = numberRatusToString(ratusan);
					if (spell.length() > 0) {
						spell += SPACE + EN_THOUSAND_SUFFIX[n];
						result.append(spell);
						if (ratusan > 1 && n > 0) {
							result.append(EN_PLURAL);
						}
						result.append(SPACE);
					}
				}
			}
			return result.toString().trim();
		}

		private String numberRatusToString(int number) {
			int[] tmp = getDigits(number);
			int digitRatus = tmp[0];
			int digitPuluh = tmp[1];
			int digit = tmp[2];
			String ratus = "";
			String puluh = "";
			String satu = "";
			StringBuffer buff = new StringBuffer();
			if (digitRatus > 0) {
				buff.delete(0, buff.length());
				buff.append(EN_NUMBERS[digitRatus]).append(SPACE)
						.append(EN_HUNDRED);
				if (digitRatus > 1) {
					buff.append(EN_PLURAL);
				}
				ratus = buff.toString();
			}

			int puluhan = 10 * digitPuluh + digit;
			if (puluhan < 20 && puluhan > 0) {
				puluh = EN_NUMBERS[puluhan];
			} else {
				buff.delete(0, buff.length());
				puluh = buff.append(EN_NUMBERS_TEEN[digitPuluh]).toString();

				if (digit > 0) {
					buff.delete(0, buff.length());
					satu = buff.append(EN_NUMBERS[digit]).toString();
				}
			}

			buff.delete(0, buff.length());
			buff.append(ratus).append(puluh.length() > 0 ? SPACE : "")
					.append(puluh).append(satu.length() > 0 ? SPACE : "")
					.append(satu);
			return buff.toString().trim();
		}
	}
}
