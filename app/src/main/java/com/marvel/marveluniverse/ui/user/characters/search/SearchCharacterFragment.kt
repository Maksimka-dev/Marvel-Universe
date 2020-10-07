package com.marvel.marveluniverse.ui.user.characters.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.MenuInflater
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marvel.marveluniverse.MyApp
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.model.Character
import com.marvel.marveluniverse.ui.user.main.Router
import com.marvel.marveluniverse.ui.user.characters.adapter.CharacterAdapter
import kotlinx.android.synthetic.main.fragment_search_character.*
import javax.inject.Inject

private var position: Int? = null

class SearchCharacterFragment : Fragment() {

    @Inject
    lateinit var searchCharacterViewModel: SearchCharacterViewModel

    lateinit var mainRouter: Router

    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Router) {
            mainRouter = context
        }

        (context.applicationContext as MyApp).appComponent.userManager().userComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        mainRouter.configureActionBar(requireActivity(),true)
        return inflater.inflate(R.layout.fragment_search_character, container, false)
    }

    override fun onPause() {
        super.onPause()
        position = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CharacterAdapter((::openCharacterDetailsFragment)())

        recyclerView = search_character_recyclerview
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.adapter = adapter

        search_character_input_text.doOnTextChanged { text, start, before, count ->
            search()
        }

        searchCharacterViewModel.characters.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

            if (position != null) {
                (recyclerView.layoutManager as GridLayoutManager).scrollToPosition(position!!)
                position = null
            }
        })

        searchCharacterViewModel.isLoadingState.observe(
            viewLifecycleOwner,
            Observer { loadingState ->
                if (loadingState) {
                    Log.i("tag", "visible")
                    search_progress_bar.visibility = View.VISIBLE
                } else {
                    Log.i("tag", "invisible")
                    search_progress_bar.visibility = View.INVISIBLE
                }
            })

        searchCharacterViewModel.connectionState.observe(
            viewLifecycleOwner,
            Observer { connectionState ->
                if (connectionState) {
                    search()
                } else {
                    showSnackBar(getString(R.string.connection_error))
                }
            })

        search_button.setOnClickListener {
            hideKeyboard()
            searchCharacterViewModel.checkConnection()
        }

        search_character_input_text.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                searchCharacterViewModel.checkConnection()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun hideKeyboard() {

        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
    }

    private fun search() {
        val name = search_character_input_text.text.toString()
        searchCharacterViewModel.getCharactersByName(name)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings_item_menu -> {
                mainRouter.goToSettingFragment(requireActivity())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            this.requireView(), message, Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.try_again) {
                searchCharacterViewModel.checkConnection()
            }
            .setBackgroundTint(resources.getColor(R.color.colorPrimary))
            .show()
    }

    private fun openCharacterDetailsFragment(): (Character) -> Unit = {
        mainRouter.goToDetailsCharacterFragment(requireActivity(), it.id)
    }
}
