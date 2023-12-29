package ru.montgolfier.comments.app.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.montgolfier.comments.app.databinding.FragmentCommentsBinding
import ru.montgolfier.comments.app.view.comments.CommentsAdapter
import ru.montgolfier.comments.app.viewmodel.CommentsViewModel

class FragmentComments(viewModelFactory: ViewModelProvider.Factory) : Fragment() {

    private val commentsViewModel: CommentsViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentCommentsBinding? = null
    private val binding: FragmentCommentsBinding by lazy { _binding!! }
    private lateinit var adapter: CommentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        adapter = CommentsAdapter()
        binding.commentsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.commentsRecyclerView.adapter = adapter
        commentsViewModel.liveDataComments.observe(viewLifecycleOwner) {
            when (it) {
                is CommentsViewModel.CommentsUiState.Data -> adapter.updateData(it.result)
                CommentsViewModel.CommentsUiState.Error -> {
                    Log.d(TAG, "StateError")
                }

                CommentsViewModel.CommentsUiState.LoadingData -> adapter.loadingData()
                CommentsViewModel.CommentsUiState.NoData -> {
                    Log.d(TAG, "StateNoData")
                }
            }

        }
        commentsViewModel.loadComments()
        return binding.root
    }

    companion object {
        const val TAG = "FragmentComments"
    }
}