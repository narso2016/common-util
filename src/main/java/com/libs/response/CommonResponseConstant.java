package com.libs.response;

public abstract class CommonResponseConstant {
	public static final String GENERAL_ERROR_CODE = "X06";
//	public static final String GENERAL_ERROR_DESC = "General Error";
	public static final String GENERAL_ERROR_DESC = "Internal Server Error";
	public static final String DEFAULT_SUCCESS_CODE = "00";
	public static final String DEFAULT_SUCCESS_DESC = "SUCCESS";
	public static final String DEFAULT_FAILED_CODE = "14";
	public static final String DEFAULT_TRX_NOT_FOUND_CODE = "402";
	public static final String DEFAULT_TRX_TAGIHAN_TERBAYAR_CODE = "401";
	
	public static final String ID_KEY_NOT_FOUND_CODE = "501";
	public static final String INSTITUTION_NOT_FOUND_CODE = "502";
	public static final String ID_CLIENT_NOT_FOUND_CODE = "503";
	
	public static final String REQUEST_TIME_OUT_CODE = "408";
	
	public static final String ID_KEY_REQUIRED_CODE      = "01";
	public static final String INSTITUTION_REQUIRED_CODE = "02";
	public static final String CLIENT_ID_REQUIRED_CODE   = "03";
	public static final String ID_PEL_REQUIRED_CODE      = "04";
	public static final String JML_TAG_REQUIRED_CODE     = "05";
	
	public static final String ID_KEY_REQUIRED_DESC      = "Account Id tidak boleh kosong";
	public static final String ID_PEL_REQUIRED_DESC      = "ID Pelanggan tidak boleh kosong";
	public static final String JML_TAG_REQUIRED_DESC     = "Jumlah tagihan tidak boleh kosong";
	public static final String INSTITUTION_REQUIRED_DESC = "Kode Institusi tidak boleh kosong";
	public static final String CLIENT_ID_REQUIRED_DESC   = "Kode Channel tidak boleh kosong";
	
	public static final String DEFAULT_TRX_NOT_FOUND_DESC = "Data Tidak Ditemukan";
	
	public static final String ID_KEY_NOT_FOUND_DESC = "Account Id tidak ditemukan";
	public static final String INSTITUTION_CODE_NOT_FOUND_DESC = "Kode Institusi tidak ditemukan";
	public static final String ID_CLIENT_NOT_FOUND_DESC = "Id CLient tidak ditemukan";
	
	public static final String REQUEST_TIME_OUT_DESC = "Request Time Out";
	
	public static final String DEFAULT_TRX_TAGIHAN_TERBAYAR_DESC = "Tagihan Sudah Terbayar";
	public static final String DEFAULT_TRX_AMOUNT_NOT_QUALS_CODE = "001";
	public static final String DEFAULT_DKI_NOT_FOUND_NOREK_CODE = "10";
	public static final String DEFAULT_DKI_NOT_FOUND_NOREK_DESC = "Nomor rekening tidak ditemukan";
	public static final String DEFAULT_TRX_AMOUNT_GREATER_THAN_CODE = "002";
	public static final String DEFAULT_TRX_AMOUNT_NOT_QUALS_DESC = "Jumlah Bayar Fix Payment Harus Sama Dengan Jumlah Tagihan";
	public static final String DEFAULT_TRX_AMOUNT_GREATER_THAN_DESC = "Jumlah Bayar Lebih dari Jumlah Tagihan";
	public static final String DEFAULT_FAILED_DESC = "Wrong Tanggal Akhir, Please Check Your Excel File";
	
}
