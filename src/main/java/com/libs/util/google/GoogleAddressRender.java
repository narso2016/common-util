/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libs.util.google;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GoogleAddressRender {

//    private static final String API_KEY = "AIzaSyDXcEdtFcWXJNw8NHarT_qhPNgI4OtS9co";
    private static final String ADDRESS_TO_GPS = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String GPS_TO_ADDRESS = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

    @SuppressWarnings("resource")
    public static GoogleResponseResult convertToLatLong(String fullAddress) throws IOException {
        URL url = new URL(ADDRESS_TO_GPS + fullAddress + "&sensor=true");// + "&key=" + this.API_KEY);
        URLConnection conn = url.openConnection();

        InputStream in = conn.getInputStream();
       
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);
        StringBuilder sbuild = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sbuild.append(line);
        }
        in.close();
        String json = sbuild.toString();
        GoogleResponseResult response = new Gson().fromJson(json, GoogleResponseResult.class);
        return response;
    }

    @SuppressWarnings("resource")
    public static GoogleResponseResult getAddressByGpsCoordinates(String lng, String lat) throws IOException {
        URL url = new URL(GPS_TO_ADDRESS + lat + "," + lng + "&sensor=true");// + "&key=" + this.API_KEY);
        URLConnection conn = url.openConnection();

        InputStream in = conn.getInputStream();
       
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sbuild = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sbuild.append(line);
        }
        in.close();
        String json = sbuild.toString();
        GoogleResponseResult response = new Gson().fromJson(json, GoogleResponseResult.class);
        return response;
    }

    public static void main(String[] args) throws IOException {
//        String address = "Suryomentaraman Wetan No.39,Kraton,Yogyakarta,55131,Indonesia";
//        String address = "Jl. Suryomentaraman no.39, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55131, Indonesia";
//        String address = "Jl. Scientia Boulervard, Kota Tangerang Selatan, Banten 15811, Indonesia";
//        String address = "Jl Laksda Adisucipto No. 161,Sleman, Yogyakarta, Indonesia, 55281";
        String address = "Cluster Bougenville loka Blok N1/19, Jl. Boulevard Graha Raya, Bintaro Jaya,Kecamatan Serpong Utara, Kota Tangerang Selatan, Banten 15324, Indonesia";
        GoogleResponseResult res = GoogleAddressRender.convertToLatLong(GoogleAddressFormater.formatAddress(address));
        String latitude = "";
        String longitude = "";
        if (res.getStatus().equals("OK")) {
            for (GoogleResult result : res.getResults()) {
                System.out.println("Lattitude of address is :" + result.getGeometry().getLocation().getLat());
                System.out.println("Longitude of address is :" + result.getGeometry().getLocation().getLng());
                System.out.println("Location is " + result.getGeometry().getLocation_type());
                latitude = String.valueOf(result.getGeometry().getLocation().getLat());
                longitude = String.valueOf(result.getGeometry().getLocation().getLng());
            }
        } else {
            System.out.println(res.getStatus());
        }

        @SuppressWarnings("unused")
		GoogleResponseResult res2 = GoogleAddressRender.getAddressByGpsCoordinates(longitude, latitude);
        if (res.getStatus().equals("OK")) {
            for (GoogleResult result : res.getResults()) {
                System.out.println(result.getFormatted_address());
                System.out.println("Lattitude of address is :" + result.getGeometry().getLocation().getLat());
                System.out.println("Longitude of address is :" + result.getGeometry().getLocation().getLng());
                System.out.println("Location is " + result.getGeometry().getLocation_type());
                latitude = String.valueOf(result.getGeometry().getLocation().getLat());
                longitude = String.valueOf(result.getGeometry().getLocation().getLng());
            }
        } else {
            System.out.println(res.getStatus());
        }

    }

}
