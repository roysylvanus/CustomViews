package com.malikali.customviewapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.malikali.customviewapp.databinding.CustomViewItemBinding
import com.malikali.customviewapp.databinding.CustomViewLayoutBinding

//extend with LinearLayout because we have Linear Layout inside merge tag
class CustomView(context: Context, attrs: AttributeSet) : LinearLayout(context,attrs)  {


    private lateinit var autoCompleteTextView :AutoCompleteTextView
    private var allItems:MutableList<String> = ArrayList()
    private var selectedItems:MutableList<String> = ArrayList()
    private lateinit var title:TextView
    private lateinit var listView: ListView
    private lateinit var add:ImageView

    init {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
     val view = inflater.inflate(R.layout.custom_view_layout, this, true)
    autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView)
        listView = view.findViewById(R.id.listView)
        title = view.findViewById(R.id.tvTitle)
        add = view.findViewById(R.id.ivAdd)

    autoCompleteTextView.threshold = 1

       add.setOnClickListener {
            val selectedString = autoCompleteTextView.text.trim().toString()

            when{
                selectedString.isEmpty()-> Toast.makeText(context,"Please enter data",Toast.LENGTH_LONG).show()
                selectedItems.contains(selectedString) -> Toast.makeText(context,"Item already added",Toast.LENGTH_LONG).show()

                else-> {
                    selectedItems.add(0, selectedString)
                    refreshData(true)
                }
            }

        }

    }

    fun setData(data:MutableList<String>){
        allItems = data
        autoCompleteTextView.setAdapter(ArrayAdapter(context,android.R.layout.simple_list_item_1,allItems))
    }

    fun setTitle(str:String){
        title.text = str
    }

    fun getSelectedData():MutableList<String>{
        return selectedItems
    }

    fun refreshData(clearData:Boolean){
     listView.adapter = CustomViewAdapter(context,R.layout.custom_view_item,selectedItems)
        setListViewHeightBasedOnChildren(listView)
        if (clearData){
         autoCompleteTextView.setText("")
        }
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter?:return
        var totalHeight = listView.paddingTop + listView.paddingBottom

        for (i in 0 until listAdapter.count){
            val listItem = listAdapter.getView(i,null,listView)
            (listItem as ViewGroup).layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT)
            listItem.measure(0,0)
            totalHeight += listItem.measuredHeight

        }

        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count-1)
        listView.layoutParams = params

    }

    inner class CustomViewAdapter(context: Context,var resource: Int,var objects: MutableList<String>) :
        ArrayAdapter<String>(context, resource, objects){

        private val inflater:LayoutInflater = LayoutInflater.from(context)

        override fun getCount(): Int {
            return objects.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view =  inflater.inflate(resource,parent,false)
            val name = view.findViewById<TextView>(R.id.name)
            val delete = view.findViewById<ImageView>(R.id.delete)
            name.text = objects[position]
           delete.setOnClickListener {
                selectedItems.removeAt(position)
                refreshData(false)

            }

            return view
        }

        }
}