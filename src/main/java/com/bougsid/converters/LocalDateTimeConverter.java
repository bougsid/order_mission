package com.bougsid.converters;

/**
 * Created by ayoub on 6/24/2016.
 */

import com.bougsid.MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
@Scope("request")
public class LocalDateTimeConverter implements javax.faces.convert.Converter {
    @Autowired
    private MSG msg;
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return LocalDateTime.parse(value,DateTimeFormatter.ofPattern(msg.getMessage("date.pattern")));
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(msg.getMessage("mission.error.date"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
//            context.addMessage(component.getClientId(context), message);
            e.printStackTrace();
            throw new ConverterException(message);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        LocalDateTime dateValue = (LocalDateTime) value;

        return dateValue.format(DateTimeFormatter.ofPattern(msg.getMessage("date.pattern")));
    }

}
