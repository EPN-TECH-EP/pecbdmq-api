package epntech.cbdmq.pe.dominio.fichaPersonal.formacion;

import lombok.Data;

@Data
public class DatosEstudianteParaCrearUsuario {

    Long codDatosPersonales;
    String cedula;

    public DatosEstudianteParaCrearUsuario(Long codDatosPersonales, String cedula) {
        this.codDatosPersonales = codDatosPersonales;
        this.cedula = cedula;
    }
}
