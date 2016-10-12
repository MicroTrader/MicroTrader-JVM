/*
 * _______________________________________________________________________________
 *
 * Copyright (c) 2016. Suminda Sirinath Salpitikorala Dharmasena and
 *     MicroTrader-JVM Project Contributors
 *
 * MicroTrader-JVM, is a collection of works and / or artifacts submitted
 * and / or contributed by multiple authors ("Project Contributors"),
 * collectively creating a larger work(s) and / or artifact(s) (the / this
 * "Project"). This project is licensed under the terms of either:
 *  - the Apache License, Version 2.0 (ASL 2.0), or
 *  - the Academic Free License, Version 3.0 (AFL 3.0), or
 *  - the MIT License (MIT), collectively referred as the "Project Licenses".
 * As a recipient of this Project, you may choose which license to receive
 * the Project under (except as noted in other explicit declarations and / or
 * notices of intellectual property rights). Some artifacts and / or works may
 * not be the intellectual property of the Project Contributors. These are
 * noted in explicit declarations and / or notices of intellectual property
 * rights.
 *
 * This Project uses a shared copyright model. Each contributor maintains
 * copyright over their contributions and / or submissions to this Project.
 * Contributions and / or submissions are typically transformation,
 * modification or adaptation of existing artifacts or works (underlying work)
 * resulting in derivative works. Thus, the Project artifacts, in its entirety
 * is not the copyright of any single person or institution. Instead, it is the
 * collective copyright of all those who have made contributions and / or
 * submissions to the Project.
 *
 * No contributions and / or submissions are allowed under licenses which are
 * fundamentally incompatible with the Project Licenses under which this
 * Project is licenced under. By contributing or making a submission to this
 * Project you hereby understand and agree to the following:
 *  - your contributions and / or submissions are licensed under the terms of
 *    all the Project Licenses whereas the recipients are free to choose under
 *    which license the contributions and submission is received under;
 *  - you will only make contributions and / or submissions which you own the
 *    intellectual property right or have appropriate and adequate licenses,
 *    and in addition have the authority and ability to make the contributions
 *    and / or submissions, under the terms of the Project Licenses, without
 *    encumbrances, limitations and restrictions whatsoever;
 *  - you comply and adhere to the adopted: code of conduct, norms, etiquettes
 *    and protocols of the Project in all you dealing with the Project.
 *
 * _______________________________________________________________________________
 *
 * Copyright (c) 2016. Suminda Sirinath Salpitikorala Dharmasena and
 *     MicroTrader-JVM Project Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * _______________________________________________________________________________
 *
 * Copyright (c) 2016. Suminda Sirinath Salpitikorala Dharmasena and
 *     MicroTrader-JVM Project Contributors
 *
 * Licensed under the Academic Free License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/AFL-3.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * _______________________________________________________________________________
 *
 * The MIT License (MIT)
 * Copyright (c) 2016. Suminda Sirinath Salpitikorala Dharmasena and
 *     MicroTrader-JVM Project Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * _______________________________________________________________________________
 */

package com.github.microtrader.fabric

import it.unimi.dsi.fastutil.booleans.BooleanArrayList
import it.unimi.dsi.fastutil.doubles.DoubleArrayList
import it.unimi.dsi.fastutil.longs.LongArrayList
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j

/**
  * Created by sirinath on 29/09/2016.
  */
abstract class Ports(val window: Int) {
  type ThisType <: Ports
  type ElementType
  type ContainerType

  def assign(other: ThisType): ThisType

  def at(index: Int): ElementType

  def dropLast: ElementType

  def history: ContainerType
}

class ObjectPort[T](window: Int) extends Ports(window = window) {
  type ThisType = ObjectPort[T]
  type ElementType = T
  type ContainerType = ObjectArrayList[ElementType]
  private[this] val port: ContainerType = new ObjectArrayList[ElementType](window)

  def assign(other: ThisType): ThisType = {
    port.add(0, other.at(0))

    this
  }

  def at(index: Int): ElementType = port.get(index)

  def dropLast: ElementType = port.remove(0)

  def history: ContainerType = {
    port.trim(window)
    port
  }
}

class DoublePort(window: Int) extends Ports(window = window) {
  type ThisType = DoublePort
  type ElementType = Double
  type ContainerType = DoubleArrayList
  private[this] val port: ContainerType = new DoubleArrayList(window)

  def assign(other: ThisType): ThisType = {
    port.add(0, other.at(0))

    this
  }

  def at(index: Int): ElementType = port.getDouble(index)

  def dropLast: ElementType = port.removeDouble(0)

  def history: ContainerType = {
    port.trim(window)
    port
  }
}

class LongPort(window: Int) extends Ports(window = window) {
  type ThisType = LongPort
  type ElementType = Long
  type ContainerType = LongArrayList
  private[this] val port: ContainerType = new LongArrayList(window)

  def assign(other: ThisType): ThisType = {
    port.add(0, other.at(0))

    this
  }

  def at(index: Int): ElementType = port.getLong(index)

  def dropLast: ElementType = port.removeLong(0)

  def history: ContainerType = {
    port.trim(window)
    port
  }
}

class BoolPort(window: Int) extends Ports(window = window) {
  type ThisType = BoolPort
  type ElementType = Boolean
  type ContainerType = BooleanArrayList
  private[this] val port: ContainerType = new BooleanArrayList(window)

  def assign(other: ThisType): ThisType = {
    port.add(0, other.at(0))

    this
  }

  def at(index: Int): ElementType = port.getBoolean(index)

  def dropLast: ElementType = port.removeBoolean(0)

  def history: ContainerType = {
    port.trim(window)
    port
  }
}

class NDArrayPort(window: Int, val inputs: Int) extends Ports(window = window) {
  type ThisType = NDArrayPort
  type ElementType = INDArray
  type ContainerType = INDArray
  private[this] val port: ContainerType = Nd4j.create(window, inputs)
  port.setWrapAround(true)

  def assign(other: ThisType): ThisType = {
    for (i <- 0 to (window - 1)) {
      port.putRow(i + 1, port.getRow(i))
    }

    port.putRow(0, other.at(0))

    this
  }

  def at(index: Int): ElementType = port.getRow(index)

  def dropLast: ElementType = {
    val row0 = port.getRow(0)
    for (i <- 1 to window) {
      port.putRow(i - 1, port.getRow(i))
    }

    row0
  }

  def history: ContainerType = port
}

object to {
  def apply[A <: Ports](from: A#ThisType, to: A#ThisType): Link[A] = new Link[A](from, to)
}

class Link[A <: Ports](val from: A#ThisType, val to: A#ThisType) extends Runnable {
  override def run(): Unit = to.assign(from)
}