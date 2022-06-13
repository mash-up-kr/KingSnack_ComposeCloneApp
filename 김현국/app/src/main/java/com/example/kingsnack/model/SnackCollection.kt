package com.example.kingsnack.model

import androidx.compose.runtime.Immutable

/**
 * @author 김현국
 * @created 2022/06/13
 */

/*
Immutable annotation
클래스를 불변 인스턴스를 생성하는 것으로 표시하는데 사용할 수 있다.
클래스의 불변성은 검증되지 않았으며 공개적으로 액세스할 수 있는 모든 속성과 필드는
안스턴스가 생성된 후 변경되지 않을 것이라는 형식의 약속이다.
이는 setter를 통해 값을 변경할 수 없고, 값도 절대 변경되지 않을 것이라고 약속하기 때문에
val 보다 더 강력한 약속이다.

Immutable은 유형에서 읽은 값이 변경되지 않는다는 가정을 기반으로 수행할 수 있는 구성 최적화를
가능하게 하는 구성에서 사용된다.
사용자 지정 getter가 없는 val 속성만 포함하는 데이터 클래스는 속성 유혀이
기본 유형이거나 불변인 경우 안전하게 불변으로 표싷라 수 있다.
 */

@Immutable
data class SnackCollection(
    val id: Long,
    val name: String,
    val snacks: List<Snack>,
    val type: CollectionType = CollectionType.Normal
)

enum class CollectionType { Normal, HighLight }

/*
fake repo

 */
object SnackRepo {
    fun getSnacks(): List<SnackCollection> = snackCollections
    fun getSnack(snackId: Long) = snacks.find { it.id == snackId }!!
    fun getRelated(@Suppress("UNUSED_PARAMETER") snackId: Long) = related
    fun getInspiredByCart() = inspiredByCart
    fun getFilters() = filters
    fun getPriceFilter() = priceFilters
    fun getCart() = cart
    fun getSortFilters() = sortFilters
    fun getCategoryFilters() = categoryFilters
    fun getSortDefault() = sortDefault
    fun getLifeStyleFilters() = lifeStyleFilters
}

/*
Static data
 */
private val tastyTreats = SnackCollection(
    id = 1L,
    name = "Android's picks",
    type = CollectionType.HighLight,
    snacks = snacks.subList(0, 13)
)
private val popular = SnackCollection(
    id = 2L,
    name = "Popular on Kingsnack",
    snacks = snacks.subList(14, 19)
)
private val wfhFavs = tastyTreats.copy(
    id = 3L,
    name = "WFH favourites"
)
private val newlyAdded = popular.copy(
    id = 4L,
    name = "Newly Added"
)
private val exclusive = tastyTreats.copy(
    id = 5L,
    name = "Only on Jetsnack"
)
private val also = tastyTreats.copy(
    id = 6L,
    name = "Customers also bought"
)
private val inspiredByCart = tastyTreats.copy(
    id = 7L,
    name = "Inspired by your cart"
)

private val snackCollections = listOf(
    tastyTreats,
    popular,
    wfhFavs,
    newlyAdded,
    exclusive
)

private val related = listOf(
    also,
    popular
)

private val cart = listOf(
    Orderline(snacks[4], 2),
    Orderline(snacks[6], 3),
    Orderline(snacks[8], 1)
)
@Immutable
data class Orderline(
    val snack: Snack,
    val count: Int
)
