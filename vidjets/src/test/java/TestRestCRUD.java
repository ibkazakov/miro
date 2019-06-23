import widjets.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import widjets.controllers.RestAPI;
import widjets.repository.Widjet;
import widjets.repository.WidjetRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestRestCRUD {

    @Autowired
    private RestAPI restAPI;

    @Autowired
    private WidjetRepo widjetRepo;


    @Before
    public void initWidjects() {
        restAPI.add(1.0, 1.0, 10.0, 10.0, null);
        restAPI.add(2.0, 2.0, 13.4, 143.0, null);
        restAPI.add(3.0, 3.0, 13.0, 10.0, null);
        restAPI.add(4.0, 4.0, 10.0, 10.0, null);
        restAPI.add(5.0, 5.0, 10.0, 10.0, null);
    }


    @After
    public void clearWidjects() {
        widjetRepo.refresh();
    }


    @Test
    public void testAllGetting() {
        List<Widjet> expectedResult = new ArrayList<Widjet>();
        expectedResult.add(new Widjet(0L,1.0, 1.0, 1,10.0, 10.0));
        expectedResult.add(new Widjet(1L,2.0, 2.0, 2,13.4, 143.0));
        expectedResult.add(new Widjet(2L,3.0, 3.0, 3,13.0, 10.0));
        expectedResult.add(new Widjet(3L,4.0, 4.0, 4,10.0, 10.0));
        expectedResult.add(new Widjet(4L,5.0, 5.0, 5,10.0, 10.0));
        List<Widjet> actualResult = restAPI.allWidjets();

        Assert.assertTrue(Arrays.equals(expectedResult.toArray(), actualResult.toArray()));
    }

    @Test
    public void testDeleting() {
        List<Widjet> expectedResult = new ArrayList<Widjet>();
        expectedResult.add(new Widjet(0L,1.0, 1.0, 1,10.0, 10.0));
        expectedResult.add(new Widjet(2L,3.0, 3.0, 3,13.0, 10.0));
        expectedResult.add(new Widjet(4L,5.0, 5.0, 5,10.0, 10.0));

        restAPI.deleteWidjet(1L);
        restAPI.deleteWidjet(3L);
        List<Widjet> actualResult = restAPI.allWidjets();

        Assert.assertTrue(Arrays.equals(expectedResult.toArray(), actualResult.toArray()));
    }

    @Test
    public void testAddWithoutZ() {
        List<Widjet> expectedResult = new ArrayList<Widjet>();
        expectedResult.add(new Widjet(0L,1.0, 1.0, 1,10.0, 10.0));
        expectedResult.add(new Widjet(1L,2.0, 2.0, 2,13.4, 143.0));
        expectedResult.add(new Widjet(2L,3.0, 3.0, 3,13.0, 10.0));
        expectedResult.add(new Widjet(3L,4.0, 4.0, 4,10.0, 10.0));
        expectedResult.add(new Widjet(4L,5.0, 5.0, 5,10.0, 10.0));
        expectedResult.add(new Widjet(5L,6.0, 6.0, 6,10.0, 10.0));

        restAPI.add(6.0, 6.0, 10.0, 10.0, null);
        List<Widjet> actualResult = restAPI.allWidjets();

        Assert.assertTrue(Arrays.equals(expectedResult.toArray(), actualResult.toArray()));
    }

    @Test
    public void testAddWithExistingZ() {
        List<Widjet> expectedResult = new ArrayList<Widjet>();
        expectedResult.add(new Widjet(0L,1.0, 1.0, 1,10.0, 10.0));
        expectedResult.add(new Widjet(1L,2.0, 2.0, 2,13.4, 143.0));
        expectedResult.add(new Widjet(5L,6.0, 6.0, 3,10.0, 10.0));
        expectedResult.add(new Widjet(2L,3.0, 3.0, 4,13.0, 10.0));
        expectedResult.add(new Widjet(3L,4.0, 4.0, 5,10.0, 10.0));
        expectedResult.add(new Widjet(4L,5.0, 5.0, 6,10.0, 10.0));

        restAPI.add(6.0, 6.0, 10.0, 10.0, 3);
        List<Widjet> actualResult = restAPI.allWidjets();

        Assert.assertTrue(Arrays.equals(expectedResult.toArray(), actualResult.toArray()));

    }

    @Test
    public void testAddWithNonExistingZ() {
        List<Widjet> expectedResult = new ArrayList<Widjet>();
        expectedResult.add(new Widjet(0L,1.0, 1.0, 1,10.0, 10.0));
        expectedResult.add(new Widjet(5L,6.0, 6.0, 3,10.0, 10.0));
        expectedResult.add(new Widjet(4L,5.0, 5.0, 5,10.0, 10.0));

        restAPI.deleteWidjet(1L);
        restAPI.deleteWidjet(2L);
        restAPI.deleteWidjet(3L);
        restAPI.add(6.0, 6.0, 10.0, 10.0, 3);
        List<Widjet> actualResult = restAPI.allWidjets();

        Assert.assertTrue(Arrays.equals(expectedResult.toArray(), actualResult.toArray()));
    }


    @Test
    public void testModify() {
        List<Widjet> expectedResult = new ArrayList<Widjet>();
        expectedResult.add(new Widjet(0L,1.0, 1.0, 1,9.9, 9.9));
        expectedResult.add(new Widjet(1L,2.0, 2.0, 2,13.4, 143.0));
        expectedResult.add(new Widjet(2L,3.0, 3.0, 3,13.0, 10.0));
        expectedResult.add(new Widjet(3L,4.0, 4.0, 5,10.0, 10.0));
        expectedResult.add(new Widjet(4L,5.0, 5.0, 6,10.0, 10.0));

        restAPI.modifyWidjet(0L, null, null, 9.9, 9.9, null);
        restAPI.modifyWidjet(3L, null, null, null, null, 5);
        List<Widjet> actualResult = restAPI.allWidjets();

        Assert.assertTrue(Arrays.equals(expectedResult.toArray(), actualResult.toArray()));
    }

    @Test
    public void testGettingById() {
        Widjet expectedWidjet = new Widjet(1L,2.0, 2.0, 2,13.4, 143.0);
        Widjet actualWidjet = restAPI.getWidjetById(1L);
        Assert.assertEquals(expectedWidjet, actualWidjet);
    }
}
