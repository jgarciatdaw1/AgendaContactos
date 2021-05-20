package ut7.agenda.modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Un objeto de esta clase, modela una agenda para guardar contactos profesionales y personales, 
 * permitiendote realizar diversas funciones con ellos.<br><br>
 * Los contactos se guardan en orden alfabético por primer apellido y no se permiten duplicados.
 * 
 * @author Iker Iparraguirre, Jon García, Naiara Borrega
 * 
 */
public class AgendaContactos {
	private Map<Character, Set<Contacto>> agenda;

	public AgendaContactos() {
		agenda = new TreeMap<>();
	}

	/**
	 * Añade un nuevo contacto a la lista de contactos.
	 * Dicho contacto será añadido a la agenda en la letra que le corresponda.<br><br>
	 * En caso de que la letra no exista, se creará una nueva letra para posteriormente
	 * insertar el nuevo contacto especificado.
	 * 
	 * @param c - Contacto a añadir.
	 */
	public void añadirContacto(Contacto c) {
		char letra = c.getApellidos().charAt(0);
		if (agenda.containsKey(letra)) {
			agenda.get(letra).add(c);
		} else {
			Set<Contacto> temp = new TreeSet<>();
			temp.add(c);
			agenda.put(letra, temp);
		}
	}

	/**
	 * Este metodo devuelve un Set de los contactos que esten en la letra que le pidas.
	 * 
	 * @param letra - Letra en la que se quiere buscar.
	 * @return Set
	 */
	public Set<Contacto> contactosEnLetra(char letra) {
		return agenda.get(letra);
	}

	/**
	 * Este metodo devuelve un int de todos los contactos que hay en la agenda.
	 * 
	 * @return int
	 */
	public int totalContactos() {
		int contador = 0;
		for (Set<Contacto> lista : agenda.values()) {
			contador += lista.size();
		}
		return contador;
	}
	
	/**
	 * Busca un contacto en la agenda de contactos mediante una serie de
	 * caractéres especificados.
	 * 
	 * 
	 * @param texto - Texto para buscar
	 * @return Una lista con todas las coincidencias encontradas en base al texto introducido 
	 * 		   en parámetros. Si no se encuentra ninguna coincidencia, se devolverá una lista vacia.
	 */
	public List<Contacto> buscarContactos(String texto) {
		List<Contacto> temp = new ArrayList<>();
		for (Set<Contacto> lista : agenda.values()) {
			for (Contacto contacto : lista) {
				if ((contacto.getNombre() + " " + contacto.getApellidos()).contains(texto.toUpperCase())) {
					temp.add(contacto);
				}
			}
		}
		return temp;
	}

	/**
	 * Este metodo devuelve un List de todos los Personales que tengan la letra.
	 * @param La letra para buscar los personales (la clave del TreeMap).
	 * @return Una collecion List de todos los personales que tengan esa letra, si no hay ninguno, da error de NullPointerException.<br>
	 */
	public List<Personal> personalesEnLetra(char letra) {
		List<Personal> devuelve = new ArrayList<>();

		if(agenda.get(letra) == null) {
			return null;
		}
		for (Contacto contacto : agenda.get(letra)) {
			if (contacto instanceof Personal) {
				Personal p = (Personal) contacto;
				devuelve.add(p);
			}

		}
		
		return devuelve;
	}

	//Metodo privado creado para la interfaz para ver si existen contactos con esa letra. FALTA COMENTAR BIEN.
	public boolean contieneLetra(Character c) {
		if(agenda.containsKey(c)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Este metodo devuelve un List de Personal de tods los Personales a los que hay que felicitar.
	 * @return Una Lista de Personales a los que hay que felicitar.
	 */
	public List<Personal> felicitar() {
		List<Personal> devuelve = new ArrayList<>();

		for (Set<Contacto> contacto : agenda.values()) {
			for (Contacto c : contacto) {
				if (c instanceof Personal) {
					Personal p = (Personal) c;
					if (p.esCumpleaños()) {
						devuelve.add(p);
					}
				}
			}
		}

		return devuelve;
	}

	/**
	 * Este metodo devuelve un Map con los contactos ordenados por relacion.
	 * 
	 * @return Map
	 */
	public Map<Relacion, List<String>> personalesPorRelacion() {
		Map<Relacion, List<String>> contactos;
		contactos = new TreeMap<>();
		for (Set<Contacto> lista : agenda.values()) {
			for (Contacto temp : lista) {
				if (temp instanceof Personal) {
					String nombreapell = temp.getApellidos() + "  " + temp.getNombre();
					if (contactos.containsKey(((Personal) temp).getRel())) {
						contactos.get(((Personal) temp).getRel()).add(nombreapell);
					} else {
						List<String> temp1 = new ArrayList<>();
						temp1.add(nombreapell);
						contactos.put(((Personal) temp).getRel(), temp1);
					}
				}
			}
		}
		return contactos;
	}

	/**
	 * Dada una letra devuelve un List ordenado por la fecha de nacimiento de todos los Personales que empiecen por esa letra<br>
	 * 
	 * @param Un char de la primera letra del apellido del Personal.
	 * @return Un List de Personales ordenados por la fecha de nacimiento.
	 */
	public List<Personal> personalesOrdenadosPorFechaNacimiento(char letra) {
		List<Personal> devuelve = personalesEnLetra(letra);
		Collections.sort(devuelve, new Comparator<Personal>() {

			@Override
			public int compare(Personal o1, Personal o2) {
				if (o1.getFecha().isBefore(o2.getFecha())) {
					return -1;
				}

				return 1;
			}

		});
		return devuelve;
	}

	/**
	 * Representación textual de la agenda.<br>
	 * Se mostrarán todas las letras ordenadas alfabéticamente que contienen contactos
	 * en la agenda.
	 * 
	 * 
	 * @return String con la representación textual de la agenda
	 * 
	 */
	@Override
	public String toString() {
		String output = "";
		for (char letra : agenda.keySet()) {
			String bloque = letra + " (" + agenda.get(letra).size() + " contactos) \n---------------\n";
			for (Contacto contacto : agenda.get(letra)) {
				bloque += contacto + "\n";
			}
			output += bloque;
		}
		output += "(" + totalContactos() + " contacto/s)";
		return output;
	}
}
