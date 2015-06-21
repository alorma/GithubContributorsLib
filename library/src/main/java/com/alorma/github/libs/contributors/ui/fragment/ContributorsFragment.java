package com.alorma.github.libs.contributors.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.alorma.github.libs.contributors.ApiUtils;
import com.alorma.github.libs.contributors.Contributors;
import com.alorma.github.libs.contributors.R;
import com.alorma.github.libs.contributors.api.client.GetRepoContributorsClient;
import com.alorma.github.libs.contributors.api.dto.Contributor;
import com.alorma.github.libs.contributors.ui.UniversalImageLoaderUtils;
import com.alorma.github.libs.contributors.ui.adapter.UsersAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ContributorsFragment extends Fragment implements Callback<List<Contributor>>, AbsListView.OnScrollListener {

    private UsersAdapter usersAdapter;
    private ListView listView;
    private int nextPage = 0;
    private String token;
    private String owner;
    private String repo;

    public static ContributorsFragment newInstance(String token, String url) {
        Bundle bundle = new Bundle();

        bundle.putString(Contributors.BUNDLE_REPOSITORY_URL, url);
        bundle.putString(Contributors.BUNDLE_CONTRIBUTORS_LIBRARY_GITHUB_TOKEN, token);

        ContributorsFragment fragment = new ContributorsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github_contributors, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageLoader.getInstance().init(UniversalImageLoaderUtils.getImageLoaderConfiguration(getActivity()));

        listView = (ListView) view.findViewById(R.id.listView);
        setupListView(listView);

        if (getArguments() != null) {
            String repositoryUrl = getArguments().getString(Contributors.BUNDLE_REPOSITORY_URL);
            token = getArguments().getString(Contributors.BUNDLE_CONTRIBUTORS_LIBRARY_GITHUB_TOKEN);
            if (!TextUtils.isEmpty(repositoryUrl) && !TextUtils.isEmpty(token)) {
                Uri uri = Uri.parse(repositoryUrl);

                if (uri.getPathSegments().size() >= 2) {
                    owner = uri.getPathSegments().get(0);
                    repo = uri.getPathSegments().get(1);

                    loadContributors(0);
                }
            }
        }
    }

    protected void setupListView(ListView listView) {
        if (listView != null) {
            listView.setDivider(null);
            listView.setOnScrollListener(this);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView absListView, int first, int last, int total) {
        if (total > 0 && first + last == total) {
            if (nextPage > 0) {
                if (usersAdapter != null) {
                    usersAdapter.setLazyLoading(true);
                }
                loadContributors(nextPage);
            }
        }
    }

    private void loadContributors(int page) {
        GetRepoContributorsClient repoContributorsClient = new GetRepoContributorsClient(getActivity(), token, owner, repo, page, this);
        repoContributorsClient.execute();

    }

    @Override
    public void success(List<Contributor> contributors, Response response) {
        if (getActivity() != null && getResources() != null) {
            this.nextPage = ApiUtils.getNextPage(response);
            if (usersAdapter == null) {
                usersAdapter = new UsersAdapter(getActivity(), contributors);
                listView.setAdapter(usersAdapter);
            } else {
                usersAdapter.addAll(contributors);
            }
        }
    }

    @Override
    public void failure(RetrofitError error) {

    }
}
