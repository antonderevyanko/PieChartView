package view.custom.derevyanko.com.piechartview;

public final class DynamicChartData {

    /** Used to compare floats, if the difference is smaller than this, they are considered equal */
    private static final float TOLERANCE = 0.01f;
    /** The amount of springiness that the dynamics has */
    private static final float SPRIGNESS = 70f;
    private static final float DAMPING_COEF = 0.45f;

    /** The position the dynamics should to be at */
    private float targetProportion;

    /** The current position of the dynamics */
    private int position;

    /** The current velocity of the dynamics */
    private float velocity;

    /** The time the last update happened */
    private long lastTime;

    /** The damping that the dynamics has */
    private float damping;

    private StaticGraphData staticGraphData;

    public DynamicChartData(StaticGraphData staticGraphData) {
        this.staticGraphData = staticGraphData;
        this.targetProportion = staticGraphData.getProportion();
        this.damping = (float) (DAMPING_COEF * 2 * Math.sqrt(SPRIGNESS));
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
        float acceleration = -SPRIGNESS * x - damping * velocity;

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
