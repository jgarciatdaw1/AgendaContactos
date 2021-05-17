package ut7.agenda.modelo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * La clase Personal es una clase que hereda todos los atributos de contacto y añade dos atributos nuevos, fecha
 * de nacimiento y una relacion.<br>
 * Esta clase tiene una firma de email que es siempre "Un abrazo!!"<br>
 *
 *	@see {@link Contacto}
 *  @author Iker Iparraguirre
 *
 */
public class Personal extends Contacto {
	
	private LocalDate fecha;
	private DateTimeFormatter formateador;
	private Relacion rel;
	private final String FIRMA = "Un abrazo!!";

	/**
	 * Recibe como parametros los mismos que la clase Contacto y añade 2 mas, que 
	 * son un String de fecha y un enum relacion.<br>
	 * @see {@link Contacto}
	 */
	public Personal(String nombre, String apellidos, String telefono, String email, String fecha, Relacion relacion) {
		super(nombre, apellidos, telefono, email);
		String[] split = fecha.split("/");
		this.fecha = LocalDate.of(Integer.parseInt(split[2]), Integer.parseInt(split[1]), Integer.parseInt(split[0]));
		formateador = DateTimeFormatter.ofPattern("dd MMM yyyy", new Locale("es", "ES"));
		rel = relacion;
	}

	/**
	 * Este metodo devuelve la firma del email.<br>
	 * 
	 * @return La firma en String.
	 */
	@Override
	public String firma() {
		return this.FIRMA;
	}

	/**
	 * Este metodo verifica si hoy cumple años el Personal, para eso, el dia del mes y el mes deben de ser los mismos<br>
	 * 
	 * @return True si es el dia de su cumpleaños y false si no lo es.
	 */
	public boolean esCumpleaños() {
		LocalDate ahora = LocalDate.now();
		if (ahora.getDayOfMonth() == this.fecha.getDayOfMonth()) {
			if (ahora.getMonth().equals(this.fecha.getMonth())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Este metodo devuelve la fecha formateada en un String.<br>
	 * 
	 * @return La fecha formateada.
	 */
	public String getFechaFormateada() {
		return this.fecha.format(formateador);
	}
	
	/**
	 * Este metodo devuelve la fecha en formato LocalDate.<br>
	 * 
	 * @return La fecha en formato LocalDate.
	 */
	public LocalDate getFecha() {
		return this.fecha;
	}

	/**
	 * Este metodo devuelve la relacion del Personal.<br>
	 * 
	 * @return La relacion.
	 */
	public Relacion getRel() {
		return rel;
	}

	/**
	 * Este metodo modifica la relacion del Personal.<br>
	 * 
	 * @param La nueva relacion del Persona.
	 */
	public void setRel(Relacion rel) {
		this.rel = rel;
	}

	/**
	 * Este metodo representacion textual de la clase Personal.<br>
	 * 
	 * @return Un String con la representacion textual de la clase.
	 */
	@Override
	public String toString() {
		return super.toString() + "Fecha cumpleaños: " + getFechaFormateada() + "\n" + "Relacion: " + getRel() + "\n";
	}

}