package ru.shaden.tasktracker.model.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
class ProjectEntity {
    @[PrimaryKey(autoGenerate = true) ColumnInfo(name = "id")]
    var projectId: Int = 0

    @ColumnInfo
    var name: String = ""

    @ColumnInfo
    var description: String? = null
}
