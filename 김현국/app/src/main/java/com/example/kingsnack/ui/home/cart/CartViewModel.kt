package com.example.kingsnack.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kingsnack.R
import com.example.kingsnack.model.Orderline
import com.example.kingsnack.model.SnackRepo
import com.example.kingsnack.model.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @Created by 김현국 2022/06/20
 * @Time 12:45 오후
 */
class CartViewModel(
    private val snackbarManager: SnackbarManager,
    snackRepository: SnackRepo
) : ViewModel() {
    private val _orderLines: MutableStateFlow<List<Orderline>> =
        MutableStateFlow(snackRepository.getCart())

    val orderLines: StateFlow<List<Orderline>> get() = _orderLines

    private var requestCount = 0
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0

    fun increaseSnackCount(snackId: Long) {
        if (!shouldRandomlyFail()) {
            val currentCount = _orderLines.value.first { it.snack.id == snackId }.count
            updateSnackCount(snackId, currentCount + 1)
        } else {
            snackbarManager.showMessage(R.string.cart_increase_error)
        }
    }

    private fun updateSnackCount(snackId: Long, count: Int) {
        _orderLines.value = _orderLines.value.map {
            if (it.snack.id == snackId) {
                it.copy(count = count)
            } else {
                it
            }
        }
    }

    fun decreaseSnackCount(snackId: Long) {
        if (!shouldRandomlyFail()) {
            val currentCount = _orderLines.value.first { it.snack.id == snackId }.count
            if (currentCount == 1)
                removeSnack(snackId)
            else
                updateSnackCount(snackId, currentCount - 1)
        } else {
            snackbarManager.showMessage(R.string.cart_decrease_error)
        }
    }
    fun removeSnack(snackId: Long) {
        _orderLines.value = _orderLines.value.filter { it.snack.id != snackId }
    }

    companion object {
        fun provideFactory(
            snackbarManager: SnackbarManager = SnackbarManager,
            snackRepository: SnackRepo = SnackRepo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CartViewModel(snackbarManager, snackRepository) as T
            }
        }
    }
}
