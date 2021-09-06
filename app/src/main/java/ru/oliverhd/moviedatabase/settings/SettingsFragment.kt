package ru.oliverhd.moviedatabase.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.oliverhd.moviedatabase.R
import ru.oliverhd.moviedatabase.databinding.FragmentSettingsBinding
import ru.oliverhd.moviedatabase.datasource.RemoteDataSource.Companion.isAdultOn
import ru.oliverhd.moviedatabase.utils.ADULT_STATE
import ru.oliverhd.moviedatabase.utils.IS_ADULT_ON

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adultRatingSwitch.isChecked =
            activity?.getPreferences(Context.MODE_PRIVATE)?.getBoolean(IS_ADULT_ON, true) ?: true
        binding.adultRatingTextView.text = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString(ADULT_STATE, getString(R.string.rating_r_on))
            ?: getString(R.string.rating_r_on)
        binding.adultRatingSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.adultRatingTextView.text = getString(R.string.rating_r_on)
                isAdultOn = true
            } else {
                binding.adultRatingTextView.text = getString(R.string.rating_r_off)
                isAdultOn = false
            }
            saveAdultState(isAdultOn, binding.adultRatingTextView.text.toString())
        }
    }

    private fun saveAdultState(isAdult: Boolean, state: String) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(IS_ADULT_ON, isAdult)
                putString(ADULT_STATE, state)
                apply()
            }
        }
    }


    companion object {
        fun newInstance() = SettingsFragment()
    }
}