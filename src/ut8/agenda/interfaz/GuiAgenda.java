package ut8.agenda.interfaz;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
	
	private char[] abecedario = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' ,'I', 
					             'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q',
					             'R', 'S', 'T', 'U', 'V', 'W', 'Y', 'X', 'Z'};

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
		
		
		panel.getChildren().addAll(txtBuscar, rbtListarTodo, rbtListarSoloNumero, btnListar,
								   btnPersonalesEnLetra, btnPersonalesOrdenadosPorFecha, btnClear, btnSalir);

		return panel;
	}

	private GridPane crearPanelLetras() {
		// a completar
		GridPane panel = new GridPane();

		return panel;
	}

	private MenuBar crearBarraMenu() {
		MenuBar barra = new MenuBar();
		/*Menu menu1 = new Menu("Archivo");
		Menu menu2 = new Menu("Operaciones");
		Menu menu3 = new Menu("Help");
		
		barra.getMenus().addAll(menu1, menu2, menu3);
		
		menu1.getItems().addAll(itemImportar, itemExportarPersonales, itemSalir);
		menu2.getItems().addAll(itemBuscar, itemFelicitar);
		menu3.getItems().addAll(itemAbout);*/
		
		return barra;
	}

	private void importarAgenda() {
		// a completar

	}

	private void exportarPersonales() {
		// a completar

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
		
		// a completar

	}

	private void contactosPersonalesEnLetra() {
		clear();
		// a completar

	}

	private void contactosEnLetra(char letra) {
		clear();
		// a completar
	}

	private void felicitar() {
		clear();
		// a completar

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
