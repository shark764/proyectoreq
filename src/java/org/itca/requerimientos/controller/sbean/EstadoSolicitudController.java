package org.itca.requerimientos.controller.sbean;

import org.itca.requerimientos.model.entities.EstadoSolicitud;
import org.itca.requerimientos.controller.sbean.util.JsfUtil;
import org.itca.requerimientos.controller.sbean.util.JsfUtil.PersistAction;
import org.itca.requerimientos.controller.facade.EstadoSolicitudFacade;

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

@ManagedBean(name = "estadoSolicitudController")
@SessionScoped
public class EstadoSolicitudController implements Serializable {

    @EJB
    private org.itca.requerimientos.controller.facade.EstadoSolicitudFacade ejbFacade;
    private List<EstadoSolicitud> items = null;
    private EstadoSolicitud selected;

    public EstadoSolicitudController() {
    }

    public EstadoSolicitud getSelected() {
        return selected;
    }

    public void setSelected(EstadoSolicitud selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EstadoSolicitudFacade getFacade() {
        return ejbFacade;
    }

    public EstadoSolicitud prepareCreate() {
        selected = new EstadoSolicitud();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/CataloguesBundle").getString("EstadoSolicitudCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/CataloguesBundle").getString("EstadoSolicitudUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/CataloguesBundle").getString("EstadoSolicitudDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EstadoSolicitud> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/CataloguesBundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/CataloguesBundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<EstadoSolicitud> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EstadoSolicitud> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EstadoSolicitud.class)
    public static class EstadoSolicitudControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstadoSolicitudController controller = (EstadoSolicitudController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estadoSolicitudController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Short getKey(String value) {
            java.lang.Short key;
            key = Short.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Short value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EstadoSolicitud) {
                EstadoSolicitud o = (EstadoSolicitud) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EstadoSolicitud.class.getName()});
                return null;
            }
        }

    }

}
