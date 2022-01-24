package com.flethy.androidacademy.domain

import com.flethy.androidacademy.data.models.Actor

class ActorsDataSource {
    fun getActors(): List<Actor> {
        return listOf<Actor>(
            Actor("Robert Downey Jr.", "https://s3-alpha-sig.figma.com/img/a58c/56ed/0f8f5ec204f1a37e0e15e2a731ab190a?Expires=1643587200&Signature=fvaWCnYep2M8wyfxfdOEQnQItir9nMYAjOn9bHh5GyTq1j2mm~0QZMl0tCQftjmNt0yxrV-MAe9MD9qXxvdXFZBtALfAvcFTVyASWClRXxNQk2WOaXRCFazaSKrIQKKUz4kgac-xw71~5IC8gNIlsdRMZuKLScQ2C7rcznTyzDo7jj2KBWcDRznX9vDD-94d8vSiyHtjjLlzgQmH8l5uPYAWo2swrZzfBuwZescNzK7owBMybPyfGPX7ZTe~4RHNeVzcd8lumTPpORjUkuBCUeXIcUBrMFcxRWx3fiN~kLcTamkS727QWsxSNAG5wgrxZ6XNYYzJC2aRKU920DvSCw__&Key-Pair-Id=APKAINTVSUGEWH5XD5UA"),
            Actor("Chris Evans", "https://www.figma.com/file/p3e0HZexHmxwQaN9NcwAD9/image/95e567945c9007d7ca26a2d00d93fe8d18c89643?fuid=743513597300855546"),
            Actor("Mark Ruffalo", "https://www.figma.com/file/p3e0HZexHmxwQaN9NcwAD9/image/d3fabc44eeb8d6e5b9c059a681dcbe67501dceae?fuid=743513597300855546"),
            Actor("Chris Hemsworth", "https://www.figma.com/file/p3e0HZexHmxwQaN9NcwAD9/image/7bd1c6fef56e2ec650a0ee6e64c9b1356e383bb3?fuid=743513597300855546")
        )
    }
}