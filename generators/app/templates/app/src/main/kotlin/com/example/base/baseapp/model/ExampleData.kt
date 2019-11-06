package com.example.base.baseapp.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.ArrayList

@Entity
class ExampleData(@field:PrimaryKey
                  @field:ColumnInfo(name = "id")
                  var id: String, @field:ColumnInfo(name = "content")
                  var content: String?) {
    companion object {

        private val NUMBER_OF_ELEMENTS = 10

        fun populateData(): List<ExampleData> {
            val exampleData = ArrayList<ExampleData>()
            for (i in 0 until NUMBER_OF_ELEMENTS) {
                exampleData.add(ExampleData(i.toString(), "Data $i"))
            }
            return exampleData
        }
    }
}
