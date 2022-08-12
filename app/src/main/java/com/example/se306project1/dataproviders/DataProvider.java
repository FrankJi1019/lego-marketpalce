package com.example.se306project1.dataproviders;

import com.example.se306project1.R;
import com.example.se306project1.models.CartProduct;
import com.example.se306project1.models.Category;
import com.example.se306project1.models.Category1;
import com.example.se306project1.models.Category2;
import com.example.se306project1.models.Category3;
import com.example.se306project1.models.ICategory;
import com.example.se306project1.models.IProduct;
import com.example.se306project1.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static ArrayList<IProduct> getIProductList(int size) {
        ArrayList<IProduct> res = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            IProduct product = new Product();
            product.setId(i + 1);
            product.setCategoryId(1);
            product.setName("Colosseum");
            product.setDescription("Build and discover the Taj Mahal! The huge ivory-white marble mausoleum, renowned as one of the world’s architectural wonders, was commissioned in 1631 by the Emperor Shah Jahan in memory of his wife, the Empress Mumtaz Mahal. This relaunched 2008 LEGO® Creator Expert interpretation features the structure's 4 facades with sweeping arches, balconies and arched windows. The central dome, subsidiary domed chambers and surrounding minarets are topped with decorative finials, and the raised platform is lined with recessed arches.");
            product.setPrice(199.90);
            product.setStock(20);
            List<String> images = new ArrayList<>();
            images.add("image_placeholder.png");
            product.setImages(images);
            res.add(product);
        }
        return res;
    }

    public static IProduct getIProduct() {
        IProduct product = new Product();
        product.setId(1);
        product.setCategoryId(1);
        product.setName("Colosseum");
        product.setDescription("Build and discover the Taj Mahal! The huge ivory-white marble mausoleum, renowned as one of the world’s architectural wonders, was commissioned in 1631 by the Emperor Shah Jahan in memory of his wife, the Empress Mumtaz Mahal. This relaunched 2008 LEGO® Creator Expert interpretation features the structure's 4 facades with sweeping arches, balconies and arched windows. The central dome, subsidiary domed chambers and surrounding minarets are topped with decorative finials, and the raised platform is lined with recessed arches.");
        product.setPrice(199.90);
        product.setStock(20);
        List<String> images = new ArrayList<>();
        images.add("image_placeholder.png");
        product.setImages(images);
        return product;
    }

    public static ArrayList<ICategory> getICategoryList() {
        ArrayList<ICategory> res = new ArrayList<>();
        Category category1 = new Category1();
        Category category2 = new Category2();
        Category category3 = new Category3();
        category1.setId(1);
        category2.setId(2);
        category3.setId(3);
        category1.setTitle("Technic");
        category2.setTitle("Star War");
        category3.setTitle("Friends");
        category1.setImage("image_placeholder.png");
        category1.setImage("image_placeholder.png");
        category1.setImage("image_placeholder.png");
        res.add(category1);
        res.add(category2);
        res.add(category3);
        return res;
    }

    public static List<Integer> getImageList(int size) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            res.add(R.drawable.image_placeholder);
        }
        return res;
    }

    public static List<CartProduct> getCartProductList(int size) {
        List<CartProduct> res = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setId(1);
            cartProduct.setCategoryId(1);
            cartProduct.setName("Colosseum");
            cartProduct.setDescription("Build and discover the Taj Mahal! The huge ivory-white marble mausoleum, renowned as one of the world’s architectural wonders, was commissioned in 1631 by the Emperor Shah Jahan in memory of his wife, the Empress Mumtaz Mahal. This relaunched 2008 LEGO® Creator Expert interpretation features the structure's 4 facades with sweeping arches, balconies and arched windows. The central dome, subsidiary domed chambers and surrounding minarets are topped with decorative finials, and the raised platform is lined with recessed arches.");
            cartProduct.setPrice(199.90);
            cartProduct.setStock(10);
            List<String> images = new ArrayList<>();
            images.add("image_placeholder.png");
            cartProduct.setImages(images);
            cartProduct.setAmount(1);
            res.add(cartProduct);
        }
        return res;
    }

}
