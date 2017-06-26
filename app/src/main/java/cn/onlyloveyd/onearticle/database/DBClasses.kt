/**
 * Copyright 2017 yidong
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.onlyloveyd.onearticle.database

import java.io.Serializable

/**
 * 文 件 名: DBClasses
 * 创 建 人: 易冬
 * 创建日期: 2017/6/20 15:06
 * 描   述：
 */
class ArticleInDb(var map: MutableMap<String, Any?>) :Serializable{
    var _id: Long by map
    var title: String by map
    var author: String by map
    var content: String by map
    var wc: String by map
    var prev: String by map
    var curr: String by map
    var next: String by map

    constructor(title: String, author: String, content: String, wc: String, prev: String, curr: String, next: String) : this(HashMap()) {
        this.title = title
        this.author = author
        this.content = content
        this.wc = wc
        this.prev = prev
        this.curr = curr
        this.next = next
    }
}