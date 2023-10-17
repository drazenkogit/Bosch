package com.bosch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bosch.R
import com.bosch.databinding.CountryListItemBinding
import com.bosch.domain.models.Country
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CountryAdapter @Inject constructor() : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private var list: MutableList<Country> = mutableListOf()
    private var itemDetailsListener: ((Country) -> Unit)? = null
    private var itemPinListener: ((Country) -> Unit)? = null

    fun setItemDetailsListener(listener: (Country) -> Unit) {
        this.itemDetailsListener = listener
    }

    fun setItemPinListener(listener: (Country) -> Unit) {
        this.itemPinListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CountryListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.country_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position, itemDetailsListener, itemPinListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding: CountryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var binding: CountryListItemBinding? = null

        init {
            this.binding = binding
        }

        fun bind(
            country: Country,
            position: Int,
            itemDetailsListener: ((Country) -> Unit)?,
            itemPinListener: ((Country) -> Unit)?
        ) {
            binding?.let { itemBinding ->
                itemBinding.position = position
                itemBinding.countryListItemName.text = country.name
                itemBinding.countryListItemCapital.text = "(${country.capital})"
                itemBinding.buttonDetails.setOnClickListener {
                    itemDetailsListener?.invoke(country)
                }
                itemBinding.buttonPin.setImageResource(if (country.pinned) R.drawable.baseline_push_pin_24 else R.drawable.baseline_push_pin_24_dimmed)
                itemBinding.buttonPin.setOnClickListener {
                    itemPinListener?.invoke(country)
                }
            }
        }
    }

    fun updateList(countries: List<Country>) {
        list.clear()
        list.addAll(countries)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}