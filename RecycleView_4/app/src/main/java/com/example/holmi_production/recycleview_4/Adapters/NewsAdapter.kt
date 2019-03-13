import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.holmi_production.recycleview_4.ActivityItem
import com.example.holmi_production.recycleview_4.Adapters.ListItem
import com.example.holmi_production.recycleview_4.Adapters.NewsItem
import com.example.holmi_production.recycleview_4.Model.News
import com.example.holmi_production.recycleview_4.R

//package com.example.holmi_production.recycleview_4.Adapters
//
//import android.content.Intent
//import android.support.v4.content.ContextCompat.startActivity
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.example.holmi_production.recycleview_4.ActivityItem
//import com.example.holmi_production.recycleview_4.Model.News
//import com.example.holmi_production.recycleview_4.R
//
//class NewsAdapter(_news: ArrayList<News>, _isFavorite: Boolean) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
//    private var news = _news
//    private var isFavorite = _isFavorite
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.view_list_item_news, parent, false)
//        news = if (isFavorite) news.filter { it.isFavorites } as ArrayList<News> else news
//        return NewsHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return news.size
//    }
//
//    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
//        val news = news[position]
//        holder.bind(news)
//    }
//
//
//    class NewsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
//        private val theme = v.findViewById<TextView>(R.id.Theme)
//        private val date = v.findViewById<TextView>(R.id.Date)
//        private val content = v.findViewById<TextView>(R.id.Content)
//        private var isFavorite = false
//
//        init {
//            v.setOnClickListener(this)
//        }
//
//        fun bind(news: News) {
//            theme.text = news.theme
//            date.text = news.date
//            content.text = news.content
//            isFavorite = news.isFavorites
//        }
//
//        override fun onClick(v: View) {
//            val intent = Intent(v.context, ActivityItem::class.java).apply{
//                putExtra("theme",theme.text)
//                putExtra("content", content.text)
//                putExtra("date",date.text)
//                putExtra("isFavorite", isFavorite)
//            }
//            startActivity(v.context,intent,null)
//            Log.d("RecyclerView", "CLICK!")
//        }
//    }
//}
//

class NewsAdapter(val listItem: List<NewsItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class HeaderViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var txt_header: TextView = itemView.findViewById(R.id.txt_header) as TextView

    }

    private class NewsViewHolder internal constructor(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private val theme = v.findViewById<TextView>(R.id.Theme)
        private val date = v.findViewById<TextView>(R.id.Date)
        private val content = v.findViewById<TextView>(R.id.Content)
        private var isFavorite = false

        init {
            v.setOnClickListener(this)
        }

        fun bind(news: News) {
            theme.text = news.theme
            date.text = news.date
            content.text = news.content
            isFavorite = news.isFavorites
        }

        override fun onClick(v: View) {

            val intent = Intent(v.context, ActivityItem::class.java).apply {
                putExtra("theme", theme.text)
                putExtra("content", content.text)
                putExtra("date", date.text)
                putExtra("isFavorite", isFavorite)
            }
            startActivity(v.context, intent, null)
            Log.d("RecyclerView", "CLICK!")
        }
    }

    private var items = emptyList<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ListItem.TYPE_HEADER -> {
                val itemView = inflater.inflate(R.layout.view_list_item_header, parent, false)
                HeaderViewHolder(itemView)
            }
            ListItem.TYPE_NEWS -> {
                val itemView = inflater.inflate(R.layout.view_list_item_news, parent, false)
                NewsViewHolder(itemView)
            }
            else -> throw IllegalStateException("unsupported item type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

