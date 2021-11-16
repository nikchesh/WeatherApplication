package ru.iu3.weatherapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.iu3.weatherapplication.databinding.FragmentSearchBinding
import ru.iu3.weatherapplication.presentation.CurrentWeatherViewModel
import java.util.*

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.findCityButton.setOnClickListener {
            if (!binding.editCityText.text.toString().trim().equals("")) {
                val city = binding.editCityText.text.toString()
                bundle.putString("City", city)
                findNavController().navigate(R.id.action_searchFragment_to_mainFragment, bundle)
            }


        }



        binding.locationButton.setOnClickListener {
            bundle.putString("City", "")
            findNavController().navigate(R.id.action_searchFragment_to_mainFragment, bundle)
        }
    }
}