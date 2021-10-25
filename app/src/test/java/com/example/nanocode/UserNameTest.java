package com.example.nanocode;

import static org.junit.Assert.assertEquals;

import com.pdn.eng.cipher.nanochat.CreateUser;

import org.junit.Before;
import org.junit.Test;

public class UserNameTest {
    CreateUser createUser;

    @Before
    public void setUp() throws Exception {
        createUser = new CreateUser();
    }

    @Test
    public void isValidUserNameTest1() {
        assertEquals(false,createUser.isValidUserName("nuwan"));
    }

    @Test
    public void isValidUserNameTest2() {
        assertEquals(true,createUser.isValidUserName("saubhagya"));
    }

    @Test
    public void isValidUserNameTest3() {
        assertEquals(false,createUser.isValidUserName("99saubhagya"));
    }

    @Test
    public void isValidUserNameTest4() {
        assertEquals(true,createUser.isValidUserName("n2637281"));
    }

    @Test
    public void isValidUserNameTest5() {
        assertEquals(false,createUser.isValidUserName("2724212637281"));
    }

}
