package com.github.frcsty.animationsapi;

import com.github.frcsty.animationsapi.action.AnimationExecutor;
import com.github.frcsty.animationsapi.action.type.Animation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;

public final class AnimationsAPI extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getCommand("animations-test").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player = (Player) sender;

        final AnimationExecutor executor = new AnimationExecutor(
                new Animation("RIGHT_ARM", 0, 0, 10),
                new Animation("RIGHT_ARM", 0, 0, 20),
                new Animation("RIGHT_ARM", 0, 0, 30),
                new Animation("RIGHT_ARM", 0, 0, 40),
                new Animation("RIGHT_ARM", 0, 0, 50),
                new Animation("RIGHT_ARM", 0, 0, 60),
                new Animation("RIGHT_ARM", 0, 0, 70),
                new Animation("RIGHT_ARM", 0, 0, 80),
                new Animation("RIGHT_ARM", 0, 0, 90)
        );

        executor.addAnimationPart(2,
                new Animation("RIGHT_ARM", 0, 0, 100),
                new Animation("RIGHT_ARM", 0, 0, 110),
                new Animation("RIGHT_ARM", 0, 0, 120),
                new Animation("RIGHT_ARM", 0, 0, 130),
                new Animation("RIGHT_ARM", 0, 0, 140),
                new Animation("RIGHT_ARM", 0, 0, 130),
                new Animation("RIGHT_ARM", 0, 0, 120),
                new Animation("RIGHT_ARM", 0, 0, 110)
        );

        executor.addAnimationPart(3,
                new Animation("RIGHT_ARM", 0, 0, 100),
                new Animation("RIGHT_ARM", 0, 0, 90),
                new Animation("RIGHT_ARM", 0, 0, 80),
                new Animation("RIGHT_ARM", 0, 0, 70),
                new Animation("RIGHT_ARM", 0, 0, 60),
                new Animation("RIGHT_ARM", 0, 0, 50),
                new Animation("RIGHT_ARM", 0, 0, 40),
                new Animation("RIGHT_ARM", 0, 0, 30),
                new Animation("RIGHT_ARM", 0, 0, 20),
                new Animation("RIGHT_ARM", 0, 0, 10),
                new Animation("RIGHT_ARM", 0, 0, 0)
        );

        executor.invalidateEntityOnFinish(true);
        executor.replayAmount(2, 2);

        final Block targetBlock = player.getTargetBlock(new HashSet<>(Arrays.asList(Material.AIR)), 10);
        final Location loc = targetBlock.getLocation();
        loc.add(.5F, 1.5F, .5F);

        final ArmorStand entity = (ArmorStand) targetBlock.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        entity.setBasePlate(false);
        entity.setArms(true);

        executor.executeWithDelay(entity, 4);

        return true;
    }
}
