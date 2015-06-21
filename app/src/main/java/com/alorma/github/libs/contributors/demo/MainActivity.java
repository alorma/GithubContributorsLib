package com.alorma.github.libs.contributors.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alorma.github.libs.contributors.Contributors;
import com.alorma.github.libs.contributors.ContributorsBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ContributorsBuilder().withActivityStyle(Contributors.ActivityStyle.DARK).start(MainActivity.this, BuildConfig.GITHUB_TOKEN, "https://github.com/gitskarios/gitskarios");
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ContributorsBuilder().withActivityStyle(Contributors.ActivityStyle.LIGHT).start(MainActivity.this, BuildConfig.GITHUB_TOKEN, "https://github.com/mikepenz/AboutLibraries");
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ContributorsBuilder().withActivityStyle(Contributors.ActivityStyle.LIGHT_DARK_TOOLBAR).start(MainActivity.this, BuildConfig.GITHUB_TOKEN, "https://github.com/square/retrofit");
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ContributorsBuilder().withActivityTheme(R.style.contributors_custom_theme).start(MainActivity.this, BuildConfig.GITHUB_TOKEN, "https://github.com/alorma/travis_check");
            }
        });
    }
}
