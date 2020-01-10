package com.cti.lifego.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.content.Groceries;
import com.cti.lifego.content.Medicals;
import com.cti.lifego.databinding.ProductDetailBinding;
import com.cti.lifego.models.Cart;
import com.cti.lifego.models.Product;
import com.cti.lifego.utils.CartHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailFragment extends BaseFragment {
    private ProductDetailBinding binding;
    private Cart cart;
    private int productID;
    private String catName;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()){
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.no_internet, container, false);
        }
        else {
            getViewOrderFragment();
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.product_detail, container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cart = CartHelper.getCart();

        navController = Navigation.findNavController(view);

        Product product = new Product();

        assert getArguments() != null;

        productID = ProductDetailFragmentArgs.fromBundle(getArguments()).getProductID();
        catName = ProductDetailFragmentArgs.fromBundle(getArguments()).getCatName();

        Log.i("CAT", catName);
        Log.i("ID", String.valueOf(productID));

        Groceries mGroceries = new Groceries();
        ArrayList<Product> grocArrayList = new ArrayList<>(Arrays.asList(mGroceries.PHARMS));

        Medicals mMeds = new Medicals();
        ArrayList<Product> medArrayList = new ArrayList<>(Arrays.asList(mMeds.MEDS));

        if (catName.equals("grocery")){
            product = grocArrayList.get(productID);
            binding.setProduct(product);
        }else if (catName.equals("pharmacy")){
            product = medArrayList.get(productID);
            binding.setProduct(product);
        }
        CircleImageView add =  binding.addToCart;
        Product finalProduct = product;
        add.setOnClickListener(v -> {
            add.setEnabled(false);
            add.setCircleBackgroundColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.lightGrey));
            //Add item to cart object
            Log.d("Product Detail", "Adding product: " + finalProduct.getName());
            cart.add(finalProduct, 1);
            Log.i("CART", cart.toString());
            getViewOrderFragment();
        });
    }

    private void getViewOrderFragment(){
        LinearLayout floating_order = binding.floatingCardHolder;
        if (!cart.isEmpty()){
            floating_order.setVisibility(View.VISIBLE);
        }
        floating_order.setOnClickListener(v -> navController.navigate(R.id.action_open_cart));
    }
}
