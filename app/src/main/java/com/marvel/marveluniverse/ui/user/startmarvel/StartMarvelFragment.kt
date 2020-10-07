package com.marvel.marveluniverse.ui.user.startmarvel

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.ui.user.main.Router
import kotlinx.android.synthetic.main.fragment_start_marvel.*

class StartMarvelFragment : Fragment() {

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
        setHasOptionsMenu(true)

        mainRouter.configureActionBar(requireActivity(),false)

        return inflater.inflate(R.layout.fragment_start_marvel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comics_start_marvel.setOnClickListener {
            mainRouter.goToComicsListFragment(requireActivity())
        }

        characters_start_marvel.setOnClickListener {
            mainRouter.goToCharactersListFragment(requireActivity())
        }
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
}
