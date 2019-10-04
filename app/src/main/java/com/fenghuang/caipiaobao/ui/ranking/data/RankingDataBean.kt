package com.fenghuang.caipiaobao.ui.ranking.data

data class RankingDataBean(var rankingNumber: String,
                           var name: String,
                           var id: String,
                           var popularity: String)

data class RanKingTopThreeBean(var runnerUpName: String,
                               var runnerUpID: String,
                               var runnerUpPopularity: String,
                               var championName: String,
                               var championID: String,
                               var championPopularity: String,
                               var thirdPlaceName: String,
                               var thirdPlaceID: String,
                               var thirdPlacePopularity: String)