package com.example.microtemp.microblog.api.models.requests;

public class AllMenusRequestBody extends RequestBody {
    @Override
    public String getUrl() {
        return "/menu";
    }
}
