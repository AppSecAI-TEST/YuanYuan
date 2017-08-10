package xyz.zimuju.common.eventbus;

/**

 */

public class BusEvent {

    private String id;
    private Object obj;

    public BusEvent(String id, Object t) {
        this.id = id;
        obj = t;
    }

    public String getId() {
        return id;
    }

    public Object getObj() {
        return obj;
    }

}
