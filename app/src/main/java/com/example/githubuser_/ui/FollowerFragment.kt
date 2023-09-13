package com.example.githubuser_.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser_.adapter.UserAdapter
import com.example.githubuser_.databinding.FragmentFollowerBinding
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.viewModel.DetailViewModel
import com.example.githubuser_.viewModel.DetailViewModel.Companion.username


class FollowerFragment : Fragment() {

    private lateinit var _binding: FragmentFollowerBinding
    private val binding get() = _binding

    private val followerViewModel: DetailViewModel by viewModels()


    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "section_number"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val username = arguments?.getString(ARG_USERNAME).toString()
        val position = arguments?.getInt(ARG_POSITION)

        if (position == 1) {
            getFollowers()
        } else {
            getFollowing()
        }

    }

    private fun getFollowers() {
        followerViewModel.getFollowersData(username)
        followerViewModel.listFollowers.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setUserData(user)
            }
        }
    }

    private fun getFollowing() {
        followerViewModel.getFollowingData(username)
        followerViewModel.listFollowing.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setUserData(user)
            }
        }
    }

    private fun setUserData(data: List<ItemsItem>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvItemFollower.layoutManager = layoutManager
        binding.rvItemFollower.setHasFixedSize(true)

        val adapter = UserAdapter()
        adapter.submitList(data)
        binding.rvItemFollower.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}