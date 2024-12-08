package com.testing.realmmm

import com.testing.realmmm.models.Person
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlin.system.measureNanoTime

object DatabaseOperations {

    private lateinit var realm: Realm

    fun initRealm() {

        val config = RealmConfiguration.create(schema = setOf(Person::class))
        realm = Realm.open(config)
    }

    fun addPerson(id: String, name: String, age: Int): Long {
        val time = measureNanoTime {
            realm.writeBlocking {
                copyToRealm(Person().apply {
                    this.id = id
                    this.name = name
                    this.age = age
                })
            }
        }
        return time
    }

    fun getAllPersons(): Pair<List<Person>, Long> {
        var persons: List<Person>
        val time = measureNanoTime {
            persons = realm.query<Person>().find()
        }
        return persons to time
    }

    fun updatePerson(id: String, newName: String, newAge: Int): Long {
        val time = measureNanoTime {
            realm.writeBlocking {
                val person = query<Person>("id == $0", id).first().find()
                person?.apply {
                    this.name = newName
                    this.age = newAge
                }
            }
        }
        return time
    }

    fun deletePerson(id: String): Long {
        val time = measureNanoTime {
            realm.writeBlocking {
                val person = query<Person>("id == $0", id).first().find()
                if (person != null) delete(person)
            }
        }
        return time
    }
}
