package ru.shaden.tasktracker.model.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "task_statuses")
class StatusEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var statusId: Int = 0

    @ColumnInfo
    var name: String = ""
}
