package com.example.split_eat.data.local

import javax.inject.Inject
import javax.inject.Singleton
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.split_eat.domain.models.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DifferentRestaurantException(message: String) : Exception(message)


@Singleton
class CartStorage @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("cart_preferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val _cartItemsFlow = MutableStateFlow(getSavedCartItems())
    val cartItemsFlow: StateFlow<List<CartItem>> = _cartItemsFlow

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == "cart_items") {
            _cartItemsFlow.value = getSavedCartItems()
        }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    private fun getSavedCartItems(): List<CartItem> {
        val json = sharedPreferences.getString("cart_items", "[]")
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveCartItems(cartItems: List<CartItem>) {
        val json = gson.toJson(cartItems)
        sharedPreferences.edit().putString("cart_items", json).apply()
        _cartItemsFlow.value = cartItems
    }

    fun addItem(item: CartItem) {
        val cartItems = getSavedCartItems().toMutableList()
        if (cartItems.isNotEmpty() && item.restaurant != cartItems[0].restaurant) {
            throw DifferentRestaurantException("Нельзя добавить товары из разных ресторанов")
        } else {
            val existingItem = cartItems.find { it.id_product == item.id_product }
            if (existingItem != null) {
                existingItem.quantity += 1
            } else {
                cartItems.add(item.copy(quantity = 1))
            }
            saveCartItems(cartItems)
        }
    }

    fun decreaseItemQuantity(item: CartItem) {
        val cartItems = getSavedCartItems().toMutableList()
        val existingItem = cartItems.find { it.name == item.name && it.restaurant == item.restaurant }
        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                existingItem.quantity -= 1
            } else {
                cartItems.remove(existingItem)
            }
            saveCartItems(cartItems)
        }
    }

    fun removeItem(item: CartItem) {
        val cartItems = getSavedCartItems().toMutableList()
        cartItems.removeAll { it.name == item.name && it.restaurant == item.restaurant }
        saveCartItems(cartItems)
    }

    fun getItems(): List<CartItem> {
        return getSavedCartItems()
    }

    fun clearCart() {
        saveCartItems(emptyList())
    }
}
