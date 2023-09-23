package com.example.githubuser_.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser_.adapter.UserAdapter
import com.example.githubuser_.databinding.FragmentFollowBinding
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.viewModel.DetailViewModel.Companion.username
import com.example.githubuser_.viewModel.FollowViewModel

class FollowFragment : Fragment() {

    private lateinit var _binding: FragmentFollowBinding
    private val binding get() = _binding

    private val followerViewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvItemFollower.layoutManager = layoutManager
        binding.rvItemFollower.setHasFixedSize(true)

        val position = arguments?.getInt(ARG_POSITION)

        if (position == 1) {
            getFollowers()
        } else {
            getFollowing()
        }
    }

    private fun getFollowers() {
        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followerViewModel.getFollowersData(username)
        followerViewModel.listFollowers.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setUserData(user)
            }
        }
    }

    private fun getFollowing() {
        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followerViewModel.getFollowingData(username)
        followerViewModel.listFollowing.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setUserData(user)
            }
        }
    }

    private fun setUserData(data: List<ItemsItem>) {
        if (data.isEmpty()) {
            binding.rvItemFollower.visibility = View.INVISIBLE
            binding.textTest.visibility = View.VISIBLE
        } else {
            binding.rvItemFollower.visibility = View.VISIBLE
            binding.textTest.visibility = View.INVISIBLE
            showLoading(false)
            val adapter = UserAdapter()
            adapter.submitList(data)
            binding.rvItemFollower.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        const val ARG_POSITION = "section_number"
    }

}