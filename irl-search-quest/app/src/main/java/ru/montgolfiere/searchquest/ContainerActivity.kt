package ru.montgolfiere.searchquest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.montgolfiere.searchquest.fragments.QuestFragmentFactory
import ru.montgolfiere.searchquest.viewmodels.QuestViewModel
import ru.montgolfiere.searchquest.viewmodels.QuestViewModelFactory
import javax.inject.Inject

class ContainerActivity : AppCompatActivity() {
    @Inject
    lateinit var questFragmentFactory: QuestFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        QuestApplication.component.inject(this)
        val fragmentFactory = questFragmentFactory
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_activity)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_content, fragmentFactory.createQuestFragment())
            .commit()

    }
}