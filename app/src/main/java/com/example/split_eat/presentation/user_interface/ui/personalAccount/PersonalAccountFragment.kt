package com.example.split_eat.presentation.user_interface.ui.personalAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.split_eat.databinding.FragmentPersonalAccountBinding
import com.example.split_eat.R
import com.example.split_eat.databinding.FragmentNewApplicationBinding
import com.example.split_eat.presentation.user_interface.ui.personalAccount.PersonalAccountViewModel

class PersonalAccountFragment : Fragment() {

private var _binding: FragmentPersonalAccountBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val personalAccountViewModel =
            ViewModelProvider(this).get(PersonalAccountViewModel::class.java)

        _binding = FragmentPersonalAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPersonalAccount
        personalAccountViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
        //return inflater.inflate(R.layout.fragment_personal_account, container, false)
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}