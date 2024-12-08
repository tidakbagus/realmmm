package com.testing.realmmm.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

 class Person : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var age: Int = 0
}
