package com.alorma.github.libs.contributors;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Bernat on 18/06/2015.
 */
public class Contributors {

    public static final String BUNDLE_STYLE = "CONTRIBUTORS_LIBRARY_STYLE";
    public static final String BUNDLE_TITLE = "CONTRIBUTORS_LIBRARY_TITLE";
    public static final String BUNDLE_THEME = "CONTRIBUTORS_LIBRARY_THEME";

    public static final String BUNDLE_REPOSITORY_URL = "CONTRIBUTORS_LIBRARY_REPOSITORY_URL";
    public static final String BUNDLE_CONTRIBUTORS_LIBRARY_GITHUB_TOKEN = "CONTRIBUTORS_LIBRARY_GITHUB_TOKEN";

    public enum ActivityStyle {
        LIGHT,
        DARK,
        LIGHT_DARK_TOOLBAR;
    }

    public static boolean checkPermission(Context context) {
        if (context != null) {
            String permission = Manifest.permission.INTERNET;
            int res = context.checkCallingOrSelfPermission(permission);
            return (res == PackageManager.PERMISSION_GRANTED);
        }
        return false;
    }

}
