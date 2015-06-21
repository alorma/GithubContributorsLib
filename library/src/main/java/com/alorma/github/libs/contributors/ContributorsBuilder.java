package com.alorma.github.libs.contributors;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alorma.github.libs.contributors.ui.activity.ContributorsActivity;

/**
 * Created by Bernat on 18/06/2015.
 */
public class ContributorsBuilder {

    private Contributors.ActivityStyle activityStyle = Contributors.ActivityStyle.LIGHT;
    private int activityTheme = -1;

    /**
     * Builder method to set the activity theme
     *
     * @param activityStyle as example R.theme.AppTheme (just for the activity)
     * @return this
     */
    public ContributorsBuilder withActivityStyle(Contributors.ActivityStyle activityStyle) {
        this.activityStyle = activityStyle;
        return this;
    }

    /**
     * Builder method to set the activity theme
     *
     * @param activityTheme as example R.theme.AppTheme (just for the activity)
     * @return this
     */
    public ContributorsBuilder withActivityTheme(int activityTheme) {
        this.activityTheme = activityTheme;
        return this;
    }

    /**
     * intent() method to build and create the intent with the set params
     *
     * @return the intent to start the activity
     */
    public Intent intent(Context ctx, String token, String url) {
        Intent i = new Intent(ctx, ContributorsActivity.class);

        i.putExtra(Contributors.BUNDLE_THEME, this.activityTheme);

        if (!TextUtils.isEmpty(token)) {
            i.putExtra(Contributors.BUNDLE_CONTRIBUTORS_LIBRARY_GITHUB_TOKEN, token);
        }

        if (!TextUtils.isEmpty(url)) {
            i.putExtra(Contributors.BUNDLE_REPOSITORY_URL, url);
        }

        if (this.activityStyle != null) {
            i.putExtra(Contributors.BUNDLE_STYLE, this.activityStyle.name());
        }

        return i;
    }

    public void start(Context context, String token, String url) {
        if (context != null) {
            context.startActivity(intent(context, token, url));
        }
    }
}
