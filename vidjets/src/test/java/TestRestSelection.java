import widjets.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
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
public class TestRestSelection {
    private static List<List<Widjet>> preparedPages = new ArrayList<List<Widjet>>();

    @Autowired
    RestAPI restAPI;

    @Autowired
    WidjetRepo widjetRepo;

    @BeforeClass
    public static void generatePages() {
        for (int i = 0; i < 10; i++) {
            List<Widjet> currentPage = new ArrayList<Widjet>();
            for(int j = 0; j < 10; j++) {
                currentPage.add(new Widjet(new Long(10*i + j), 0.5 + j, 0.5 + i,
                        10*i + j + 1, 1.0, 1.0 ));
            }
            preparedPages.add(currentPage);
        }
    }

    @After
    public void clearWidjects() {
        widjetRepo.refresh();
    }


    @Test
    public void testPagination() {
        squareWidjets10x10();
        boolean isCorrect = true;
        for(int i = 0; i < 10; i++) {
            List<Widjet> actualPage = restAPI.pageWidjects(i,10);
            isCorrect &= Arrays.equals(actualPage.toArray(), preparedPages.get(i).toArray());
        }
        Assert.assertTrue(isCorrect);
    }

    private void squareWidjets10x10() {
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                restAPI.add(0.5 + j, 0.5 + i, 1.0, 1.0, null);
            }
        }
    }

    @Test
    public void testAreaSelection() {
        squareWidjets10x10();
        List<Widjet> expectedWidjets = new ArrayList<Widjet>();
        expectedWidjets.add(new Widjet(new Long(10*2 + 1), 0.5 + 1, 0.5 + 2,
                10*2 + 1 + 1, 1.0, 1.0 ));
        expectedWidjets.add(new Widjet(new Long(10*1 + 1), 0.5 + 1, 0.5 + 1,
                10*1 + 1 + 1, 1.0, 1.0 ));
        List<Widjet> actualWidjets = restAPI.areaWidjets(0.5, 2.5, 0.5, 3.5);
    }
}
