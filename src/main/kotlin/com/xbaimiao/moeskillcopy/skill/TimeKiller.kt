package com.xbaimiao.moeskillcopy.skill

import com.xbaimiao.easylib.module.item.hasLore
import com.xbaimiao.easylib.module.item.isAir
import com.xbaimiao.easylib.module.utils.colored
import com.xbaimiao.easylib.module.utils.submit
import com.xbaimiao.moeskillcopy.api.ConfigurationReader
import com.xbaimiao.moeskillcopy.api.Cooldown
import com.xbaimiao.moeskillcopy.util.CooldownUtil
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.concurrent.TimeUnit

/**
 * @author 小白
 * @date 2023/4/22 17:07
 **/
class TimeKiller(
    override val cooldown: Long,
    override val timeUnit: TimeUnit,
    private val lore: String,
    private val deadTime: Long,
    private val deadMessage: String
) : Cooldown(), Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    fun damage(event: EntityDamageByEntityEvent) {
        val damager = event.damager as? Player? ?: return
        val entity = event.entity as? LivingEntity? ?: return

        val weapon = damager.itemInUse ?: damager.inventory.itemInMainHand
        if (weapon.isAir()) {
            return
        }

        if (weapon.hasLore(lore) && canUse(damager.uniqueId)) {
            use(damager.uniqueId)
            if (entity is Player) {
                CooldownUtil.cooldownMessage(entity, deadTime, deadMessage)
            }
            submit(delay = deadTime * 20L) {
                entity.damage(entity.health, damager)
            }
        }
    }

    companion object : ConfigurationReader<TimeKiller> {

        override fun read(section: ConfigurationSection): TimeKiller {
            val lore = section.getString("lore").colored()
            val cooldown = section.getLong("cooldown")
            val deadTime = section.getLong("dead-time")
            val deadMessage = section.getString("dead-message").colored()
            return TimeKiller(cooldown, TimeUnit.SECONDS, lore, deadTime, deadMessage)
        }

    }

}