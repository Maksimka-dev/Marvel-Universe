package com.marvel.marveluniverse.ui.user.comics.details

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.marvel.marveluniverse.MyApp
import com.marvel.marveluniverse.R
import com.marvel.marveluniverse.model.Comics
import com.marvel.marveluniverse.ui.user.main.Router
import kotlinx.android.synthetic.main.fragment_comics_details.*
import javax.inject.Inject

class ComicsDetailsFragment : Fragment() {

    @Inject
    lateinit var comicsDetailsViewModel: ComicsDetailsViewModel

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
        setHasOptionsMenu(true)

        mainRouter.configureActionBar(requireActivity(),true)

        return inflater.inflate(R.layout.fragment_comics_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(ID_COMICS_ARGUMENT)
        var currentComics: Comics? = null

        comicsDetailsViewModel.comicsByIdState.observe(viewLifecycleOwner, Observer {
            currentComics = it
            update(it)
        })

        id?.let { comicsDetailsViewModel.getComicsById(it) }

        text_button.setOnClickListener {
            mainRouter.openUrl(requireActivity(), currentComics?.url)
        }
    }

    private fun update(comics: Comics) {

        Glide
            .with(requireActivity())
            .load(comics.thumbnail)
            .into(image_comics_details)

        title_comics_details.text = comics.title
        description_comics_details.text = comics.description
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

    companion object {
        private const val ID_COMICS_ARGUMENT = "ID_COMICS"

        fun createInstance(id: Int?): ComicsDetailsFragment {
            return ComicsDetailsFragment().apply {
                arguments = bundleOf(
                    ID_COMICS_ARGUMENT to id
                )
            }
        }
    }
}
