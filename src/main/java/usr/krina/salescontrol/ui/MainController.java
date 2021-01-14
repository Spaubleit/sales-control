package usr.krina.salescontrol.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import usr.krina.salescontrol.entity.Product;
import usr.krina.salescontrol.service.ProductService;

import javax.annotation.PostConstruct;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {

    // Инъекции Spring
    @Autowired private ProductService productService;

    // Инъекции JavaFX
    @FXML private TableView<Product> productTable;
//    @FXML private TableView<Product> table;
//    @FXML private TextField txtName;
//    @FXML private TextField txtWholesalePrice;
//    @FXML private TextField txtRetailPrice;

    // Variables
    private ObservableList<Product> productData;

    @FXML
    public void initialize() {
        // Этап инициализации JavaFX
    }

    /**
     * На этом этапе уже произведены все возможные инъекции.
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        List<Product> products = productService.findAll();
        productData = FXCollections.observableArrayList(products);

        productTable.getColumns().setAll(
                new TableColumn<>("ID") {{
                    setCellValueFactory(new PropertyValueFactory<>("id"));
                }},
                new TableColumn<>("Название") {{
                    setCellValueFactory(new PropertyValueFactory<>("name"));
                }},
                new TableColumn<>("Оптовая цена") {{
                    setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
                }},
                new TableColumn<>("Розничная цена") {{
                    setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
                }}
        );

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

//    @FXML
//    public void addContact() {
//        String name = txtName.getText();
//        double phone = txtPhone.getLength();
//        double email = txtEmail.getLength();
//        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(email)) {
//            return;
//        }
//
//        Product product = new Product(name, phone, email);
//        contactService.save(product);
//        data.add(product);
//
//        // чистим поля
//        txtName.setText("");
//        txtPhone.setText("");
//        txtEmail.setText("");
//    }
}
