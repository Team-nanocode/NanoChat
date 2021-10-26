package com.example.nanocode;

import static org.junit.Assert.assertEquals;

import com.pdn.eng.cipher.nanochat.CreateUser;

import org.junit.Before;
import org.junit.Test;

public class PasswordTest {

    CreateUser createUser;

    @Before
    public void setUp() throws Exception {
        createUser = new CreateUser();
    }

    @Test
    public void isValidPasswordTest1() {
        assertEquals(true,createUser.isValidPassword("A8&opw99"));
    }

    @Test
    public void isValidPasswordTest2() {
        assertEquals(false,createUser.isValidPassword("A8&o"));
    }

    @Test
    public void isValidPasswordTest3() {
        assertEquals(false,createUser.isValidPassword("Weerathunga"));
    }

    @Test
    public void isValidPasswordTest4() {
        assertEquals(true,createUser.isValidPassword("Bbfads8&opw99dbj"));
    }

    @Test
    public void isValidPasswordTest5() {
        assertEquals(false,createUser.isValidPassword("12638290233"));
    }

    @Test
    public void isValidPasswordTest6() {
        assertEquals(false,createUser.isValidPassword(""));
    }
}
