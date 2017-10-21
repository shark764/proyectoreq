/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itca.requerimientos.model.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dfhernandez
 */
@Entity
@Table(name = "tipo_requerimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoRequerimiento.findAll", query = "SELECT t FROM TipoRequerimiento t"),
    @NamedQuery(name = "TipoRequerimiento.findById", query = "SELECT t FROM TipoRequerimiento t WHERE t.id = :id"),
    @NamedQuery(name = "TipoRequerimiento.findByNombre", query = "SELECT t FROM TipoRequerimiento t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoRequerimiento.findByCodigo", query = "SELECT t FROM TipoRequerimiento t WHERE t.codigo = :codigo")})
public class TipoRequerimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableGenerator(name = "sec_tipo_requerimiento",
            table = "secuencia",
            pkColumnName = "secuencia",
            valueColumnName = "valor",
            pkColumnValue = "sec_tipo_requerimiento")
    @Id
    @GeneratedValue(generator = "sec_tipo_requerimiento")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "codigo")
    private String codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoRequerimiento", fetch = FetchType.LAZY)
    private List<DetalleSolicitud> detalleSolicitudList;

    public TipoRequerimiento() {
    }

    public TipoRequerimiento(Short id) {
        this.id = id;
    }

    public TipoRequerimiento(Short id, String nombre, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<DetalleSolicitud> getDetalleSolicitudList() {
        return detalleSolicitudList;
    }

    public void setDetalleSolicitudList(List<DetalleSolicitud> detalleSolicitudList) {
        this.detalleSolicitudList = detalleSolicitudList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoRequerimiento)) {
            return false;
        }
        TipoRequerimiento other = (TipoRequerimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + this.codigo + "] " + this.nombre;
        // return "org.itca.requerimientos.model.entities.TipoRequerimiento[ id=" + id + " ]";
    }
    
}
