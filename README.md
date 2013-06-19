Service Assemblies Generation Tool
==================================
Name: sa-generation-tool(SA批量生成工具)

Version 1.6

Create Date: 2013/5/20 10:57

Update Date: 2013/6/19 21:28 

Author: haycco

Useage
------
程序入口为：org.hayccco.sagent.main.GenServiceAssembliesApp

如果需要重新自定义输出目录，则修改GenServiceAssemblyUtil中TEMP_DIRECTORY（程序默认为：D:\\sa\\output）

本程序现在只提供了最多含servicemix-jsr181、servicemix-http、servicemix-camel和servicemix-bean四个SU组件的SA压缩包批量生产，
根据提供的SA发布的zip包，按相应编号进行复制。

Description
-----------
1.程序能自动扫描解析按规范命名SA中包含的SU组件及其类型。

2.如果SA还包其他类型的SU组件类型的SU，需要实现org.teemo.command.BeanCommand相应bean类型SU的配置文件修改(可参考Jsr181Command/HttpCommand/CamelCommand等的具体处理方式)。

3.由于Jbi不属于SU，虽然JbiCommand实现SuCommand，仅为方便统一SA内容修改步骤。

Testing
-------
To run the tests:

    $ rake

To add tests see the `Commands` section earlier in this
README.