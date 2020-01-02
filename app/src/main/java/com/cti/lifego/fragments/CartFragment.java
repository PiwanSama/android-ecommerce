package com.cti.lifego.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.CartAdapter;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.CartFragmentBinding;
import com.cti.lifego.intefaces.ICartFragment;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.models.Product;
import com.cti.lifego.viewmodels.CartViewModel;
import com.cti.lifego.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartFragment extends Fragment implements ICartFragment {

    private Context mContext;
    private CartFragmentBinding binding;
    private CartViewModel cartViewModel;
    private ProductViewModel productViewModel;
    private List<CartItem> cartItems;
    private RecyclerView cartRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.cart_fragment, container, false);

        showBar();

        cartRecyclerView = binding.cartRecyclerView;
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getShoppingCartList();
        if (cartItems.size()!=0){
            CartAdapter cartAdapter = new CartAdapter(cartItems);
            cartRecyclerView.setAdapter(cartAdapter);
            showCart();
        }
        else {
            cartEmpty();
        }
    }

    private void getShoppingCartList() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> ids = preferences.getStringSet(PreferenceKeys.shopping_cart_ids, new HashSet<>());
        //In this case I will populate the cartItemsList with Cart Items created using the Product IDS stores
        cartItems = new ArrayList<>();
        if (ids!=null) {
            for (String id : ids) {
                //Fetch the product associated with each product id from the database using productViewModel.getProduct(id).getValue();
                //Fetch the product quantity from shared preferences
                int quantity = preferences.getInt(id, 0);
                //Create a new cart item from that product id.
                cartItems.add(new CartItem(productViewModel.getProduct(id), quantity));
            }
        }
        cartViewModel.setCartList(cartItems);
        binding.setCartView(cartViewModel);
    }

    @Override
    public void updateQuantity(Product product, int quantity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();

        //add the quantity
        int currentQuantity = preferences.getInt(String.valueOf(product.getId()), 0);

        //commit the updated quantity
        editor.putInt(String.valueOf(product.getId()), (currentQuantity + quantity));
        editor.commit();
        getShoppingCartList();
    }

    @Override
    public void deleteItem(CartItem cartItem) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(String.valueOf(cartItem.getProduct().getValue().getId()));
        editor.commit();

        Set<String> ids  = preferences.getStringSet(PreferenceKeys.shopping_cart_ids, new HashSet<String>());
        assert ids != null;
        if (ids.size() == 1){
            editor.remove(PreferenceKeys.shopping_cart_ids);
            editor.commit();
        }
        else {
            ids.remove(String.valueOf(cartItem.getProduct().getValue().getId()));
            editor.putStringSet(PreferenceKeys.shopping_cart_ids, ids);
            editor.commit();
        }

        getShoppingCartList();
    }

    @Override
    public void emptyCart() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Set<String> serialNumbers = preferences.getStringSet(PreferenceKeys.shopping_cart_ids, new HashSet<String>());
        SharedPreferences.Editor editor = preferences.edit();

        for(String serialNumber : serialNumbers){
            editor.remove(serialNumber);
            editor.commit();
        }

        editor.remove(PreferenceKeys.shopping_cart_ids);
        editor.commit();

        getShoppingCartList();
        cartEmpty();
    }

    @Override
    public void checkout() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showBar(){
        binding.contentLoading.show();
    }

    private void cartEmpty(){
        binding.contentLoading.hide();
        binding.cartEmptyView.setVisibility(View.VISIBLE);
    }

    private void showCart(){
        binding.contentLoading.hide();
        binding.populatedCartView.setVisibility(View.VISIBLE);
    }

    //TODO - Selected Store

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

}

