package org.itca.requerimientos.controller.sbean;

import org.itca.requerimientos.model.entities.Roles;
import org.itca.requerimientos.controller.sbean.util.JsfUtil;
import org.itca.requerimientos.controller.sbean.util.JsfUtil.PersistAction;
import org.itca.requerimientos.controller.facade.RolesFacade;

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

@ManagedBean(name = "rolesController")
@SessionScoped
public class RolesController implements Serializable {

    @EJB
    private org.itca.requerimientos.controller.facade.RolesFacade ejbFacade;
    private List<Roles> items = null;
    private Roles selected;

    public RolesController() {
    }

    public Roles getSelected() {
        return selected;
    }

    public void setSelected(Roles selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setRolesPK(new org.itca.requerimientos.model.entities.RolesPK());
    }

    private RolesFacade getFacade() {
        return ejbFacade;
    }

    public Roles prepareCreate() {
        selected = new Roles();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/SecurityBundle").getString("RolesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/SecurityBundle").getString("RolesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/SecurityBundle").getString("RolesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Roles> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/SecurityBundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/org/itca/requerimientos/bundles/SecurityBundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Roles> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Roles> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Roles.class)
    public static class RolesControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RolesController controller = (RolesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rolesController");
            return controller.getFacade().find(getKey(value));
        }

        org.itca.requerimientos.model.entities.RolesPK getKey(String value) {
            org.itca.requerimientos.model.entities.RolesPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new org.itca.requerimientos.model.entities.RolesPK();
            key.setRol(Integer.parseInt(values[0]));
            key.setIduser(values[1]);
            return key;
        }

        String getStringKey(org.itca.requerimientos.model.entities.RolesPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getRol());
            sb.append(SEPARATOR);
            sb.append(value.getIduser());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Roles) {
                Roles o = (Roles) object;
                return getStringKey(o.getRolesPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Roles.class.getName()});
                return null;
            }
        }

    }

}
