package ut8.agenda.interfaz;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ut7.agenda.io.AgendaIO;
import ut7.agenda.modelo.AgendaContactos;
import ut7.agenda.modelo.Contacto;
import ut7.agenda.modelo.Personal;

public class GuiAgenda extends Application {
	private AgendaContactos agenda;
	private MenuItem itemImportar;
	private MenuItem itemExportarPersonales;
	private MenuItem itemSalir;

	private MenuItem itemBuscar;
	private MenuItem itemFelicitar;

	private MenuItem itemAbout;

	private TextArea areaTexto;

	private RadioButton rbtListarTodo;
	private RadioButton rbtListarSoloNumero;
	private Button btnListar;

	private Button btnPersonalesEnLetra;
	private Button btnPersonalesOrdenadosPorFecha;

	private TextField txtBuscar;

	private Button btnClear;
	private Button btnSalir;

	private char[] abecedario = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	@Override
	public void start(Stage stage) {
		agenda = new AgendaContactos(); // el modelo

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 1100, 700);
		stage.setScene(scene);
		stage.setTitle("Agenda de contactos");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.show();

	}

	private BorderPane crearGui() {
		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		return panel;
	}

	private BorderPane crearPanelPrincipal() {
		BorderPane panel = new BorderPane();

		panel.setPadding(new Insets(10));

		areaTexto = new TextArea();
		areaTexto.getStyleClass().add("textarea");

		panel.setTop(crearPanelLetras());
		panel.setCenter(areaTexto);
		panel.setLeft(crearPanelBotones());

		return panel;
	}

	/*
	 * Crea el panel de botones de la izquierda y se le asigna a cada boton sus
	 * respectivos estilos.
	 * 
	 * @return Un VBox.
	 */
	private VBox crearPanelBotones() {
		VBox panel = new VBox(10);

		txtBuscar = new TextField();
		txtBuscar.setPromptText("Buscar");
		txtBuscar.setMinHeight(40);
		txtBuscar.setOnAction(n -> buscar());
		VBox.setMargin(txtBuscar, new Insets(0, 0, 40, 0));
		txtBuscar.getStyleClass().add("text-field");

		ToggleGroup group = new ToggleGroup();

		rbtListarTodo = new RadioButton("Listar toda la agenda");

		rbtListarTodo.setToggleGroup(group);
		rbtListarTodo.setOnAction(e -> listar());
		rbtListarTodo.setSelected(true);
		rbtListarTodo.getStyleClass().add("radio-button");

		rbtListarSoloNumero = new RadioButton("Listar nº contactos");
		rbtListarSoloNumero.setOnAction(e -> totalContactos());
		rbtListarSoloNumero.setToggleGroup(group);
		rbtListarSoloNumero.getStyleClass().add("radio-button");

		int prefWidth = 250;

		btnListar = new Button("Listar");
		btnListar.setOnAction(e -> listar());
		btnListar.getStyleClass().add("botones");
		btnListar.setPrefWidth(prefWidth);
		VBox.setMargin(btnListar, new Insets(0, 0, 40, 0));

		btnPersonalesEnLetra = new Button("Contactos personales en letra");
		btnPersonalesEnLetra.getStyleClass().add("botones");
		btnPersonalesEnLetra.setPrefWidth(prefWidth);

		btnPersonalesOrdenadosPorFecha = new Button("Contactos personales\nordenados por fecha");
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		btnPersonalesOrdenadosPorFecha.setPrefWidth(prefWidth);

		btnPersonalesOrdenadosPorFecha.setOnMouseClicked(e -> personalesOrdenadosPorFecha());
		btnPersonalesEnLetra.setOnMouseClicked(f -> contactosPersonalesEnLetra());
		btnListar.setOnMouseClicked(g -> listar());
		itemFelicitar.setOnAction(h -> felicitar());

		btnClear = new Button("Clear");
		btnClear.setOnAction(e -> clear());
		btnClear.getStyleClass().add("botones");
		btnClear.setPrefWidth(prefWidth);
		VBox.setMargin(btnClear, new Insets(40, 0, 0, 0));

		btnSalir = new Button("Salir");
		btnSalir.setOnAction(e -> salir());
		btnSalir.getStyleClass().add("botones");
		btnSalir.setPrefWidth(prefWidth);

		panel.setPadding(new Insets(10));

		panel.getChildren().addAll(txtBuscar, rbtListarTodo, rbtListarSoloNumero, btnListar, btnPersonalesEnLetra,
				btnPersonalesOrdenadosPorFecha, btnClear, btnSalir);

		return panel;
	}

	/*
	 * Crea barra con todo el abecedario.
	 * 
	 * @return Un GridPane que es la barra.
	 * 
	 */
	private GridPane crearPanelLetras() {
		GridPane panel = new GridPane();

		panel.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		panel.setPadding(new Insets(10));
		panel.setHgap(5);
		panel.setVgap(5);

		int pos = 0;

		for (int i = 0; i < abecedario.length; i++) {
			String letraString = String.valueOf(abecedario[i]);
			char letraChar = abecedario[i];

			Button b = new Button(letraString);
			b.setOnAction(e -> contactosEnLetra(letraChar));

			b.getStyleClass().add("botonletra");
			b.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

			GridPane.setHgrow(b, Priority.ALWAYS);
			GridPane.setVgrow(b, Priority.ALWAYS);

			if (i >= 14) {
				panel.add(b, pos, 1);
				pos++;
			} else {
				panel.add(b, i, 0);
			}
		}
		return panel;
	}

	/*
	 * Crea la barra de menu.
	 * 
	 * @return MenuBar que es la barra del menu.
	 * 
	 */
	private MenuBar crearBarraMenu() {
		MenuBar barra = new MenuBar();

		Menu archivo = new Menu();
		archivo.setText("Archivo");
		itemFelicitar = new MenuItem();

		itemImportar = new MenuItem("Importar agenda");
		itemImportar.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
		itemImportar.setOnAction(e -> importarAgenda());

		itemExportarPersonales = new MenuItem("Exportar Personales");
		itemExportarPersonales.setDisable(true);
		itemExportarPersonales.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
		itemExportarPersonales.setOnAction(f -> exportarPersonales());

		itemSalir = new MenuItem("Salir");
		itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		itemSalir.setOnAction(g -> salir());
		archivo.getItems().addAll(itemImportar, itemExportarPersonales, itemSalir);

		Menu operaciones = new Menu();
		operaciones.setText("Operaciones");
		itemBuscar = new MenuItem("Buscar");
		itemBuscar.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));
		itemBuscar.setOnAction(j -> buscar());
		itemFelicitar = new MenuItem("Felicitar");
		itemFelicitar.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
		itemFelicitar.setOnAction(a -> felicitar());
		operaciones.getItems().addAll(itemBuscar, itemFelicitar);

		Menu help = new Menu();
		help.setText("Help");
		itemAbout = new MenuItem("About");
		itemAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		itemAbout.setOnAction(h -> about());
		help.getItems().add(itemAbout);

		barra.getMenus().addAll(archivo, operaciones, help);

		return barra;
	}

	/*
	 * Importa la agenda, para eso se abre una ventana, seleccionamos el archivo csv
	 * a importar y lo importamos. Al importar nos apareceran La cantidad de
	 * contactos que no se han podido importar.
	 * 
	 */
	private void importarAgenda() {
		Stage s = new Stage();
		FileChooser fc = new FileChooser();
		fc.setTitle("Abrir fichero csv");
		File f = fc.showOpenDialog(s);
		int errores = AgendaIO.importar(agenda, f.getPath());
		areaTexto.appendText("No se han podido importar " + errores + " contactos.");

		itemImportar.setDisable(true);
		itemExportarPersonales.setDisable(false);
	}

	/*
	 * Exporta la agenda con todos los personales. Al exportar se nos abre una
	 * ventana para seleccionar la ruta del destino y el nombre del archivo.
	 */
	private void exportarPersonales() {
		Stage s = new Stage();
		FileChooser fc = new FileChooser();
		fc.setTitle("Exportar datos personales");
		File f = fc.showSaveDialog(s);
		AgendaIO.exportarPersonales(agenda, f.getPath());
	}

	/*
	 * Lista todos los contactos, en caso de que la agenda no este importada nos
	 * lanzara un mensaje.
	 */
	private void listar() {
		clear();
		if (!itemImportar.isDisable()) {
			areaTexto.setText("Importa primero la agenda.");
		} else {
			areaTexto.appendText("AGENDA DE CONTACTOS\n\n" + agenda.toString());
		}
	}

	/*
	 * Nos devuelve la cantidad de contactos que hay en la agenda, si la agenda no
	 * esta importada nos pide por un mensaje que lo importemos.
	 * 
	 */
	private void totalContactos() {
		if (!itemImportar.isDisable()) {
			areaTexto.setText("Importa primero la agenda.");
		} else {
			areaTexto.setText("Total contactos en la agenda:\t " + String.valueOf(agenda.totalContactos()));
		}

	}

	/*
	 * Sera necesario importar la agenda primero, sino, nos lanza un mensaje
	 * indicandonos que se debe de importar la agenda. Despues, se nos abre una
	 * ventana y seleccionamos la letra de los contactos que queremos sacar y nos
	 * imprime los contactos personales ordenados por la fecha por la letra
	 * especificada. En caso de que no haya ningun contacto nos lanza otro mensaje.
	 * 
	 */
	private void personalesOrdenadosPorFecha() {
		clear();
		Character[] abecedario3 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P',
				'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'Y', 'X', 'Z' };

		if (!itemImportar.isDisable()) {
			areaTexto.setText("Importa primero la agenda.");

		} else {
			ChoiceDialog<Character> cd = new ChoiceDialog(abecedario3[0], abecedario3);
			cd.setTitle("Selector de letra");
			cd.setHeaderText(null);
			cd.setContentText("Eliga la opcion: ");

			Optional<Character> resultado = cd.showAndWait();

			if (resultado.isPresent()) {
				Character opcionElegida = resultado.get();
				if (agenda.contieneLetra(opcionElegida)) {
					List<Personal> lista = agenda.personalesOrdenadosPorFechaNacimiento(opcionElegida);
					if (lista.isEmpty()) {
						areaTexto.setText("No hay contactos personales.");
					} else {
						areaTexto.appendText("Contactos personales ordenados por la fecha de nacimiento \n\n"
								+ opcionElegida + "\n\n");
						for (Personal p : lista) {
							areaTexto.appendText(p.toString() + "\n");
						}
					}
					cd.close();
				} else {
					areaTexto.setText("No hay contactos personales.");
				}
			}
		}

	}

	/*
	 * Sera necesario importar la agenda primero, sino, nos lanza un mensaje
	 * indicandonos que se debe de importar la agenda. Despues, se nos abre una
	 * ventana y seleccionamos la letra de los contactos que queremos sacar y nos
	 * imprime los contactos personales por la letra especificada. En caso de que no
	 * haya ningun contacto nos lanza otro mensaje.
	 * 
	 */
	private void contactosPersonalesEnLetra() {
		clear();

		Character[] abecedario3 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P',
				'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'Y', 'X', 'Z' };

		if (!itemImportar.isDisable()) {
			areaTexto.setText("Importa primero la agenda.");

		} else {
			ChoiceDialog<Character> c = new ChoiceDialog(abecedario3[0], abecedario3);
			c.setTitle("Selector de letra");
			c.setHeaderText(null);
			c.setContentText("Eliga la opcion: ");

			Optional<Character> resultado = c.showAndWait();
			Character opcionElegida = resultado.get();
			if (resultado.isPresent()) {
				if (agenda.contieneLetra(opcionElegida)) {
					List<Personal> lista = agenda.personalesEnLetra(opcionElegida);
					if (lista.isEmpty()) {
						areaTexto.setText("La letra " + opcionElegida + " no esta en la agenda.");
					} else {
						areaTexto.appendText("Contactos personales en la letra " + opcionElegida + " (" + lista.size()
								+ " contacto/s) \n\n");
						for (Personal p : lista) {
							areaTexto.appendText(p.toString() + "\n");
						}
					}
					c.close();
				} else {
					areaTexto.setText("La letra " + opcionElegida + " no esta en la agenda.");
					c.close();
				}

			}
		}
	}

	/*
	 * Sera necesario importar la agenda primero, sino, nos lanza un mensaje
	 * indicandonos que se debe de importar la agenda. Despues, se nos abre una
	 * ventana y seleccionamos la letra de los contactos que queremos sacar y nos
	 * imprime primero una letra con la cantidad de contactos que hay en la letra
	 * especificada y despues una lista de todos los contactos en esa letra. En caso
	 * de que no haya ningun contacto nos lanza otro mensaje.
	 * 
	 * @param La letra por la que se quieren buscar los contactos.
	 * 
	 */
	private void contactosEnLetra(char letra) {
		clear();

		Character[] abecedario3 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P',
				'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'Y', 'X', 'Z' };

		if (!itemImportar.isDisable()) {
			areaTexto.appendText("Importa primero la agenda.");

		} else {

			ChoiceDialog<Character> cd = new ChoiceDialog(abecedario3[0], abecedario3);
			cd.setTitle("Selector de letra");
			cd.setHeaderText(null);
			cd.setContentText("Eliga la opcion: ");

			Optional<Character> resultado = cd.showAndWait();

			if (resultado.isPresent()) {
				Character opcionElegida = resultado.get();
				if (agenda.contieneLetra(opcionElegida)) {
					Set<Contacto> lista = agenda.contactosEnLetra(opcionElegida);
					if (lista.isEmpty()) {
						areaTexto.setText("Contactos en la letra " + opcionElegida + " \n\n\nNo hay contactos.");
					} else {
						areaTexto.appendText(
								"Contactos en la letra " + opcionElegida + " (" + lista.size() + " contacto/s) \n\n");
						for (Contacto c : lista) {
							areaTexto.appendText(c.toString() + "\n");
						}
					}
					cd.close();
				}

				else {
					areaTexto.setText("Contactos en la letra " + opcionElegida + " \n\\n\\nNo hay contactos.");
				}
			}
		}
	}

	/*
	 * Se debe de importar primero la agenda, en caso de no tenerlo importado nos
	 * lanza un mensaje. Despues, nos saca la fecha actual y todos los contactos a
	 * los que hay que felicitar.
	 * 
	 */
	private void felicitar() {
		clear();

		if (!itemImportar.isDisable()) {
			areaTexto.appendText("Importa primero la agenda.");
		}

		else {
			LocalDate lc = LocalDate.now();
			areaTexto.appendText(
					"Hoy es el " + lc.getDayOfMonth() + " del " + lc.getMonthValue() + " de " + lc.getYear() + "\n");

			if (!agenda.felicitar().isEmpty()) {
				areaTexto.appendText(agenda.felicitar().toString());
			} else {
				areaTexto.appendText("Hoy no cumple años ningun contacto.");
			}
		}

	}

	/*
	 * Se busca el contacto por la letra introducida en el TextField.
	 * 
	 */
	private void buscar() {
		clear();

		String texto = txtBuscar.getText();

		if (agenda.buscarContactos(texto).isEmpty()) {
			areaTexto.appendText("No existe el contacto que buscas.");
		} else if (texto.equals("")) {
			areaTexto.setText("No has introduciodo nada para buscar.");
		} else if (!agenda.buscarContactos(texto).isEmpty()) {
			areaTexto.appendText(agenda.buscarContactos(texto).toString());
		}

		cogerFoco();
	}

	/*
	 * Nos saca una ventana personalizada.
	 * 
	 */
	private void about() {
		Alert a = new Alert(Alert.AlertType.INFORMATION);

		a.setTitle("About Agenda de Contactos");
		a.setHeaderText(null);
		a.setContentText("Mi agenda de \ncontactos");

		DialogPane dp = a.getDialogPane();
		dp.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		a.showAndWait();
	}

	/*
	 * Nos limpia la pantalla del TextArea
	 * 
	 */
	private void clear() {
		areaTexto.setText("");
	}

	/*
	 * Cierra la aplicacion AgendaContactos.
	 * 
	 */
	private void salir() {
		Platform.exit();
	}

	private void cogerFoco() {
		txtBuscar.requestFocus();
		txtBuscar.selectAll();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
