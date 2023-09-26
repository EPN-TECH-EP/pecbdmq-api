package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.util.Date;

@Data
public class FuncionarioApiDto {
        private String apellidos;
        private String callePrincipalResidencia;
        private String calleSecundariaResidencia;
        private String cargo;
        private String cedula;
        private String codigoCantonCuartoNivel;
        private String codigoCantonNacimiento;
        private String codigoCantonResidencia;
        private String codigoCantonSegundoNivel;
        private String codigoCantonTercerNivel;
        private String codigoEstacion;
        private String codigoProvinciaCuartoNivel;
        private String codigoProvinciaNacimiento;
        private String codigoProvinciaResidencia;
        private String codigoProvinciaSegundoNivel;
        private String codigoProvinciaTercerNivel;
        private String codigoUnidadGestion;
        private String correoInstitucional;
        private String descripcionMeridoDeportivo;
        private String descripcionMeritoAcademico;
        private String fechaNacimiento;
        private Date fechaIngreso;
        private String grado;
        private String grupoOcupacional;
        private String institucionSegundoNivel;
        private String nombres;
        private String numeroCasaResidencia;
        private String paisCuartoNivel;
        private String paisResidencia;
        private String paisSegundoNivel;
        private String paisTercerNivel;
        private String sexo;
        private String telefonoCelular;
        private String telefonoConvencional;
        private Boolean tieneMeritoAcademico;
        private Boolean tieneMeritoDeportivo;
        private String tipoSangre;
        private String tituloCuartoNivel;
        private String tituloSegundoNivel;
        private String tituloTercerNivel;
}
