package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.daomem.CartDaoMem;
import com.codecool.shop.model.Cart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CartDaoTest {

    Cart cart;
    CartDao dao;

    @Before
    public void setUp() throws Exception {
        cart = new Cart();

        dao = CartDaoMem.getInstance();
    }

    @Test
    public void testAddandFind() throws Exception {
        dao.add(cart);
        assertEquals(cart, dao.find(1));
    }

    @Test
    public void testFindNonexistent() throws Exception {
        assertEquals(null, dao.find(-1));
    }

    @Test
    public void testSingleton() throws Exception {
        assertEquals(dao, CartDaoMem.getInstance());
    }

    @After
    public void tearDown() throws Exception {
        cart = null;
        dao = null;
    }

}