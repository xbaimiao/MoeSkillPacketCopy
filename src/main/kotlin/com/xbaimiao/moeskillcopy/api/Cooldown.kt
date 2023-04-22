package com.xbaimiao.moeskillcopy.api

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author 小白
 * @date 2023/4/22 17:07
 **/
abstract class Cooldown {

    abstract val cooldown: Long
    abstract val timeUnit: TimeUnit

    private val cache: Cache<UUID, Boolean> = buildCache()

    private fun buildCache(): Cache<UUID, Boolean> {
        return CacheBuilder.newBuilder()
            .expireAfterWrite(cooldown, timeUnit)
            .build()
    }

    protected fun canUse(uuid: UUID): Boolean {
        return cache.getIfPresent(uuid) == null
    }

    protected fun use(uuid: UUID) {
        cache.put(uuid, true)
    }

}