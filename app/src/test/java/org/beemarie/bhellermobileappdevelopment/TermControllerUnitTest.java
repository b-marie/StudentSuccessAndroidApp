package org.beemarie.bhellermobileappdevelopment;

import org.beemarie.bhellermobileappdevelopment.data.DataSourceInterface;
import org.beemarie.bhellermobileappdevelopment.view.TermViewInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class TermControllerUnitTest {

    @Mock
    DataSourceInterface dataSource;

    @Mock
    TermViewInterface view;


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}