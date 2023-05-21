package com.example.templatesampleapp.repo.entity

/**
 * @author Umer Bilal
 * Created 5/1/2023 at 6:39 PM
 */

//@Parcelize
//@Entity
//data class TemperatureModel(
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0,
//    @ColumnInfo(name = "Date",defaultValue = "") val date: String?,
//    @ColumnInfo(name = "Time",defaultValue = "") val time: String?,
//    @ColumnInfo(name = "Temperature_F",defaultValue = "0.0") val temperature_F: String?,
//    @ColumnInfo(name = "Temperature_C",defaultValue = "0.0") val temperature_C: String?,
//    @ColumnInfo(name = "Oxygen",defaultValue = "0") val oxygen: String?,
//    @ColumnInfo(name = "Pulse",defaultValue = "0") val pulse: String?,
//    @ColumnInfo(name = "Desc",defaultValue = "") val desc: String?,
//    @ColumnInfo(name = "Tags",defaultValue = "0") val tags: Int=-1,
//    @ColumnInfo(name = "Weight",defaultValue = "") val weight: String?,
//    @ColumnInfo(name = "Height",defaultValue = "") val height: String?,
//):Parcelable{
//
//    @Ignore var dataType: TemperatureAdatper.VIEW_TYPE=TemperatureAdatper.VIEW_TYPE.CARD_VIEW
//    @Ignore var currentDateTypeText: String=""
//
//    fun toCommaSeprated():String{
//        return "$date,$time,$temperature_F,$temperature_C,$oxygen,$pulse,$desc,$tags,$weight,$height,$date,"
//    }
//
//    override fun toString(): String {
//        return "id=$id, date=$date, time=$time, temperature_F=$temperature_F, temperature_C=$temperature_C, oxygen=$oxygen, pulse=$pulse, desc=$desc, tags=$tags, weight=$weight, height=$height"
//    }
//
////    override fun toString(): String {
////
////
////        return  "$id, " +
////                "$date, " +
////                "$time, " +
////                "$temperature_F, " +
////                "$temperature_C, " +
////                "$oxygen, " +
////                "$pulse, " +
////                "$desc, " +
////                "$tags, " +
////                "$weight, " +
////                "$height, " +
////                "$dataType, " +
////                currentDateTypeText
////    }
//
//}