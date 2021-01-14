package usr.krina.salescontrol.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import usr.krina.salescontrol.EDIT_MODE;
import usr.krina.salescontrol.EditState;
import usr.krina.salescontrol.TAB;
import usr.krina.salescontrol.entity.Contractor;
import usr.krina.salescontrol.entity.Product;
import usr.krina.salescontrol.entity.Retail;
import usr.krina.salescontrol.entity.Wholesale;
import usr.krina.salescontrol.service.ProductService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {

    // Инъекции Spring
    @Autowired private ProductService productService;

    // Инъекции JavaFX
    @FXML private TabPane tabs;
    @FXML private TableView<Product> productTable;
    @FXML private VBox productBox;
    @FXML private TextField productName;
    @FXML private TextField productWholesalePrice;
    @FXML private TextField productRetailPrice;

    @FXML private TableView<Contractor> contractorTable;
    @FXML private VBox contractorBox;

    @FXML private TableView<Wholesale> wholesaleTable;
    @FXML private VBox wholesaleBox;

    @FXML private TableView<Retail> retailTable;
    @FXML private VBox retailBox;

    @FXML private StackPane editStack;


    // Variables
    private ObservableList<Product> productData;
    private ObservableList<Contractor> contractorsData;
    private EditState state = new EditState(this::stateReaction);
    private TAB tab = TAB.PRODUCT;

    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
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

    static private <T> void setColumns(TableView<T> table, Map<String, String> columns) {
        columns.forEach((field, name) -> table.getColumns().add(new TableColumn<>(name) {{
            setCellValueFactory(new PropertyValueFactory<>(field));
        }}));
    }

    public void stateReaction(EditState state) {
        if (state.isInChangeState()) {
            editStack.setDisable(false);
            state.getProduct().ifPresent(product -> {
                productName.requestFocus();
                productName.setText(product.getName());
                productWholesalePrice.setText(Double.toString(product.getWholesalePrice()));
                productRetailPrice.setText(Double.toString(product.getRetailPrice()));
            });
        } else {
            editStack.setDisable(true);
        }
    }

    @FXML
    public void tabChange() {
        if (productBox != null && contractorBox != null && wholesaleBox != null && retailBox != null) {
            editStack.getChildren().forEach(child -> child.setVisible(false));
            switch (tabs.getSelectionModel().getSelectedIndex()) {
                case 0:
                    tab = TAB.PRODUCT;
                    productBox.setVisible(true);
                    break;
                case 1:
                    tab = TAB.CONTRACTOR;
                    contractorBox.setVisible(true);
                    break;
                case 2:
                    tab = TAB.WHOLESALE;
                    wholesaleBox.setVisible(true);
                    break;
                case 3:
                    tab = TAB.RETAIL;
                    retailBox.setVisible(true);
                    break;
            }
        }
    }

    public Optional<TableView> getCurrentTable() {
        switch (tab) {
            case PRODUCT:
                return Optional.of(productTable);
            case CONTRACTOR:
                return Optional.of(contractorTable);
            case WHOLESALE:
                return Optional.of(wholesaleTable);
            case RETAIL:
                return Optional.of(retailTable);
            default:
                return Optional.empty();
        }
    }

    @FXML
    public void startAdd() {
        switch (tab) {
            case PRODUCT:
                state.update(new Product());
                break;
            case CONTRACTOR:
                state.update(new Contractor());
                break;
            case WHOLESALE:
                state.update(new Wholesale());
                break;
            case RETAIL:
                state.update(new Retail());
                break;
        }
    }

    @FXML
    public void startUpdate() {
        getCurrentTable()
                .flatMap(tableView -> Optional.ofNullable(tableView.getSelectionModel().getSelectedItem()))
                .ifPresent(state::update);
    }

    @FXML
    public void startDelete() {
    }

    @FXML
    public void clear() {
        state.clear();
    }

    @FXML
    public void productCreate() {
        var product = new Product(
                productName.getText(),
                Double.parseDouble(productWholesalePrice.getText()),
                Double.parseDouble(productRetailPrice.getText())
        );
        System.out.println(product);
        productData.add(productService.save(product));
        clear();
    }
}
