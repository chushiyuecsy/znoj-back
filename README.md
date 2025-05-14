# ZNOJ代码评测平台 SpringBoot 后端，整合判题模块和沙箱模块

> 作者：[程序员狗哥](https://github.com/Chushiyue)

基于 Java SpringBoot 的项目初始模板，整合了常用框架和主流业务的示例代码。

使用了代理模式，工厂模式，策略模式，三种设计模式，以及 Spring Boot 的常用功能。
根据提交的编程语言的不同，使用不同的策略来进行判题。

[toc]

## 判题状态result
1: "In Queue"
2: "Processing"
3: "Accepted"
4: "Wrong Answer"
5: "Time Limit Exceeded"
6: "Compilation Error"
7: "Runtime Error (SIGSEGV)"
8: "Runtime Error (SIGXFSZ)"
9: "Runtime Error (SIGFPE)"
10: "Runtime Error (SIGABRT)"
11: "Runtime Error (NZEC)"
12: "Runtime Error (Other)"
13: "Internal Error"
14: "Exec Format Error"