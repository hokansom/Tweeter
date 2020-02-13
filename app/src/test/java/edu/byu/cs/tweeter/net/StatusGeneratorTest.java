package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class StatusGeneratorTest {

    public StatusGeneratorTest() {

    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void getSampleStatuses() {
//        List<String> samples = StatusGenerator.getInstance().getSampleStatuses();
//        System.out.println(samples);
//        assertNotNull(samples);
//    }

    @Test
    void testThis(){
        System.out.println("A simple test");
    }
}