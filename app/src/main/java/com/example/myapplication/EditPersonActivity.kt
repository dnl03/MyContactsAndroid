package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityEditPersonBinding

class EditPersonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val person = intent.getParcelableExtra<Person>("person")
        val index = intent.getIntExtra("index", -1)

        binding.editTextName.setText(person?.name ?: "")
        binding.editTextSurname.setText(person?.surname ?: "")
        binding.editTextNumber.setText(person?.number ?: "")

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val surname = binding.editTextSurname.text.toString().trim()
            val number = binding.editTextNumber.text.toString().trim()

            if (name.isEmpty() || surname.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "Wypelnij wszytkie pola!", Toast.LENGTH_LONG).show()
            } else {
                val editedPerson = Person(name, surname, number)
                val resultIntent = Intent().apply {
                    putExtra("edited_person", editedPerson)
                    putExtra("index", index)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        binding.buttonDelete.setOnClickListener {
            if (index != -1) {
                val resultIntent = Intent().apply {
                    putExtra("index", index)
                    putExtra("delete", true)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Kontakt nie zapisany", Toast.LENGTH_LONG).show()
            }
        }
    }
}