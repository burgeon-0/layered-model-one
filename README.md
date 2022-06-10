# Layered Model

## 代码架构

![image](https://github.com/burgeon-0/spring-boot-demo/blob/master/assets/architecture.png)

- Adapter层为接入层，对外提供业务服务；
- App层为应用逻辑层，处理应用核心逻辑；
- Domain层为领域层，封装核心领域服务，Domain层应当是与具体技术松耦合的，对于技术强耦合部分（如repository），应当通过接口抽象，将具体实现放置于基础设施层；
- Core层为核心层，源自于Domain层的扩展，在Domain层中出现的可供整个应用共享使用的功能将放置于此；
- Infrastructure层为基础设施层，全局通用的、技术强相关的功能，都放置于此；
- 每一层的业务对象是严格区分的，每一层的业务对象都有固定的命名（如Adapter层，入参均以Form结尾，出参均以VO结尾；App层，入参、出参均以DTO结尾）；
- 为了减少业务对象的代码编写量，将业务对象的共同部分抽象到Core层的base包中。
