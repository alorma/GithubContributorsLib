package com.alorma.github.libs.contributors.api.client;

import android.content.Context;

import com.alorma.github.libs.contributors.Contributors;
import com.alorma.github.libs.contributors.api.dto.Contributor;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Bernat on 20/07/2014.
 */
public class GetRepoContributorsClient {

    private int page;
    private Context context;
    private String owner;
    private String repo;
    private Callback<List<Contributor>> returnCallback;
    private String token;

    public GetRepoContributorsClient(Context context, String token, String owner, String repo, Callback<List<Contributor>> returnCallback) {
        this.context = context;
        this.owner = owner;
        this.repo = repo;
        this.returnCallback = returnCallback;
        this.token = token;
    }

    public GetRepoContributorsClient(Context context, String token, String owner, String repo, int page, Callback<List<Contributor>> returnCallback) {
        this.context = context;
        this.owner = owner;
        this.repo = repo;
        this.page = page;
        this.returnCallback = returnCallback;
        this.token = token;
    }

    public void execute() {
        if (Contributors.checkPermission(context) && token != null) {
            final ContributorsService contributorsService = createService();
            if (contributorsService != null) {
                if (page == 0) {
                    contributorsService.contributors(owner, repo, returnCallback);
                } else {
                    contributorsService.contributors(owner, repo, page, returnCallback);
                }
            }
        }
    }

    private ContributorsService createService() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("https://api.github.com");
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/vnd.github.v3.json");
                request.addHeader("User-Agent", "Contributors Library");
                request.addHeader("Authorization", "token " + token);
            }
        });
        return builder.build().create(ContributorsService.class);
    }
}
