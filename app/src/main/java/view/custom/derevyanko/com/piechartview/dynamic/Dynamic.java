package view.custom.derevyanko.com.piechartview.dynamic;

/**
 * Created by anton on 4/30/16.
 */
public interface Dynamic<T> {

    boolean isFinished();

    void update(long now);

    T getPosition();

}
