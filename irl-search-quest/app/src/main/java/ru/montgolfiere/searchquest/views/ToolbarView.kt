package ru.montgolfiere.searchquest.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.montgolfiere.searchquest.R

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val title: TextView
    private val subtitle: TextView
    private val navigationLeftButton: ImageView

    init {
        inflate(context, R.layout.toolbar, this)
        title = findViewById(R.id.toolbar_title)
        subtitle = findViewById(R.id.toolbar_subtitle)
        navigationLeftButton = findViewById(R.id.toolbar_left_menu_button)
        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(
                attrs,
                R.styleable.ToolbarView,
                defStyleAttr,
                0
            )
            val leftButtomImage = attributes.getDrawable(R.styleable.ToolbarView_left_button_img)
            val toolbarTitle = attributes.getString(R.styleable.ToolbarView_title)
            val toolbarSubtitle = attributes.getString(R.styleable.ToolbarView_subtitle)
            toolbarTitle?.let { setTitle(it) }
            toolbarSubtitle?.let { setSubTitle(it) }
            leftButtomImage?.let { navigationLeftButton.setImageDrawable(it) }
            attributes.recycle()
        }
    }

    fun setTitle(value: String?) {
        title.text = value
    }

    fun setSubTitle(value: String?) {
        if (value.isNullOrEmpty()) {
            subtitle.visibility = View.GONE
            return
        }
        subtitle.visibility = View.VISIBLE
        subtitle.text = value
    }

    fun enableBackMode() {
        navigationLeftButton.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_arrow_back_24
            )
        )
    }

    fun setLeftButtonClickListener(l: OnClickListener?) {
        navigationLeftButton.setOnClickListener(l)
    }

}