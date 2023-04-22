package com.xbaimiao.moeskillcopy.api

import org.bukkit.configuration.ConfigurationSection

/**
 * @author 小白
 * @date 2023/4/22 17:17
 **/
interface ConfigurationReader<T> {

    fun read(section: ConfigurationSection): T

}