package model.bl;

import controller.exceptions.NoProductFoundException;
import model.da.ProductDa;
import model.entity.Product;

import java.util.List;

public class ProductBl {
    public static Product save(Product product) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            productDa.save(product);
            return product;
        }
    }

    public static Product edit(Product product) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            if (productDa.findById(product.getId()) != null) {
                productDa.edit(product);
                return product;
            }else{
                throw new NoProductFoundException();
            }
        }
    }

    public static Product remove(int id) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            Product product = productDa.findById(id);
            if (product != null) {
                productDa.remove(id);
                return product;
            }else{
                throw new NoProductFoundException();
            }
        }
    }

    public static List<Product> findAll() throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            List<Product> productList = productDa.findAll();
            if (!productList.isEmpty()) {
                return productList;
            } else {
                throw new NoProductFoundException();
            }
        }
    }

    public static Product findById(int id) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            Product product = productDa.findById(id);
            if (product != null) {
                return product;
            }else{
                throw new NoProductFoundException();
            }
        }
    }
    public static List<Product> findByName(String name) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            List<Product> productList = productDa.findByName(name);
            if (!productList.isEmpty()) {
                return productList;
            } else {
                throw new NoProductFoundException();
            }
        }
    }

    public static List<Product> findByGroup(String group) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            List<Product> productList = productDa.findByGroup(group);
            if (!productList.isEmpty()) {
                return productList;
            } else {
                throw new NoProductFoundException();
            }
        }
    }


}
