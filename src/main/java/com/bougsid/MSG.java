package com.bougsid;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by ayoub on 6/25/2016.
 */
@Component
public class MSG {
    private ResourceBundle resourceBundle;

    public MSG() {
        this.resourceBundle = ResourceBundle.getBundle("messages", Locale.FRANCE);
    }
    public String getMessage(String key){
        return this.resourceBundle.getString(key);
    }
}
