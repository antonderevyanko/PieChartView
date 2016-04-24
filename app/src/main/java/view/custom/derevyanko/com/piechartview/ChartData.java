package view.custom.derevyanko.com.piechartview;

import android.support.annotation.ColorRes;

public final class ChartData {

    private int count;
    @ColorRes
    private int colorResId;
    private boolean isFull;

    public ChartData(int count, int colorResId) {
        this(count, colorResId, false);
    }

    public ChartData(int count, int colorResId, boolean isFull) {
        this.count = count;
        this.colorResId = colorResId;
        this.isFull = isFull;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @ColorRes
    public int getColorResId() {
        return colorResId;
    }

    public void setColorResId(int colorResId) {
        this.colorResId = colorResId;
    }
}
