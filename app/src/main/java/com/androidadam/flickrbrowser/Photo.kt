package com.androidadam.flickrbrowser

class Photo(val title: String, val author: String, val authorID: String, val link: String, val tags: String, val image: String) {

    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorID='$authorID', link='$link', tags='$tags', image='$image')"
    }
}