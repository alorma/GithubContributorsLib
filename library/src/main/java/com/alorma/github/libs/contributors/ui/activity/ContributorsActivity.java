package com.alorma.github.libs.contributors.ui.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.alorma.github.libs.contributors.Contributors;
import com.alorma.github.libs.contributors.R;
import com.alorma.github.libs.contributors.ui.fragment.ContributorsFragment;

/**
 * Created by Bernat on 18/06/2015.
 */
public class ContributorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();

        String token = null;
        String repositoryUrl = null;

        if (bundle == null || !bundle.containsKey(Contributors.BUNDLE_CONTRIBUTORS_LIBRARY_GITHUB_TOKEN) || !bundle.containsKey(Contributors.BUNDLE_REPOSITORY_URL)) {
            finish();
        }

        //set the theme
        boolean customTheme = false;
        Contributors.ActivityStyle activityStyle = Contributors.ActivityStyle.DARK;

        if (bundle != null) {
            token = bundle.getString(Contributors.BUNDLE_CONTRIBUTORS_LIBRARY_GITHUB_TOKEN);
            repositoryUrl = bundle.getString(Contributors.BUNDLE_REPOSITORY_URL);
            int themeId = bundle.getInt(Contributors.BUNDLE_THEME, -1);
            if (themeId != -1) {
                customTheme = true;
                setTheme(themeId);
            }

            String style = bundle.getString(Contributors.BUNDLE_STYLE);
            if (style != null) {
                activityStyle = Contributors.ActivityStyle.valueOf(style);
            }
        }

        if (!customTheme) {
            if (activityStyle == Contributors.ActivityStyle.DARK) {
                setTheme(R.style.ContributorsLibraryTheme);
            } else if (activityStyle == Contributors.ActivityStyle.LIGHT) {
                setTheme(R.style.ContributorsLibraryTheme_Light);
            } else if (activityStyle == Contributors.ActivityStyle.LIGHT_DARK_TOOLBAR) {
                setTheme(R.style.ContributorsLibraryTheme_Light_DarkToolbar);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_contributors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //if we have a darkToolbar set the text white
        if (activityStyle == Contributors.ActivityStyle.LIGHT_DARK_TOOLBAR) {
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setSubtitleTextColor(Color.WHITE);
        }
        setSupportActionBar(toolbar);
        //if we use the DarkToolbar style we have to handle the back arrow on our own too
        if (activityStyle == Contributors.ActivityStyle.LIGHT_DARK_TOOLBAR && getSupportActionBar() != null) {
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            if (upArrow != null) {
                upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        String title = getString(R.string.contributors_library_name);
        if (bundle != null && bundle.containsKey(Contributors.BUNDLE_TITLE)) {
            title = bundle.getString(Contributors.BUNDLE_TITLE);
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) {

            // SetUp ActionBar
            ab.setDisplayHomeAsUpEnabled(true);
            if (TextUtils.isEmpty(title)) {
                ab.setDisplayShowTitleEnabled(false);
            } else {
                ab.setDisplayShowTitleEnabled(true);
                ab.setTitle(title);
            }
        }

        ContributorsFragment fragment = ContributorsFragment.newInstance(token, repositoryUrl);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                ContributorsActivity.this.finish();
                return true;
            }
            default:
                return false;
        }
    }
}
