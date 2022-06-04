package ru.shaden.tasktracker.model.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks", foreignKeys = [
        ForeignKey(
            entity = WorkspaceEntity::class,
            parentColumns = ["id"],
            childColumns = ["workspace_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StatusEntity::class,
            parentColumns = ["id"],
            childColumns = ["status_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class TaskEntity {
    @[PrimaryKey(autoGenerate = true) ColumnInfo(name = "id")]
    var taskId: Int = 0

    @ColumnInfo
    var name: String = ""

    @ColumnInfo
    var description: String? = null

    @ColumnInfo(name = "workspace_id")
    var workspaceId: Int = 0

    @ColumnInfo(name = "status_id")
    var statusId: Int = 0

}
