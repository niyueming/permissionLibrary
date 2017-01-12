# permissionLibrary
Android 6.0以上的权限申请
这只是个Android Studio的Module，不能独立运行。
### build.gradle
该文件中的rootProject.ext.*定义在被依赖项目中的build.gradle，如：

-------
    ext {
        compileSdkVersion = 25
        targetSdkVersion = compileSdkVersion
        minSdkVersion = 16
        buildToolsVersion = '24.0.2'
        supportLibVersion = '25.1.0'
        playServicesVersion = '9.4.0'
    }

### My Build Enviroment
- Common
 - MacOS Sierra 版本10.12.2
- Android 25
 - Java 1.8
- [Android Studio 2.2.3](https://developer.android.com/studio/index.html)

## 申请权限
**一个权限**
```java
new NPermission.Builder(this)
          .requestCode(REQUEST_CAMERA)
          .permissions(Manifest.permission.WRITE_CONTACTS)
          .permissionListener(permissionListener)
          .rationaleListener(rationaleListener)
          .build()
          .request();
```

**多个权限**
```java
new NPermission.Builder(this)
          .requestCode(REQUEST_CAMERA)
          .permissions(Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_SMS)
          .permissionListener(permissionListener)
          .rationaleListener(rationaleListener)
          .build()
          .request();
```
在使用到特殊权限时，只需要在继承自NPermissionActivity的`Activity`或继承自NPermissionFragment的`Fragment`中直接调用，等到回调时即可执行相应的代码。

**注意**  
1. 如果不想使用提供的NPermissionActivity和NPermissionFragment，可以自己实现。在在6.0以下的手机是没有onRequestPermissionsResult()方法，可以实现ActivityCompat.OnRequestPermissionsResultCallback。
2. permissionListener是申请权限结果的回调。
3. rationaleListener是申请权限的时候被拒绝过，再次申请解释下为什么要这些权限，有默认的实现，可以不设置。


