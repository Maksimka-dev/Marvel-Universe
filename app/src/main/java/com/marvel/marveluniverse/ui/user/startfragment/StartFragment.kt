package com.marvel.marveluniverse.ui.user.startfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.ui.authorization.AuthorizationViewModel
import com.marvel.marveluniverse.ui.user.main.Router
import kotlinx.android.synthetic.main.start_fragment.*

class StartFragment : Fragment() {

    lateinit var mainRouter: Router

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Router) {
            mainRouter = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.start_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {
            mainRouter.goToAuthorization(requireActivity(), AuthorizationViewModel.LOGIN)
        }

        register_button.setOnClickListener {
            mainRouter.goToAuthorization(requireActivity(), AuthorizationViewModel.REGISTRATION)
        }
    }

    companion object {
        private val TAG = StartFragment::class.java.simpleName
    }
}
