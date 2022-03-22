package com.jonah.cookiefactions.core.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class NoRainEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onRain(WeatherChangeEvent e) {
        if (e.toWeatherState()) e.setCancelled(true);
    }

}
