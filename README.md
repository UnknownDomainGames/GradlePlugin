# Pangu Engine Gradle Plugin

![Java Version](https://img.shields.io/badge/Java-11-blue)
[![All Contributors](https://img.shields.io/github/contributors/UnknownDomainGames/GradlePlugin)](https://github.com/UnknownDomainGames/GradlePlugin/graphs/contributors)
[![License](https://img.shields.io/github/license/UnknownDomainGames/GradlePlugin)](https://github.com/UnknownDomainGames/GradlePlugin/blob/dev/LICENSE)
[![Discord Onlines](https://img.shields.io/discord/556150394057916426)](http://discord.gg/KRGMBw3)
[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/UnknownDomainGames/GradlePlugin/Build)](https://github.com/UnknownDomainGames/GradlePlugin/actions/workflows/build.yml)
[![Latest Version](https://img.shields.io/github/v/release/UnknownDomainGames/PanguEngine?include_prereleases)](https://github.com/UnknownDomainGames/GradlePlugin/releases/latest)

用于[PanguEngine](https://github.com/UnknownDomainGames/PanguEngine) mod开发的快捷[Gradle](https://gradle.org/)插件。

A useful [Gradle](https://gradle.org/) plugin for mods in [PanguEngine](https://github.com/UnknownDomainGames/PanguEngine)

## 使用（Using）

添加这些构建脚本到`build.gradle`的开头。

Add these build scripts to the head of `build.gradle`.

```groovy
buildscript {
    repositories {
        mavenLocal()
        maven { url 'https://jitpack.io' }
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath 'com.github.UnknownDomainGames:GradlePlugin:<VERSION>'
    }
}
apply plugin: 'pangu-gradle'
```

添加这些构建脚本到`build.gradle`之中。

Add these build scripts to `build.gradle`.

```groovy
engine {
    version '<ENGINE VERSION>'
}
```



## 贡献代码（Contributing）

请阅读我们的贡献指南：[简体中文(zh_CN)](https://github.com/UnknownDomainGames/PanguEngine/blob/dev/CONTRIBUTING.md)

Please read our contribution guide: [English(en_US)](https://github.com/UnknownDomainGames/PanguEngine/blob/dev/CONTRIBUTING_EN.md)

## 相关链接（Links）

- [[PanguEngine](https://github.com/UnknownDomainGames/PanguEngine)](https://github.com/UnknownDomainGame/PanguEngine/wiki)
- [项目简介（Introducing Project）](https://github.com/UnknownDomainGames/PanguEngine/wiki/Introducing-Project)
- [示例模组（Example Mod）](https://github.com/UnknownDomainGames/ExampleMod)
- 交流QQ群（QQ group for communication）：[965101316](https://jq.qq.com/?_wv=1027&k=5exnX2o)
- Discord：http://discord.gg/KRGMBw3