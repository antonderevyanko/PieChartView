package view.custom.derevyanko.com.piechartview;

import android.util.FloatMath;

public final class DynamicChartData {

    /**
     * Used to compare floats, if the difference is smaller than this, they are
     * considered equal
     */
    private static final float TOLERANCE = 0.01f;

    /** The position the dynamics should to be at */
    private float targetProportion;

    /** The current position of the dynamics */
    private int position;

    /** The current velocity of the dynamics */
    private float velocity;

    /** The time the last update happened */
    private long lastTime;

    /** The amount of springiness that the dynamics has */
    private float springiness;

    /** The damping that the dynamics has */
    private float damping;

    private StaticGraphData staticGraphData;

    public DynamicChartData(StaticGraphData staticGraphData) {
        this.staticGraphData = staticGraphData;
        this.targetProportion = staticGraphData.getProportion();
        this.springiness = 70f;
        this.damping = (float) (0.30f * 2 * Math.sqrt(springiness));
    }

    public void setPosition(int position, long now) {
        this.position = position;
        lastTime = now;
    }

    public void setVelocity(float velocity, long now) {
        this.velocity = velocity;
        lastTime = now;
    }

    public void setTargetPosition(StaticGraphData staticGraphData, long now) {
        this.targetProportion = staticGraphData.getProportion();
        lastTime = now;
    }

    public void update(long now) {
        float dt = Math.min(now - lastTime, 50) / 1000f;

        float x = position - targetProportion;
        float acceleration = -springiness * x - damping * velocity;

        velocity += acceleration * dt;
        position += velocity * dt;

        lastTime = now;
    }

    public boolean isFinished() {
        final boolean standingStill = Math.abs(velocity) < TOLERANCE;
        final boolean isAtTarget = (targetProportion - position) < TOLERANCE;
        return standingStill && isAtTarget;
    }

    public int getPosition() {
        return position;
    }

    public float getTargetPos() {
        return targetProportion;
    }

    public float getVelocity() {
        return velocity;
    }

    public StaticGraphData getStaticData() {
        return staticGraphData;
    }
}
