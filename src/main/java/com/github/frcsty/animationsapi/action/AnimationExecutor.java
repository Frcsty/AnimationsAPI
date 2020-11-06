package com.github.frcsty.animationsapi.action;

import com.github.frcsty.animationsapi.AnimationsAPI;
import com.github.frcsty.animationsapi.action.type.ArmorStandAnimation;
import com.github.frcsty.animationsapi.action.type.ArmorStandPose;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class AnimationExecutor {

    private final AnimationsAPI plugin = JavaPlugin.getPlugin(AnimationsAPI.class);
    private final Map<Integer, LinkedList<ArmorStandAnimation>> animations = new HashMap<>();

    private boolean invalidateEntity;

    /**
     * Adds the provided set of animations to the executor's map index
     *
     * @param associatedAnimations animations we wish to initialize the executor with
     */
    public AnimationExecutor(final ArmorStandAnimation... associatedAnimations) {
        final LinkedList<ArmorStandAnimation> an = new LinkedList<>(Arrays.asList(associatedAnimations));

        this.animations.put(1, addAndGet(1, an));
    }

    /**
     * Adds animations to the existing list and retrieves it
     *
     * @param part          Animation part
     * @param additions     New animation additions
     * @return  Existing list with the additional animations
     */
    private LinkedList<ArmorStandAnimation> addAndGet(final int part, final LinkedList<ArmorStandAnimation> additions) {
        final LinkedList<ArmorStandAnimation> existingAnimations = this.animations.getOrDefault(part, new LinkedList<>());

        existingAnimations.addAll(additions);
        return existingAnimations;
    }

    /**
     * Adds "Replays" to the animation by stacking all added animations the specified
     * amount of times
     *
     * @param part      The part of the animation that should be replayed
     * @param replays   The amount of replays that should be added to the animation
     */
    public void replayAmount(final int part, final int replays) {
        for (int i = 1; i < replays; i++) {
            this.animations.put(part, addAndGet(part, this.animations.get(part)));
        }
    }

    /**
     * Determines if the associated entity should be removed
     * after the animation has finished
     *
     * @param invalidation  Weather the entity should be invalidated
     */
    public void invalidateEntityOnFinish(final boolean invalidation) {
        this.invalidateEntity = invalidation;
    }

    /**
     * Adds animations to a new/existing animation part
     *
     * @param part          The part the animations should be added to
     * @param animations    Additional animations
     */
    public void addAnimationPart(final int part, final ArmorStandAnimation... animations) {
        final LinkedList<ArmorStandAnimation> an = new LinkedList<>(Arrays.asList(animations));

        this.animations.put(part, addAndGet(part, an));
    }

    /**
     * Executes the animations provided to the executor, using the desired
     * delay in ticks
     *
     * @param manipulatedEntity The {@link ArmorStand} entity we wish to manipulate
     * @param tickRate          Desired delay in ticks
     */
    public void executeWithDelay(final ArmorStand manipulatedEntity, final long tickRate) {
        if (this.animations.size() == 0) return;

        final LinkedList<ArmorStandAnimation> executables = getAllSequencedAnimations();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (executables.size() == 0) {
                    cancel();
                    if (invalidateEntity) manipulatedEntity.remove();
                    return;
                }
                final ArmorStandAnimation animation = executables.removeFirst();

                manipulateEntity(manipulatedEntity, animation);
            }
        }.runTaskTimerAsynchronously(plugin, tickRate, tickRate);
    }

    /**
     * Returns a list of all animations sequenced
     *
     * @return  A {@link LinkedList} containing all sequenced animations
     */
    private LinkedList<ArmorStandAnimation> getAllSequencedAnimations() {
        final LinkedList<ArmorStandAnimation> sequence = new LinkedList<>();

        for (final int part : this.animations.keySet()) {
            sequence.addAll(this.animations.getOrDefault(part, new LinkedList<>()));
        }

        return sequence;
    }

    /**
     * Manipulates all of the {@link ArmorStand} entities poses from our animation
     *
     * @param entity    The {@link ArmorStand} entity we wish to manipulate
     * @param animation The current animation stage of the executor
     */
    private void manipulateEntity(final ArmorStand entity, final ArmorStandAnimation animation) {
        animation.build().forEach((move, rotation) ->
                ArmorStandPose.execute(move, entity, rotation)
        );
    }

}
