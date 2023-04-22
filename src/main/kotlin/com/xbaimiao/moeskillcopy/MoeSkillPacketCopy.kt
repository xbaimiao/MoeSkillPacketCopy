package com.xbaimiao.moeskillcopy

import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.module.utils.registerListener
import com.xbaimiao.moeskillcopy.skill.TimeKiller

@Suppress("unused")
class MoeSkillPacketCopy : EasyPlugin() {

    override fun enable() {
        saveDefaultConfig()
        registerListener(TimeKiller.read(config.getConfigurationSection("时离")!!))
    }

}