package com.marvel.marveluniverse.ui.user.characters.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.MenuItem
import android.view.Menu
import android.view.MenuInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.marvel.marveluniverse.MyApp
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.model.Character
import com.marvel.marveluniverse.ui.user.main.Router
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {

    @Inject
    lateinit var characterDetailsViewModel: CharacterDetailsViewModel

    lateinit var mainRouter: Router

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
        Log.i("tag", "details fragment onCreateView")
        setHasOptionsMenu(true)

        mainRouter.configureActionBar(requireActivity(),true)

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(ID_CHARACTER_ARGUMENT)

        characterDetailsViewModel.characterByIdState.observe(viewLifecycleOwner, Observer {
            update(it)
        })

        id?.let { characterDetailsViewModel.getCharacterById(it) }
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

    private fun update(character: Character) {
        Log.i("tag", "details fragment update ${character}")

        Glide.with(requireActivity())
            .load(character.thumbnail)
            .into(details)

        character_name.text = character.name
        description.text = character.description
    }

    companion object {
        private val TAG = CharacterDetailsFragment::class.java.simpleName
        private const val ID_CHARACTER_ARGUMENT = "ID_CHARACTER"

        fun createInstance(id: Int?): CharacterDetailsFragment {
            return CharacterDetailsFragment().apply {
                arguments = bundleOf(
                    ID_CHARACTER_ARGUMENT to id
                )
            }
        }
    }
}
