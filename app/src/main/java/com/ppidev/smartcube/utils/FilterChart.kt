package com.ppidev.smartcube.utils

import android.annotation.SuppressLint
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import java.text.SimpleDateFormat
import java.util.Date

sealed class FilterChart {
    object OneDay : FilterChart()
    object OneWeek : FilterChart()
}

@SuppressLint("SimpleDateFormat")
fun createAxisValueFormatter(
    xValuesToDates: List<Pair<Long, Float>>,
    filter: FilterChart
): AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    return AxisValueFormatter { value, _ ->
        val epochSecondDate = xValuesToDates[value.toInt()].first

        val formattedDate = when (filter) {
            FilterChart.OneDay -> {
                val format = SimpleDateFormat("HH:mm")
                val netDate = Date(epochSecondDate * 1000)
                format.format(netDate)
            }

            FilterChart.OneWeek -> {
                val format = SimpleDateFormat("dd-MM")
                val netDate = Date(epochSecondDate * 1000)
                format.format(netDate)
            }

            else -> {
                val date = Date(epochSecondDate)
                val format = SimpleDateFormat("dd-MM-yyyy")
                format.format(date)
            }
        }

        formattedDate
    }
}