package usr.krina.salescontrol.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import usr.krina.salescontrol.EditState;
import usr.krina.salescontrol.TAB;
import usr.krina.salescontrol.entity.Contractor;
import usr.krina.salescontrol.entity.Product;
import usr.krina.salescontrol.entity.Retail;
import usr.krina.salescontrol.entity.Wholesale;
import usr.krina.salescontrol.service.EntityService;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.*;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {

    // Инъекции Spring
    @Autowired private EntityService<Product> productService;
    @Autowired private EntityService<Contractor> contractorService;
    @Autowired private EntityService<Wholesale> wholesaleService;
    @Autowired private EntityService<Retail> retailService;

    // Инъекции JavaFX
    @FXML private TabPane tabs;
    @FXML private TableView<Product> productTable;
    @FXML private VBox productBox;
    @FXML private TextField productName;
    @FXML private TextField productWholesalePrice;
    @FXML private TextField productRetailPrice;

    @FXML private TableView<Contractor> contractorTable;
    @FXML private VBox contractorBox;
    @FXML private TextField contractorName;

    @FXML private TableView<Wholesale> wholesaleTable;
    @FXML private VBox wholesaleBox;
    @FXML private DatePicker wholesaleDate;
    @FXML private TextField wholesaleCount;
    @FXML private ComboBox<Product> wholesaleProduct;
    @FXML private ComboBox<Contractor> wholesaleContractor;

    @FXML private TableView<Retail> retailTable;
    @FXML private VBox retailBox;
    @FXML private DatePicker retailDate;
    @FXML private TextField retailCount;
    @FXML private ComboBox<Product> retailProduct;

    @FXML private StackPane editStack;
    @FXML private TextField searchField;

    // Variables
    private ObservableList<Product> productData;
    private ObservableList<Contractor> contractorsData;
    private ObservableList<Wholesale> wholesaleData;
    private ObservableList<Retail> retailData;
    private final EditState state = new EditState(this::stateReaction);
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
        productData = FXCollections.observableArrayList(productService.findAll());
        contractorsData = FXCollections.observableArrayList(contractorService.findAll());
        wholesaleData = FXCollections.observableArrayList(wholesaleService.findAll());
        retailData = FXCollections.observableArrayList(retailService.findAll());

        var filteredProducts = new FilteredList<>(productData, p -> true);
        var filteredContractors = new FilteredList<>(contractorsData, p -> true);
        var filteredWholesales = new FilteredList<>(wholesaleData, p -> true);
        var filteredRetail = new FilteredList<>(retailData, p -> true);

        searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var search = newValue.toLowerCase();

            filteredProducts.setPredicate(product ->
                    product.getName().toLowerCase().contains(search) ||
                    Double.toString(product.getRetailPrice()).contains(search)
            );
            filteredContractors.setPredicate(contractor -> contractor.getName().toLowerCase().contains(search));
            filteredWholesales.setPredicate(wholesale ->
                    wholesale.getDate().toString().contains(search) ||
                    Integer.toString(wholesale.getCount()).contains(search) ||
                    wholesale.getProduct().toString().toLowerCase().contains(search) ||
                    wholesale.getContractor().toString().toLowerCase().contains(search)
            );
            filteredRetail.setPredicate(retail ->
                    retail.getDate().toString().contains(search) ||
                    Integer.toString(retail.getCount()).contains(search) ||
                    retail.getProduct().toString().toLowerCase().contains(search)
            );
        });

        setColumns(productTable, new TreeMap<>(Map.ofEntries(
                Map.entry("id", "id"),
                Map.entry("name", "Название"),
                Map.entry("wholesalePrice", "Оптовая цена"),
                Map.entry("retailPrice", "Розничная цена")
        )));
        var sortedProducts = new SortedList<>(filteredProducts);
        sortedProducts.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedProducts);
        setColumns(contractorTable, new TreeMap<>(Map.ofEntries(
                Map.entry("id", "id"),
                Map.entry("name", "Название")
        )));
        var sortedContractors = new SortedList<>(filteredContractors);
        sortedContractors.comparatorProperty().bind(contractorTable.comparatorProperty());
        contractorTable.setItems(sortedContractors);
        setColumns(wholesaleTable, new TreeMap<>(Map.ofEntries(
                Map.entry("id", "id"),
                Map.entry("date", "Дата"),
                Map.entry("count", "Количество"),
                Map.entry("product", "Товар"),
                Map.entry("contractor", "Контрагент")
        )));
        var sortedWholesales = new SortedList<>(filteredWholesales);
        sortedWholesales.comparatorProperty().bind(wholesaleTable.comparatorProperty());
        wholesaleTable.setItems(sortedWholesales);
        setColumns(retailTable, new TreeMap<>(Map.ofEntries(
                Map.entry("id", "id"),
                Map.entry("date", "Дата"),
                Map.entry("count", "Количество"),
                Map.entry("product", "Товар")
        )));
        var sortedRetails = new SortedList<>(filteredRetail);
        sortedRetails.comparatorProperty().bind(retailTable.comparatorProperty());
        retailTable.setItems(sortedRetails);

        wholesaleProduct.setItems(productData);
        wholesaleContractor.setItems(contractorsData);
        retailProduct.setItems(productData);
    }

    static private <T> void setColumns(TableView<T> table, Map<String, String> columns) {
        columns.forEach((field, name) -> table.getColumns().add(new TableColumn<>(name) {{
            setCellValueFactory(new PropertyValueFactory<>(field));
        }}));
    }

    public void stateReaction(EditState state) {
        switch (state.getMode()) {
            case UPDATE:
            case CREATE:
                editStack.setDisable(false);
                state.getProduct().ifPresent(product -> {
                    productName.requestFocus();
                    productName.setText(product.getName());
                    productWholesalePrice.setText(Double.toString(product.getWholesalePrice()));
                    productRetailPrice.setText(Double.toString(product.getRetailPrice()));
                });
                state.getContractor().ifPresent(contractor -> {
                    contractorName.requestFocus();
                    contractorName.setText(contractor.getName());
                });
                state.getWholesale().ifPresent(wholesale -> {
                    wholesaleDate.requestFocus();
                    wholesaleDate.setValue(Optional.ofNullable(wholesale.getDate())
                            .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                            .orElse(null)
                    );
                    wholesaleCount.setText(Integer.toString(wholesale.getCount()));
                    wholesaleProduct.setValue(wholesale.getProduct());
                    wholesaleContractor.setValue(wholesale.getContractor());
                });
                state.getRetail().ifPresent(retail -> {
                    retailDate.requestFocus();
                    retailDate.setValue(Optional.ofNullable(retail.getDate())
                            .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                            .orElse(null)
                    );
                    retailCount.setText(Integer.toString(retail.getCount()));
                    retailProduct.setValue(retail.getProduct());
                });
                break;
            case DELETE:
                new Alert(Alert.AlertType.CONFIRMATION).showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    state.getProduct().ifPresent(product -> {
                        productService.delete(product);
                        productData.remove(product);
                    });
                    state.getContractor().ifPresent(contractor -> {
                        contractorService.delete(contractor);
                        contractorsData.remove(contractor);
                    });
                    state.getWholesale().ifPresent(wholesale -> {
                        wholesaleService.delete(wholesale);
                        wholesaleData.remove(wholesale);
                    });
                    state.getRetail().ifPresent(retail -> {
                        retailService.delete(retail);
                        retailData.remove(retail);
                    });
                });
                editStack.setDisable(true);
                break;
            case NONE:
                editStack.setDisable(true);
                break;
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
                state.create(new Product());
                break;
            case CONTRACTOR:
                state.create(new Contractor());
                break;
            case WHOLESALE:
                state.create(new Wholesale());
                break;
            case RETAIL:
                state.create(new Retail());
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
        TableView table = null;
        switch (tab) {
            case PRODUCT:
                table = productTable;
                break;
            case CONTRACTOR:
                table = contractorTable;
                break;
            case WHOLESALE:
                table = wholesaleTable;
                break;
            case RETAIL:
                table = retailTable;
                break;
        }
        Optional.ofNullable(table)
                .flatMap(innerTable -> Optional.ofNullable(innerTable.getSelectionModel().getSelectedItem()))
                .ifPresent(state::delete);
    }

    @FXML
    public void clear() {
        state.clear();
    }

    @FXML
    public void productApply() {
        var newProduct = new Product(
                productName.getText(),
                Double.parseDouble(productWholesalePrice.getText()),
                Double.parseDouble(productRetailPrice.getText())
        );
        switch (state.getMode()) {
            case CREATE:
                productData.add(productService.save(newProduct));
                break;
            case UPDATE:
                state.getProduct().ifPresent(product -> Collections.replaceAll(
                        productData,
                        product,
                        productService.save(product.update(newProduct))
                ));
                break;
        }
        clear();
    }

    @FXML
    public void contractorApply() {
        var newContractor = new Contractor(contractorName.getText());
        switch (state.getMode()) {
            case CREATE:
                contractorsData.add(contractorService.save(newContractor));
                break;
            case UPDATE:
                state.getContractor().ifPresent(contractor -> Collections.replaceAll(
                        contractorsData,
                        contractor,
                        contractorService.save(contractor.update(newContractor))
                ));
                break;
        }
        clear();
    }

    @FXML
    public void wholesaleApply() {
        var newWholesale = new Wholesale(
                Integer.parseInt(wholesaleCount.getText()),
                Date.from(wholesaleDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                wholesaleProduct.getValue(),
                wholesaleContractor.getValue()
        );
        switch (state.getMode()) {
            case CREATE:
                wholesaleData.add(wholesaleService.save(newWholesale));
                break;
            case UPDATE:
                state.getWholesale().ifPresent(wholesale -> Collections.replaceAll(
                        wholesaleData,
                        wholesale,
                        wholesaleService.save(wholesale.update(newWholesale))
                ));
        }
        clear();
    }

    @FXML
    public void retailApply() {
        var newRetail = new Retail(
                Integer.parseInt(retailCount.getText()),
                Date.from(retailDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                retailProduct.getValue()
        );
        switch (state.getMode()) {
            case CREATE:
                retailData.add(retailService.save(newRetail));
                break;
            case UPDATE:
                state.getRetail().ifPresent(retail -> Collections.replaceAll(
                        retailData,
                        retail,
                        retailService.save(retail.update(newRetail))
                ));
        }
        clear();
    }

    @FXML
    public void clearSearch() {
        searchField.setText("");
    }
}
