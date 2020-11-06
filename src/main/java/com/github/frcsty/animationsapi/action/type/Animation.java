package com.github.frcsty.animationsapi.action.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Animation implements ArmorStandAnimation {

    private final Map<ArmorStandPose, int[]> poses;

    public Animation(final Object... poses) {
        this.poses = getReplacements(poses);
    }

    @Override public Map<ArmorStandPose, int[]> build() {
        return this.poses;
    }

    private Map<ArmorStandPose, int[]> getReplacements(final Object... values) {
        final Map<ArmorStandPose, int[]> conversion = new HashMap<>();
        for (int i = 0; i < values.length - 1; i += 4) {
            final ArmorStandPose pose = ArmorStandPose.getByString((String) values[i]);
            final int[] rotation = new int[]{(int) values[i + 1], (int) values[i + 2], (int) values[i + 3]};
            conversion.put(pose, rotation);
        }

        return conversion;
    }

}
