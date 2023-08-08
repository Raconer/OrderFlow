package com.order.flow.testData;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class OrderTestData {

    private MultiValueMap<String, String> params = new LinkedMultiValueMap();

    public MultiValueMap<String, String> getSearchData(){
        this.params.add("page", Integer.toString(1));
        this.params.add("size", Integer.toString(30));

        return params;
    }
}
