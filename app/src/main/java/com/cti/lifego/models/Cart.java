package com.cti.lifego.models;

import com.cti.lifego.intefaces.Saleable;
import com.cti.lifego.utils.ProductNotFoundException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable{

    private Map<Saleable, Integer>  cartItemMap = new HashMap<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private int totalQuantity = 0;

    public void add(final Product product, int quantity){
        if (cartItemMap.containsKey(product)){
            cartItemMap.put(product, cartItemMap.get(product) + quantity);
        }else {
            cartItemMap.put(product, quantity);
        }
        totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        totalQuantity+=quantity;
    }

    public void update(final Saleable saleable, int quantity) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(saleable)) throw new ProductNotFoundException();
        int productQuantity = cartItemMap.get(saleable);
        BigDecimal productPrice = saleable.getPrice().multiply(BigDecimal.valueOf(productQuantity));

        cartItemMap.put(saleable, quantity);
        totalQuantity = totalQuantity - productQuantity + quantity;
        totalPrice = totalPrice.subtract(productPrice).add(saleable.getPrice().multiply(BigDecimal.valueOf(quantity)));
    
    }
    

    public void remove(final Saleable saleable) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(saleable)) throw new ProductNotFoundException();

        int quantity = cartItemMap.get(saleable);
        cartItemMap.remove(saleable);
        totalPrice = totalPrice.subtract(saleable.getPrice().multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    /**
     * Remove all products from this shopping cart
     */
    public void clear() {
        cartItemMap.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    /**
     * Get quantity of a {@link Saleable} product in this shopping cart
     *
     * @param saleable the product of interest which this method will return the quantity
     * @return The product quantity in this shopping cart
     * @throws ProductNotFoundException if the product is not found in this shopping cart
     */
    public int getQuantity(final Saleable saleable) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(saleable)) throw new ProductNotFoundException();
        return cartItemMap.get(saleable);
    }

    /**
     * Get total cost of a {@link Saleable} product in this shopping cart
     *
     * @param product the product of interest which this method will return the total cost
     * @return Total cost of the product
     * @throws ProductNotFoundException if the product is not found in this shopping cart
     */
    public BigDecimal getCost(final Product product) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(product)) throw new ProductNotFoundException();
        return product.getPrice().multiply(BigDecimal.valueOf(cartItemMap.get(product)));
    }

    /**
     * Get total price of all products in this shopping cart
     *
     * @return Total price of all products in this shopping cart
     */
    public BigDecimal getCartTotal() {
        return totalPrice;
    }

    /**
     * Get total quantity of all products in this shopping cart
     *
     * @return Total quantity of all products in this shopping cart
     */
    public int getCartTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Get set of products in this shopping cart
     *
     * @return Set of {@link Saleable} products in this shopping cart
     */
    Set<Saleable> getProducts() {
        return cartItemMap.keySet();
    }

    /**
     * Get a map of products to their quantities in the shopping cart
     *
     * @return A map from product to its quantity in this shopping cart
     */
    public Map<Saleable, Integer> getItemWithQuantity() {
        Map<Saleable, Integer> cartItemMap = new HashMap<Saleable, Integer>();
        cartItemMap.putAll(this.cartItemMap);
        return cartItemMap;
    }

    public boolean isEmpty(){
        return !cartItemMap.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (Map.Entry<Saleable, Integer> entry : cartItemMap.entrySet()) {
            strBuilder.append(String.format("Product: %s, Unit Price: %f, Quantity: %d%n", entry.getKey().getName(), entry.getKey().getPrice(), entry.getValue()));
        }
        strBuilder.append(String.format("Total Quantity: %d, Total Price: %f", totalQuantity, totalPrice));

        return strBuilder.toString();
    }
}

