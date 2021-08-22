package com.example.pokemonapp.ui.pokemondetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.data.model.Stats
import com.example.pokemonapp.databinding.PokemonStatisticsItemBinding
import com.example.pokemonapp.utils.Constants.MAX_BASE_STATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StatisticsAdapter :
    RecyclerView.Adapter<StatisticsAdapter.CartViewHolder>() {

    private val stats = ArrayList<Stats>()

    @SuppressLint("NotifyDataSetChanged")
    fun setStats(newList: ArrayList<Stats>) {
        stats.clear()
        stats.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemBinding =
            PokemonStatisticsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(stats[position])
    }

    override fun getItemCount(): Int {
        return stats.size
    }

    inner class CartViewHolder(private val binding: PokemonStatisticsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stat: Stats) {
            binding.apply {
                val mProgress = progressCircular
                mProgress.secondaryProgress = MAX_BASE_STATE
                mProgress.max = MAX_BASE_STATE

                CoroutineScope(Dispatchers.Main).launch {
                    var state = 0
                    while (state <= stat.base_stat) {
                        mProgress.progress = state
                        state++
                        delay(10)
                    }
                }
                statName.text = stat.stat.name.uppercase()
                if (stat.stat.name.contains("-")) {
                    val first = stat.stat.name.substringBefore("-").uppercase()
                    val second = stat.stat.name.substringAfter("-").uppercase()
                    "$first - $second".also { statName.text = it }
                }
                statCount.text = stat.base_stat.toString()
            }
        }
    }

}