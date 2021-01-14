package usr.krina.salescontrol.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import usr.krina.salescontrol.entity.Contractor;
import usr.krina.salescontrol.entity.Product;
import usr.krina.salescontrol.service.ProductService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {

    // Инъекции Spring
    @Autowired private ProductService productService;

    // Инъекции JavaFX
    @FXML private TableView<Product> productTable;
    @FXML private TableView<Contractor> contractorTable;

    // Variables
    private ObservableList<Product> productData;
    private ObservableList<Contractor> contractorsData;

    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
    }

    static private <T> void setColumns(TableView<T> table, Map<String, String> columns) {
        columns.forEach((field, name) -> table.getColumns().add(new TableColumn<>(name) {{
            setCellValueFactory(new PropertyValueFactory<>(field));
        }}));
    }

    /**
     * На этом этапе уже произведены все возможные инъекции.
     */
    @PostConstruct
    public void init() {
        List<Product> products = productService.findAll();
        productData = FXCollections.observableArrayList(products);

        setColumns(productTable, Map.ofEntries(
                Map.entry("id", "id"),
                Map.entry("name", "Название"),
                Map.entry("wholesalePrice", "Оптовая цена"),
                Map.entry("retailPrice", "Розничная цена")
        ));

        productTable.setItems(productData);
    }

    @FXML
    public void startAdd() {

    }

    @FXML
    public void startUpdate() {

    }

    @FXML
    public void startDelete() {

    }
}
