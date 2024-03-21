## Micronaut + Zalando Logbook Issue.

To reproduce run the application and then call:

```curl
curl http://localhost:8080/hello/world
```

Application log will show:

```
09:42:34.217 [default-nioEventLoopGroup-1-7] ERROR i.m.h.n.AbstractCompositeCustomizer - Failed to trigger customizer event
java.util.NoSuchElementException: http-streams-codec
	at io.netty.channel.DefaultChannelPipeline.getContextOrDie(DefaultChannelPipeline.java:1073)
	at io.netty.channel.DefaultChannelPipeline.addBefore(DefaultChannelPipeline.java:248)
	at io.netty.channel.DefaultChannelPipeline.addBefore(DefaultChannelPipeline.java:237)
	at com.example.LogbookNettyServerCustomizer$Customizer.onStreamPipelineBuilt(LogbookFactory.kt:71)
	at io.micronaut.http.netty.AbstractCompositeCustomizer.forEach(AbstractCompositeCustomizer.java:109)
	at io.micronaut.http.server.netty.CompositeNettyServerCustomizer.onStreamPipelineBuilt(CompositeNettyServerCustomizer.java:53)
	at io.micronaut.http.server.netty.HttpPipelineBuilder$ConnectionPipeline.configureForHttp1(HttpPipelineBuilder.java:383)
	at io.micronaut.http.server.netty.HttpPipelineBuilder$ConnectionPipeline.initChannel(HttpPipelineBuilder.java:299)
	at io.micronaut.http.server.netty.NettyHttpServer$Listener.initChannel(NettyHttpServer.java:892)
	at io.netty.channel.ChannelInitializer.initChannel(ChannelInitializer.java:129)
	at io.netty.channel.ChannelInitializer.handlerAdded(ChannelInitializer.java:112)
	at io.netty.channel.AbstractChannelHandlerContext.callHandlerAdded(AbstractChannelHandlerContext.java:1130)
	at io.netty.channel.DefaultChannelPipeline.callHandlerAdded0(DefaultChannelPipeline.java:609)
	at io.netty.channel.DefaultChannelPipeline.access$100(DefaultChannelPipeline.java:46)
	at io.netty.channel.DefaultChannelPipeline$PendingHandlerAddedTask.execute(DefaultChannelPipeline.java:1463)
	at io.netty.channel.DefaultChannelPipeline.callHandlerAddedForAllHandlers(DefaultChannelPipeline.java:1115)
	at io.netty.channel.DefaultChannelPipeline.invokeHandlerAddedIfNeeded(DefaultChannelPipeline.java:650)
	at io.netty.channel.AbstractChannel$AbstractUnsafe.register0(AbstractChannel.java:514)
	at io.netty.channel.AbstractChannel$AbstractUnsafe.access$200(AbstractChannel.java:429)
	at io.netty.channel.AbstractChannel$AbstractUnsafe$1.run(AbstractChannel.java:486)
	at io.netty.util.concurrent.AbstractEventExecutor.runTask(AbstractEventExecutor.java:173)
	at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:166)
	at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:470)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:569)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:833)
```