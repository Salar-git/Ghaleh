package ir.iamsalar.ghaleh.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.iamsalar.ghaleh.R
import ir.iamsalar.ghaleh.databinding.AdapterContentItemBinding
import ir.iamsalar.ghaleh.network.model.ContentResponseModel
import ir.iamsalar.ghaleh.viewmodel.ContentViewModel

class ContetnItemAdapter(
    private var contetns: List<ContentResponseModel>,
    private var viewModel: ContentViewModel
) :
    RecyclerView.Adapter<ContetnItemAdapter.ContentViewHolder>() {

    inner class ContentViewHolder(
        private var binding: AdapterContentItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contetn: ContentResponseModel) {
            binding.apply {
                tvTitle.text = contetn.title
                tvAuthor.text = contetn.author
                tvCategory.text = contetn.category
                tvLike.text = "${contetn.likes}"


                lytLike.setOnClickListener {
                    viewModel.like(contetn.id)
                    imgLike.setImageDrawable(imgLike.context.getDrawable(R.drawable.ic_favorite))
                    tvLike.text = "${(tvLike.text.toString()).toInt() + 1}"
                    viewModel.currentPosition = adapterPosition
                }

            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val viewHolder = ContentViewHolder(
            AdapterContentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(contetns.get(position))

    }

    override fun getItemCount(): Int {
        return contetns.size
    }


    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}