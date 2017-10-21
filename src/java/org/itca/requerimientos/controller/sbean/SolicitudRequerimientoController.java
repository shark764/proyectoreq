package org.itca.requerimientos.controller.sbean;

import org.itca.requerimientos.model.entities.SolicitudRequerimiento;
import org.itca.requerimientos.controller.sbean.util.JsfUtil;
import org.itca.requerimientos.controller.sbean.util.JsfUtil.PersistAction;
import org.itca.requerimientos.controller.facade.SolicitudRequerimientoFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "solicitudRequerimientoController")
@SessionScoped
public class SolicitudRequerimientoController implements Serializable {

    @EJB
    private org.itca.requerimientos.controller.facade.SolicitudRequerimientoFacade ejbFacade;
    private List<SolicitudRequerimiento> items = null;
    private SolicitudRequerimiento selected;

    public SolicitudRequerimientoController() {
    }

    public SolicitudRequerimiento getSelected() {
        return selected;
    }

    public void setSelected(SolicitudRequerimiento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SolicitudRequerimientoFacade getFacade() {
        return ejbFacade;
    }

    public SolicitudRequerimiento prepareCreate() {
        selected = new SolicitudRequerimiento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/RequestBundle").getString("SolicitudRequerimientoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/RequestBundle").getString("SolicitudRequerimientoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/RequestBundle").getString("SolicitudRequerimientoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<SolicitudRequerimiento> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/RequestBundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/RequestBundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<SolicitudRequerimiento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<SolicitudRequerimiento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = SolicitudRequerimiento.class)
    public static class SolicitudRequerimientoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SolicitudRequerimientoController controller = (SolicitudRequerimientoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "solicitudRequerimientoController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SolicitudRequerimiento) {
                SolicitudRequerimiento o = (SolicitudRequerimiento) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), SolicitudRequerimiento.class.getName()});
                return null;
            }
        }

    }

}
