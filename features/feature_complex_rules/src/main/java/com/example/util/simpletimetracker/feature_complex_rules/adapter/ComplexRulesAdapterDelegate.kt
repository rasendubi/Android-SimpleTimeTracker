package com.example.util.simpletimetracker.feature_complex_rules.adapter

import android.widget.TextView
import androidx.annotation.ColorInt
import com.example.util.simpletimetracker.feature_base_adapter.ViewHolderType
import com.example.util.simpletimetracker.feature_base_adapter.createRecyclerBindingAdapterDelegate
import com.example.util.simpletimetracker.feature_views.ColorUtils
import com.example.util.simpletimetracker.feature_views.extension.setOnClickWith
import com.example.util.simpletimetracker.feature_views.extension.visible
import com.example.util.simpletimetracker.feature_complex_rules.adapter.ComplexRuleViewData as ViewData
import com.example.util.simpletimetracker.feature_complex_rules.databinding.ItemComplexRuleLayoutBinding as Binding

fun createComplexRuleAdapterDelegate(
    onItemClick: ((ViewData) -> Unit),
) = createRecyclerBindingAdapterDelegate<ViewData, Binding>(
    Binding::inflate,
) { binding, item, _ ->

    with(binding) {
        item as ViewData

        fun TextView.setData(data: String) {
            if (data.isNotEmpty()) {
                visible = true
                text = data
            } else {
                visible = false
            }
        }

        // TODO RULES move text to res from layout
        containerComplexRuleItem.setCardBackgroundColor(item.color)
        viewComplexRuleItemDivider.setBackgroundColor(ColorUtils.normalizeLightness(item.color))
        tvComplexRuleItemAction.text = item.actionTitle
        tvComplexRuleItemStartingTypes.setData(item.startingTypes)
        tvComplexRuleItemCurrentTypes.setData(item.currentTypes)
        tvComplexRuleItemDayOfWeek.setData(item.daysOfWeek)

        containerComplexRuleItem.setOnClickWith(item, onItemClick)
    }
}

data class ComplexRuleViewData(
    val id: Long,
    val actionTitle: String,
    val startingTypes: String,
    val currentTypes: String,
    val daysOfWeek: String,
    @ColorInt val color: Int,
) : ViewHolderType {

    override fun getUniqueId(): Long = id

    override fun isValidType(other: ViewHolderType): Boolean = other is ViewData
}