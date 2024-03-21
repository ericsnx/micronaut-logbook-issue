package com.example

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import io.micronaut.http.netty.channel.ChannelPipelineCustomizer
import io.micronaut.http.server.netty.NettyServerCustomizer
import io.netty.channel.Channel
import jakarta.inject.Singleton
import org.zalando.logbook.Logbook
import org.zalando.logbook.core.Conditions.exclude
import org.zalando.logbook.core.Conditions.requestTo
import org.zalando.logbook.core.DefaultHttpLogWriter
import org.zalando.logbook.core.DefaultSink
import org.zalando.logbook.core.HeaderFilters.authorization
import org.zalando.logbook.core.QueryFilters.accessToken
import org.zalando.logbook.json.JsonHttpLogFormatter
import org.zalando.logbook.netty.LogbookServerHandler

@Factory
class LogbookFactory {
    @Singleton
    fun providesLogbook(): Logbook {
        return Logbook.builder()
            .condition(
                exclude(
                    requestTo("/health"),
                    requestTo("/prometheus"),
                    requestTo("/metrics")
                )
            )
            .sink(
                DefaultSink(
                    JsonHttpLogFormatter(),
                    DefaultHttpLogWriter()
                )
            )
            .queryFilter(accessToken())
            .headerFilter(authorization())
            .build()
    }
}

@Requires(beans = [Logbook::class])
@Singleton
class LogbookNettyServerCustomizer(
    private val logbook: Logbook
) : BeanCreatedEventListener<NettyServerCustomizer.Registry> {

    override fun onCreated(
        event: BeanCreatedEvent<NettyServerCustomizer.Registry>
    ): NettyServerCustomizer.Registry {
        val registry = event.bean
        registry.register(Customizer(null))
        return registry
    }

    private inner class Customizer(
        private val channel: Channel?
    ) : NettyServerCustomizer {

        override fun specializeForChannel(
            channel: Channel,
            role: NettyServerCustomizer.ChannelRole
        ): Customizer {
            return Customizer(channel)
        }

        override fun onStreamPipelineBuilt() {
            channel?.pipeline()?.addBefore(
                ChannelPipelineCustomizer.HANDLER_HTTP_STREAM,
                "logbook",
                LogbookServerHandler(logbook)
            )
        }
    }
}
