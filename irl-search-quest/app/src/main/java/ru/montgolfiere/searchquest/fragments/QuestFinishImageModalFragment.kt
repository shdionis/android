package ru.montgolfiere.searchquest.fragments


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.montgolfiere.searchquest.R


class QuestFinishImageModalFragment: DialogFragment() {
    var onDismissListener : () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.QuestDialog);
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.modal_fragment_finish_image, container, false)
        root.setOnClickListener {
            dismiss()
        }
        return root
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissListener()
        super.onDismiss(dialog)
    }
}