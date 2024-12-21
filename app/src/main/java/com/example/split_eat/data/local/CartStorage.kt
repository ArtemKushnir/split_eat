package com.example.split_eat.data.local

import javax.inject.Inject
import javax.inject.Singleton
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.split_eat.domain.models.CartItem

@Singleton

class CartStorage @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("cart_preferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Получаем список товаров из SharedPreferences
    private fun getSavedCartItems(): MutableList<CartItem> {
        val json = sharedPreferences.getString("cart_items", "[]")
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.fromJson(json, type)
    }

    // Сохраняем список товаров в SharedPreferences
    private fun saveCartItems(cartItems: List<CartItem>) {
        val json = gson.toJson(cartItems)
        sharedPreferences.edit().putString("cart_items", json).apply()
    }

    // Добавить товар в корзину
    fun addItem(item: CartItem) {
        val cartItems = getSavedCartItems()
        val existingItem = cartItems.find { it.id == item.id }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            cartItems.add(item.copy(quantity = 1))
        }
        saveCartItems(cartItems)
    }

    // Уменьшить количество товара на 1 или удалить
    fun decreaseItemQuantity(item: CartItem) {
        val cartItems = getSavedCartItems()
        val existingitem = cartItems.find { it.id == item.id }
        if (existingitem != null) {
            if (existingitem.quantity > 1) {
                existingitem.quantity -= 1
            } else {
                cartItems.remove(existingitem)
            }
            saveCartItems(cartItems)
        }
    }

    // Удалить товар из корзины
    fun removeItem(item: CartItem) {
        val cartItems = getSavedCartItems()
        cartItems.removeAll { it.id == item.id }
        saveCartItems(cartItems)
    }

    // Получить все товары из корзины
    fun getItems(): MutableList<CartItem> {
        return getSavedCartItems()
    }

    // Очистить корзину
    fun clearCart() {
        saveCartItems(emptyList())
    }
}
