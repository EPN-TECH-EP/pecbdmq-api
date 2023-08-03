package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

@Data
public class CiudadanoApiDto {

        private String cedula;
        private String estadoCivil;
        private String fechaNacimiento;
        private String lugarInscripcionNacimiento;
        private String lugarNacimiento;
        private String nombre;
        private String profesion;

}
