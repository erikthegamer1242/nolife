package com.erikb.nolife;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.configuration.ConfigurationSection;

import javax.security.auth.login.LoginException;

public class Discord {

    public static JDA jdaBuilder;

    private String token;

    public Discord (ConfigurationSection section) {
         token = section.getString("token");
    }

    public void setup() {
        Nolife.getInstance().getLogger().info("Discord Bot started");
        try {
            jdaBuilder = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new DiscordEventListener())
                    .setActivity(Activity.playing("!nolife"))
                    .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER)
                    .build();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }
}
