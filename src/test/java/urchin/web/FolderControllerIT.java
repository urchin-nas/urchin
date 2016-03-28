package urchin.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urchin.testutil.SpringApplication;

@RunWith(SpringJUnit4ClassRunner.class)
public class FolderControllerIT extends SpringApplication {

    private String url;

    @Override
    protected String getPath() {
        return "/folder";
    }

    @Before
    public void setup() {
        url = baseUrl + getPath();
    }

    @Test
    public void todo() {

    }


}