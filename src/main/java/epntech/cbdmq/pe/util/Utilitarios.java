package epntech.cbdmq.pe.util;

import org.springframework.stereotype.Component;

import epntech.cbdmq.pe.excepcion.dominio.DataException;

import static epntech.cbdmq.pe.constante.MensajesConst.CEDULA_INCORRECTA;
import static epntech.cbdmq.pe.constante.MensajesConst.ERROR_REGISTRO;

@Component
public class Utilitarios {

	public boolean validadorDeCedula(String cedula) throws DataException {
		boolean cedulaCorrecta = false;

		try {

			if (cedula.length() == 10) 
			{
				int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
				if (tercerDigito < 6) {
					int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
					int verificador = Integer.parseInt(cedula.substring(9, 10));
					int suma = 0;
					int digito = 0;
					for (int i = 0; i < (cedula.length() - 1); i++) {
						digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
						suma += ((digito % 10) + (digito / 10));
					}

					if ((suma % 10 == 0) && (suma % 10 == verificador)) {
						cedulaCorrecta = true;
					} else if ((10 - (suma % 10)) == verificador) {
						cedulaCorrecta = true;
					} else {
						cedulaCorrecta = false;
					}
				} else {
					cedulaCorrecta = false;
				}
			} else {
				cedulaCorrecta = false;
			}
		} catch (NumberFormatException nfe) {
			cedulaCorrecta = false;
		} catch (Exception err) {
			System.out.println("Una excepcion ocurrio en el proceso de validadcion");
			cedulaCorrecta = false;
			throw new DataException(ERROR_REGISTRO);
		}

		if (!cedulaCorrecta) {
			System.out.println("La Cédula ingresada es Incorrecta");
			throw new DataException(CEDULA_INCORRECTA);
		}
		return cedulaCorrecta;
	}
}
