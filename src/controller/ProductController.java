package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import model.bl.ProductBl;
import model.entity.Product;
import model.entity.enums.Group;
import model.tools.Validator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j
public class ProductController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, buyPriceTxt, searchNameTxt, searchGroupTxt;
    @FXML
    private ComboBox<String> groupCmb;
    @FXML
    private Button saveBtn, editBtn, removeBtn, addBtn;
    @FXML
    private TableView<Product> productTbl;
    @FXML
    private TableColumn<Product, String> idCol, nameCol, groupCol, buyPriceCol;



    public static String save(String name, String group, int buyPrice) {
        try {
            Product product = Product
                    .builder()
                    .name(Validator.validateName(name, "Invalid Name...!"))
                    .productGroup(Group.valueOf(group))
                    .buyPrice(buyPrice)
                    .build();
            ProductBl.save(product);
            log.info("Product Saved\n" + product.toString());
            return "Info : Product Saved\n" + product.toString();
        } catch (Exception e) {
            log.error("Error Save : " + e.getMessage());
            return "Error Save : " + e.getMessage();
        }
    }

    public static String edit(int id, String name, String group, int buyPrice) {
        try {
            Product product = Product
                    .builder()
                    .id(id)
                    .name(Validator.validateName(name, "Invalid Name...!"))
                    .productGroup(Group.valueOf(group))
                    .buyPrice(buyPrice)
                    .build();
            ProductBl.edit(product);
            log.info("Product Edited\n" + product.toString());
            return "Info : Product Edited\n" + product.toString();
        } catch (Exception e) {
            log.error("Error Edit : " + e.getMessage());
            return "Error Edit : " + e.getMessage();
        }
    }

    public static String remove(int id) {
        try {
            Product product = ProductBl.remove(id);
            log.info("Product Removed\n" + product.toString());
            return "Info : Product Removed\n" + product.toString();
        } catch (Exception e) {
            log.error("Error Remove : " + e.getMessage());
            return "Error Remove : " + e.getMessage();
        }
    }

    public static List<Product> findAll() {
        try {
            log.info("FindAll");
            return ProductBl.findAll();
        } catch (Exception e) {
            log.error("Error FindAll : " + e.getMessage());
            return null;
        }
    }

    public static Product findById(int id) {
        try {
            log.info("FindById : " + id);
            return ProductBl.findById(id);
        } catch (Exception e) {
            log.error("Error FindById :" + e.getMessage());
            return null;
        }
    }

    public static List<Product> findByName(String name) {
        try {
            log.info("FindByName : " + name);
            return ProductBl.findByName(name);
        } catch (Exception e) {
            log.error("Error FindByName : " + e.getMessage());
            return null;
        }
    }

    public static List<Product> findByGroup(String group) {
        try {
            log.info("FindByGroup : " + group);
            return ProductBl.findByGroup(group);
        } catch (Exception e) {
            log.error("Error FindByGroup : " + e.getMessage());
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Group value : Group.values()) {
            groupCmb.getItems().add(value.name());
        }
        resetForm();

        addBtn.setOnAction((event) -> {
            resetForm();
        });

        saveBtn.setOnAction((event) ->{
            String message = save(
                    nameTxt.getText(),
                    groupCmb.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(buyPriceTxt.getText())
            );

            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        editBtn.setOnAction((event) ->{
            String message = edit(
                    Integer.parseInt(idTxt.getText()),
                    nameTxt.getText(),
                    groupCmb.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(buyPriceTxt.getText())
            );


            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        removeBtn.setOnAction((event) -> {
            String message = remove(Integer.parseInt(idTxt.getText()));

            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        searchNameTxt.setOnKeyReleased((event) -> {
            refreshTable(findByName(searchNameTxt.getText()));
        });

        searchGroupTxt.setOnKeyReleased((event) -> {
            refreshTable(findByName(searchGroupTxt.getText()));
        });

        productTbl.setOnMouseReleased((event) -> {
            selectProduct();
        });

        productTbl.setOnKeyReleased((event) -> {
            selectProduct();
        });



    }

    public void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        groupCmb.getSelectionModel().select(0);
        buyPriceTxt.clear();
        refreshTable(findAll());
    }
    public void refreshTable(List<Product> productList) {
        try {
            ObservableList<Product> observableList = FXCollections.observableList(productList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            groupCol.setCellValueFactory(new PropertyValueFactory<>("productGroup"));
            buyPriceCol.setCellValueFactory(new PropertyValueFactory<>("buyPrice"));

            productTbl.setItems(observableList);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public void selectProduct() {
        Product product = productTbl.getSelectionModel().getSelectedItem();
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        groupCmb.getSelectionModel().select(product.getProductGroup().name());
        buyPriceTxt.setText(String.valueOf(product.getBuyPrice()));

    }
}
