package uz.nabijonov.otabek.quotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_quote.view.*
import uz.nabijonov.otabek.quotesapp.CopyListener
import uz.nabijonov.otabek.quotesapp.R
import uz.nabijonov.otabek.quotesapp.model.QuotesModel

class QuotesAdapter(
    private val quotesList: ArrayList<QuotesModel>,
    private val listener: CopyListener
) :
    RecyclerView.Adapter<QuotesAdapter.ItemHolder>() {

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val quote = quotesList[position]
        holder.itemView.text.text = quote.text
        holder.itemView.author.text = "©" + quote.author
        holder.itemView.btn_copy.setOnClickListener {
            listener.onCopyClicked(quote.text)

        }
    }

    override fun getItemCount(): Int {
        return quotesList.size
    }
}