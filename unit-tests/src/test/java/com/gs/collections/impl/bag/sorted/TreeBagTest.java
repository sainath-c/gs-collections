/*
 * Copyright 2013 Goldman Sachs.
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

package com.gs.collections.impl.bag.sorted;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.gs.collections.api.LazyIterable;
import com.gs.collections.api.bag.sorted.MutableSortedBag;
import com.gs.collections.impl.bag.mutable.sorted.TreeBag;
import com.gs.collections.impl.block.factory.Comparators;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * @since 4.2
 */
public class TreeBagTest extends AbstractSortedBagTestCase
{
    @Override
    protected <T> MutableSortedBag<T> classUnderTest()
    {
        return TreeBag.newBag();
    }

    @Override
    protected <T> MutableSortedBag<T> classUnderTest(Comparator<? super T> comparator)
    {
        return TreeBag.newBag(comparator);
    }

    @Override
    protected <T> MutableSortedBag<T> classUnderTest(T... elements)
    {
        return TreeBag.newBagWith(elements);
    }

    @Override
    protected <T> MutableSortedBag<T> classUnderTest(Comparator<? super T> comparator, T... elements)
    {
        return TreeBag.newBag(comparator, Arrays.asList(elements));
    }

    @Override
    @Test(expected = UnsupportedOperationException.class)
    public void asSynchronized()
    {
        //not yet supported
        this.classUnderTest().asSynchronized();
    }

    @Test
    public void sortedBagIterableConstructor()
    {
        TreeBag<Integer> sortedBagA = TreeBag.newBag(Collections.<Integer>reverseOrder());
        TreeBag<Integer> sortedBagB = TreeBag.newBag(sortedBagA.with(1).with(2, 3).with(4, 5, 6).with(1, 1, 1, 1));
        Verify.assertSortedBagsEqual(sortedBagA, sortedBagB);
        Assert.assertTrue(sortedBagA.getFirst().equals(sortedBagB.getFirst()) && sortedBagB.getFirst() == 6);
        Verify.assertSortedBagsEqual(sortedBagB, TreeBag.<Integer>newBag(sortedBagB));
    }

    @Test
    public void sortedBagConstructor()
    {
        MutableSortedBag<String> bagA = TreeBag.newBag(FastList.newListWith("a", "c", "b", "d"));
        Verify.assertSortedBagsEqual(bagA, TreeBag.newBag(bagA));
        Verify.assertSortedBagsEqual(bagA, TreeBag.newBag(bagA));
    }

    @Test
    public void iterableConstructor()
    {
        LazyIterable<Integer> integerLazyIterable = FastList.newListWith(2, 4, 1, 3).asLazy();
        TreeBag<Integer> sortedBag = TreeBag.newBag(Comparators.reverseNaturalOrder(), integerLazyIterable);
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(Comparators.reverseNaturalOrder(), 1, 2, 3, 4), sortedBag);
    }

    @Override
    @Test
    public void serialization()
    {
        MutableSortedBag<Integer> bag = this.newWith(1, 2, 3, 4, 5);
        Verify.assertPostSerializedEqualsAndHashCode(bag);
    }
}
