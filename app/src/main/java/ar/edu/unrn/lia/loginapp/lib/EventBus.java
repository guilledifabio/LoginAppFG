package ar.edu.unrn.lia.loginapp.lib;

/**
 * Created by Germán on 1/2/2017.
 */

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
