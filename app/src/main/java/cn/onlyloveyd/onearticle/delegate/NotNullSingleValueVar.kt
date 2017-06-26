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
package cn.onlyloveyd.onearticle.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 文 件 名: NotNullSingleValueVar
 * 创 建 人: 易冬
 * 创建日期: 2017/6/19 15:58
 * 描   述：
 */
class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    //Getter函数 如果已经被初始化，则会返回一个值，否则会抛异常。
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("this object not initialized")
    }

    //Setter函数 如果仍然是null，则赋值，否则会抛异常。
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("object already initialized")
    }

    object DelegatesExt {
        fun <T> notNullSingleValue(): ReadWriteProperty<Any?, T> = NotNullSingleValueVar()
    }
}