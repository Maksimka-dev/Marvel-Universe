package com.marvel.marveluniverse.ui.authorization.registration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.ui.authorization.AuthorizationViewModel
import com.marvel.marveluniverse.ui.authorization.AuthorizationRouter
import com.marvel.marveluniverse.ui.authorization.AuthorizationActivity
import com.marvel.marveluniverse.ui.authorization.AuthorizationSuccess
import com.marvel.marveluniverse.ui.authorization.AuthorizationError
import com.marvel.marveluniverse.ui.authorization.InputError
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    @Inject
    lateinit var authorizationViewModel: AuthorizationViewModel

    private lateinit var authorizationRouter: AuthorizationRouter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is AuthorizationRouter) {
            authorizationRouter = context
        }

        (activity as AuthorizationActivity).authorizationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initClickListeners()

        username_text_registration.doOnTextChanged { text, start, before, count ->
            username_registration.error = null
            text_view_error.visibility = android.view.View.INVISIBLE
        }

        password_text_registration.doOnTextChanged { text, start, before, count ->
            password_registration.error = null
            text_view_error.visibility = android.view.View.INVISIBLE
        }

    }

    private fun initObservers() {

        authorizationViewModel.connectionState.observe(
            viewLifecycleOwner,
            Observer { connectionState ->

                when (connectionState) {
                    is AuthorizationSuccess -> {
                        text_view_error.visibility = View.INVISIBLE
                        register()
                    }
                    is AuthorizationError -> {

                        registration_progress_bar.visibility = android.view.View.INVISIBLE
                        buttonShakeAnimation()
                        text_view_error.text =
                            getString(R.string.connection_error)
                        text_view_error.visibility = android.view.View.VISIBLE

                    }
                }
            })

        authorizationViewModel.inputState.observe(viewLifecycleOwner, Observer { state ->


            when (state) {
                is AuthorizationSuccess -> {
                    authorizationViewModel.checkUserName(username_text_registration.text.toString())
                }
                is InputError -> {

                    registration_progress_bar.visibility = View.INVISIBLE
                    buttonShakeAnimation()

                    when (state.view) {
                        AuthorizationViewModel.USERNAME_LENGTH -> {
                            username_registration.error =
                                getString(R.string.input_incorrect_length)
                        }
                        AuthorizationViewModel.USERNAME_ERROR -> {
                            username_registration.error =
                                getString(R.string.input_incorrect_chars)
                        }
                        AuthorizationViewModel.PASSWORD_LENGTH -> {
                            password_registration.error =
                                getString(R.string.input_incorrect_length)
                        }
                        AuthorizationViewModel.PASSWORD_ERROR -> {
                            password_registration.error =
                                getString(R.string.input_incorrect_chars)
                        }
                    }

                }
            }
        })

        authorizationViewModel.checkUserNameState.observe(viewLifecycleOwner, Observer { state ->


            when (state) {
                is AuthorizationSuccess -> {
                    authorizationViewModel.registerUser(
                        username_text_registration.text.toString(),
                        password_text_registration.text.toString()
                    )
                }
                is AuthorizationError -> {

                    buttonShakeAnimation()
                    registration_progress_bar.visibility = View.INVISIBLE
                    text_view_error.text =
                        getString(R.string.registration_exists)
                    text_view_error.visibility = View.VISIBLE

                }
            }
        })

        authorizationViewModel.registrationState.observe(viewLifecycleOwner, Observer { state ->
            registration_progress_bar.visibility = View.INVISIBLE

            when (state) {
                is AuthorizationSuccess -> {
                    authorizationRouter.goToMainActivity()
                }
                is AuthorizationError -> {

                    buttonShakeAnimation()
                    text_view_error.text =
                        getString(R.string.registration_error)
                    text_view_error.visibility = View.VISIBLE

                }
            }
        })
    }

    private fun initClickListeners() {

        register_button.setOnClickListener {
            authorizationViewModel.checkConnection()
        }

        password_text_registration.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                authorizationViewModel.checkConnection()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false

        }
    }

    private fun hideKeyboard() {

        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
    }

    private fun register() {
        registration_progress_bar.visibility = View.VISIBLE
        authorizationViewModel.validateInput(
            username_text_registration.text.toString(),
            password_text_registration.text.toString()
        )
    }

    private fun buttonShakeAnimation() {
        val shakeAnimation = AnimationUtils.loadAnimation(activity, R.anim.shake)
        register_button.startAnimation(shakeAnimation)
    }
}
