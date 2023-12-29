package ru.montgolfier.comments.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.montgolfier.comments.app.databinding.FragmentGoodsBinding
import ru.montgolfier.comments.app.viewmodel.RoutingViewModel

class FragmentGoods : Fragment() {
    private val routingViewModel by activityViewModels<RoutingViewModel>()

    private var _binding: FragmentGoodsBinding? = null
    private val binding: FragmentGoodsBinding by lazy { _binding!! }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsBinding.inflate(inflater, container, false)
        binding.toCommentsButton.setOnClickListener {
            routingViewModel.goToComments()
        }
        return binding.root
    }
}