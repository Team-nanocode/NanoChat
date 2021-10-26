package com.example.nanocode;

import static org.junit.Assert.assertEquals;

import com.pdn.eng.cipher.nanochat.CreateUser;

import org.junit.Before;
import org.junit.Test;

public class PhoneNumberTest {
    CreateUser createUser;

    @Before
    public void setUp() throws Exception {
        createUser = new CreateUser();
    }

    @Test
    public void isValidPhoneNumberTest1() {
        assertEquals(true,createUser.isValidPhoneNumber("0713478279"));
    }

    @Test
    public void isValidPhoneNumberTest2() {
        assertEquals(false,createUser.isValidPhoneNumber("713478"));
    }

    @Test
    public void isValidPhoneNumberTest3() {
        assertEquals(false,createUser.isValidPhoneNumber("absdfuavdn"));
    }

    @Test
    public void isValidPhoneNumberTest4() {
        assertEquals(true,createUser.isValidPhoneNumber("0723274379"));
    }
}
