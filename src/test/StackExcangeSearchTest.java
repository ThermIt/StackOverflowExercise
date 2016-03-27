package test;

import stackExchange.SearchResults;
import stackExchange.StackExcangeSearch;

import static org.junit.Assert.*;

/**
 * Created by Andrey on 27.03.16.
 */
public class StackExcangeSearchTest {

    @org.junit.Test
    public void search() throws Exception {
        StackExcangeSearch search = new StackExcangeSearch("stackoverflow");

        SearchResults searchWpfResult = search.search("wpf");

        assertNotNull(searchWpfResult);
        assertNotNull(searchWpfResult.getItems());
        assertFalse(searchWpfResult.getItems().size() == 0);
    }
}