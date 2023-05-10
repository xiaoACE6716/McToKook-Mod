# McToKook-Mod

这是一个可以让MC的聊天与指定的一个KOOK服务器频道互通的MC Forge Mod \
支持版本：目前只有Minecraft 1.12.2 \
基于Forge 与 JKook api 与 KookBC 

## Mod的食用方法
将mod添加至服务端的mods文件夹中并启动服务端 \
注意，首次添加启动会使服务端直接关闭并且告知原因 \
然后您应该到config文件夹中寻找 mctokook.cfg 文件 \
打开并且填写里面的必要配置项目 bot_token 与 channel_ID \
填写完毕后再次启动服务端，本Mod就会正常运作 \
注意：本Mod为服务端Mod 在玩家客户端上不会加载 

## bot_token的获取方式
访问[kook开发者中心](https://developer.kookapp.cn/app/index)，
点击新建应用，创建一个新的机器人，可以在该机器人的控制台页面寻找到机器人的token，
将其填入到配置文件当中。\
记得使用邀请链接将机器人邀请到你要进行互通的KOOK服务器里 \
具体的机器人相关问题请好好查阅[kook开发者中心](https://developer.kookapp.cn/app/index)的文档 \
注意: 请保管好机器人的token,不要随意泄露,虽然它可以随意重置,但是请还是做好相关工作。

## channel_ID的获取方式
在Kook的设置里找到高级设置,将开发者模式启用\
然后回到你想实现互通的服务器频道,右键频道,你会发现多了一个选项叫做：复制ID 点击就可以将频道ID复制至剪贴板\
然后将其复制到配置文件当中。

## 最后
还有什么问题、想法或建议，请直接与我联系，或者直接在github上开一个issues。
