package com.mattcapazz.wheelviana

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LineAdapter(private val dataSet: ArrayList<Place>) :
  RecyclerView.Adapter<LineAdapter.ViewHolder>() {

  /**
   * Provide a reference to the type of views that you are using
   * (custom ViewHolder).
   */
  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val vhAutocarro: TextView = view.findViewById(R.id.autocarro)
    val vhParagem: TextView = view.findViewById(R.id.paragem)
    val vhHora: TextView = view.findViewById(R.id.hora)
  }

  // Create new views (invoked by the layout manager)
  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
    // Create a new view, which defines the UI of the list item
    val view = LayoutInflater.from(viewGroup.context)
      .inflate(R.layout.line, viewGroup, false)
    return ViewHolder(view)
  }

  // Replace the contents of a view (invoked by the layout manager)
  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    /* Get element from your dataset at this position and replace the
    contents of the view with that element */
    viewHolder.vhAutocarro.text = dataSet[position].autocarro_id
    viewHolder.vhParagem.text = dataSet[position].paragem
    viewHolder.vhHora.text = dataSet[position].hora
  }

  // Return the size of your dataset (invoked by the layout manager)
  override fun getItemCount() = dataSet.size

}

