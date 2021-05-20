package ut8.agenda.interfaz;

import java.io.File;
import java.util.Date;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import ut7.agenda.io.AgendaIO;
import ut7.agenda.modelo.AgendaContactos;

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
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'Y', 'X', 'Z' };

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

	private VBox crearPanelBotones() {
		VBox panel = new VBox(10);

		txtBuscar = new TextField();
		txtBuscar.setPromptText("Buscar");
		txtBuscar.setMinHeight(40);
		VBox.setMargin(txtBuscar, new Insets(0, 0, 40, 0));
		txtBuscar.getStyleClass().add("text-field");

		ToggleGroup group = new ToggleGroup();

		rbtListarTodo = new RadioButton("Listar toda la agenda");
		rbtListarTodo.setToggleGroup(group);
		rbtListarTodo.setSelected(true);
		rbtListarTodo.getStyleClass().add("radio-button");

		rbtListarSoloNumero = new RadioButton("Listar nº contactos");
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

		
		// CAMBIAR JON
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

	private GridPane crearPanelLetras() {
		// a completar
		GridPane panel = new GridPane();

		return panel;
	}

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

	private void exportarPersonales() {
		Stage s = new Stage();
		FileChooser fc = new FileChooser();
		fc.setTitle("Exportar datos personales");
		File f = fc.showSaveDialog(s);
		AgendaIO.exportarPersonales(agenda, f.getPath());
	}

	/**
	 *  
	 */
	private void listar() {
		clear();
		// a completar

	}

	private void personalesOrdenadosPorFecha() {
		clear();

		ChoiceDialog<String> cd = new ChoiceDialog("A", abecedario);

		if (!itemImportar.isDisable()) {
			areaTexto.appendText("Importa primero la agenda.");

		} else {
			cd.setTitle("Selector de letra");
			cd.setHeaderText(null);
			cd.setContentText("Eliga la opcion: ");
			
			Optional<String> resultado = cd.showAndWait();
			
			if (resultado.isPresent()) {
				String opcionElegida = resultado.get();
				char opcion = opcionElegida.charAt(0);
				agenda.personalesOrdenadosPorFechaNacimiento(opcion);
				cd.close();
				
			} else if (!resultado.isPresent()) {
				areaTexto.appendText("La letra no esta en la agenda o no hay contactos personales en ella.");
				cd.close();
			}
		}
		// a completar

	}

	private void contactosPersonalesEnLetra() {
		clear();
		// a completar
		
		
		ChoiceDialog<String> c = new ChoiceDialog("A", abecedario);

		if (!itemImportar.isDisable()) {
			areaTexto.appendText("Importa primero la agenda.");

		} else {
			c.setTitle("Selector de letra");
			c.setHeaderText(null);
			c.setContentText("Eliga la opcion: ");

			Optional<String> resultado = c.showAndWait();

			if (resultado.isPresent()) {
				String opcionElegida = resultado.get();
				char opcion = opcionElegida.charAt(0);
				areaTexto.appendText(agenda.personalesEnLetra(opcion).toString());
				c.close();
			}
			else{
				areaTexto.appendText("La letra no esta en la agenda.");
				c.close();
			}
		}
	}

	private void contactosEnLetra(char letra) {
		clear();
		// a completar
		
		ChoiceDialog<String> cd = new ChoiceDialog("A", abecedario);

		if (!itemImportar.isDisable()) {
			areaTexto.appendText("Importa primero la agenda.");

		} else {
			cd.setTitle("Selector de letra");
			cd.setHeaderText(null);
			cd.setContentText("Eliga la opcion: ");

			Optional<String> resultado = cd.showAndWait();

			if (resultado.isPresent()) {
				String opcionElegida = resultado.get();
				char opcion = opcionElegida.charAt(0);
				areaTexto.appendText(agenda.contactosEnLetra(opcion).toString());
				cd.close();
			}

			else if (!resultado.isPresent()) {
				areaTexto.appendText("La letra no esta en la agenda.");
				cd.close();
			}
		}
	}

	private void felicitar() {
		clear();
		// a completar
		itemImportar = new MenuItem();
		
		if(!itemImportar.isDisable()) {
			areaTexto.appendText("Importa primero la agenda.");
		}
		
		else {
			Date fecha = new Date();
			areaTexto.appendText("Hoy es el " + fecha.getDay() + " de " + fecha.getMonth() + " del " + fecha.getYear());
			areaTexto.appendText(agenda.felicitar().toString());
		}
		
	}

	private void buscar() {
		clear();
		// a completar

		cogerFoco();

	}

	private void about() {
		// a completar

	}

	private void clear() {
		areaTexto.setText("");
	}

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
