package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val people = mutableListOf(
        Person("fdg", "fdgdfv", "123321123"),
        Person("gdfg", "werewfd", "12332123"),
        Person("sad", "werewr", "123321231"),
        Person("xzcxzc", "fgdfg", "12321213"),
    )
    private lateinit var adapter: PersonAdapter

    private val editPersonLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val index = result.data?.getIntExtra("index", -1) ?: -1
            val isDelete = result.data?.getBooleanExtra("delete", false) ?: false

            if (isDelete && index != -1) {
                people.removeAt(index)
                adapter.notifyItemRemoved(index)
            } else {
                val editedPerson = result.data?.getParcelableExtra<Person>("edited_person")
                if (editedPerson != null) {
                    if (index == -1) {
                        people.add(editedPerson)
                        adapter.notifyItemInserted(people.size - 1)
                    } else if (index != -1) {
                        people[index] = editedPerson
                        adapter.notifyItemChanged(index)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        adapter = PersonAdapter(this, people) { person, index ->
            val intent = Intent(this, EditPersonActivity::class.java).apply {
                putExtra("person", person)
                putExtra("index", index)
            }
            editPersonLauncher.launch(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupAddButton() {
        binding.btnAddPerson.setOnClickListener {
            val intent = Intent(this, EditPersonActivity::class.java)
            intent.putExtra("index", -1)
            editPersonLauncher.launch(intent)
        }
    }
}