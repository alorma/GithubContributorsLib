package com.alorma.github.libs.contributors;

import com.alorma.github.libs.contributors.api.dto.PaginationLink;
import com.alorma.github.libs.contributors.api.dto.RelType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Created by Bernat on 18/06/2015.
 */
public class ApiUtils {

    public static int getNextPage(Response r) {
        List<Header> headers = r.getHeaders();
        Map<String, String> headersMap = new HashMap<String, String>(headers.size());
        for (Header header : headers) {
            headersMap.put(header.getName(), header.getValue());
        }

        String link = headersMap.get("Link");

        if (link != null) {
            String[] parts = link.split(",");
            try {
                PaginationLink bottomPaginationLink = new PaginationLink(parts[0]);
                if (bottomPaginationLink.rel == RelType.next) {
                    return bottomPaginationLink.page;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
