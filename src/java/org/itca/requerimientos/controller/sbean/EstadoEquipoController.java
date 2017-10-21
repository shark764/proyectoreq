package org.itca.requerimientos.controller.sbean;

import org.itca.requerimientos.model.entities.EstadoEquipo;
import org.itca.requerimientos.controller.sbean.util.JsfUtil;
import org.itca.requerimientos.controller.sbean.util.JsfUtil.PersistAction;
import org.itca.requerimientos.controller.facade.EstadoEquipoFacade;

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

@ManagedBean(name = "estadoEquipoController")
@SessionScoped
public class EstadoEquipoController implements Serializable {

    @EJB
    private org.itca.requerimientos.controller.facade.EstadoEquipoFacade ejbFacade;
    private List<EstadoEquipo> items = null;
    private EstadoEquipo selected;

    public EstadoEquipoController() {
    }

    public EstadoEquipo getSelected() {
        return selected;
    }

    public void setSelected(EstadoEquipo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EstadoEquipoFacade getFacade() {
        return ejbFacade;
    }

    public EstadoEquipo prepareCreate() {
        selected = new EstadoEquipo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/CataloguesBundle").getString("EstadoEquipoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/CataloguesBundle").getString("EstadoEquipoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/CataloguesBundle").getString("EstadoEquipoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EstadoEquipo> getItems() {
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

    public List<EstadoEquipo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<EstadoEquipo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = EstadoEquipo.class)
    public static class EstadoEquipoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstadoEquipoController controller = (EstadoEquipoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estadoEquipoController");
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
            if (object instanceof EstadoEquipo) {
                EstadoEquipo o = (EstadoEquipo) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EstadoEquipo.class.getName()});
                return null;
            }
        }

    }

}
