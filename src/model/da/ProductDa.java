package model.da;
import model.entity.Product;
import model.entity.enums.Group;
import model.tools.JDBC;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDa implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ProductDa() throws SQLException {
        connection = JDBC.getJdbc().getConnection();
    }

    public void save(Product product) throws Exception {
        preparedStatement = connection.prepareStatement(
                "INSERT INTO PRODUCT (ID, NAME, PRODUCT_GROUP, BUY_PRICE) VALUES (PRODUCT_SEQ.nextval,?,?,?)"
        );
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getProductGroup().name());
        preparedStatement.setInt(3, product.getBuyPrice());
        preparedStatement.execute();
    }

    public void edit(Product product) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE PRODUCT SET NAME=?, PRODUCT_GROUP=?, BUY_PRICE=? WHERE ID=?"
        );
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getProductGroup().name());
        preparedStatement.setInt(3, product.getBuyPrice());
        preparedStatement.setInt(4, product.getId());
        preparedStatement.execute();
    }


    public void remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM PRODUCT WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public List<Product> findAll() throws Exception{
        preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT ORDER BY ID, NAME");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            Product product = Product
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .productGroup(Group.valueOf(resultSet.getString("PRODUCT_GROUP")))
                    .buyPrice(resultSet.getInt("BUY_PRICE"))
                    .build();
            productList.add(product);
        }
        return productList;
    }

    public Product findById(int id) throws Exception{
        preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE ID=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Product product = null;
        if (resultSet.next()) {
            product = Product
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .productGroup(Group.valueOf(resultSet.getString("PRODUCT_GROUP")))
                    .buyPrice(resultSet.getInt("BUY_PRICE"))
                    .build();
        }
        return product;
    }

    public List<Product> findByName(String name) throws Exception{
        preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE NAME LIKE ?");
        preparedStatement.setString(1, name+"%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            Product product = Product
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .productGroup(Group.valueOf(resultSet.getString("PRODUCT_GROUP")))
                    .buyPrice(resultSet.getInt("BUY_PRICE"))
                    .build();
            productList.add(product);
        }
        return productList;
    }

    public List<Product> findByGroup(String group) throws Exception{
        preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT WHERE PRODUCT_GROUP LIKE ?");
        preparedStatement.setString(1, group+"%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            Product product = Product
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .productGroup(Group.valueOf(resultSet.getString("PRODUCT_GROUP")))
                    .buyPrice(resultSet.getInt("BUY_PRICE"))
                    .build();
            productList.add(product);
        }
        return productList;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
