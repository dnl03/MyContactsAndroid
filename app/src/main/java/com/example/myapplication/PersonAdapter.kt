package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPersonBinding

class PersonAdapter(
    private val context: Context,
    private val people: List<Person>,
    private val onClick: (Person, Int) -> Unit
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        holder.binding.textViewName.text = "${person.name} ${person.surname}"
        holder.binding.buttonCall.setOnClickListener {
            onClick(person, position)
        }
    }

    override fun getItemCount(): Int = people.size

    class PersonViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root)
}