package com.cube_me.cubeme_crm;

import java.util.Observable;

/**
 * Created by FredrickCyril on 5/9/17.
 */

public class ObservableObject extends Observable {
    private static ObservableObject instance = new ObservableObject();

    public static ObservableObject getInstance() {
        return instance;
    }

    private ObservableObject() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
