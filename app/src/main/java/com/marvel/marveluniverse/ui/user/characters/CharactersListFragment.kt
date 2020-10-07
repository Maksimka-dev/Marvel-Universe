package com.marvel.marveluniverse.ui.user.characters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
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
import com.paginate.Paginate
import kotlinx.android.synthetic.main.characters_list_fragment.*
import javax.inject.Inject

private var position: Int? = null

class CharactersListFragment : Fragment() {

    @Inject
    lateinit var charactersListViewModel: CharactersListViewModel

    lateinit var mainRouter: Router

    private lateinit var recyclerView: RecyclerView
    private lateinit var callbacks: Paginate.Callbacks
    private var isLoadingState = false
    private var hasLoadedAllCharacters = false

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
        return inflater.inflate(R.layout.characters_list_fragment, container, false)
    }

    override fun onPause() {
        super.onPause()
        position = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        Log.i("tag", "before change ${position}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CharacterAdapter((::openCharacterDetailsFragment)())

        recyclerView = characters_recycler_view
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.adapter = adapter

        charactersListViewModel.characters.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

            if (position != null) {
                (recyclerView.layoutManager as GridLayoutManager).scrollToPosition(position!!)
                Log.i("tag", "after change ${position}")
                position = null
            }
        })

        charactersListViewModel.isLoadingState.observe(viewLifecycleOwner, Observer {
            isLoadingState = it
        })

        charactersListViewModel.hasLoadedAllItemsState.observe(viewLifecycleOwner, Observer {
            hasLoadedAllCharacters = it
        })

        charactersListViewModel.connectionState.observe(
            viewLifecycleOwner,
            Observer { connectionState ->
                if (!connectionState) {
                    showSnackBar(getString(R.string.connection_error))
                }
            })

        callbacks = object : Paginate.Callbacks {
            override fun onLoadMore() {
                charactersListViewModel.getNewCharacters()
            }

            override fun isLoading(): Boolean {
                return isLoadingState
            }

            override fun hasLoadedAllItems(): Boolean {
                return hasLoadedAllCharacters
            }
        }

        Paginate.with(recyclerView, callbacks)
            .setLoadingTriggerThreshold(0)
            .addLoadingListItem(true)
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.characters_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings_item_menu -> {
                mainRouter.goToSettingFragment(requireActivity())
            }
            R.id.search_item_menu -> {
                mainRouter.goToSearchFragment(requireActivity())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            this.requireView(), message, Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.try_again) {
                callbacks.onLoadMore()
            }
            .setBackgroundTint(resources.getColor(R.color.colorPrimary))
            .show()
    }

    private fun openCharacterDetailsFragment(): (Character) -> Unit = {
        mainRouter.goToDetailsCharacterFragment(requireActivity(), it.id)
    }

    companion object {
        private val TAG = CharactersListFragment::class.java.simpleName
    }
}
