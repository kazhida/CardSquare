package com.abplus.cardsquare.app.ui.carddetail

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abplus.cardsquare.app.R

/**
 * A placeholder fragment containing a simple view.
 */
class CardDetailActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_card_detail, container, false)
    }
}
