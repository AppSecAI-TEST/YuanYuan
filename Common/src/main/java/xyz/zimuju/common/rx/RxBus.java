package xyz.zimuju.common.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {

    private final Subject<Object> objectSubject;

    private RxBus() {
        objectSubject = PublishSubject.create();
    }

    public static synchronized RxBus getInstance() {
        return RxBusHolder.instance;
    }

    public void send(Object o) {
        objectSubject.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return objectSubject.ofType(eventType);
    }

    private static class RxBusHolder {
        private static final RxBus instance = new RxBus();
    }
}
