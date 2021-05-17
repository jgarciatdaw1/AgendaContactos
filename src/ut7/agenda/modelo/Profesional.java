package ut7.agenda.modelo;
import java.util.Random;

/**
 * Esta clase modela un contacto profesional con todos los atributos heredados de la clase Contacto.<br>
 * Un contacto profesional, te permite guardar además de nombre, apellidos, telefono y contacto,<br>
 * un el nombre de la empresa y contiene cuatro saludos
 * que serán escogidos de forma aleatoria.
 *  
 * 
 * 
 * @see {@link Contacto}
 * @author Jon García
 * 
 */

public class Profesional extends Contacto {

	private String nombreEmpresa;
	private Random rnd;
	private final String[] SALUDOS = {"Atentamente", "Saludos", "Saludos cordiales", "Mis mejores deseos"};

	/**
	 * 
	 * Este constructor modela un contacto Profesional, herendando los atributos de contacto y añadiendo
	 * nombre empresa
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param email
	 * @param nombreEmpresa
	 * @see {@link Contacto}
	 */
	public Profesional(String nombre, String apellidos, String telefono, String email, String nombreEmpresa) {
		super(nombre, apellidos, telefono, email);
		this.nombreEmpresa = parsearEmpresa(nombreEmpresa);
		this.rnd = new Random();
	}

	private String parsearEmpresa(String nombre) {
		String output = "";
		String[] split = nombre.split(" ");
		for (String nombreSpliteado : split) {
			output += nombreSpliteado.substring(0, 1).toUpperCase()
				    + nombreSpliteado.substring(1, nombreSpliteado.length()) + " ";
		}
		return output;
	}
	
	/**
	 * Devuelve un saludo aleatorio de entre los siguientes:<br>
	 * Atentamente, Saludos, Saludos cordiales, Mis Mejores Deseos
	 * 
	 * @return - Un saludo aleatorio
	 */
	@Override
	public String firma() {
		return SALUDOS[rnd.nextInt(SALUDOS.length)];
	}
	
	/**
	 * Accesor para el nombre de la empresa
	 * 
	 * 
	 * @return Nombre de la empresa
	 */
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	/**
	 * Representacion textual de la clase Profesional
	 * 
	 * 
	 * @return String con la representación textual de la clase
	 */
	@Override
	public String toString() {
		return super.toString() + "Empresa: " + nombreEmpresa + "\n";
	}
	
}
