[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-GithubContributorsLib-green.svg?style=flat)](https://android-arsenal.com/details/1/2020)

# GithubContributorsLib
A library to easily show open source (Github) project contributors

## Screenshots

![Image](https://raw.githubusercontent.com/alorma/GithubContributorsLib/develop/screens/screen_init.png)
![Image](https://raw.githubusercontent.com/alorma/GithubContributorsLib/develop/screens/screen_light.png)
![Image](https://raw.githubusercontent.com/alorma/GithubContributorsLib/develop/screens/screen_dark.png)
![Image](https://raw.githubusercontent.com/alorma/GithubContributorsLib/develop/screens/screen_custom.png)

## Usage

Add dependency to your build.gradle
```groovy
    compile 'com.github.alorma:githubcontributors:0.0.1'
```

```java
new ContributorsBuilder()
.withActivityStyle(Contributors.ActivityStyle.DARK)
.start(MainActivity.this
    , BuildConfig.GITHUB_TOKEN
    , "https://github.com/gitskarios/gitskarios");
```

### Customize

Library provides 3 different themes:

- Contributors.ActivityStyle.DARK
- Contributors.ActivityStyle.LIGHT
- Contributors.ActivityStyle.LIGHT_DARK_TOOLBAR

But you can also use your custom theme, extending ```Theme.AppCompat.Light.NoActionBar```

```xml
    <style name="contributors_custom_theme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="colorPrimary">#ffff9376</item>
        <item name="colorPrimaryDark">#ffff7345</item>
        <item name="colorAccent">#0000FF</item>
        <item name="ghco_divider_color">#FF00FF</item>
    </style>
```

- *colorAccent* will be used in refresh widget
- *ghco_divider_color* will be used to colorize each row divider

## Developed By

* Bernat Borras - http://github.com/alorma


## License

    Copyright 2015 Bernat Borras

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
