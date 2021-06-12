KotlinPoet API说明
https://square.github.io/kotlinpoet/

```
//一定要改1.8
sourceCompatibility = JavaVersion.VERSION_1_8
targetCompatibility = JavaVersion.VERSION_1_8
```

compiler 依赖
```
implementation project(path: ':apt-annotations')
implementation "com.google.auto.service:auto-service:1.0"
kapt "com.google.auto.service:auto-service:1.0"
implementation "com.squareup:kotlinpoet:1.8.0"
```
app 依赖
```
implementation project(path: ':apt')
kapt project(path: ':apt-annotations')
implementation project(path: ':apt-annotations')
//一定要kapt
kapt project(path: ':apt-compiler')
```
