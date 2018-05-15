# App暂时叫快翔吧🙃🙃
## 一、前情提要
1. App莫名其妙的就选择了**MVC**的组合模式，最近才了解到**Presenter**和**ViewModel**😅，
之后随着页面需要的数据量与数据类型的增加会更改为**MVVP**的组合模式的。最近谷歌又推出[Jetpack](https://developer.android.com/jetpack/)感觉好多代码可以省了，也就是说白写了。
2. 整个UI采用的*1Activity + 3Fragments + ViewPager* + 使用得不怎么专业的*Material Design*来呈现。
3. 使用的第三方库如下：Gradle:
>```
>    implementation 'com.github.clans:fab:1.6.4'
>    implementation 'org.greenrobot:eventbus:3.1.1'
>    implementation 'com.haibin:calendarview:3.3.0'
>    implementation 'com.jakewharton:butterknife:8.8.1'
>    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
>    implementation 'com.afollestad.material-dialogs:commons:0.9.6.0'
>```
## 二、截图展示
1. [主界面](https://github.com/lzjlxebr/hurrypush-data/blob/master/image/main_ui.gif)
<img src="https://github.com/lzjlxebr/hurrypush-data/blob/master/image/main_ui.gif" height="650"/>

2. [真正功能](https://github.com/lzjlxebr/hurrypush-data/blob/master/image/real_work.gif)
<img src="https://github.com/lzjlxebr/hurrypush-data/blob/master/image/real_work.gif" height="650"/>

3. [设置页面](https://github.com/lzjlxebr/hurrypush-data/blob/master/image/settings.gif)
<img src="https://github.com/lzjlxebr/hurrypush-data/blob/master/image/settings.gif" height="650"/>

## 三、存在的问题
- [ ] 最大的问题，没有医学顾问，无法确认排便时间越短对身体越健康，但排便时间可能与长痔疮有着密切联系。
- [ ] UX上的问题，目前没真实用户使用，仅凭自己的喜好来设定如何操作。
- [ ] 代码很多地方可以进行优化，比如在统计页面展示数据时，我是把所有数据一并查出，之后再过滤显示，看起来很愚蠢，
但愿在更换MVVM之后会得到解决

License
-------
    Copyright 2018 lzjlxebr <tyyzmyd@gmail.com>
     
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
     
        http://www.apache.org/licenses/LICENSE-2.0
     
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.