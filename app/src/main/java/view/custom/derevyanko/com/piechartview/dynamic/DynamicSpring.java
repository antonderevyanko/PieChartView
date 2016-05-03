package view.custom.derevyanko.com.piechartview.dynamic;

/**
 * Created by anton on 4/30/16.
 */
public final class DynamicSpring implements Dynamic<Integer> {

    /** Used to compare floats, if the difference is smaller than this, they are considered equal */
    private static final float TOLERANCE = 0.01f;
    /** The amount of springiness that the dynamics has */
    private static final float SPRIGNESS = 70f;
    private static final float DAMPING_COEF = 0.45f;

    private float targetProportion; /** The position the dynamics should to be at */
    private int position;   /** The current position of the dynamics */
    private float velocity; /** The current velocity of the dynamics */
    private long lastTime;  /** The time the last update happened */
    private float damping;  /** The damping that the dynamics has */

    public DynamicSpring(float targetProportion) {
        this.targetProportion = targetProportion;
        this.damping = (float) (DAMPING_COEF * 2 * Math.sqrt(SPRIGNESS));
    }

    @Override
    public boolean isFinished() {
        final boolean standingStill = Math.abs(velocity) < TOLERANCE;
        final boolean isAtTarget = (targetProportion - position) < TOLERANCE;
        return standingStill && isAtTarget;
    }

    @Override
    public void update(long now) {
        float dt = Math.min(now - lastTime, 50) / 1000f;
        float x = position - targetProportion;
        float acceleration = -SPRIGNESS * x - damping * velocity;
        velocity += acceleration * dt;
        position += velocity * dt;
        lastTime = now;
    }

    @Override
    public Integer getPosition() {
        return position;
    }
}
