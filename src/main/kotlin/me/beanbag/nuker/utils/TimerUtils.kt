package me.beanbag.nuker.utils

import me.beanbag.nuker.eventsystem.CallbackHolder
import me.beanbag.nuker.eventsystem.EventBus
import me.beanbag.nuker.eventsystem.events.TickEvent
import me.beanbag.nuker.types.TickCounter
import me.beanbag.nuker.types.TimeoutSet
import java.util.concurrent.ConcurrentHashMap

object TimerUtils {
    private val tickTimerMaps = arrayListOf<ConcurrentHashMap<TickCounter, *>>()
    private val timeoutMaps = arrayListOf<TimeoutSet<*>>()
    private var callbackHolder = CallbackHolder()

    init {
        EventBus.addCallbackHolder(callbackHolder)
        callbackHolder.addCallback<TickEvent> {
            tickTimerMaps.forEach {
                it.keys.forEach { counter -> counter.tickCounter() }
            }

            timeoutMaps.forEach {
                it.updateMap()
            }
        }
    }

    fun ConcurrentHashMap<TickCounter, *>.subscribeTickTimerMap() {
        tickTimerMaps.add(this)
    }

    fun TimeoutSet<*>.subscribeOnTickUpdate() {
        timeoutMaps.add(this)
    }
}