package com.example.passwordgenerator.ui.adapter

import androidx.recyclerview.widget.RecyclerView

/* В приложении есть два типа адаптеров: отображающий списки и отображающий пароли. При этом
*  один адаптер не должен отображать и списки, и пароли, т.е. используется только один тип viewholder.
*  Поэтому удобно сделать общий класс адаптера. Можно было бы сделать обычный, неабстрактный класс здесь же,
*  но этому мешает функция onCreateViewHolder, которой нужен конкретный binding, а не ViewDataBinding,
*  на котором нельзя вызвать inflate. */
abstract class ListAdapter<T : Any>: RecyclerView.Adapter<ListItemViewHolder<T>>() {
    protected val list = ArrayList<T>()

    override fun onBindViewHolder(holder: ListItemViewHolder<T>, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<T>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}