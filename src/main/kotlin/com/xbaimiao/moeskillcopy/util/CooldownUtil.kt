package com.xbaimiao.moeskillcopy.util

import com.xbaimiao.easylib.module.utils.submit
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player
import java.text.MessageFormat

/**
 * @author 小白
 * @date 2023/4/22 17:14
 **/
object CooldownUtil {

    fun cooldownMessage(target: Player, time: Long, message: String) {
        var timeClone = time
        submit(period = 20) {
            if (timeClone-- <= 0) {
                cancel()
                return@submit
            }
            val component = TextComponent(MessageFormat.format(message, timeClone))
            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, component)
        }
    }

}