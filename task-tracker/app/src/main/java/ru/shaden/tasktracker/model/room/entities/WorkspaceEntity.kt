package ru.shaden.tasktracker.model.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "workspaces", foreignKeys = [ForeignKey(
        entity = ProjectEntity::class,
        parentColumns = ["id"],
        childColumns = ["project_id"],
        onDelete = CASCADE
    )]
)
class WorkspaceEntity {
    @[PrimaryKey(autoGenerate = true) ColumnInfo(name = "id")]
    var workspaceId: Int = 0
    @ColumnInfo
    var name: String = ""
    @ColumnInfo
    var description: String? = null
    @ColumnInfo(name = "project_id")
    var projectId: Int = 0
}
