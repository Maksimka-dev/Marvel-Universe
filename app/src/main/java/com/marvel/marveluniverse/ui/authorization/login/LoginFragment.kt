package com.marvel.marveluniverse.ui.authorization.login

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
import com.marvel.marveluniverse.databinding.FragmentLoginBinding
import com.marvel.marveluniverse.ui.authorization.AuthorizationViewModel
import com.marvel.marveluniverse.ui.authorization.AuthorizationRouter
import com.marvel.marveluniverse.ui.authorization.AuthorizationActivity
import com.marvel.marveluniverse.ui.authorization.AuthorizationSuccess
import com.marvel.marveluniverse.ui.authorization.AuthorizationError
import com.marvel.marveluniverse.ui.authorization.InputError
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var authorizationViewModel: AuthorizationViewModel

    private lateinit var authorizationRouter: AuthorizationRouter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is AuthorizationActivity) {
            authorizationRouter = context
        }

        (activity as AuthorizationActivity).authorizationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initClickListeners()

        username_input_text.doOnTextChanged { text, start, before, count ->
            username_input_layout.error = null
            text_view_login_error.visibility = View.INVISIBLE
        }

        password_input_text.doOnTextChanged { text, start, before, count ->
            password_input_layout.error = null
            text_view_login_error.visibility = View.INVISIBLE
        }
    }

    private fun initObservers() {

        authorizationViewModel.connectionState.observe(
            viewLifecycleOwner,
            Observer { connectionState ->

                when (connectionState) {
                    is AuthorizationSuccess -> {
                        text_view_login_error.visibility = View.INVISIBLE
                        login()
                    }
                    is AuthorizationError -> {

                        login_activity_progress_bar.visibility = android.view.View.INVISIBLE
                        buttonShakeAnimation()
                        text_view_login_error.text =
                            getString(R.string.connection_error)
                        text_view_login_error.visibility = android.view.View.VISIBLE

                    }
                }
            })

        authorizationViewModel.inputState.observe(viewLifecycleOwner, Observer { inputState ->

            when (inputState) {
                is AuthorizationSuccess -> {
                    authorizationViewModel.checkUserInDatabase(
                        username_input_text.text.toString()
                    )
                }
                is InputError -> {

                    login_activity_progress_bar.visibility = android.view.View.INVISIBLE
                    buttonShakeAnimation()

                    when (inputState.view) {
                        AuthorizationViewModel.USERNAME_LENGTH -> {
                            username_input_layout.error =
                                getString(R.string.input_incorrect_length)
                        }
                        AuthorizationViewModel.USERNAME_ERROR -> {
                            username_input_layout.error =
                                getString(R.string.input_incorrect_chars)
                        }
                        AuthorizationViewModel.PASSWORD_LENGTH -> {
                            password_input_layout.error =
                                getString(R.string.input_incorrect_length)
                        }
                        AuthorizationViewModel.PASSWORD_ERROR -> {
                            password_input_layout.error =
                                getString(R.string.input_incorrect_chars)
                        }
                    }

                }
            }
        })

        authorizationViewModel.checkUserInDatabaseState.observe(viewLifecycleOwner, Observer {

            when (it) {
                is AuthorizationSuccess -> {
                    authorizationViewModel.login(
                        username_input_text.text.toString(),
                        password_input_text.text.toString()
                    )
                }
                is AuthorizationError -> {
                    buttonShakeAnimation()
                    login_activity_progress_bar.visibility = View.INVISIBLE
                    text_view_login_error.text = getString(R.string.login_error)
                    text_view_login_error.visibility = View.VISIBLE
                }
            }
        })

        authorizationViewModel.loginState.observe(viewLifecycleOwner, Observer { state ->

            login_activity_progress_bar.visibility = View.INVISIBLE

            when (state) {
                is AuthorizationSuccess -> {
                    authorizationRouter.goToMainActivity()
                }
                is AuthorizationError -> {
                    buttonShakeAnimation()
                    login_activity_progress_bar.visibility = View.INVISIBLE
                    text_view_login_error.text = getString(R.string.input_error)
                    text_view_login_error.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun initClickListeners() {
        button_login.setOnClickListener {
            authorizationViewModel.checkConnection()
        }

        password_input_text.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                authorizationViewModel.checkConnection()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun login() {
        login_activity_progress_bar.visibility = View.VISIBLE

        authorizationViewModel.validateInput(
            username_input_text.text.toString(),
            password_input_text.text.toString()
        )
    }

    private fun hideKeyboard() {

        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
    }

    private fun buttonShakeAnimation() {
        val shakeAnimation = AnimationUtils.loadAnimation(activity, R.anim.shake)
        button_login.startAnimation(shakeAnimation)
    }
}
