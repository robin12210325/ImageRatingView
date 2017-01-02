# ImageRatingView

<img src="https://raw.githubusercontent.com/qingtenglv/ImageRatingView/master/device-2017-01-02-101318.gif" width="200">  

##Add to project


first:
```
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```
then:
```
    dependencies {
        compile 'com.github.qingtenglv:ImageRatingView:v1.0.2'
    }
```
##How to use

```
 <com.hard.imageratingview.ImageRatingView
        android:id="@+id/sample1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginTop="10dp"
        app:frontImage="@drawable/xxx"
        app:backImage="@drawable/yyy"
        app:rating="5"
        app:imageHeight="30dp"
        app:imageWidth="30dp"
        app:spanSize="10dp"
        app:minStep="0.5"
        app:maxCount="7"
        app:touchable="true" />
```
