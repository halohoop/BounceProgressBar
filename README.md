# BounceProgressbar

![demo0](./demo0.gif)

![demo1](./demo1.gif)

#Usage 

**compile 'com.halohoop:bounceprogressbar:1.0.2'**

    <com.halohoop.bounceprogressbar.views.BounceProgressBar
        android:background="#00d5f1"
        android:id="@+id/bpb"
        android:layout_width="200dp"
        android:layout_height="200dp"
        BounceProgressBar:LineWidth="200"
        BounceProgressBar:LineColor="#fff700"
        BounceProgressBar:StrokeWidth="5"
        BounceProgressBar:FixRadius="10"
        BounceProgressBar:FixBallColor="#ff0000"
        BounceProgressBar:BouncingBallRadius="50"
        BounceProgressBar:BouncingBallColor="#bf00ff"
        android:layout_centerInParent="true" />
#### you may need

	xmlns:BounceProgressBar="http://schemas.android.com/apk/res-auto"
* you can custom attrs such as color,line width, radius and so on;

# Customization

# Compatibility

  * Android 3.0+,cuz needing the ValueAnimator;

# Changelog 

### Version: 1.0.2

  * add method for stop animation,endTotalAnimation();

### Version: 1.0.1

  * change color by code instead of by attrs in xml;

### Version: 1.0

  * Initial Build

## License

    Copyright 2016, Halohoop

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
