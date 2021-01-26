package com.avalon.avalon.ui.main.fragments.profile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avalon.avalon.databinding.ProfileFragmentBinding
import com.avalon.avalon.utils.loadPPUrl
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding:ProfileFragmentBinding
    private lateinit var pieChart: PieChart
    private lateinit var chartList :List<PieData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
        binding.ivProfilePageIm.loadPPUrl("https://www.harikalardiyari.com/wp-content/uploads/2018/08/Selfie-%C3%87ekmenin-P%C3%BCf-Noktalar%C4%B1.jpg")
        pieChart = binding.chartPie

        val pieEntries: ArrayList<PieEntry> = ArrayList()

        val typeAmountMap = HashMap<String, Int>()

        typeAmountMap.put("Kaybedilen", 200)
        typeAmountMap.put("Kazanilan", 230)
        typeAmountMap.put("Engelleyenler", 100)
        for (type in typeAmountMap.keys) {
            typeAmountMap[type]?.toFloat()?.let { PieEntry(it, type) }?.let { pieEntries.add(it) }
        }
       val pieDataSet = PieDataSet(pieEntries,"label");
        val colors = ArrayList<Int>();
        colors.add(Color.parseColor("#2ecc71"))
        colors.add(Color.parseColor("#e74c3c"))
        colors.add(Color.parseColor("#f39c12"))

        pieDataSet.valueTextSize = 9f
        pieDataSet.colors =colors

        val pieData = PieData(pieDataSet);
        pieData.setDrawValues(true);

        pieChart.data = pieData;
        pieChart.invalidate();



    }

}