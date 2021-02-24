package com.libs.util.converter;

public class ConvertRp {

	public String info_terbilang(int value) {
		String[] bilangan = { "", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan",
				"Sepuluh", "Sebelas" };
		String temp = " ";
		if (value < 12) {
			temp = " " + bilangan[value];
		} else if (value < 20) {
			temp = info_terbilang(value - 10) + " Belas";
		} else if (value < 100) {
			temp = info_terbilang(value / 10) + " Puluh" + info_terbilang(value % 10);
		} else if (value < 200) {
			temp = " Seratus" + info_terbilang(value - 100);
		} else if (value < 1000) {
			temp = info_terbilang(value / 100) + " Ratus" + info_terbilang(value % 100);
		} else if (value < 2000) {
			temp = "Seribu" + info_terbilang(value - 1000);
		} else if (value < 1000000) {
			temp = info_terbilang(value / 1000) + " Ribu" + info_terbilang(value % 1000);
		}
		return temp;
	}
}
