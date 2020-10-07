package com.marvel.marveluniverse.ui.user.comics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marvel.marveluniverse.MyApp
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.model.Comics
import com.marvel.marveluniverse.ui.user.main.Router
import com.marvel.marveluniverse.ui.user.comics.adapter.ComicsAdapter
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_comics_list.*
import javax.inject.Inject

private var position: Int? = null

class ComicsListFragment : Fragment() {

    @Inject
    lateinit var comicsViewModel: ComicsViewModel

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
        return inflater.inflate(R.layout.fragment_comics_list, container, false)
    }

    override fun onPause() {
        super.onPause()
        position = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ComicsAdapter((::openComicsDetailsFragment)())

        recyclerView = comics_recycler_view
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.adapter = adapter

        comicsViewModel.comics.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

            if (position != null) {
                (recyclerView.layoutManager as GridLayoutManager).scrollToPosition(position!!)
                position = null
            }
        })

        comicsViewModel.isLoadingState.observe(viewLifecycleOwner, Observer {
            isLoadingState = it
        })

        comicsViewModel.hasLoadedAllItemsState.observe(viewLifecycleOwner, Observer {
            hasLoadedAllCharacters = it
        })

        comicsViewModel.connectionState.observe(
            viewLifecycleOwner,
            Observer { connectionState ->
                if (!connectionState) {
                    showSnackBar(getString(R.string.connection_error))
                }
            })

        callbacks = object : Paginate.Callbacks {
            override fun onLoadMore() {
                comicsViewModel.getNewComics()
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
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings_item_menu -> {
                mainRouter.goToSettingFragment(requireActivity())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openComicsDetailsFragment(): (Comics) -> Unit = {
        mainRouter.goToDetailsComicsFragment(requireActivity(), it.id)
    }
}
