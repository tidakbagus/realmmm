package com.testing.realmmm

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Realm (Ensure Realm is initialized here)
        DatabaseOperations.initRealm()

        val logOutput = findViewById<TextView>(R.id.log_output)

        // Add Person
        findViewById<Button>(R.id.add_person_button).setOnClickListener {
            // Perform the add operation in background thread
            lifecycleScope.launch {
                val time = withContext(Dispatchers.IO) {
                    DatabaseOperations.addPerson("1", "John Doe", 25)
                }
                logOutput.text = "Added: John Doe, 25\nExecution Time: ${time}ns"
            }
        }

        // View All Persons
        findViewById<Button>(R.id.view_all_button).setOnClickListener {
            // Perform the get operation in background thread
            lifecycleScope.launch {
                val (persons, time) = withContext(Dispatchers.IO) {
                    DatabaseOperations.getAllPersons()
                }
                val result = persons.joinToString("\n") { "ID: ${it.id}, Name: ${it.name}, Age: ${it.age}" }
                logOutput.text = "Persons:\n$result\nExecution Time: ${time}ns"
            }
        }

        // Update Person
        findViewById<Button>(R.id.update_person_button).setOnClickListener {
            // Perform the update operation in background thread
            lifecycleScope.launch {
                val time = withContext(Dispatchers.IO) {
                    DatabaseOperations.updatePerson("1", "John Smith", 26)
                }
                logOutput.text = "Updated ID: 1 to John Smith, 26\nExecution Time: ${time}ns"
            }
        }

        // Delete Person
        findViewById<Button>(R.id.delete_person_button).setOnClickListener {
            // Perform the delete operation in background thread
            lifecycleScope.launch {
                val time = withContext(Dispatchers.IO) {
                    DatabaseOperations.deletePerson("1")
                }
                logOutput.text = "Deleted Person with ID: 1\nExecution Time: ${time}ns"
            }
        }
    }
}
