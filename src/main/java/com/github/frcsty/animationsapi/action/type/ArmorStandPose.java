package com.github.frcsty.animationsapi.action.type;

import com.github.frcsty.animationsapi.util.CoordinateConverter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public enum ArmorStandPose {

    HEAD_POSE("HEAD"),
    BODY_POSE("BODY"),
    RIGHT_ARM_POSE("RIGHT_ARM"),
    LEFT_ARM_POSE("LEFT_ARM"),
    RIGHT_LEG_POSE("RIGHT_LEG"),
    LEFT_LEG_POSE("LEFT_LEG");

    private final String poseString;

    ArmorStandPose(final String poseString) {
        this.poseString = poseString;
    }

    public static ArmorStandPose getByString(final String pose) {
        ArmorStandPose result = null;

        for (final ArmorStandPose armorStandPose : values()) {
            if (armorStandPose.poseString.equalsIgnoreCase(pose)) {
                result = armorStandPose;
                break;
            }
        }

        return result;
    }

    public static void execute(final ArmorStandPose pose, final ArmorStand entity, final int[] rotation) {
        final EulerAngle angle = getAngleFromRotation(rotation);

        switch (pose) {
            case HEAD_POSE:
                entity.setHeadPose(angle);
                break;
            case BODY_POSE:
                entity.setBodyPose(angle);
                break;
            case RIGHT_ARM_POSE:
                entity.setRightArmPose(angle);
                break;
            case LEFT_ARM_POSE:
                entity.setLeftArmPose(angle);
                break;
            case RIGHT_LEG_POSE:
                entity.setRightLegPose(angle);
                break;
            case LEFT_LEG_POSE:
                entity.setLeftLegPose(angle);
                break;
        }
    }

    private static EulerAngle getAngleFromRotation(final int[] rotation) {
        return new EulerAngle(
                CoordinateConverter.toRadians(rotation[0]),
                CoordinateConverter.toRadians(rotation[1]),
                CoordinateConverter.toRadians(rotation[2])
        );
    }

}
