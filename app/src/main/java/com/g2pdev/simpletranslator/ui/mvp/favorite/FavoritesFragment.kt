package com.g2pdev.simpletranslator.ui.mvp.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g2pdev.simpletranslator.R
import com.g2pdev.simpletranslator.db.FavoriteTranslation
import com.g2pdev.simpletranslator.ui.mvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.fragment_favorites.*
import moxy.presenter.InjectPresenter

class FavoritesFragment : BaseMvpFragment(), FavoritesView {

    @InjectPresenter
    lateinit var presenter: FavoritesPresenter

    private var adapter = FavoritesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        adapter.onCopyClickListener = {
            presenter.copyText(it.targetText)
        }

        adapter.onShareClickListener = {
            presenter.shareText(it.targetText)
        }

        favoritesRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        favoritesRv.adapter = adapter
    }

    override fun showFavorites(favorites: List<FavoriteTranslation>) {
        adapter.setFavorites(favorites)

        if (favorites.isEmpty()) {
            favoritesRv.isVisible = false
            emptyTv.isVisible = true
        } else {
            emptyTv.isVisible = false
            favoritesRv.isVisible = true
        }
    }

    override fun showCopiedToClipboard() {
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show()
    }

    override fun shareText(text: String) {
        Intent(Intent.ACTION_SEND)
            .apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            .also {
                startActivity(it)
            }
    }
}