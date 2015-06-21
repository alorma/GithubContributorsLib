package com.alorma.github.libs.contributors.api.client;

import com.alorma.github.libs.contributors.api.dto.Contributor;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Bernat on 18/06/2015.
 */
public interface ContributorsService {

    @GET("/repos/{owner}/{name}/stats/contributors")
    void contributors(@Path("owner") String owner, @Path("name") String repo, Callback<List<Contributor>> callback);

    @GET("/repos/{owner}/{name}/stats/contributors")
    void contributors(@Path("owner") String owner, @Path("name") String repo, @Query("page") int page, Callback<List<Contributor>> callback);
}
