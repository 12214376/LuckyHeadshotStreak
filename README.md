# 抢枪爆头好运连连 (Lucky Headshot Streak)

Minecraft **Forge 1.20.1** 客户端整合模组。

- 中文名：抢枪爆头好运连连
- 英文名：Lucky Headshot Streak
- 作者 GitHub：[@12214376](https://github.com/12214376)
- Mod ID：`creature_radar`（兼容原配置键位与资源路径）
- 当前版本：`1.0.7`

## 功能

- 生物雷达：距离、过滤、绘制模式、颜色/渐变
- AutoAim / TacZ 战斗选项：穿墙、射速、单发连发等
- 自动疾跑、背包移动
- 信息面板 / 目标 HUD / 全亮
- 第三人称模型自转（可调转速，第一人称镜头不受影响）
- 统一配置 UI：雷达 / 战斗 / 移动 / 渲染
- 目标过滤含玩家、怪物、动物、村民、傀儡、水生动物、水生生物、环境生物

## 安装

1. 安装 Minecraft 1.20.1 + Forge 47.x
2. 可选安装 TacZ，以启用枪械相关增强
3. 将 [dist/LuckyHeadshotStreak-1.0.7.jar](dist/LuckyHeadshotStreak-1.0.7.jar) 放入 `mods`
4. 启动游戏，用配置键或 ClickGUI 打开设置

## 仓库结构

- `dist/` 预构建 jar
- `src/main/java` 维护/重写过的源码（配置 UI、第三人称自转等）
- `src/main/resources` mods.toml、mixin、语言文件
- `reference/decompiled` 逆向参考代码

## 许可证

MIT License

## 声明

仅供学习、研究与私人整合使用。请遵守服务器规则与当地法律法规。
