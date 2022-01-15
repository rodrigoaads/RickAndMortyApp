package com.rodrigoads.rickandmortyapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rodrigoads.rickandmortyapp.databinding.HeaderFragmentBinding

class HeaderFragment : Fragment() {
    lateinit var binding: HeaderFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HeaderFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}