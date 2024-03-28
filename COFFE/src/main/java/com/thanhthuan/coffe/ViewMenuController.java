package com.thanhthuan.coffe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class ViewMenuController implements Initializable {
    @FXML
    private Button Minimize;

    @FXML
    private Button Minimize1;

    @FXML
    private ComboBox<String> Order_ProductID;

    @FXML
    private ComboBox<String> Order_ProductName;

    @FXML
    private Button Order_addBtn;

    @FXML
    private TextField Order_amount;

    @FXML
    private AnchorPane Order_form;

    @FXML
    private Button Order_payBtn;

    @FXML
    private Spinner<Integer> Order_quantity;

    @FXML
    private Button Order_receiptBtn;

    @FXML
    private Button Order_removeBtn;

    @FXML
    private TableView<product> Order_tableView;

    @FXML
    private Label Order_total;

    @FXML
    private Button anvalibleFD_AddBtn;

    @FXML
    private Button anvalibleFD_btn;

    @FXML
    private Button anvalibleFD_clearBtn;
    @FXML
    private TableView<categories> anvalibleFD_tableView;

    @FXML
    private TableColumn<categories, String> anvalibleFD_col_productID;

    @FXML
    private TableColumn<categories, String> anvalibleFD_col_productName;

    @FXML
    private TableColumn<categories, String> anvalibleFD_col_productPrice;

    @FXML
    private TableColumn<categories, String> anvalibleFD_col_productStatus;

    @FXML
    private TableColumn<categories, String> anvalibleFD_col_productType;

    @FXML
    private Button anvalibleFD_deleteBtn;

    @FXML
    private AnchorPane anvalibleFD_form;

    @FXML
    private TextField anvalibleFD_productID;

    @FXML
    private TextField anvalibleFD_productName;

    @FXML
    private TextField anvalibleFD_productPrice;

    @FXML
    private ComboBox<String> anvalibleFD_productStatus;

    @FXML
    private ComboBox<String> anvalibleFD_productType;

    @FXML
    private TextField anvalibleFD_search;

    @FXML
    private Button anvalibleFD_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Button close1;

    @FXML
    private AreaChart<?, ?> dashboard_ICChar;

    @FXML
    private Label dashboard_NC_Label_Text;

    @FXML
    private BarChart<?, ?> dashboard_NOCChar;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TIncome;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button order_btn;
    @FXML
    private Label Order_balance;

    @FXML
    private TableColumn<product, String> order_col_ID;

    @FXML
    private TableColumn<product, String> order_col_name;

    @FXML
    private TableColumn<product, String> order_col_price;

    @FXML
    private TableColumn<product, String> order_col_quantity;

    @FXML
    private TableColumn<product, String> order_col_type;

    @FXML
    private Label username;
    private Object resources;
    private Object location;

    private double x = 0;
    private double y = 0;

    private Connection connect=null;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    //DASHBOARD DATA
    public void dashboardNC(){
        String sql = "SELECT COUNT(id) FROM product_info";

        int nc = 0;
        connect = DatabaseConnection.databaseLink();
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if(result.next()){
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC_Label_Text.setText(String.valueOf(nc));
        }catch(Exception e){e.printStackTrace();}
    }
    public void dashboardTI(){

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM product_info WHERE date = '"+sqlDate+"'";

        connect = DatabaseConnection.databaseLink();
        double ti = 0;
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if(result.next()){
                ti = result.getDouble("SUM(total)");
            }
            dashboard_TI.setText("$" + String.valueOf(ti));
        }catch(Exception e){e.printStackTrace();}

}
    public void dashboardTIcome(){

        String sql = "SELECT SUM(total) FROM  product_info";
        connect = DatabaseConnection.databaseLink();
        double ti = 0;
        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if(result.next()){
                ti = result.getDouble("SUM(total)");
            }
            dashboard_TIncome.setText("$" + String.valueOf(ti));
        }catch(Exception e){e.printStackTrace();}

    }
    //Biểu đồ ORDER
    public void dashboardNOCChart(){
        try{
        dashboard_NOCChar.getData().clear();
        String sql = "SELECT date, COUNT(id) FROM product_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

        connect = DatabaseConnection.databaseLink();

            XYChart.Series chart = new XYChart.Series<>();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            dashboard_NOCChar.getData().add(chart);
        }catch(Exception e){e.printStackTrace();}
    }
    //Biểu đồ thu nhập
    public void dashboardICC(){

        dashboard_ICChar.getData().clear();

        String sql = "SELECT date, SUM(total) FROM product_info GROUP BY date ORDER BY date ASC LIMIT 7";

        connect = DatabaseConnection.databaseLink();
        try{
            XYChart.Series chart = new XYChart.Series();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));
            }
            dashboard_ICChar.getData().add(chart);
        }catch(Exception e){e.printStackTrace();}
    }
    //Catelori
    private ObservableList<categories> availableFDList;
    public ObservableList<categories> availableFDListData(){
        ObservableList<categories> ListData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM categori";

        connect = DatabaseConnection.databaseLink();
          try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            categories cat;
            while(result.next()){
                       cat = new categories(result.getString("product_id"),result.getString("product_name"),result.getString("type"),result.getDouble("price"),result.getString("status"));

                       ListData.add(cat);
            }
        }catch(Exception e){e.printStackTrace();}
        return ListData;
    }

    public void availableFDShowData() {
        availableFDList = availableFDListData();
            // Update UI logic
            anvalibleFD_col_productID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
            anvalibleFD_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
            anvalibleFD_col_productType.setCellValueFactory(new PropertyValueFactory<>("type"));
            anvalibleFD_col_productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            anvalibleFD_col_productStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            // Retrieve data from database


            // Set data to the TableView
            anvalibleFD_tableView.setItems(availableFDList);

    }
    public void availableFDAdd() {
        String sql = "INSERT INTO categori(product_id, product_name, type, price, status)" + "VALUES(?,?,?,?,?)";

        connect = DatabaseConnection.databaseLink();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, anvalibleFD_productID.getText());
            prepare.setString(2, anvalibleFD_productName.getText());
            prepare.setString(3, (String) anvalibleFD_productType.getSelectionModel().getSelectedItem());
            prepare.setString(4, anvalibleFD_productPrice.getText());
            prepare.setString(5, (String) anvalibleFD_productStatus.getSelectionModel().getSelectedItem());

            Alert alert;

            if (anvalibleFD_productID.getText().isEmpty()
                    || anvalibleFD_productName.getText().isEmpty()
                    || anvalibleFD_productType.getSelectionModel().getSelectedItem() == null
                    || anvalibleFD_productPrice.getText().isEmpty()
                    || anvalibleFD_productStatus.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền đầy đủ thông tin");
                alert.showAndWait();
            }else{

                String checkData = "SELECT product_id FROM categori WHERE product_id = '"
                        + anvalibleFD_productID.getText() + "'";
                connect = DatabaseConnection.databaseLink();

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("ID sản phẩm: " + anvalibleFD_productID.getText() + " đã tồn tại!");
                    alert.showAndWait();
                }else{
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Thêm thành công!");
                    alert.showAndWait();

                    //TO SHOW THE DATA
                    availableFDShowData();
                    //XÓA MÓN
                    availableFDClear();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   //UPDATE FOOD/DRINKS
   public void availableFDUpdate() {
       String sql = "UPDATE categori SET product_name = '"
               + anvalibleFD_productName.getText() + "', type = '"
               + anvalibleFD_productType.getSelectionModel().getSelectedItem() + "', price = '"
               + anvalibleFD_productPrice.getText() + "', status = '"
               + anvalibleFD_productStatus.getSelectionModel().getSelectedItem()
               + "' WHERE product_id = '" + anvalibleFD_productID.getText() + "'";

       connect = DatabaseConnection.databaseLink();
       try {
           Alert alert;
           if (anvalibleFD_productID.getText().isEmpty()
                   || anvalibleFD_productName.getText().isEmpty()
                   || anvalibleFD_productType.getSelectionModel().getSelectedItem() == null
                   || anvalibleFD_productPrice.getText().isEmpty()
                   || anvalibleFD_productStatus.getSelectionModel().getSelectedItem() == null) {

               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Lỗi");
               alert.setHeaderText(null);
               alert.setContentText("Vui lòng điền đầy đủ thông tin");
               alert.showAndWait();

           } else {

               alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Xác nhận");
               alert.setHeaderText(null);
               alert.setContentText("Bạn chắc chắn muốn sửa Product ID:" + anvalibleFD_productID.getText() + "?");

               Optional<ButtonType> option = alert.showAndWait();
               if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                   alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Thông báo");
                   alert.setHeaderText(null);
                   alert.setContentText("Sửa thành công! ");
                   alert.showAndWait();

                   statement = connect.createStatement();
                   statement.executeUpdate(sql);

                   // TO SHOW THE DATA
                   availableFDShowData();
                   // XÓA MÓN
                   availableFDClear();
               } else {
                   alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Thông báo");
                   alert.setHeaderText(null);
                   alert.setContentText("Không thành công! ");
                   alert.showAndWait();
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   // SEARCH DINKS AND FODD
    public void availableFDSearch(){
        FilteredList<categories> filter = new FilteredList<>(availableFDList, e-> true);

        anvalibleFD_search.textProperty().addListener((observabl, newValue, oldValue) -> {
            filter.setPredicate(predicateCategories -> {

                if(newValue.isEmpty() || newValue == null){
                    return true;
                }

                String searchKey= newValue.toLowerCase();

               if(predicateCategories.getProduct_id().toLowerCase().contains(searchKey)){
                   return true;
               }else if(predicateCategories.getName().toLowerCase().contains(searchKey)){
                   return true;
               }else if(predicateCategories.getType().toLowerCase().contains(searchKey)){
                   return true;
               }else if(predicateCategories.getPrice().toString().contains(searchKey)){
                   return true;
               }else if(predicateCategories.getStatus().toLowerCase().contains(searchKey)){
                   return true;
               }else{
                   return false;
               }
            });
        });

        SortedList<categories> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(anvalibleFD_tableView.comparatorProperty());
        anvalibleFD_tableView.setItems(sortList);
    }

    //CLEAR FOOD AND DRINKS

    public void  availableFDClear(){
        anvalibleFD_productID.setText("");
        anvalibleFD_productName.setText("");
        anvalibleFD_productType.getSelectionModel().clearSelection();
        anvalibleFD_productPrice.setText("");
        anvalibleFD_productStatus.getSelectionModel().clearSelection();

    }
    //DELETE DRINK AND FOOD
    public void availableFDDelete() {
        String productId = anvalibleFD_productID.getText().trim();

        if (productId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập ID sản phẩm");
            alert.showAndWait();
            return;
        }

        String sql = "DELETE FROM categori WHERE product_id = '" + productId + "'";
        connect = DatabaseConnection.databaseLink();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chắc chắn muốn xóa Product ID: " + productId + "?");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Xóa thành công! ");
                alert.showAndWait();

                statement = connect.createStatement();
                statement.executeUpdate(sql);

                // TO SHOW THE DATA
                availableFDShowData();
                // XÓA MÓN
                availableFDClear();
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Không thành công!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SELECT VIEWTABLE
    public void availableFDSelect() {
        categories catData = anvalibleFD_tableView.getSelectionModel().getSelectedItem();

        int num = anvalibleFD_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }
        anvalibleFD_productID.setText(catData.getProduct_id());
        anvalibleFD_productName.setText(catData.getName());
        anvalibleFD_productType.setValue(catData.getType());
        anvalibleFD_productPrice.setText(String.valueOf(catData.getPrice()));
    }

    //MEAL AND DRINKS
    private String[] categories = {"Đồ ăn ","Nước uống"};

    public void availableFDType() {
        List<String> listCat = new ArrayList<>();

        for (String data : categories) {
            listCat.add(data);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listCat);
        anvalibleFD_productType.setItems(listData);
    }

    // ORDER FOOD AND DRINK
    public void orderProductId() {
        String sql = "SELECT product_id FROM categori WHERE status ='có sẵn'";

        connect = DatabaseConnection.databaseLink();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList<String> listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_id"));
            }

            Order_ProductID.setItems(listData);
            orderProductName();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ODER ProductName
    public void orderProductName(){

        String sql = "SELECT product_name FROM categori WHERE product_id = '"
                +Order_ProductID.getSelectionModel().getSelectedItem()+"'";
        connect = DatabaseConnection.databaseLink();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while(result.next()){
                listData.add(result.getString("product_name"));
            }
            Order_ProductName.setItems(listData);

        }catch(Exception e){e.printStackTrace();}
    }
    // ORDER SPINNER
    private SpinnerValueFactory<Integer> spinner;

    public void orderSpinner(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);

        Order_quantity.setValueFactory(spinner);
    }

    // ORDER QUANTITY
    private int qty;
    public void orderQuantity(){
        qty = Order_quantity.getValue();

        System.out.println(qty);
    }
    //Chọn MÓN ĂN
    public void orderAdd() {
        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product (customer_id, product_id, product_name, type, price, quantity, date) VALUES (?,?,?,?,?,?,?)";
        connect = DatabaseConnection.databaseLink();

        try {
            String orderType = "";
            double orderPrice = 0;

            String checkData = "SELECT * FROM categori WHERE product_id = '"
                    + Order_ProductID.getSelectionModel().getSelectedItem() + "'";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);
            if (result.next()) {
                orderType = result.getString("type");
                orderPrice = result.getDouble("price");
            }

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(customerId));
            prepare.setString(2, (String) Order_ProductID.getSelectionModel().getSelectedItem());
            prepare.setString(3, (String) Order_ProductName.getSelectionModel().getSelectedItem());
            prepare.setString(4, orderType);

            double totalPrice = orderPrice * qty;

            prepare.setString(5, String.valueOf(totalPrice));
            prepare.setString(6, String.valueOf(qty));

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            prepare.setString(7, String.valueOf(sqlDate));
            prepare.executeUpdate();

            orderDisplayTotal();
            orderDisplayData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ORDER AMOUNT
    private double amount = 0;
    private double balance;
    public void orderAmount(){
        orderTotal();
        Alert alert;
        if(Order_amount.getText().isEmpty() || Order_amount.getText() == null || Order_amount.getText() == ""){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập số tiền!");
            alert.showAndWait();
        }else{
            amount = Double.parseDouble(Order_amount.getText());

            if(amount < totalP){
                Order_amount.setText("");
            }else{
                balance = (amount - totalP);
                Order_balance.setText("$"+String.valueOf(balance));
            }
        }
    }
    // ORDER PAY
    public void orderPay() {
        String sql = "INSERT INTO product_info (customer_id, total, date) VALUES (?, ?, ?)";
        connect = DatabaseConnection.databaseLink();

        try {
            Alert alert;

            if (balance < 0 || String.valueOf(balance) == "$0.0" || String.valueOf(balance) == null
                    ||totalP == 0 || String.valueOf(totalP) == "$0.0" || String.valueOf(totalP) == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập số tiền thanh toán!");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Thông Báo!");
                alert.setHeaderText(null);
                alert.setContentText("Bạn chắc chắn muốn mua nó không?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(balance));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báp!");
                    alert.setHeaderText(null);
                    alert.setContentText("Mua thành công! ");
                    alert.showAndWait();

                    Order_total.setText("$0.0");
                    Order_balance.setText("$0.0");
                    Order_amount.setText("");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Không thành công!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // REMOVEDATA
    public void orderRemove(){

        String sql = "DELETE FROM product WHERE id = " + item;
        connect = DatabaseConnection.databaseLink();
        try{
            Alert alert;
            if(item == 0 || String.valueOf(item) ==null || String.valueOf(item) == "" ){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn mục cần xóa trước!");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn xóa mục này?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Loại bỏ thành công!");
                    alert.showAndWait();

                    orderDisplayData();
                    orderDisplayTotal();

                    Order_amount.setText("");
                    Order_balance.setText("$0.0");

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Không thành công!");
                    alert.showAndWait();

                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
    //ORDER SELECTDATA
    private int item = 0;

    public void orderSelectData() {
        product prod = Order_tableView.getSelectionModel().getSelectedItem();
        int num = Order_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        item = prod.getId();
    }

    private double totalP = 0;
    public void orderTotal(){
        String sql = "SELECT SUM(price) FROM product WHERE customer_id = " + customerId;
        connect = DatabaseConnection.databaseLink();
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                totalP = result.getDouble("SUM(price)");

            }
        }catch(Exception e){e.printStackTrace();}
    }
    public void orderDisplayTotal(){
        orderTotal();
        Order_total.setText("$"+String.valueOf(totalP));

    }
    public ObservableList<product> orderListData() {
        orderCustomerId();

        ObservableList<product> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product WHERE customer_id = ?";
        connect = DatabaseConnection.databaseLink();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, customerId);
            result = prepare.executeQuery();

            product prod;
            while (result.next()) {
                prod = new product(
                        result.getInt("id"),
                        result.getString("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getInt("quantity")
                );

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return listData;
    }

    private ObservableList<product> orderData;
    public void orderDisplayData(){
        orderData = orderListData();
        order_col_ID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        order_col_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        order_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        order_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        order_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        Order_tableView.setItems(orderData);

    }
    // ORDER CUSTOMER
    private int customerId;

    public void orderCustomerId(){
       String sql = "SELECT customer_id FROM product";

       connect = DatabaseConnection.databaseLink();

       try{
         prepare = connect.prepareStatement(sql);
         result = prepare.executeQuery();

         while(result.next()){
             customerId = result.getInt("customer_id");
         }

         String checkData = "SELECT customer_id FROM product_info ";

         statement = connect.createStatement();
         result = statement.executeQuery(checkData);

         int customerInfoId = 0;
         while(result.next()){
            customerInfoId = result.getInt("customer_id");
         }
         if(customerId == 0){
             customerId += 1;
         }else if(customerId == customerInfoId){
             customerId +=1;
         }
       }catch(Exception e){e.printStackTrace();}

    }
// NOW LETS GIVE THEM BEHAVIORS

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            anvalibleFD_form.setVisible(false);
            Order_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px");
            anvalibleFD_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000");
            order_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000");

            dashboardTIcome();
            dashboardTI();
            dashboardNC();
            dashboardNOCChart();
            dashboardICC();


        } else if (event.getSource() == anvalibleFD_btn) {
            dashboard_form.setVisible(false);
            anvalibleFD_form.setVisible(true);
            Order_form.setVisible(false);

            anvalibleFD_btn.setStyle("-fx-background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px");
            dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000");
            order_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000");

            availableFDShowData();
            availableFDSearch();

        } else if (event.getSource() == order_btn) {
            dashboard_form.setVisible(false);
            anvalibleFD_form.setVisible(false);
            Order_form.setVisible(true);

            order_btn.setStyle("-fx-background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px");
            dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000");
            anvalibleFD_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000");

            orderProductId();
            orderProductName();
            orderSpinner();
            orderDisplayData();
            orderDisplayTotal();
        }
    }
// AVALIBLE FOOD/DRINKS
private String[] status = {"Có sẵn", "Không có sẵn"};

    public void availableFDStatus() {
        List<String> listStatus = new ArrayList<>();

        for (String data : status) {
            listStatus.add(data);
        }

        ObservableList<String> listData = FXCollections.observableArrayList(listStatus);
        anvalibleFD_productStatus.setItems(listData);
    }


    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận!");
            alert.setContentText("Bạn có chắc chắn muốn thoát ra?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load());

                scene.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                scene.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8f);
                });
                scene.setOnMousePressed((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardTIcome();
        dashboardTI();
        dashboardNC();
        dashboardNOCChart();
        dashboardICC();


        availableFDStatus();
        availableFDType();
        availableFDShowData();

        orderProductId();
        orderProductName();
        orderSpinner();
        orderDisplayData();
        orderDisplayTotal();


    }
}
