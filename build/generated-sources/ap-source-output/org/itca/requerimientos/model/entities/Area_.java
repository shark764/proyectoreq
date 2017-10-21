package org.itca.requerimientos.model.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.itca.requerimientos.model.entities.Empleado;
import org.itca.requerimientos.model.entities.Equipo;
import org.itca.requerimientos.model.entities.Prestamo;
import org.itca.requerimientos.model.entities.SolicitudRequerimiento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-20T23:45:02")
@StaticMetamodel(Area.class)
public class Area_ { 

    public static volatile ListAttribute<Area, Prestamo> prestamoList;
    public static volatile SingularAttribute<Area, String> codigo;
    public static volatile ListAttribute<Area, SolicitudRequerimiento> solicitudRequerimientoList;
    public static volatile ListAttribute<Area, Empleado> empleadoList;
    public static volatile ListAttribute<Area, Equipo> equipoList;
    public static volatile SingularAttribute<Area, Short> id;
    public static volatile SingularAttribute<Area, String> nombre;

}