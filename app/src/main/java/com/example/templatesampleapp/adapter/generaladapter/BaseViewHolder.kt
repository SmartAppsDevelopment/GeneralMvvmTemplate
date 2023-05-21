/*
 * Copyright 2019 vmadalin.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.templatesampleapp.adapter.generaladapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.templatesampleapp.base.BaseModel

/**
 * Base view holder to standardize and simplify initialization for this component.
 *
 * @param binding View data binding generated class instance.
 * @see RecyclerView.ViewHolder
 */
class BaseViewHolderCustom<T> internal constructor(private val binding: ViewBinding, private val experssion:(T, ViewBinding)->Unit)
    :RecyclerView.ViewHolder(binding.root){
    fun bind(item:T){
        experssion(item,binding)
    }
}