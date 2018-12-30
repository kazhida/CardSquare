package com.abplus.cardsquare.app.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.abplus.cardsquare.app.R
import com.abplus.cardsquare.app.databinding.FragmentSquareCardBinding

class SquareCardFragment : Fragment() {

    private var binding: FragmentSquareCardBinding? = null

    val viewModel: SquareCardViewModel? get() = binding?.viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_square_card, container).also { view ->
            binding = FragmentSquareCardBinding.bind(view).also { binding ->
                binding.setLifecycleOwner(this)
                binding.viewModel = ViewModelProviders.of(this).get(SquareCardViewModel::class.java)
            }
        }
    }
}