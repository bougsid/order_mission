package com.bougsid.converters;

import com.bougsid.ville.Ville;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 * Created by ayoub on 6/29/2016.
 */
@FacesConverter("villeConverter")
public class VilleConverter implements javax.faces.convert.Converter {
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        Object ret = null;
        if (arg1 instanceof PickList) {
            Object dualList = ((PickList) arg1).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Object o : dl.getSource()) {
                String id = "" + ((Ville) o).getId();
                if (arg2.equals(id)) {
                    ret = o;
                    break;
                }
            }
            if (ret == null)
                for (Object o : dl.getTarget()) {
                    String id = "" + ((Ville) o).getId();
                    if (arg2.equals(id)) {
                        ret = o;
                        break;
                    }
                }
        }
        return ret;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        String str = "";
        if (arg2 instanceof Ville) {
            str = "" + ((Ville) arg2).getId();
        }
        return str;
    }
}
