# DayTracker - 一日轨迹记录器（高德地图版）

📱 使用高德地图的 Android 轨迹记录应用。

## 🚀 快速开始

### 1. 获取高德地图 API Key

1. 访问 [高德开放平台](https://lbs.amap.com/)
2. 注册账号并实名认证
3. 创建新应用
4. 添加 "高德地图 Android SDK"
5. 获取 API Key

### 2. 替换 API Key

打开 `AndroidManifest.xml`，替换：
```xml
<meta-data
    android:name="com.amap.api.v2.apikey"
    android:value="你的高德API_KEY" />
```

### 3. 构建 APK

```bash
./gradlew assembleRelease
```

APK 位置：`app/build/outputs/apk/release/app-release-unsigned.apk`

## 🛠️ 技术栈

- **Kotlin** - 现代 Android 开发
- **高德地图 SDK** - 国内地图服务
- **Room Database** - 本地数据存储

## 📝 功能

- ✅ 实时轨迹记录
- ✅ 高德地图显示
- ✅ 本地数据存储
- ✅ 轨迹导出

## 📄 License

MIT
