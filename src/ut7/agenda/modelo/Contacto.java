package ut7.agenda.modelo;
/*
 * La clase Contacto es una clase abstracta que crea un contacto con un nombre, 
 * apellido, telefono y email. <br>
 * 
 * 	@author Naiara Borrega
 */
public abstract class Contacto implements Comparable<Contacto> {
	protected String nombre;
	protected String apellidos;
	protected String telefono;
	protected String email;

	/*
	 * El constructor construye la clase Contacto.<br>
	 * 
	 * @param nombre - Nombre del contacto.
	 * @param apellidos - Apellidos del contacto.
	 * @param telefono - Telefono del contacto.
	 * @param email - Email del contacto.
	 */
	public Contacto(String nombre, String apellidos, String telefono,
			String email) {
		this.nombre = nombre.toUpperCase();
		this.apellidos = apellidos.toUpperCase();
		this.telefono = telefono;
		this.email = email.toLowerCase();
	}

	/*
	 * Este metodo devuelve el nombre del contacto en un String.<br>
	 * 
	 * @return String
	 */
	public String getNombre() {
		return nombre;
	}

	/*
	 * Este metodo modifica el nombre del contacto.<br>
	 * 
	 * @param nombre - El nuevo nombre del contacto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/*
	 * Este metodo devuelve los apellidos del contacto en String.<br>
	 * 
	 * @return String
	 */
	public String getApellidos() {
		return apellidos;
	}

	/*
	 * Este metodo modifica los apellidos del contacto.<br>
	 * 
	 * @param apellidos - Los nuevos apellidos.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/*
	 * Este metodo devuelve el telefono del contacto en String.<br>
	 * 
	 * @return String
	 */
	public String getTelefono() {
		return telefono;
	}
	
	/*
	 * Este metodo modifica el telefono del contacto.<br>
	 * 
	 * @param telefono - El nuevo telefono.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/*
	 * Este metodo devuelve la firma.<br>
	 * 
	 * @return firma()
	 */
	public String getEmail() {
		return firma();
	}

	/*
	 * Este metodo modifica el email del contacto.<br>
	 * 
	 * @param email - El nuevo email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/*
	 * Este metodo devuelve la primera letra del contacto en char.<br>
	 * 
	 * @return char
	 */
	public char getPrimeraLetra() {
		return nombre.charAt(0);
	}

	/*
	 * Este metodo devuelve el numero hash del email en int.<br>
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return email.hashCode();

	}

	/*
	 * Este metodo dice si dos contactos son identicos.<br>
	 * 
	 * @param obj - un objeto contacto
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Contacto) {
			Contacto c = (Contacto) obj;
			if(this.nombre.equals(c.getNombre()) && this.apellidos.equals(c.getApellidos()) && this.email.equals(c.email)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Este metodo es para ordenar los contactos.<br>
	 * 
	 * @param o - un objeto contacto
	 * @return int
	 */
	@Override
	public int compareTo(Contacto o) {
		if(this.apellidos.equals(o.getApellidos())) {
			return this.nombre.compareTo(o.getNombre());
		}else {
			return this.apellidos.compareTo(o.getApellidos());
		}
	}
	
	/**
	 * Este metodo es una representacion textual de la clase Contacto.<br>
	 * 
	 * @return Un String con la representacion textual de la clase.
	 */
	@Override
	public String toString() {
		return  this.apellidos + ", " + this.nombre + " (" + this.getClass().getSimpleName() + ") " + 
				"\n" + "Tfno: " + this.telefono + " | " + "email: " + this.email + "\n";
	}
	
	public abstract String firma();
}
