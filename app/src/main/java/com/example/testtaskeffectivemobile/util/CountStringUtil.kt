package com.example.testtaskeffectivemobile.util

class CountStringUtil {
    companion object {
        fun getStringCountAvailable(count: Int) =
            "Доступно для заказа $count ${
                if (count in (11..19))
                    "штук"
                else
                    when (count % 10) {
                        0 -> "штук"
                        1 -> "штука"
                        in 2..4 -> "штуки"
                        in 5..9 -> "штук"
                        else -> ""
                    }
            }"

        fun getStringCountFeedback(count: Int) =
            "$count ${
                if (count in (11..19))
                    "отзывов"
                else
                    when (count % 10) {
                        0 -> "отзывов"
                        1 -> "отзыв"
                        in 2..4 -> "отзыва"
                        in 5..9 -> "отзывов"
                        else -> ""
                    }
            }"

        fun getStringCountFavoriteProduct(count: Int) =
            "$count ${
                if (count in (11..19))
                    "товаров"
                else
                    when (count % 10) {
                        0 -> "товаров"
                        1 -> "товар"
                        in 2..4 -> "товара"
                        in 5..9 -> "товаров"
                        else -> ""
                    }
            }"
    }
}