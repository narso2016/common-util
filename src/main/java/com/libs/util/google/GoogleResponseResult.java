/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libs.util.google;

/**
 *
 * @author vince
 */
public class GoogleResponseResult {
    private GoogleResult[] results;
    private String status;

    public GoogleResponseResult() {
    }

    public GoogleResult[] getResults() {
        return results;
    }

    public void setResults(GoogleResult[] results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
